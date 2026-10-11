# CHANGELOG

## v0.95 BETA (Build 20261011)
**Focus: Bi-Directional SCADA Control, Type-Explicit Write API & Multi-Method Architecture Optimization**
- NEW: [CRITICAL] Introduced a type-explicit internal Write API (`WriteFloat`, `WriteInt`, `WriteUInt`, `WriteBoolean`, `WriteLong`, `WriteULong`) that directly aligns with native B4R types (`Float`, `Int`, `UInt`, `Long`, `ULong`). This bypasses unstable 64-bit floating-point `double` casting, ensuring exact bit-level matching inside open62541 memory registers.
- NEW: [CRITICAL] Implemented a thread-safe execution barrier flag (`isOnDataWrite`) inside the synchronized mutex scope of all internal write methods. This guarantees that internal server loops (such as sensor updates or tracking `AvailableRAM`) execute silently without triggering recursive user-facing callbacks or watchdog timeouts.
- NEW: Added the universal `OnDataWrite` background routing callback engine. Real-time network modifications originating from external clients (FUXA, Node-RED, etc.) now instantly fire standard event handler subroutines (`Sub OnDataWrite`) natively in B4R, providing the qualified full network Node ID string prefix (`ns=X;s=...`) alongside a clean 4-byte raw value payload array.
- NEW: Added `AddTriggerNode` to create a polymorphic node initialized with `UA_VALUERANK_ANY`. This allows a single Node ID to dynamically accept, process, and parse multi-format incoming data payloads (Strings, ByteStrings, Byte Arrays, and Scalar Numbers) seamlessly without runtime configuration failures or casting issues.
- NEW: Refactored `AddMethodNode` to utilize native `methodContext` tracking. Multiple custom method nodes (e.g., separate Tank 1 and Tank 2 control lines) can now be initialized concurrently. Execution routing bypasses rigid global class member fields, firing assigned B4R subroutines instantly and synchronously inside the callback envelope.
- NEW: Added `AddObjectNode` to allow standardized structural grouping of process variables and method handlers under a single parent object asset profile (BaseObjectType), maximizing compliance with the official OPC UA Information Modeling specification.
- FIX: Patched an internal memory cross-talk bug inside `AddMethodNode` by transitioning argument parameters from temporary stack/shared configurations to unique, permanent heap allocations via `new UA_ValueCallback`. This guarantees distinct pointer isolation and stable memory persistence when initializing multiple nodes sequentially.
- FIX: Resolved a binary array serialization bug where raw bytes (`Array As Byte`) passed from high-level client wrappers (such as B4J with PyBridge) were failing parsing conditions and turning into empty buffers. Implementations can now pass data securely via an `ISO-8859-1` encoded wrapper or explicit `ByteString` writers.
- FIX: Standardized Core 0 background worker task configuration (`Priority 1`) to preserve an ultra-low-overhead networking profile, ensuring maximum processing headroom remains dedicated to real-time B4R control algorithms running on Core 1.
- FIX: Main execution loop managing real-time tank simulation timers and handling synchronous event callbacks instantly (TankSim 25-Node example optimized with standard `justValue` compressed reading payload arrays).

### Development Status
B4R-facing OPC UA server API implements:
- OPC UA Server Core Configuration: Complete initialization handling with clean IsReady state reporting.
- Robust Security Profiles: Supports both full Anonymous Access and strict Username/Password authentication.
- Dual-Core Asymmetric Processing: High-performance ESP32 FreeRTOS background server task architecture (Core 0 networking / Core 1 B4R control logic).
- Dynamic Node Profiling: On-the-fly allocation of Float, Int, Boolean, String, and Polymorphic Trigger/ByteString nodes under a shared structural root asset folder.
- Thread-Safe Memory Management: Full Semaphore mutex protection wrapping all low-level open62541 memory map access routines.
- Qualified Data Space Tracking: Full support for mandatory namespace-aware string NodeIds (ns=X;s=...) across both server mapping and client communication.
- Bi-Directional SCADA Interception: High-speed generic data-write callbacks (OnDataWrite) that instantly execute B4R subroutines on remote client network updates.
- Type-Explicit Application Writes: Dedicated hardware-aligned write API (WriteFloat, WriteInt, WriteUInt, etc.) utilizing thread-isolated flag suppression (isOnDataWrite) to allow silent internal sensor/RAM loops.
- Remote Procedure Calls (RPC): Native Method node creation with multi-context data routing and synchronous input/output payload array response handling.

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
