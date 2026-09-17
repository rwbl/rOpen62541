# TUTORIAL-CALLBACKS

## How to Use Callbacks (Tutorial & Examples)

The library uses a dual-core architecture (FreeRTOS running the OPC UA engine on Core 0, and your B4R code running on Core 1).  
Callbacks bridge these cores safely using background event routing.

There are two types of callbacks you can implement:

- **Node Write Trigger** (automatically attached when writing to a node named "Trigger")
	- Runs when an OPC UA Client performs a network write operation to the OPC UA Server using namespace 1 (`ns=1`) and string identifier `Trigger` (`s=Trigger`).
	- Fires the background execution subroutine registered inside your server's `Initialize` method.

- **Native Executable Method Call** (for RPC execution)
	- Fired automatically when a client invokes an RPC method call.
	- When a client invokes a method created via **AddMethodNode**, the server pauses the network frame, fires your B4R subroutine, extracts the dynamic **ByteString** input, and expects you to submit a return code back across the socket layer using **SetMethodReturnCode**.

---

## Node Write Trigger

**NodeID** 
```text
ns=1;s=Trigger
```
The exact case-sensitive string identifier `Trigger` is mandatory to hook into the native network write interceptor.

**Value Types Handled**  
Variant: Boolean, Int, Double, Float, String, or Byte Array.

### Example 
This example uses a `B4J` desktop client alongside the `OPC UA Client` library to control the on/off state of a physical LED connected to the B4R server hardware.

* **Target Server Endpoint:** `opc.tcp://192.168.1.175:4840/`

#### OPC UA Client (B4J)
```b4x
Private NODE_TRIGGER As String = "ns=1;s=Trigger"

Dim cmd As String = IIf(State, "ledon", "ledoff")

OpcClient.Write(NODE_TRIGGER, cmd)
```

#### OPC UA Server (B4R)
```b4x
Private OpcServer As Open62541
Private PORT As Int = 4840

' Init the OPC UA Server with the designated callback routine to handle the Trigger node
OpcServer.Initialize(WiFi.LocalIp, PORT, "", "", "OpcTriggerCallback")

' OpcTriggerCallback
' Automatically runs when a client updates the "Trigger" variable node across the network
Private Sub OpcTriggerCallback(buffer() As Byte)
	Dim cmd As String = ByteConv.StringFromBytes(buffer)
	Log("[OpcTriggerCallback] Client trigger method cmd=", cmd, " length=", buffer.length)
	
	Select cmd
		Case "ledon"
			LedRedPin.DigitalWrite(True)
			LedRedState = True
		Case "ledoff"
			LedRedPin.DigitalWrite(False)
			LedRedState = False
		' Add additional command behaviors here
	End Select
End Sub
```

---

## Native Executable Method Call

**ObjectID**
```text
ns=1;s=Factory_Floor
```

**MethodID**
```text
ns=1;s=ExecuteJob
```

### Example
This example uses `Node-RED` with the custom `node-red-contrib-opcua` node palette to send a raw `START_BATCH` instruction frame to the server execution node.  

* **Target Server Endpoint:** `opc.tcp://192.168.1.175:4840/`

#### OPC UA Client (Node-RED)
The Function Node defines and routes the specific metadata properties required by the `node-opcua` client protocol parser:
```javascript
// Clear any default payload string to prevent conflicts
msg.payload = ""; 

// Explicitly populate the target metadata fields that Node-RED looks for
msg.objectId = "ns=1;s=Factory_Floor";
msg.methodId = "ns=1;s=ExecuteJob";

// Supply the single required input argument parameter block
msg.inputArguments = [
    {
        dataType: "ByteString",
        value: "START_BATCH"
    }
];

return msg;
```

#### OPC UA Server (B4R)
```b4x
Private OpcServer As Open62541
Private PORT As Int = 4840

Sub AppStart
    ' ... Network startup code ...
    OpcServer.Initialize(WiFi.LocalIp, PORT, "", "", "")
    
    ' MANDATORY: Instantiate the Method node in the tree and register the callback pointer!
    OpcServer.AddMethodNode("ExecuteJob", "Execute Production Job", "OpcMethodCallback")
End Sub

' Fired automatically when a remote client invokes CallMethod on the ExecuteJob node
Private Sub OpcMethodCallback(InputParams() As Byte)
	Dim ParamText As String = ByteConv.StringFromBytes(InputParams)
    
	Log("[OpcMethodCallback] Client invoked method node with argument:", _
		" string=", ParamText, _
		" hex=", ByteConv.HexFromBytes(InputParams))
    
	' Evaluate your local logic parameters safely
	If ParamText = "START_BATCH" Then
		Log("[OpcMethodCallback] Command accepted. Running production batch code...")
		' Send 100 (Success token confirmation) back across the network socket
		OpcServer.SetMethodReturnCode(100) 
	Else
		Log("[OpcMethodCallback][E] Command rejected. Invalid parameter format.")
		' Send 400 (Failure token confirmation) back across the network socket
		OpcServer.SetMethodReturnCode(400) 
	End If
End Sub
```
