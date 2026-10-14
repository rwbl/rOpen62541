# TUTORIAL-CALLBACKS
How to Use Callbacks (Tutorial & Examples)

## Overview

The library uses a dual-core architecture (FreeRTOS running the OPC UA engine on Core 0, and your high-level B4R code running on Core 1). 
Callbacks safely bridge these processor cores using background event routing.
There are three distinct types of callbacks available in the API:
* Universal Data Write Event Handler (OnDataWrite)
	* Triggers on: Any standard variable node change written by an external client.
	* Data Passed: NodeId String, DataType Flag (Enum), and raw value bytes.
	* Best for: Processing setpoints, PID tuning variables, and configuration adjustments dynamically without memory fragmentation.
* Dedicated Action Trigger Event Handler (OnTriggerCall)
	* Triggers on: A network write specifically targeting the mandatory node ID ns=1;s=Trigger.
	* Data Passed: A raw byte buffer containing the payload.
	* Best for: Simple command strings or basic execution flags (e.g., "ledon", "stop").
* Native Executable Method Call Event Handler (OnMethodCall)
	* Triggers on: A client executing an explicit OPC UA RPC Method service call.
	* Data Passed: Input arguments as raw bytes. Can reply using SetMethodReturnCode.
	* Best for: Executing heavy operations (e.g., executing structural production batches, homing a motor, running calibration).

## Execution Routing Paths

### 1. Universal Variable Writes
```
OPC UA Client writes to ANY Variable Node (e.g., Tank1.Setpoint)
        │
        ▼
onWriteUniversal() [C++]
        │
        ├── Detect Data Type (Float, Int, Bool)
        ├── Copy raw bytes into static payload union
        └── scadaDataWriteCalled = true
                    │
                    ▼
              B4R looper()
                    │
                    ▼
             DataWriteSub ➔ Sub OnDataWrite(NodeId, DataType, RawValue)
```

### 2. Dedicated Trigger Writes
```
OPC UA Client writes explicitly to ns=1;s=Trigger
        │
        ▼
scadaTriggerCallback() [C++]
        │
        └── scadaTriggerCalled = true
                    │
                    ▼
              B4R looper()
                    │
                    ▼
             MethodTriggerSub ➔ Sub OnTriggerCall(buffer)
```

### 3. Native Method Calls
```
OPC UA Client invokes an RPC Method
        │
        ▼
opcuaMethodBridge() [C++]
        │
        ├── Copy input arguments to buffer
        ├── scadaMethodCalled = true
        ├── Wait 50ms block for B4R execution
        └── Read globalMethodOutputResult from RAM
                    ▲
                    │
              B4R looper()
                    │
                    ▼
             MethodCallSub ➔ Sub OnMethodCall(InputParams)
                    │
                    ▼
          SetMethodReturnCode(code)
```

### 1. Universal Data Write Event Handler (OnDataWrite)

This event handles all variable modifications across the server ecosystem using a memory-safe, zero-allocation design.

Module Configuration (Top of B4R Script)

```b4r
#Event: OnDataWrite (NodeId As String, DataType As Int, RawValue() As Byte)
```

### OPC UA Server Implementation (B4R)

```b4r
Private OPCUAServer As rOpen62541
Private PORT As Int = 4840

Sub AppStart
    Serial1.Initialize(115200)
    
    ' Pass both the dedicated trigger and the universal write callbacks
    OPCUAServer.Initialize(WiFi.LocalIp, PORT, "", "", "OnTriggerCall", "OnDataWrite")
    
    ' Writable variable nodes
    OPCUAServer.AddFloatNode("ns=1;s=Tank1.Setpoint", "Tank 1 Target Setpoint", 50.0)
    OPCUAServer.AddBooleanNode("ns=1;s=Relay1.State", "Hardware Relay Status", False)
    
    OPCUAServer.Start
End Sub

' Fired automatically when a remote client updates ANY writable variable node value
Sub OPCUAServer_OnDataWrite(NodeId As String, DataType As Int, RawValue() As Byte)
    Dim bc As ByteConverter ' Built-in B4R tool for safe binary parsing
    
    Select DataType
        Case 1 ' Float Type
            Dim F_Value As Float = bc.FloatFromBytes(RawValue)
            Log("[Write] Node: ", NodeId, " | Float Value: ", F_Value)
            
            If NodeId = "ns=1;s=Tank1.Setpoint" Then
                ' Adjust your internal control loop variables directly here
            End If
            
        Case 2 ' Int32 Type
            Dim I_Value As Int = bc.IntFromBytes(RawValue)
            Log("[Write] Node: ", NodeId, " | Int Value: ", I_Value)
            
        Case 3 ' Boolean Type
            Dim B_Value As Boolean = (RawValue(0) = 1)
            Log("[Write] Node: ", NodeId, " | Bool Status: ", B_Value)
    End Select
End Sub
```

### 2. Dedicated Action Trigger Event Handler (OnTriggerCall)

Module Configuration (Top of B4R Script)

```b4r
#Event: OnTriggerCall (buffer() As Byte)
```

Node ID Requirement
```
ns=1;s=Trigger
```
The exact case-sensitive string identifier Trigger is mandatory to hook into this single-node routing system.

OPC UA Server Implementation (B4R)

```b4r
Private OPCUAServer As rOpen62541
Private PORT As Int = 4840
Private NODEID_TRIGGER As String = "ns=1;s=Trigger"

Sub AppStart
    Serial1.Initialize(115200)
    OPCUAServer.Initialize(WiFi.LocalIp, PORT, "", "", "OnTriggerCall", "OnDataWrite")
    
    ' The mandatory trigger node space must be added manually during boot
    OPCUAServer.AddStringNode(NODEID_TRIGGER, "Remote Action Trigger", "0")
    
    OPCUAServer.Start
End Sub

' Fired automatically ONLY when a client writes explicitly to the "Trigger" node
Private Sub OPCUAServer_OnTriggerCall(buffer() As Byte)
    Dim bc As ByteConverter
    Dim cmd As String = bc.StringFromBytes(buffer)
    Log("[Trigger] Client issued command payload: ", cmd)
    
    Select cmd
        Case "ledon"
            LedRedPin.DigitalWrite(True)
        Case "ledoff"
            LedRedPin.DigitalWrite(False)
    End Select
End Sub
```

### 3. Native Executable Method Call Event Handler (OnMethodCall)
Module Configuration (Top of B4R Script)

```b4r
#Event: OnMethodCall (InputParams() As Byte)
```

OPC UA Server Implementation (B4R)

```b4r
Private OPCUAServer As rOpen62541
Private PORT As Int = 4840
Private NODEID_METHOD As String = "ns=1;s=ExecuteJob"

Sub AppStart
    Serial1.Initialize(115200)
    OPCUAServer.Initialize(WiFi.LocalIp, PORT, "", "", "OnTriggerCall", "OnDataWrite")
    
    ' Instantiate the Method node in the object tree and reference its B4R Sub name
    OPCUAServer.AddMethodNode(NODEID_METHOD, "Execute Production Job", "OnMethodCall")
    
    OPCUAServer.Start
End Sub

' Fired automatically when a remote client executes an explicit RPC Call on this node
Private Sub OPCUAServer_OnMethodCall(InputParams() As Byte)
    Dim bc As ByteConverter
    Dim ParamText As String = bc.StringFromBytes(InputParams)
    Log("[Method RPC] Client invoked Method with argument: ", ParamText)
    
    If ParamText = "START_BATCH" Then
        Log("RPC Accepted. Running production batch cycle...")
        ' Reply across the synchronous network socket with a Success Token (100)
        OPCUAServer.SetMethodReturnCode(100) 
    Else
        Log("RPC Rejected. Invalid runtime parameter layout.")
        ' Reply across the synchronous network socket with a Failure Token (400)
        OPCUAServer.SetMethodReturnCode(400) 
    End If
End Sub
```
