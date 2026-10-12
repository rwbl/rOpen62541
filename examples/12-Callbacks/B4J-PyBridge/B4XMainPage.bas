B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
#Region Class Info
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client for the Callbacks example.
' Date:			See class_globals VERSION
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	Demonstrates how to use the Write, Trigger and methodcall callbacks.
'				Write - Fired when the value of the nodeid ns=1;s=Tank.Setpoint is updated
'				Trigger - Fired when the special nodeid ns=1;s=Trigger is called with a value
'				MethodCall - Fired when the client invokes methods calls, like "ns=1;s=ExecuteJobA"
'				See below HMITilesIO click events.
' OPC UA Nodes:	
'				[OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=i=2253|Server|Object
'				[OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=Factory_Floor|Factory_Floor|Object
'				[OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=Tank.Setpoint|Tank Setpoint|Variable
'				[OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=Trigger|Remote Trigger|Variable
'				[OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=TriggerResult|Remote Trigger Result|Variable
'				[OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=ExecuteJobA|Remote Method Call A|Object
'				[OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=ExecuteJobB|Remote Method Call B|Object
' Hardware:		ESP32-S3-N16R8	(minimal requirement)
' Software:		B4J 10.70
' DependsOn:	PyBridge (http://www.b4x.com/android/forum/threads/pybridge-the-very-basics.165654/)
'				Python package asyncua (https://pypi.org) v2.0.1
'				HMITilesIO 0.70 (https://www.b4x.com/android/forum/threads/HMITilesIO.171863/)
#End Region

' #CustomBuildAction: after packager, %WINDIR%\System32\robocopy.exe, Python temp\build\bin\python /E /XD __pycache__ Doc pip setuptools tests
#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	Private VERSION As String = "rOpen62541 Trigger Example v20261012"
	' UI
	Private xui As XUI
	Private Root As B4XView

	' HMITilesIO Controls
	Private TileIOConnectSwitch As HMITilesIO
	Private TileIOConnected As HMITilesIO
	Private TileIOTrigger As HMITilesIO
	Private TileIOTriggerResponse As HMITilesIO

	' PyBridge Instance
	Public Py 						As PyBridge
	
	' OpcUaClient Instance
	Private OpcUaClient 			As PyOpcUaClient					' Class OpcUaClient
	Private IP 						As String = "192.168.1.176"			' Set according ESP32 OPC UA Server
	Private PORT 					As Int = 4840						' Default port

	' NodeIDs
	Private NODEID_TANK_SETPOINT	As String = "ns=1;s=Tank.Setpoint"	' Client writes to node direct
	Private NODEID_METHODCALL_A		As String = "ns=1;s=ExecuteJobA"	' Client sends callmethod
	Private NODEID_METHODCALL_B		As String = "ns=1;s=ExecuteJobB"	' Client sends callmethod
	
	' Trigger Commands
	Private TRIGGER_COMMAND 		As String = "START"					' Trigger command which then handled by the OPC UA server
	Private TileWrite As HMITilesIO
	Private TileCallMethod As HMITilesIO
End Sub

Public Sub Initialize
    
End Sub

#Region B4X Pages
Private Sub B4XPage_Created (Root1 As B4XView)
	' UI Core
	Root = Root1
	Root.LoadLayout("MainPage")
	
	' B4XPages
	B4XPages.SetTitle(Me, VERSION)
	B4XPages.GetNativeParent(Me).Resizable = False

	' HMITilesIO 
	' Mandatory sleep and state settings
	Sleep(50)
	TileIOConnectSwitch.State = False
	TileIOConnected.Value = "Disconnected"
	TileIOConnected.ValueFontSize = 12
	
	' PyBridge 
	' Initialize core layer framework (event Py)
	Py.Initialize(Me, "Py")
	Dim opt As PyOptions = Py.CreateOptions("Python/python/python.exe")
	Py.Start(opt)
	' Connect to the PyBridge on port 54067 and start process
	Wait For Py_Connected (Success As Boolean)
	If Success = False Then
		LogError("[B4XPage_Created][E] Failed to start Python process.")
		Return
	Else
		Log("[B4XPage_Created] Python process started.")
	End If

	' OpcUaClient 
	' initialize custom Class object reference (event OpcClient)
	Sleep(50)
	OpcUaClient.Initialize(Me, _
						   "OpcUaClient", _ 	' OPC UA client event
						   Py)					' PyBridge engine
	
	' Start the background extraction loop instantly
	StartBackgroundEventLoop
End Sub

' Clean up allocations cleanly when context minimizes or closes
Private Sub B4XPage_CloseRequest As ResumableSub
	If OpcUaClient.Connected Then
		Log("[B4XPage_CloseRequest] OPC UA client disconnected")
		OpcUaClient.Disconnect
		Sleep(200)
		TileIOConnected.Value = "Disconnected"
	End If
	Log("[B4XPage_CloseRequest] PyBridge stopping")
	Py.KillProcess
	Return True
End Sub

Private Sub B4XPage_Background
	' No action
End Sub
#End Region

'=============================================================================
' PYBRIDGE EVENTS
'=============================================================================

' Event trigger when disconnected from the PyBridge
Private Sub Py_Disconnected
	Log("[Py_Disconnected] Resetting HMITilesIO")
	TileIOConnectSwitch.State = False
End Sub

'Event raised by the python script bridge_instance.raise_event.
'The args can have various notification keys.
Private Sub Py_Notification(Args As Map)
	Log($"[Py_Notification] Received=${Args}"$)
End Sub

'=============================================================================
' ASYNCHRONOUS EVENT MONITORING DISPATCHER (100% WORKING BASELINE)
'=============================================================================

Private Sub StartBackgroundEventLoop
	Log("[StartBackgroundEventLoop] Asynchronous background event loop started.")

	Dim ConnectionCheckCounter As Int = 0

	' Loop forevere
	Do While True
		' Ask the custom class if Python has any queued events waiting for us.
		Wait For (OpcUaClient.PollNextEvent) Complete (EventData As Object)

		' If an event is waiting, parse its dictionary parameters safely.
		If EventData <> Null Then
			Dim EvMap As Map = EventData
			Dim EvName As String = EvMap.Get("event")
			Dim EvValue As Object = EvMap.Get("value")

			' Log($"[StartBackgroundEventLoop] Dispatched Event From Queue name=${EvName} | value=${EvValue}"$)

			OpcUaClient.RaiseB4jEvent(EvName, EvValue)
		End If

		' Check the OPC UA connection every 5 seconds.
		ConnectionCheckCounter = ConnectionCheckCounter + 1

		If ConnectionCheckCounter >= 50 Then
			ConnectionCheckCounter = 0
			wait for (OpcUaClient.CheckConnection) complete (result As Boolean)
			If Not(result) Then
				Log($"[StartBackgroundEventLoop][E] Connection lost!"$)
				TileIOConnected.Value = $"ERROR"$
			End If
		End If

		' Sleep for 100 ms to allow smooth UI rendering and prevent CPU spikes.
		Sleep(100)
	Loop
End Sub

' Generically triggers background node tree crawling workflows inside PyBridge.
' Parameter
'	- StartNodeId: Target node string context path, like "ns=1;s=Factory_Floor"
' Result Example
' [PyBridge Browser] Found 5 children under ns=1;s=Factory_Floor
' [OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=Tank.Setpoint|Tank Setpoint|Variable
' [OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=Trigger|Remote Trigger|Variable
' [OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=TriggerResult|Remote Trigger Result|Variable
' [OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=ExecuteJobA|Remote Method Call A|Object
' [OpcUaClient] RaiseB4jEvent name=opc_browse_item | value=ns=1;s=ExecuteJobB|Remote Method Call B|Object
Public Sub BrowseFull (StartNodeId As String)
	Dim CodeExec As String = $"
def CallBrowse(target_start_node):
    if global_worker:
        global_worker.browse_and_sync_nodes(target_start_node)
    return "OK"
"$
	Py.RunCode("CallBrowse", Array(StartNodeId), CodeExec)
End Sub

'=============================================================================
' OPCUA CLASS CALLBACK EVENTS RAISED AUTOMATICALLY
'=============================================================================

' Raised automatically when Connect or Disconnect completes execution loops
Private Sub OpcUaClient_ConnectionChanged (Connected As Boolean)
	Log($"[OpcUaClient_ConnectionChanged] connected=${Connected}"$)		'Opc.Connected
	TileIOConnectSwitch.State = Connected
	
	' If successfully online, activate the subscriptions
	If Connected = True Then
		Log("[OpcUaClient_ConnectionChanged] Connection verified, Subscribing to nodes...")
		OpcUaClient.Subscribe(OpcUaClient.NODEID_TRIGGER_RESULT)
	
		' Give the ESP32 network stack a tiny 100ms break to register the table writes
		Sleep(100)
		TileIOConnected.Value = "Connected"
		
		BrowseFull(OpcUaClient.NODEID_ROOT_OBJECTS)
		BrowseFull(OpcUaClient.NODEID_FACTORY_FLOOR)
		
	Else
		TileIOConnected.Value = "Disconnected"
	End If
End Sub

' Raised automatically whenever the ESP32 server signals a subscription update!
' [OpcUaClient_DataChanged] Event Received nodeid=[ns=1;s=LedState] value=1
Private Sub OpcUaClient_DataChanged (NodeId As String, Value As String)
	Log($"[OpcUaClient_DataChanged] Event Received nodeid=[${NodeId}] | value=${Value}"$)

	' Check sensor values	
	Select NodeId
		Case OpcUaClient.NODEID_TRIGGER_RESULT
			Log($"[OpcUaClient_DataChanged] Trigger response received, value=${Value}"$)
			TileIOTriggerResponse.Footer = $"Value: ${Value}"$
			TileIOTriggerResponse.State = IIf(Value < 5, False, True)
		End Select
End Sub
#End Region

'=============================================================================
' HMITilesIO USER PANEL DASHBOARD INTERACTIONS
'=============================================================================
#Region HMITilesIO
Private Sub TileIOConnectSwitch_Click(State As Boolean, Value As String)
	State = Not(State)
	Log($"[TileIOConnectSwitch_Click] Toggled switch to ${State}"$)
	
	' Freeze switch position in place until background connection status resolves
	TileIOConnectSwitch.State = OpcUaClient.Connected
	
	If State = True Then
		Log("[TileIOConnectSwitch_Click] Initiating manual connection sequence...")
		TileIOConnected.Value = "Connecting"
		OpcUaClient.Connect(IP, PORT)
	Else
		Log("[TileIOConnectSwitch_Click] Closing connection manually.")
		TileIOConnected.Value = "Disconnecting"
		OpcUaClient.Disconnect
	End If
	TileIOConnected.Footer = GetTime
End Sub

' TileIOTrigger_Click
' B4J Log:
' [TileIOTrigger_Click] sendindg command=START 
' [OpcUaClient] RaiseB4jEvent name=opc_datachange | Value=ns=1;s=TriggerResult 4
' [OpcUaClient_DataChanged] Event Received nodeid=[ns=1;s=TriggerResult] | Value=4
' [OpcUaClient_DataChanged] Trigger response received, Value=4
' B4R Log:
' [OnTriggerCall] OPC UA Client invoked trigger with argument=START, hex=5354415254
Private Sub TileIOTrigger_Click(State As Boolean, Value As String)
	If Not(OpcUaClient.Connected) Then
		Log("[TileIOTrigger_Click][E] Action Aborted: Connect to the OPC UA Server first!")
		Return
	End If
	Log($"[TileIOTrigger_Click] sending command=${TRIGGER_COMMAND} "$)
	OpcUaClient.WriteTrigger(OpcUaClient.NODEID_TRIGGER, TRIGGER_COMMAND)
End Sub

' TileWrite_Click
' Update the value of the node ns=1;s=Tank.Setpoint.
' Triggers in B4R the event onDataWrite.
' B4J Log:
' [TileWrite_Click] Value=38 
' [OpcUaClient] Successfully wrote Int32 38 To ns=1;s=Tank.Setpoint
' B4R Log:
' [OnDataWrite] OPC UA Client write to nodeid=ns=1;s=Tank.Setpoint, value=26000000
' [OnDataWrite] New Tank Setpoint=38
Private Sub TileWrite_Click(State As Boolean, Value As String)
	If Not(OpcUaClient.Connected) Then
		Log("[TileWrite_Click][E] Action Aborted: Connect to the OPC UA Server first!")
		Return
	End If
	Dim v As Int = Rnd(0, 101)
	Log($"[TileWrite_Click] value=${v} "$)
	OpcUaClient.WriteInt(NODEID_TANK_SETPOINT, v)
	TileWrite.Footer = v
End Sub

' TileCallMethod_Click
' Invoke the methodcall handled in B4R by events OnMethodCallA and OnMethodCallB.
' B4J Log:
' [OpcUaClient] Invoking RPC Method ns=1;s=ExecuteJobA...
' [OpcUaClient] RPC Execution complete. Server Return token code: 0
' [OpcUaClient] Invoking RPC Method ns=1;s=ExecuteJobB...
' [OpcUaClient] RPC Execution complete. Server Return token code: 0
' B4R Log:
' [OnMethodCallA] OPC UA Client invoked method with argument=START_BATCH, hex=53544152545F4241544348
' [OnMethodCallA] Command accepted. Running production batch code...
' [OnMethodCallB] OPC UA Client invoked method with argument=START_BATCH, hex=53544152545F4241544348
' [OnMethodCallB] Command accepted. Running production batch code...
Private Sub TileCallMethod_Click(State As Boolean, Value As String)
	OpcUaClient.CallMethod(NODEID_METHODCALL_A, "START_BATCH")
	OpcUaClient.CallMethod(NODEID_METHODCALL_B, "START_BATCH")
End Sub
#End Region

'==============================================================
' HELPER
'==============================================================

Public Sub GetTime As String
	Return $"${DateTime.Time(DateTime.Now)}"$
End Sub

Public Sub ToLog(msg As String)
	Log($"${GetTime} ${msg}"$)
End Sub

' GetNodeIdentifier
' Get s from nodeid, i.e., ns=1;s=Humidity
' ns = namespace, s = string identifier
Public Sub GetNodeIdentifier(msg As String) As String
	Dim result As String = ""
	Dim components() As String = Regex.Split(";", msg)
	If components.length = 2 Then
		result = components(1).Replace("s=", "")
	End If
	Return result
End Sub
