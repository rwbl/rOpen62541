# OPC UA Client B4J PyBridge

## Description

A B4XPages project utilizing the PyBridge framework to interface with and control the rOpen62541 OPC UA Server.

## Environment Dependencies & Prerequisites

### Hardware Requirements
* Microcontroller: ESP32-S3-N16R8 (Absolute minimal specification requirement)

### Software Specs
* IDE: B4J v10.70
* B4X Libraries:
	* PyBridge v1.00 [Thread Link]
* Python Runtime: Python 3.x with asyncua v2.0.1 package dependency.

### Python Environment Setup (Windows / B4J IDE Integration)
Installing the asyncua Core Package

1. Open the global Python command-line shell directly from the B4J IDE by navigating to the shortcut: ide://run?File=%B4J_PYTHON%\..\WinPython+Command+Prompt.exe
2. Once the console shell opens at C:\Prog\B4J\Libraries\Python\Notebooks, execute:cmd
pip install asyncua
3. Verified Setup Target Dependencies Log:text
Successfully installed aiosqlite-0.22.1 anyio-4.15.1 asyncua-2.0.1 cffi-2.1.1 cryptography-50.0.1 idna-3.19 pycparser-3.0 pyopenssl-26.4.0 python-dateutil-2.9.0.post0 pytz-2026.3.post1 six-1.17.0 sortedcontainers-2.4.0 typing-extensions-4.16.0

### IDE Automation Shortcuts & Shell Routing Hints

* Create localized Python runtime workspace environment variables:
ide://run?File=%WINDIR%\System32\Robocopy.exe&args=%B4X%\libraries\Python&args=Python&args=/E
* Launch local project Python terminal space:
ide://run?File=%PROJECT%\Objects\Python\WinPython+Command+Prompt.exe
* Launch global environment Python shell: (Ensure exact path definitions are assigned inside Tools ➔ Configure Paths. Do not modify internal standard vendor package bundles)
ide://run?File=%B4J_PYTHON%\..\WinPython+Command+Prompt.exe

## Standard Execution Logs

### PyBridge Framework Console Stream

```text
Server Is listening on PORT: 54067
Python path: C:\Prog\B4J\libraries\Python\python\python.exe
connected
starting PyBridge v1.00
watchdog set To 30 seconds
Connecting To PORT: 54067
[B4XPage_Created] Python process started.
```

### OPC UA Protocol Communication Log

```text
[StartBackgroundEventLoop] Asynchronous background event Loop started.
[TileIOConnectSwitch_Click] Toggled switch To: True
[TileIOConnectSwitch_Click] Initiating manual connection sequence...
[OpcUaClient] Connecting To OpcClient.tcp://192.168.1.175:4840...
[StartBackgroundEventLoop] Dispatched Event From Queue name=opc_connection_changed value=True
[OpcClient_ConnectionChanged] connected=True
[OpcClient_ConnectionChanged] Connection verified! Activating subscriptions...
[OpcClient_ConnectionChanged] Subscribing To ns=1;s=LedState
Revised values returned differ from subscription values: CreateSubscriptionResult(SubscriptionId=1, RevisedPublishingInterval=500.0, RevisedLifetimeCount=10000, RevisedMaxKeepAliveCount=100)
[OpcUaClient] Subscribed cleanly To ns=1;s=LedState
[StartBackgroundEventLoop] Dispatched Event From Queue name=opc_datachange value=ns=1;s=LedState 0
[OpcClient_DataChanged] Event Received nodeid=[ns=1;s=LedState] value=0
[TileIOLedSwitch_Click] Toggled switch To True using Trigger Node
[B4XPage_CloseRequest] Opc disconnect
[StartBackgroundEventLoop] Dispatched Event From Queue name=opc_connection_changed value=False
[OpcClient_ConnectionChanged] connected=False
[B4XPage_CloseRequest] PyBridge stopping
[Py_Disconnected] PyBridge dropped.
Process completed. ExitCode: 1
```

## Technical Notes & Protocol Deep-Dive

Understanding the Subscription Parameters Log:

### Message CreateSubscriptionResult
Revised values returned differ from subscription values: CreateSubscriptionResult(SubscriptionId=1, RevisedPublishingInterval=500.0, RevisedLifetimeCount=10000, RevisedMaxKeepAliveCount=100)

#### Technical Explanation

This is a standard confirmation response from the industrial OPC UA core protocol engine. It means the ESP32-S3 microcontroller successfully negotiated and adjusted data delivery speeds to protect its active memory and processor resource constraints.
When the Python client initializes, the asyncua library routinely requests a high-frequency data sampling refresh window of 100 milliseconds (self.client.create_subscription(100, handler)).
However, because the open62541 stack on the ESP32-S3 is running a real-time embedded hardware loop alongside critical Wi-Fi radio processes and FreeRTOS context tasks, the microcontroller overrides that aggressive request. It sends back a programmatic constraint response stating: "I am a resource-constrained microcontroller. The fastest I can safely process and transmit asynchronous data updates without lagging my primary control loops is every 500 milliseconds."

Data Property Field Brakedown:

* RevisedPublishingInterval=500.0: The server changed the requested 100ms interval to 500ms. Telemetry data will be securely pushed out twice per second.
* RevisedLifetimeCount=10000: The total number of scheduling intervals the server will maintain the active subscription allocation in memory without receiving an acknowledgment frame from Python before automatically tearing the channel down.
* RevisedMaxKeepAliveCount=100: If data points remain static (e.g., the target LED stays in state 0 for a prolonged duration), the server will automatically issue an empty "ping" packet every 100 intervals (exactly every 50 seconds) to confirm line health to the Python runtime client.
