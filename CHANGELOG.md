# CHANGELOG

## v0.90.0 (Build 20261002)
**Focus: Major development improvements**

- NEW: Node identifiers are no longer restricted to namespace 1.
	- Supports OPC UA string NodeIds such as `ns=2;s=ledstate`.
	- Existing identifiers such as `ledstate` continue to use namespace 1, i.e. `ns=1;s=ledstate`.
- NEW: Added `WriteByteString` and `ReadByteString`.
- NEW: Added a shared root folder NodeId for dynamically added nodes.
	- The default root folder is `Factory_Floor`.
	- All added nodes and methods are attached to this root folder.
- NEW: Added example ServoControl to demonstrate usage of the Trigger callback.

### Development Status
B4R-facing OPC UA server API implements:
- OPC UA server initialization
- Anonymous and username/password authentication
- ESP32 FreeRTOS background server task
- Dynamic node creation
- Float, Int, Boolean, String and ByteString nodes
- Read/write operations
- Namespace-aware string NodeIds
- Method nodes with input/output handling
- B4R callbacks for triggers and methods
- Shared root folder handling
- IsReady status
- Thread protection around open62541 access

## v0.70 (Build 20260919)
- FIX: Optimized OPC UA server task stack size from 64KB to 16KB to force internal SRAM allocation, preventing silent cross-core PSRAM memory corruption and network lockouts during client disconnections.
- NEW: ReadNumeric - Reads a node value using a numeric identifier.
- NEW: ReadString - Reads a node value using a string identifier.
- NEW: WriteNumeric - Writes a node numeric value using a string identifier.
- NEW: WriteString - Writes a node string value using a string identifier.
- NEW: Status Code Constants - Official OPC UA severity status codes (OPC 10000-4 clause 7.38).
- NEW: GitHub Documention - Additional guides in docs DEV-NOTES, FUNCTIONS-REFERENCE, README, TROUBLESHOOTING, TUTORIAL-CALLBACKS, TUTORIAL-NODEID-LIST.
- NEW: Example NodeIDs - Low-level standard namespace lookup implementations. Demonstrates querying standard Namespace 0 system variables and parsing complex structure payloads.
- UPD: Example MethodCall - Revised methods names for the callbacks.
- UPD: All examples to apply critical SRAM fix.
- DEL: UpdateNodeValue - Replaced by WriteNumeric and WriteString for consistency with the Read functions.

## v0.68.0 (Build 20260915)
**Focus: First working version to test**
- NEW: First version.
	- Published [B4R Forum](https://www.b4x.com/android/forum/threads/ropen62541-opc-ua-server.172071/) and [GitHub](https://github.com/rwbl/rOpen62541/).
