B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
#Region Class Info
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client for the EnvSim example.
' Date:			2026-09-19
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	B4X pages project with the PyBridge to Control the OPC UA Server LED.
'				The OPC UA server receives from this OPC UA client a msg with nodeid "Trigger" and value "ledon" or "ledoff".
'				Industrial control panel Loop:
'				1. CONNECT Switch initializes the network socket channel manually and connects/disconnects from the OPc UA server
'				2. LED SWITCH Tile acts purely as a command transmitter sending write payloads (ledon / ledoff) down to the server's "Trigger" node. It does not cheat by changing itself or the indicator locally.
'				3. LED STATE Indicator is the source of truth. It sits tightly bound To the asynchronous network subscription, only turning green or red when the physical ESP32-S3 hardware confirms its state over the Wi-Fi link.
' DependsOn:	PyBridge (http://www.b4x.com/android/forum/threads/pybridge-the-very-basics.165654/)
'				Python package asyncua (https://pypi.org) v2.0.1
'				HMITilesIO 0.70 (https://www.b4x.com/android/forum/threads/hmitilesio.171863/)
' Hardware:		ESP32-S3-N16R8	(minimal requirement)
' Software:		B4J 10.70
' Python:		The Python package asyncua is required.
'				Install asyncua package:
'				 	Open from the B4J IDE the global Python shell: ide://run?File=%B4J_PYTHON%\..\WinPython+Command+Prompt.exe
'					> The terminal shows folder C:\Prog\B4J\Libraries\Python\Notebooks
'					Run: pip install asyncua
'					Resulting in Log:
'					Successfully installed aiosqlite-0.22.1 anyio-4.15.1 asyncua-2.0.1 cffi-2.1.1 cryptography-50.0.1 idna-3.19 pycparser-3.0 pyopenssl-26.4.0 python-dateutil-2.9.0.post0 pytz-2026.3.post1 six-1.17.0 sortedcontainers-2.4.0 typing-extensions-4.16.0
'				Hints:
'				Create a local Python runtime:   
'					ide://run?File=%WINDIR%\System32\Robocopy.exe&args=%B4X%\libraries\Python&args=Python&args=/E
'				Open local Python shell: 
'					ide://run?File=%PROJECT%\Objects\Python\WinPython+Command+Prompt.exe
'				Open global Python shell - make sure To set the path under Tools - Configure Paths. Do Not update the internal package.
'					ide://run?File=%B4J_PYTHON%\..\WinPython+Command+Prompt.exe
' PyBridge Log:	Server Is listening on PORT: 54067
'				Python path: C:\Prog\B4J\libraries\Python\python\python.exe
'				connected
'				starting PyBridge v1.00
'				watchdog set To 30 seconds
'				Connecting To PORT: 54067
'				[B4XPage_Created] Python process started.
' OPC UA Log:		
'				[StartBackgroundEventLoop] Asynchronous background event Loop started.
'				[TileIOConnectSwitch_Click] Toggled switch To: True
'				[TileIOConnectSwitch_Click] Initiating manual connection sequence...
'				[OpcUaClient] Connecting To OpcClient.tcp://192.168.1.175:4840...
'				[StartBackgroundEventLoop] Dispatched Event From Queue name=opc_connection_changed value=True
'				[OpcClient_ConnectionChanged] connected=True
'				[OpcClient_ConnectionChanged] Connection verified! Activating subscriptions...
'				[OpcClient_ConnectionChanged] Subscribing To ns=1;s=LedState
'				Revised values returned differ from subscription values: CreateSubscriptionResult(SubscriptionId=1, RevisedPublishingInterval=500.0, RevisedLifetimeCount=10000, RevisedMaxKeepAliveCount=100)
'				[OpcUaClient] Subscribed cleanly To ns=1;s=LedState
'				[StartBackgroundEventLoop] Dispatched Event From Queue name=opc_datachange value=ns=1;s=LedState 0
'				[OpcClient_DataChanged] Event Received nodeid=[ns=1;s=LedState] value=0
'				[TileIOLedSwitch_Click] Toggled switch To True using Trigger Node
'				[B4XPage_CloseRequest] Opc disconnect
'				[StartBackgroundEventLoop] Dispatched Event From Queue name=opc_connection_changed value=False
'				[OpcClient_ConnectionChanged] connected=False
'				[B4XPage_CloseRequest] PyBridge stopping
'				[Py_Disconnected] PyBridge dropped.
'				Process completed. ExitCode: 1
' Notes:		What does the message mean?
'				Revised values returned differ from subscription values: CreateSubscriptionResult(SubscriptionId=1, RevisedPublishingInterval=500.0, RevisedLifetimeCount=10000, RevisedMaxKeepAliveCount=100)
'				Explain:
'				Normal confirmation Log from the industrial Opc UA standard > The ESP32-S3 microcontroller successfully negotiated and adjusted the data delivery speed To protect its resource limits.
'				When the Python client initializes, the asyncua library requests a data sampling refresh window of 100 milliseconds (self.client.create_subscription(100, handler)).
'				However, because the open62541 stack on the ESP32-S3 Is running a real-time embedded hardware Loop alongside Wi-Fi And FreeRTOS operations, 
'				the microcontroller overrides that request And sends back a response saying:"I am a resource-constrained microcontroller. The fastest I can safely process and transmit data updates without lagging my main loop is every 500 milliseconds.
'				Fields: 
'				RevisedPublishingInterval=500.0: The server changed the requested 100ms interval To 500ms. Telemetry data will be pushed out twice per second.
'				RevisedLifetimeCount=10000: The number of intervals the server will keep the subscription session open in memory without receiving an acknowledgment from Python before tearing it down.
'				RevisedMaxKeepAliveCount=100: If the data values Do Not change (e.g., the LED stays 0 For a long time), the server will automatically send an empty "ping" packet every 100 intervals (every 50 seconds) To tell Python the link Is still healthy.
#End Region

#CustomBuildAction: after packager, %WINDIR%\System32\robocopy.exe, Python temp\build\bin\python /E /XD __pycache__ Doc pip setuptools tests
#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	Private VERSION As String = "rOpc62541 InOut Example v20260915"
	' UI
	Private xui As XUI
	Private Root As B4XView

	' PyBridge Instance
	Public Py 		As PyBridge
	
	' OpcUaClient Instance
	Private OpcClient 			As OpcUaClient					' Connect url = OpcClient.tcp://192.168.1.175:4840
	Private IP 					As String = "192.168.1.175"		' Set according ESP32 OPC UA Server
	Private PORT 				As Int = 4840					' Default port
	Private NODE_TEMPERATURE 	As String = "ns=1;s=Temperature"
	Private NODE_HUMIDITY 		As String = "ns=1;s=Humidity"
	Private NODE_TRIGGER 		As String = "ns=1;s=Trigger"
		
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

	' HMITilesIO mandatory sleep and state settings
	Sleep(50)
	TileIOConnectSwitch.State = False
	TileIOConnected.Value = "Disconnected"
	TileIOConnected.ValueFontSize = 12
	

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
	OpcClient.Initialize(Me, "OpcClient", Py)
	
	' Start the background extraction loop instantly
	StartBackgroundEventLoop
End Sub

' Clean up allocations cleanly when context minimizes or closes
Private Sub B4XPage_CloseRequest As ResumableSub
	If OpcClient.Connected Then
		Log("[B4XPage_CloseRequest] Opc disconnect")
		OpcClient.Disconnect
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
		Wait For (OpcClient.PollNextEvent) Complete (EventData As Object)
		
		' If an event is waiting, parse its dictionary parameters safely
		If EventData <> Null Then
			Dim EvMap As Map = EventData
			Dim EvName As String = EvMap.Get("event")
			Dim EvValue As Object = EvMap.Get("value")
			
			Log($"[StartBackgroundEventLoop] Dispatched Event From Queue name=${EvName} | value=${EvValue}"$)
			' [StartBackgroundEventLoop] Dispatched Event From Queue name=opc_datachange | value=ns=1;s=LedState 1
			
			' Execute 2-parameter signature call
			OpcClient.RaiseB4jEvent(EvName, EvValue)
		End If
		
		' Sleep for 100ms to allow smooth UI rendering and prevent CPU spikes
		Sleep(100)
	Loop
End Sub

'=============================================================================
' OPCUA CLASS CALLBACK EVENTS RAISED AUTOMATICALLY
'=============================================================================

' Raised automatically when Connect or Disconnect completes execution loops
Private Sub OpcClient_ConnectionChanged (Connected As Boolean)
	Log($"[OpcClient_ConnectionChanged] connected=${Connected}"$)		'OpcClient.Connected
	TileIOConnectSwitch.State = Connected
	
	' If successfully online, activate the subscriptions
	If Connected = True Then
		Log("[OpcClient_ConnectionChanged] Connection verified! Activating subscriptions...")
		
		' Subscribe to the read telemetry state node
		OpcClient.Subscribe(NODE_TEMPERATURE)
		Sleep(100)
		OpcClient.Subscribe(NODE_HUMIDITY)
		
		' Give the ESP32 network stack a tiny 100ms break to register the table writes
		Sleep(100)
		TileIOConnected.Value = "Connected"
	Else
		TileIOConnected.Value = "Disconnected"
	End If
End Sub

' Raised automatically whenever the ESP32 server signals a subscription update!
' [OpcClient_DataChanged] Event Received nodeid=[ns=1;s=LedState] value=1
Private Sub OpcClient_DataChanged (NodeId As String, Value As String)
	Log($"[OpcClient_DataChanged] Event Received nodeid=[${NodeId}] | value=${Value}"$)
	
	' Handles both numeric states and string actions using pure text conditional cases safely
	Select Case NodeId
		Case NODE_TEMPERATURE
			TileTemperature.Value = Value
			TileTemperatureGauge.Value = Value
		Case NODE_HUMIDITY
			TileHumidity.Value = Value
			TileHumidityGauge.Value = Value
		Case NODE_TRIGGER
			Log($"[OpcClient_DataChanged] Trigger Action text=${Value}"$)
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
	TileIOConnectSwitch.State = OpcClient.Connected
	
	If State = True Then
		Log("[TileIOConnectSwitch_Click] Initiating manual connection sequence...")
		TileIOConnected.Value = "Connecting"
		OpcClient.Connect(IP, PORT)
	Else
		Log("[TileIOConnectSwitch_Click] Closing connection manually.")
		TileIOConnected.Value = "Disconnecting"
		OpcClient.Disconnect
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

