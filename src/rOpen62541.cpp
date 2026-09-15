/*
 * rOpen62541.cpp
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

// Trigger Method initiated from OPCUA client ("Trigger"
static volatile bool scadaTriggerCalled = false; 		// Flag for method calls
static uint8_t globalTriggerBuffer[64]; 				// Cache for incoming raw byte payloads (String, Int, Float, etc.)
static volatile size_t globalTriggerLength = 0;

// Method Call initiated from OPCUA client (Method Call)
static volatile bool scadaMethodCalled = false;			// Flag for method calls
static uint8_t globalMethodInputBuffer[64];      		// Cache for incoming method arguments
static volatile size_t globalMethodInputLength = 0;
static volatile int32_t globalMethodOutputResult = 0;	// Return parameter sent back to SCADA

// User Auth Management
static char uaUsername[32] = "";
static char uaPassword[32] = "";
static bool useAuthentication = false;

namespace B4R {

    // ============================================================================
    // B4R Library Lifetime & Hook Management
    // ============================================================================

	void B4ROPEN62541::Initialize(
		B4RString* LocalIP, 
		int Port,
		B4RString* Username, 
		B4RString* Password, 
		SubVoidArray MethodTriggerSub) {
		
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

		this->MethodTriggerSub = MethodTriggerSub; 

		if (open62541Mutex == NULL) {
			open62541Mutex = xSemaphoreCreateMutex();
		}
		
		FunctionUnion fu;
		fu.PollerFunction = looper;
		pollers.add(fu, this);

		// Free rtos multitasking background engine initialization
		// Offloads the resource-intensive open62541 stack processing completely
		// to ESP32 Core 0 (Network Processor), preventing blocking loops on Core 1.
		xTaskCreatePinnedToCore(
			B4ROPEN62541::opcuaServerTask,   // Task callback entry routine
			"OPCUA_Server_Task",             // Debugging name tag
			64 * 1024,                       // Stack allocation allocation (64KB out of 8MB Octal PSRAM pool)
			NULL,                            // Context parameters pointer mapping
			1,                               // Task priority ranking schedule
			NULL,                            // Task tracking reference handle
			0                                // Core Assignment: Core 0 (Network management)
		);
		
		initialized = true;
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
            if (me->MethodTriggerSub != NULL) {
                const UInt cp = B4R::StackMemory::cp;
                ArrayByte* arr = CreateStackMemoryObject(ArrayByte);
                arr->data = (Byte*)globalTriggerBuffer;
                arr->length = globalTriggerLength;
                me->MethodTriggerSub(arr);
                B4R::StackMemory::cp = cp;
            }
        }

        // CASE 2: Native Executable Method Call (scadaMethodCalled)
        if (scadaMethodCalled) {
            scadaMethodCalled = false;
            if (me->MethodCallSub != NULL) {
                const UInt cp = B4R::StackMemory::cp;
                ArrayByte* arr = CreateStackMemoryObject(ArrayByte);
                arr->data = (Byte*)globalMethodInputBuffer;
                arr->length = globalMethodInputLength;
                me->MethodCallSub(arr); 
                B4R::StackMemory::cp = cp;
            }
        }
    }

    // ============================================================================
    // OPC UA Server Execution Task (Runs completely on Core 0)
    // ============================================================================

    void B4ROPEN62541::opcuaServerTask(void *pvParameters) {
        // This catches the very first boot messages generated by server instantiation.
        freopen("/dev/null", "w", stdout);

        server = UA_Server_new();
        if (server == NULL) {
            ::Serial.println("[opcuaServerTask][E] Memory allocation failed!");
            vTaskDelete(NULL);
            return;
        }

		// Create server cofig
        UA_ServerConfig *config = UA_Server_getConfig(server);

        // Initialize with standard minimal configuration parameters
        UA_ServerConfig_setMinimal(config, serverPort, NULL);

        // Server-side wi-fi stability overrides for relaxed keep-alives
        // Tell the server to allow loose client subscription publishing intervals
        config->publishingIntervalLimits.min = 500.0;    // Minimum 500ms
        config->publishingIntervalLimits.max = 60000.0;  // Allow up to 60 seconds
                
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
        
        // Build the basic folder infrastructure
        buildOpcUaTree();
        
        // Startup the socket server loops
        // Set the global discovery interval directly on the root config struct if needed,
        // or simply configure the session pool limits directly:
        config->maxSessions = 10; 

        if (UA_Server_run_startup(server) == UA_STATUSCODE_GOOD) {
            // Note: Use ::Serial directly here because stdout is now muted!
            ::Serial.println("[opcuaServerTask] OPC UA Server successfully running on Core 0!");
            globalIsReady = true; 
        } else {
            ::Serial.println("[opcuaServerTask][E] Critical open62541 Startup Sequence Failed!");
            globalIsReady = false;
        }

        // Infinite background processing loop (Simple, clean, no POSIX dup errors!)
        while(true) {
            if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
                UA_Server_run_iterate(server, true);
                xSemaphoreGive(open62541Mutex); 
            }
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
        
        UA_Server_addObjectNode(server, 
                                UA_NODEID_STRING(1, (char*)"Factory_Floor"), 
                                UA_NODEID_NUMERIC(0, UA_NS0ID_OBJECTSFOLDER), 
                                UA_NODEID_NUMERIC(0, UA_NS0ID_ORGANIZES),     
                                UA_QUALIFIEDNAME(1, (char*)"Factory_Floor"),         
                                UA_NODEID_NUMERIC(0, UA_NS0ID_BASEOBJECTTYPE),
                                oAttr, NULL, &folderNodeId);
    }

    // ============================================================================
    // Flexible Dynamic Node Management (Public Methods API)
    // ============================================================================

    void B4ROPEN62541::AddMethodNode(B4RString* MethodName, B4RString* DisplayName, SubVoidArray MethodCallSub) {
        if (server == NULL) return;
        this->MethodCallSub = MethodCallSub;

        if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
            
            UA_Argument inputArgument;
            UA_Argument_init(&inputArgument);
            inputArgument.description = UA_LOCALIZEDTEXT("en-US", "Command Payload Code");
            inputArgument.name = UA_STRING("InputData");
            inputArgument.dataType = UA_TYPES[UA_TYPES_BYTESTRING].typeId; 
            inputArgument.valueRank = UA_VALUERANK_SCALAR;

            UA_Argument outputArgument;
            UA_Argument_init(&outputArgument);
            outputArgument.description = UA_LOCALIZEDTEXT("en-US", "Execution Return Code");
            outputArgument.name = UA_STRING("ReturnStatus");
            outputArgument.dataType = UA_TYPES[UA_TYPES_INT32].typeId;
            outputArgument.valueRank = UA_VALUERANK_SCALAR;

            UA_MethodAttributes mAttr = UA_MethodAttributes_default;
            mAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
            mAttr.executable = true;
            mAttr.userExecutable = true;

            UA_Server_addMethodNode(server, 
                UA_NODEID_STRING(1, (char*)MethodName->data),
                UA_NODEID_STRING(1, (char*)"Factory_Floor"),
                UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT),
                UA_QUALIFIEDNAME(1, (char*)MethodName->data),
                mAttr, &B4ROPEN62541::opcuaMethodBridge, 
                1, &inputArgument, 
                1, &outputArgument, 
                NULL, NULL);

            xSemaphoreGive(open62541Mutex);
        }
    }

    UA_StatusCode B4ROPEN62541::opcuaMethodBridge(UA_Server *server,
        const UA_NodeId *sessionId, void *sessionContext,
        const UA_NodeId *methodId, void *methodContext,
        const UA_NodeId *objectId, void *objectContext,
        size_t inputSize, const UA_Variant *input,
        size_t outputSize, UA_Variant *output) {
        
        // Verify that the client passed the required argument
        if (inputSize > 0 && input != NULL) {
            
            // Access the first element of the array directly.
            const UA_Variant *firstArg = input; 
            
            // Validate the type and data pointers directly
            if (firstArg->type != NULL && firstArg->data != NULL) {
                size_t rawLength = 0;
                const uint8_t* rawData = NULL;

                // Case A: Parameter arrived formatted as a standard String
                if (firstArg->type == &UA_TYPES[UA_TYPES_STRING]) {
                    UA_String *uaStr = (UA_String*)firstArg->data;
                    rawLength = uaStr->length;
                    rawData = (const uint8_t*)uaStr->data;
                }
                // Case B: Parameter arrived formatted as a raw ByteString array
                else if (firstArg->type == &UA_TYPES[UA_TYPES_BYTESTRING]) {
                    UA_ByteString *uaBytes = (UA_ByteString*)firstArg->data;
                    rawLength = uaBytes->length;
                    rawData = (const uint8_t*)uaBytes->data;
                }

                // Copy extracted raw bytes into the global concrete array buffer (64 bytes)
                if (rawData != NULL && rawLength > 0) {
                    memset(globalMethodInputBuffer, 0, sizeof(globalMethodInputBuffer));
                    
                    size_t maxAllowed = sizeof(globalMethodInputBuffer) - 1;
                    globalMethodInputLength = (rawLength < maxAllowed) ? rawLength : maxAllowed;
                    
                    memcpy(globalMethodInputBuffer, rawData, globalMethodInputLength);
                    globalMethodInputBuffer[globalMethodInputLength] = '\0'; // Hard string seal
                    
                    // Reset return token buffer and alert Core 1 looper worker thread
                    globalMethodOutputResult = 0; 
                    scadaMethodCalled = true; 
                    
                    // Give Core 1 a clean 50ms processing slice to execute B4R code
                    vTaskDelay(pdMS_TO_TICKS(50));
                    
                    // Package the final B4R SetMethodReturnCode token back into the output variant array
                    if (outputSize > 0 && output != NULL) {
                        UA_Int32 *returnValue = (UA_Int32*)UA_malloc(sizeof(UA_Int32));
                        if (returnValue != NULL) {
                            *returnValue = globalMethodOutputResult;
                            // Pass the pointer directly without '&' to avoid nesting
                            UA_Variant_setScalarCopy(output, returnValue, &UA_TYPES[UA_TYPES_INT32]);
                            UA_free(returnValue);
                        }
                    }
                    
                    return UA_STATUSCODE_GOOD; // Signal full success to open62541 engine
                }
            }
        }
        
        return UA_STATUSCODE_BADINVALIDARGUMENT;
    }

	void B4ROPEN62541::SetMethodReturnCode(int code) {
        globalMethodOutputResult = code;
    }

    void B4ROPEN62541::AddFloatNode(B4RString* NodeIdentifier, B4RString* DisplayName, float InitialValue) {
        if (server == NULL) return;
        if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
            
            UA_VariableAttributes vAttr = UA_VariableAttributes_default;
            UA_Float val = InitialValue;
            UA_Variant_setScalar(&vAttr.value, &val, &UA_TYPES[UA_TYPES_FLOAT]);
            vAttr.displayName = UA_LOCALIZEDTEXT("en-US", (char*)DisplayName->data);
            vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
            
            UA_Server_addVariableNode(server, 
                                      UA_NODEID_STRING(1, (char*)NodeIdentifier->data), 
                                      UA_NODEID_STRING(1, (char*)"Factory_Floor"),                               
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
                                      UA_QUALIFIEDNAME(1, (char*)NodeIdentifier->data), 
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
                                      vAttr, NULL, NULL);
                                      
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
            vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
            
            UA_NodeId targetNodeId = UA_NODEID_STRING(1, (char*)NodeIdentifier->data);
            
            // 1. Create the variable node normally
            UA_Server_addVariableNode(server, 
                                      targetNodeId, 
                                      UA_NODEID_STRING(1, (char*)"Factory_Floor"),                               
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
                                      UA_QUALIFIEDNAME(1, (char*)NodeIdentifier->data), 
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
                                      vAttr, NULL, NULL);
                                      
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
			vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
			
			// Use a persistent NodeId instance copy for tracking
			UA_NodeId targetNodeId = UA_NODEID_STRING(1, (char*)NodeIdentifier->data);
			
			// Add the variable node to the address space first!
			UA_Server_addVariableNode(server, 
									  targetNodeId, 
									  UA_NODEID_STRING(1, (char*)"Factory_Floor"),                               
									  UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
									  UA_QUALIFIEDNAME(1, (char*)NodeIdentifier->data), 
									  UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
									  vAttr, NULL, NULL);

            // Attach string interceptor
            if (strcmp((char*)NodeIdentifier->data, "Trigger") == 0) {
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
                                // Direct binary block memory transfer copy
                                memcpy(globalTriggerBuffer, uaBytes->data, globalTriggerLength);
                            }
                        }

                        // Explicitly cast to char* or cast indices to apply string seal
                        ((char*)globalTriggerBuffer)[globalTriggerLength] = '\0';
                        scadaTriggerCalled = true; 
                    }
                };
                UA_Server_setVariableNode_valueCallback(server, targetNodeId, callback);
            }

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
            vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
            
            UA_NodeId targetNodeId = UA_NODEID_STRING(1, (char*)NodeIdentifier->data);
            
            UA_Server_addVariableNode(server, 
                                      targetNodeId, 
                                      UA_NODEID_STRING(1, (char*)"Factory_Floor"),                               
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
                                      UA_QUALIFIEDNAME(1, (char*)NodeIdentifier->data), 
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
                                      vAttr, NULL, NULL);
                                      
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
            vAttr.accessLevel = UA_ACCESSLEVELMASK_READ | UA_ACCESSLEVELMASK_WRITE;
            
            UA_NodeId targetNodeId = UA_NODEID_STRING(1, (char*)NodeIdentifier->data);
            
            UA_Server_addVariableNode(server, 
                                      targetNodeId, 
                                      UA_NODEID_STRING(1, (char*)"Factory_Floor"),                               
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_HASCOMPONENT), 
                                      UA_QUALIFIEDNAME(1, (char*)NodeIdentifier->data), 
                                      UA_NODEID_NUMERIC(0, UA_NS0ID_BASEDATAVARIABLETYPE), 
                                      vAttr, NULL, NULL);
                                      
            xSemaphoreGive(open62541Mutex);
        }
    }

    // ============================================================================
    // OPC UA UPDATE NODE
    // ============================================================================

    void B4ROPEN62541::UpdateNodeValue(B4RString* NodeIdentifier, double NewValue) {
        if (server == NULL || !globalIsReady) return;

        if (xSemaphoreTake(open62541Mutex, portMAX_DELAY) == pdTRUE) {
            UA_NodeId targetNodeId = UA_NODEID_STRING(1, (char*)NodeIdentifier->data);
            
            UA_Variant currentValue;
            UA_Variant_init(&currentValue);
            
            if (UA_Server_readValue(server, targetNodeId, &currentValue) == UA_STATUSCODE_GOOD) {
                UA_Variant myVar;
                UA_Variant_init(&myVar);
                
                // Case A: Target node is configured as a Floating-Point node
                if (currentValue.type == &UA_TYPES[UA_TYPES_FLOAT]) {
                    float fVal = (float)NewValue;
                    UA_Variant_setScalar(&myVar, &fVal, &UA_TYPES[UA_TYPES_FLOAT]);
                    UA_Server_writeValue(server, targetNodeId, myVar);
                } 
                // Case B: Target node is configured as a 32-bit Integer node
                else if (currentValue.type == &UA_TYPES[UA_TYPES_INT32]) {
                    int32_t iVal = (int32_t)NewValue;
                    UA_Variant_setScalar(&myVar, &iVal, &UA_TYPES[UA_TYPES_INT32]);
                    UA_Server_writeValue(server, targetNodeId, myVar);
                }
                // ============================================================================
                // NEW - Case C: Target node is configured as a Boolean binary state node
                // ============================================================================
                else if (currentValue.type == &UA_TYPES[UA_TYPES_BOOLEAN]) {
                    // Converts 0.0 to false, and any non-zero value (like 1.0/True) to true
                    UA_Boolean bVal = (NewValue != 0.0); 
                    UA_Variant_setScalar(&myVar, &bVal, &UA_TYPES[UA_TYPES_BOOLEAN]);
                    UA_Server_writeValue(server, targetNodeId, myVar);
                }
                // ============================================================================
            }
            UA_Variant_clear(&currentValue);
            xSemaphoreGive(open62541Mutex);
        }
    }

    // ============================================================================
    // OPC UA Callbacks & Event Handling
    // ============================================================================

    UA_StatusCode B4ROPEN62541::scadaTriggerCallback(
        UA_Server *server, const UA_NodeId *sessionId, void *sessionContext,
        const UA_NodeId *methodId, void *methodContext, const UA_NodeId *objectId,
        void *objectContext, size_t inputSize, const UA_Variant *input,
        size_t outputSize, UA_Variant *output) {
        
		// Flip the atomic cross-core flag to true
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
