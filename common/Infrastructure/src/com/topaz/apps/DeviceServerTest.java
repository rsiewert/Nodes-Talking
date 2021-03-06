package com.topaz.apps;

import com.topaz.utils.*;
import com.topaz.communications.servers.*;
import com.topaz.communications.handlers.*;
import com.topaz.communications.messages.*;
import com.topaz.communications.protocols.*;
import com.topaz.nodes.*;

public class DeviceServerTest {

	private final static String EXCHANGE_NAME = "register";
	private final static String ROUTE = "register.rk.device_server_test";
	private final static String SERVER = "127.0.0.1";


    /**
	 * @param args
	 */
	public static void main(String[] args) throws java.io.IOException {

		MessageService ms = new MessageService(SERVER);
        int count = 0;


        // Add 1K devices to the database and start heartbeats for each
		for (int i = 0x55590000; i < 0x55590400; i++) {

			// A unique number for the device. Mimic a MAC address
			String deviceNum = Integer.toString(i, 16);

			// Each device will have it's own exchange
			String exchange = "deviceExchange.0x" + deviceNum;

			// Create the device and add it to the registration
			Device testDevice = Device.builder().status(Node.STATUS.YELLOW)
					.nodeId("0x" + deviceNum + ".gateway")
					.description("Test Device " + count++).actsAs(Node.ActsAs.DEVICE)
					.location(new Location(count%90, count%360, count)).build();

			Registration regMessage = new Registration();
			regMessage.setRegisteringNode(testDevice);
			regMessage.setRequestAck(true);

			// Create some protocols for communications with the device

			// A mirror REST API end-point
			RestProtocol restP = new RestProtocol();
			restP.setDomain("192.168.1.44");
			restP.setEndpoint("/json-mirror");
			restP.setPort(3000);
			testDevice.addRestProtocol("Mirror", restP);

			// Protocol for command messages
			MessageProtocol messP = new MessageProtocol();
			messP.setExchange(exchange);
			messP.setQueue("serviceQueue");
			messP.setRoutingKey("routing.commands");

			// Add the protocol to the device
			testDevice.addMessageProtocol("command_server", messP);

			// Create a protocol for ON Publish
			MessageProtocol messOP = new MessageProtocol();
			messOP.setExchange(exchange);
			messOP.setQueue("serviceQueue");
			messOP.setRoutingKey("routing.on_publish");
			messOP.setManageExchange(true);
			testDevice.addMessageProtocol("on_publish", messOP);

			// Create a protocol for the heart-beat message 
			MessageProtocol messHB = new MessageProtocol(); 
			messHB.setExchange(exchange);
			messHB.setRoutingKey("routing.heartbeat");
			messHB.setManageExchange(true);
			testDevice.addMessageProtocol("heartbeat", messHB);

			// Create a protocol for ACKS from MQ Servers
			MessageProtocol messA = new MessageProtocol();
			messA.setExchange(exchange);
			messA.setQueue("serviceQueue");
			messA.setRoutingKey("routing.acks");

			// Application will create and destroy the exchange
			messA.setManageExchange(true);

			// Add the protocol to the device
			testDevice.addMessageProtocol("on_ack", messA);

			// Notify the MessageService of the new protocol and a protocol
			// server
			ms.addProtocolHandler(new ProtocolHandlerAck(ms,testDevice,messA));

			// Request an ACK for the registration
			regMessage.setRequestAck(true);
	
			ms.sendMessage(EXCHANGE_NAME, ROUTE, regMessage);

            //Create a control protocol handler for the device and register it with the ms
            ms.addProtocolHandler(new ApiProtocolHandler(ms, testDevice, messP));

			// Start the heart beat  
			// Notify the MessageService of the new protocol and a protocol server
			ms.addProtocolHandler( new ProtocolHandlerHeartbeat(ms,testDevice,messHB));
		
		}

		System.out.println("Completed Registration. Sending Heartbeats");
        while (true)
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
            ms.close();
		}

	}
}
