# CHANGELOG

## v1.00 (Build 20261018)
- NEW: First final release.

## v0.96 BETA (Build 20261017)
**Focus: Finalizing API & Tests**
- FIX: Dynamic Flag Toggles Retained: The isOnDataWrite intercept boundaries are completely preserved to avoid collision problems with synchronous data callbacks elsewhere in the project framework.
- FIX: Internal Memory Management: For WriteByteString and WriteString, structures match the clean writeDataValue lifecycle correctly, meaning variables won't cause memory leakage on tiny RTOS heap allocations.
- UPD: Removed Temporary UA_Variant myVar: The variant nested inside the data value structure (myDataValue.value) is directly assigned via UA_Variant_setScalar, optimizing RAM overhead.
- NEW: Integrated Automated Source Timestamps: Upgraded all `Write` methods from `UA_Server_writeValue` to `UA_Server_writeDataValue` using `UA_DateTime_now()` to natively feed exact event time metrics to external logging clients.
- NEW: Universal `OnDataWrite` background routing callback engine added `ByteString` and increased to max 64-byte raw value payload array.
- NEW: Added `ReadDataValue` diagnostic method to pull full low-level node cache data structures (Value, Status, and dual 64-bit Hex Timestamps) directly out of the Address Space to the native C++ hardware serial line.
- NEW: Added `ReadDataAttribute` selector function providing direct access to specific data bytes (Value, Source Timestamp, Server Timestamp, Status Code) packaged safely inside standard B4R `ArrayByte` stack containers.
- NEW: Folder `examples/Shared` with [B4J](https://www.b4x.com/b4j.html) helper classes, like `PyOpcUaClient` and code modules.
- NEW: `PyOpcUaClient` is a B4J class module that provides an OPC UA client interface through **PyBridge** and Python's `asyncua` package.
- NEW: Example `ByteString` with standard byte array and serialized byte array (`B4RSerializator`).
- NEW: Example `Read` to demonstrate reading node value or node attributes (value, source timestamp, server timestamp, status).
- NEW: Example `TankSim` to demonstrate rOpen62541 with a) OPC UA server with [FUXA](http://github.com/frangoteam/fuxa) and b) B4J with the `PyOpcUaClient` class.

## v0.95 (Build 20261011)
**Focus: Bi-Directional SCADA Control, Type-Explicit Write API & Multi-Method Architecture Optimization**
- NEW: Introduced a type-explicit internal Write API (`WriteFloat`, `WriteInt`, `WriteUInt`, `WriteBoolean`, `WriteLong`, `WriteULong`) that directly aligns with native B4R types (`Float`, `Int`, `UInt`, `Long`, `ULong`). This bypasses unstable 64-bit floating-point `double` casting, ensuring exact bit-level matching inside open62541 memory registers.
- NEW: Implemented a thread-safe execution barrier flag (`isOnDataWrite`) inside the synchronized mutex scope of all internal write methods. This guarantees that internal server loops (such as sensor updates or tracking `AvailableRAM`) execute silently without triggering recursive user-facing callbacks or watchdog timeouts.
- NEW: Added the universal `OnDataWrite` background routing callback engine. Real-time network modifications originating from external clients (FUXA, Node-RED, etc.) now instantly fire standard event handler subroutines (`Sub OnDataWrite`) natively in B4R, providing the qualified full network Node ID string prefix (`ns=X;s=...`) alongside a clean 4-byte raw value payload array.
- NEW: Added `AddTriggerNode` to create a polymorphic node initialized with `UA_VALUERANK_ANY`. This allows a single Node ID to dynamically accept, process, and parse multi-format incoming data payloads (Strings, ByteStrings, Byte Arrays, and Scalar Numbers) seamlessly without runtime configuration failures or casting issues.
- NEW: Refactored `AddMethodNode` to utilize native `methodContext` tracking. Multiple custom method nodes (e.g., separate Tank 1 and Tank 2 control lines) can now be initialized concurrently. Execution routing bypasses rigid global class member fields, firing assigned B4R subroutines instantly and synchronously inside the callback envelope.
- NEW: Added `AddObjectNode` to allow standardized structural grouping of process variables and method handlers under a single parent object asset profile (BaseObjectType), maximizing compliance with the official OPC UA Information Modeling specification.
- FIX: Patched an internal memory cross-talk bug inside `AddMethodNode` by transitioning argument parameters from temporary stack/shared configurations to unique, permanent heap allocations via `new UA_ValueCallback`. This guarantees distinct pointer isolation and stable memory persistence when initializing multiple nodes sequentially.
- FIX: Resolved a binary array serialization bug where raw bytes (`Array As Byte`) passed from high-level client wrappers (such as B4J with PyBridge) were failing parsing conditions and turning into empty buffers. Implementations can now pass data securely via an `ISO-8859-1` encoded wrapper or explicit `ByteString` writers.
- FIX: Standardized Core 0 background worker task configuration (`Priority 1`) to preserve an ultra-low-overhead networking profile, ensuring maximum processing headroom remains dedicated to real-time B4R control algorithms running on Core 1.
- FIX: Main execution loop managing real-time tank simulation timers and handling synchronous event callbacks instantly (TankSim 25-Node example optimized with standard `justValue` compressed reading payload arrays).

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
