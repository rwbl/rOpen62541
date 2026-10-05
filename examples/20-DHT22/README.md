# rOpen62541 B4R Library

## DHT22 - OPC UA Environment Example
This project demonstrates a bi-directional industrial environmental system using a DHT22 sensor running on the ESP32-S3-N16R8.  
It acts as an autonomous OPC UA Server, securely exposing live telemetry to networks while intercepting inbound control override parameters from clients (like B4J or Node-RED).

------------------------------

## Operational Pipeline

[ Industrial Client / SCADA ]
      │
      ├─── (1) SUBSCRIBE / READ ───►  [ ns=1;s=Temperature ]  (Float, DHT22 Live Telemetry)
      ├─── (2) SUBSCRIBE / READ ───►  [ ns=1;s=Humidity ]  (Float, DHT22 Live Telemetry)
      │
      └─── (3) WRITE (Str/Int/Flt) ─►  [ ns=1;s=Trigger ] (String, Universal Interceptor) ──► Fires B4R Callback (value must be set, but is not used as always fires callback)

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
* Pacing Interval: Updates if sensor data changes (every 50ms) via the background B4R task.

* Node Identifier: ns=1;s=Humidity
* Data Type: Float
* Pacing Interval: Updates if sensor data changes (every 50ms) via the background B4R task.

------------------------------

## Sending Data & Triggering Actions (Input to ESP32)
To bypass the lack of native method invocation wrappers (CallMethod) in standard client applications, this example exposes a Universal Write Interceptor Node.  
Writing any primitive data layout to this node automatically executes the internal B4R hardware response routing loop.

* Node Identifier: ns=1;s=Trigger
* Data Type: String (Accepts numerical conversions seamlessly)

## Client Execution Payload Examples:
You can write values to ns=1;s=Trigger using various data types.  
The ESP32's hardware callback interceptor automatically flattens them into a clean string representation before firing the B4R event:

   1. Text Actions: Writing "STOP" immediately forces the emergency hardware safe loop.
   2. Integer Actions: Writing 68 parses straight into a production recipe index selector.
   3. Floating-Point Actions: Writing 24.50 triggers real-time threshold calibration tracking.

------------------------------

## Console Execution Footprint
When a client attaches to the node layout and subscribes to the environmental nodes it receives data when changes (`[OnDHTStateChanged] t=18.2000, h=55.3000`).
The client can also execute a remote command action over the network, your B4R console monitor will print the sequential thread-safe transition (`[OnDataWrite] Client trigger method requestdata`).
```
[AppStart] rOpen62541 DHT22 v20261004
[Main.AppStart] t=18.1000, h=55.4000
[AppStart] WiFi connected > local ip=192.168.1.175
[Hardware Engine] Wi-Fi Modem-Sleep forcefully disabled! Radio set to high-performance mode.
[AppStart] Init OPC UA server
[InitOPCUAServer] Initializing...
[InitOPCUAServer] Awaiting background core network initialization...
[opcuaServerTask] OPC UA Security: Anonymous Access Mode Active!
[opcuaServerTask] OPC UA Server successfully running on Core 0!
[InitOPCUAServer] OPC UA Server initialized > Register nodes...
[InitOPCUAServer] Register nodes OK
[AppStart] OPC UA server started
[OnDHTStateChanged] t=18.2000, h=55.3000
[OnDataWrite] Client trigger method requestdata
```

---

## Wiring
Requires 4.7K resistor R between signal and VCC.
```
DHT22 = ESP32S3
VCC = 3.3V - after R
Signal = IO17
Signal > R = 3.3V
GND = GND
```



