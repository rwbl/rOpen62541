# rOpen62541 B4R Library

## EnvSim - OPC UA Environment Simulation Example
This project demonstrates a bi-directional industrial environmental simulator running on the ESP32-S3-N16R8.  
It acts as an autonomous OPC UA Server, securely exposing live simulated telemetry to networks while intercepting inbound control override parameters from clients (like B4J or Node-RED).

------------------------------

## Operational Pipeline

[ Industrial Client / SCADA ]
      │
      ├─── (1) SUBSCRIBE / READ ───►  [ ns=1;s=Temperature ]   (Float, Live Telemetry)
      ├─── (2) SUBSCRIBE / READ ───►  [ ns=1;s=Humidity ]      (Float, Live Telemetry)
      ├─── (3) SUBSCRIBE / READ ───►  [ ns=1;s=Counter ]       (Int, Live Telemetry)
      ├─── (4) SUBSCRIBE / READ ───►  [ ns=1;s=RawTelemetry ]  (ByteString, Live Telemetry)
      │
      └─── (5) WRITE (Str/Int/Flt) ─►  [ ns=1;s=Trigger ]      (String, Universal Interceptor) ──► Fires B4R Callback

*Note:* The library only supports flat hierarchy under the root node `Factory_Floor`)

------------------------------

## Connection Specifications
To connect an external industrial client or SCADA system to the EnvSim server, use the following profile parameters:

* Target Endpoint URL: opc.tcp://192.168.1.175:4840/ (Replace with your ESP32's actual local IP address)
* Security Policy: None
* Security Mode: None
* Authentication: Anonymous (No-Auth)

------------------------------

## Receiving Data (Output from ESP32)
The server dynamically maintains a floating-point data register that simulates ambient industrial conditions. Clients should connect and SUBSCRIBE or READ the following address:

* Node Identifier: ns=1;s=Temperature
* Data Type: Float
* Pacing Interval: Updates every 2000ms via the background B4R timer routine.
Same for Humidity, Counter, RawTelemetry

------------------------------
## Sending Data & Triggering Actions (Input to ESP32)
To bypass the lack of native method invocation wrappers (CallMethod) in standard client applications, this example exposes a Universal Write Interceptor Node. Writing any primitive data layout to this node automatically executes the internal B4R hardware response routing loop.

* Node Identifier: ns=1;s=Trigger
* Data Type: String (Accepts numerical conversions seamlessly)

## Client Execution Payload Examples:
You can write values to node `ns=1;s=Trigger` using various data types.  
The ESP32's hardware callback interceptor automatically flattens them into a clean string representation before firing the B4R event:

1. Text Actions: Writing "STOP" immediately forces the emergency hardware safe loop.
2. Integer Actions: Writing 68 parses straight into a production recipe index selector.
3. Floating-Point Actions: Writing 24.50 triggers real-time threshold calibration tracking.

------------------------------

## Console Execution Footprint
When a client attaches to the simulator node layout and executes a remote command action over the network, your B4R console monitor will cleanly print the sequential thread-safe transition:
B4R Log:
```
[AppStart] rOpen62541 EnvSim v20260920
[AppStart] WiFi connected > local IP=192.168.1.175
[AppStart] Init opc server
[InitOPCUAServer] Initializing...
[InitOPCUAServer] Awaiting background core network initialization...
[opcuaServerTask] OPC UA Security: Anonymous Access Mode Active!
[opcuaServerTask] OPC UA Server successfully running on Core 0!
[InitOPCUAServer] Core 0 online! Spawning dynamic address space nodes...
[AppStart] Opc server started
[AppTimer] Sensor read complete. Updated Node memory with: t=25.5000 h=74
[OpcCallback] SCADA/B4J Client clicked the trigger method. command=STOP
```
This means
1. The B4R setup engine yields And waits: [AppStart] Awaiting background core network initialization...
2. Core 0 spins up silently, selects the security mode, And mounts the PORT: OPC UA Security: Anonymous Access Mode Active! And OPC UA Server successfully running on Core 0!
3. B4R breaks out of the safety Loop And dynamically attaches the nodes: [AppStart] Core 0 online! Spawning dynamic address space nodes...
4. The background FreeRTOS value-callback intercepts the B4J write And safely executes the B4R Sub: [OpcCallback] SCADA/B4J Client clicked the trigger method!

---



