package com.topaz.nodes;

import com.topaz.communications.handlers.ControlProtocolHandler;
import com.topaz.communications.messages.Registration;
import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.servers.MessageService;
import org.junit.Assert;

/**
 * Created by rbgodwin on 4/26/14.
 */
public class DeviceTest {

    private final static String SERVER = "127.0.0.1";
    private final static String EXCHANGE_NAME = "test-exchange";
    private final static String ROUTE = "the.routing.register";

    @org.junit.Test
    public void testLocalRemote() throws Exception {

        // We need a message service to connect the local and remote
        MessageService ms = new MessageService(SERVER);

        Device localDevice = Device.builder()
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

        ms.sendMessage(EXCHANGE_NAME, ROUTE, regMessage);

        //Create a control protocol handler for the device and register it with the ms
        ms.addProtocolHandler(new ControlProtocolHandler(ms, localDevice, messP));

        // Retrieve a remote version of our local device
//        Device remoteDevice = MessageService.

//        Device remoteDevice = Device.builder().nodeId("testDevice").remote(true).build();



       // We tie the remote to the local and check if we can set via the remote

    //    localDevice.setStatus(Node.STATUS.GREEN);
      //  remoteDevice.setStatus(Node.STATUS.BLACK);

      //  Assert.assertEquals(Node.STATUS.BLACK, localDevice.getStatus());

    }
}
