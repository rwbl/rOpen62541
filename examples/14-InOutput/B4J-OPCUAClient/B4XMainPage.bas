B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client for the Input/Output example.
' Date:			2026-09-19
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	Control the OPC UA Server LED.
'				The OPC UA server receives from this OPC UA client a msg with nodeid "Trigger" and value "ledon" or "ledoff".
' DependsOn:	SS_OPCUAClient 1.00 (Thanks, see https://www.b4x.com/android/forum/threads/opc-ua-industrial-client-library-connect-to-servers-devices.171977/ )
'				HMITilesIO 0.70 (see https://www.b4x.com/android/forum/threads/hmitilesio.171863/#content )
' Hardware:		ESP32-S3-N16R8
' Software:		B4J 10.70

' Log Example:
'[milo-shared-thread-pool-1] INFO org.eclipse.milo.opcua.sdk.client.OpcUaClient - Java version: 19.0.2
'[milo-shared-thread-pool-1] INFO org.eclipse.milo.opcua.sdk.client.OpcUaClient - Eclipse Milo OPC UA Stack version: dev
'[milo-shared-thread-pool-1] INFO org.eclipse.milo.opcua.sdk.client.OpcUaClient - Eclipse Milo OPC UA Client SDK version: dev
'[milo-nonce-util-secure-random] INFO org.eclipse.milo.opcua.stack.core.util.NonceUtil - SecureRandom seeded in 0ms.
'[OpcClient_Connected] Connected to ESP32 OPC UA Server!
'[OpcClient_ReadResult] Node: ns=1;s=LedState | Value: 0 | Status: StatusCode{name=Good, value=0x00000000, quality=good}
'[OpcClient_SubscriptionValueChanged] Node: ns=1;s=LedState | Value: 0 | Time: 196979
'[TileLedSetState] state=true
'[OpcClient_WriteResult] ns=1;s=Trigger | success=true
'[TileLedSetState] state=false
'[OpcClient_WriteResult] ns=1;s=Trigger | success=true
'[TileLedSetState] state=true
'[OpcClient_WriteResult] ns=1;s=Trigger | success=true
'[TileLedSetState] state=false
'[OpcClient_WriteResult] ns=1;s=Trigger | success=true
'[TileLedSetState] state=true
'[OpcClient_WriteResult] ns=1;s=Trigger | success=true
'[OpcClient_Disconnected]
'*** mainpage: B4XPage_CloseRequest [mainpage]
'[B4XPage_CloseRequest] Forcefully closing OPC UA Client socket channels.
'*** mainpage: B4XPage_Disappear [mainpage]

#Region Shared Files
#CustomBuildAction: folders ready, %WINDIR%\System32\Robocopy.exe,"..\..\Shared Files" "..\Files"
'Ctrl + click to sync files: ide://run?file=%WINDIR%\System32\Robocopy.exe&args=..\..\Shared+Files&args=..\Files&FilesSync=True
#End Region

#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	' Info
	Private VERSION As String = "rOpen62541 OPC UA Server InOutput v20260913"
	
	' UI Base
	Private xui As XUI
	Private Root As B4XView

	' UI HMITilesIO
	Private TileConnect As HMITilesIO
	Private TileConnected As HMITilesIO
	Private TileLedState As HMITilesIO
	Private TileLedSetState As HMITilesIO

	' Communication OPC UA Server
	Private ENDPOINT 			As String = "opc.tcp://192.168.1.175:4840"
	Private OpcClient 			As OPCUAClient 
	Private SAMPLING_INTERVAL 	As Int = 500	'ms
	Private IsConnected 		As Boolean
	Private NODE_LED_STATE 		As String = "ns=1;s=LedState"
	Private NODE_TRIGGER 		As String = "ns=1;s=Trigger"
End Sub

Public Sub Initialize
	B4XPages.GetManager.LogEvents = True
End Sub

'This event will be called once, before the page becomes visible.
Private Sub B4XPage_Created (Root1 As B4XView)
	' UI Base
	Root = Root1
	Root.LoadLayout("MainPage")
	
	' UI B4XPages
	B4XPages.SetTitle(Me, VERSION)
	Root.Color = xui.Color_LightGray
	
	' HMITilesIO
	Sleep(50)	' MANDATORY
	TileConnect.State = False
	TileConnect.Footer = ""
	TileConnected.Value = "Disconnected"
	TileConnected.ValueFontSize = 14
	TileConnected.Footer = ""
	TileLedState.Footer = GetTime
	TileLedSetState.State = False
	TileLedSetState.Footer = GetTime
	
	' Init the Opc client with events
	OpcClient.Initialize("OpcClient")
End Sub

' B4XPage_CloseRequest
' Mandatory to disconnect from the opc server
Private Sub B4XPage_CloseRequest As ResumableSub
	Log("[B4XPage_CloseRequest] Forcefully closing OPC UA Client socket channels.")
	Try
		' If your client object is active, shut it down to clear the PC's socket cache
		If OpcClient.IsConnected Then
			OpcClient.Disconnect
		End If
	Catch
		Log($"[B4XPage_CloseRequest][E] Exception caught during shutdown: ${LastException.Message}"$)
	End Try
	Return True
End Sub

'==============================================================
' OPCUA SERVER HELPER
'==============================================================

Public Sub ConnectToServer
	Log($"[ConnectToServer] Trying to connect..."$)
	OpcClient.Connect(ENDPOINT)
End Sub

Public Sub SubscribeNodes
	' Subscribe to live changes
	' ns is namespace index 1; s is the string identifier
	' These are defined in the B4R program
	OpcClient.Subscribe(NODE_LED_STATE, SAMPLING_INTERVAL)
	' OpcClient.Subscribe("ns=1;s=RawTelemetry", SAMPLING_INTERVAL)
End Sub

Public Sub ReadNodes
	OpcClient.Read(NODE_LED_STATE)
End Sub

Public Sub UpdateHMITiles(NodeId As String, Value As Object)
	Dim s As String = GetNodeIdentifier(NodeId)
	Select s 
		Case "LedState"
			TileLedState.State = IIf(Value.As(Int) == 1, True, False)
			TileLedState.Footer = GetTime
			TileLedSetState.State = TileLedState.State
	End Select
End Sub

'==============================================================
' OPCUA CLIENT EVENTS
'==============================================================

' Connect_NoAuth
' Connect without credentials
Sub Connect_NoAuth
	OpcClient.Connect(ENDPOINT)
End Sub

' Connected
' Triggered if client successfully connects to the OPC UA server
Sub OpcClient_Connected
	Log("[OpcClient_Connected] Connected to ESP32 OPC UA Server!")
    
	IsConnected = True
	ReadNodes	
	SubscribeNodes        
	' Update hmitiles
	TileConnect.State = IsConnected
	TileConnected.Value = "Connected"
End Sub

' Disconnected
Sub OpcClient_Disconnected
	IsConnected = False
	TileConnect.State = IsConnected
	TileConnected.Value = "Disconnected"
	Log("[OpcClient_Disconnected]")
End Sub

' ConnectionError
Sub OpcClient_ConnectionError(Error As String)
	IsConnected = False
	TileConnect.State = IsConnected
	TileConnected.Value = "Disconnected"
	Log($"[OpcClient_ConnectionError][E] ${Error}"$)
End Sub

' SubscriptionValueChanged 
' Triggered when subscription value has changed
Sub OpcClient_SubscriptionValueChanged (NodeId As String, Value As Object, Timestamp As Long)
	Log($"[OpcClient_SubscriptionValueChanged] Node: ${NodeId} | Value: ${Value} | Time: ${Timestamp}"$)
	UpdateHMITiles(NodeId, Value)
End Sub

' ReadResult
' Trap async Read results!
Sub OpcClient_ReadResult (NodeId As String, Value As Object, Status As String)
	Log($"[OpcClient_ReadResult] Node: ${NodeId} | Value: ${Value} | Status: ${Status}"$)
	UpdateHMITiles(NodeId, Value)
End Sub

' NodeValueChanged
' Event fires when the ESP32 background core updates the value
Sub OpcClient_NodeValueChanged (NodeId As String, Value As Object)
	Log($"[OpcClient_NodeValueChanged] Node: ${NodeId} | Value: ${Value}"$)
	UpdateHMITiles(NodeId, Value)
End Sub

' Event fired write method
' NodeId contains ns ans s, like WriteResult: ns=1;s=Trigger 
' Success boolean true or false
Sub OpcClient_WriteResult(NodeId As String, Success As Boolean)
	Log($"[OpcClient_WriteResult] ${NodeId} | success=${Success}"$)
End Sub

'==============================================================
' HMITILESIO Events
'==============================================================

' Connect to the endpoint > triggers event Connected
Private Sub TileConnect_Click(State As Boolean, Value As String)
	State = Not(State)
	If State Then
		TileConnected.Value = "Connecting"
		ConnectToServer
	Else
		TileConnected.Value = "Disconnecting"
		OpcClient.Disconnect
	End If
End Sub

' Set Led state
Private Sub TileLedSetState_Click(State As Boolean, Value As String)
	If IsConnected Then
		State = Not(State)
		Dim cmd As String = IIf(State, "ledon", "ledoff")
		OpcClient.Write(NODE_TRIGGER, cmd)
		TileLedSetState.State = State
		TileLedState.State = State
		Log($"[TileLedSetState] state=${State}"$)
	End If
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
