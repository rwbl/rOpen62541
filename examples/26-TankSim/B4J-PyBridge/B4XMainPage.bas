B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
#Region Class Info
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client the Tank Simulator Example.
' Date:			See class_globals VERSION.
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	
' OPC UA Nodes:	See README.md
' DependsOn:	PyBridge (http://www.b4x.com/android/forum/threads/pybridge-the-very-basics.165654/)
'				Python package asyncua (https://pypi.org) v2.0.1
'				HMITilesIO 0.70 (https://www.b4x.com/android/forum/threads/HMITilesIO.171863/)
' Hardware:		ESP32-S3-N16R8	(minimal requirement)
' Software:		B4J 10.70
#End Region

' #CustomBuildAction: after packager, %WINDIR%\System32\robocopy.exe, Python temp\build\bin\python /E /XD __pycache__ Doc pip setuptools tests
#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	Private VERSION As String = "rOpen62541 TankSim v20261016"
	' UI
	Private xui As XUI
	Private Root As B4XView

	' HMITilesIO Controls
	Private TileIOConnectSwitch As HMITilesIO
	Private TileIOTriggerRequest As HMITilesIO
	Private TileIOTank1Level As HMITilesIO
	Private TileIOTank1SetPoint As HMITilesIO
	Private TileIOTank1TrendChart As HMITilesIO
	Private TileIOTank2Level As HMITilesIO
	Private TileIOTank2SetPoint As HMITilesIO
	Private TileIOTank2TrendChart As HMITilesIO
	Private TileIOBrowse As HMITilesIO
	Private TileIOAvailableRAM As HMITilesIO

	' PyBridge Instance
	Public Py 						As PyBridge
	
	' OpcUaClient Instance
	Private OpcUaClient 			As PyOpcUaClient							' Class PyOpcUaClient
	Private IP 						As String = "192.168.1.176"					' Set according ESP32 OPC UA Server
	Private PORT 					As Int = 4840								' Default port

	' NodeIDs
	Private NODEID_AVAILABLERAM			As String = "ns=1;s=System.AvailableRAM"	' Subscribe to changes (see OpcUaClient_ConnectionChanged)
	Private NODEID_COMMAND				As String = "ns=1;s=Command"

	' Flat Namespace 1 Node String Keys
	Private NODEID_AVAILABLERAM		As String = "ns=1;s=System.AvailableRAM"

	' Tank 1 Identifiers
	Private NODEID_T1_LEVEL 	As String = "ns=1;s=Tank1.Level"
	Private NODEID_T1_SETPOINT 	As String = "ns=1;s=Tank1.Setpoint"
	Private NODEID_T1_INFLOW 	As String = "ns=1;s=Tank1.InputFlow"
	Private NODEID_T1_OUTFLOW 	As String = "ns=1;s=Tank1.OutputFlow"
	Private NODEID_T1_TEMP 		As String = "ns=1;s=Tank1.Temperature"
	Private NODEID_T1_KP 		As String = "ns=1;s=Tank1.Kp"
	Private NODEID_T1_KI 		As String = "ns=1;s=Tank1.Ki"
	Private NODEID_T1_KD 		As String = "ns=1;s=Tank1.Kd"
	Private NODEID_T1_ALARM_HH	As String = "ns=1;s=Tank1.Alarm_HH"
	Private NODEID_T1_ALARM_LL	As String = "ns=1;s=Tank1.Alarm_LL"
	Private NODEID_T1_PUMP		As String = "ns=1;s=Tank1.Pump"
    
	' Tank 2 Identifiers
	Private NODEID_T2_LEVEL 	As String = "ns=1;s=Tank2.Level"
	Private NODEID_T2_SETPOINT 	As String = "ns=1;s=Tank2.Setpoint"
	Private NODEID_T2_INFLOW 	As String = "ns=1;s=Tank2.InputFlow"
	Private NODEID_T2_OUTFLOW 	As String = "ns=1;s=Tank2.OutputFlow"
	Private NODEID_T2_TEMP 		As String = "ns=1;s=Tank2.Temperature"
	Private NODEID_T2_KP	 	As String = "ns=1;s=Tank2.Kp"
	Private NODEID_T2_KI 		As String = "ns=1;s=Tank2.Ki"
	Private NODEID_T2_KD 		As String = "ns=1;s=Tank2.Kd"
	Private NODEID_T2_ALARM_HH 	As String = "ns=1;s=Tank2.Alarm_HH"
	Private NODEID_T2_ALARM_LL	As String = "ns=1;s=Tank2.Alarm_LL"

	' TankSim Commands
	Private const CMD_T1_SETPOINT As Byte = 1
	Private const CMD_T1_KP       As Byte = 2
	Private const CMD_T1_KI       As Byte = 3
	Private const CMD_T1_KD       As Byte = 4
    
	Private const CMD_T2_SETPOINT As Byte = 5
	Private const CMD_T2_KP       As Byte = 6
	Private const CMD_T2_KI       As Byte = 7
	Private const CMD_T2_KD       As Byte = 8

	' Locals
	Private AvailableRAMPrev As Long = -1
	
	' Helper
	Private bc As ByteConverter
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

	' HMITilesIO mandatory sleep and state settings
	Sleep(50)
	TileIOConnectSwitch.State = False
	TileIOConnectSwitch.Footer = "Disconnected"
	TileIOConnectSwitch.FooterFontSize = 12
	TileIOTank1Level.SetSegmentColor(TileIOTank1Level.SEGMENT_RED, TileIOTank1Level.SEGMENT_GREEN_COLOR)
	TileIOTank1Level.SetSegmentColor(TileIOTank1Level.SEGMENT_YELLOW, TileIOTank1Level.SEGMENT_YELLOW_COLOR)
	TileIOTank1Level.SetSegmentColor(TileIOTank1Level.SEGMENT_GREEN, TileIOTank1Level.SEGMENT_RED_COLOR)
	TileIOTank1SetPoint.InstanceSpinner.StepSize = 5
	
	TileIOTank2Level.SetSegmentColor(TileIOTank2Level.SEGMENT_RED, TileIOTank2Level.SEGMENT_GREEN_COLOR)
	TileIOTank2Level.SetSegmentColor(TileIOTank2Level.SEGMENT_YELLOW, TileIOTank2Level.SEGMENT_YELLOW_COLOR)
	TileIOTank2Level.SetSegmentColor(TileIOTank2Level.SEGMENT_GREEN, TileIOTank2Level.SEGMENT_RED_COLOR)
	TileIOTank2SetPoint.InstanceSpinner.StepSize = 5

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
		Log("[B4XPage_CloseRequest] Opc disconnect")
		OpcUaClient.Disconnect
		Sleep(200)
		TileIOConnectSwitch.Footer = "Disconnected"
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
				TileIOConnectSwitch.Footer = $"ERROR"$
			End If
		End If

		' Sleep for 100 ms to allow smooth UI rendering and prevent CPU spikes.
		Sleep(100)
	Loop
End Sub

'=============================================================================
' OPCUA CLASS CALLBACK EVENTS RAISED AUTOMATICALLY
'=============================================================================

' Raised automatically when Connect or Disconnect completes execution loops
Private Sub OpcUaClient_ConnectionChanged (Connected As Boolean)
	Log($"[OpcUaClient_ConnectionChanged] connected=${Connected}"$)		'OpcUaClient.Connected
	TileIOConnectSwitch.State = Connected
	
	' If successfully online, activate the subscriptions
	If Connected = True Then
		Log("[OpcUaClient_ConnectionChanged] Connection verified! Activating subscriptions...")
		
		' Subscribe to OPC UA server > Event DataChanged
		OpcUaClient.Subscribe(NODEID_T1_LEVEL)
		'OpcUaClient.Subscribe(NODEID_T1_SETPOINT)
		OpcUaClient.Subscribe(NODEID_T2_LEVEL)
		'OpcUaClient.Subscribe(NODEID_T2_SETPOINT)
		
		OpcUaClient.Subscribe(NODEID_AVAILABLERAM)
		OpcUaClient.Subscribe(OpcUaClient.NODEID_TRIGGER_RESULT)

		Log("[OpcUaClient_ConnectionChanged] Subscribe OK")
		' Error example:
		' [OpcUaClient][E] Subscription error: The node id refers to a node that does not exist in the server address space.(BadNodeIdUnknown)

		' Give the ESP32 network stack a tiny 100ms break to register the table writes
		Sleep(100)
		
		' Initial readings > Event ReadResult
		OpcUaClient.Read(OpcUaClient.NODEID_CURRENTTIME)
		OpcUaClient.Read(NODEID_T1_SETPOINT)
		OpcUaClient.Read(NODEID_T2_SETPOINT)
		
		'
		TileIOConnectSwitch.Footer = "Connected"
	Else
		TileIOConnectSwitch.Footer = "Disconnected"
	End If
End Sub

' OpcUaClient_DataChanged
' Raised automatically whenever the ESP32 server signals a subscription update.
Private Sub OpcUaClient_DataChanged (NodeId As String, Value As String)
	Log($"[OpcUaClient_DataChanged] Event Received nodeid=[${NodeId}] | value=${Value}"$)

	' Select the node and update the HMITilesIO	
	Select NodeId
		Case NODEID_T1_LEVEL
			TileIOTank1Level.Value = Round(Value)
			TileIOTank1Level.Footer = Round(Value)
			TileIOTank1TrendChart.Value = $"${TileIOTank1TrendChart.Value};${Round(Value)}"$
			Return

		Case NODEID_T2_LEVEL
			TileIOTank2Level.Value = Round(Value)
			TileIOTank2Level.Footer = Round(Value)
			TileIOTank2TrendChart.Value = $"${TileIOTank2TrendChart.Value};${Round(Value)}"$
			Return
		
		Case OpcUaClient.NODEID_TRIGGER_RESULT
			' [OpcUaClient_DataChanged] Event Received nodeid=[ns=1;s=TriggerResult] | value=7
			TileIOTriggerRequest.Footer = $"Result=${Value}"$
			Return

		Case NODEID_AVAILABLERAM
			' [OpcUaClient_DataChanged] Event Received nodeid=[ns=1;s=System.AvailableRAM] | value=33964.0
			Dim v As Long = Value.As(Long)
			TileIOAvailableRAM.Value = v
			TileIOAvailableRAM.Footer = $"${TileIOAvailableRAM.Value}B"$
			Return
	End Select
End Sub

' OpcUaClient_DataChanged
' Raised automatically whenever the ESP32 server signals a subscription update.
Private Sub OpcUaClient_ReadResult(NodeId As String, Value As String)
	Log($"[OpcUaClient_ReadResult] Event Received nodeid=[${NodeId}] | value=${Value}"$)

	Select NodeId
		Case NODEID_T1_SETPOINT
			If TileIOTank1SetPoint.State = False Then
				TileIOTank1SetPoint.State = True
				TileIOTank1SetPoint.Value = Round(Value)
			End If
			Return
		Case NODEID_T2_SETPOINT
			If TileIOTank2SetPoint.State = False Then
				TileIOTank2SetPoint.State = True
				TileIOTank2SetPoint.Value = Round(Value)
			End If
			Return
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
		TileIOConnectSwitch.Footer = "Connecting"
		OpcUaClient.Connect(IP, PORT)
	Else
		Log("[TileIOConnectSwitch_Click] Closing connection manually.")
		TileIOConnectSwitch.Footer = "Disconnecting"
		OpcUaClient.Disconnect
	End If
	TileIOConnectSwitch.Footer = GetTime
End Sub

Private Sub TileIOTank1SetPoint_Click(State As Boolean, Value As String)
	Log($"[TileIOTank1SetPoint_Click] value=${Value}"$)
	OpcUaClient.WriteFloat(NODEID_T1_SETPOINT, Value)
End Sub

Private Sub TileIOTank2SetPoint_Click(State As Boolean, Value As String)
	Log($"[TileIOTank1SetPoint_Click] value=${Value}"$)
	OpcUaClient.WriteFloat(NODEID_T2_SETPOINT, Value)
End Sub

'
' Example changing the setpoint of Tank1.
' The OPC UA clients sends 5-byte frame to the OPC UA server.
' Byte 0=Command ID, Bytes 1-4=Float 
' Logs:
' [TileIOTriggerRequest_Click] Set new Tank1 Setpoint=19.0 
' B4J Log: [SetCommand] id=1, value=19.0, hex=0100009841
' B4R Log: [OnTriggerCall] Tank 1 Setpoint updated to -> 19, result=6
Private Sub TileIOTriggerRequest_Click(State As Boolean, Value As String)
	If Not(OpcUaClient.Connected) Then
		Log("[TileIOTriggerRequest_Click][E] Action Aborted: Connect to the OPC UA Server first!")
		Return
	End If
	
	' Send command to the OPC UA server to change Tank1 Setpoint	
	Dim sp As Float = Rnd(0, 1001)
	WriteCommand(CMD_T1_SETPOINT, sp)
	Log($"[TileIOTriggerRequest_Click] Set new Tank1 Setpoint=${sp} "$)
End Sub

Private Sub TileIOBrowse_Click(State As Boolean, Value As String)
	OpcUaClient.Browse(OpcUaClient.NODEID_FACTORY_FLOOR)
	Log($"[AvailableRAM] ${TileIOAvailableRAM.Value}B"$)
End Sub
#End Region

'==============================================================
' TANKSIM CONTROL
'==============================================================

' WriteCommand
' B4J Log: [SetCommand] id=1, value=19.0, hex=0100009841
' B4R Log: [OnTriggerCall] Tank 1 Setpoint updated to -> 19, result=6 <== just a random number
Public Sub WriteCommand(id As Byte, value As Float)
	Dim RawBytes(5) As Byte
	
	Dim b() As Byte = FloatToBytes(value)
	RawBytes(0) = id
	RawBytes(1) = b(0)
	RawBytes(2) = b(1)
	RawBytes(3) = b(2)
	RawBytes(4) = b(3)

	' Send trigger with bytearray as data
	OpcUaClient.WriteByteArray(OpcUaClient.NODEID_TRIGGER, RawBytes)
	OpcUaClient.WriteByteArray(NODEID_COMMAND, RawBytes)
	
	Log($"[WriteCommand] id=${id}, value=${value}, hex=${bc.HexFromBytes(RawBytes)}"$)
End Sub

'==============================================================
' HELPER
'==============================================================

Public Sub GetTime As String
	Return $"${DateTime.Time(DateTime.Now)}"$
End Sub

Public Sub ToLog(msg As String)
	Log($"${GetTime} ${msg}"$)
End Sub

Public Sub FloatToBytes(f As Float) As Byte()
	Dim bc As ByteConverter
	bc.LittleEndian = True 
    
	Dim fa() As Byte = bc.FloatsToBytes(Array As Float(f))
	If fa.Length > 0 Then
		Return fa
	Else
		Return Null
	End If
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
