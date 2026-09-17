# Functions Reference

This document provides a comprehensive API reference list for the **rOpen62541** library classes, tracking types, and data-routing methods.

---

## Core Engine Methods

### Initialize
```b4x
Initialize (Port As Int, LocalIP As String, Username As String, Password As String, MethodTriggerSub As Object)
```
Initializes the OPC UA Server core engine, establishes the listening network port, boots the underlying server background runtime loop on Core 0, and hooks your B4R callback routine.

### IsReady
```b4x
IsReady As Boolean (Property Getter)
```
Returns `True` if the background FreeRTOS network task on Core 0 has successfully initialized the configurations, created root folder structures, and bound the TCP sockets.

---

## Address Space Node Creation

### AddStringNode
```b4x
AddStringNode (NodeIdentifier As String, DisplayName As String, InitialValue As String)
```
Dynamically instantiates a unique string-identified OPC UA variable node in the main `"Factory_Floor"` parent directory. If the `NodeIdentifier` matches exactly `"Trigger"`, the library attaches a native C++ write-callback interceptor to capture network payloads.

### AddFloatNode
```b4x
AddFloatNode (NodeIdentifier As String, DisplayName As String, InitialValue As Float)
```
Dynamically instantiates a unique string-identified floating-point variable node attached to the primary tree registry.

### AddIntNode
```b4x
AddIntNode (NodeIdentifier As String, DisplayName As String, InitialValue As Int)
```
Dynamically instantiates a unique string-identified 32-bit signed integer variable node attached to the primary tree registry.

### AddByteStringNode
```b4x
AddByteStringNode (NodeIdentifier As String, DisplayName As String, InitialBytes() As Byte)
```
Dynamically allocates a new raw `ByteString` variable node inside the Factory Floor folder. Perfect for transferring `B4RSerializator` binary buffers or plain byte sets.

### AddBooleanNode
```b4x
AddBooleanNode (NodeIdentifier As String, DisplayName As String, InitialValue As Boolean)
```
Dynamically instantiates a unique string-identified OPC UA variable node in the main `"Factory_Floor"` parent directory. Configured using the native `UA_TYPES_BOOLEAN` primitive format, this node is ideal for publishing raw system states or driving hardware switching configurations like output relays.

---

## Remote Procedure Call (RPC) Methods

### AddMethodNode
```b4x
AddMethodNode (MethodName As String, DisplayName As String, MethodCallSub As Object)
```
Dynamically instantiates a unique string-identified executable RPC Method node inside the main `"Factory_Floor"` parent directory. It configures a single universal input argument parameter slot (`ByteString` layout) and a single `INT32` output verification parameter slot, anchoring your dedicated B4R execution callback subroutine entry pointer.

### SetMethodReturnCode
```b4x
SetMethodReturnCode (Code As Int)
```
Sets the integer execution status return token code for the currently processed network method invocation frame. This function **must** be executed inside your B4R method callback subroutine to send an atomic confirmation value (e.g., `100` for success or `400` for failure) back across the network socket layer to the client application.

---

## Thread-Safe Node Live Updates

### UpdateNodeValue (Boolean)
```b4x
UpdateNodeValue (NodeIdentifier As String, NewValue As Boolean)
```
An overloaded variation of the thread-safe update engine. It intercepts your B4R boolean statuses, locks the cross-core FreeRTOS semaphore, verifies if the target node registry matches the boolean data signature, and pushes the binary update straight out to your connected SCADA monitors.

### UpdateNodeValue (Double)
```b4x
UpdateNodeValue (NodeIdentifier As String, NewValue As Double)
```
A type-agnostic, thread-safe method using dynamic variant level checks to safely access server variables across cores.
