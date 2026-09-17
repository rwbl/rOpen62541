# rOpen62541

> [!WARNING]
> **Final Testing & Documentation In Progress**  
> Core server developments are complete and in final testing, meaning minor API changes may still occur before the formal release. Documentation and project examples are actively being written and updated.

**rOpen62541** is an open-source library wrapper for the industrial open62541 OPC UA protocol stack, specifically optimized for the ESP32-S3 Dual-Core architecture. 
It provides thread-safe cross-core communication, dynamic string-node creation, and type-agnostic runtime write diagnostics.

---

## Project Overview & Background

<details>
<summary><b>Author's Note & Personal Context (Click to expand)</b></summary>

**This library was developed purely for personal educational use**, born out of a desire to dive deep into industrial connectivity and tackle the challenging feat of wrapping the open62541 stack for [B4R](https://www.b4x.com/b4r.html). 
It wasn't easy to build, but exploring cross-platform client integration — such as [B4J](https://www.b4x.com/b4j.html) with the PyBridge and [opcua-asyncio](https://github.com/FreeOpcUa/opcua-asyncio) or [Node-RED](https://nodered.org) — and seeing the dual-core hardware spring to life made it an incredibly rewarding project. 
Moving forward, this proof-of-concept server framework will serve as a foundational wireless gateway component for the author's open-source several **MAKE projects**.
</details>

<details>
<summary><b>What is OPC UA & What is it used for? (Click to expand)</b></summary>

**OPC UA (Open Platform Communications Unified Architecture)** is a robust, platform-independent, and highly secure industrial machine-to-machine (M2M) communication protocol framework widely deployed in **Industry 4.0 / Industrial IoT (IIoT)** environments.
Unlike standard message-based IoT protocols (like MQTT), OPC UA provides a unified Address Space allowing devices to structurally expose complex object folders, variable data nodes, and custom tracking methods with rich data metadata. It is extensively used to interconnect hardware sensors, embedded controllers, PLCs, industrial SCADA systems, and high-level enterprise MES/ERP software architectures seamlessly over modern Ethernet/Wi-Fi networks.
</details>

---

## Purpose & Scope
- **Server-Only Architecture**: This library is dedicated exclusively to acting as an **OPC UA Server**. 
	- It turns the  microcontroller into a data provider but does not include client connection parsing capabilities.
- **Proof of Concept & Learning Project:** This framework was explicitly developed as a personal educational project to learn the foundational basics of OPC UA by creating a custom standalone hardware device from scratch.
- **No Professional Intent:** There is absolutely no intention for this codebase to be deployed in mission-critical environments, production facilities, or professional commercial installations.
- Provides a high-level B4R abstraction layer for the native C-based open62541 library engine.
- Offloads heavy TCP/IP layers and subscription socket polling entirely to **ESP32 Core 0 (Network Core)** using FreeRTOS tasks to guarantee zero timing jitter on your hardware loops.
- Keeps **ESP32 Core 1 (B4R Core)** completely fluid and responsive for low-level critical hardware execution, physical interrupts, and timing loops.
- Implements a strict FreeRTOS binary semaphore mutex (open62541Mutex) preventing data collisions or memory corruption during concurrent memory read/write cycles.
- Exposes a universal, type-agnostic string node interceptor payload framework capable of catching incoming String, Int, or Float writes natively over a robust B4R Byte() array block.

---

## Development Info
This B4R rOpen62541 library is:
- An [open62541](https://open62541.org) protocol stack wrapper using Git-Revision **v1.2-rc1-20-g78a6721b-dirty**.
	- The [open62541-121-esp32](https://github.com/cmbahadir) opcua-esp32 have been used to obtain the single-file-release open62541.h and open62541.c.
- Written in C++ using Arduino IDE 2.3.10+, Espressif ESP32 Arduino Core V3.x, and the standard B4Rh2xml parsing pipeline.
- Was developed and strictly tested with an **ESP32-S3-N16R8** developer kit (32-bit Xtensa lx7 dual-core chip with 16MB Flash and 8MB PSRAM). 
	- Due to memory allocation sizes and dual-core constraints, utilizing this specific hardware class is highly recommended or mandatory.
- Is meant for local subnet networks (LAN/WLAN) where no external internet router firewall ports need to be exposed.
- Tested with B4R 4.00 (64-bit).

<details>
<summary><b>Architectural Version Selection: Why open62541 v1.2? (Click to expand)</b></summary>
This library explicitly uses the open62541 v1.2 legacy branch (v1.2-rc1-20-g78a6721b-dirty) instead of v1.3+ or v1.5+ release lines.  
While modern versions introduce advanced enterprise desktop configurations, version 1.2 is carefully selected for the following critical engineering reasons:
- Embedded-First Resource footprint: Version 1.2 compiles into a highly lightweight binary footprint. Newer versions contain massive auto-generated internal structures (such as updated Namespace 0 trees) that routinely hit compiler variable-tracking limits, causing the Xtensa compiler toolchain to freeze, link-crash, or hang the B4R IDE.
- Native lwIP Connection Abstraction: The network socket management layer in v1.2 seamlessly adapts to the ESP32’s native embedded FreeRTOS/lwIP stack out of the box. Newer versions introduce rigid desktop POSIX dependencies (such as <poll.h> and complex desktop mutex types) that create structural friction on microcontrollers.
- Streamlined Property Configuration: Version 1.2 exposes clean, low-level configuration functions like UA_ServerConfig_setCustomHostname(). Later versions completely refactor these into complex, deeply nested configuration allocation macros that are difficult to manage within an object-oriented B4R C++ wrapper interface.
- Perfect Functional Match: The v1.2 branch provides 100% of the industrial protocol features required for this proof of concept (including dynamic float, integer, string, and raw binary ByteString node arrays) without any unnecessary software bloat.

</details>

---

## Compatibility & Verified Clients

### Hardware & Platform Compatibility
* **Supported Hardware:** Optimized for **Espressif ESP32-S3** high-memory microcontrollers (specifically the **N16R8** format featuring 16MB Flash and 8MB PSRAM).
* **Network Layer:** Requires standard embedded network `lwIP` socket frameworks to be initialized on boot.

### Verified OPC UA Clients
This server implementation complies strictly with core industrial data-modeling specs and has been successfully verified across multiple desktop, terminal, and automation client ecosystems:

* [**Node-RED**](https://github.com) — Fully interoperable using the standard `node-red-contrib-opcua` flow palette node module.
* [**opcua-commander**](https://github.com/node-opcua/opcua-commander) — Validated using the interactive, keyboard-driven terminal curses explorer (TUI).
* [**B4J (Native Client)**](https://b4x.com) — Smooth integration with Peter Simpson's high-level wrapper library (`SS_OPCUAClient`).
* [**B4J with PyBridge**](https://b4x.com) — Confirmed working via Python-backend socket communication routing bridges.

---

## Install
Download the ropository from [GitHub](https://github.com/rwbl/rOpen62541).
- Copy the src sub-folder **rOpen62541** into your B4R **Additional Libraries** folder, keeping the directory structure fully intact.
- Copy the src file **rOpen62541.xml** into your B4R **Additional Libraries** folder.
- The folder **examples** holds several usage examples.

---

## Examples

Includes *EnvSim*, *MethodCallback*, *InOutput*, and *NodeIDs* examples within the repository.

---

## Project Tutorials & Documentation

Refer to the repository documentation folder for detailed callback and node ID guides.

---

## Screenshot Example

![InOut](images/ropen62541-b4j-inout-pybridge.png)

---

## Functions Reference

<details>
<summary><b>Click to view the full B4R Class Methods Reference List</b></summary>

- **Initialize (Port As Int, LocalIP As String, Username As String, Password As String, MethodTriggerSub As Object)**  
Initializes the OPC UA Server core engine, establishes the listening network port, boots the underlying server background runtime loop on Core 0, and hooks your B4R callback.
- **IsReady As Boolean (Property Getter)**  
Returns True if the background FreeRTOS network task on Core 0 has successfully initialized the minimal configurations, created root folder structures, and bound the TCP sockets.
- **AddStringNode (NodeIdentifier As String, DisplayName As String, InitialValue As String)**  
Dynamically instantiates a unique string-identified OPC UA variable node in the main "Factory_Floor" parent directory. If the NodeIdentifier string parameter matches exactly "Trigger", the library attaches a native C++ write-callback interceptor to capture network write payloads.
- **AddMethodNode (MethodName As String, DisplayName As String, MethodCallSub As Object)**  
Dynamically instantiates a unique string-identified executable RPC Method node inside the main "Factory_Floor" parent directory. It configures a single universal input argument parameter slot (ByteString layout) and a single INT32 output verification parameter slot, safely anchoring your dedicated B4R execution callback subroutine entry pointer.
- **SetMethodReturnCode (Code As Int)**  
Sets the integer execution status return token code for the currently processed network method invocation frame. This function must be executed inside your B4R method callback subroutine to send an atomic confirmation value (e.g., 100 for success or 400 for failure) back across the network socket layer to the client application.
- **AddFloatNode (NodeIdentifier As String, DisplayName As String, InitialValue As Float)**  
Dynamically instantiates a unique string-identified floating-point variable node attached to the primary tree registry.
- **AddIntNode (NodeIdentifier As String, DisplayName As String, InitialValue As Int)**  
Dynamically instantiates a unique string-identified 32-bit signed integer variable node attached to the primary tree registry.
- **AddByteStringNode (NodeIdentifier As String, DisplayName As String, InitialBytes() As Byte)**  
Dynamically allocates a new raw ByteString variable node inside the Factory Floor folder. Perfect for transferring B4RSerializator binary buffers or plain byte sets.
- **AddBooleanNode (NodeIdentifier As String, DisplayName As String, InitialValue As Boolean)**  
Dynamically instantiates a unique string-identified OPC UA variable node in the main "Factory_Floor" parent directory. Configured using the native UA_TYPES_BOOLEAN primitive format, this node is ideal for publishing raw system states or driving hardware switching configurations like output relays.
- **UpdateNodeValue (NodeIdentifier As String, NewValue As Boolean)**  
An overloaded variation of the thread-safe update engine. It intercepts your B4R boolean statuses, locks the cross-core FreeRTOS semaphore, verifies if the target node registry matches the boolean data signature, and pushes the binary update straight out to your connected SCADA monitors.
- **UpdateNodeValue (NodeIdentifier As String, NewValue As Double)**  
A type-agnostic, thread-safe method using dynamic variant level checks to safely access server variables across cores.

</details>

---

## Code Example (Snippet)

<details>
<summary><b>Click to expand the full B4R Example Source Code</b></summary>

```b4x
    Private VERSION As String = "rOpen62541 EnvSim v20260913"

    ' Communication
    Public Serial1 As Serial
    Private WiFi As ESP8266WiFi	' Lib rESP8266WiFi
    Private SSID As String = "***"
    Private PW  As String = "***"
    
    ' Open62541
    Private OpcServer As Open62541	' Lib rOpen62541
    Private PORT As Int = 4840

    Private AppTimer As Timer
    Private APPTIMER_INTERVAL As ULong = 2000
    
    'Helper
    Private bc As ByteConverter    'ignore
End Sub

Sub AppStart
    Serial1.Initialize(115200)
    Log(CRLF, "[AppStart] ", VERSION)

    ' Init app timer to generate env data
    AppTimer.Initialize("AppTimer_Tick", APPTIMER_INTERVAL)
    ' Start after opc server has been initialized and nodes created
    AppTimer.Enabled = False
    
    ' Connect to the network first
    If WiFi.Connect2(SSID, PW) Then
        Log("[AppStart] WiFi connected > local ip=", WiFi.LocalIP)
        ' [AppStart] WiFi connected. IP=NNN.NNN.NNN.NNN

        ' Forces the ESP32-S3 Wi-Fi radio to stay 100% active, dropping latency
        ' from ~100ms+ down to an immediate 2ms, completely wiping out Bad_Timeout.
        RunNative("DisableWiFiSleep", Null)
                  
        Log("[AppStart] Init opc server")
        If InitOpcServer Then
            ' All good > start the app timer to update nodes
            AppTimer.Enabled = True
            Log("[AppStart] Opc server started")
        End If
    Else
        Log("[AppStart][E] WiFi Connection Failed")
    End If
End Sub

' InitOpcServer
' Steps:
' Init the server with ip, port and client callback
' Wait till the opc server has been started successfully
' Add various nodes
Private Sub InitOpcServer As Boolean
    Dim TimeoutCounter As Int = 0
    Dim ServerBootFailed As Boolean = False

    Log("[InitOpcServer] Initializing...")
    ' Spin up the Core 0 open62541 network engine with callback
    OpcServer.Initialize(WiFi.LocalIp, PORT, "", "", "OpcCallback")
    
    ' Wait for the background thread layout initialization to complete!
    Log("[InitOpcServer] Awaiting background core network initialization...")
    Do While OpcServer.IsReady = False
        Delay(100) ' 100ms yield ticks for the cooperative scheduler
        
        TimeoutCounter = TimeoutCounter + 1
        If TimeoutCounter >= 50 Then ' 50 ticks * 100ms = 5000ms (5 Seconds Timeout)
            ServerBootFailed = True
            Exit ' Break out of the endless loop safely!
        End If
    Loop
    
    If ServerBootFailed Then
        Log("[InitOpcServer][E] OPC UA Server initialization TIMEOUT! Core 0 failed.")
        ' Optional: Run local emergency fallback routine or let local sensors run offline
    Else
        Log("[InitOpcServer] Core 0 online! Spawning dynamic address space nodes...")
        ' Add nodes holding data
        OpcServer.AddFloatNode("Temperature", "Room Temperature", 20.0)
        OpcServer.AddFloatNode("Humidity", "Room Humidity", 68.0)
        OpcServer.AddIntNode("Counter", "Total Shift Cycle Count", 0)

        ' Allocate a local test array buffer: 0x19, 0x02, 0x03, 0x04, 0x58
        Dim RawBuffer() As Byte = Array As Byte(0x19, 0x02, 0x03, 0x04, 0x58)
    
        ' Create the standardized ByteString variable node
        OpcServer.AddByteStringNode("RawTelemetry", "Atomic Hex Package", RawBuffer)
    
        ' Add trigger received from the client and call OpcCallback
        OpcServer.AddStringNode("Trigger", "Remote Action Trigger", "0")
    End If
    Return Not(ServerBootFailed)
End Sub

Sub AppStart
    ' Core loop logic
End Sub

Sub AppTimer_Tick
    ' Only write data if the background server task on Core 0 is fully ready
    If OpcServer.IsReady Then
        
        ' Read real sensor here:
        ' Dim CurrentTemp As Float = BMP.ReadTemperature
        Dim CurrentTemp As Float = 24.5 + Rnd(-2.0, 3.0)
        Dim CurrentHum As Float = 68 + Rnd(-10.0, 11.0)
        
        ' PUSH DATA INTO THE NODE CONTAINER
        OpcServer.UpdateNodeValue("Temperature", CurrentTemp)       
        OpcServer.UpdateNodeValue("Humidity", CurrentHum)

        ' Log update
        Log("[AppTimer] Sensor read complete. Updated Node memory with: t=", CurrentTemp, " h=", CurrentHum)
    End If
End Sub

' OpcCallback
Private Sub OpcCallback(buffer() As Byte)
    Log("[OpcCallback] SCADA/B4J Client clicked the trigger method. command=", bc.StringFromBytes(buffer))
End Sub

#if C
#include "esp_wifi.h"

void DisableWiFiSleep(B4R::Object* o) {
    esp_wifi_set_ps(WIFI_PS_NONE);
    ::Serial.println("[Hardware Engine] Wi-Fi Modem-Sleep forcefully disabled!");
}
#End If
```

</details>

---

## Troubleshooting

- B4J Client Node Errors (`Bad_NodeIdUnknown`): Ensure your client calls use explicit string node formats using `s=` syntax (e.g., `ns=1;s=Temperature` or `ns=1;s=Trigger`). Do not look up auto-incrementing numerical configurations (`i=`).
- Node-RED Link Timeout ("invalid endpoint"): The underlying `node-opcua` JavaScript module is very strict.   
	- Ensure your target URL incorporates the complete lowercase protocol structure along with a trailing forward slash, explicitly configured like this: `opc.tcp://NNN.NNN.NNN.NNN:4840/`.  
	- Set both Security Policy and Security Mode to `None` inside your server profile pane.
- Missing Log Actions: If a client writes to the trigger node but B4R remains silent, ensure that your `AddStringNode` function block configures the callback mappings after the variable instantiation lines are executed, and verify that your B4R callback subroutine accepts a single `Buffer() As Byte` parameter.
- Console Debug Silence: Core library logs are intentionally routed to `/dev/null` at the hardware level during task setup. 
	- This completely drops pre-compiled verbose `trace/channel` and `debug/session` stdout spam to maximize hardware efficiency while leaving the explicit B4R `Log()` actions functional.

---

## License

- **rOpen62541** Library * MIT License as stated in the LICENSE file provided with rOpen62541.
- **Open62541** Library * Mozilla Public License v2.0 as stated in the LICENSE file provided with open62541.

---

## Credits

- Developers, maintainers, and open-source contributors of the official [open62541 architecture framework](https://open62541.org), providing an industrial-grade embedded C implementation of OPC UA.
- Developer of the [opcua-esp32](https://github.com) repository, which served as the foundation for this B4R wrapper.
- [Anywhere Software](https://www.b4x.com/) for the B4X suite of RAD development tools.
- Developer of the [B4J](https://b4x.com) library [SS_OPCUAClient](https://www.b4x.com/android/forum/threads/opc-ua-industrial-client-library-connect-to-servers-devices.171977/).
- AI for engineering collaboration.
---

**Disclaimer**

* All product names, logos, protocols, and brands are property of their respective owners.
- This B4R library is an independent open-source wrapper tracking standard open62541 architectures.
	- It is neither officially endorsed nor maintained by the primary open62541 project core maintainers.
- This codebase represents a strict standalone proof-of-concept / learning exercise and is explicitly **not intended for professional, commercial, or critical industrial application**.
- This wrapper, its cross-core FreeRTOS mutex mappings, and its data type interceptor routines were designed and polished with the specialized interactive assistance of an AI engineering collaborator, achieving optimal compatibility with the B4R pre-compiler stack.

---
