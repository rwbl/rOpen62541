## ESP32 OPC UA (rOpen62541) to Home Assistant via Node-RED

This guide provides a robust and optimized blueprint for connecting a resource-constrained ESP32 server running an OPC UA (rOpen62541) stack compiled via B4R (Basic4ppc for Arduino) to Home Assistant.

## The Problem with Native Integrations
Microcontrollers like the ESP32 have serious constraints regarding concurrent TCP connections and available memory blocks. The default rOpen62541 setup on an ESP32 reliably handles only a single persistent connection.
When using native Home Assistant custom components (such as the HACS asyncua integration), the integration opens and closes a new network handshake during every poll interval (scan_interval). This rapid cycling forces the client to invoke open_secure_channel continuously. For an ESP32, this causes connection thrashing, buffer allocation exhaustion, and frequent unexpected dropouts visible in the Home Assistant logs:

Unexpected error fetching open62541_server data
Traceback (most recent call last):
  File "/usr/src/homeassistant/homeassistant/helpers/update_coordinator.py", line 441, in _async_refresh
  ...
  File "/usr/local/lib/python3.14/site-packages/asyncua/client/client.py", line 287, in connect
    await self.open_secure_channel()

## The Architecture Solution
To bypass network stack constraints, Node-RED is implemented as a middleware gateway. Node-RED establishes exactly one persistent TCP socket to the ESP32 server. It handles data filtering and mathematical rounding locally before pushing clean updates asynchronously into Home Assistant via the native companion API.

┌───────────────┐     Single Persistent Connection     ┌──────────┐     Native Websocket     ┌────────────────┐
│  ESP32 Server │ ───────────────────────────────────> │ Node-RED │ ───────────────────────> │ Home Assistant │
│ (rOpen62541)  │         (opc.tcp://...)              │ Gateway  │   (hass-node-red)      │   Dashboard    │
└───────────────┘                                      └──────────┘                          └────────────────┘

## Requirements## Home Assistant

* 
* HACS (Home Assistant Community Store) installed.
* Node-RED Companion (hass-node-red) integration installed via HACS.
* 

## Node-RED

* 
* Node-RED Add-on active within Home Assistant.
* node-red-contrib-opcua palette installed.
* node-red-contrib-home-assistant-websocket palette installed.
* 

------------------------------
## Configuration & Implementation Steps## 1. Home Assistant Companion Setup

   1. In Home Assistant, navigate to HACS -> Integrations -> Explore & Download Repositories.
   2. Search for Node-RED Companion and download it.
   3. Restart Home Assistant.
   4. Navigate to Settings -> Devices & Services -> + Add Integration.
   5. Search for Node-RED, select it, and complete the integration setup wizard.

## 2. Node-RED Flow Implementation
The pipeline processes concurrent data queries smoothly across a single connection utilizing the following structural nodes:
## Trigger Block (Inject Nodes)
Two separate Inject nodes act as timers to pulse inquiries every 5 seconds. One is configured for Temperature and the other for Humidity.
## Client Block (OPC UA Client Node)
The OPC UA Client node is configured with the explicit endpoint url: opc.tcp://192.168.1.175:4840. It keeps a running connection status of active reading and utilizes incoming tracking variables to target specific properties:

* 
* Temperature NodeID: ns=1;s=Temperature
* Humidity NodeID: ns=1;s=Humidity
* 

## Data Processing Block (Function Node)
Microcontrollers can introduce floating-point inaccuracies during binary translation (e.g., yielding 51.599998... instead of 51.6). A Function node named Set Decimal uses JavaScript to cleanly round numerical payloads down to a single decimal place:

msg.payload = Math.round(msg.payload * 10) / 10;return msg;

## Routing Block (Switch Node)
A yellow Switch node acts as a traffic director. By evaluating msg.topic, it forks incoming data down distinct pipeline branches depending on which identifier generated the message:

* 
* Path 1: Matches strings containing Temperature
* Path 2: Matches strings containing Humidity
* 

## Optimization Block (Filter Nodes)
To prevent Home Assistant database bloat, both branches pass through a Filter node (configured to "block unless value changes"). If an environmental value remains completely steady across multiple intervals, Node-RED drops the payload, eliminating redundant database writes.
## Delivery Block (Home Assistant Sensor Nodes)
The final blue Home Assistant Sensor nodes natively construct and populate the tracking entities within your dashboard:
 
* Temperature Sensor: Configured with Device Class temperature, Unit of Measurement °C, and Node Name OPCUA Temperature.
* Humidity Sensor: Configured with Device Class humidity, Unit of Measurement %, and Node Name OPCUA Humidity.
 
------------------------------
## Key Maintenance Rules

* Single Connection Enforced: Do not use additional desktop OPC UA discovery tools (like UaExpert) permanently while Node-RED is active. The B4R wrapper memory allocation limit is configured specifically to hold one stable channel open.
* Keep Alive Configuration: Ensure the Node-RED OPC UA connection endpoint properties have an appropriate keep-alive timeout interval configured so that the socket remains active over silent gaps without triggering an explicit reconnect routine on the ESP32 side.

