B4J=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=10.7
@EndOfDesignText@
#Region Class Info
' Project:      rOpen62541 (OPC UA Server Client Module)
' File:         OpcUaClient.bas
' Brief:        Proper Object-Oriented Class for managing OPC UA via PyBridge.
' Date:         2026-09-15
' Author:       Robert W.B. Linn (c) 2026 - MIT
' Description:  Manages asynchronous OPC UA connections and live telemetry 
'               subscriptions using a decoupled producer-consumer memory queue.
'				The trigger nodeid "ns=1;s=Trigger" is used to send a command to the OPC US Server.
'				(see InjectPythonCoreCode > write_trigger)
#End Region

Sub Class_Globals
	' UI
	Private xui As XUI
	
	' Events
	Private mTarget As Object
	Private mEventName As String
	Private EVENT_OPC_CONNECTION_CHANGED	As String = "opc_connection_changed"
	Private EVENT_OPC_DATACHANGED 			As String = "opc_datachange"			' Python uses data_change, but B4J event data_changed to align with other B4J events
	
	' PyBridge Instance
	Private Py As PyBridge
	
	' OPC UA Server flags
	Private IsConnected As Boolean = False
End Sub

' Initializes the class module and links it to the PyBridge engine framework
Public Sub Initialize (TargetModule As Object, EventName As String, PyBridgeEngine As PyBridge)
	mTarget = TargetModule
	mEventName = EventName
	Py = PyBridgeEngine
	
	' Inject the core Python engine code into global memory instantly
	InjectPythonCoreCode
End Sub

#Region Connection
' Connect Method Profile matching the direct asynchronous pattern
Public Sub Connect (ServerIp As String, Port As Int)
	Dim CodeExec As String = $"
def CallConnect():
    global global_worker
    if global_worker is None:
        global_worker = OpcSyncWorker("${ServerIp}", ${Port})
    global_worker.connect_server()
    return "OK"
"$
	Py.RunCode("CallConnect", Array(), CodeExec)
End Sub

' Disconnect Method Profile
Public Sub Disconnect
	Dim CodeExec As String = $"
def CallDisconnect():
    if global_worker:
        global_worker.disconnect_server()
    return "OK"
"$
	Py.RunCode("CallDisconnect", Array(), CodeExec)
End Sub

' Connected
' Read-only Property getter to check connection state from the main page
' Used like Opc.Connected
Public Sub getConnected As Boolean
	Return IsConnected
End Sub
#End Region

#Region  Node Read Write
' Subscribe
' Subscribe (Read) Method Profile with forced argument cache-clearing array
Public Sub Subscribe (NodeId As String) As PyWrapper
	Dim CodeExec As String = $"
def CallSubscribe(target_node_str):
    if global_worker:
        return global_worker.subscribe_node(target_node_str)
    return False
"$
	Return Py.RunCode("CallSubscribe", Array(NodeId), CodeExec)
End Sub

' SendOpcTrigger 
' Send (Write) method profile
Public Sub SendOpcTrigger (CommandValue As String) As PyWrapper
	Dim CodeExec As String = $"
def SendOpcTrigger(cmd):
    if global_worker:
        return global_worker.write_trigger(cmd)
    return "Error"
"$
	Return Py.RunCode("SendOpcTrigger", Array(CommandValue), CodeExec)
End Sub
#End Region

#Region Events
' Safe Asynchronous Event Queue Poller (Exactly 1 return value to prevent casting faults)
Public Sub PollNextEvent As ResumableSub
	Dim CodeExec As String = $"
def PollNextEvent():
    global global_event_queue
    if len(global_event_queue) > 0:
        return global_event_queue.pop(0)
    return None
"$
	Dim Res As PyWrapper = Py.RunCode("PollNextEvent", Array(), CodeExec)
	Wait For (Res.Fetch) Complete (FetchedResult As PyWrapper)
	Return FetchedResult.Value
End Sub

' Core 2-parameter event handler dispatcher routing back up to the MainPage
Public Sub RaiseB4jEvent (EventName As String, Value As Object)
	Log($"[OpcUaClient] RaiseB4jEvent name=${EventName} | value=${Value}"$)
	' [OpcClient_DataChanged] Event Received nodeid=[ns=1;s=LedState] | value=0
	
	' Select the event raised
	Select EventName
		Case EVENT_OPC_CONNECTION_CHANGED
			IsConnected = Value
			If xui.SubExists(mTarget, mEventName & "_ConnectionChanged",1) Then
				CallSub2(mTarget, mEventName & "_ConnectionChanged", IsConnected)
			End If

		Case EVENT_OPC_DATACHANGED
			' Value arrives as a combined space-separated text string: "NodeID Value"
			Dim Line As String = Value
			Dim Parts() As String = Regex.Split(" ", Line)
			
			If Parts.Length > 1 Then
				Dim NodeId As String = Parts(0).Trim
				Dim RealValue As String = Parts(1).Trim
					
				' Forward the isolated components using CallSub3
				If xui.SubExists(mTarget, mEventName & "_DataChanged", 1) Then
					CallSub3(mTarget, mEventName & "_DataChanged", NodeId, RealValue)
				End If
			End If
		Case Else
			Log($"[OpcUaClient][W] RaiseB4jEvent unknown event name=${EventName} | value=${Value}"$)			
	End Select
End Sub
#End Region

'=============================================================================
' INTERNAL CORE PYBRIDGE ENGINE CODE INJECTION (RESTORED CLEAN COUPLING)
'=============================================================================
Private Sub InjectPythonCoreCode
	Dim Code As String = $"
import logging
from asyncua import ua
from asyncua.sync import Client

global_worker = None
global_event_queue = []

class SyncSubHandler:
    def datachange_notification(self, node, val, data):
        global global_event_queue
        node_id_str = node.nodeid.to_string()
        
        # Combine Node ID and Value into a single text entry string payload
        # This completely side-steps numeric format casting crashes for strings like "ledoff"
        combined_payload = f"{node_id_str} {val}"
        global_event_queue.append({"event": "opc_datachange", "value": combined_payload})

    def status_change_notification(self, status):
        pass

class OpcSyncWorker:
    def __init__(self, ip, port):
        self.url = f"opc.tcp://{ip}:{port}"
        self.client = Client(self.url, timeout=10)
        self.is_connected = False

    def connect_server(self):
        global global_event_queue
        try:
            print(f"[OpcUaClient] Connecting to {self.url}...", flush=True)
            self.client.connect()
            self.is_connected = True
            global_event_queue.append({"event": "opc_connection_changed", "value": True})
        except Exception as err:
            print(f"[OpcUaClient][E] Connection failed: {err}", flush=True)
            self.is_connected = False
            global_event_queue.append({"event": "opc_connection_changed", "value": False})

    def subscribe_node(self, node_str):
        try:
            if not self.is_connected:
                return
            
            # FIX: Create ONE single master subscription session container if it doesn't exist yet
            if not hasattr(self, 'master_sub') or self.master_sub is None:
                handler = SyncSubHandler()
                self.master_sub = self.client.create_subscription(100, handler)
            
            # Reuse that single channel to monitor multiple nodes securely
            target_node = self.client.get_node(node_str)
            self.master_sub.subscribe_data_change(target_node)
            print(f"[OpcUaClient] Subscribed cleanly to {node_str}", flush=True)
        except Exception as err:
            print(f"[OpcUaClient][E] Subscription error: {err}", flush=True)

    def write_trigger(self, value_str):
        try:
            if not self.is_connected:
                return
            trigger_node = self.client.get_node("ns=1;s=Trigger")
            payload = ua.Variant(value_str, ua.VariantType.String)
            trigger_node.write_value(payload)
        except Exception as err:
            print(f"[OpcUaClient][E] Write error: {err}", flush=True)

    def disconnect_server(self):
        global global_event_queue
        try:
            import asyncio
            if hasattr(self.client, 'tloop') and self.client.tloop:
                asyncio.run_coroutine_threadsafe(self.client.disconnect(), self.client.tloop.loop).result()
            else:
                self.client.disconnect()
        except:
            pass
        self.is_connected = False
        global_event_queue.append({"event": "opc_connection_changed", "value": False})

def InjectPythonCoreCode():
    return "Ready"
"$
	Py.RunCode("InjectPythonCoreCode", Array(), Code)
End Sub
