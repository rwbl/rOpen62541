# CHANGELOG

## v0.95 BETA (Build 20261009)
**Focus: API Final Testing**
- NEW: Added `AddTriggerNode` to create a polymorphic node initialized with `UA_VALUERANK_ANY`. This allows a single Node ID to dynamically accept, process, and parse multi-format incoming data payloads (Strings, ByteStrings, Byte Arrays, and Scalar Numbers) seamlessly without runtime configuration failures or casting issues.
- FIX: Resolved a binary array serialization bug where raw bytes (`Array As Byte`) passed from high-level client wrappers (such as B4J `SS_OPCUAClient`) were failing parsing conditions and turning into empty buffers. Implementations can now pass data securely via an `ISO-8859-1` encoded wrapper or explicit `ByteString` writers.
- FIX: Callback example with trigger and method nodes & callback.

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

## v0.91 (Build 20261007)
**Focus: API Aligned Simplification & Full Multi-Format Node Support**
- NEW: Rewrote `Read` and `Write` methods to accept a unified `NodeIdentifier` string (e.g., `"ns=1;s=Temperature"` or `"ns=0;i=2258"`). This eliminates the clunky separation of `NamespaceIndex` and `NodeString` parameters, perfectly aligning them with the `Add` node methods.
- NEW: Added native parser support for numeric Node IDs (`i=`), allowing direct interaction with core system metrics and native server nodes (such as reading server status or diagnostic variables like `ns=0;i=2258`).
- NEW: Added a complete `DHT22` sensor telemetry example demonstrating how to publish real-world ambient Temperature & Humidity metrics to modern SCADA/IoT infrastructure like **Home Assistant** (via Node-RED).
- UPD: Refactored all packaged library examples to adopt the new streamlined single-string `Read` and `Write` API.

## v0.90 (Build 20261002)
**Focus: Major development improvements**
- NEW: Node identifiers are no longer restricted to namespace 1.
	- Supports OPC UA string NodeIds such as `ns=2;s=ledstate`.
	- Existing identifiers such as `ledstate` continue to use namespace 1, i.e. `ns=1;s=ledstate`.
- NEW: Added `WriteByteString` and `ReadByteString`.
- NEW: Added a shared root folder NodeId for dynamically added nodes.
	- The default root folder is `Factory_Floor`.
	- All added nodes and methods are attached to this root folder.
- NEW: Added example ServoControl to demonstrate usage of the Trigger callback.

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
