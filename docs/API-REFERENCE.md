# Public API Reference

This document provides a comprehensive **Public API reference** list for the **rOpen62541** library classes, tracking types, and data-routing methods.

---

## Core Engine Methods

### Initialize
```b4x
Initialize (Port As Int, LocalIP As String, Username As String, Password As String, TriggerSub As String, DataWriteSub As String)
```
Initializes the OPC UA Server engine, builds the network configuration, and registers the B4R method trigger callback.
Important: Initialize must be called only once during application startup.
If Username is empty "", it defaults to anonymous access.
Parameter
- LocalIP The target TCP network IP.
- Port The target listening TCP network port (typically 4840).
- Username The security access username (use "" for no auth).
- Password The security access password (use "" for no auth).
- TriggerSub The B4R Sub to invoke when the remote trigger node is updated. If not used, set to null (not to "").
- DataWriteSub The B4R Sub to invoke when a node is updated remotely. If not used, set to null (not to "").

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

### AddTriggerNode(NodeIdentifier As String, B4RString* DisplayName, B4RString* InitialValue);
```b4x
AddTriggerNode(NodeIdentifier As String, DisplayName As String, InitialValue As String)
```
Dynamically allocates a new Polymorphic Trigger node variable inside the Factory Floor folder.
Configured with UA_VALUERANK_ANY to act as a universal receptor for various data payloads.
It processes incoming data types dynamically, automatically extracting raw memory contents into the global trigger buffer. 
Supported types include:
- Strings (Text payloads like "STOP", "START")
- ByteStrings / Wrapped Data (Binary arrays from B4J/B4R clients like hex sequences)
- Explicit multi-element Arrays (Byte[])
- Scalar Numbers (Int32, Float, Double, Int16)
Parameter:
- NodeIdentifier The targeting unique string Node ID (mandatory to use "ns=1;s=Trigger").
- DisplayName The human-readable string representation exposed to clients.
- InitialValue The starting string value assigned to the node space on boot.

### AddObjectNode
```b4x
AddObjectNode(NodeIdentifier As String, DisplayName As String)
```
Registers a standardized OPC UA Object node container in the address space.
Creates an instance of BaseObjectType inside the target parent folder. 
This allows logical structural grouping of process variables and method handlers into clear, self-documenting assets (e.g., grouping level tags and setpoint 
handlers together under a single "Tank1" parent object asset).
Parameter:
- ObjectName Unique string identifier utilized to generate the NodeId (String type).
- DisplayName Human-readable localized text displayed by clients during tree browsing.

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

### WriteFloat
```b4x
WriteFloat(NodeIdentifier As String, NewValue As Float)
```
Updates an active OPC UA Float variable node.
Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
Parameter:
- NodeIdentifier The complete NodeId string (e.g., "ns=1;s=Tank1.Level").
- NewValue The floating-point value payload to assign.

### WriteInt
```b4x
WriteInt(NodeIdentifier As String, NewValue As Int);
```
Updates an active OPC UA Int32 variable node.
Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
Parameter:
- NodeIdentifier The complete NodeId string (e.g., "ns=1;s=Counter").
- NewValue The signed 32-bit integer value payload to assign.

### WriteUInt
```b4x
WriteUInt(NodeIdentifier As String, NewValue As UInt)
```
Updates an active OPC UA UInt32 variable node.
Perfect for transferring B4R ULong values or memory metrics cleanly without floating-point shifts.
Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
Parameter:
- NodeIdentifier The complete NodeId string (e.g., "ns=1;s=System.AvailableRAM").
- NewValue The unsigned 32-bit integer value payload to assign.

### WriteBoolean
```b4x
WriteBoolean(NodeIdentifier As String, NewValue As Boolean)
```
Updates an active OPC UA Boolean variable node.
Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
Parameter:
- NodeIdentifier The complete NodeId string (e.g., "ns=1;s=Relay1.State").
- NewValue The Boolean flag state status to assign.

### WriteLong
```b4x
WriteLong(NodeIdentifier As String, NewValue As Long)
```
Updates an active OPC UA Int64 variable node.
Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
Parameter:
- NodeIdentifier The complete NodeId string (e.g., "ns=1;s=LargeCounter").
- NewValue The signed 64-bit integer value payload to assign.

### WriteULong
```b4x
WriteULong(NodeIdentifier As String, NewValue As ULong)
```
Updates an active OPC UA UInt64 variable node.
Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
Parameter:
- NodeIdentifier The complete NodeId string (e.g., "ns=1;s=LargeUnsignedCounter").
- NewValue The unsigned 64-bit integer value payload to assign.

### WriteString
```b4x
WriteString(NodeIdentifier As String, NewValue As String)
```
Writes a String payload into an active OPC UA node.
Automatically handles string memory copying and verifies target node type data inside Namespace 1.
Parameter:
- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=1;s=Temperature").
- Data The B4R ArrayByte payload to write into the node address space.

### WriteByteString
```b4x
WriteByteString(NodeIdentifier As String, Data As Byte())
```
Writes a binary ByteString payload into an active OPC UA node.
Automatically handles string memory copying and verifies target node type data inside Namespace 1.
Parameter:
- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=1;s=Temperature").
- Data The B4R ArrayByte payload to write into the node address space.

---

## Read Nodes

```b4x
ReadNumeric(NodeIdentifier As String)
```
Reads a node value using a numeric identifier.
Dynamically converts scalars (Integers, Booleans, Floats, Doubles, Strings, and Datetimes) into a generic string representation.
Standard OPC UA DateTime structures are automatically converted and formatted into a universal UTC Zulu timestamp string.
Parameter:
- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=0;i=2258" for the server time).
Return: 
- The scalar node value formatted as a B4R String.
	
```b4x
ReadString(NodeIdentifier A String)
```
Reads a node value using a string identifier.
Dynamically converts scalars (Integers, Booleans, Floats, Doubles, Strings, and Datetimes) into a generic string representation.
Standard OPC UA DateTime structures are automatically converted and formatted into a universal UTC Zulu timestamp string.
Parameter:
- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=1;s=Temperature").
Return: 
- B4RString pointer containing the text-formatted value payload.

```b4x
ReadByteString(NodeIdentifier A String) As Byte()
```
Reads a binary ByteString value using a string identifier.
Parameter:
- NodeIdentifier The targeting string namespace (ns) and the nodestring (s) (e.g., "ns=1;s=DeviceData").
Return: 
- Array As Byte containing the binary ByteString payload.

---

## Getter/Setter
```b4x
IsReady As Boolean (Property Getter)
```
Set or get whether the server is running.
Return:
- Returns `True` if the background FreeRTOS network task on Core 0 has successfully initialized the configurations, created root folder structures, and bound the TCP sockets.

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
			
