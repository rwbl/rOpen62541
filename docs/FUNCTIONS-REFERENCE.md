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
WriteNumeric(NodeIdentifier As String, NewValue As Double)
```
- Writes a numeric payload into an active OPC UA node.
- Supports cross-core type verification for floating-point, integer, and boolean structures inside Namespace 1.
- Parameter:
	- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=1;s=Temperature").
	- NewValue The numerical double representation payload to convert and assign.

### WriteString
```b4x
WriteString(NodeIdentifier As String, NewValue As String)
```
- Writes a String payload into an active OPC UA node.
- Automatically handles string memory copying and verifies target node type data inside Namespace 1.
- Parameter:
	- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=1;s=Temperature").
	- Data The B4R ArrayByte payload to write into the node address space.

### WriteByteString
```b4x
WriteByteString(NodeIdentifier As String, Data As Byte())
```
- Writes a binary ByteString payload into an active OPC UA node.
- Automatically handles string memory copying and verifies target node type data inside Namespace 1.
- Parameter:
	- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=1;s=Temperature").
	- Data The B4R ArrayByte payload to write into the node address space.

---

## Read Nodes

```b4x
ReadNumeric(NodeIdentifier As String)
```
- Reads a node value using a numeric identifier.
- Dynamically converts scalars (Integers, Booleans, Floats, Doubles, Strings, and Datetimes) into a generic string representation.
- Standard OPC UA DateTime structures are automatically converted and formatted into a universal UTC Zulu timestamp string.
- Parameter:
	- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=0;i=2258" for the server time).
- Return: 
	- The scalar node value formatted as a B4R String.
	
```b4x
ReadString(NodeIdentifier A String)
```
- Reads a node value using a string identifier.
- Dynamically converts scalars (Integers, Booleans, Floats, Doubles, Strings, and Datetimes) into a generic string representation.
- Standard OPC UA DateTime structures are automatically converted and formatted into a universal UTC Zulu timestamp string.
- Parameter:
	- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=1;s=Temperature").
- Return: 
	- A B4RString pointer containing the text-formatted value payload.

```b4x
ReadByteString(NodeIdentifier A String) As Byte()
```
- Reads a binary ByteString value using a string identifier.
- Parameter:
	- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=1;s=DeviceData").
- Return: 
	- Array As Byte containing the binary ByteString payload.

---

## Getter/Setter
```b4x
IsReady As Boolean
```
- Set or get whether the server is running.
- Return:
	- Boolean True = Server is ready, False = Server not ready.

---

## Constants
Indicates that the operation was successful and results may be used.
```
ULong STATUS_GOOD = 0x00000000
```
Indicates partial success; results might not fit all purposes.
```
ULong STATUS_UNCERTAIN = 0x40000000
```
Indicates the operation failed completely; results cannot be used.
```
ULong STATUS_BAD = 0x80000000
```
Indicates that the operation was successful and results may be used.
```
ULong STATUS_SUCCESS = 0x00000000
```
Indicates partial success; results might not fit all purposes.
```
ULong STATUS_WARNING = 0x40000000
```
Indicates the operation failed completely; results cannot be used.
```
ULong STATUS_FAILURE = 0x80000000
```
			
