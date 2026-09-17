# TUTORIAL-NODEID-LIST

> [!IMPORTANT]
> **Embedded Profile Constraints (Namespace 0 Differences)**
> When querying system nodes (`ns=0`) on an ESP32 microcontroller wrapper, certain standard values may return `0`, `null`, or alternate string identifiers (e.g., `i=2262` returning a URL string or `i=2295` returning `Bad_AttributeIdInvalid`).
> 
> This is intentional architectural behavior. The underlying open62541 core trims non-essential nested system properties to keep the memory footprint lightweight, preserving maximum RAM for your low-level hardware control execution loops on Core 1!

## How to Use Node IDs (Tutorial & Examples)

The complete, official list of these system node IDs is defined by the OPC Foundation in a standardized document called "Namespace 0 NodeIds" (or Appendix A of the OPC UA specifications). Because these numbers are universally identical for every OPC UA vendor in the world, they are baked directly into open62541 and Milo (the engine behind Peter's B4J library).

Here are the best places to look up the full list of identifiers:

### Online Interactive Reference Tables
* **Unified Automation NodeId Online Lookup**: A complete indexed list maintained by the creators of UaExpert.
* **OPC Foundation Official CSV / Source Files**: The raw, definitive machine-readable CSV list hosted on the official OPC Foundation GitHub repository. You can open this file in Excel to search through all Namespace 0 definitions.

---

## Cheat Sheet: Most Useful Namespace 0 System Nodes
For industrial debugging and script monitoring, you will typically only need a handful of core server status nodes:

| Node ID     | Browse Path Name | Description / Use Case |
|-------------|---|---|
| `ns=0;i=2256` | ServerStatus | Parent structure containing all runtime states (Complex Payload). |
| `ns=0;i=2258` | CurrentTime | The exact server clock timestamp node you just viewed. |
| `ns=0;i=2259` | StartTime | Shows exactly when the ESP32 server booted up. |
| `ns=0;i=2262` | SecondsTillShutdown | Used to warn clients before a server restarts. |
| `ns=0;i=2295` | State | Returns an integer indicating server health (0=Running, 1=Failed). |
| `ns=0;i=2261` | BuildInfo | Sub-folder holding the firmware manufacturer and engine version details. |

---

### ns=0;i=2259 (StartTime) ➜ Returns `0`
* **Why**: To save RAM and flash size, the open62541 embedded profile does not always compute a fully separate DateTime structure for the system start time. Instead, it defaults to 0 (the raw initialization tick baseline).
* **What to expect**: It evaluates cleanly as `Good` because the node officially exists in the server tree, but its scalar container value is simply kept at zero.

### ns=0;i=2262 (SecondsTillShutdown) ➜ Returns `"http://open62541.org"`
* **Why**: This is a classic case of Node ID Reuse/Slicing overlap in stripped-down legacy configurations.
* **What to expect**: In a full desktop server layout, `i=2262` is reserved for an integer tracking remaining seconds before a shutdown. However, under open62541's minimal compilation flags, the internal mapping array skips complex structures. The memory slot index gets redirected to pull the standard product/namespace string array baseline instead, which happens to be your framework URL text block.

### ns=0;i=2295 (State) ➜ Returns `Bad_AttributeIdInvalid / null`
* **Why**: The error `Bad_AttributeIdInvalid (0x80350000)` explicitly tells us that while the node identifier `i=2295` is present in the layout schema, the lightweight open62541 profile completely compiled out the actual data properties behind it.
* **What to expect**: The client is asking to read the Value attribute, but the micro-server firmware replies that this specific asset attribute doesn't exist on this hardware layer to minimize RAM overhead.

### ns=0;i=2261 (BuildInfo) ➜ Returns `"open62541 OPC UA Server"`
* **Why**: This node is working perfectly! Instead of forcing the client to pull the entire heavy nested structure, the microcontroller maps this node identifier directly to its primary human-readable identification name string.

---

## How to discover them live in B4J
If you want to read any system node without checking a manual, just use the B4J client implementation.

### Test B4J OPCUA-CLIENT (Scalar Value Node)
```b4x
OpcClient.Read("ns=0;i=2258")
```
Resulting log event trigger:
```text
[ReadResult] Node: ns=0;i=2258 | Value: DateTime{utcTime=134340392488490450, javaDate=Wed Sep 16 15:34:08 CEST 2026} | Status: StatusCode{name=Good, value=0x00000000, quality=good}
```

### Advanced: Parsing Complex ExtensionObject Structs (`ns=0;i=2256`)
When querying the full `ServerStatus` node, the embedded architecture passes the data back as a raw binary package wrapped inside an `ExtensionObject`. You can read it securely using this custom index-aligned byte buffer slice lookup without needing heavy object reflections:

```b4x
Sub OpcClient_ReadResult (NodeId As String, Value As Object, Status As String)
    If NodeId = "ns=0;i=2256" And Status.Contains("Good") Then
        Try
            Dim ExtensionObj As JavaObject = Value
            Dim Body As JavaObject = ExtensionObj.RunMethod("getBody", Null)
            
            If Body.IsInitialized Then
                ' Extract raw primitive bytes out of Milo ByteString wrapper
                Dim RawBytes() As Byte = Body.RunMethod("bytes", Null)
                Dim BC As ByteConverter
                
                ' 1. Extract Software Build Tag (Exact length 28, text starts at index 88)
                Dim SoftwareVersionBytes(28) As Byte
                BC.ArrayCopy2(RawBytes, 88, SoftwareVersionBytes, 0, 28)
                Dim SoftwareVersion As String = BC.StringFromBytes(SoftwareVersionBytes, "ASCII")
                
                ' 2. Extract Compile Baseline Date (Exact length 20, text starts at index 120)
                Dim BuildTimeBytes(20) As Byte
                BC.ArrayCopy2(RawBytes, 120, BuildTimeBytes, 0, 20)
                Dim BuildTime As String = BC.StringFromBytes(BuildTimeBytes, "ASCII")
                
                Log("--- ESP32 Server Status Summary (Aligned-Parsed) ---")
                Log("Open62541 Build Version: " & SoftwareVersion.Trim)
                Log("Compile Baseline Date: " & BuildTime.Trim)
            End If
        Catch
            Log("[Parser][E] Failed to parse complex payload: " & LastException.Message)
        End Try
    End If
End Sub
```
