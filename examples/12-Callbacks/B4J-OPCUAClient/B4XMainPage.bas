B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client for the Trigger example.
' Date:			2026-10-09
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	Set the position of a servo connected to the ESP32 OPC UA Server.
'				The servo acts as a gate with position open or closed.
' Node Structure:
'				Root Folder = BrowseFull("ns=0;i=85")
'				[BrowseResult] nodes found= 2
'				(MyMap) {NodeId=ns=0;i=2253, DisplayName=Server, NodeClass=Object}
'				(MyMap) {NodeId=ns=1;s=Factory_Floor, DisplayName=Factory_Floor, NodeClass=Object}
'				[BrowseResult] nodes found= 3
'				(MyMap) {NodeId=ns=1;s=Trigger, DisplayName=Remote Trigger, NodeClass=Variable}
'				(MyMap) {NodeId=ns=1;s=TriggerResult, DisplayName=Remote Trigger Result, NodeClass=Variable}
'				(MyMap) {NodeId=ns=1;s=ExecuteJob, DisplayName=Remote Method Call, NodeClass=Method}
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
	Private VERSION As String = "rOpen62541 OPC UA Server Trigger v20261009"
	
	' UI Base
	Private xui As XUI
	Private Root As B4XView

	' UI HMITilesIO
	Private TileConnect As HMITilesIO
	Private TileConnected As HMITilesIO
	Private TileTrigger As HMITilesIO
	Private TileBrowse As HMITilesIO

	' OPC UA Server
	Private OPCUAClient 			As OPCUAClient
	' OPC UA server IP tcp address
	Private ENDPOINT 				As String = "opc.tcp://192.168.1.175:4840"
	' Suscribe data sampling
	Private SAMPLING_INTERVAL 		As Int = 500	'ms
	Private IsConnected 			As Boolean

	' OPC UA Nodes
	' All nodes are added to the default folder Factory_Floor (NodeId=ns=1;s=Factory_Floor)
	Private NODEID_TRIGGER			As String = "ns=1;s=Trigger"
	Private NODEID_TRIGGER_RESULT	As String = "ns=1;s=TriggerResult"
	
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

' ConnectToServer
' Connect to the OPC UA server.
' Triggers event Connected or ConnectionError
Public Sub ConnectToServer
	Log($"[ConnectToServer] Trying to connect..."$)
	OPCUAClient.Connect(ENDPOINT)
End Sub

Public Sub SubscribeNodes
	' Subscribe to live changes
	OPCUAClient.Subscribe(NODEID_TRIGGER_RESULT, SAMPLING_INTERVAL)
End Sub

' Read the state of the nodeid gate state
' Triggers Read_Result
Public Sub ReadNodes
	OPCUAClient.Read(NODEID_TRIGGER)
End Sub

' Browse thru all nodes
' Triggers Browse_Result
Public Sub BrowseNodes
	' Root Folder
	OPCUAClient.BrowseFull("ns=0;i=85")
	' 	[BrowseResult] nodes found= 2
	' (MyMap) {NodeId=ns=0;i=2253, DisplayName=Server, NodeClass=Object}
	' (MyMap) {NodeId=ns=1;s=Factory_Floor, DisplayName=Factory_Floor, NodeClass=Object}
	
	' Folder Factory_Floor (subfolder from the root folder)
	OPCUAClient.BrowseFull("ns=1;s=Factory_Floor")
	' [BrowseResult] nodes found= 2
	' (MyMap) {NodeId=ns=1;s=gatestate, DisplayName=Gate State, NodeClass=Variable}
	' (MyMap) {NodeId=ns=1;s=trigger, DisplayName=Remote Action Trigger, NodeClass=Variable}
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
	SubscribeNodes        
	Sleep(50)
'	BrowseNodes
'	ReadNodes
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

' BrowseResult
'	[BrowseResult] nodes found= 2
'	(MyMap) {NodeId=ns=0;i=2253, DisplayName=Server, NodeClass=Object}
'	(MyMap) {NodeId=ns=1;s=Factory_Floor, DisplayName=Factory_Floor, NodeClass=Object}
'	[BrowseResult] nodes found= 2
'	(MyMap) {NodeId=ns=1;s=gatestate, DisplayName=Gate State, NodeClass=Variable}
'	(MyMap) {NodeId=ns=1;s=trigger, DisplayName=Remote Action Trigger, NodeClass=Variable}
'	[OPCUAClient_ReadResult] Node: ns=1;s=gatestate | Value: 1 | Status: StatusCode{name=Good, Value=0x00000000, quality=good}
Sub OPCUAClient_BrowseResult(Nodes As List)
	Log($"[BrowseResult] nodes found= ${Nodes.Size}"$)
	For Each n As Object In Nodes
		Log(n)
	Next
End Sub

' NodeValueChanged
' Event fires when the ESP32 background core updates the value
Sub OPCUAClient_NodeValueChanged (NodeId As String, Value As Object)
	Log($"[OPCUAClient_NodeValueChanged] Node: ${NodeId} | Value: ${Value}"$)
	UpdateHMITiles(NodeId, Value)
End Sub

' WriteResult
' Event fired by the write method.
' NodeId contains ns ans s, like WriteResult: ns=1;s=Trigger 
' Success boolean true or false
Sub OPCUAClient_WriteResult(NodeId As String, Success As Boolean)
	Log($"[OPCUAClient_WriteResult] ${NodeId} | success=${Success}"$)
End Sub

'==============================================================
' HMITILESIO METHODS & EVENTS
'==============================================================

Public Sub UpdateHMITiles(NodeId As String, Value As Object)
	Log($"[UpdateHMITiles] nodeid=${NodeId}, value=${Value}"$)
End Sub

' TileConnect_Click
' Connect to or disconnect from the endpoint > triggers event Connected.
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

' TileTrigger_Click
Private Sub TileTrigger_Click(State As Boolean, Value As String)
	If IsConnected Then

		' Several examples of datatype

		' String
'		OPCUAClient.Write(NODEID_TRIGGER, "Hello")
'		Sleep(10)

		' Int
'		OPCUAClient.Write(NODEID_TRIGGER, 123)
'		Sleep(10)

		' Float
'		OPCUAClient.Write(NODEID_TRIGGER, 123.45)
'		Sleep(10)

		' ByteArray
		Dim bc As ByteConverter
		Dim RawBytes() As Byte = Array As Byte(0x01, 0x02, 0x03, 0x04, 0x05)
		' Convert the binary array to an encoded ISO string layout
		' so B4J treats it as a single standard string object
		Dim StringPayload As String = bc.StringFromBytes(RawBytes, "ISO-8859-1")
		OPCUAClient.Write(NODEID_TRIGGER, StringPayload)
		' B4R Log:
		' [OnTriggerCall] OPC UA Client invoked trigger with argument=, hex=0102030405

		Log($"[TileTrigger]"$)
	End If
'	Dim buffer(5) As Byte
'	Dim floats() As Byte = ByteConv.FloatsToBytes(Array As Float(12,34))
'	buffer(0) = 1
'	ByteConv.ArrayCopy(floats, 0, buffer, 1, 4)
End Sub

' TileBrowse_Click
Private Sub TileBrowse_Click(State As Boolean, Value As String)
	OPCUAClient.BrowseFull("ns=1;s=Factory_Floor")

	BrowseNodes
	Sleep(50)
	ReadNodes
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

' ByteStringToBytes
' Convert milo bytestring to B4R bytearray
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
