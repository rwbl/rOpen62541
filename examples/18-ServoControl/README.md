# rOpen62541 B4R Library

## ServoControl - OPC UA Server Example
This project demonstrates controling the position of a servo motor connected to the ESP32S3 acting as an autonomous OPC UA Server.

* The servo acts as a gate with position open or close.
* An Traffic-Light-LED (output) shows the state open (green) or close (red).
* A push-button (input) can also be used to open or close the gate.
* The OPC UA client sends the trigger message `ns=1;s=Trigger` with string values `gateopen` or `gateclose`.

------------------------------

## Operational Pipeline

```
[ Industrial Client / SCADA ]
      │
      ├─── (1) SUBSCRIBE / READ ───►  [ ns=1;s=gatestate ]   (Int, Live Telemetry)
      │
      └─── (2) WRITE (Str/Int/Flt) ─►  [ ns=1;s=Trigger ]    (String, Universal Interceptor) ──► Fires B4R Callback
```

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
The server dynamically maintains a int data register. Clients should connect and SUBSCRIBE or READ the following address:

* Node Identifier: ns=1;s=gatestate
* Data Type: Int
* Updates on every servo motor position change - values 1=gate open, 0=gate closed.

------------------------------
## Sending Data & Triggering Actions (Input to ESP32)
To bypass the lack of native method invocation wrappers (CallMethod) in standard client applications, this example exposes a Universal Write Interceptor Node. Writing any primitive data layout to this node automatically executes the internal B4R hardware response routing loop.

* Node Identifier: ns=1;s=Trigger
* Data Type: String 

## Client Execution Payload Examples:
The node `ns=1;s=Trigger` expects value from data type string.
The ESP32's hardware callback interceptor automatically flattens them into a clean string representation before firing the B4R event:

Text Actions: 
Writing "gateopen" or "gateclose" to set the state of the servo motor and the state of the traffic light RED or GREEN LED.

------------------------------

## Console Execution Footprint
When a client attaches to the simulator node layout and executes a remote command action over the network, your B4R console monitor will cleanly print the sequential thread-safe transition:
B4R Log:
```
```

---

## Wiring

### Output
ESP32S3 = REDLED; IO42 = Signal; GND = GND
ESP32S3 = GREENLED; IO40 = Signal; GND = GND

### Input
ESP32S3 = Pushbutton (DFRobot); IO4 = Signal; 3.3V = VCC; GND = GND
ESP32S3 = Servo; IO14 = Signal; Ext. Power 5V = VCC; GND = Common GND
WARNING: Connect Servo VCC to an external 5V power supply; powering it directly from the ESP32 5V pin can cause instability or damage due to high current draw.
NOTE: Ensure the external power supply GND is tied together with the ESP32 GND to create a common ground rail.



