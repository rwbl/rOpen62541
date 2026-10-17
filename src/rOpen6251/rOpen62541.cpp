/*
 * rOpen62541.cpp
 * Source of the rOpen62541 B4R wrapper.
 */

#include "B4RDefines.h"
#include "freertos/semphr.h" 

// ============================================================================
// File-Scoped Global Variables (Static Context for FreeRTOS Task)
// ============================================================================
static UA_Server *server = NULL;
static UA_NodeId folderNodeId; 							// Main base folder node allocation reference
static int serverPort = 4840;							// Default port accessed by clients
static bool globalIsReady = false; 
static char espIpAddress[32] = "127.0.0.1"; 			// Default ESP32 IP address which is changed by Initialize

static SemaphoreHandle_t open62541Mutex = NULL; 

// Trigger Method initiated from OPCUA client ("Trigger" or "trigger")
static volatile bool scadaTriggerCalled = false; 		// Flag for method calls
static uint8_t globalTriggerBuffer[64]; 				// Cache for incoming raw byte payloads (String, Int, Float, etc.)
static volatile size_t globalTriggerLength = 0;

// Generic Variable Data Write Context 
static volatile bool scadaDataWriteCalled = false;     	// Flag for standard node updates
static char globalWriteNodeIdBuffer[64]; 				// Safe space for full "ns=1;s=..." string
static uint8_t globalWriteValueBuffer[64];
static size_t globalWriteValueLength = 0;
static volatile bool isOnDataWrite = true; 				// Controlled exclusively by internal Write methods

// Method Call initiated from OPCUA client (Method Call)
static volatile bool scadaMethodCalled = false;			// Flag for method calls
static uint8_t globalMethodInputBuffer[64];      		// Cache for incoming method arguments
static volatile size_t globalMethodInputLength = 0;
static volatile ULong globalMethodOutputResult = 0;		// Return parameter sent back to SCADA

// User Auth Management
static char uaUsername[32] = "";
static char uaPassword[32] = "";
static bool useAuthentication = false;

namespace B4R {

    // ============================================================================
    // INITIALIZE
    // ============================================================================

	void B4ROPEN62541::Initialize(
		B4RString* LocalIP, 
		int Port,
		B4RString* Username, 
		B4RString* Password, 
		SubVoidArray TriggerSub,
		SubDataWrite DataWriteSub) {
		
		serverPort = Port;
		globalIsReady = false;
		initialized = false;
		
		memset(espIpAddress, 0, sizeof(espIpAddress));
		strncpy(espIpAddress, LocalIP->data, sizeof(espIpAddress) - 1);

		// Capture the security credentials
		memset(uaUsername, 0, sizeof(uaUsername));
		memset(uaPassword, 0, sizeof(uaPassword));
		
		// Check if username given
		if (Username->getLength() > 0) {
			strncpy(uaUsername, Username->data, sizeof(uaUsername) - 1);
			strncpy(uaPassword, Password->data, sizeof(uaPassword) - 1);
			useAuthentication = true;
		} else {
			useAuthentication = false;
		}

		this->TriggerSub = TriggerSub; 
		this->DataWriteSub = DataWriteSub;
		
		if (open62541Mutex == NULL) {
			open62541Mutex = xSemaphoreCreateMutex();
		}
		
		FunctionUnion fu;
		fu.PollerFunction = looper;
		pollers.add(fu, this);

		/**
		 * Initializes the underlying open62541 multitasking background execution engine.
		 * Offloads the resource-intensive OPC UA networking and protocol stack processing 
		 * completely to ESP32 Core 0 (Network Processor). This design safely isolates the 
		 * communication layer, preventing any potential timing jitters or blocking execution 
		 * loops on the main B4R control loop running on Core 1.
		 */
		xTaskCreatePinnedToCore(
			B4ROPEN62541::opcuaServerTask,   // Task callback entry routine
			"OPCUA_Server_Task",             // Debugging name tag
			16 * 1024,                       // Stack allocation (16KB handles multi-client indexing)
			NULL,                            // Context parameters pointer mapping (Static context task)
			1,                               // Task priority ranking schedule (Low impact background tier)
			NULL,                            // Task tracking reference handle
			0                                // Core Assignment: Pinned to Core 0
		);
		
		initialized = true;
	}

	// ============================================================================
	// OPC UA Server UNIVERSAL WRITE CALLBACK
	// ============================================================================

	static void onDataWrite(UA_Server *server,
							const UA_NodeId *sessionId, void *sessionContext,
							const UA_NodeId *nodeId, void *nodeContext,
							const UA_NumericRange *range, const UA_DataValue *value) {

		// If an internal write method lowered this flag, exit instantly!
		if (!isOnDataWrite) return;
				
		if (nodeId == NULL || nodeId->identifierType != UA_NODEIDTYPE_STRING) return;
		if (value == NULL || !value->hasValue) return;

		const UA_Variant *data = &value->value;
		if (data->data == NULL || !UA_Variant_isScalar(data)) return;

		// ::Serial.printf("[rOpen62541] Variant type index: %u\n", (unsigned)data->type->typeIndex);
		// ::Serial.printf("[rOpen62541] Is scalar: %s\n", data->arrayLength == 0 ? "yes" : "no");

		// Format the full Node ID
		int nodeIdLen = (int)nodeId->identifier.string.length;
		if (nodeIdLen > 52) nodeIdLen = 52;

		snprintf(globalWriteNodeIdBuffer, sizeof(globalWriteNodeIdBuffer),
				 "ns=%d;s=%.*s",
				 nodeId->namespaceIndex, nodeIdLen,
				 (const char*)nodeId->identifier.string.data);

		// Reset the shared value buffer
		memset(globalWriteValueBuffer, 0, sizeof(globalWriteValueBuffer));
		globalWriteValueLength = 0;

		// Boolean: 1 byte
		if (UA_Variant_hasScalarType(data, &UA_TYPES[UA_TYPES_BOOLEAN])) {
			globalWriteValueLength = sizeof(UA_Boolean);
		}
		// 32-bit numeric values: 4 bytes
		else if (UA_Variant_hasScalarType(data, &UA_TYPES[UA_TYPES_FLOAT]) ||
				 UA_Variant_hasScalarType(data, &UA_TYPES[UA_TYPES_INT32]) ||
				 UA_Variant_hasScalarType(data, &UA_TYPES[UA_TYPES_UINT32])) {
			globalWriteValueLength = 4;
		}
		// 64-bit numeric values and DateTime: 8 bytes
		else if (UA_Variant_hasScalarType(data, &UA_TYPES[UA_TYPES_DOUBLE]) ||
				 UA_Variant_hasScalarType(data, &UA_TYPES[UA_TYPES_DATETIME])) {
			globalWriteValueLength = 8;
		}
		// String: copy the string payload
		else if (UA_Variant_hasScalarType(data, &UA_TYPES[UA_TYPES_STRING])) {
			const UA_String *str = (const UA_String*)data->data;
			globalWriteValueLength = str->length;

			if (globalWriteValueLength > sizeof(globalWriteValueBuffer)) {
				globalWriteValueLength = sizeof(globalWriteValueBuffer);
			}

			if (globalWriteValueLength > 0 && str->data != NULL) {
				memcpy(globalWriteValueBuffer, str->data, globalWriteValueLength);
			}

			scadaDataWriteCalled = true;
			return;
		}
		// ByteString: copy the raw payload
		else if (UA_Variant_hasScalarType(data, &UA_TYPES[UA_TYPES_BYTESTRING])) {
			const UA_ByteString *bs = (const UA_ByteString*)data->data;
			globalWriteValueLength = bs->length;

			if (globalWriteValueLength > sizeof(globalWriteValueBuffer)) {
				globalWriteValueLength = sizeof(globalWriteValueBuffer);
			}

			if (globalWriteValueLength > 0 && bs->data != NULL) {
				memcpy(globalWriteValueBuffer, bs->data, globalWriteValueLength);
			}

			scadaDataWriteCalled = true;
			return;
		}
		else {
			// Unsupported datatype
			return;
		}

		// Copy the raw numeric value
		if (globalWriteValueLength > sizeof(globalWriteValueBuffer)) return;

		memcpy(globalWriteValueBuffer, data->data, globalWriteValueLength);

		scadaDataWriteCalled = true;
	}

    // ============================================================================
    // OPC UA Server LOOPER
    // ============================================================================

	void B4ROPEN62541::looper(void* b) {
		B4ROPEN62541* me = (B4ROPEN62541*)b;
		if (!me->initialized) return;

		// CASE 1: Node Write Trigger (scadaTriggerCalled)
		if (scadaTriggerCalled) {
			scadaTriggerCalled = false; 
			if (me->TriggerSub != NULL) {
				const UInt cp = B4R::StackMemory::cp;
				ArrayByte* arr = CreateStackMemoryObject(ArrayByte);
				arr->data = (Byte*)globalTriggerBuffer;
				arr->length = globalTriggerLength;
				me->TriggerSub(arr);
				B4R::StackMemory::cp = cp;
			}
		}

		// CASE 2: New Generic OnDataWrite Execution Node
		if (scadaDataWriteCalled) {
			scadaDataWriteCalled = false;

			if (me->DataWriteSub != NULL) {
				const UInt cp = B4R::StackMemory::cp;

				B4RString* b4rNodeId = CreateStackMemoryObject(B4RString);
				b4rNodeId->data = (const char*)globalWriteNodeIdBuffer;

				ArrayByte* b4rValueBytes = CreateStackMemoryObject(ArrayByte);
				b4rValueBytes->data = (Byte*)globalWriteValueBuffer;
				b4rValueBytes->length = globalWriteValueLength;

				me->DataWriteSub(b4rNodeId, b4rValueBytes);

				B4R::StackMemory::cp = cp; 
			}
		}
	}

    // ============================================================================
    // OPC UA Server Execution Task (Runs completely on Core 0)
    // ============================================================================

	void B4ROPEN62541::opcuaServerTask(void *pvParameters) {
        freopen("/dev/null", "w", stdout);

        server = UA_Server_new();
        if (server == NULL) {
            ::Serial.println("[opcuaServerTask][E] Memory allocation failed!");
            vTaskDelete(NULL);
            return;
        }

        UA_ServerConfig *config = UA_Server_getConfig(server);
        UA_ServerConfig_setMinimal(config, serverPort, NULL);

        // ====================================================================
        // 1. CONSOLIDATED CONFIGURATION (All structural variables set first)
        // ====================================================================
        config->publishingIntervalLimits.min = 500.0;    
        config->publishingIntervalLimits.max = 60000.0;  
        config->maxSessions = 10; // Set maximum client allocations cleanly up front!

        // CRITICAL PROTECTION: Enforce speed limits on recursive browsing scans.
        // This limits the references returned per packet, protecting microcontroller RAM
        // from collapsing when hammered by aggressive tools like opcua-commander.
        config->maxReferencesPerNode = 20;
        config->maxNodesPerBrowse = 10;
        
        // DYNAMIC SECURITY POLICY MANAGEMENT
        if (useAuthentication) {
            static UA_UsernamePasswordLogin loginCredentials;
            loginCredentials.username = UA_STRING(uaUsername);
            loginCredentials.password = UA_STRING(uaPassword);
            UA_AccessControl_default(config, false, NULL, 1, &loginCredentials);
            ::Serial.println("[opcuaServerTask] OPC UA Security: User Authentication Mode Active!");
        } else {
            UA_AccessControl_default(config, true, NULL, 0, NULL);
            ::Serial.println("[opcuaServerTask] OPC UA Security: Anonymous Access Mode Active!");
        }

        UA_String customHostname = UA_STRING(espIpAddress);
        UA_ServerConfig_setCustomHostname(config, customHostname);
        
        // ====================================================================
        // 2. DATA TREE COMPILATION (Executed only AFTER configuration is frozen)
        // ====================================================================
        buildOpcUaTree();

        if (UA_Server_run_startup(server) == UA_STATUSCODE_GOOD) {
            // ::Serial.println("[opcuaServerTask] OPC UA Server successfully running on Core 0!");
            globalIsReady = true; 
        } else {
            ::Serial.println("[opcuaServerTask][E] Critical open62541 Startup Sequence Failed!");
            globalIsReady = false;
        }

        // Infinite background processing loop (Bulletproof Multi-Client Optimization)
        while(true) {
            if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
                UA_Server_run_iterate(server, true);
                xSemaphoreGive(open62541Mutex); 
            }
            
            // Keeps stable baseline loop delay intact
            vTaskDelay(pdMS_TO_TICKS(10)); 
        }
    }

	
    // ============================================================================
    // Address Space Construction (Base Setup Only)
    // ============================================================================

	void B4ROPEN62541::buildOpcUaTree() {
		// Create the base folder. Nodes will be attached here dynamically later.
		UA_ObjectAttributes oAttr = UA_ObjectAttributes_default;
		oAttr.displayName = UA_LOCALIZEDTEXT("en-US", "Factory_Floor");

		folderNodeId = UA_NODEID_STRING(1, (char*)"Factory_Floor");

		UA_Server_addObjectNode(
			server,
			folderNodeId,
			UA_NODEID_NUMERIC(0, UA_NS0ID_OBJECTSFOLDER),
			UA_NODEID_NUMERIC(0, UA_NS0ID_ORGANIZES),
			UA_QUALIFIEDNAME(1, (char*)"Factory_Floor"),
			UA_NODEID_NUMERIC(0, UA_NS0ID_BASEOBJECTTYPE),
			oAttr,
			NULL,
			NULL);
	}

    // ============================================================================
	// HELPER CREATING & ADDING NODES
    // ============================================================================

	UA_NodeId CreateNodeId(B4RString* NodeIdentifier) {
		const char* identifier = (char*)NodeIdentifier->data;

		UA_UInt16 namespaceIndex = 1;
		const char* nodeId = identifier;

		if (strncmp(identifier, "ns=", 3) == 0) {
			namespaceIndex = atoi(identifier + 3);
			const char* separator = strchr(identifier, ';');
			if (separator != NULL && strncmp(separator + 1, "s=", 2) == 0)
			{
				nodeId = separator + 3;
			}
		}
		return UA_NODEID_STRING(namespaceIndex, (char*)nodeId);
	}

	const char* GetStringIdentifier(B4RString* NodeIdentifier) {
		const char* identifier = (char*)NodeIdentifier->data;

		if (strncmp(identifier, "ns=", 3) == 0) {
			const char* separator = strchr(identifier, ';');

			if (separator != NULL && strncmp(separator + 1, "s=", 2) == 0)
				return separator + 3;
		}
		return identifier;
	}

    // ============================================================================
    // ADD NODES (Public Methods API)
    // ============================================================================

	void B4ROPEN62541::AddObjectNode(B4RString* ObjectName, B4RString* DisplayName) {
		if (server == NULL) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			
			UA_ObjectAttributes oAttr = UA_ObjectAttributes_default;
			oAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			oAttr.description = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			
			UA_NodeId objectNodeId = CreateNodeId(ObjectName);
			const char* stringIdentifier = GetStringIdentifier(ObjectName);
			
			UA_Server_addObjectNode(server, objectNodeId,
									 folderNodeId, // Put it inside Factory_Floor
									 UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT),
									 UA_QUALIFIEDNAME(1, (char*)stringIdentifier),
									 UA_NODEID_NUMERIC(0, UA_NS0ID_BASEOBJECTTYPE), // Explicit Object Type!
									 oAttr, NULL, NULL);
									 
			xSemaphoreGive(open62541Mutex);
		}
	}

	void B4ROPEN62541::AddMethodNode(B4RString* MethodName, B4RString* DisplayName, SubVoidArray MethodCallSub) {
		if (server == NULL) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {

			UA_Argument *inputArgument = (UA_Argument*)UA_malloc(sizeof(UA_Argument));
			UA_Argument_init(inputArgument);
			inputArgument->description = UA_LOCALIZEDTEXT("en-US", "Command Payload Code");
			inputArgument->name = UA_STRING("InputData");
			inputArgument->dataType = UA_TYPES[UA_TYPES_BYTESTRING].typeId;
			inputArgument->valueRank = UA_VALUERANK_SCALAR;

			UA_Argument *outputArgument = (UA_Argument*)UA_malloc(sizeof(UA_Argument));
			UA_Argument_init(outputArgument);
			outputArgument->description = UA_LOCALIZEDTEXT("en-US", "Execution Return Code");
			outputArgument->name = UA_STRING("ReturnStatus");
			outputArgument->dataType = UA_TYPES[UA_TYPES_INT32].typeId;
			outputArgument->valueRank = UA_VALUERANK_SCALAR;

			UA_MethodAttributes mAttr = UA_MethodAttributes_default;
			mAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			// Fill the description text block to eliminate "text=null" string bugs
			mAttr.description = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
				   
			// Clear masks to signal clients this node is strictly a functional handler
			mAttr.writeMask = 0;
			mAttr.userWriteMask = 0;		
			
			mAttr.executable = true;
			mAttr.userExecutable = true;

			UA_NodeId methodNodeId = CreateNodeId(MethodName);
			const char* methodIdentifier = GetStringIdentifier(MethodName);

			UA_Server_addMethodNode(
				server,
				methodNodeId,
				folderNodeId,
				UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT),
				UA_QUALIFIEDNAME(1, (char*)methodIdentifier),
				mAttr,
				&B4ROPEN62541::opcuaMethodBridge,
				1, inputArgument,  // Pass the unique, persistent pointer directly
				1, outputArgument, // Pass the unique, persistent pointer directly
				(void*)MethodCallSub, 
				NULL);

			xSemaphoreGive(open62541Mutex);
		}
	}

	UA_StatusCode B4ROPEN62541::opcuaMethodBridge(UA_Server *server,
		const UA_NodeId *sessionId, void *sessionContext,
		const UA_NodeId *methodId, void *methodContext,
		const UA_NodeId *objectId, void *objectContext,
		size_t inputSize, const UA_Variant *input,
		size_t outputSize, UA_Variant *output) {
		
		if (inputSize > 0 && input != NULL) {
			const UA_Variant *firstArg = input; 
			if (firstArg->type != NULL && firstArg->data != NULL) {
				size_t rawLength = 0;
				const uint8_t* rawData = NULL;

				if (firstArg->type == &UA_TYPES[UA_TYPES_STRING]) {
					UA_String *uaStr = (UA_String*)firstArg->data;
					rawLength = uaStr->length;
					rawData = (const uint8_t*)uaStr->data;
				}
				else if (firstArg->type == &UA_TYPES[UA_TYPES_BYTESTRING]) {
					UA_ByteString *uaBytes = (UA_ByteString*)firstArg->data;
					rawLength = uaBytes->length;
					rawData = (const uint8_t*)uaBytes->data;
				}

				if (rawData != NULL && rawLength > 0) {
					memset(globalMethodInputBuffer, 0, sizeof(globalMethodInputBuffer));
					size_t maxAllowed = sizeof(globalMethodInputBuffer) - 1;
					globalMethodInputLength = (rawLength < maxAllowed) ? rawLength : maxAllowed;
					
					memcpy(globalMethodInputBuffer, rawData, globalMethodInputLength);
					globalMethodInputBuffer[globalMethodInputLength] = '\0';
					
					globalMethodOutputResult = 0; 
					
					// --- FIX: Extract the specific Sub routine callback reference pointer bound to this node context ---
					SubVoidArray targetB4RSub = (SubVoidArray)methodContext;
					
					if (targetB4RSub != NULL) {
						const UInt cp = B4R::StackMemory::cp;
						ArrayByte* arr = CreateStackMemoryObject(ArrayByte);
						arr->data = (Byte*)globalMethodInputBuffer;
						arr->length = globalMethodInputLength;
						
						// Fire the unique mapped B4R subroutine immediately inside the bridge thread context
						targetB4RSub(arr); 
						B4R::StackMemory::cp = cp;
					}
					
					if (outputSize > 0 && output != NULL) {
						UA_Int32 *returnValue = (UA_Int32*)UA_malloc(sizeof(UA_Int32));
						if (returnValue != NULL) {
							*returnValue = globalMethodOutputResult;
							UA_Variant_setScalarCopy(output, returnValue, &UA_TYPES[UA_TYPES_INT32]);
							UA_free(returnValue);
						}
					}
					return UA_STATUSCODE_GOOD;
				}
			}
		}
		return UA_STATUSCODE_BADINVALIDARGUMENT;
	}

	void B4ROPEN62541::SetMethodReturnCode(ULong code) {
        globalMethodOutputResult = code;
    }

    void B4ROPEN62541::AddFloatNode(B4RString* NodeIdentifier, B4RString* DisplayName, float InitialValue) {
        if (server == NULL) return;
        if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
            
            UA_VariableAttributes vAttr = UA_VariableAttributes_default;
            UA_Float val = InitialValue;
            UA_Variant_setScalar(&vAttr.value, &val, &UA_TYPES[UA_TYPES_FLOAT]);
            vAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			vAttr.description = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
            vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
                       
			UA_NodeId targetNodeId = CreateNodeId(NodeIdentifier);
			const char* stringIdentifier = GetStringIdentifier(NodeIdentifier);
			
            UA_Server_addVariableNode(server, 
                                      targetNodeId, 
                                      folderNodeId,                               
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
                                      UA_QUALIFIEDNAME(1, (char*)stringIdentifier), 
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
                                      vAttr, NULL, NULL);
           
			UA_Server_setVariableNode_valueCallback(server, targetNodeId, *(new UA_ValueCallback{NULL, onDataWrite}));
     				
            xSemaphoreGive(open62541Mutex);
        }
    }

    void B4ROPEN62541::AddIntNode(B4RString* NodeIdentifier, B4RString* DisplayName, int InitialValue) {
        if (server == NULL) return;
        if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
            
            UA_VariableAttributes vAttr = UA_VariableAttributes_default;
            UA_Int32 val = InitialValue;
            UA_Variant_setScalar(&vAttr.value, &val, &UA_TYPES[UA_TYPES_INT32]);
            vAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			vAttr.description = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
            vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
            
			UA_NodeId targetNodeId = CreateNodeId(NodeIdentifier);
			const char* stringIdentifier = GetStringIdentifier(NodeIdentifier);
            
            UA_Server_addVariableNode(server, 
                                      targetNodeId, 
                                      folderNodeId,                               
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
                                      UA_QUALIFIEDNAME(1, (char*)stringIdentifier), 
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
                                      vAttr, NULL, NULL);

			UA_Server_setVariableNode_valueCallback(server, targetNodeId, *(new UA_ValueCallback{NULL, onDataWrite}));
                                      
            xSemaphoreGive(open62541Mutex);
        }
    }

    void B4ROPEN62541::AddBooleanNode(B4RString* NodeIdentifier, B4RString* DisplayName, bool InitialValue) {
        if (server == NULL) return;
        if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
            
            UA_VariableAttributes vAttr = UA_VariableAttributes_default;
            UA_Boolean val = InitialValue;
            
            UA_Variant_setScalar(&vAttr.value, &val, &UA_TYPES[UA_TYPES_BOOLEAN]);
            vAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			vAttr.description = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
            vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
            
			UA_NodeId targetNodeId = CreateNodeId(NodeIdentifier);
			const char* stringIdentifier = GetStringIdentifier(NodeIdentifier);
            
            UA_Server_addVariableNode(server, 
                                      targetNodeId, 
                                      folderNodeId,                               
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
                                      UA_QUALIFIEDNAME(1, (char*)stringIdentifier), 
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
                                      vAttr, NULL, NULL);

			UA_Server_setVariableNode_valueCallback(server, targetNodeId, *(new UA_ValueCallback{NULL, onDataWrite}));
                                      
            xSemaphoreGive(open62541Mutex);
        }
    }

	void B4ROPEN62541::AddStringNode(B4RString* NodeIdentifier, B4RString* DisplayName, B4RString* InitialValue) {
		if (server == NULL) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			
			UA_VariableAttributes vAttr = UA_VariableAttributes_default;
			UA_String val = UA_STRING((char*)InitialValue->data);
			UA_Variant_setScalar(&vAttr.value, &val, &UA_TYPES[UA_TYPES_STRING]);
			vAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			vAttr.description = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
			
			// Use a persistent NodeId instance copy for tracking
			UA_NodeId targetNodeId = CreateNodeId(NodeIdentifier);
			const char* stringIdentifier = GetStringIdentifier(NodeIdentifier);
			
			// Add the variable node to the address space first!
			UA_Server_addVariableNode(server, 
									  targetNodeId, 
									  folderNodeId,                               
									  UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
                                      UA_QUALIFIEDNAME(1, (char*)stringIdentifier), 
									  UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
									  vAttr, NULL, NULL);

			UA_Server_setVariableNode_valueCallback(server, targetNodeId, *(new UA_ValueCallback{NULL, onDataWrite}));

			xSemaphoreGive(open62541Mutex);
		}
	}

    void B4ROPEN62541::AddByteStringNode(B4RString* NodeIdentifier, B4RString* DisplayName, ArrayByte* InitialBytes) {
        if (server == NULL) return;
        if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
            
            UA_VariableAttributes vAttr = UA_VariableAttributes_default;
            
            // Build the native OPC UA ByteString container directly from the B4R ArrayByte elements
            UA_ByteString bString;
            bString.length = InitialBytes->length;
            bString.data = (UA_Byte*)InitialBytes->data;
            
            // Explicitly bind using the native BYTESTRING datatype layout pointer
            UA_Variant_setScalar(&vAttr.value, &bString, &UA_TYPES[UA_TYPES_BYTESTRING]);
            vAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			vAttr.description = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
            vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
            
            // UA_NodeId targetNodeId = UA_NODEID_STRING(1, (char*)NodeIdentifier->data);
			UA_NodeId targetNodeId = CreateNodeId(NodeIdentifier);
			const char* stringIdentifier = GetStringIdentifier(NodeIdentifier);
            
            UA_Server_addVariableNode(server, 
                                      targetNodeId, 
                                      folderNodeId,                               
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
                                      UA_QUALIFIEDNAME(1, (char*)stringIdentifier), 
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
                                      vAttr, NULL, NULL);

			UA_Server_setVariableNode_valueCallback(server, targetNodeId, *(new UA_ValueCallback{NULL, onDataWrite}));
                                      
            xSemaphoreGive(open62541Mutex);
        }
    }

	void B4ROPEN62541::AddTriggerNode(B4RString* NodeIdentifier, B4RString* DisplayName, B4RString* InitialValue) {
		if (server == NULL) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			
			UA_VariableAttributes vAttr = UA_VariableAttributes_default;
			UA_String val = UA_STRING((char*)InitialValue->data);
			UA_Variant_setScalar(&vAttr.value, &val, &UA_TYPES[UA_TYPES_STRING]);
			vAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			vAttr.description = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
			vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
			
			// Use a persistent NodeId instance copy for tracking
			UA_NodeId targetNodeId = CreateNodeId(NodeIdentifier);
			const char* stringIdentifier = GetStringIdentifier(NodeIdentifier);
			
			// Add the variable node to the address space first!
			UA_Server_addVariableNode(server, 
									  targetNodeId, 
									  folderNodeId,                               
									  UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
									  UA_QUALIFIEDNAME(1, (char*)stringIdentifier), 
									  UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
									  vAttr, NULL, NULL);

            // Attach string interceptor
			UA_ValueCallback callback;

			callback.onRead = NULL;
			callback.onWrite = [](UA_Server *server, const UA_NodeId *sessionId, 
								 void *sessionContext, const UA_NodeId *nodeId, 
								 void *nodeContext, const UA_NumericRange *range, 
								 const UA_DataValue *data) {
				
				if (data && data->hasValue) {
					memset(globalTriggerBuffer, 0, sizeof(globalTriggerBuffer));
					globalTriggerLength = 0;

					// Case A: The client sent an actual String payload (e.g., "STOP")
					if (data->value.type == &UA_TYPES[UA_TYPES_STRING]) {
						UA_String *uaStr = (UA_String*)data->value.data;
						size_t maxAllowed = sizeof(globalTriggerBuffer) - 1;
						globalTriggerLength = (uaStr->length < maxAllowed) ? uaStr->length : maxAllowed;
						
						if (globalTriggerLength > 0 && uaStr->data != NULL) {
							memcpy(globalTriggerBuffer, uaStr->data, globalTriggerLength);
						}
					}
					// Case B: The client sent a numeric Integer (e.g., B4J passing raw 68)
					else if (data->value.type == &UA_TYPES[UA_TYPES_INT32]) {
						int32_t incomingInt = *(int32_t*)data->value.data;
						globalTriggerLength = snprintf((char*)globalTriggerBuffer, sizeof(globalTriggerBuffer) - 1, "%d", incomingInt);
					}
					// Case C: The client sent a floating-point Float (32-bit float)
					else if (data->value.type == &UA_TYPES[UA_TYPES_FLOAT]) {
						float incomingFloat = *(float*)data->value.data;
						globalTriggerLength = snprintf((char*)globalTriggerBuffer, sizeof(globalTriggerBuffer) - 1, "%.2f", incomingFloat);
					}
					// Case D: The client sent a double-precision Float (64-bit double)
					else if (data->value.type == &UA_TYPES[UA_TYPES_DOUBLE]) {
						double incomingDouble = *(double*)data->value.data;
						globalTriggerLength = snprintf((char*)globalTriggerBuffer, sizeof(globalTriggerBuffer) - 1, "%.2f", incomingDouble);
					}
					// Case E: Alternative standard Integer sizes
					else if (data->value.type == &UA_TYPES[UA_TYPES_INT16]) {
						int16_t incomingInt = *(int16_t*)data->value.data;
						globalTriggerLength = snprintf((char*)globalTriggerBuffer, sizeof(globalTriggerBuffer) - 1, "%d", incomingInt);
					}

					// Case F: The client sent an official OPC UA ByteString / Binary payload package
					else if (data->value.type == &UA_TYPES[UA_TYPES_BYTESTRING]) {
						UA_ByteString *uaBytes = (UA_ByteString*)data->value.data;
						size_t maxAllowed = sizeof(globalTriggerBuffer) - 1;
						globalTriggerLength = (uaBytes->length < maxAllowed) ? uaBytes->length : maxAllowed;
						
						if (globalTriggerLength > 0 && uaBytes->data != NULL) {
							memcpy(globalTriggerBuffer, uaBytes->data, globalTriggerLength);
						}
					}
					// Case G: Explicit multi-element native Byte arrays (Byte[])
					else if (!UA_Variant_isScalar(&data->value) && data->value.type == &UA_TYPES[UA_TYPES_BYTE]) {
						size_t maxAllowed = sizeof(globalTriggerBuffer) - 1;
						globalTriggerLength = (data->value.arrayLength < maxAllowed) ? data->value.arrayLength : maxAllowed;
						
						if (globalTriggerLength > 0 && data->value.data != NULL) {
							memcpy(globalTriggerBuffer, data->value.data, globalTriggerLength);
						}
					}
				
					// Explicitly cast to char* or cast indices to apply string seal
					((char*)globalTriggerBuffer)[globalTriggerLength] = '\0';
					scadaTriggerCalled = true; 
				}
			};
			UA_Server_setVariableNode_valueCallback(server, targetNodeId, callback);

			xSemaphoreGive(open62541Mutex);
		}
	}

    // ============================================================================
    // READ/WRITE HELPER
    // ============================================================================

	void ParseNodeId(
		B4RString* NodeId,
		int& NamespaceIndex,
		const char*& NodeIdentifier)
	{
		NamespaceIndex = 1;
		NodeIdentifier = (char*)NodeId->data;

		char* text = (char*)NodeId->data;

		if (strncmp(text, "ns=", 3) == 0) {

			NamespaceIndex = atoi(text + 3);

			char* separator = strstr(text, ";s=");

			if (separator != NULL) {
				NodeIdentifier = separator + 3;
			}
		}
	}

	void ParseNumericNodeId(
		B4RString* NodeId,
		int& NamespaceIndex,
		int& NumericIdentifier)
	{
		// Default fallback if parsing fails (Namespace 0, ID 0)
		NamespaceIndex = 0;
		NumericIdentifier = 0;

		char* text = (char*)NodeId->data;

		if (strncmp(text, "ns=", 3) == 0) {
			NamespaceIndex = atoi(text + 3);

			// Find the numeric separator ";i="
			char* separator = strstr(text, ";i=");
			if (separator != NULL) {
				NumericIdentifier = atoi(separator + 3);
			}
		}
	}

    // ============================================================================
    // WRITE
    // ============================================================================

	void B4ROPEN62541::WriteFloat(B4RString* NodeIdentifier, float NewValue) {
		if (server == NULL || !globalIsReady) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int ns; const char* id; ParseNodeId(NodeIdentifier, ns, id);
			UA_NodeId targetNodeId = UA_NODEID_STRING(ns, (char*)id);
			
			// Initialize a full DataValue container instead of just a Variant
			UA_DataValue myDataValue;
			UA_DataValue_init(&myDataValue);
			
			// Set float value inside the container's variant
			UA_Variant_setScalar(&myDataValue.value, &NewValue, &UA_TYPES[UA_TYPES_FLOAT]);
			myDataValue.hasValue = true;
			
			// Fetch current system time
			UA_DateTime nowTime = UA_DateTime_now();
		
			// Inject the current server timestamp (real time) into the payload
			myDataValue.sourceTimestamp = nowTime;
			myDataValue.hasSourceTimestamp = true;

			// Set the Server Timestamp 
			myDataValue.serverTimestamp = nowTime;
			myDataValue.hasServerTimestamp = true;
        
			// Lower flag
			isOnDataWrite = false;
			
			// Use writeDataValue to pass the combined structure (Value + Timestamp)
			UA_Server_writeDataValue(server, targetNodeId, myDataValue);
			
			// Raise flag
			isOnDataWrite = true;
			
			xSemaphoreGive(open62541Mutex);
		}
	}

	void B4ROPEN62541::WriteInt(B4RString* NodeIdentifier, Int NewValue) {
		if (server == NULL || !globalIsReady) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int ns; const char* id; ParseNodeId(NodeIdentifier, ns, id);
			UA_NodeId targetNodeId = UA_NODEID_STRING(ns, (char*)id);
			
			int32_t iVal = (int32_t)NewValue;
			
			UA_DataValue myDataValue;
			UA_DataValue_init(&myDataValue);
			UA_Variant_setScalar(&myDataValue.value, &iVal, &UA_TYPES[UA_TYPES_INT32]);
			myDataValue.hasValue = true;
			UA_DateTime nowTime = UA_DateTime_now();
			myDataValue.sourceTimestamp = nowTime;
			myDataValue.hasSourceTimestamp = true;
			myDataValue.serverTimestamp = nowTime;
			myDataValue.hasServerTimestamp = true;
        			
			isOnDataWrite = false;
			UA_Server_writeDataValue(server, targetNodeId, myDataValue);
			isOnDataWrite = true;
			
			xSemaphoreGive(open62541Mutex);
		}
	}

	void B4ROPEN62541::WriteUInt(B4RString* NodeIdentifier, UInt NewValue) {
		if (server == NULL || !globalIsReady) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int ns; const char* id; ParseNodeId(NodeIdentifier, ns, id);
			UA_NodeId targetNodeId = UA_NODEID_STRING(ns, (char*)id);
			
			uint32_t uiVal = (uint32_t)NewValue;
			
			UA_DataValue myDataValue;
			UA_DataValue_init(&myDataValue);
			UA_Variant_setScalar(&myDataValue.value, &uiVal, &UA_TYPES[UA_TYPES_UINT32]);
			myDataValue.hasValue = true;
			myDataValue.sourceTimestamp = UA_DateTime_now();
			myDataValue.hasSourceTimestamp = true;
			
			isOnDataWrite = false;
			UA_Server_writeDataValue(server, targetNodeId, myDataValue);
			isOnDataWrite = true;
			
			xSemaphoreGive(open62541Mutex);
		}
	}

	void B4ROPEN62541::WriteBoolean(B4RString* NodeIdentifier, bool NewValue) {
		if (server == NULL || !globalIsReady) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int ns; const char* id; ParseNodeId(NodeIdentifier, ns, id);
			UA_NodeId targetNodeId = UA_NODEID_STRING(ns, (char*)id);
			
			UA_Boolean bVal = NewValue;
			
			UA_DataValue myDataValue;
			UA_DataValue_init(&myDataValue);
			UA_Variant_setScalar(&myDataValue.value, &bVal, &UA_TYPES[UA_TYPES_BOOLEAN]);
			myDataValue.hasValue = true;
			myDataValue.sourceTimestamp = UA_DateTime_now();
			myDataValue.hasSourceTimestamp = true;
			
			isOnDataWrite = false;
			UA_Server_writeDataValue(server, targetNodeId, myDataValue);
			isOnDataWrite = true;
			
			xSemaphoreGive(open62541Mutex);
		}
	}

	void B4ROPEN62541::WriteLong(B4RString* NodeIdentifier, Long NewValue) {
		if (server == NULL || !globalIsReady) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int ns; const char* id; ParseNodeId(NodeIdentifier, ns, id);
			UA_NodeId targetNodeId = UA_NODEID_STRING(ns, (char*)id);
			
			int64_t lVal = (int64_t)NewValue;
			
			UA_DataValue myDataValue;
			UA_DataValue_init(&myDataValue);
			UA_Variant_setScalar(&myDataValue.value, &lVal, &UA_TYPES[UA_TYPES_INT64]);
			myDataValue.hasValue = true;
			myDataValue.sourceTimestamp = UA_DateTime_now();
			myDataValue.hasSourceTimestamp = true;
			
			isOnDataWrite = false;
			UA_Server_writeDataValue(server, targetNodeId, myDataValue);
			isOnDataWrite = true;
			
			xSemaphoreGive(open62541Mutex);
		}
	}

	void B4ROPEN62541::WriteULong(B4RString* NodeIdentifier, ULong NewValue) {
		if (server == NULL || !globalIsReady) return;
		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int ns; const char* id; ParseNodeId(NodeIdentifier, ns, id);
			UA_NodeId targetNodeId = UA_NODEID_STRING(ns, (char*)id);
			
			uint64_t ulVal = (uint64_t)NewValue;
			
			UA_DataValue myDataValue;
			UA_DataValue_init(&myDataValue);
			UA_Variant_setScalar(&myDataValue.value, &ulVal, &UA_TYPES[UA_TYPES_UINT64]);
			myDataValue.hasValue = true;
			myDataValue.sourceTimestamp = UA_DateTime_now();
			myDataValue.hasSourceTimestamp = true;
			
			isOnDataWrite = false;
			UA_Server_writeDataValue(server, targetNodeId, myDataValue);
			isOnDataWrite = true;
			
			xSemaphoreGive(open62541Mutex);
		}
	}

    void B4ROPEN62541::WriteString(B4RString* NodeIdentifier, B4RString* NewValue) {
        if (server == NULL || !globalIsReady || NewValue == NULL) return;

        if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int NamespaceIndex;
			const char* Identifier;
			ParseNodeId(NodeIdentifier, NamespaceIndex, Identifier);

			UA_NodeId targetNodeId = UA_NODEID_STRING(NamespaceIndex, (char*)Identifier);
            UA_Variant currentValue;
            UA_Variant_init(&currentValue);
            
            if (UA_Server_readValue(server, targetNodeId, &currentValue) == UA_STATUSCODE_GOOD) {
                if (currentValue.type == &UA_TYPES[UA_TYPES_STRING]) {
                    UA_String uaStr;
                    uaStr.length = strlen((char*)NewValue->data);
                    uaStr.data = (UA_Byte*)NewValue->data;
                    
                    UA_DataValue myDataValue;
                    UA_DataValue_init(&myDataValue);
                    UA_Variant_setScalar(&myDataValue.value, &uaStr, &UA_TYPES[UA_TYPES_STRING]);
                    myDataValue.hasValue = true;
                    myDataValue.sourceTimestamp = UA_DateTime_now();
                    myDataValue.hasSourceTimestamp = true;
                    
                    UA_Server_writeDataValue(server, targetNodeId, myDataValue);
                }
            }
            UA_Variant_clear(&currentValue);
            xSemaphoreGive(open62541Mutex);
        }
    }

	void B4ROPEN62541::WriteByteString(B4RString* NodeIdentifier, ArrayByte* Data) {
		if (server == NULL || !globalIsReady || Data == NULL) return;

		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int NamespaceIndex;
			const char* Identifier;
			ParseNodeId(NodeIdentifier, NamespaceIndex, Identifier);

			UA_NodeId targetNodeId = UA_NODEID_STRING(NamespaceIndex, (char*)Identifier);

			UA_ByteString byteString;
			byteString.length = Data->length;
			byteString.data = (UA_Byte*)Data->data;

			UA_DataValue myDataValue;
			UA_DataValue_init(&myDataValue);
			
			// Note: setScalarCopy handles deep allocation safely for internal byte ranges
			UA_Variant_setScalarCopy(&myDataValue.value, &byteString, &UA_TYPES[UA_TYPES_BYTESTRING]);
			myDataValue.hasValue = true;
			myDataValue.sourceTimestamp = UA_DateTime_now();
			myDataValue.hasSourceTimestamp = true;

			UA_Server_writeDataValue(server, targetNodeId, myDataValue);

			UA_Variant_clear(&myDataValue.value);

			xSemaphoreGive(open62541Mutex);
		}
	}

    // ============================================================================
    // READ
    // ============================================================================

	B4RString* B4ROPEN62541::ReadNumeric(B4RString* NodeIdentifier) {
		PrintToMemory pm;
		B4RString* s = B4RString::PrintableToString(NULL);
		bool dataCaptured = false;
		char numericBuffer[64]; 
		memset(numericBuffer, 0, sizeof(numericBuffer));

		if (server != NULL && xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int NamespaceIndex;
			int Identifier;
			ParseNumericNodeId(NodeIdentifier, NamespaceIndex, Identifier);

			UA_NodeId targetNodeId = UA_NODEID_NUMERIC(NamespaceIndex, Identifier);
			UA_Variant outVariant;
			UA_Variant_init(&outVariant);
			
			UA_StatusCode retval = UA_Server_readValue(server, targetNodeId, &outVariant);
			if (retval == UA_STATUSCODE_GOOD && UA_Variant_isScalar(&outVariant)) {
				dataCaptured = true;
				if (outVariant.type == &UA_TYPES[UA_TYPES_INT32]) { itoa(*(UA_Int32*)outVariant.data, numericBuffer, 10); pm.print(numericBuffer); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_BOOLEAN]) { pm.print(*(UA_Boolean*)outVariant.data ? "True" : "False"); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_FLOAT]) { dtostrf(*(UA_Float*)outVariant.data, 1, 4, numericBuffer); pm.print(numericBuffer); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_DOUBLE]) { dtostrf(*(UA_Double*)outVariant.data, 1, 4, numericBuffer); pm.print(numericBuffer); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_STRING]) { UA_String* uaStr = (UA_String*)outVariant.data; for (size_t i = 0; i < uaStr->length; i++) pm.print((char)uaStr->data[i]); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_DATETIME]) {
					UA_DateTime rawTime = *(UA_DateTime*)outVariant.data;
					time_t unixTime = (time_t)((rawTime - 116444736000000000LL) / 10000000LL);
					struct tm* timeInfo = gmtime(&unixTime);
					if (timeInfo != NULL) { strftime(numericBuffer, sizeof(numericBuffer), "%Y-%m-%d %H:%M:%SZ", timeInfo); pm.print(numericBuffer); }
				}
				else { pm.print("[Unsupported Type]"); }
			}
			UA_Variant_clear(&outVariant);
			xSemaphoreGive(open62541Mutex);
		}
		if (!dataCaptured) pm.print(server == NULL ? "[Server Null]" : "[Read Error]");
		StackMemory::buffer[StackMemory::cp++] = 0;
		return s;
	}

	B4RString* B4ROPEN62541::ReadString(B4RString* NodeIdentifier) {
		PrintToMemory pm;
		B4RString* s = B4RString::PrintableToString(NULL);
		bool dataCaptured = false;
		char numericBuffer[64]; 
		memset(numericBuffer, 0, sizeof(numericBuffer));

		if (server != NULL && xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int NamespaceIndex;
			const char* Identifier;
			ParseNodeId(NodeIdentifier, NamespaceIndex, Identifier);

			UA_NodeId targetNodeId = UA_NODEID_STRING(NamespaceIndex, (char*)Identifier);
			UA_Variant outVariant;
			UA_Variant_init(&outVariant);
			
			UA_StatusCode retval = UA_Server_readValue(server, targetNodeId, &outVariant);
			if (retval == UA_STATUSCODE_GOOD && UA_Variant_isScalar(&outVariant)) {
				dataCaptured = true;
				if (outVariant.type == &UA_TYPES[UA_TYPES_INT32]) { itoa(*(UA_Int32*)outVariant.data, numericBuffer, 10); pm.print(numericBuffer); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_BOOLEAN]) { pm.print(*(UA_Boolean*)outVariant.data ? "True" : "False"); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_FLOAT]) { dtostrf(*(UA_Float*)outVariant.data, 1, 4, numericBuffer); pm.print(numericBuffer); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_DOUBLE]) { dtostrf(*(UA_Double*)outVariant.data, 1, 4, numericBuffer); pm.print(numericBuffer); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_STRING]) { UA_String* uaStr = (UA_String*)outVariant.data; for (size_t i = 0; i < uaStr->length; i++) pm.print((char)uaStr->data[i]); }
				else if (outVariant.type == &UA_TYPES[UA_TYPES_DATETIME]) {
					UA_DateTime rawTime = *(UA_DateTime*)outVariant.data;
					time_t unixTime = (time_t)((rawTime - 116444736000000000LL) / 10000000LL);
					struct tm* timeInfo = gmtime(&unixTime);
					if (timeInfo != NULL) { strftime(numericBuffer, sizeof(numericBuffer), "%Y-%m-%d %H:%M:%SZ", timeInfo); pm.print(numericBuffer); }
				}
				else { pm.print("[Unsupported Type]"); }
			}
			UA_Variant_clear(&outVariant);
			xSemaphoreGive(open62541Mutex);
		}
		if (!dataCaptured) pm.print(server == NULL ? "[Server Null]" : "[Read Error]");
		StackMemory::buffer[StackMemory::cp++] = 0;
		return s;
	}

	ArrayByte* B4ROPEN62541::ReadByteString(B4RString* NodeIdentifier) {
		if (server == NULL || !globalIsReady) return NULL;

		ArrayByte* result = NULL;

		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int NamespaceIndex;
			const char* Identifier;
			ParseNodeId(NodeIdentifier, NamespaceIndex, Identifier);

			UA_NodeId targetNodeId = UA_NODEID_STRING(NamespaceIndex, (char*)Identifier);

			UA_Variant value;
			UA_Variant_init(&value);

			if (UA_Server_readValue(server, targetNodeId, &value) == UA_STATUSCODE_GOOD) {
				if (value.type == &UA_TYPES[UA_TYPES_BYTESTRING] &&
					value.data != NULL) {

					UA_ByteString* byteString = (UA_ByteString*)value.data;

					result = CreateStackMemoryObject(ArrayByte);
					result->data = (Byte*)byteString->data;
					result->length = byteString->length;
				}
			}

			UA_Variant_clear(&value);
			xSemaphoreGive(open62541Mutex);
		}

		return result;
	}

	void B4ROPEN62541::ReadDataValue(B4RString* NodeIdentifier) {
		if (server == NULL) {
			::Serial.println("ReadDataValue Error: Server is NULL");
			return;
		}

		if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
			int NamespaceIndex;
			const char* Identifier;
			ParseNodeId(NodeIdentifier, NamespaceIndex, Identifier);

			UA_NodeId targetNodeId = UA_NODEID_STRING(NamespaceIndex, (char*)Identifier);
			
			// 1. Configure a standard read item criteria targeting the VALUE attribute space
			UA_ReadValueId rvi;
			UA_ReadValueId_init(&rvi);
			rvi.nodeId = targetNodeId;
			rvi.attributeId = UA_ATTRIBUTEID_VALUE;
			
			// 2. Perform a low-level direct read, explicitly requesting BOTH timestamp blocks
			UA_DataValue dbSnapshot = UA_Server_read(server, &rvi, UA_TIMESTAMPSTORETURN_BOTH);
			
			::Serial.println("=========== LOCAL DATABASE CACHE SNAPSHOT ===========");
			::Serial.print("Node Identifier: "); ::Serial.println(Identifier);
			
			// FIX: Changed .statusCode to .status to match your open62541 version struct layout
			::Serial.print("Status Code:     0x"); ::Serial.println(dbSnapshot.status, HEX);
			
			// 3. Extract and display the raw variant value matching your ReadString logic
			if (dbSnapshot.hasValue && UA_Variant_isScalar(&dbSnapshot.value)) {
				char numericBuffer[64];
				memset(numericBuffer, 0, sizeof(numericBuffer));
				
				if (dbSnapshot.value.type == &UA_TYPES[UA_TYPES_FLOAT]) {
					dtostrf(*(UA_Float*)dbSnapshot.value.data, 1, 4, numericBuffer);
					::Serial.print("Current Value:   "); ::Serial.println(numericBuffer);
				}
				else if (dbSnapshot.value.type == &UA_TYPES[UA_TYPES_INT32]) {
					itoa(*(UA_Int32*)dbSnapshot.value.data, numericBuffer, 10);
					::Serial.print("Current Value:   "); ::Serial.println(numericBuffer);
				}
				else if (dbSnapshot.value.type == &UA_TYPES[UA_TYPES_BOOLEAN]) {
					::Serial.print("Current Value:   "); ::Serial.println(*(UA_Boolean*)dbSnapshot.value.data ? "True" : "False");
				}
				else if (dbSnapshot.value.type == &UA_TYPES[UA_TYPES_STRING]) {
					UA_String* uaStr = (UA_String*)dbSnapshot.value.data;
					::Serial.print("Current Value:   ");
					for (size_t i = 0; i < uaStr->length; i++) ::Serial.print((char)uaStr->data[i]);
					::Serial.println();
				}
			} else {
				::Serial.println("Current Value:   [Empty or Null]");
			}
			
			// 4. Decode and check the Source Timestamp profile status flags
			::Serial.print("hasSourceTime:   "); ::Serial.println((int)dbSnapshot.hasSourceTimestamp);
			if (dbSnapshot.hasSourceTimestamp) {
				uint64_t srcTimeRaw = (uint64_t)dbSnapshot.sourceTimestamp;
				uint32_t hiSrc = (uint32_t)(srcTimeRaw >> 32); 
				uint32_t loSrc = (uint32_t)(srcTimeRaw & 0xFFFFFFFF);
				char hexSrc[32]; sprintf(hexSrc, "0x%08X%08X", hiSrc, loSrc);
				::Serial.print("SourceTime Hex:  "); ::Serial.println(hexSrc);
			}
			
			// 5. Decode and check the Server Timestamp profile status flags
			::Serial.print("hasServerTime:   "); ::Serial.println((int)dbSnapshot.hasServerTimestamp);
			if (dbSnapshot.hasServerTimestamp) {
				uint64_t srvTimeRaw = (uint64_t)dbSnapshot.serverTimestamp;
				uint32_t hiSrv = (uint32_t)(srvTimeRaw >> 32); 
				uint32_t loSrv = (uint32_t)(srvTimeRaw & 0xFFFFFFFF);
				char hexSrv[32]; sprintf(hexSrv, "0x%08X%08X", hiSrv, loSrv);
				::Serial.print("ServerTime Hex:  "); ::Serial.println(hexSrv);
			}
			::Serial.println("=====================================================");
			
			// 6. Clean the allocated structure resource container
			UA_DataValue_clear(&dbSnapshot);
			xSemaphoreGive(open62541Mutex);
		}
	}

    B4R::ArrayByte* B4ROPEN62541::ReadDataAttribute(B4RString* NodeIdentifier, int Attr) {
        // Instantiate the B4R byte array wrapper utilizing your exact macro pattern
        B4R::ArrayByte* result = NULL;
        result = CreateStackMemoryObject(ArrayByte);
        result->data = NULL;
        result->length = 0;
        
        if (server == NULL || !globalIsReady) {
            return result;
        }

        bool dataPacked = false;

        if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
            int ns; const char* id; ParseNodeId(NodeIdentifier, ns, id);
            UA_NodeId targetNodeId = UA_NODEID_STRING(ns, (char*)id);
            
            // 1. Fetch full data model snapshot straight from the cache address space
            UA_ReadValueId rvi;
            UA_ReadValueId_init(&rvi);
            rvi.nodeId = targetNodeId;
            rvi.attributeId = UA_ATTRIBUTEID_VALUE;
            UA_DataValue dbSnapshot = UA_Server_read(server, &rvi, UA_TIMESTAMPSTORETURN_BOTH);
            
            if (dbSnapshot.status == UA_STATUSCODE_GOOD) {
                // TYPE 0: Extract the raw Variable value payload
                if (Attr == 0 && dbSnapshot.hasValue && UA_Variant_isScalar(&dbSnapshot.value)) {
                    if (dbSnapshot.value.type == &UA_TYPES[UA_TYPES_FLOAT]) {
                        result->length = sizeof(float);
                        // Allocate the raw memory payload block inside B4R Stack Memory space
                        result->data = (Byte*)B4R::StackMemory::buffer + B4R::StackMemory::cp;
                        B4R::StackMemory::cp += result->length;
                        
                        memcpy(result->data, dbSnapshot.value.data, result->length);
                        dataPacked = true;
                    }
                    else if (dbSnapshot.value.type == &UA_TYPES[UA_TYPES_INT32]) {
                        result->length = sizeof(int32_t);
                        result->data = (Byte*)B4R::StackMemory::buffer + B4R::StackMemory::cp;
                        B4R::StackMemory::cp += result->length;
                        
                        memcpy(result->data, dbSnapshot.value.data, result->length);
                        dataPacked = true;
                    }
                    else if (dbSnapshot.value.type == &UA_TYPES[UA_TYPES_BOOLEAN]) {
                        result->length = sizeof(UA_Boolean);
                        result->data = (Byte*)B4R::StackMemory::buffer + B4R::StackMemory::cp;
                        B4R::StackMemory::cp += result->length;
                        
                        memcpy(result->data, dbSnapshot.value.data, result->length);
                        dataPacked = true;
                    }
                }
                // TYPE 1: Extract 64-bit Source Timestamp (8 bytes)
                else if (Attr == 1 && dbSnapshot.hasSourceTimestamp) {
                    result->length = sizeof(UA_DateTime);
                    result->data = (Byte*)B4R::StackMemory::buffer + B4R::StackMemory::cp;
                    B4R::StackMemory::cp += result->length;
                    
                    memcpy(result->data, &dbSnapshot.sourceTimestamp, result->length);
                    dataPacked = true;
                }
                // TYPE 2: Extract 64-bit Server Timestamp (8 bytes)
                else if (Attr == 2 && dbSnapshot.hasServerTimestamp) {
                    result->length = sizeof(UA_DateTime);
                    result->data = (Byte*)B4R::StackMemory::buffer + B4R::StackMemory::cp;
                    B4R::StackMemory::cp += result->length;
                    
                    memcpy(result->data, &dbSnapshot.serverTimestamp, result->length);
                    dataPacked = true;
                }
                // TYPE 3: Extract 32-bit Status Register Code (4 bytes)
                else if (Attr == 3) {
                    result->length = sizeof(UA_StatusCode);
                    result->data = (Byte*)B4R::StackMemory::buffer + B4R::StackMemory::cp;
                    B4R::StackMemory::cp += result->length;
                    
                    memcpy(result->data, &dbSnapshot.status, result->length);
                    dataPacked = true;
                }
            }
            
            UA_DataValue_clear(&dbSnapshot);
            xSemaphoreGive(open62541Mutex);
        }
        
        // Reset properties cleanly if lookup criteria fails
        if (!dataPacked) {
            result->data = NULL;
            result->length = 0;
        }
        return result;
    }

    // ============================================================================
    // OPC UA Callbacks & Event Handling
    // ============================================================================

    UA_StatusCode B4ROPEN62541::scadaTriggerCallback(
        UA_Server *server, const UA_NodeId *sessionId, void *sessionContext,
        const UA_NodeId *methodId, void *methodContext, const UA_NodeId *objectId,
        void *objectContext, size_t inputSize, const UA_Variant *input,
        size_t outputSize, UA_Variant *output) {
        
		// Signal the B4R looper that the Trigger node was written.
        scadaTriggerCalled = true; 
		
		// Return a good status code back to the B4J Milo Client immediately
        return UA_STATUSCODE_GOOD;
    }

    // ============================================================================
	// Getter/Setter abstractions
    // ============================================================================

	void B4ROPEN62541::setIsReady(bool value) { globalIsReady = value; }
	bool B4ROPEN62541::getIsReady() { return globalIsReady; }

}
