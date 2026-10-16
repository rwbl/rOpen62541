B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
#Region Class Info
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client for the Serializator example.
' Date:			See class_globals VERSION.
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	Write 2 serialized objects.
' DependsOn:	PyBridge (http://www.b4x.com/android/forum/threads/pybridge-the-very-basics.165654/)
'				Python package asyncua (https://pypi.org) v2.0.1
'				HMITilesIO 0.70 (https://www.b4x.com/android/forum/threads/HMITilesIO.171863/)
' Hardware:		ESP32-S3-N16R8	(minimal requirement)
' Software:		B4J 10.70
#End Region

' #CustomBuildAction: after packager, %WINDIR%\System32\robocopy.exe, Python temp\build\bin\python /E /XD __pycache__ Doc pip setuptools tests
#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	Private VERSION As String = "rOpen62541 Serializator v20261013"
	' UI
	Private xui As XUI
	Private Root As B4XView

	' HMITilesIO Controls
	Private TileIOConnectSwitch As HMITilesIO
	Private TileIOBrowse As HMITilesIO
	Private TileIOWrite As HMITilesIO

	' PyBridge Instance
	Public Py 						As PyBridge
	Private PyRunning				As Boolean = False
	
	Private Ser As B4RSerializator
	
	' OpcUaClient Instance
	Private OpcUaClient 			As PyOpcUaClient				
	Private IP 						As String = "192.168.1.176"		' Set according ESP32 OPC UA Server
	Private PORT 					As Int = 4840					' Default port

	' NodeIDs
	Private NODEID_BYTESTRING		As String = "ns=1;s=ByteString"	' Triggers event OnDataWrite
	Private NODEID_INT				As String = "ns=1;s=Int"		' Triggers event OnDataWrite

	' Helper
	Private ByteConv As ByteConverter
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

	' B4R Serializator
	Ser.Initialize

	' HMITilesIO mandatory sleep and state settings
	Sleep(50)
	TileIOConnectSwitch.State = False
	TileIOConnectSwitch.FooterFontSize = 12
	TileIOConnectSwitch.Footer = "Starting PyBridge"

	' PyBridge initialize core layer framework (event Py)
	Py.Initialize(Me, "Py")
	Dim opt As PyOptions = Py.CreateOptions("Python/python/python.exe")
	Py.Start(opt)
	' Connect to the PyBridge on port 54067 and start process
	Wait For Py_Connected (Success As Boolean)
	PyRunning = Success
	If Not(PyRunning) Then
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
		Log("[B4XPage_CloseRequest] OPC UA client disconnecting")
		OpcUaClient.Disconnect
		Sleep(500)
		TileIOConnectSwitch.Footer = "Disconnected"
	End If

	Log("[B4XPage_CloseRequest] PyBridge stopping")
	Sleep(200)
	Py.KillProcess
	Sleep(200)

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
	TileIOConnectSwitch.Footer = "Disconnected"
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

	If Not(PyRunning) Then Return

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
				TileIOConnectSwitch.State = False
				TileIOConnectSwitch.Footer = "PyBrige Error"
				Return
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
		' Subscribe to reading nodes here
		' ...			
		' Give the ESP32 network stack a tiny 100ms break to register the table writes
		Sleep(100)
		TileIOConnectSwitch.Footer = "Connected"
	Else
		Log("[OpcUaClient_ConnectionChanged][W] Disconnected")
		TileIOConnectSwitch.Footer = "Disconnected"
	End If
End Sub

' Raised automatically whenever the ESP32 server signals a subscription update!
' [OpcUaClient_DataChanged] Event Received nodeid=[ns=1;s=LedState] value=1
Private Sub OpcUaClient_DataChanged (NodeId As String, Value As String)
	Log($"[OpcUaClient_DataChanged] Event Received nodeid=[${NodeId}] | value=${Value}"$)
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

Private Sub TileIOWrite_Click(State As Boolean, Value As String)
	Dim id As Int = 1958
	Dim v As Int = Rnd(0, 101)

	Dim b() As Byte = Ser.ConvertArrayToBytes(Array(id, v))
	OpcUaClient.WriteByteArray(NODEID_BYTESTRING, b)			
	TileIOWrite.Footer = $"${ByteConv.HexFromBytes(b)} | ${v}"$
	
	OpcUaClient.WriteInt(NODEID_INT, Rnd(0, 1001))				
End Sub

Private Sub TileIOBrowse_Click(State As Boolean, Value As String)
	OpcUaClient.Browse(OpcUaClient.NODEID_FACTORY_FLOOR)
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
