B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
#Region Class Info
' Project:      rOpen62541 (OPC UA Server)
' Example:      Listen to simulated sensor data temperature & humidity and display in a dashboard.
' Version:      See class_globals VERSION.
' Author:       Robert W.B. Linn (c) 2026 - MIT License
' Description:  B4XPages client interface utilizing PyBridge to interact 
'               with the remote rOpen62541 ESP32-S3 Server.
' Note:         For detailed installation steps, PyBridge environment configuration, 
'               and full protocol technical notes, please refer to the project 
'               README.md file located in this example's root directory.
#End Region

' #CustomBuildAction: after packager, %WINDIR%\System32\robocopy.exe, Python temp\build\bin\python /E /XD __pycache__ Doc pip setuptools tests
#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	Private VERSION As String = "rOpen62541 EnvSim Example v20261012"
	' UI
	Private xui As XUI
	Private Root As B4XView

	' PyBridge Instance
	Public Py 		As PyBridge
	
	' OpcUaClient Instance
	Private OpcUaClient 		As PyOpcUaClient					' Connect url = opc.tcp://192.168.1.176:4840
	Private IP 					As String = "192.168.1.176"			' Set according ESP32 OPC UA Server
	Private PORT 				As Int = 4840						' Default port
																																	
	' Nodes using namespace 1
	Private NODEID_TEMPERATURE 	As String = "ns=1;s=Temperature"	
	Private NODEID_HUMIDITY 	As String = "ns=1;s=Humidity"
		
	' HMITilesIO View Controls
	Private TileIOConnectSwitch As HMITilesIO
	Private TileIOConnected As HMITilesIO
	Private TileTemperature As HMITilesIO
	Private TileHumidity As HMITilesIO
	Private TileTemperatureGauge As HMITilesIO
	Private TileHumidityGauge As HMITilesIO
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
	' Initialize custom Class object reference (event OpcClient)
	Sleep(50)
	OpcUaClient.Initialize(Me, _
						  "OpcUaClient", _	' Event 
						  Py)				' PyBridge engine
	
	' Start the background extraction loop instantly
	StartBackgroundEventLoop
End Sub

' Clean up allocations cleanly when context minimizes or closes
Private Sub B4XPage_CloseRequest As ResumableSub
	If OpcUaClient.Connected Then
		Log("[B4XPage_CloseRequest] OpcUaClient disconnected")
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
			
			Log($"[StartBackgroundEventLoop] Dispatched Event From Queue name=${EvName} | value=${EvValue}"$)
			' [StartBackgroundEventLoop] Dispatched Event From Queue name=opc_datachange | value=ns=1;s=LedState 1
			
			' Execute 2-parameter signature call
			OpcUaClient.RaiseB4jEvent(EvName, EvValue)
		End If
		
		' Sleep for 100ms to allow smooth UI rendering and prevent CPU spikes
		Sleep(100)
	Loop
End Sub

'=============================================================================
' OPCUA CLASS CALLBACK EVENTS RAISED AUTOMATICALLY
'=============================================================================

' Raised automatically when Connect or Disconnect completes execution loops
Private Sub OpcUaClient_ConnectionChanged (Connected As Boolean)
	Log($"[OpcUaClient_ConnectionChanged] connected=${Connected}"$)
	TileIOConnectSwitch.State = Connected
	
	' If successfully online, activate the subscriptions
	If Connected = True Then
		Log("[OpcUaClient_ConnectionChanged] Connection verified! Activating subscriptions...")
		
		' Subscribe to the read telemetry state node
		OpcUaClient.Subscribe(NODEID_TEMPERATURE)
		Sleep(100)
		OpcUaClient.Subscribe(NODEID_HUMIDITY)
		
		' Give the ESP32 network stack a tiny 100ms break to register the table writes
		Sleep(100)
		TileIOConnected.Value = "Connected"
	Else
		TileIOConnected.Value = "Disconnected"
	End If
End Sub

' Raised automatically whenever the ESP32 server signals a subscription update!
' [OpcClient_DataChanged] Event Received nodeid=[ns=1;s=LedState] value=1
Private Sub OpcUaClient_DataChanged (NodeId As String, Value As String)
	Log($"[OpcUaClient_DataChanged] Event Received nodeid=[${NodeId}] | value=${Value}"$)
	
	' Handles both numeric states and string actions using pure text conditional cases safely
	Select Case NodeId
		Case NODEID_TEMPERATURE
			TileTemperature.Value = Value
			TileTemperatureGauge.Value = Value
		Case NODEID_HUMIDITY
			TileHumidity.Value = Value
			TileHumidityGauge.Value = Value
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
		TileTemperature.Value = 0
		TileTemperatureGauge.Value = 0
		TileHumidity.Value = 0
		TileHumidityGauge.Value = 0
	End If
	TileIOConnected.Footer = GetTime
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

