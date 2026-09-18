import { OPCUAClient, AttributeIds } from "node-opcua";

const endpointUrl = "opc.tcp://192.168.1.175:4840";

const client = OPCUAClient.create({ endpointMustExist: false });

await client.withSessionAsync(endpointUrl, async (session) => {
  const dataValue = await session.read({
    nodeId: "ns=0;i=2258", // Server CurrentTime
    attributeId: AttributeIds.Value,
  });
  console.log("Server time:", dataValue.value.value);
});

