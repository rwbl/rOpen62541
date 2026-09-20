B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=9.85
@EndOfDesignText@
' Project:		rOpen62541 (OPC UA Server)
' Brief:		OPC UA client for reading system nodes id's (ns=0).
' Date:			2026-09-20
' Author:		Robert W.B. Linn (c) 2026 - MIT
' Description:	Several tests:
'				- Read the standard system node ids. Some of the nodes deliver 0 or null: See TUTORIAL_NODEIDS.md
'				- Browse the root folder "ns=1;s=Factory_Floor"
' DependsOn:	SS_OPCUAClient 1.00 (Thanks, see https://www.b4x.com/android/forum/threads/opc-ua-industrial-client-library-connect-to-servers-devices.171977/ )
'				HMITilesIO 0.70 (see https://www.b4x.com/android/forum/threads/hmitilesio.171863/#content )
'				ByteConverter
' Hardware:		ESP32-S3-N16R8
' Software:		B4J 10.70

' Log Example:
'*** mainpage: B4XPage_Created 
'*** mainpage: B4XPage_Appear 
'*** mainpage: B4XPage_Resize [mainpage]
'[milo-shared-thread-pool-0] INFO org.eclipse.milo.opcua.sdk.client.OpcUaClient - Java version: 19.0.2
'[milo-shared-thread-pool-0] INFO org.eclipse.milo.opcua.sdk.client.OpcUaClient - Eclipse Milo OPC UA Stack version: dev
'[milo-shared-thread-pool-0] INFO org.eclipse.milo.opcua.sdk.client.OpcUaClient - Eclipse Milo OPC UA Client SDK version: dev
'[milo-nonce-util-secure-random] INFO org.eclipse.milo.opcua.stack.core.util.NonceUtil - SecureRandom seeded in 0ms.
'[OpcClient_Connected] Connected to ESP32 OPC UA Server!
'[OpcClient_ReadResult] nodeid=ns=0;i=2258
'[OpcClient_ReadResult] value=DateTime{utcTime=134341116631754180, javaDate=Thu Sep 17 11:41:03 CEST 2026}
'[OpcClient_ReadResult] status=StatusCode{name=Good, value=0x00000000, quality=good}
'--- ESP32 Server Clock Sync ---
'Verified Server Time: Thu Sep 17 11:41:03 CEST 2026
'[OpcClient_ReadResult] nodeid=ns=0;i=2259
'[OpcClient_ReadResult] value=0
'[OpcClient_ReadResult] status=StatusCode{name=Good, value=0x00000000, quality=good}
'[OpcClient_ReadResult] nodeid=ns=0;i=2262
'[OpcClient_ReadResult] value=http://open62541.org
'[OpcClient_ReadResult] status=StatusCode{name=Good, value=0x00000000, quality=good}
'[OpcClient_ReadResult] nodeid=ns=0;i=2295
'[OpcClient_ReadResult] value=null
'[OpcClient_ReadResult] status=StatusCode{name=Bad_AttributeIdInvalid, value=0x80350000, quality=bad}
'[OpcClient_ReadResult] nodeid=ns=0;i=2261
'[OpcClient_ReadResult] value=open62541 OPC UA Server
'[OpcClient_ReadResult] status=StatusCode{name=Good, value=0x00000000, quality=good}
'[OpcClient_ReadResult] nodeid=ns=0;i=2256
'[OpcClient_ReadResult] value=ExtensionObject{encoded=ByteString{bytes=[64, -62, 81, -42, -34, -79, -99, 1, -104, -39, 92, -70, -120, 70, -35, 1, 0, 0, 0, 0, 20, 0, 0, 0, 104, 116, 116, 112, 58, 47, 47, 111, 112, 101, 110, 54, 50, 53, 52, 49, 46, 111, 114, 103, 9, 0, 0, 0, 111, 112, 101, 110, 54, 50, 53, 52, 49, 23, 0, 0, 0, 111, 112, 101, 110, 54, 50, 53, 52, 49, 32, 79, 80, 67, 32, 85, 65, 32, 83, 101, 114, 118, 101, 114, 28, 0, 0, 0, 49, 46, 50, 46, 48, 45, 114, 99, 49, 45, 50, 48, 45, 103, 55, 56, 97, 54, 55, 50, 49, 98, 45, 100, 105, 114, 116, 121, 20, 0, 0, 0, 83, 101, 112, 32, 49, 55, 32, 50, 48, 50, 54, 32, 49, 48, 58, 51, 56, 58, 50, 51, 106, 77, 81, -42, -34, -79, -99, 1, 0, 0, 0, 0, 0]}, encodingId=NodeId{ns=0, id=864}}
'[OpcClient_ReadResult] status=StatusCode{name=Good, value=0x00000000, quality=good}
'Successfully extracted byte array! Length = 153
'--- ESP32 Server Status Summary (Fixed-Parsed) ---
'Open62541 Build Version: 1.2.0-rc1-20-g78a6721b-dirty
'Compile Baseline Date: Sep 17 2026 10:38:23
'[B4XPage_CloseRequest] Forcefully closing OPC UA Client socket channels.
'[OpcClient_Disconnected]

#Region Shared Files
#CustomBuildAction: folders ready, %WINDIR%\System32\Robocopy.exe,"..\..\Shared Files" "..\Files"
'Ctrl + click to sync files: ide://run?file=%WINDIR%\System32\Robocopy.exe&args=..\..\Shared+Files&args=..\Files&FilesSync=True
#End Region

#Macro: Title, Export B4XPages, ide://run?File=%B4X%\Zipper.jar&Args=%PROJECT_NAME%.zip

Sub Class_Globals
	' Info
	Private VERSION As String = "rOpen62541 OPC UA Server NodeIDs v20260920"
	
	' UI Base
	Private xui As XUI
	Private Root As B4XView

	' UI HMITilesIO
	Private TileConnect As HMITilesIO
	Private TileConnected As HMITilesIO
	Private TileNodeIDs As HMITilesIO
	Private TileBrowse As HMITilesIO

	' Communication OPC UA Server
	Private ENDPOINT 					As String = "opc.tcp://192.168.1.175:4840"
	Private OpcClient 					As OPCUAClient 
	Private IsConnected 				As Boolean

	' NodeIDs
	' These are variable nodes without children and not an folder or object node.
	' ns=0 is a static Namespace 0 system variable.
	Private NODEID_SERVERSTATUS 		As String = "ns=0;i=2256"	' | ServerStatus | Parent structure containing all runtime states. |
	Private NODEID_CURRENTTIME 			As String = "ns=0;i=2258"	' | CurrentTime | The exact server clock timestamp NODE. |
	Private NODEID_STARTTIME 			As String = "ns=0;i=2259"	' | StartTime | Shows exactly when the ESP32 server booted up. |
	Private NODEID_SECONDSTILLSHUTDOWN	As String = "ns=0;i=2262"	' | SecondsTillShutdown | Used To warn clients before a server restarts. |
	Private NODEID_STATE 				As String = "ns=0;i=2295"	' | State | Returns an integer indicating server health (0=Running, 1=Failed). |
	Private NODEID_BUILDINFO 			As String = "ns=0;i=2261"	' | BuildInfo | Sub-folder holding the firmware manufacturer and engine version details. |

	' Custom root folder string which has children > this node can be browsed
	Private NODEID_ROOTFOLDER 			As String = "ns=1;s=Factory_Floor"	' 
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
	TileNodeIDs.Items = Array As String("ServerStatus", "CurrentTime", "StartTime", "SecondsTillShutdown", "State", "BuildInfo")
	TileNodeIDs.Value = TileNodeIDs.Items.Get(0)
	TileNodeIDs.ValueFontSize = 12
	TileNodeIDs.Footer = "Select NodeID"
		
	' Init the Opc client with events
	OpcClient.Initialize("OpcClient")
End Sub

' B4XPage_CloseRequest
' Mandatory to disconnect from the opc server
Private Sub B4XPage_CloseRequest As ResumableSub
	Log("[B4XPage_CloseRequest] Forcefully closing OPC UA Client socket channels.")
	Try
		' If the client object is active, shut it down to clear the PC's socket cache
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
	OpcClient.Connect(ENDPOINT)
End Sub

Public Sub SubscribeNodes
	' Subscribe to live changes
	' ns is namespace index 1; s is the string identifier
	' These are defined in the B4R program
	' OpcClient.Subscribe("ns=1;s=RawTelemetry", SAMPLING_INTERVAL)
End Sub

Public Sub ReadNodes
	' 
End Sub

Public Sub UpdateHMITiles(NodeId As String, Value As Object)
	'
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

' NodeValueChanged
' Event fires when the ESP32 background core updates the value
Sub OpcClient_NodeValueChanged (NodeId As String, Value As Object)
	Log($"[OpcClient_NodeValueChanged] Node: ${NodeId} | Value: ${Value}"$)
	UpdateHMITiles(NodeId, Value)
End Sub

' WriteResult
' Event fired write method
' NodeId contains ns ans s, like WriteResult: ns=1;s=Trigger 
' Success boolean true or false
Sub OpcClient_WriteResult(NodeId As String, Success As Boolean)
	Log($"[OpcClient_WriteResult] ${NodeId} | success=${Success}"$)
End Sub

' ReadResult
' Trap async Read results!
Sub OpcClient_ReadResult (NodeId As String, Value As Object, Status As String)
	Log($"[OpcClient_ReadResult] nodeid=${NodeId}"$)
	Log($"[OpcClient_ReadResult] value=${Value}"$)
	Log($"[OpcClient_ReadResult] status=${Status}"$)
	
	Select NodeId
		Case NODEID_SERVERSTATUS
			' [OpcClient_ReadResult] value=ExtensionObject{encoded=ByteString{bytes=[64, -62, 81, -42, -34, -79, -99, 1, -102, -97, 116, -102, -121, 70, -35, 1, 0, 0, 0, 0, 20, 0, 0, 0, 104, 116, 116, 112, 58, 47, 47, 111, 112, 101, 110, 54, 50, 53, 52, 49, 46, 111, 114, 103, 9, 0, 0, 0, 111, 112, 101, 110, 54, 50, 53, 52, 49, 23, 0, 0, 0, 111, 112, 101, 110, 54, 50, 53, 52, 49, 32, 79, 80, 67, 32, 85, 65, 32, 83, 101, 114, 118, 101, 114, 28, 0, 0, 0, 49, 46, 50, 46, 48, 45, 114, 99, 49, 45, 50, 48, 45, 103, 55, 56, 97, 54, 55, 50, 49, 98, 45, 100, 105, 114, 116, 121, 20, 0, 0, 0, 83, 101, 112, 32, 49, 55, 32, 50, 48, 50, 54, 32, 49, 48, 58, 51, 56, 58, 50, 51, 106, 77, 81, -42, -34, -79, -99, 1, 0, 0, 0, 0, 0]}, encodingId=NodeId{ns=0, id=864}}
			Dim ExtensionObj As JavaObject = Value
			Dim Body As JavaObject = ExtensionObj.RunMethod("getBody", Null)
        
			If Body.IsInitialized Then
				Dim RawBytes() As Byte = Body.RunMethod("bytes", Null)
				Log("Successfully extracted byte array! Length = " & RawBytes.Length)
            
				Dim BC As ByteConverter
            
				' The Build Tag ("1.2.0-rc1-20-g78a6721b-dirty") is exactly 28 bytes long.
				' In the dump, its 4-byte length prefix (34) starts at index 80, meaning the text starts at 84.
				Dim SoftwareVersionBytes(28) As Byte
				BC.ArrayCopy(RawBytes, 88, SoftwareVersionBytes, 0, 28)
				Dim SoftwareVersion As String = BC.StringFromBytes(SoftwareVersionBytes, "ASCII")
            
				' 2. The Compile Date ("Sep 17 2026 10:38:23") is exactly 20 bytes long.
				' In the dump, its 4-byte length prefix (20) starts at index 112, meaning the text starts at 116.
				Dim BuildTimeBytes(20) As Byte
				BC.ArrayCopy(RawBytes, 120, BuildTimeBytes, 0, 20)
				Dim BuildTime As String = BC.StringFromBytes(BuildTimeBytes, "ASCII")
            
				Log("[OpcClient_ReadResult] ESP32 Server Status Summary (Fixed-Parsed):")
				Log("Open62541 Build Version: " & SoftwareVersion.Trim)
				Log("Compile Baseline Date: " & BuildTime.Trim)
			End If

		Case NODEID_CURRENTTIME
			' [OpcClient_ReadResult] value=DateTime{utcTime=134341112136782470, javaDate=Thu Sep 17 11:33:33 CEST 2026}
			' Standard Server CurrentTime Node
			' Milo auto-parses this into a Java DateTime object wrapper.
			Dim DateTimeObj As JavaObject = Value
			Dim JavaDate As Object = DateTimeObj.RunMethod("getJavaDate", Null)
        
			' Print the clean, formatted object string
			Log($"[OpcClient_ReadResult] ESP32 Server Clock Sync > Verified Server Time: ${JavaDate}"$)
			' Verified Server Time: Thu Sep 17 11:35:46 CEST 2026

		Case NODEID_STARTTIME
			' [OpcClient_ReadResult] value=0
		Case NODEID_SECONDSTILLSHUTDOWN
			' [OpcClient_ReadResult] value=http://open62541.org
		Case NODEID_STATE
			' [OpcClient_ReadResult] value=null
		Case NODEID_BUILDINFO
			' [OpcClient_ReadResult] value=ExtensionObject{encoded=ByteString{bytes=[64, -62, 81, -42, -34, -79, -99, 1, -104, -39, 92, -70, -120, 70, -35, 1, 0, 0, 0, 0, 20, 0, 0, 0, 104, 116, 116, 112, 58, 47, 47, 111, 112, 101, 110, 54, 50, 53, 52, 49, 46, 111, 114, 103, 9, 0, 0, 0, 111, 112, 101, 110, 54, 50, 53, 52, 49, 23, 0, 0, 0, 111, 112, 101, 110, 54, 50, 53, 52, 49, 32, 79, 80, 67, 32, 85, 65, 32, 83, 101, 114, 118, 101, 114, 28, 0, 0, 0, 49, 46, 50, 46, 48, 45, 114, 99, 49, 45, 50, 48, 45, 103, 55, 56, 97, 54, 55, 50, 49, 98, 45, 100, 105, 114, 116, 121, 20, 0, 0, 0, 83, 101, 112, 32, 49, 55, 32, 50, 48, 50, 54, 32, 49, 48, 58, 51, 56, 58, 50, 51, 106, 77, 81, -42, -34, -79, -99, 1, 0, 0, 0, 0, 0]}, encodingId=NodeId{ns=0, id=864}}
	End Select
	
End Sub

' BrowseResult
Sub OpcClient_BrowseResult(Nodes As List)
	Log($"[BrowseResult] nodes found= ${Nodes.Size}"$)
	For Each n As Object In Nodes
		Log(n)
	Next
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

Private Sub TileNodeIDs_Click(State As Boolean, Value As String)
	If OpcClient.IsConnected Then
		Select Value
			Case "ServerStatus"
				OpcClient.Read(NODEID_SERVERSTATUS)
			Case "CurrentTime"
				OpcClient.Read(NODEID_CURRENTTIME)
			Case "StartTime"
				OpcClient.Read(NODEID_STARTTIME)
			Case "SecondsTillShutdown"
				OpcClient.Read(NODEID_SECONDSTILLSHUTDOWN)
			Case "State"
				OpcClient.Read(NODEID_STATE)
			Case "BuildInfo"
				OpcClient.Read(NODEID_BUILDINFO)
		End Select
	End If
End Sub

Private Sub TileBrowse_Click(State As Boolean, Value As String)
	If IsConnected Then
		Log($"[TileBrowse] nodeidstring=${NODEID_ROOTFOLDER}"$)
		OpcClient.BrowseFull(NODEID_ROOTFOLDER)
		' Result:
'	[TileBrowse] nodeidstring=ns=1;s=Factory_Floor
'	[BrowseResult] nodes found= 5
'	(MyMap) {NodeId=ns=1;s=Temperature, DisplayName=Room Temperature, NodeClass=Variable}
'	(MyMap) {NodeId=ns=1;s=Humidity, DisplayName=Room Humidity, NodeClass=Variable}
'	(MyMap) {NodeId=ns=1;s=Counter, DisplayName=Total Shift Cycle Count, NodeClass=Variable}
'	(MyMap) {NodeId=ns=1;s=RawTelemetry, DisplayName=Atomic Hex Package, NodeClass=Variable}
'	(MyMap) {NodeId=ns=1;s=Trigger, DisplayName=Remote Action Trigger, NodeClass=Variable}		
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
