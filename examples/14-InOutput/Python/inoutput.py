"""opcua_client.py.

Project: rOpen62541
Brief: Object-Oriented Class for rOpen62541 with Asynchronous Data Subscription.
Date: 20260919
Author: Robert W.B. Linn (c) 2026 - MIT
Dependencies: asyncua library (https://pypi.org)

This module serves as a pedagogical reference for strict PEP 8 and PEP 257
styling guidelines, demonstrating non-blocking communication patterns with an
embedded open62541 OPC UA server running on an ESP32-S3 microcontroller.
"""

import asyncio
from typing import Optional
from asyncua import Client, ua


# =============================================================================
# GLOBAL CONFIGURATION CONSTANTS (PEP 8 Compliant Uppercase)
# =============================================================================
SERVER_IP: str = "192.168.1.175"
SERVER_PORT: int = 4840
OPC_UA_URL: str = f"opc.tcp://{SERVER_IP}:{SERVER_PORT}"

# Connection profile optimization for resource-constrained microcontrollers
CONNECTION_TIMEOUT_SEC: int = 10

# Node Identifiers configured within the B4R open62541 wrapper
NODE_ID_TRIGGER: str = "ns=1;s=Trigger"
NODE_ID_LED_STATE: str = "ns=1;s=LedState"

# Friendly display names for telemetry tracking
NODE_NAME_LED_STATE: str = "LedState"

# Hardware control command values expected by the B4R wrapper
CMD_LED_ON: str = "ledon"
CMD_LED_OFF: str = "ledoff"
VALID_COMMANDS: list[str] = [CMD_LED_ON, CMD_LED_OFF]

# CLI control commands
CMD_EXIT_1: str = "exit"
CMD_EXIT_2: str = "quit"

# OPC UA Sampling interval profile (in milliseconds)
SUBSCRIPTION_INTERVAL_MS: int = 100


# =============================================================================
# SUBSCRIPTION NOTIFICATION HANDLER
# =============================================================================
class ESP32SubHandler:
    """Handles incoming data-change events pushed asynchronously from the server.
    
    According to the asyncua library specifications, the background worker thread
    looks specifically for a method named 'datachange_notification' to pass
    updates from monitored items.
    """

    def __init__(self, node_name: str) -> None:
        """Initialize the notification handler instance."""
        self.node_name: str = node_name
        
        # PEP 8 Internal Attribute: Tracks the initial event push to prevent
        # terminal input prompt overlapping.
        self._is_initial_burst: bool = True

    def datachange_notification(
        self, node: ua.Node, val: ua.Variant, data: object
    ) -> None:
        """Callback invoked automatically when a subscribed Node value alters.

        Args:
            node (ua.Node): The underlying asyncua Node object that changed.
            val (ua.Variant): The new value payload sent by the ESP32 server.
            data: Low-level metadata associated with the MonitoredItem event.
        """
        # Print the live notification value pushed by the server
        print(f"\n[SUBSCRIPTION] Monitored Node '{self.node_name}' changed to: {val}")
        
        # UI Optimization: Avoid printing a trailing prompt if this is the 
        # connection's immediate initial state dump.
        if not self._is_initial_burst:
            print("Enter command ('ledon' or 'ledoff'): ", end="", flush=True)
        else:
            # Flip the flag so all subsequent changes re-prompt the user cleanly
            self._is_initial_burst = False


# =============================================================================
# OBJECT-ORIENTED OPC UA WRAPPER CLASS
# =============================================================================
class Open62541Client:
    """An Object-Oriented wrapper managing safe lifecycle context for rOpen62541.
    
    Implements asynchronous context management mechanisms (__aenter__ / __aexit__)
    to ensure networking socket allocations and server subscriptions teardown cleanly.
    """

    def __init__(self, endpoint_url: str) -> None:
        """Initialize the OPC UA client engine instance configuration."""
        self.url: str = endpoint_url
        self.client: Client = Client(url=self.url)
        
        # PEP 8 Configuration: Inject explicit network timeout variables into 
        # the underlying asyncua transport layers to accommodate embedded hardware.
        self.client.session_timeout = CONNECTION_TIMEOUT_SEC * 1000  # In ms
        self.client.uaclient.timeout = CONNECTION_TIMEOUT_SEC        # In seconds
        
        self.subscription: Optional[object] = None
        self.sub_handle: Optional[int] = None

    async def __aenter__(self) -> "Open62541Client":
        """Establish the connection layer when utilizing async with expressions."""
        print(f"Connecting to {self.url} (Timeout: {CONNECTION_TIMEOUT_SEC}s)...")
        await self.client.connect()
        print("Connected successfully!")
        return self

    async def __aexit__(
        self, exc_type: object, exc_val: object, exc_tb: object
    ) -> None:
        """Handle closing procedures gracefully when exiting the async with scope.
        
        Guarantees cleanup communication executes even if runtime exceptions 
        disrupt primary task trees.
        """
        if self.subscription:
            print("\nUnsubscribing from monitored nodes...")
            await self.subscription.delete()
            
        print("Disconnecting from server...")
        await self.client.disconnect()
        print("Disconnected cleanly.")

    async def send_trigger(self, command: str) -> None:
        """Dispatch text control string arrays down to the specified Trigger Node.

        Args:
            command (str): Action string matching expected target firmware structures.
        """
        trigger_node = self.client.get_node(NODE_ID_TRIGGER)
        try:
            payload = ua.Variant(command, ua.VariantType.String)
            await trigger_node.write_value(payload)
            print(f"Trigger command '{command}' sent successfully!")
        except Exception as error:
            print(f"Failed writing to targeted trigger node reference: {error}")

    async def subscribe_to_node(
        self, node_id: str, node_name: str, interval_ms: int
    ) -> None:
        """Instruct the server engine to push live data events matching specified IDs.

        Args:
            node_id (str): OPC UA formatted Address Node identifier.
            node_name (str): Label applied to internal state logs.
            interval_ms (int): Target polling rate evaluation ceiling requested.
        """
        handler = ESP32SubHandler(node_name)
        self.subscription = await self.client.create_subscription(
            interval_ms, handler
        )
        target_node = self.client.get_node(node_id)
        
        # PEP 8 Alignment: singular 'subscribe_data_change' utilized to match API
        self.sub_handle = await self.subscription.subscribe_data_change(
            target_node
        )
        print(f"Successfully subscribed to {node_id} ({node_name})")


# =============================================================================
# CONCURRENT ASYNCHRONOUS USER INTERFACE TASK LOOP
# =============================================================================
async def user_input_loop(client_instance: Open62541Client) -> None:
    """Monitor standard input concurrently without locking the subscription engine.

    Args:
        client_instance (Open62541Client): The operational connected engine instance.
    """
    # Pause briefly to allow initial server connection responses to render cleanly
    await asyncio.sleep(0.5) 
    
    while True:
        # Standard input() blocks thread pathways entirely under standard execution.
        # Placing it in a default background executor offloads processing, allowing
        # asyncua socket event updates to fire smoothly in parallel.
        loop = asyncio.get_running_loop()
        user_raw = await loop.run_in_executor(
            None, input, "Enter command ('ledon' or 'ledoff'): "
        )
        command = user_raw.strip().lower()

        if command in VALID_COMMANDS:
            await client_instance.send_trigger(command)
        elif command in [CMD_EXIT_1, CMD_EXIT_2]:
            break
        else:
            print(
                f"Invalid entry. Please use: {VALID_COMMANDS} or '{CMD_EXIT_1}'"
            )


# =============================================================================
# RUNTIME ENTRY POINT APPLICATION PIPELINE
# =============================================================================
async def main() -> None:
    """Orchestrate runtime startup allocations and async execution contexts."""
    async with Open62541Client(OPC_UA_URL) as client:
        await client.subscribe_to_node(
            node_id=NODE_ID_LED_STATE, 
            node_name=NODE_NAME_LED_STATE, 
            interval_ms=SUBSCRIPTION_INTERVAL_MS
        )
        
        print(f"\n--- Control Panel Active (Type '{CMD_EXIT_1}' to quit) ---")
        await user_input_loop(client)


if __name__ == "__main__":
    try:
        # Launch standard top-level asynchronous application event orchestration loop
        asyncio.run(main())
    except KeyboardInterrupt:
        print("\nApplication execution terminated by local user intervention.")
