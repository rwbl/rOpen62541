# Troubleshooting Guide

This document lists common edge cases, error codes, and configuration requirements encountered when integrating the **rOpen62541** server library with external third-party OPC UA clients.

---

### B4J Client Node Errors (`Bad_NodeIdUnknown`)
* **Cause:** The client is trying to look up nodes using automatic or sequential numerical index structures.
* **Solution:** Ensure your client calls use explicit string node formats utilizing the `s=` syntax prefix exactly as configured on the server layer.
  * **Correct Example:** `ns=1;s=Temperature` or `ns=1;s=Trigger`
  * **Incorrect Example:** `ns=1;i=2` (Do not search for auto-incrementing numerical configurations).

---

### Node-RED Link Timeout (`"invalid endpoint"`)
* **Cause:** The underlying `node-opcua` JavaScript parser module uses highly strict URL and security validation constraints.
* **Solutions:**
  * **URL Formatting:** Ensure your target connection string incorporates the complete lowercase protocol schema alongside a mandatory trailing forward slash. 
    * **Format:** `opc.tcp://NNN.NNN.NNN.NNN:4840/`
  * **Security Profile:** Ensure both your **Security Policy** and **Message Security Mode** fields are explicitly set to `None` inside your Node-RED server profile configuration pane.

---

### Missing Log Actions (Silent Callback Hooks)
* **Cause:** Wrong callback routine execution sequences or mismatched function parameters.
* **Solutions:**
  * **Initialization Order:** Verify that your `AddStringNode` function block registers your custom tracking mappings *after* your global variable instantiation lines have cleared execution blocks.
  * **Parameter Array Matching:** Ensure that your background B4R callback subroutine accepts exactly a single primitive byte array buffer parameter block:
    ```b4x
    Private Sub OpcTriggerCallback(buffer() As Byte)
    ```

---

### Console Debug Silence (Missing Core Logs)
* **Behavior:** The library loads fine but does not spit out internal open62541 C-stack framework outputs to the Arduino/Serial trace window.
* **Explanation:** Core library traces are intentionally routed to `/dev/null` at the hardware register level during FreeRTOS background task setup. 
* **Impact:** This drops verbose `trace/channel` and `debug/session` stdout terminal spam to optimize micro-processing hardware efficiency and maximize throughput on Core 0, while keeping your explicit B4R `Log()` actions completely untouched and functional.
