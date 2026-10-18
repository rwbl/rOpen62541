#pragma once
/**
 * @file rOpen62541.h
 * @brief B4R C++ partial wrapper for the Open62541 library for ESP32.
 * @note This B4R Library is partially wrapped from the single-file distribution concatenated from the open62541 sources Git-Revision: v1.2-rc1-20-g78a6721b-dirty.
 *       Visit http://open62541.org/ for information about this software. 
 *       Mozilla Public License v2.0 as stated in the LICENSE file provided with open62541.
 * @note The custom structural object folder is named "Factory_Floor" (see build buildOpcUaTree) with target "ns=1;s=Factory_Floor" as objectId.
 *       Everything in Namespace 1, but Path-like Identifiers can be used to group variables or separate from the physical factory floor sensors. 
 *       In OPC UA, a string NodeId can contain delimiters like dots or slashes > keep everything under your single Factory_Floor (ns=1) folder, but structure the NodeIds like this:
 *       ns=1;s=Factory_Floor.Sensors.Temperature
 *       ns=1;s=Factory_Floor.System.AvailableRAM
 * @version See below version
 * @date 2026-10-18
 * @author Robert W. B. Linn (c) 2026 - MIT License provided with rOpen62541.
 */

// Mandatory B4R
#include "B4RDefines.h"

// Open 62541 library stored locally
#include "open62541.h"

//~version: 1.00
namespace B4R {
	//~shortname: Open62541
	
	//~Event: MethodTriggered ()
	//~Event: OnDataWrite (NodeId As String, RawValue() As Byte)
	typedef void (*SubDataWrite)(B4RString* nodeId, ArrayByte* rawValue);

	class B4ROPEN62541 {
		private:
			/** @brief Pointer tracking the underlying native open62541 server instance memory layout. */
			UA_Server* uaServer;	
			
			/** @brief Flag verifying if internal initialization sequence was executed. */
			bool initialized;

			/** @brief Function pointer tracking the B4R user-subroutine callback target for the Trigger via write node. */
			SubVoidArray TriggerSub;

			/** @brief */
			SubDataWrite DataWriteSub;

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
			 * Initializes the OPC UA Server engine, builds the network configuration, and registers the B4R method trigger callback.
			 * Important: Initialize must be called only once during application startup.
			 * If Username is empty "", it defaults to anonymous access.
			 * @param LocalIP The target TCP network IP.
			 * @param Port The target listening TCP network port (typically 4840).
			 * @param Username The security access username (use "" for no auth).
			 * @param Password The security access password (use "" for no auth).
			 * @param TriggerSub The B4R Sub to invoke when the remote trigger node is updated.
			 * @param DataWriteSub The B4R Sub to invoke when a node is updated remotely.
			 */
			void Initialize(B4RString* LocalIP, int Port, B4RString* Username, B4RString* Password, SubVoidArray TriggerSub, SubDataWrite DataWriteSub);

			/**
			 * ADD NODES
			 */

			/**
			 * Registers a standardized OPC UA Object node container in the address space.
			 * Creates an instance of BaseObjectType inside the target parent folder. 
			 * This allows logical structural grouping of process variables and method handlers 
			 * into clear, self-documenting assets (e.g., grouping level tags and setpoint 
			 * handlers together under a single "Tank1" parent object asset).
			 * @param ObjectName Unique string identifier utilized to generate the NodeId (String type).
			 * @param DisplayName Human-readable localized text displayed by SCADA clients during tree browsing.
			 */
			void AddObjectNode(B4RString* ObjectName, B4RString* DisplayName);

			/**
			 * Registers a remote executable Method node with isolated contextual callbacks.
			 * Allocates unique, permanent heap buffers for method signatures to prevent 
			 * memory cross-talk. Binds the high-level B4R callback subroutine pointer straight 
			 * to the unique node registration lifecycle via methodContext.
			 * @param MethodName Unique string identifier utilized to generate the NodeId.
			 * @param DisplayName Human-readable label displayed on interactive SCADA dashboard buttons.
			 * @param MethodCallSub Mapped B4R subroutine callback handler targeted during remote execution.
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
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "ns=1;s=Temperature").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialValue The starting float value assigned to the node space on boot.
			 */
			void AddFloatNode(B4RString* NodeIdentifier, B4RString* DisplayName, float InitialValue);

			/**
			 * Dynamically allocates a new signed 32-bit integer node variable inside the Factory Floor folder.
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "ns=1;s=Counter").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialValue The starting integer value assigned to the node space on boot.
			 */
			void AddIntNode(B4RString* NodeIdentifier, B4RString* DisplayName, int InitialValue);

			/**
			 * Dynamically allocates a new string node variable inside the Factory Floor folder.
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "ns=1;s=Counter").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialValue The starting string value assigned to the node space on boot.
			 */
			void AddStringNode(B4RString* NodeIdentifier, B4RString* DisplayName, B4RString* InitialValue);
			
			/**
			 * Dynamically allocates a new raw ByteString variable node inside the Factory Floor folder.
			 * Perfect for transferring B4RSerializator binary buffers or plain byte sets.
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "ns=1;s=BinaryData").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialBytes The starting B4R ArrayByte buffer package payload to assign on boot (do not set Null).
			 */
			void AddByteStringNode(B4RString* NodeIdentifier, B4RString* DisplayName, ArrayByte* InitialBytes);

			/**
			 * Dynamically allocates a new Boolean node variable inside the Factory Floor folder.
			 * Perfect for switching hardware relays or reading digital state inputs.
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "ns=1;s=Relay1").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialValue The starting Boolean flag status assigned on boot.
			 */
			void AddBooleanNode(B4RString* NodeIdentifier, B4RString* DisplayName, bool InitialValue);

			/**
			 * Dynamically allocates a new Polymorphic Trigger node variable inside the Factory Floor folder.
			 * Configured with UA_VALUERANK_ANY to act as a universal receptor for various data payloads.
			 * It processes incoming data types dynamically, automatically extracting raw memory contents 
			 * into the global trigger buffer. Supported types include:
			 *  - Strings (Text payloads like "STOP", "START")
			 *  - ByteStrings / Wrapped Data (Binary arrays from B4J/B4R clients like hex sequences)
			 *  - Explicit multi-element Arrays (Byte[])
			 *  - Scalar Numbers (Int32, Float, Double, Int16)
			 * @param NodeIdentifier The targeting unique string Node ID (e.g., "ns=1;s=Trigger").
			 * @param DisplayName The human-readable string representation exposed to SCADA clients.
			 * @param InitialValue The starting string value assigned to the node space on boot.
			 */
			void AddTriggerNode(B4RString* NodeIdentifier, B4RString* DisplayName, B4RString* InitialValue);

			/**
			 * WRITE NODES
			 */

			/**
			 * Updates an active OPC UA Float variable node.
			 * Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
			 * @param NodeIdentifier The complete NodeId string (e.g., "ns=1;s=Tank1.Level").
			 * @param NewValue The floating-point value payload to assign.
			 */
			void WriteFloat(B4RString* NodeIdentifier, float NewValue);

			/**
			 * Updates an active OPC UA Int32 variable node.
			 * Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
			 * @param NodeIdentifier The complete NodeId string (e.g., "ns=1;s=Counter").
			 * @param NewValue The signed 32-bit integer value payload to assign.
			 */
			void WriteInt(B4RString* NodeIdentifier, Int NewValue);

			/**
			 * Updates an active OPC UA UInt32 variable node.
			 * Perfect for transferring B4R ULong values or memory metrics cleanly without floating-point shifts.
			 * Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
			 * @param NodeIdentifier The complete NodeId string (e.g., "ns=1;s=System.AvailableRAM").
			 * @param NewValue The unsigned 32-bit integer value payload to assign.
			 */
			void WriteUInt(B4RString* NodeIdentifier, UInt NewValue);

			/**
			 * Updates an active OPC UA Boolean variable node.
			 * Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
			 * @param NodeIdentifier The complete NodeId string (e.g., "ns=1;s=Relay1.State").
			 * @param NewValue The Boolean flag state status to assign.
			 */
			void WriteBoolean(B4RString* NodeIdentifier, bool NewValue);

			/**
			 * Updates an active OPC UA Int64 variable node.
			 * Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
			 * @param NodeIdentifier The complete NodeId string (e.g., "ns=1;s=LargeCounter").
			 * @param NewValue The signed 64-bit integer value payload to assign.
			 */
			void WriteLong(B4RString* NodeIdentifier, Long NewValue);

			/**
			 * Updates an active OPC UA UInt64 variable node.
			 * Temporarily lowers the user-callback execution flag to ensure internal values are updated silently.
			 * @param NodeIdentifier The complete NodeId string (e.g., "ns=1;s=LargeUnsignedCounter").
			 * @param NewValue The unsigned 64-bit integer value payload to assign.
			 */
			void WriteULong(B4RString* NodeIdentifier, ULong NewValue);

			/**
			 * writes a string text payload into an active OPC UA node.
			 * Automatically handles string memory copying and verifies the target node data type.
			 * @param NodeIdentifier The complete NodeId string, containing the namespace index (ns=1) and the string identifier (s=Temperature) (e.g., "ns=1;s=Temperature").
			 * @param NewValue The B4RString payload to write into the node address space.
			 */
			void WriteString(B4RString* NodeIdentifier, B4RString* NewValue);

			/**
			 * writes a binary ByteString payload into an active OPC UA node.
			 * @param NodeIdentifier The complete NodeId string, containing the namespace index (ns=1) and the string identifier (s=Temperature) (e.g., "ns=1;s=Temperature").
			 * @param Data The B4R ArrayByte payload to write into the node address space.
			 */
			void WriteByteString(B4RString* NodeIdentifier, ArrayByte* Data);

			/**
			 * READ
			 */

			/**
			 * OPC UA Standard Read Service.
			 * reads a node value using a numeric identifier.
			 * @param NodeIdentifier The complete NodeId string, containing the namespace index (ns=0) and the string identifier (i=2258) (e.g., "ns=0;i=2258" for the server time).
			 * @return Returns the scalar node value formatted as a B4R String.
			 */
			B4RString* ReadNumeric(B4RString* NodeIdentifier);

			/**
			 * OPC UA Standard Read Service.
			 * reads a node value using a string identifier.
			 * @param NodeIdentifier The complete NodeId string, containing the namespace index (ns=1) and the string identifier (s=Temperature) (e.g., "ns=1;s=Temperature").
			 * @return A B4RString pointer containing the text-formatted value payload.
			 */
			B4RString* ReadString(B4RString* NodeIdentifier);

			/**
			 * OPC UA Standard Read Service.
			 * reads a binary ByteString value using a string identifier.
			 * @param NodeIdentifier The complete NodeId string, containing the namespace index (ns=1) and the string identifier (s=ServerData) (e.g., "ns=1;s=ServerData").
			 * @return An ArrayByte containing the binary ByteString payload.
			 */
			ArrayByte* ReadByteString(B4RString* NodeIdentifier);

			/**
			 * Debug Diagnostics Service.
			 * Reads the raw, low-level data structure of a node from the database cache and immediately outputs its value, status code, and both timestamp states directly to the microcontroller's hardware serial monitor interface.
			 * @param NodeIdentifier The complete NodeId string, containing the namespace index (ns=1) and the string identifier (s=Tank1.Level) (e.g., "ns=1;s=Tank1.Level").
			 */
			void ReadDataValue(B4RString* NodeIdentifier);

			/**
			 * OPC UA Attribute Selective Read Service.
			 * Reads a specified tracking attribute from the node's internal data container and packs the raw data field bytes directly onto the B4R memory stack.
			 * @param NodeIdentifier The complete NodeId string, containing the namespace index (ns=1) and the string identifier (s=Tank1.Level) (e.g., "ns=1;s=Tank1.Level").
			 * @param Attr The attribute category index selection flag:
			 *             0 = Raw Variable Value (Returns scalar Float, Int32, or Boolean payload bytes)
			 *             1 = Source Timestamp (Returns 8 bytes of raw 64-bit int64_t microcontroller clock cycles)
			 *             2 = Server Timestamp (Returns 8 bytes of raw 64-bit int64_t network serialization clock cycles)
			 *             3 = Status Code (Returns 4 bytes of raw 32-bit uint32_t hardware condition code)
			 * @return An ArrayByte containing the requested attribute's binary memory footprint payload block.
			 */
			ArrayByte* ReadDataAttribute(B4RString* NodeIdentifier, int Attr);

			// ============================================================================
			// SETTER/GETTER
			// ============================================================================

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

			// ============================================================================
			// CONSTANTS
			// ============================================================================


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

			/** Indicates that the operation was successful and results may be used. */
			static const ULong STATUS_SUCCESS = 0x00000000;
			
			/** Indicates partial success; results might not fit all purposes. */
			static const ULong STATUS_WARNING = 0x40000000;
			
			/** Indicates the operation failed completely; results cannot be used. */
			static const ULong STATUS_FAILURE = 0x80000000;

	};
}
