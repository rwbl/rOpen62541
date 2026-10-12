# README rOpen62541 Example DHT22

## Brief



## PyBridge
The Python package asyncua is required.
Install asyncua package:
	Open from the B4J IDE the global Python shell: ide://run?File=%B4J_PYTHON%\..\WinPython+Command+Prompt.exe
	> The terminal shows folder C:\Prog\B4J\Libraries\Python\Notebooks
	Run: pip install asyncua
	Resulting in Log:
	Successfully installed aiosqlite-0.22.1 anyio-4.15.1 asyncua-2.0.1 cffi-2.1.1 cryptography-50.0.1 idna-3.19 pycparser-3.0 pyopenssl-26.4.0 python-dateutil-2.9.0.post0 pytz-2026.3.post1 six-1.17.0 sortedcontainers-2.4.0 typing-extensions-4.16.0
Hints:
Create a local Python runtime:   
	ide://run?File=%WINDIR%\System32\Robocopy.exe&args=%B4X%\libraries\Python&args=Python&args=/E
Open local Python shell: 
	ide://run?File=%PROJECT%\Objects\Python\WinPython+Command+Prompt.exe
Open global Python shell - make sure To set the path under Tools - Configure Paths. Do Not update the internal package.
	ide://run?File=%B4J_PYTHON%\..\WinPython+Command+Prompt.exe

## OPC UA Nodes
4 children under ns=1;s=Factory_Floor
value=ns=1;s=Temperature|Temperature|Variable
value=ns=1;s=Humidity|Humdity|Variable
value=ns=1;s=Trigger|Remote Action Trigger|Variable
value=ns=1;s=ExecuteJob|Trigger Production Job Routine|Object

## OPC UA CheckConnection Flow

PollNextEvent
     ↓
process OPC UA events
     ↓
Sleep 100 ms
     ↓
after 5 seconds
     ↓
CheckConnection
     ↓
     ├── OK → continue
     │
     └── failed
           ↓
      Python prints
      [OpcUaClient][W] Connection lost: ...
           ↓
      event queue
           ↓
      opc_connection_changed = False

B4J
Opc.CheckConnection
       ↓
Python function
CheckConnection()
       ↓
OpcSyncWorker
check_connection()
       ↓
OPC UA read
       ↓
OK → nothing
       ↓
Failed
       ↓
opc_connection_changed = False