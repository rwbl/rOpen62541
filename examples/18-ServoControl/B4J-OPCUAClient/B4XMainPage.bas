B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client for the ServoControl example.
' Date:			2026-10-02
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	Set the position of a servo connected to the ESP32 OPC UA Server.
'				The servo acts as a gate with position open or closed.
'				The nodeid mist use namespace ns=1. Other name spaces are not yet supported by the rOpen62541 library.
'				
' DependsOn:	SS_OPCUAClient 1.00 (Thanks, see https://www.b4x.com/android/forum/threads/opc-ua-industrial-client-library-connect-to-servers-devices.171977/ )
'				HMITilesIO 0.70 (see https://www.b4x.com/android/forum/threads/hmitilesio.171863/#content )
' Hardware:		ESP32-S3-N16R8
' Software:		B4J 10.70

' Log Example:

#Region Shared Files
#CustomBuildAction: folders ready, %WINDIR%\System32\Robocopy.exe,"..\..\Shared Files" "..\Files"
'Ctrl + click to sync files: ide://run?file=%WINDIR%\System32\Robocopy.exe&args=..\..\Shared+Files&args=..\Files&FilesSync=True
#End Region

#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	' Info
	Private VERSION As String = "rOpen62541 OPC UA Server ServoControl v20260920"
	
	' UI Base
	Private xui As XUI
	Private Root As B4XView

	' UI HMITilesIO
	Private TileConnect As HMITilesIO
	Private TileConnected As HMITilesIO
	Private TileServoState As HMITilesIO
	Private TileServoSetState As HMITilesIO
	Private TileServoGauge As HMITilesIO

	Private GAUGE_GATE_OPEN As Int = 90
	Private GAUGE_GATE_CLOSED As Int = 0


	' Communication OPC UA Server
	Private ENDPOINT 			As String = "opc.tcp://192.168.1.175:4840"
	Private OPCUAClient 		As OPCUAClient 
	Private SAMPLING_INTERVAL 	As Int = 500	'ms
	Private IsConnected 		As Boolean
	' Nodes
	Private NODEID_TRIGGER 		As String = "ns=1;s=trigger"
	Private NODEID_GATE_STATE 	As String = "ns=2;s=gatestate"
	' Commands
	Private CMD_GATE_OPEN 		As String = "gateopen"
	Private CMD_GATE_CLOSE 		As String = "gateclose"
	
	' Helper
	Private ByteConv As ByteConverter	'ignore
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

	' Switch with state true means gate is open
	TileServoSetState.State = True
	TileServoSetState.Footer = GetTime
	
	TileServoState.State = TileServoSetState.State
	TileServoState.Footer = GetTime

	' TiltGauge value 0 means servo is open at 90° position
	Sleep(50)
	TileServoGauge.Value = 0
	TileServoGauge.InstanceTiltGauge.SetPositionSegmentColor("#FF0000")
	
	' Init the Opc client with events
	OPCUAClient.Initialize("OPCUAClient")
End Sub

' B4XPage_CloseRequest
' Mandatory to disconnect from the opc server
Private Sub B4XPage_CloseRequest As ResumableSub
	Log("[B4XPage_CloseRequest] Forcefully closing OPC UA Client socket channels.")
	Try
		' If your client object is active, shut it down to clear the PC's socket cache
		If OPCUAClient.IsConnected Then
			OPCUAClient.Disconnect
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
	OPCUAClient.Connect(ENDPOINT)
End Sub

Public Sub SubscribeNodes
	' Subscribe to live changes
	' ns is namespace index 1; s is the string identifier
	' These are defined in the B4R program
	OPCUAClient.Subscribe(NODEID_GATE_STATE, SAMPLING_INTERVAL)
	' OPCUAClient.Subscribe("ns=1;s=RawTelemetry", SAMPLING_INTERVAL)
End Sub

Public Sub ReadNodes
	OPCUAClient.Read(NODEID_GATE_STATE)
End Sub

Public Sub UpdateHMITiles(NodeId As String, Value As Object)
	Dim s As String = GetNodeIdentifier(NodeId)
	Select s 
		Case "ServoState"
			TileServoState.State = IIf(Value.As(Int) == 1, True, False)
			TileServoState.Footer = GetTime
			TileServoSetState.State = TileServoState.State
	End Select
End Sub

'==============================================================
' OPCUA CLIENT EVENTS
'==============================================================

' Connect_NoAuth
' Method to connect without credentials.
Sub Connect_NoAuth
	OPCUAClient.Connect(ENDPOINT)
End Sub

' Connected
' Event triggered by method Connect if client successfully connects to the OPC UA server
Sub OPCUAClient_Connected
	Log("[OPCUAClient_Connected] Connected to ESP32 OPC UA Server!")
    
	IsConnected = True
	ReadNodes	
	SubscribeNodes        
	' Update hmitiles
	TileConnect.State = IsConnected
	TileConnected.Value = "Connected"
End Sub

' Disconnected
' Event triggered by method Disconnect.
Sub OPCUAClient_Disconnected
	IsConnected = False
	TileConnect.State = IsConnected
	TileConnected.Value = "Disconnected"
	Log("[OPCUAClient_Disconnected]")
End Sub

' ConnectionError
' Event triggered by method Connect.
Sub OPCUAClient_ConnectionError(Error As String)
	IsConnected = False
	TileConnect.State = IsConnected
	TileConnected.Value = "Disconnected"
	Log($"[OPCUAClient_ConnectionError][E] ${Error}"$)
End Sub

' SubscriptionValueChanged 
' Event triggered when subscription value has changed.
' [OPCUAClient_SubscriptionValueChanged] Node: ns=1;s=gatestate | Value: 1 | Time: 87972
Sub OPCUAClient_SubscriptionValueChanged (NodeId As String, Value As Object, Timestamp As Long)
	Log($"[OPCUAClient_SubscriptionValueChanged] Node: ${NodeId} | Value: ${Value} (${GetType(Value)}) | Time: ${Timestamp}"$)
	UpdateHMITiles(NodeId, Value)
End Sub

' ReadResult
' Event triggered by method Read.
Sub OPCUAClient_ReadResult (NodeId As String, Value As Object, Status As String)
	Log($"[OPCUAClient_ReadResult] Node: ${NodeId} | Value: ${Value} | Status: ${Status}"$)
	UpdateHMITiles(NodeId, Value)
End Sub

' NodeValueChanged
' Event fires when the ESP32 background core updates the value
Sub OPCUAClient_NodeValueChanged (NodeId As String, Value As Object)
	Log($"[OPCUAClient_NodeValueChanged] Node: ${NodeId} | Value: ${Value}"$)
	UpdateHMITiles(NodeId, Value)
End Sub

' Event fired write method
' NodeId contains ns ans s, like WriteResult: ns=1;s=Trigger 
' Success boolean true or false
Sub OPCUAClient_WriteResult(NodeId As String, Success As Boolean)
	Log($"[OPCUAClient_WriteResult] ${NodeId} | success=${Success}"$)
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
		OPCUAClient.Disconnect
	End If
End Sub

' Set state of the servo to open or close
Private Sub TileServoSetState_Click(State As Boolean, Value As String)
	If IsConnected Then
		State = Not(State)
		Dim cmd As String = IIf(State, CMD_GATE_OPEN, CMD_GATE_CLOSE)

		OPCUAClient.Write(NODEID_TRIGGER, cmd)
		TileServoSetState.State = State
		TileServoState.State = State

		TileServoGauge.Value = IIf(State, GAUGE_GATE_CLOSED, GAUGE_GATE_OPEN)
		Log($"[TileServoSetState] state=${State}"$)
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

' [ByteStringToBytes] org.eclipse.milo.opcua.stack.core.types.builtin.ByteString | ByteString{bytes=[25, 0, 88]}
Public Sub ByteStringToBytes(value As Object) As Byte()
	Log($"[ByteStringToBytes] ${GetType(value)} | ${value}"$)
	
	' Check if type is milo byteString
	If GetType(value) = "org.eclipse.milo.opcua.stack.core.types.builtin.ByteString" Then
		Dim joValue As JavaObject = value
		
		' Directly call bytesOrEmpty on the ByteString object
		Dim RawBytes() As Byte = joValue.RunMethod("bytesOrEmpty", Null)
		
		If RawBytes <> Null Then
			Log("Successfully extracted byte array! Length = " & RawBytes.Length)
			Return RawBytes
		Else
			Return Null
		End If
	Else
		Return Null
	End If
End Sub
