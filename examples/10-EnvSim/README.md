# EnvSim — OPC UA Environment Simulation Example
This project demonstrates a bi-directional industrial environmental simulator running on the ESP32-S3-N16R8.  
It acts as an autonomous OPC UA Server, securely exposing live simulated telemetry to networks while intercepting inbound control override parameters from industrial clients (like B4J or Node-RED).

------------------------------

## Operational Pipeline

[ Industrial Client / SCADA ]
      │
      ├─── (1) SUBSCRIBE / READ ───►  [ ns=1;s=Temperature ]  (Live Telemetry)
      │
      └─── (2) WRITE (Str/Int/Flt) ─►  [ ns=1;s=Trigger ]      (Universal Interceptor) ──► Fires B4R Callback

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

------------------------------
## Sending Data & Triggering Actions (Input to ESP32)
To bypass the lack of native method invocation wrappers (CallMethod) in standard client applications, this example exposes a Universal Write Interceptor Node. Writing any primitive data layout to this node automatically executes the internal B4R hardware response routing loop.

* Node Identifier: ns=1;s=Trigger
* Data Type: String (Accepts numerical conversions seamlessly)

## Client Execution Payload Examples:
You can write values to ns=1;s=Trigger using various data types. The ESP32's hardware callback interceptor automatically flattens them into a clean string representation before firing the B4R event:

   1. Text Actions: Writing "STOP" immediately forces the emergency hardware safe loop.
   2. Integer Actions: Writing 68 parses straight into a production recipe index selector.
   3. Floating-Point Actions: Writing 24.50 triggers real-time threshold calibration tracking.

------------------------------

## Console Execution Footprint
When a client attaches to the simulator node layout and executes a remote command action over the network, your B4R console monitor will cleanly print the sequential thread-safe transition:

[AppStart] Starting Universal String OPC UA Server...
[AppStart] Awaiting background core network initialization...
OPC UA Security: Anonymous Access Mode Active!
OPC UA Server successfully running on Core 0!
[AppStart] Core 0 online! Creating dynamic node assets...
[AppTimer] Sensor read complete. Updated Node memory with: 24.5000
[OpcCallback] Universal Data Payload Received!
Payload Text: 68
-> Running operation index 68!

---



