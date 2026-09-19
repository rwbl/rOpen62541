# CHANGELOG

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
- UPD: All examples to apply SRAM fix.
- DEL: UpdateNodeValue - Replaced by WriteNumeric and WriteString for consistency with the Read functions.

## v0.68 (Build 20260915)
- NEW: First version. 
	- Published [B4R Forum](https://www.b4x.com/android/forum/threads/ropen62541-opc-ua-server.172071/) and [GitHub](https://github.com/rwbl/rOpen62541/).
