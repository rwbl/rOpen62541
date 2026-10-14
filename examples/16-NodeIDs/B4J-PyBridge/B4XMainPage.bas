B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
#Region Class Info
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client to read system nodeids
' Date:			See class_globals
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	B4X pages project with the PyBridge to set the state of an LED connected to the OPC UA Server.
'				The client writes a boolean to the nodeid for the LED state.
' DependsOn:	PyBridge (http://www.b4x.com/android/forum/threads/pybridge-the-very-basics.165654/)
'				Python package asyncua (https://pypi.org) v2.0.1
'				HMITilesIO 0.70 (https://www.b4x.com/android/forum/threads/hmitilesio.171863/)
' Hardware:		ESP32-S3-N16R8	(minimal requirement)
' Software:		B4J 10.70
#End Region

' #CustomBuildAction: after packager, %WINDIR%\System32\robocopy.exe, Python temp\build\bin\python /E /XD __pycache__ Doc pip setuptools tests
#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	Private VERSION As String = "rOpc62541 NodeIDs Example v20261014"

	' UI
	Private xui As XUI
	Private Root As B4XView

	' HMITilesIO View Controls
	Private TileIOConnectSwitch As HMITilesIO
	Private TileIOConnected As HMITilesIO
	Private TileNodeIDs As HMITilesIO
	Private TextAreaNodeIDValue As B4XView

	' PyBridge Instance
	Public Py 		As PyBridge
	
	' OpcUaClient Instance
	Private OpcUaClient 				As PyOpcUaClient				' Connect url
	Private IP 							As String = "192.168.1.176"		' Set according ESP32 OPC UA Server
	Private PORT 						As Int = 4840					' Default port

	' NodeIDs
End Sub

Public Sub Initialize
    
End Sub

#Region B4X Pages
Private Sub B4XPage_Created (Root1 As B4XView)
	' UI Core
	Root = Root1
	Root.LoadLayout("MainPage")
	B4XPages.SetTitle(Me, VERSION)
	B4XPages.GetNativeParent(Me).Resizable = False

	' HMITilesIO mandatory sleep and state settings
	Sleep(50)
	TileIOConnectSwitch.State = False
	TileIOConnected.Value = "Disconnected"
	TileIOConnected.ValueFontSize = 12
	TileNodeIDs.Items = Array As String("ServerStatus", "CurrentTime", "StartTime", "SecondsTillShutdown", "State", "BuildInfo")
	TileNodeIDs.Value = TileNodeIDs.Items.Get(0)
	TileNodeIDs.ValueFontSize = 12
	TileNodeIDs.Footer = "Select NodeID"

	' PyBridge initialize core layer framework (event Py)
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

	' OpcUaClient initialize custom Class object reference (event OpcClient)
	Sleep(50)
	OpcUaClient.Initialize(Me, "OpcUaClient", Py)
	
	' Start the background extraction loop instantly
	StartBackgroundEventLoop
End Sub

' Clean up allocations cleanly when context minimizes or closes
Private Sub B4XPage_CloseRequest As ResumableSub
	If OpcUaClient.Connected Then
		Log("[B4XPage_CloseRequest] OPC UA Server disconnected")
		OpcUaClient.Disconnect
		Sleep(200)
		TileIOConnected.Value = "Disconnected"
	End If
	Log("[B4XPage_CloseRequest] PyBridge stopping")
	Py.KillProcess
	TileIOConnectSwitch.State = False
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
	Log("[Py_Disconnected] PyBridge dropped.")
	TileIOConnectSwitch.State = False
End Sub

'Event raised by the python script bridge_instance.raise_event.
'The args can have various notification keys.
Private Sub Py_Notification(Args As Map)
	Log($"[Py_Notification] Received ${Args}"$)
End Sub

'=============================================================================
' ASYNCHRONOUS EVENT MONITORING DISPATCHER (100% WORKING BASELINE)
'=============================================================================

Private Sub StartBackgroundEventLoop
	Log("[StartBackgroundEventLoop] Asynchronous background event loop started.")
	
	Do While True
		' Ask the custom class if Python has any queued events waiting for us
		Wait For (OpcUaClient.PollNextEvent) Complete (EventData As Object)
		
		' If an event is waiting, parse its dictionary parameters safely
		If EventData <> Null Then
			Dim EvMap As Map = EventData
			Dim EvName As String = EvMap.Get("event")
			Dim EvValue As Object = EvMap.Get("value")
			
			' Log($"[StartBackgroundEventLoop] Dispatched Event From Queue name=${EvName} | value=${EvValue}"$)
			' [StartBackgroundEventLoop] Dispatched Event From Queue name=opc_datachange | value=ns=1;s=LedState 1
			
			' Execute 2-parameter signature call
			OpcUaClient.RaiseB4jEvent(EvName, EvValue)
		End If
		
		' Sleep for 100ms to allow smooth UI rendering and prevent CPU spikes
		Sleep(100)
	Loop
End Sub

' Generically triggers background node tree crawling workflows inside PyBridge.
' Parameters -> StartNodeId: Target node string context path, like "ns=1;s=Factory_Floor"
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
	' If successfully online, activate the subscriptions
	If Connected Then
		Log("[OpcUaClient_ConnectionChanged] Connection verified! Activating subscriptions...")
		' Give the ESP32 network stack a tiny 100ms break to register the table writes
		TileIOConnected.Value = "Connected"
		TileIOConnectSwitch.State = True
	Else
		TileIOConnected.Value = "Disconnected"
		TileIOConnectSwitch.State = False
	End If
End Sub

' Raised automatically whenever the ESP32 server signals a subscription update!
' [PyOpcUaClient] RaiseB4jEvent name=opc_datachange | value=ns=1;s=System.LedState True
' [OpcUaClient_DataChanged] Event Received nodeid=[ns=1;s=System.LedState] | value=True
Private Sub OpcUaClient_ReadResult (NodeId As String, Value As String)
	Log($"[OpcUaClient_ReadResult] nodeid=[${NodeId}], value=${Value}"$)
	TextAreaNodeIDValue.Text = $"No result"$
	
	Select NodeId
		Case OpcUaClient.NODEID_SERVERSTATUS
			TextAreaNodeIDValue.Text = $"NodeID:${CRLF}${NodeId}${CRLF}Value:${CRLF}${Value}"$
			' [PyOpcUaClient] RaiseB4jEvent name=opc_read_result, value=ns=0;i=2256 {"is_complex": true, "StartTime": "1970-01-01T00:00:04.207326+00:00", "CurrentTime": "2026-10-14T08:09:35.605942+00:00", "State": "0", "ProductUri": "http://open62541.org", "ManufacturerName": "open62541", "ProductName": "open62541 OPC UA Server", "SoftwareVersion": "1.2.0-rc1-20-g78a6721b-dirty", "BuildNumber": "Oct 13 2026 10:30:35"}

		Case OpcUaClient.NODEID_CURRENTTIME
			TextAreaNodeIDValue.Text = $"NodeID:${CRLF}${NodeId}${CRLF}Value:${CRLF}${Value}"$
			' [OpcUaClient_ReadResult] nodeid=[ns=0;i=2258], value=2026-10-12 15:32:10.191665+00:00
			
		Case OpcUaClient.NODEID_STARTTIME
			TextAreaNodeIDValue.Text = $"NodeID:${CRLF}${NodeId}${CRLF}Value:${CRLF}${Value}"$
			' [OpcClient_ReadResult] value=0

		Case OpcUaClient.NODEID_SECONDSTILLSHUTDOWN
			TextAreaNodeIDValue.Text = $"NodeID:${CRLF}${NodeId}${CRLF}Value:${CRLF}${Value}"$
			' [OpcUaClient_ReadResult] nodeid=[ns=0;i=2262], value=http://open62541.org

		Case OpcUaClient.NODEID_STATE
			TextAreaNodeIDValue.Text = $"NodeID:${CRLF}${NodeId}${CRLF}Value:${CRLF}${Value}"$
			' [PyOpcUaClient][E] Read error on ns=0;i=2295: The attribute is not supported for the specified Node.(BadAttributeIdInvalid)

		Case OpcUaClient.NODEID_BUILDINFO
			TextAreaNodeIDValue.Text = $"NodeID:${CRLF}${NodeId}${CRLF}Value:${CRLF}${Value}"$
			' [OpcUaClient_ReadResult] nodeid=[ns=0;i=2261], value=open62541 OPC UA Server
	End Select
End Sub
#End Region

'=============================================================================
' HMITILESIO USER PANEL DASHBOARD INTERACTIONS
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

Private Sub TileNodeIDs_Click(State As Boolean, Value As String)
	Select Value
		Case "ServerStatus"
			OpcUaClient.Read(OpcUaClient.NODEID_SERVERSTATUS)
		Case "CurrentTime"
			OpcUaClient.Read(OpcUaClient.NODEID_CURRENTTIME)
		Case "StartTime"
			OpcUaClient.Read(OpcUaClient.NODEID_STARTTIME)
		Case "SecondsTillShutdown"
			OpcUaClient.Read(OpcUaClient.NODEID_SECONDSTILLSHUTDOWN)
		Case "State"
			OpcUaClient.Read(OpcUaClient.NODEID_STATE)
		Case "BuildInfo"
			OpcUaClient.Read(OpcUaClient.NODEID_BUILDINFO)
	End Select
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
