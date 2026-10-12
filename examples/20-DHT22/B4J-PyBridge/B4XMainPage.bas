B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
#Region Class Info
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client for the DHT22 example.
' Date:			See class_globals VERSION
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	Subscribe to the OPC UA server nodes Temperature and Humidity.
'				Display the data tiles from the library HMITilesIO.
' OPC UA Nodes:	4 children under ns=1;s=Factory_Floor
'				value=ns=1;s=Sensor.Temperature|Temperature|Variable|Float|
'				value=ns=1;s=Sensor.Humidity|Humdity|Variable|Float|
'				value=ns=1;s=System.AvailableRAM|AvailableRAM|Variable|Float|
'				value=ns=1;s=Trigger|Remote Action Trigger|Variable|String|
'				value=ns=1;s=ExecuteJob|Trigger Production Job Routine|Object|String|
' DependsOn:	PyBridge (http://www.b4x.com/android/forum/threads/pybridge-the-very-basics.165654/)
'				Python package asyncua (https://pypi.org) v2.0.1
'				HMITilesIO 0.70 (https://www.b4x.com/android/forum/threads/HMITilesIO.171863/)
' Hardware:		ESP32-S3-N16R8	(minimal requirement)
' Software:		B4J 10.70
#End Region

' #CustomBuildAction: after packager, %WINDIR%\System32\robocopy.exe, Python temp\build\bin\python /E /XD __pycache__ Doc pip setuptools tests
#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	Private VERSION As String = "rOpen62541 DHT22 v20261012"

	' UI
	Private xui As XUI
	Private Root As B4XView

	' HMITilesIO Controls
	Private TileIOConnectSwitch As HMITilesIO
	Private TileIOConnected As HMITilesIO
	Private TileIOTemperature As HMITilesIO
	Private TileIOHumidity As HMITilesIO
	Private TileIOTemperatureDigital As HMITilesIO
	Private TileIOHumidityDigital As HMITilesIO
	Private TileIOAvailableRAM As HMITilesIO
	Private TileIORequestData As HMITilesIO

	' PyBridge Instance
	Public Py 						As PyBridge
	
	' OpcUaClient Instance
	Private OpcUaClient 			As PyOpcUaClient				' Class PyOpcUaClient
	Private IP 						As String = "192.168.1.175"		' Set according ESP32 OPC UA Server
	Private PORT 					As Int = 4840					' Default port

	' NodeIDs
	Private NODEID_TEMPERATURE		As String = "ns=1;s=Sensor.Temperature"		' Subscribe to changes (see OpcUaClient_ConnectionChanged)
	Private NODEID_HUMIDITY			As String = "ns=1;s=Sensor.Humidity"		' Subscribe to changes (see OpcUaClient_ConnectionChanged)
	Private NODEID_AVAILABLERAM		As String = "ns=1;s=System.AvailableRAM"	' Subscribe to changes (see OpcUaClient_ConnectionChanged)

	Private NODEID_REQUEST_DATA		As String = "ns=1;s=System.RequestData"		' This is a method call do not subscribe
	Private CMD_REQUEST_DATA 		As String = "REQUEST_DATA"					' Method call to request latest sensor data

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
	TileIOConnected.Value = "Disconnected"
	TileIOConnected.ValueFontSize = 12
	TileIOAvailableRAM.InstanceTrendChart.MinValue = 40
	TileIOAvailableRAM.InstanceTrendChart.MaxValue = 60

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
	OpcUaClient.Initialize(Me, _
						   "OpcUaClient", _
						   Py)
	
	' Start the background extraction loop instantly
	StartBackgroundEventLoop
End Sub

' Clean up allocations when context minimizes or closes
Private Sub B4XPage_CloseRequest As ResumableSub
	If OpcUaClient.Connected Then
		Log("[B4XPage_CloseRequest] Opc disconnect")
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
	TileIOTemperature.Value = 0
	TileIOHumidity.value = 0
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
				TileIOConnected.Value = $"ERROR"$
			End If
		End If

		' Sleep for 100 ms to allow smooth UI rendering and prevent CPU spikes.
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
	Log($"[OpcUaClient_ConnectionChanged] connected=${Connected}"$)		'OpcUaClient.Connected
	TileIOConnectSwitch.State = Connected
	
	' If successfully online, activate the subscriptions
	If Connected = True Then
		Log("[OpcUaClient_ConnectionChanged] Connection verified! Activating subscriptions...")
		
		' Subscribe to the read sensor nodes
		Log("[OpcUaClient_ConnectionChanged] Subscribing ...")
		OpcUaClient.Subscribe(NODEID_TEMPERATURE)
		OpcUaClient.Subscribe(NODEID_HUMIDITY)
		OpcUaClient.Subscribe(NODEID_AVAILABLERAM)
		
		' Give the ESP32 network stack a tiny 100ms break to register the table writes
		Sleep(100)
		TileIOConnected.Value = "Connected"
	Else
		TileIOConnected.Value = "Disconnected"
	End If
End Sub

' Raised automatically whenever the ESP32 server signals a subscription update!
' [OpcUaClient_DataChanged] Event Received nodeid=[ns=1;s=LedState] value=1
Private Sub OpcUaClient_DataChanged (NodeId As String, Value As String)
	' Log($"[OpcUaClient_DataChanged] Event Received nodeid=[${NodeId}] | value=${Value}"$)

	' Check sensor values	
	If Not(NodeId.EqualsIgnoreCase(NODEID_REQUEST_DATA)) Then
		If Not(IsNumber(Value)) Then
			Return
		End If
		Dim newvalue As String = NumberFormat(Value,01,1)
	End If
	
	' Handles both numeric states and string actions using pure text conditional cases
	Select Case NodeId
		Case NODEID_TEMPERATURE
			Log($"[OpcUaClient_DataChanged] temperature=${newvalue}"$)
			TileIOTemperature.Value = newvalue
			TileIOTemperature.Footer = $"${newvalue}° - ${GetTime}"$
			TileIOTemperatureDigital.Value = newvalue
		Case NODEID_HUMIDITY
			Log($"[OpcUaClient_DataChanged] humidity=${newvalue}"$)
			TileIOHumidity.Value = newvalue
			TileIOHumidity.Footer = $"${newvalue}%RH - ${GetTime}"$
			TileIOHumidityDigital.Value = newvalue
		Case NODEID_AVAILABLERAM
			Log($"[OpcUaClient_DataChanged] availableram=${newvalue}"$)
			Dim v As Long = newvalue.Replace(",","")
			If v <> TileIOAvailableRAM.InstanceTrendChart.LastValue Then
				TileIOAvailableRAM.Value = v
				TileIOAvailableRAM.Footer = $"${v}B"$
			End If

		Case NODEID_REQUEST_DATA
			Log($"[OpcUaClient_DataChanged] Request Data=${Value}"$)
	End Select
End Sub

Private Sub OpcUaClient_MethodCalled(Value As Object)
	Log($"[OpcUaClient_Methodcalled] value=${Value}"$)
	TileIORequestData.Footer = $"${gettime} - response=${value}"$
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

' B4J Log:
' [TileIORequestData_Click] Request data update from OPC UA server
' [PyOpcUaClient] Invoking Method ns=1;s=System.RequestData...
' [PyOpcUaClient] Method completed. Server Return token code: 0
' B4R Log:
' [OnRequestData] Client invoked the method node with argument: hex=REQUEST_DATA string=524551554553545F44415441
' [OnRequestData] Command accepted.
' [RequestData] t=20.9000, h=51.3000
' [RequestData] Completed
Private Sub TileIORequestData_Click(State As Boolean, Value As String)
	' Pure object check safety interlock protection check
	If Not(OpcUaClient.Connected) Then
		Log("[TileIORequestData_Click][E] Action Aborted: Connect to the OPC UA Server first!")
		Return
	End If
	Log($"[TileIORequestData_Click] Request data update from OPC UA server"$)
	OpcUaClient.CallMethod(NODEID_REQUEST_DATA, CMD_REQUEST_DATA)
	TileIORequestData.State = State
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
