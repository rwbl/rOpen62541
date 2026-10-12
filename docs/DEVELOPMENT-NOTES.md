# Developer & Technical Architecture Notes

This document captures the low-level development configurations, compile environments, and architectural decisions behind the **rOpen62541** B4R library wrapper stack.

---

## Core Architecture & Execution Layout

The library features the following low-level optimization layers to bridge the B4R environment with the underlying protocol stack:

* **Abstraction Layer:** Provides a high-level B4R abstraction layer for the native C-based open62541 library engine.
* **Core 0 Offloading:** Offloads heavy TCP/IP layers and subscription socket polling entirely to **ESP32 Core 0 (Network Core)** using FreeRTOS tasks to guarantee zero timing jitter on your hardware loops.
* **Core 1 Isolation:** Keeps **ESP32 Core 1 (B4R Core)** completely fluid and responsive for low-level critical hardware execution, physical interrupts, and timing loops.
* **Memory Protection:** Implements a strict FreeRTOS binary semaphore mutex (`open62541Mutex`) preventing data collisions or memory corruption during concurrent memory read/write cycles.
* **Universal Interceptor:** Exposes a universal, type-agnostic string node interceptor payload framework capable of catching incoming String, Int, or Float writes natively over a robust B4R `Byte()` array block.

---

## Hard- and Software

### Software
This B4R rOpen62541 library is an [open62541](https://open62541.org) protocol stack wrapper using Git-Revision **v1.2-rc1-20-g78a6721b-dirty**.
The [open62541-121-esp32](https://github.com/cmbahadir) opcua-esp32 have been used to obtain the single-file-release **open62541.h** and **open62541.c**.
It is meant for local subnet networks (LAN/WLAN) where no external internet router firewall ports need to be exposed.

Written in C++ using the Arduino IDE 2.3.10+ with the Espressif ESP32 Arduino Core V3.x.

The standard `B4Rh2xml` parsing tool used to verify the C++ wrapped code and to create the XML documentation **rOpen62541.xml** located in the B4R additional libraries folder.

[B4R](https://www.b4x.com/b4r.html) 4.00 (64-bit) for compiling, testing and creating several examples.

> [!TIP]
> **Toolchain Version Lock:** As shown in the compile logs, ensure your Arduino Board Manager is running **Espressif Core v3.x (e.g., 3.3.11)**. Older 2.x cores use an outdated lwIP socket abstraction layer that will fail to link against the open62541 network engine loop task.

### Hardware
The B4R library is developed and strictly tested with an **ESP32-S3-N16R8** developer kit (32-bit Xtensa lx7 dual-core chip with 16MB Flash and 8MB PSRAM). 
Due to memory allocation sizes and dual-core constraints, utilizing this specific hardware class is highly recommended or mandatory.
Using an ESP32-Wrover Kit results in memory issues.

---

## Compiling
In the B4R IDE the board settings are:
* **Board Type:** ESP32S3 Dev Module
* **BaudRate:** 115200
* **Partition Scheme:** Huge App
* **PSRAM:** OPI PSRAM

Especially the `PSRAM` setting is important.

The detailed board settings is shown in the B4R compile log (see next).

### B4R Log Snippet
```text
cli: compile -b esp32:esp32:esp32s3 C:\Daten\b4\b4r\LIBRAR~1\ROPEN6~1\examples\14-INO~1\B4R\Objects\src\src.ino -v  --board-options UploadSpeed=921600,USBMode=hwcdc,CDCOnBoot=default,MSCOnBoot=default,DFUOnBoot=default,UploadMode=default,CPUFreq=240,FlashMode=qio,FlashSize=4M,PartitionScheme=huge_app,DebugLevel=none,PSRAM=opi,LoopCore=1,EventsCore=1,EraseFlash=none,JTAGAdapter=default,ZigbeeMode=default
FQBN: esp32:esp32:esp32s3:PartitionScheme=huge_app,PSRAM=opi
Using board 'esp32s3' from platform in folder: ...\AppData\Local\Arduino15\packages\esp32\hardware\esp32\3.3.11
Using core 'esp32' from platform in folder: ...\AppData\Local\Arduino15\packages\esp32\hardware\esp32\3.3.11
esptool v5.3.1
ResolveLibrary(WiFi.h) -> candidates: [WiFi@3.3.11]
ResolveLibrary(Network.h) -> candidates: [Networking@3.3.11]
Chip type:          ESP32-S3 (QFN56) (revision v0.2)
Features:           Wi-Fi, BT 5 (LE), Dual Core + LP Core, 240MHz, Embedded PSRAM 8MB (AP_3v3)
Crystal frequency:  40MHz
MAC:                14:c1:9f:42:b6:2c
Wrote 1121152 bytes (713088 compressed) at 0x00010000 in 12.0 seconds (749.9 kbit/s).
New upload port: COM15 (serial)
ESP-ROM:esp32s3-20210327
Build:Mar 27 2021
rst:0x1 (POWERON),boot:0x8 (SPI_FAST_FLASH_BOOT)
[AppStart] rOpen62541 InOutput v20260918
[AppStart] WiFi connected > local ip=192.168.1.175
[Hardware Engine] Wi-Fi Modem-Sleep forcefully disabled! Radio set to high-performance mode.
[AppStart] Init opc server
[InitOpcServer] Initializing...
[InitOpcServer] Awaiting background core network initialization...
[opcuaServerTask] OPC UA Security: Anonymous Access Mode Active!
[opcuaServerTask] OPC UA Server successfully running on Core 0!
[InitOpcServer] Core 0 online! Spawning dynamic address space nodes...
[AppStart] Opc server started
```

---

## Architectural Version Selection: Why open62541 v1.2?

This library explicitly uses the **open62541 v1.2 legacy branch (v1.2-rc1-20-g78a6721b-dirty)** instead of v1.3+ or v1.5+ release lines.  
While modern versions introduce advanced enterprise desktop configurations, version 1.2 is carefully selected for the following critical engineering reasons:

* **Embedded-First Resource footprint:** Version 1.2 compiles into a highly lightweight binary footprint. Newer versions contain massive auto-generated internal structures (such as updated Namespace 0 trees) that routinely hit compiler variable-tracking limits, causing the Xtensa compiler toolchain to freeze, link-crash, or hang the B4R IDE.
* **Native lwIP Connection Abstraction:** The network socket management layer in v1.2 seamlessly adapts to the ESP32’s native embedded FreeRTOS/lwIP stack out of the box. Newer versions introduce rigid desktop POSIX dependencies (such as `<poll.h>` and complex desktop mutex types) that create structural friction on microcontrollers.
* **Streamlined Property Configuration:** Version 1.2 exposes clean, low-level configuration functions like `UA_ServerConfig_setCustomHostname()`. Later versions completely refactor these into complex, deeply nested configuration allocation macros that are difficult to manage within an object-oriented B4R C++ wrapper interface.
* **Perfect Functional Match:** The v1.2 branch provides 100% of the industrial protocol features required for this proof of concept (including dynamic float, integer, string, and raw binary ByteString node arrays) without any unnecessary software bloat.

---

## Which Core to Choose? (Core 0 vs Core 1)
* Core 0 handles the ESP32 network stack (Wi-Fi, TCP/IP, Bluetooth).
* Core 1 is typically where the main B4R environment and your custom B4R code execute.

Since `open62541` is a network-heavy service, running it on Core 0 is often highly advantageous. It localizes network processing to the same core handling the radio, reducing core-to-core overhead and keeping Core 1 completely free to run the B4R application logic smoothly.

---

## OPC UA Server Notes

### Address Space
The `Factory_Floor` folder will appear in the address space tree. This is defined in the function `buildOpcUaTree`. The `OPCUA CLI-Client example` screenshot shows nicely the `Address Space` with `RootFolder` and the objects for `EnvSim` example.

![InOut](images/opcua-cli-client-example.png)

### Server Mode
In OPC UA, the server is in passive mode. It does not actively push data to clients when they connect. Instead, the server maintains an internal Address Space (a tree of Nodes, like sensor data Temperature, Humidity, actor states or a Counter).

### Data Flow Example
B4R with an BMP280 connected.

```text
{ B4R Application }
       │  (Reads BMP280 every 5 seconds)
       ▼
   OpcUa.UpdateFloatNode("Temperature", 24.5)
       │  (Overwrites the data inside the Server's memory)
       ▼
┌──────────────────────────────────────────────┐
│            ESP32 OPC UA SERVER               │
│                                              │
│   Address Space Memory:                      │
│   └── Factory_Floor                          │
│        └── Temperature: [ 24.5 ] ◄───────────┼── (Updated value sits here)
└──────────────────────┬───────────────────────┘
                       ▲
                       │ (Client connects and asks)
                       │ "Give me the value of 'Temperature'"
                       ▼
             { OPC UA SCADA Client }
```

#### Step-by-Step Breakdown
1. B4R reads the BMP280 every 5 seconds.
2. B4R pushes the new value into the server using the wrapper method:
   * `OpcUa.UpdateFloatNode("Temperature", CurrentTemp)`
   * This updates the local memory variable inside the ESP32 server. It does not send anything to the network yet.
3. An OPC UA Client connects to your ESP32.
4. The Client reads the data: 
   * The client asks the server for the current value of the Temperature node. 
   * The server reads its own internal memory and replies back to the client with `24.5`.
