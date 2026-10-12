## OPC UA Commander Quick Start Guide (Windows 11)
A lightweight guide to launching opcua-commander and monitoring server nodes.

## Install the package globally
```
npm install -g opcua-commander
```

## Launching the Client
Execute the command using absolute path in a terminal.  
Replace the IP and port with the target OPC UA server configuration:

```
"%APPDATA%\npm\opcua-commander" -e opc.tcp://192.168.1.175:4840                         
```
*Note*:
If the command is not found after a global installation, use the direct path to run it under Windows 11.

## Keyboard Navigation
The interface is entirely driven by the keyboard.

* ↑ / ↓ (Up / Down): Navigate through nodes in the Address Space (left panel).
* → or Enter: Expand a folder node (e.g., expand RootFolder ➔ Objects).
* ←: Collapse an expanded folder.
* Tab: Switch focus between different UI panels (Address Space, Attribute List, Log Info).

## Monitoring Nodes
To watch variables update in real-time inside the Monitored Items panel:

* Use the arrows to navigate to a variable node (e.g., Temperature or Humidity).
* Press m on the keyboard to add it to the monitoring list.
* The server will now automatically stream live data updates to the bottom Info log panel whenever a value changes.

## Hints

* Method Execution: If you highlight a Method node (marked with an [M] tag like Trigger Production), you can press k to call/execute it directly from the terminal.
* Writing Values: If you have write permissions on a variable, highlight the node and press w to input a new value to the server.
* Clear Logs: The bottom console logs every value change sequentially. If it gets too cluttered, it is normal behavior; it simply tracks incoming packets.
* Exit: Press q at any time to safely disconnect from the server and close the application.

------------------------------

