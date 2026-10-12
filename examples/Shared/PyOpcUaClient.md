Here is an exhaustive, production-grade learning documentation module tailored precisely for your rOpen62541 Integration Architecture. This document serves as your technical reference and explains the underlying systems, threading models, and data synchronization patterns that made your project a success.
------------------------------
## Technical Reference Documentation: Asynchronous Inter-Process Control Pipeline
This documentation details the cross-runtime integration architecture combining B4J (Java Virtual Machine), Python (asyncua), and an embedded C (open62541) server running on an ESP32-S3 microcontroller.
------------------------------
## 1. Architectural Topology & Runtime Boundary
The core of this system is an asymmetrical, multi-layered environment designed to bridge a rapid application development frontend with a highly asynchronous industrial protocol stack.

+----------------──────────────────────────────┐
│        B4J RUNTIME ENVIRONMENT (JVM)         │
│                                              │
│  ┌──────────────────────┐                    │
│  │   B4XMainPage (UI)   │                    │
│  └──────────┬───────────┘                    │
│             │ Event Handlers & Callbacks     │
│             ▼                                │
│  ┌──────────────────────┐                    │
│  │   OpcUaClient.bas    │                    │
│  └──────────┬───────────┘                    │
+─────────────┼────────────────────────────────+
              │ IPC Socket Boundary (Loopback)
              ▼
+─────────────┼────────────────────────────────+
│       PYTHON RUNTIME PROCESS (CPython)       │
│                                              │
│  ┌──────────┴───────────┐                    │
│  │    PyBridge Engine   │                    │
│  └──────────┬───────────┘                    │
│             │ Thread Dispatching             │
│             ▼                                │
│  ┌──────────────────────┐                    │
│  │  OpcSyncWorker (OOP) │                    │
│  │  [ThreadLoopClient]  │                    │
│  └──────────┬───────────┘                    │
+─────────────┼────────────────────────────────+
              │ Binary Network Stream (Wi-Fi)
              ▼
+─────────────┼────────────────────────────────+
│     EMBEDDED HARDWARE LAYER (ESP32-S3)       │
│                                              │
│  ┌──────────┴───────────┐                    │
│  │   open62541 C Stack  │                    │
│  └──────────────────────┘                    │
+----------------──────────────────────────────┘

## Process Isolation Boundary

* B4J Process: Executes on the main system Java thread layout, controlling the application pipeline and rendering graphic views via JavaFX. It is non-blocking, event-driven, and single-threaded by design.
* Python Subprocess: Spawned by B4J as a completely independent, detached operating system process via PyBridge. It hosts its own private application memory map, library dependencies (asyncua), and native network thread layouts.
* Inter-Process Communication (IPC): B4J and Python converse using standard JSON frames streamed across a private local loopback TCP socket (127.0.0.1), allocated dynamically at boot.

------------------------------
## 2. Deep-Dive: The Producer-Consumer Memory Queue Pattern
When managing data flows across completely separate operating system environments, direct, real-time method invocations can lock or crash the application. If B4J freezes waiting for a slow network handshake from an ESP32, the user interface locks up completely.
To eliminate this vulnerability, this framework implements a Decoupled Producer-Consumer Array Queue Pattern using a standard Python list variable (global_event_queue).

                              [ PRODUCER LAYER ]
                            Asynchronous Python
                                     │
                                     ▼
        ┌─────────────────────────────────────────────────────────┐
        │ global_event_queue = [ Event0, Event1, Event2, ... ]    │
        └────────────────────────────┬────────────────────────────┘
                                     │
                                     ▼
                              [ CONSUMER LAYER ]
                            Synchronous B4J Loop

## The Mechanism

   1. The Producer (Python Background Thread): The SyncSubHandler and OpcSyncWorker handle incoming network events asynchronously. When the ESP32 pushes a modification or a connection state shifts, Python wraps the data inside an un-serialized dictionary primitive and pushes it to the bottom of the array list:
   
   global_event_queue.append({"event": "opc_datachange", "value": f"{node_id_str} {val}"})
   
   2. The Buffer Sandbox: The events sit safely inside the isolated Python global memory map. This design prevents any synchronization locks or thread deadlocks, because the network layer does not need to know if B4J is ready to process the data.
   3. The Consumer (B4J Main Loop): Inside B4XMainPage.bas, a continuous background Do While True loop queries Python's memory space every 100 milliseconds:
   
   Wait For (Opc.PollNextEvent) Complete (EventData As Object)
   
   4. Non-Blocking Extraction: The Wait For statement temporarily pauses the subroutine and instantly yields 100% of the CPU allocation back to the JVM renderer, keeping the layout completely responsive.
   5. Python pops the oldest event off the list (global_event_queue.pop(0)) and hands it straight back across the IPC boundary to B4J as a safe Object. B4J maps this object to a local Map, extracts the keys, and raises the appropriate callback.

------------------------------
## 3. Industrial Data Flow: State-Feedback Verification Loop
The dashboard handles data verification by following strict industrial SCADA guidelines rather than checking or toggling view states locally on click. It implements a complete State-Feedback Verification Loop.

┌─────────────────┐   1. Click   ┌────────────────┐   2. Write Value   ┌──────────┐
│   LED SWITCH    ├─────────────►│ OpcUaClient.bas├───────────────────►│ ESP32-S3 │
│  (Command Tile) │              │  (SendTrigger) │                    │  SERVER  │
└─────────────────┘              └────────────────┘                    └────┬─────┘
                                                                            │
                                                                            │ 3. Evaluate
                                                                            │    Hardware
                                                                            ▼    Pin State
┌─────────────────┐              ┌────────────────┐  5. Raise Callback ┌──────────┐
│    LED STATE    │◄─────────────┤ B4XMainPage UI ◄────────────────────┤ Telemetry│
│(Indicator Tile) │ 6. Update UI │  (DataChanged) │  Data-Change Push  │   Push   │
└─────────────────┘              └────────────────┘                    └──────────┘

## Operational Step Sequence

   1. The Intent Command: The user interacts with the TileIOLedSwitch. B4J intercepts the event on the layout layer, locks the switch position to match its current known connection property state, and executes:
   
   Opc.SendOpcTrigger("ledon")
   
   2. The IPC Dispatch: B4J passes the string parameter over the loopback TCP socket to Python. Python takes the value and wraps it inside an industrial binary variant envelope layout:
   
   payload = ua.Variant(value_str, ua.VariantType.String)
   trigger_node.write_value(payload)
   
   3. The Network Handshake: The packet travels over the Wi-Fi network to the ESP32-S3. The open62541 stack on the chip receives the payload, unpacks the text string value "ledon", and executes its native B4R callback routine to toggle the hardware pin.
   4. The Verified Feedback: The microcontroller changes the state of its internal LedState variable node from 0 to 1. Because the client is subscribed to this data node, the ESP32 automatically generates a high-priority data-change notification push packet back to the client.
   5. The UI Update: The client catches the packet, appends it to the event queue, and B4J extracts it. B4J checks the node name inside a Select Case block and explicitly flips the TileIOLedState tile color indicator.

## Why this is superior to Local UI Toggling:

* If a hardware fault occurs (such as a lost Wi-Fi connection, power loss on the chip, or an open circuit), the LED SWITCH will toggle, but the LED STATE indicator will remain dark. This provides the operator with real-time field-level hardware fault detection.
* If the LED is switched from an external source (like a physical push-button on the machine or an overlay Node-RED layout panel), the B4J LED STATE view will update automatically, because it relies on the same shared backend subscription pipeline.

------------------------------
## 4. Analysis of Protocols: Variable Nodes vs. Write-Only Action Nodes
The reason subscribing to "ns=1;s=Trigger" originally caused the client connection to crash (Write error: client is disconnected) comes down to an essential design rule of the OPC UA protocol specifications.
## Variable Nodes (Telemetry Channels)
Nodes like ns=1;s=LedState are Data Variable Nodes. They are explicitly configured on the server with read and notify access rights. They maintain a stable state value in RAM. Subscribing to them is efficient; the server sets up an automatic monitoring task and pushes notifications only when the underlying data register shifts.
## Write-Only Action Nodes (Command Channels)
Nodes like ns=1;s=Trigger function as Action/Command Nodes. Their sole purpose is to act as an execution doorway for incoming client payloads ("ledon", "ledoff", etc.). They pass arguments down into a callback function on the chip and do not maintain or retain a stable telemetry value in RAM.
## Why the Duplicate Subscription Crashed the Socket:
When the code requested a subscription to "ns=1;s=Trigger", it forced the small embedded open62541 stack on the ESP32-S3 to attempt to allocate a data-change tracking thread loop for a write-only action node.
Because the node does not maintain a continuous data history, the microcontroller's memory stack flooded immediately, causing an internal queue exception. The ESP32 tore down the secure socket session to protect its core tasks, which caused asyncua to endlessly report ConnectionError: client is disconnected.
The Golden Rule: You should only Write to action nodes on demand via SendOpcTrigger, and reserve Subscriptions exclusively for readable variable data nodes like sensors and status indicators.
------------------------------
## 5. Industrial Protocol Analysis: The Subscription Handshake Log
When your application successfully hooks into your ESP32-S3 client thread, you will notice this log confirmation every single time:

Revised values returned differ from subscription values: 
CreateSubscriptionResult(SubscriptionId=1, RevisedPublishingInterval=500.0, 
RevisedLifetimeCount=10000, RevisedMaxKeepAliveCount=100)

This log tracking line shows the underlying negotiation pattern required by the OPC UA Specification (IEC 62541):

   1. The Request: When B4J triggers Opc.Subscribe, Python's asyncua layer contacts the ESP32 server and asks for a fast data monitoring sampling refresh window of 100 milliseconds.
   2. The Server Negotiation: The embedded open62541 core running on your ESP32-S3-N16R8 evaluation chip analyzes its current execution metrics (managing Wi-Fi network stacks, FreeRTOS scheduling tasks, and hardware loops). It decides that sampling a node every 100ms is too resource-intensive and would cause core loop lag.
   3. The Revised Override: The ESP32 overrides the client's request and sends back a revised contract:
   * RevisedPublishingInterval=500.0: The server locks the maximum delivery speed of telemetry push updates to 500 milliseconds (twice a second). This gives the microcontroller ample time to process its tasks stably.
      * RevisedMaxKeepAliveCount=100: If your field value does not alter state for a long time, the server will automatically send an empty keep-alive ping packet every 100 intervals (every 50 seconds) to verify the network link is still healthy.
      * RevisedLifetimeCount=10000: If the client application crashes or stops acknowledging these keep-alive frames for 10,000 continuous cycles, the ESP32 will drop the session and free up its RAM tables automatically.
   
------------------------------
## 6. Structural Code Analysis: Method Scope Flow Chart
This structural code execution map details exactly how data and control commands pass through your custom class routines:

[ USER INTERACTION LAYER ]
 B4XMainPage.bas
   │
   ├─► TileIOConnectSwitch_Click() ──► Calls Opc.Connect()
   │                                     │
   ├─► TileIOLedSwitch_Click()     ──► Calls Opc.SendOpcTrigger()
   │                                     │
   └─► StartBackgroundEventLoop()  ◄── Polling Loop (Every 100ms)
                                         │
                                         ▼
[ ENCAPSULATION WRAPPER LAYER ]          │
 OpcUaClient.bas                         │
   │                                     │
   ├─► Connect() ─────────► Dispatches Py.RunCode("CallConnect")
   ├─► Subscribe() ───────► Dispatches Py.RunCode("CallSubscribe")
   ├─► SendOpcTrigger() ──► Dispatches Py.RunCode("SendOpcTrigger")
   │                                     │
   ├─► PollNextEvent() ◄─────────────────┤ Calls Py.RunCode("PollNextEvent")
   │     │                               │
   │     └─► Res.Value ──► Returns Event Object snapshot map
   │                                     │
   └─► RaiseB4jEvent() ◄─────────────────┘ Extracts keys and fires:
         │
         ├─► CallSub2(MainPage, "OpcClient_ConnectionChanged")
         └─► CallSub3(MainPage, "OpcClient_DataChanged")

By keeping the code structured this way, your main user interface code layer remains remarkably clean and easy to maintain. All of the complex underlying Python data types, thread safety loops, and multi-process TCP socket transactions are handled safely behind the scenes inside your OpcUaClient class module container.
------------------------------
This learning documentation module is fully complete for your reference files! If you want to expand your framework later, let me know if you would like to look at:

* Adding an automated CSV Telemetry File Logger inside B4J to log every subscription data change with a timestamp.
* Expanding your OpcClient_DataChanged block to parse analog values like temperature floating-point readings.


