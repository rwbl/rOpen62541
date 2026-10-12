# rOpen62541 Three-Callback Architecture Overview

The library categorizes all incoming client interactions into three distinct, highly optimized callback vectors to handle variables, quick triggers, and complex RPC methods cleanly.

## 1. Dedicated Action Trigger (OnTriggerCall)

* Purpose: High-speed, single-purpose command routing.
* How it works: Writing to the special ns=1;s=Trigger node automatically strips out overhead and isolates the incoming command text payload.
* Execution Flow:
```text
B4J Client ➔ OpcUaClient.WriteTrigger("ns=1;s=Trigger", "START")
   │
   ▼ [Network Packet: Raw UTF-8 String]
   │
B4R Server ➔ Triggers: [OnTriggerCall]
              Logs: "OPC UA Client invoked trigger with argument=START"
```
## 2. Universal Data Writes (OnDataWrite)

* Purpose: Standard variable updates and parameter tuning (e.g., setpoints, scales).
* How it works: Network modifications to regular variables fire a type-explicit handler. Local micro-controller hardware updates (like sensor streams or RAM checks) are safely filtered out by the internal isOnDataWrite execution flag.
* Execution Flow:
```text
B4J Client ➔ OpcUaClient.WriteInt("ns=1;s=Tank.Setpoint", 38)
   │
   ▼ [Network Packet: Explicit Little-Endian Int32]
   │
B4R Server ➔ Triggers: [OnDataWrite]
              Logs: "New Tank Setpoint=38"
```

## 3. Native Executable Method Calls (OnMethodCall)

* Purpose: Synchronous Remote Procedure Calls (RPC) to run isolated sequential jobs.
* How it works: The client targets a specific functional method node underneath a hardcoded parent object layout context (ns=1;s=Factory_Floor). The server processes multiple custom method lines (MethodA, MethodB) concurrently without memory pointer cross-talk.
* Execution Flow:
```text
B4J Client ➔ OpcUaClient.CallMethod("ns=1;s=ExecuteJobA", "START_BATCH")
   │
   ▼ [Network Packet: Binary ByteString Payload Object]
   │
B4R Server ➔ Triggers: [OnMethodCallA] ➔ Runs production logic
              Replies: Synchronous Token confirmation code returned over wire
   │
   ▼ [Network Pipe Confirmation]
   │
B4J Client ➔ Logs: "RPC Execution complete. Server return token code: 0"
```

