# Functions Reference

This document provides a comprehensive **API reference** list for the **rOpen62541** library classes, tracking types, and data-routing methods.

---

## Core Engine Methods

### Initialize
```b4x
Initialize (Port As Int, LocalIP As String, Username As String, Password As String, MethodTriggerSub As Object)
```
- Initializes the OPC UA Server core engine, establishes the listening network port, boots the underlying server background runtime loop on Core 0, and hooks your B4R callback routine.

### IsReady
```b4x
IsReady As Boolean (Property Getter)
```
- Returns `True` if the background FreeRTOS network task on Core 0 has successfully initialized the configurations, created root folder structures, and bound the TCP sockets.

---

## Address Space Node Creation

### AddStringNode
```b4x
AddStringNode (NodeIdentifier As String, DisplayName As String, InitialValue As String)
```
- Dynamically instantiates a unique string-identified OPC UA variable node in the main `"Factory_Floor"` parent directory. If the `NodeIdentifier` matches exactly `"Trigger"`, the library attaches a native C++ write-callback interceptor to capture network payloads.

### AddFloatNode
```b4x
AddFloatNode (NodeIdentifier As String, DisplayName As String, InitialValue As Float)
```
- Dynamically instantiates a unique string-identified floating-point variable node attached to the primary tree registry.

### AddIntNode
```b4x
AddIntNode (NodeIdentifier As String, DisplayName As String, InitialValue As Int)
```
- Dynamically instantiates a unique string-identified 32-bit signed integer variable node attached to the primary tree registry.

### AddByteStringNode
```b4x
AddByteStringNode (NodeIdentifier As String, DisplayName As String, InitialBytes() As Byte)
```
- Dynamically allocates a new raw `ByteString` variable node inside the Factory Floor folder. Perfect for transferring `B4RSerializator` binary buffers or plain byte sets.

### AddBooleanNode
```b4x
AddBooleanNode (NodeIdentifier As String, DisplayName As String, InitialValue As Boolean)
```
- Dynamically instantiates a unique string-identified OPC UA variable node in the main `"Factory_Floor"` parent directory. Configured using the native `UA_TYPES_BOOLEAN` primitive format, this node is ideal for publishing raw system states or driving hardware switching configurations like output relays.

---

## Remote Procedure Call (RPC) Methods

### AddMethodNode
```b4x
AddMethodNode (MethodName As String, DisplayName As String, MethodCallSub As Object)
```
- Dynamically instantiates a unique string-identified executable RPC Method node inside the main `"Factory_Floor"` parent directory. It configures a single universal input argument parameter slot (`ByteString` layout) and a single `INT32` output verification parameter slot, anchoring your dedicated B4R execution callback subroutine entry pointer.

### SetMethodReturnCode
```b4x
SetMethodReturnCode (Code As Int)
```
- Sets the integer execution status return token code for the currently processed network method invocation frame. This function **must** be executed inside your B4R method callback subroutine to send an atomic confirmation value (e.g., `100` for success or `400` for failure) back across the network socket layer to the client application.

---

## Write Nodes

### WriteNumeric
```b4x
WriteNumeric(NamespaceIndex As Int, NodeIdentifier As String, NewValue As Double)
```
- Writes a numeric payload into an active OPC UA node.
- Supports cross-core type verification for floating-point, integer, and boolean structures inside Namespace 1.
- Parameter:
	- NodeIdentifier The targeting string Node ID (e.g., "Temperature").
	- NewValue The numerical double representation payload to convert and assign.
- Examples (namespace 1):
	- OpcServer.WriteNumeric(1, "Temperature", CurrentTemp)
	- OpcServer.WriteNumeric(1, "SystemReady", 1) ' Sets boolean state to true

### WriteString
```b4x
WriteString(NamespaceIndex As Int, NodeIdentifier As String, NewValue As String)
```
- Writes a string text payload into an active OPC UA node.
- Automatically handles string memory copying and verifies target node type data inside Namespace 1.
- Parameter:
	- NodeIdentifier The targeting string Node ID (e.g., "DeviceStatus").
	- NewValue The B4RString payload to write into the node address space.
- Example (namespace 1):
	- OpcServer.WriteString(1, "DeviceStatus", "RUNNING")

---

## Read Nodes

```b4x
ReadNumeric(NamespaceIndex As Int, NumericIdentifier As Int)
```
- Reads a node value using a numeric identifier.
- Dynamically converts scalars (Integers, Booleans, Floats, Doubles, Strings, and Datetimes) into a generic string representation.
- Standard OPC UA DateTime structures are automatically converted and formatted into a universal UTC Zulu timestamp string.
- Parameter:
	- NamespaceIndex The targeting name space index (e.g., 0, 1).
	- NumericIdentifier The targeting numeric identifier (e.g., 2258).
- Example (namespace 0):
	- Dim clockText As String = OpcServer.ReadNumeric(0, 2258)

```b4x
ReadString(NamespaceIndex As Int, NodeIdentifier A String)
```
- Reads a node value using a string identifier.
- Dynamically converts scalars (Integers, Booleans, Floats, Doubles, Strings, and Datetimes) into a generic string representation.
- Standard OPC UA DateTime structures are automatically converted and formatted into a universal UTC Zulu timestamp string.
- Parameter:
	- NamespaceIndex The targeting name space index (e.g., 0, 1).
	- NumericIdentifier The targeting string identifier (e.g., "Temperature").
- Example (namespace 1):
	- Dim tempText As String = OpcServer.ReadString(1, "Temperature")
