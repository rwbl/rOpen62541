# TODO

## Final Testing
The API is freezed and complete final testing before releasing as v1.0.0. 
**Testscenarios***
| Priority | Test Goal                      | Test                                                       | Example       | Result        |
|----------|--------------------------------|------------------------------------------------------------|---------------|---------------|
| H        | ByteString round-trip          |                                                            |               | ToDo          |
| H        | Method callback + return code  | B4J client.                                                | 12-MethodCall | OK            |
| H        | Disconnect/reconnect           | NR client: 1. NR stopped/started; 2. ESP32 restarted.      | 20-DHT22      | OK            |
| H        | 12–24 h soak test              | HA client. Added node `ns=1;s=System.AvailableRAM`.        | 20-DHT22      | OK            |
| M        | Namespace 1 / Namespace 2      | Namespace 2 deliberately not used in current API examples. |               | Not required  |
| M        | Multiple simultaneous clients  | HA, NR and opcua-commander connected simultaneously.       | 20-DHT22      | OK            |
| L        | Larger realistic flat node set | Two Tank simulator with 21 nodes (including soak test).    | 24-TankSim    | In progress   |

*Legend:* H = High, M = Medium, L = Low; HA = Home Assistant; NR = Node-RED.

### Status
The current B4R API has been tested, documented, and is stable enough that existing client applications should not need API changes.

## More Examples & Improve Documentation
* README.md for every example.
* Communication between B4R and B4J using the B4R Serializator - methods `WriteByteString` and `ReadByteString` as added in v0.90.
* Tank Simulator.
### Status
In progress.

## Documentation Updates
Enhance the documentation (markdown format) in the repository [docs](http://github.com/rwbl/rOpen62541/tree/main/docs) folder.
### Status
In progress.

## B4A Client
Test B4A client using the [SS_OPCUAClient](https://www.b4x.com/android/forum/threads/b4x-b4j-b4a-opc-ua-industrial-client-library-connect-to-servers-devices.171977/#content) library.
### Status
Not started.
