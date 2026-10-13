# PyOpcUaClient --- B4J Class Module

## 1. Overview

`PyOpcUaClient` is a B4J class module that provides an OPC UA client interface through **PyBridge** and Python's `asyncua` package. 
It manages the client connection, reads and writes node values, creates data-change subscriptions, invokes OPC UA methods, and forwards selected
events from Python to a B4J target module.

`PyOpcUaClient` using the **PyBridge** handles the essential operations:
- OPC UA connection and disconnection
- Reading and writing values
- Subscriptions and data-change events
- Browsing nodes
- Calling OPC UA methods
- ByteString read/write and subscription notifications
- Connection-loss checking and process cleanup

The Python client implementation is injected into the PyBridge engine when the class is initialized.   
The Python code uses `asyncua.sync.Client`, so the OPC UA service operations exposed here are called through the synchronous client API.

The class uses a producer-consumer event queue:
1.  Python callbacks or operations append event dictionaries to `global_event_queue`.
2.  B4J calls `PollNextEvent` to retrieve the next queued event.
3.  The application passes the event name and value to `RaiseB4jEvent`.
4.  `RaiseB4jEvent` translates the event into a callback on the configured B4J target module.

**Important:** The class module defines `PollNextEvent`, but the application must arrange for it to be called regularly and pass returned
events to `RaiseB4jEvent`. The class source shown does not start this polling loop itself.

## 2. Requirements and dependencies

-   [B4J](https://www.b4x.com/b4j.html).
-   [PyBridge](https://www.b4x.com/android/forum/threads/pybridge-the-very-basics.165654/) Framework, with a `PyBridge` engine instance supplied to
    `Initialize`.
-   Python with the `asyncua` package available to that PyBridge environment.
-   An OPC UA server reachable from the Python runtime.
-   The server's node IDs and data types must match the IDs and types used by the application.

The injected Python code imports `logging`, `json`, `ua` from `asyncua`, and `Client` from `asyncua.sync`.

## 3. Initialization

``` vb
Private Opc As PyOpcUaClient

Sub AppStart (Form1 As Form)
    Opc.Initialize(Me, "Opc", Py)
End Sub
```

The example assumes that `Py` is an already initialized `PyBridge` instance and that the class module is available to the project.

### `Initialize(TargetModule, EventName, PyBridgeEngine)`

Initializes the class and injects its Python implementation into the
PyBridge engine.
```
-----------------------------------------------------------------------
Parameter               Type                    Description
----------------------- ----------------------- -----------------------
`TargetModule`          `Object`                B4J module that
											  receives callbacks.

`EventName`             `String`                Prefix used to
											  construct callback
											  subroutine names.

`PyBridgeEngine`        `PyBridge`              Existing PyBridge
											  engine instance.
-----------------------------------------------------------------------
```

With `EventName = "Opc"`, callback names include
`Opc_ConnectionChanged`, `Opc_DataChanged`, and `Opc_ReadResult`.

## 4. Connection management

### `Connect(ServerIp, Port)`

Requests a connection to the OPC UA server at
`opc.tcp://<ServerIp>:<Port>`.

``` vb
Opc.Connect("192.168.1.50", 4840)
```

The Python code creates an `OpcSyncWorker`, calls `connect_server`, and queues an `opc_connection_changed` event with `True` or `False`.

**Implementation note:** `Connect` does not expose the Python return value as a B4J result. Use the connection-change callback and/or
`CheckConnection` to determine the reported connection state.

### `Disconnect`

Disconnects the current worker if one exists.  
The Python implementation attempts to delete the master subscription and disconnect the client, clears the event queue, and then queues a connection-change event with
`False`.

``` vb
Opc.Disconnect
```

### `CheckConnection`

``` vb
Wait For (Opc.CheckConnection) Complete (ConnectedNow As Boolean)
Log(ConnectedNow)
```

Checks whether the worker reports itself connected and then performs a
lightweight OPC UA request by reading the browse name of the standard
Server object (`i=2258`). If the request raises an exception, the Python
worker marks the connection as disconnected, queues a connection-change
event with `False`, and returns `False`.

This is a `ResumableSub`; call it using `Wait For`.

### `Connected` property

``` vb
If Opc.Connected Then
    Log("Connection is reported as active")
End If
```

Read-only property returning the B4J-side `IsConnected` flag. This flag
is updated by `CheckConnection` and by the connection-change event
dispatcher. It is the class's last reported state, not an independent
live network test.

## 5. Node IDs and predefined constants

The class exposes these public node ID constants:

  --------------------------------------------------------------------------
  Constant                  Value                    Purpose
  ------------------------- ------------------------ -----------------------
  `NODEID_ROOT_OBJECTS`     `ns=0;i=85`              Standard OPC UA Objects
                                                     folder.

  `NODEID_FACTORY_FLOOR`    `ns=1;s=Factory_Floor`   Application-specific
                                                     parent object used by
                                                     method calls.

  `NODEID_TRIGGER`          `ns=1;s=Trigger`         Application-specific
                                                     trigger node.

  `NODEID_TRIGGER_RESULT`   `ns=1;s=TriggerResult`   Application-specific
                                                     trigger-result node.
  --------------------------------------------------------------------------

These are constants supplied by this class; the application must ensure
that the corresponding custom nodes exist on its server. `CallMethod`
currently uses `ns=1;s=Factory_Floor` as the parent object directly in
the Python implementation.

## 6. Reading and writing values

The read/write methods accept node IDs as strings. The write methods
also accept values as strings and convert them in Python where
appropriate.

### `Read(NodeID)`

Requests a read of the node's value.

``` vb
Opc.Read("ns=1;s=Temperature")
```

The Python method reads the node value and queues an `opc_read_result`
event. The event payload is formatted as:

``` text
NodeID#Value
```

For ordinary scalar values, the value is converted to text with `str()`.
The code also contains special handling for the standard ServerStatus
node ID `ns=0;i=2256`, serializing selected status/build information as
JSON.

The B4J dispatcher splits this payload at `#` and calls the
`_ReadResult` callback with the node ID and value text.

### `WriteFloat(NodeID, Value)`

Writes a value as an OPC UA `Float` (`Float32`).

``` vb
Opc.WriteFloat("ns=1;s=TemperatureSetpoint", "35.0")
```

Python converts the value string using `float()` and wraps it in
`ua.VariantType.Float`.

### `WriteInt(NodeID, Value)`

Writes a value as an OPC UA `Int32`.

``` vb
Opc.WriteInt("ns=1;s=Counter", "35")
```

Python converts the value string using `int()` and wraps it in
`ua.VariantType.Int32`.

### `WriteBoolean(NodeID, Value)`

Writes an OPC UA Boolean. The Python implementation trims and lowercases
the input string. It interprets `"true"`, `"1"`, `"on"`, and `"yes"` as
`True`; other strings become `False`.

``` vb
Opc.WriteBoolean("ns=1;s=PumpRunning", "true")
```

### `WriteTrigger(NodeID, CommandValue)`

Writes a string value to the specified trigger node using an OPC UA
String variant. The source comments describe `ns=1;s=Trigger` as the
command path to the OPC UA server.

``` vb
Opc.WriteTrigger(Opc.NODEID_TRIGGER, "START")
```

The actual command format is application-specific. Confirm that the
server-side trigger handler expects the supplied string.

### Return values for read/write calls

These methods return a `PyWrapper` from `Py.RunCode`. The Python methods
generally do not return a structured success result: when disconnected
they return early, and exceptions are logged in Python. Do not assume
the returned wrapper always contains a Boolean success/failure value.
Check the project's PyBridge behavior and the Python log output when
handling failures.

## 7. Subscriptions

### `Subscribe(NodeId)`

Subscribes to data changes for the specified node.

``` vb
Opc.Subscribe("ns=1;s=LedState")
```

The Python worker creates one subscription object (`master_sub`) the
first time it is needed, with a requested publishing interval of `100`
milliseconds, and reuses it for later node subscriptions.

On a data change, `SyncSubHandler.datachange_notification` appends an
`opc_datachange` event with this text payload:

``` text
NodeID Value
```

The B4J dispatcher splits the payload on spaces and forwards the node ID
and value as strings.

**Payload limitation:** Because the current event format uses a space as
the separator, it is not a general-purpose serialization format for
arbitrary string values containing spaces. The current B4J parser also
takes the first two split parts as the node ID and value.

## 8. Calling OPC UA methods

### `CallMethod(NodeId, Argument)`

Calls a method on the remote server.

``` vb
Opc.CallMethod("ns=1;s=ResetCounter", "RESET")
```

The Python implementation:

1.  Uses `ns=1;s=Factory_Floor` as the parent object.
2.  Resolves the supplied method node ID.
3.  Encodes the argument string as UTF-8 bytes.
4.  Sends it as an OPC UA `ByteString` variant.
5.  Calls the method and queues an `opc_call_method` event containing
    the returned response.

The server method must be located under the parent object expected by
the implementation and must accept a compatible `ByteString` input. The
exact argument and result semantics are defined by the server method.

## 9. Event polling and callbacks

### `PollNextEvent`

Asynchronously retrieves the next event from the Python event queue.

``` vb
Wait For (Opc.PollNextEvent) Complete (EventData As Object)
```

The Python function removes and returns the first queue item, or returns
`None` when the queue is empty. A returned event is expected to be a
map-like object containing `event` and `value` entries.

The class module does not include a complete polling loop. The
application should poll regularly, check whether a result is present,
extract its event name and value according to the PyBridge wrapper's
actual return representation, and pass them to `RaiseB4jEvent`.

### `RaiseB4jEvent(EventName, Value)`

Dispatches a Python event to the matching B4J callback. It is normally
called by the application's polling loop.

  --------------------------------------------------------------------------
  Internal event name        B4J callback suffix     Callback arguments
  -------------------------- ----------------------- -----------------------
  `opc_connection_changed`   `_ConnectionChanged`    `Boolean` connection
                                                     state

  `opc_datachange`           `_DataChanged`          `NodeID As String`,
                                                     `NewValue As String`

  `opc_browse_item`          `_BrowseItemFound`      `ItemMap As Map` with
                                                     `NodeId`,
                                                     `DisplayName`,
                                                     `NodeClass`

  `opc_call_method`          `_MethodCalled`         Returned method
                                                     response
                                                     (`Value As Object`)

  `opc_read_result`          `_ReadResult`           `NodeID As String`,
                                                     `NewValue As String`
  --------------------------------------------------------------------------

For example, with `EventName = "Opc"`, the target module can implement:

``` vb
Sub Opc_ConnectionChanged(ConnectedNow As Boolean)
    Log($"OPC UA connected: ${ConnectedNow}"$)
End Sub

Sub Opc_DataChanged(NodeID As String, NewValue As String)
    Log($"Data changed: ${NodeID} = ${NewValue}"$)
End Sub

Sub Opc_ReadResult(NodeID As String, NewValue As String)
    Log($"Read result: ${NodeID} = ${NewValue}"$)
End Sub

Sub Opc_MethodCalled(Result As Object)
    Log($"Method result: ${Result}"$)
End Sub

Sub Opc_BrowseItemFound(Item As Map)
    Log(Item)
End Sub
```

Only callbacks that exist on the target module are invoked for the event
types guarded by `xui.SubExists`.

## 10. Browse(StartNodeId)
Browses the immediate child nodes of the specified starting node and queues an opc_browse_item event for each discovered child.
This method browses immediate children only, not the complete descendant hierarchy recursively.  

Example:
```
Opc.Browse(Opc.NODEID_FACTORY_FLOOR)
```

```
-----------------------------------------------------------------------
Parameter	Type	Description
StartNodeId	String	Node ID of the starting object to browse, e.g. ns=1;s=Factory_Floor.
-----------------------------------------------------------------------
```

For each child node, the Python worker retrieves its Node ID, display name and node class, then queues an event using this payload format:
```
NodeID|DisplayName|NodeClass
```
The B4J event dispatcher converts the payload into a Map containing:
- NodeId — the child's OPC UA node ID.
- DisplayName — the display name.
- NodeClass — Variable or Object.
The result is delivered through the BrowseItemFound callback:
```
Sub Opc_BrowseItemFound(Item As Map)
    Log($"NodeID: ${Item.Get("NodeId")}"$)
    Log($"Name: ${Item.Get("DisplayName")}"$)
    Log($"Class: ${Item.Get("NodeClass")}"$)
End Sub
```
With EventName = "Opc", the callback name is Opc_BrowseItemFound.
Implementation detail: Browse starts the Python operation but does not return the discovered nodes directly. The application receives them asynchronously through the event queue, so its polling loop must continue running.
The B4J log confirms the expected format:
```
[PyBridge Browser] Found 1 children under ns=1;s=Factory_Floor
[PyOpcUaClient] RaiseB4jEvent name=opc_browse_item, value=ns=1;s=ByteString|ByteString|Variable
```

## 11. Internal design

### Python worker

`OpcSyncWorker` owns the `asyncua.sync.Client` instance and provides
methods for connection management, node reads/writes, subscriptions,
browsing, and method invocation.

### Event queue

Python appends dictionaries with `event` and `value` fields to
`global_event_queue`. B4J consumes entries through `PollNextEvent` and
dispatches them through `RaiseB4jEvent`.

### Logging

The Python code prints connection, subscription, read/write, browse, and
method-call diagnostics. It also sets the `asyncua` logger level to
`CRITICAL`, with a source comment explaining that this suppresses
connection-error output from that logger. The worker's own messages are
printed separately.

## 12. Error handling and operational considerations

-   **Connection failures:** `connect_server` catches exceptions, sets
    its connection flag to false, and queues a false connection event.
-   **Connection loss:** `check_connection` tests the connection by
    issuing an OPC UA request. On failure, it reports the connection as
    false.
-   **Read/write/subscription/method errors:** Most Python worker
    methods catch exceptions and print diagnostics. Several return early
    when disconnected rather than returning a structured error object.
-   **Polling is application-managed:** If the application does not poll
    and dispatch events, queued notifications will not reach the B4J
    callback subs.
-   **Queue growth:** The queue is a Python list with no explicit size
    limit in this source. If events are produced faster than the
    application consumes them, the queue may grow.
-   **Payload delimiters:** Data-change and read-result events use text
    delimiters (`` and `#`). Browse events use `|`. These formats can be
    ambiguous if values contain the same delimiters.
-   **Fixed method parent:** Method calls always use
    `ns=1;s=Factory_Floor` as the parent object in the current
    implementation.
-   **Node IDs and data types:** The node IDs and explicit OPC UA
    variant types must match the target server's address space and
    method signatures.

## 13. Public API reference

  -------------------------------------------------------------------------------------------------------
  Member                                                  Kind                    Description
  ------------------------------------------------------- ----------------------- -----------------------
  `Initialize(TargetModule, EventName, PyBridgeEngine)`   Sub                     Initializes the class
                                                                                  and injects Python
                                                                                  code.

  `Connect(ServerIp, Port)`                               Sub                     Connects to an OPC UA
                                                                                  server.

  `Disconnect`                                            Sub                     Disconnects the worker
                                                                                  and clears queued
                                                                                  events.

  `CheckConnection`                                       Resumable Sub           Tests the current
                                                                                  connection and returns
                                                                                  a Boolean result.

  `Connected`                                             Read-only property      Returns the last
                                                                                  reported B4J-side
                                                                                  connection state.

  `Subscribe(NodeId)`                                     Sub                     Subscribes to data
                                                                                  changes.

  `Read(NodeID)`                                          Sub                     Reads a node value and
                                                                                  queues a read-result
                                                                                  event.

  `WriteFloat(NodeID, Value)`                             Sub                     Writes an OPC UA Float.

  `WriteInt(NodeID, Value)`                               Sub                     Writes an OPC UA Int32.

  `WriteBoolean(NodeID, Value)`                           Sub                     Writes an OPC UA
                                                                                  Boolean.

  `WriteTrigger(NodeID, CommandValue)`                    Sub                     Writes an OPC UA String
                                                                                  to a trigger node.

  `CallMethod(NodeId, Argument)`                          Sub                     Calls a method with a
                                                                                  ByteString argument.

  `PollNextEvent`                                         Resumable Sub           Retrieves the next
                                                                                  queued event.

  `RaiseB4jEvent(EventName, Value)`                       Sub                     Dispatches an event to
                                                                                  the target module.
  -------------------------------------------------------------------------------------------------------

The write/read/subscribe/method wrappers that declare `As PyWrapper`
return the result of `Py.RunCode`; their actual values depend on the
injected Python function and PyBridge's wrapper behavior.

## 14. Notes for a future revision

The following items are observations from the supplied source, not
claims that the class is currently broken:

1.  Consider adding a public B4J wrapper for `browse_and_sync_nodes`.
2.  Consider using a structured payload (for example, a map/dictionary
    or JSON) rather than delimiter-separated strings for data-change and
    read-result events.
3.  Consider returning explicit status values from Python worker methods
    so the B4J caller can distinguish success, disconnected state, and
    exceptions.
4.  Document or implement the application's event-polling loop alongside
    the class API.
5.  If multiple subscriptions or high event rates are expected, consider
    queue synchronization and bounded-queue behavior.

------------------------------------------------------------------------

*Documentation generated from the supplied `PyOpcUaClient.bas` source.
Behaviors described as current implementation details reflect that source version (`v20261012`).*
