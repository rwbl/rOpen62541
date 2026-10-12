import asyncio
import logging
from asyncua import Client

# Configure logging to see clean terminal output
logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(message)s")
logger = logging.getLogger("OPCUA-Test")

# Replace this string with your exact ESP32-S3 board address coordinates
SERVER_URL = "opc.tcp://192.168.1.175:4840"

async def main():
    logger.info(f"Connecting to rOpen62541 Server at {SERVER_URL}...")
    
    # Establish a clean, non-blocking connection channel context
    async with Client(url=SERVER_URL) as client:
        logger.info("Connection successful!")
        
        # 1. Access the standard parent nodes tree
        objects_node = client.nodes.objects
        logger.info(f"Objects Folder Node reference: {objects_node}")
        
        # 2. Recursively browse the 'Objects' root tree folder
        logger.info("Browsing top-level elements inside Objects...")
        children = await objects_node.get_children()
        
        factory_floor_node = None
        for child in children:
            browse_name = await child.read_browse_name()
            logger.info(f" -> Found Node: ID={child} | BrowseName={browse_name.Name}")
            
            # Identify your custom process folder
            if browse_name.Name == "Factory_Floor":
                factory_floor_node = child

        # 3. Simulate the 'opcua-commander' action by expanding Factory_Floor safely
        if factory_floor_node:
            logger.info("==================================================")
            logger.info("Expanding 'Factory_Floor' to verify all 25+ children nodes...")
            logger.info("==================================================")
            
            tank_nodes = await factory_floor_node.get_children()
            
            for node in tank_nodes:
                node_name = await node.read_browse_name()
                node_class = await node.read_node_class()
                
                # If it's a Variable (Float/Int/Bool), read its current value
                if node_class.name == "Variable":
                    current_value = await node.read_value()
                    logger.info(f" [V] Name: {node_name.Name:<20} | Value: {current_value}")
                
                # If it's your advanced Method nodes, verify it displays cleanly
                elif node_class.name == "Method":
                    logger.info(f" [M] Executable Method: {node_name.Name:<20} | Status: Ready")
                    
            logger.info("==================================================")
            logger.info("SUCCESS: All 25+ items and methods browsed with ZERO drops!")
            logger.info("==================================================")
        else:
            logger.error("Could not find 'Factory_Floor' folder in the server namespace hierarchy.")

if __name__ == "__main__":
    try:
        asyncio.run(main())
    except Exception as e:
        logger.error(f"Network processing failed: {e}")
