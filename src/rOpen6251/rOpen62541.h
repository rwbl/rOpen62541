#pragma once
/**
 * @file rOpen62541.h
 * @brief B4R C++ partial wrapper for the Open62541 library for ESP32.
 * @note This B4R Library is partially wrapped from the single-file distribution concatenated from the open62541 sources Git-Revision: v1.2-rc1-20-g78a6721b-dirty.
 *       Visit http://open62541.org/ for information about this software. 
 *       Mozilla Public License v2.0 as stated in the LICENSE file provided with open62541.
 * @note The custom structural object folder is named "Factory_Floor" (see build buildOpcUaTree) with target "ns=1;s=Factory_Floor" as objectId.
 * @version See below version
 * @date 2026-09-19
 * @author Robert W. B. Linn (c) 2026 — MIT License provided with rOpen62541.
 */

// Mandatory B4R
#include "B4RDefines.h"

// Open 62541 library stored locally
#include "open62541.h"

//~version: 0.70
namespace B4R {
	//~shortname: Open62541
	//~Event: MethodTriggered ()
	class B4ROPEN62541 {
		private:
			/** @brief Pointer tracking the underlying native open62541 server instance memory layout. */
			UA_Server* uaServer;	
			
			/** @brief Flag verifying if internal initialization sequence was executed. */
			bool initialized;

			/** @brief Function pointer tracking the B4R user-subroutine callback target for the Trigger via write node. */
			SubVoidArray MethodTriggerSub;

			/** @brief Function pointer tracking the B4R user-subroutine callback target for the CallMethod. */
			SubVoidArray MethodCallSub;
          		
			/** 
			 * @brief Static worker thread loop pinned to Core 0. 
			 * Handles heavy TCP/IP payloads and background keepalive handshakes.
			 */
			static void opcuaServerTask(void *pvParameters);

			/** @brief Assembles the custom industrial node architecture base tree folder. */
			static void buildOpcUaTree();
			
			/** 
			 * @brief Internal polling mechanism mapped directly into the main B4R scheduler. 
			 * Allows safe, single-threaded execution of B4R subs triggered by cross-core network tasks.
			 */
			static void looper(void* b);
        
			/** 
			 * @brief Native C/C++ callback invoked directly by open62541 when an OPC UA Client executes a method.
			 * Translates network invocations down to B4R execution requests.
			 */
			static UA_StatusCode scadaTriggerCallback(
				UA_Server *server, const UA_NodeId *sessionId, void *sessionContext,
				const UA_NodeId *methodId, void *methodContext, const UA_NodeId *objectId,
				void *objectContext, size_t inputSize, const UA_Variant *input,
				size_t outputSize, UA_Variant *output);
			
			/** 
			 * @brief Native C/C++ callback invoked directly by open62541 when an OPC UA Client executes a method.
			 * Translates network invocations down to B4R execution requests.
			 */
            static UA_StatusCode opcuaMethodBridge(UA_Server *server,
                const UA_NodeId *sessionId, void *sessionContext,
                const UA_NodeId *methodId, void *methodContext,
                const UA_NodeId *objectId, void *objectContext,
                size_t inputSize, const UA_Variant *input,
                size_t outputSize, UA_Variant *output);

		public:

			/**
			 * INIT
			 */

			/**
			 * Initializes the OPC UA Server engine, builds the network configuration, and registers the B4R callback.
			 * If Username is empty "", it defaults to anonymous access.
			 * @param LocalIP The target TCP network IP.
			 * @param Port The target listening TCP network port (typically 4840).
			 * @param Username The security access username (use "" for no auth).
			 * @param Password The security access password (use "" for no auth).
			 * @param MethodTriggeredSub The B4R Sub to invoke when the remote trigger node is updated.
			 */
			void Initialize(B4RString* LocalIP, int Port, B4RString* Username, B4RString* Password, SubVoidArray MethodTriggerSub);

			/**
			 * ADD NODES
			 */

			/**
			 * Dynamically allocates a new executable RPC Method node inside the Factory Floor folder.
			 * This allows SCADA/Node-RED clients to execute true industrial method commands natively,
			 * passing an input data string and awaiting a direct response token back.
			 * @param MethodName The targeting unique string Node ID for the method (e.g., "ExecuteJob").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param MethodCallSub The function pointer address targeting your B4R method handling subroutine callback.
			 */
			void AddMethodNode(B4RString* MethodName, B4RString* DisplayName, SubVoidArray MethodCallSub);

			/**
			 * Sets the integer execution status return code code for the currently invoked Method node.
			 * This function must be invoked inside your B4R method callback subroutine to send 
			 * a success (e.g., 0) or fault (e.g., 400) token response back across the network to the client.
			 * @param code The status code integer to be bundled into the client's output argument packet.
			 */
			void SetMethodReturnCode(ULong code);

			/**
			 * Dynamically allocates a new floating-point node variable inside the Factory Floor folder.
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "Temperature").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialValue The starting float value assigned to the node space on boot.
			 */
			void AddFloatNode(B4RString* NodeIdentifier, B4RString* DisplayName, float InitialValue);

			/**
			 * Dynamically allocates a new signed 32-bit integer node variable inside the Factory Floor folder.
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "Counter").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialValue The starting integer value assigned to the node space on boot.
			 */
			void AddIntNode(B4RString* NodeIdentifier, B4RString* DisplayName, int InitialValue);

			/**
			 * Dynamically allocates a new string node variable inside the Factory Floor folder.
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "Counter").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialValue The starting string value assigned to the node space on boot.
			 */
			void AddStringNode(B4RString* NodeIdentifier, B4RString* DisplayName, B4RString* InitialValue);
			
			/**
			 * Dynamically allocates a new raw ByteString variable node inside the Factory Floor folder.
			 * Perfect for transferring B4RSerializator binary buffers or plain byte sets.
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "BinaryData").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialBytes The starting B4R ArrayByte buffer package payload to assign on boot.
			 */
			void AddByteStringNode(B4RString* NodeIdentifier, B4RString* DisplayName, ArrayByte* InitialBytes);

			/**
			 * Dynamically allocates a new Boolean node variable inside the Factory Floor folder.
			 * Perfect for switching hardware relays or reading digital state inputs.
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "Relay1").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialValue The starting Boolean flag status assigned on boot.
			 */
			void AddBooleanNode(B4RString* NodeIdentifier, B4RString* DisplayName, bool InitialValue);

			/**
			 * WRITE NODES
			 */

			/**
			 * Thread-safely updates an active OPC UA node by automatically resolving its compiled data layout type.
			 * Supports cross-core type verification for floating-point, integer, and boolean structures inside Namespace 1.
			 * @param NamespaceIndex The numerical namespace target index (e.g., 0).
			 * @param NodeIdentifier The targeting string Node ID (e.g., "Temperature").
			 * @param NewValue The numerical double representation payload to convert and assign.
			 */
			void WriteNumeric(int NamespaceIndex, B4RString* NodeIdentifier, double NewValue);

			/**
			 * Thread-safely writes a string text payload into an active OPC UA node.
			 * Automatically handles string memory copying and verifies target node type data inside Namespace 1.
			 * @param NamespaceIndex The numerical namespace target index (e.g., 0).
			 * @param NodeIdentifier The targeting string Node ID (e.g., "DeviceStatus").
			 * @param NewValue The B4RString payload to write into the node address space.
			 */
			void WriteString(int NamespaceIndex, B4RString* NodeIdentifier, B4RString* NewValue);

			/**
			 * READ
			 */

			/**
			 * OPC UA Standard Read Service.
			 * Thread-safely reads a node value using a numeric identifier.
			 * @param NamespaceIndex The numerical namespace target index (e.g., 0).
			 * @param NumericIdentifier The unique numerical identifier key (e.g., 2258).
			 * @return A B4RString pointer containing the text-formatted UTC value payload.
			 */
			B4RString* ReadNumeric(int NamespaceIndex, int NumericIdentifier);

			/**
			 * OPC UA Standard Read Service.
			 * Thread-safely reads a node value using a string identifier.
			 * @param NamespaceIndex The numerical namespace target index (e.g., 1).
			 * @param NodeIdentifier The targeting string Node ID (e.g., "Temperature").
			 * @return A B4RString pointer containing the text-formatted value payload.
			 */
			B4RString* ReadString(int NamespaceIndex, B4RString* NodeIdentifier);

			/**
			 * SETTER/GETTER
			 */

			/**
			 * Sets whether the server is running.
			 * Required by B4R parser to pair with getIsReady.
			 */
			void setIsReady(bool value);

			/**
			 * Gets whether the underlying OPC UA server is completely initialized and running.
			 * Returns True if online and active.
			 */
			bool getIsReady();

			/**
			 * OFFICIAL OPC UA SEVERITY STATUS CODES (OPC 10000-4 Clause 7.38)
			 */
        
			/** Indicates that the operation was successful and results may be used. */
			static const ULong STATUS_GOOD = 0x00000000;
			
			/** Indicates partial success; results might not fit all purposes. */
			static const ULong STATUS_UNCERTAIN = 0x40000000;
			
			/** Indicates the operation failed completely; results cannot be used. */
			static const ULong STATUS_BAD = 0x80000000;
			
			// Symmetrical common aliases for developer convenience
			static const ULong STATUS_SUCCESS = 0x00000000;
			static const ULong STATUS_WARNING = 0x40000000;
			static const ULong STATUS_FAILURE = 0x80000000;

	};
}
