B4J=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=10.7
@EndOfDesignText@
#Region Class Info
' Project:      rOpen62541 (OPC UA Server Client Module)
' File:         PyOpcUaClient.bas
' Brief:        Proper Object-Oriented Class for managing OPC UA via PyBridge.
' Date:         See class_globals VERSION.
' Author:       Robert W.B. Linn (c) 2026 - MIT
' Description:  Manages asynchronous OPC UA connections and live telemetry 
'               subscriptions using a decoupled producer-consumer memory queue.
#End Region

Sub Class_Globals
	' version
	Public VERSION As String = "PyOpcUaClient v20261013"
	
	' UI
	Private xui As XUI
	
	' Events
	Private mTarget As Object
	Private mEventName As String
	
	' OPC UA events
	Private EVENT_OPC_CONNECTION_CHANGED	As String = "opc_connection_changed"
	Private EVENT_OPC_DATACHANGED 			As String = "opc_datachange"			' Python uses data_change, but B4J event data_changed to align with other B4J events
	Private EVENT_OPC_BROWSE_ITEM			As String = "opc_browse_item"
	Private EVENT_OPC_CALL_METHOD			As String = "opc_call_method"
	Private EVENT_OPC_READ_RESULT			As String = "opc_read_result"
	
	' OPC UA public nodes
	Public NODEID_ROOT_OBJECTS 				As String = "ns=0;i=85"
	Public NODEID_FACTORY_FLOOR 			As String = "ns=1;s=Factory_Floor"
	Public NODEID_TRIGGER					As String = "ns=1;s=Trigger"			' Trigger callback in B4R > onTriggerCallback
	Public NODEID_TRIGGER_RESULT			As String = "ns=1;s=TriggerResult"
		
	' PyBridge Instance
	Private Py As PyBridge
	
	' OPC UA Server flags
	Public IsConnected As Boolean = False
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
' Connect Method Profile matching the direct asynchronous pattern
Public Sub Connect (ServerIp As String, Port As Int)
	Dim CodeExec As String = $"
def CallConnect():
    global global_worker
    global_worker = None 
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

' Check OPC UA connection
' Performs a lightweight connection check through the Python worker
Public Sub CheckConnection As ResumableSub
	Dim CodeExec As String = $"
def CallCheckConnection():
    if global_worker:
        return global_worker.check_connection()
    return False
"$
	Dim PyResult As PyWrapper = Py.RunCode("CallCheckConnection", Array(), CodeExec)
	Wait For (PyResult.Fetch) Complete (PyResult As PyWrapper)
	IsConnected = PyResult.Value
	Return IsConnected
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

' WriteFloat
Public Sub WriteFloat(NodeID As String, Value As String) As PyWrapper
	Dim CodeExec As String = $"
def WriteFloat(nodeid, val):
    if global_worker:
        return global_worker.write_float(nodeid, val)
    return "Error"
"$
	Return Py.RunCode("WriteFloat", Array(NodeID, Value), CodeExec)
End Sub

' WriteInt
Public Sub WriteInt(NodeID As String, Value As String) As PyWrapper
	Dim CodeExec As String = $"
def WriteInt(nodeid, val):
    if global_worker:
        return global_worker.write_int(nodeid, val)
    return "Error"
"$
	Return Py.RunCode("WriteInt", Array(NodeID, Value), CodeExec)
End Sub

' WriteBool
Public Sub WriteBoolean(NodeID As String, Value As String) As PyWrapper
	Dim CodeExec As String = $"
def WriteBoolean(nodeid, val):
    if global_worker:
        return global_worker.write_boolean(nodeid, val)
    return "Error"
"$
	Return Py.RunCode("WriteBoolean", Array(NodeID, Value), CodeExec)
End Sub

' WriteTrigger 
' Write special trigger
Public Sub WriteTrigger (NodeID As String, Value As String) As PyWrapper
	Dim CodeExec As String = $"
def WriteTrigger(nodeid, val):
    if global_worker:
        return global_worker.write_trigger(nodeid, val)
    return "Error"
"$
	Return Py.RunCode("WriteTrigger", Array(NodeID, Value), CodeExec)
End Sub

' WriteByteArray
Public Sub WriteByteArray(NodeID As String, Value() As Byte) As PyWrapper
	Dim CodeExec As String = $"
def WriteByteArray(nodeid, arr):
	if global_worker:
		return global_worker.write_bytearray(nodeid, arr)
	return "Error"
"$
	Return Py.RunCode("WriteByteArray", Array(NodeID, Value), CodeExec)
End Sub

' Read
' Read a nodeid value
Public Sub Read (NodeID As String) As PyWrapper
	Dim CodeExec As String = $"
def Read(nodeid):
    if global_worker:
        return global_worker.read(nodeid)
    return "Error"
"$
	Return Py.RunCode("Read", Array(NodeID), CodeExec)
End Sub

' Browse
' Browse for child nodes from a root node.
' [PyBridge Browser] Found 1 children under ns=1;s=Factory_Floor
' [PyOpcUaClient] RaiseB4jEvent name=opc_browse_item, value=ns=1;s=ByteString|ByteString|Variable
Public Sub Browse (StartNodeId As String)
	Dim CodeExec As String = $"
def CallBrowse(target_start_node):
    if global_worker:
        global_worker.browse_and_sync_nodes(target_start_node)
    return "OK"
"$
	Py.RunCode("CallBrowse", Array(StartNodeId), CodeExec)
End Sub
#End Region

' CallMethod
' Synchronously executes an explicit industrial OPC UA RPC method call on the remote ESP32-S3 Server.
' Pass parameters directly as strings; the underlying engine handles automatic binary encoding layouts.
Public Sub CallMethod(NodeId As String, Argument As String) As PyWrapper
	' Send the string instruction command payload through the PyBridge pipe channel
	Dim CodeExec As String = $"
def CallMethod(nodeid, cmd):
	if global_worker:
		return global_worker.call_method(nodeid, cmd)
"$
	Return Py.RunCode("CallMethod", Array(NodeId, Argument), CodeExec)
End Sub

' EVENTS

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
	Log($"[PyOpcUaClient] RaiseB4jEvent name=${EventName}, value=${Value}"$)
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
			Dim Parts() As String = Regex.Split(" ", Value)
			
			If Parts.Length > 1 Then
				Dim NodeId As String = Parts(0).Trim
				Dim NewValue As String = Parts(1).Trim
					
				' Forward the isolated components using CallSub3
				If xui.SubExists(mTarget, mEventName & "_DataChanged", 1) Then
					CallSub3(mTarget, mEventName & "_DataChanged", NodeId, NewValue)
				End If
			End If
			
		Case EVENT_OPC_BROWSE_ITEM
			' Value arrives as a combined pipelined text row entry string: "NodeID|DisplayName|NodeClass"
			Dim RowLine As String = Value
			Dim Parts() As String = Regex.Split("\|", RowLine)
			
			If Parts.Length >= 3 Then
				Dim DiscoveredNodeId As String = Parts(0).Trim
				Dim DiscoveredName As String = Parts(1).Trim
				Dim DiscoveredClass As String = Parts(2).Trim
				
				' Package into a standard B4X Map mirroring your Milo output structure perfectly
				Dim ItemMap As Map
				ItemMap.Initialize
				ItemMap.Put("NodeId", DiscoveredNodeId)
				ItemMap.Put("DisplayName", DiscoveredName)
				ItemMap.Put("NodeClass", DiscoveredClass)
				
				' Forward the structured element up to your active MainPage callback handler hook
				If xui.SubExists(mTarget, mEventName & "_BrowseItemFound", 1) Then
					CallSub2(mTarget, mEventName & "_BrowseItemFound", ItemMap)
				End If
			End If

		Case EVENT_OPC_CALL_METHOD
			If xui.SubExists(mTarget, mEventName & "_MethodCalled",1) Then
				CallSub2(mTarget, mEventName & "_MethodCalled", Value)
			End If

		Case EVENT_OPC_READ_RESULT
			If xui.SubExists(mTarget, mEventName & "_ReadResult",1) Then
				Dim Parts() As String = Regex.Split("#", Value)
				If Parts.Length >= 2 Then
					Dim NodeId As String = Parts(0).Trim
					Dim NewValue As String = Parts(1).Trim
					CallSub3(mTarget, mEventName & "_ReadResult", NodeId, NewValue)
				End If
			End If

		Case Else
			Log($"[PyOpcUaClient][W] RaiseB4jEvent unknown event name=${EventName} | value=${Value}"$)			
	End Select
End Sub
#End Region

'=============================================================================
' INTERNAL CORE PYBRIDGE ENGINE CODE INJECTION (RESTORED CLEAN COUPLING)
'=============================================================================

Private Sub InjectPythonCoreCode
	Dim Code As String = $"
import logging
import json
from asyncua import ua
from asyncua.sync import Client
logging.getLogger("asyncua").setLevel(logging.CRITICAL)	# Setting to CRITICAL surpresses connection error also

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
            print(f"[PyOpcUaClient] Connecting to {self.url}...", flush=True)
            self.client.connect()
            self.is_connected = True
            global_event_queue.append({"event": "opc_connection_changed", "value": True})
        except Exception as err:
            print(f"[PyOpcUaClient][E] Connection failed: {err}", flush=True)
            self.is_connected = False
            global_event_queue.append({"event": "opc_connection_changed", "value": False})

    def disconnect_server(self):
        global global_event_queue
        self.is_connected = False
        try:
            if hasattr(self, 'master_sub') and self.master_sub:
                try:
                    self.master_sub.delete()
                except Exception:
                    pass
                self.master_sub = None

            if self.client:
                try:
                    self.client.disconnect()
                except Exception:
                    pass
        finally:
            global_event_queue = []
            global_event_queue.append({"event": "opc_connection_changed", "value": False})

    def check_connection(self):
        """
        Check whether the OPC UA connection is still available.
        """

        global global_event_queue

        if not self.is_connected:
            return False

        try:
            # Read the standard Server object.
            # Node i=2258 is the OPC UA Server object.
            self.client.get_node("i=2258").read_browse_name()

            return True

        except Exception as err:
            print(
                f"[PyOpcUaClient][W] Connection lost: {err}",
                flush=True
            )

            self.is_connected = False

            global_event_queue.append({
                "event": "opc_connection_changed",
                "value": False
            })

            return False
		
    def subscribe_node(self, node_str):
        try:
            if not self.is_connected:
                return
            
            # Create ONE single master subscription session container if it doesn't exist yet
            if not hasattr(self, 'master_sub') or self.master_sub is None:
                handler = SyncSubHandler()
                self.master_sub = self.client.create_subscription(100, handler)
            
            # Reuse that single channel to monitor multiple nodes securely
            target_node = self.client.get_node(node_str)
            self.master_sub.subscribe_data_change(target_node)
            print(f"[PyOpcUaClient] Subscribed to {node_str}", flush=True)
        except Exception as err:
            print(f"[PyOpcUaClient][E] Subscription to node {node_str} error: {err}", flush=True)
			# [PyOpcUaClient][E] Subscription error: The node id refers to a node that does not exist in the server address space.(BadNodeIdUnknown)

    def write_trigger(self, nodeid_str, value_str):
        try:
            if not self.is_connected:
                return
            trigger_node = self.client.get_node(nodeid_str)
            payload = ua.Variant(value_str, ua.VariantType.String)
            trigger_node.write_value(payload)
        except Exception as err:
            print(f"[PyOpcUaClient][E] Write error: {err}", flush=True)

    def write_boolean(self, nodeid_str, value_str):
        try:
            if not self.is_connected:
                return
            write_node = self.client.get_node(nodeid_str)
            
            # 1. Standardize text input and parse it into a real Python Boolean
            # This handles inputs like "true", "True", "1", or "on" safely
            clean_val = value_str.strip().lower()
            bool_value = clean_val in ("true", "1", "on", "yes")
            
            # 2. CRITICAL: Wrap it as an OPC UA Boolean (1 byte)
            payload = ua.Variant(bool_value, ua.VariantType.Boolean)
            
            write_node.write_value(payload)
            # print(f"[PyOpcUaClient] Successfully wrote Boolean {bool_value} to {nodeid_str}")
        except Exception as err:
            print(f"[PyOpcUaClient][E] Write error: {err}", flush=True)

    def write_int(self, nodeid_str, value_str):
        try:
            if not self.is_connected:
                return
            write_node = self.client.get_node(nodeid_str)
            
            # 1. Convert the incoming string text "35" into a real Python int
            numeric_value = int(value_str) 
            
            # 2. CRITICAL: Specify Int32 to match open62541's explicit data type layouts
            payload = ua.Variant(numeric_value, ua.VariantType.Int32)
            
            write_node.write_value(payload)
            # print(f"[PyOpcUaClient] Successfully wrote Int32 {numeric_value} to {nodeid_str}")
        except Exception as err:
            print(f"[PyOpcUaClient][E] Write error: {err}", flush=True)

    def write_float(self, nodeid_str, value_str):
        try:
            if not self.is_connected:
                return
            write_node = self.client.get_node(nodeid_str)
            
            # 1. Convert the incoming string text "35.0" into a real Python float
            numeric_value = float(value_str) 
            
            # 2. CRITICAL: Wrap it strictly as an OPC UA Float (Float32) payload
            payload = ua.Variant(numeric_value, ua.VariantType.Float)
            
            write_node.write_value(payload)
            # print(f"[PyOpcUaClient] Successfully wrote float {numeric_value} to {nodeid_str}")
        except Exception as err:
            print(f"[PyOpcUaClient][E] Write error: {err}", flush=True)

    def write_bytearray(self, nodeid_str, arr):
        try:
            if not self.is_connected:
                return

            write_node = self.client.get_node(nodeid_str)

            # Convert the PyBridge input to actual bytes.
            if isinstance(arr, bytes):
                byte_array = arr
            elif isinstance(arr, (bytearray, list, tuple)):
                byte_array = bytes(arr)
            elif isinstance(arr, str):
                # Fallback if PyBridge transfers the array as text.
                byte_array = arr.encode("latin-1")
            else:
                raise TypeError(
                    f"Unsupported byte array type: {type(arr).__name__}"
                )

            # Create an OPC UA ByteString variant.
            payload = ua.Variant(
                byte_array,
                ua.VariantType.ByteString
            )

            write_node.write_value(payload)

            #print(
            #    f"[PyOpcUaClient] Successfully wrote ByteString "
            #    f"{byte_array.hex(' ')} to {nodeid_str}",
            #    flush=True
            #)

        except Exception as err:
            print(
                f"[PyOpcUaClient][E] WriteByteArray error: {err}",
                flush=True
            )

    def browse_and_sync_nodes(self, start_node_str):
        """
        Generically browses any provided starting NodeID, discovers all immediate 
        child variable configurations, and marshals them back to B4J.
        """
        global global_event_queue
        try:
            if not self.is_connected or not self.client:
                print("[PyOpcUaClient][W] Discovery skipped - Client is offline", flush=True)
                return
            
            # 1. Target the requested node dynamically (e.g. "ns=1;s=Factory_Floor")
            target_node = self.client.get_node(start_node_str)
            child_nodes = target_node.get_children()
            
            print(f"[PyBridge Browser] Found {len(child_nodes)} children under {start_node_str}", flush=True)
            
            for node in child_nodes:
                # 2. Extract internal structural parameters natively from wire descriptors
                node_id_str = node.nodeid.to_string()
                browse_name = node.read_browse_name().Name
                display_name = node.read_display_name().Text
                
                # Fetch node class type safely to verify it's a variable node
                node_class_int = node.read_node_class()
                node_class_str = "Variable" if node_class_int == 2 else "Object"
                
                # 3. Serialize metadata into a safe, uniform string layout block
                # Format layout pattern: "NodeID|DisplayName|NodeClass"
                serialized_payload = f"{node_id_str}|{display_name}|{node_class_str}"
                
                # 4. Append directly into the event consumer queue to trigger RaiseB4jEvent
                global_event_queue.append({"event": "opc_browse_item", "value": serialized_payload})
                
        except Exception as err:
            print(f"[PyOpcUaClient][E] Dynamic Python browsing failed: {err}", flush=True)

    def call_method(self, method_node_str, value_str):
        try:
            if not self.is_connected:
                return
            
            # Hardcode the absolute static Parent Object Node ID
            parent_object = self.client.get_node("ns=1;s=Factory_Floor")
            target_method = self.client.get_node(method_node_str)
            
            # Binary Translation: Encode string payload to raw UTF-8 bytes
            payload = ua.Variant(value_str.encode("utf-8"), ua.VariantType.ByteString)
            
            print(f"[PyOpcUaClient] Invoking Method {method_node_str}...", flush=True)
            
            # Synchronous cross-core execution call
            response = parent_object.call_method(target_method, payload)
            print(f"[PyOpcUaClient] Method completed. Server return token code: {response}", flush=True)
            global_event_queue.append({"event": "opc_call_method", "value": response})
            
        except Exception as err:
            print(f"[PyOpcUaClient][E] Method invocation failed: {err}", flush=True)

    def read(self, nodeid_str):
        global global_event_queue
        try:
            if not self.is_connected:
                return
            
            # Resolve the node target
            read_node = self.client.get_node(nodeid_str)
            
            # Execute the synchronous read service call over the wire
            val = read_node.read_value()
            
            # Serialize and push the data into the event queue for B4J to intercept
            # Format: "ns=1;s=YourNode#Value" matches your subscription protocol setup
			
			# Check for the official ServerStatus Node ID or DataType properties
            if nodeid_str == "ns=0;i=2256" or hasattr(val, 'BuildInfo'):
                status_dict = {
                    "is_complex": True,
                    "StartTime": val.StartTime.isoformat() if val.StartTime else "N/A",
                    "CurrentTime": val.CurrentTime.isoformat() if val.CurrentTime else "N/A",
                    "State": str(val.State).split(".")[-1], # e.g. "Running"
                    "ProductUri": val.BuildInfo.ProductUri,
                    "ManufacturerName": val.BuildInfo.ManufacturerName,
                    "ProductName": val.BuildInfo.ProductName,
                    "SoftwareVersion": val.BuildInfo.SoftwareVersion,
                    "BuildNumber": val.BuildInfo.BuildNumber
                }
                val_str = json.dumps(status_dict)
            else:
                # Normal scalar variables (Floats, Ints, Booleans)
                val_str = str(val)

            combined_payload = f"{nodeid_str}#{val_str}"
            global_event_queue.append({"event": "opc_read_result", "value": combined_payload})
            
            print(f"[PyOpcUaClient] Successfully read node {nodeid_str} | Value: {val}", flush=True)
            
        except Exception as err:
            print(f"[PyOpcUaClient][E] Read error on {nodeid_str}: {err}", flush=True)


def InjectPythonCoreCode():
    return "Ready"
"$
	Py.RunCode("InjectPythonCoreCode", Array(), Code)
End Sub
