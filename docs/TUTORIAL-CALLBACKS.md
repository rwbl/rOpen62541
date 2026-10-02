## TUTORIAL-CALLBACKS

## How to Use Callbacks (Tutorial & Examples)
The library uses a dual-core architecture (FreeRTOS running the OPC UA engine on Core 0, and the B4R code running on Core 1).
Callbacks bridge these cores safely using background event routing.

There are two types of callbacks:

- **Data Write Event Handler** (automatically attached when intercepting nodeid "ns=1;s=Trigger"
	- Runs when an OPC UA Client performs a network write operation to the OPC UA Server using namespace 1 (ns=1) and string identifier Trigger (s=Trigger or s=trigger).
		- Fires the background execution subroutine registered inside the server's Initialize method.
		- Accepted are String, Int16, Int32, Float, Double, ByteString.
		- B4R define Node ID "ns=1;s=Trigger" (this nodeid is mandatory) with Event OnDataWrite.
		
- **Native Executable Method Call Event Handler** (for RPC execution)
	- Fired automatically when a client invokes an RPC method call.
		- When a client invokes a method created via AddMethodNode, the open62541 callback routes the method request to the registered B4R subroutine. 
		- The B4R callback can then provide the method result using SetMethodReturnCode.
		- Method callbacks are processed sequentially. The library is designed for one outstanding method invocation at a time.
		- B4R define Node ID "ns=1;s=ExecuteJob" (this nodeid is an example, any node id can be defined) with Event OnMethodCall.

## Path

```
OPC UA client writes Trigger or trigger
        │
        ▼
scadaTriggerCallback()
        │
        └── scadaTriggerCalled = true
                    │
                    ▼
              B4R looper()
                    │
                    ▼
             MethodTriggerSub
```

```
OPC UA client calls Method
        │
        ▼
opcuaMethodBridge()
        │
        ├── copy input
        ├── scadaMethodCalled = true
        │
        ├── wait 50 ms
        │
        └── read globalMethodOutputResult
                    ▲
                    │
              B4R looper()
                    │
                    ▼
             MethodCallSub()
                    │
                    ▼
          SetMethodReturnCode()
```

---

## Data Write Event Handler

**NodeID**
```
ns=1;s=Trigger
```
The exact case-sensitive string identifier Trigger is mandatory to hook into the native network write interceptor.

**Value Types Handled**
* Variant: Boolean, Int, Double, Float, String, or Byte Array.

## Example
This example uses a B4J desktop client alongside the OPC UA Client library to control the on/off state of a physical LED connected to the B4R server hardware.

* Target Server Endpoint: opc.tcp://192.168.1.175:4840/

## OPC UA Client (B4J)

```
Private NODEID_TRIGGER As String = "ns=1;s=Trigger"
Dim cmd As String = IIf(State, "ledon", "ledoff")
OpcClient.Write(NODE_TRIGGER, cmd)
```

## OPC UA Server (B4R)
```
Private OPCUAServer As rOpen62541
Private PORT As Int = 4840
Private NODEID_TRIGGER As String = "ns=1;s=Trigger"

' Init the OPC UA Server with the designated standard data write event hook
OPCUAServer.Initialize(WiFi.LocalIp, PORT, "", "", "OnDataWrite")

' After initialize
' MANDATORY add trigger received from the client and call event OnDataWrite
OPCUAServer.AddStringNode(NODEID_TRIGGER, "Remote Action Trigger", "0")

' OnDataWrite
' Fired automatically when a client executes a Write service on intercepted node Trigger.
Private Sub OnDataWrite(buffer() As Byte)
	Dim cmd As String = bc.StringFromBytes(buffer)
	Log("[OnDataWrite] Client wrote to value payload=", cmd, " length=", buffer.length)
	
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

## Native Executable Method Call Event Handler
**ObjectID**
```
ns=1;s=Factory_Floor
```
**MethodID**
```
ns=1;s=ExecuteJob
```

## Example
This example uses **Node-RED** with the custom **node-red-contrib-opcua** node palette to send a raw `START_BATCH` instruction frame to the server execution node.

* Target Server Endpoint: opc.tcp://192.168.1.175:4840/

## OPC UA Client (Node-RED)
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

## OPC UA Server (B4R)
```
Private OPCUAServer As rOpen62541
Private PORT As Int = 4840
Private NODEID_METHODCALL As String = "ns=1;s=ExecuteJob"

Sub AppStart
    ' ... Network startup code (no trigger event) ...
    OPCUAServer.Initialize(WiFi.LocalIp, PORT, "", "", "")
    
    ' MANDATORY: Instantiate the Method node in the tree and register the callback pointer!
    OPCUAServer.AddMethodNode(NODEID_METHODCALL, "Execute Production Job", "OnMethodCall")
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
		' Send 100 (Success token confirmation) back across the network socket
		OPCUAServer.SetMethodReturnCode(100) 
	Else
		Log("[Event][E] Command rejected. Invalid parameter format.")
		' Send 400 (Failure token confirmation) back across the network socket
		OPCUAServer.SetMethodReturnCode(400) 
	End If
End Sub
```

---
