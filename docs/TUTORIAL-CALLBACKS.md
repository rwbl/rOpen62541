## TUTORIAL-CALLBACKS

## How to Use Callbacks (Tutorial & Examples)
The library uses a dual-core architecture (FreeRTOS running the OPC UA engine on Core 0, and the B4R code running on Core 1).
Callbacks bridge these cores safely using background event routing.

There are two types of callbacks:

- **Data Write Event Handler** (automatically attached when intercepting a node named "Trigger")
	- Runs when an OPC UA Client performs a network write operation to the OPC UA Server using namespace 1 (ns=1) and string identifier Trigger (s=Trigger).
		- Fires the background execution subroutine registered inside the server's Initialize method.
- **Native Executable Method Call Event Handler** (for RPC execution)
	- Fired automatically when a client invokes an RPC method call.
		- When a client invokes a method created via AddMethodNode, the server pauses the network frame, fires the B4R subroutine, extracts the dynamic ByteString input, and expects to submit a return code back across the socket layer using SetMethodReturnCode.

---

## Data Write Event Handler ("Trigger")

NodeID
```
ns=1;s=Trigger
```
The exact case-sensitive string identifier Trigger is mandatory to hook into the native network write interceptor.

Value Types Handled:
* Variant: Boolean, Int, Double, Float, String, or Byte Array.

### Flow Data Write Handler (OnDataWrite)
Triggers when an external client attempts to update data in an intercepted node.

[ OPC UA Client ] 
       │
       ▼ (Sends standard Write service to "ns=1;s=Trigger")
[ ESP32 Core 0: open62541 Engine ]
       │
       ▼ (Safely locks mutex & pushes data payload across core boundaries)
[ ESP32 Core 1: B4R Runtime Loop ]
       │
       ▼ (Automatically routes execution to the registered callback sub)
[ B4R Method: OnDataWrite(buffer() As Byte) ]
       │
       ▼ (Parses command string and adjusts physical hardware pins)
[ Action: e.g., LedRedPin.DigitalWrite(True) ]


**Notes:**  
This diagram shows the Data Journey when an external client changes a value on our server. 
It starts when the OPC UA Client sends a standard "Write" command to the target Trigger node. 
The server engine on Core 0 captures this request and passes the data payload safely over to the B4R runtime on Core 1. 
The B4R application automatically catches this event inside the OnDataWrite subroutine. 
From here, the raw bytes are converted to a simple string command to trigger real-world actions, like turning a physical LED indicator on or off.

### Example
This example uses a B4J desktop client alongside the OPC UA Client library to control the on/off state of a physical LED connected to the B4R server hardware.

* Target Server Endpoint: opc.tcp://192.168.1.175:4840/

#### OPC UA Client (B4J)

```
Private NODE_TRIGGER As String = "ns=1;s=Trigger"

Dim cmd As String = IIf(State, "ledon", "ledoff")

OpcClient.Write(NODE_TRIGGER, cmd)
```

#### OPC UA Server (B4R)
```
Private OpcServer As rOpen62541
Private PORT As Int = 4840

' Init the OPC UA Server with the designated standard data write event hook
OpcServer.Initialize(WiFi.LocalIp, PORT, "", "", "OnDataWrite")

' OnDataWrite
' Fired automatically when a client executes a Write service on intercepted nodes
Private Sub OnDataWrite(buffer() As Byte)
	Dim cmd As String = bc.StringFromBytes(buffer)
	Log("[Event] Client wrote to value payload=", cmd, " length=", buffer.length)
	
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

## Native Executable Method Call Event Handler ("RPC")

**ObjectID**
```
ns=1;s=Factory_Floor
```
**MethodID**
```
ns=1;s=ExecuteJob
```

------------------------------
### Flow Executable Method Call Handler (OnMethodCall)
Triggers when a remote client invokes a native execution routine block.

[ OPC UA Client ] 
       │
       ▼ (Sends standard Call service targeting "ExecuteJob")
[ ESP32 Core 0: open62541 Engine ]
       │
       ▼ (Pauses the network socket frame to await internal response token)
[ ESP32 Core 1: B4R Runtime Loop ]
       │
       ▼ (Invokes your dynamic RPC subroutine with raw arguments)
[ B4R Method: OnMethodCall(InputParams() As Byte) ]
       │
       ▼ (Evaluates logic criteria: "START_BATCH")
[ OpcServer.SetMethodReturnCode(OpcServer.OpcServer.STATUS_GOOD) ]
       OR
[ OpcServer.SetMethodReturnCode(OpcServer.OpcServer.STATUS_FAILURE) ]
       │
       ▼ (Releases paused socket layer and returns Success code back to client)
[ OPC UA Client receives Success confirmation ]
       OR
[ OPC UA Client receives Failure confirmation ]

**Notes**  
This diagram maps out the Data Journey for remote command executions, also known as RPC calls. 
The client kicks things off by sending a standard "Call" service command directly to our custom ExecuteJob node.
The open62541 network engine on Core 0 accepts the payload and safely pauses the network conversation to wait for our hardware response.
The event goes right to Core 1, launching the OnMethodCall subroutine with the client's input arguments. 
Once the B4R script verifies the instructions (like starting a production batch), it submits a confirmation code back across the core layout. 
The server immediately opens up the network socket line again and delivers a clear success or failure confirmation back to the client.

### Example
This example uses **Node-RED** with the custom **node-red-contrib-opcua** node palette to send a raw `START_BATCH` instruction frame to the server execution node.

* Target Server Endpoint: opc.tcp://192.168.1.175:4840/

#### OPC UA Client (Node-RED)
The Function Node defines and routes the specific metadata properties required by the node-opcua client protocol parser:

```
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
```
Private OpcServer As rOpen62541
Private PORT As Int = 4840

Sub AppStart
    ' ... Network startup code (no trigger event) ...
    OpcServer.Initialize(WiFi.LocalIp, PORT, "", "", "")
    
    ' MANDATORY: Instantiate the Method node in the tree and register the callback pointer!
    OpcServer.AddMethodNode("ExecuteJob", "Execute Production Job", "OnMethodCall")
End Sub

' OnMethodCall
' Fired automatically when a remote client executes a Call service on this method node
Private Sub OnMethodCall(InputParams() As Byte)
	Dim ParamText As String = bc.StringFromBytes(InputParams)
    
	Log("[Event] Client called ExecuteJob with argument:", _
		" string=", ParamText, _
		" hex=", bc.HexFromBytes(InputParams))
    
	' Evaluate local logic parameters
	If ParamText = "START_BATCH" Then
		Log("[Event] Command accepted. Running production batch code...")
		' Send GOOD (Success token confirmation) back across the network socket
		OpcServer.SetMethodReturnCode(OpcServer.STATUS_GOOD) 
	Else
		Log("[Event][E] Command rejected. Invalid parameter format.")
		' Send FAILURE (Failure token confirmation) back across the network socket
		OpcServer.SetMethodReturnCode(OpcServer.STATUS_FAILURE) 
	End If
End Sub
```

---
