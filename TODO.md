# TODO

## Final Testing
The API is freezed and complete final testing before releasing as v1.0.0. 
**Testscenarios**
| Prio | Goal                           | Remarks                                           | Clients          | Example       | Result        |
|------|--------------------------------|---------------------------------------------------|------------------|---------------|---------------|
| H    | ByteString round-trip          |                                                   | B4J PyBridge     |               | ToDo          |
| H    | Callbacks Trigger,Method,Write |                                                   | B4J PyBridge, NR | 12-MethodCall | OK            |
| H    | Disconnect/reconnect           | Remove MCU power > wait > power up again.         | NR               | 20-DHT22      | OK            |
| H    | Larger realistic flat node set | Two Tank simulator with 25+ nodes.                | NR, Fuxa         | 24-TankSim    | OK            |
| H    | 12–24 h soak test              | Monitoring with node `ns=1;s=System.AvailableRAM`.| HA               | 20-DHT22      | OK            |
| M    | Namespace 1 / Namespace 2      | `ns=2` deliberately not used in API examples      |                  |               | Not required  |
| M    | Multiple simultaneous clients  |                                                   | HA, NR, OC       | 20-DHT22      | OK            |

*Legend:* MCU = ESP32S3-N16R8; H = High, M = Medium, L = Low; HA = Home Assistant; NR = Node-RED; OC = opcua-commander

#### Status
The current B4R API has been tested, documented, and is stable enough that existing client applications should not need API changes.

## Additional Examples
* Communication between B4R and B4J using the B4R Serializator - methods `WriteByteString` and `ReadByteString` as added in v0.90.
#### Status
In progress.

## Documentation Updates
Enhance the documentation (markdown format) in the repository [docs](http://github.com/rwbl/rOpen62541/tree/main/docs) folder.
Create README.md for every example.
#### Status
In progress.

## B4A Client
Test B4A client using the [SS_OPCUAClient](https://www.b4x.com/android/forum/threads/b4x-b4j-b4a-opc-ua-industrial-client-library-connect-to-servers-devices.171977/#content) library.
#### Status
Not started.
