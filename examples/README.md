# Project Examples

The following examples are structured into dedicated sub-folders:

- [**10-EnvSim**](10-EnvSim/) — Ambient industrial temperature and humidity data simulation loop. 
	- Demonstrates type-agnostic node interceptors.
	- Uses the B4J `SS_OPCUAClient` library.
- [**12-MethodCallback**](12-MethodCallback/) — Remote Procedure Call (RPC) execution routing. 
	- Demonstrates server-side functions triggered remotely by automation nodes like Node-RED.
	- Uses `Node-RED` with `node-red-contrib-opcua` nodes.
- [**14-InOutput**](14-InOutput/) — Industrial peripheral I/O mapping configurations. 
	- Shows how to dynamically read physical hardware states (Pushbuttons) and drive physical outputs (LEDs).
	- Uses the B4J `PyBridge` framework with Python package `asyncua`.
- [**16-NodeIDs**](16-NodeIDs/) — Low-level standard namespace lookup implementations. 
	- Demonstrates querying standard Namespace 0 (`ns=0`) system variables and parsing complex structure payloads.
	- Uses the B4J `SS_OPCUAClient` library.
