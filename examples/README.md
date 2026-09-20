# Project Examples

The following examples are structured into dedicated sub-folders:

- [**10-EnvSim**](10-EnvSim/) — Ambient industrial temperature and humidity data simulation loop. 
	- Demonstrates type-agnostic node interceptors.
	- Example 1 B4J with the `SS_OPCUAClient` library, `HMITilesIO` for the Dashboard.
	- Example 2 B4J with the `PyBridge` framework with Python package `asyncua`, `HMITilesIO` for the Dashboard.
- [**12-MethodCallback**](12-MethodCallback/) — Remote Procedure Call (RPC) execution routing. 
	- Demonstrates server-side functions triggered remotely by automation nodes like Node-RED.
	- Uses the B4J `SS_OPCUAClient` library, `HMITilesIO` for the Dashboard.
	- Uses `Node-RED` with `node-red-contrib-opcua` nodes.
- [**14-InOutput**](14-InOutput/) — Industrial peripheral I/O mapping configurations. 
	- Shows how to dynamically read physical hardware states (Pushbuttons) and drive physical outputs (LEDs).
	- Example 1 B4J with the `SS_OPCUAClient` library, `HMITilesIO` for the Dashboard.
	- Example 2 B4J with `PyBridge` framework with Python package `asyncua`, `HMITilesIO` for the Dashboard.
- [**16-NodeIDs**](16-NodeIDs/) — Low-level standard namespace lookup implementations. 
	- Demonstrates querying standard Namespace 0 (`ns=0`) system variables and parsing complex structure payloads.
	- Example 1 B4J with the `SS_OPCUAClient` library, `HMITilesIO` for the Dashboard.
	- Example 2 Node.js with the `node-opcua` stack.

---

## Development Tools
- [B4R](https://www.b4x.com/b4r.html) 4.0
- [B4J](https://www.b4x.com/b4j.html) 10.7
	- [B4J SS_OPCUAClient Library](https://www.b4x.com/android/forum/threads/opc-ua-industrial-client-library-connect-to-servers-devices.171977/) 1.0
	- [B4J HMITilesIO Library](https://www.b4x.com/android/forum/threads/hmitilesio.171863/) 0.70
	- [B4J PyBridge Framework](https://www.b4x.com/android/forum/threads/pybridge-the-very-basics.165654/) 1.0
- [Node-RED](https://nodered.org) 5.0.7
	- [[Node-RED](https://github.com/mikakaraila/node-red-contrib-opcua)  0.2.355
- [Node.js node-opcua stack](https://node-opcua.github.io) 2.174.0
- [Arduino IDE](https://docs.arduino.cc/software/ide/) 2.3.10
	- [Arduino core for the ESP32 family of SoCs](https://github.com/espressif/arduino-esp32) 3.3.12

