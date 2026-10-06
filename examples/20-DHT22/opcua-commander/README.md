## OPC UA Commander Quick Start Guide (Windows 11)
A lightweight guide to launching opcua-commander and monitoring server nodes.

## 1. Installation
If the command is not found after a global installation, use the direct path to run it under Windows 11.

# Install the package globally
npm install -g opcua-commander

## 2. Launching the Client
Execute the command using your absolute path in PowerShell. Replace the IP and port with your target OPC UA server configuration:

& "$env:APPDATA\npm\opcua-commander" -e opc.tcp://192.168.1.175:4840

## 3. Keyboard Navigation
The interface is entirely driven by your keyboard.

* ↑ / ↓ (Up / Down): Navigate through nodes in the Address Space (left panel).
* → or Enter: Expand a folder node (e.g., expand RootFolder ➔ Objects).
* ←: Collapse an expanded folder.
* Tab: Switch focus between different UI panels (Address Space, Attribute List, Log Info).

## 4. Monitoring Nodes
To watch variables update in real-time inside the Monitored Items panel:

   1. Use the arrows to navigate to a variable node (e.g., Temperature or Humidity).
   2. Press m on your keyboard to add it to the monitoring list.
   3. The server will now automatically stream live data updates to your bottom Info log panel whenever a value changes.

## 💡 Pro-Tips & Hints

* Method Execution: If you highlight a Method node (marked with an [M] tag like Trigger Production), you can press k to call/execute it directly from the terminal.
* Writing Values: If you have write permissions on a variable, highlight the node and press w to input a new value to the server.
* Clear Logs: The bottom console logs every value change sequentially. If it gets too cluttered, it is normal behavior; it simply tracks incoming packets.
* Exit: Press q at any time to safely disconnect from the server and close the application.

------------------------------

