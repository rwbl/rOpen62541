# OPC UA Protocol Basics

The OPC Unified Architecture (OPC UA) protocol is a secure, platform-independent framework designed for industrial automation and data exchange. It is structured into a multi-layered architecture that standardizes how industrial devices communicate, moving from raw network transport to complex, semantic information models.

---

## 1. Protocol Layer Structure

OPC UA is divided into layers to separate the transport of data from its meaning.

```text
+---------------------------------------------------------+

|                  Information Models                     |  <- What the data means (e.g., PLC, Robotics, PackML)
+---------------------------------------------------------+

|                   Services Layer                        |  <- How to interact (Read, Write, Browse, Subscribe)
+---------------------------------------------------------+

|                   Security Layer                        |  <- Encryption, Signing, Certificates (UA Secure Conversation)
+---------------------------------------------------------+

|                  Transport Layer                        |  <- Encoding & Protocols (OPC UA TCP, HTTPS, JSON, Binary)
+---------------------------------------------------------+
```

* **Information Models (Top Layer):** Defines the semantics (meaning) of the data. It allows vendors to define complex structures, objects, and relationships.
* **Services Layer:** Defines the abstract capabilities of the server. These are request-response interactions like `Read`, `Write`, `Browse` (to navigate the address space), and `CreateSubscription` (for monitoring data changes).
* **Security Layer:** Handles authentication, authorization, and encryption. It ensures data integrity and confidentiality using digital certificates and asymmetric/symmetric cryptography.
* **Transport Layer (Bottom Layer):** Converts services into network streams. It supports multiple mappings, such as:
  * **UA Binary over TCP:** High-performance, optimized for machine-to-machine (M2M) communication.
  * **JSON over WebSockets / HTTPS:** Friendly for cloud and web applications.
  * **PubSub (Publish-Subscribe):** Uses UDP, MQTT, or AMQP for real-time or cloud-scale architectures.

---

## 2. Address Space & Data Node Structure

At the core of OPC UA is the **Address Space**, which represents data as an interconnected network of Nodes (similar to an object-oriented database). Everything in OPC UA is a Node.

Each Node contains specific Attributes depending on its NodeClass (Object, Variable, Method, etc.). The fundamental data contained within a typical Variable Node includes:

* **NodeId:** The unique identifier for the data point (e.g., `ns=2;i=1024` or `ns=1;s=Temperature`).
* **BrowseName & DisplayName:** The human-readable name of the data item (e.g., "Temperature").
* **Value:** The actual payload (e.g., `23.5`).
* **DataType:** The definition of the data type (e.g., `Float`, `Int32`, `Boolean`, or custom structures).
* **AccessLevel:** Indicates if the data is read-only, read/write, or executable.

---

## 3. Data Types & Metadata

OPC UA can carry anything from a single true/false bit to a complex structural model of an entire factory:

* **Built-in / Primitive Data:** Basic types like `Boolean`, `Integer`, `Float`, `Double`, `String`, `DateTime`, and `ByteString`.
* **Data Value Metadata:** When OPC UA transmits a variable's value, it wraps it in a `DataValue` structure containing:
  * **Value:** The actual data point.
  * **StatusCode:** The quality of the data (e.g., `Good`, `Bad`, `Uncertain`, `Bad_Timeout`).
  * **SourceTimestamp:** Exactly when the physical device recorded the data.
  * **ServerTimestamp:** Exactly when the OPC UA server received or processed the data.
* **Complex & Custom Structures:** Structured data types packaged together (e.g., a "Motor" structure containing RPM, Temperature, and Status fields).
* **Events and Alarms:** Data regarding specific system occurrences, containing a message, severity level, time of occurrence, and acknowledgment state.
