package com.topaz.nodes;

import com.google.gson.Gson;
import com.topaz.communications.handlers.ApiProtocolHandler;
import com.topaz.communications.messages.Registration;
import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.servers.MessageService;
import com.topaz.communications.servers.NodeApiServer;
import com.topaz.communications.servers.RestServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.io.IOException;

/**
 * Created by rbgodwin on 4/26/14.
 */
public class DeviceTest {

    private final static String SERVER = "127.0.0.1";
    private final static String EXCHANGE_NAME = "test-exchange";
    private final static String ROUTE = "the.routing.register";
    private final static String ENDPOINT = "/getDeviceById";
    private final static String JSON_QUEURY = "{\"nodeId\":\"testDevice\"}";
    final static String REST_SERVER = "localhost:3000";


    // Convert JSON into an object
    Gson gson = new Gson();

    Device localDevice;
    Device remoteDevice;
    MessageService ms;

    public DeviceTest() throws java.io.IOException {

        // We need a message service to connect the local and remote
        ms = new MessageService(SERVER);
        RestServer rs = new RestServer(REST_SERVER);
        NodeApiServer.setServers(ms,null);

        this.localDevice = Device.builder()
                    .actsAs(Node.ActsAs.DEVICE)
                    .nodeId("testDevice").build();

        // Each device will have it's own exchange
        String exchange = "testDeviceExchange";

        Registration regMessage = new Registration();
        regMessage.setRegisteringNode(localDevice);

        // Protocol for command messages
        MessageProtocol messP = new MessageProtocol();
        messP.setExchange(exchange);
        messP.setQueue("serviceQueue");
        messP.setRoutingKey("routing.commands");

        // Add the protocol to the device
        localDevice.addMessageProtocol("command_server", messP);

        // Register the device
        ms.sendMessage(EXCHANGE_NAME, ROUTE, regMessage);

        //Create a control protocol handler for the device and register it with the message service
        ms.addProtocolHandler(new ApiProtocolHandler(ms, localDevice, messP));

        // Retrieve a remote version of our local device
        //
        String deviceJson = rs.getJson(ENDPOINT,JSON_QUEURY);
        remoteDevice = gson.fromJson(deviceJson,Device.class);

        remoteDevice.setRemote(true);



    }

    @After
    public void cleanup() {

        try {
            this.ms.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Closed Message Service");
    }

    @org.junit.Test
    public void testSetStatus() throws Exception {


        synchronized (localDevice) {
            // Set the local device status then test we can control it via the remote device
            localDevice.setStatus(Node.STATUS.GREEN);

            // Loop over all possible statuses and check if the remote device updates the local one
            for (Node.STATUS status : Node.STATUS.values()) {
                remoteDevice.setStatus(status);

                // Wait a bit to allow update of our local device by remote
                Thread.sleep(100);

                Assert.assertEquals(status, localDevice.getStatus());
            }
        }

    }

    @org.junit.Test
    public void testGetStatus() throws Exception {

        synchronized (localDevice) {
            // Test that we can retrieve the status of a local device using it's remote version
            // Loop over all possible statuses and check if the remote device updates the local one
            for (Node.STATUS status : Node.STATUS.values()) {

                // Set the local versions status
                localDevice.setStatus(status);

                // Check against the remote versions
                Assert.assertEquals(localDevice.getStatus(), remoteDevice.getStatus());
            }


        }
    }
}
