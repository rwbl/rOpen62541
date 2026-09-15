Sub Process_Globals
    Private OpcClient As OPCUAClient ' Assuming the library instance variable name
End Sub

Sub App_Start (Args() As String)
    Log("Starting B4J OPC UA Test Client...")
    
    OpcClient.Initialize("OpcClient", "opc.tcp://192.168.1.100:4840") ' Replace with your ESP32 IP
    OpcClient.Connect
End Sub

' Event fires when the client successfully handshakes with your ESP32 open62541 server
Sub OpcClient_Connected (Success As Boolean)
    If Success Then
        Log("✅ Connected to ESP32 OPC UA Server!")
        
        ' 1. Read the Temperature node immediately
        ' Node ID matching your C++ definition: Namespace index 1, String Identifier "Temperature"
        OpcClient.ReadNodeValue("ns=1;s=Temperature")
        
        ' 2. Subscribe to live changes so you see the 5-second BMP280 updates automatically
        OpcClient.SubscribeToNode("ns=1;s=Temperature")
        
        ' 3. Call the B4R Method Node after a brief delay
        Log("Calling B4R Method Routine...")
        ' Matches your C++ definition: ns=1;i=62541 
        ' Parameter 3 represents the object folder containing the method (ns=1;s=Factory_Floor or ObjectsFolder context)
        OpcClient.CallMethod("ns=1;i=62541", "ns=0;i=85", Null) 
    Else
        Log("❌ Connection failed. Check ESP32 power, Wi-Fi, and Port.")
    End If
End Sub

' Event fires when the ESP32 background core updates the value
Sub OpcClient_NodeValueChanged (NodeId As String, Value As Object)
    Log("📥 Live Update - Node: " & NodeId & " | Current Value: " & Value)
End Sub
