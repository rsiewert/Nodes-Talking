package com.topaz.apps;

import com.topaz.utils.*;
import com.topaz.communications.servers.*;
import com.topaz.communications.handlers.*;
import com.topaz.communications.messages.*;
import com.topaz.communications.protocols.*;
import com.topaz.nodes.*;

public class DeviceServer {

	private final static String EXCHANGE_NAME = "test-exchange";
	private final static String ROUTE = "the.routing.register";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws java.io.IOException {

		MessageService ms = new MessageService("localhost");
	//	 MessageService ms = new MessageService("thespacetimecontinuum.com",
		// 20005);
		
		// Create a device and add it to the registration
		Device testDevice = Device.builder()
				.status(Node.STATUS.YELLOW)
				.nodeId("0xfa32da59.gateway")
				.description("Test Device")
				.actsAs(Node.ActsAs.DEVICE)
				.location(new Location(100.12,200.34,300.45)).build();
		
		Registration regMessage = new Registration();
		regMessage.setRegisteringNode(testDevice);
		regMessage.setRequestAck(true);


		RestProtocol restP = new RestProtocol();
		restP.setDomain("192.168.1.44");
		restP.setEndpoint("/json-mirror");
		restP.setPort(3000);
		testDevice.addRestProtocol("Mirror", restP);

	//	 Create a Protocol for Messagecommands 
		MessageProtocol messP = new MessageProtocol();
		messP.setExchange("deviceExchange.0xfa32da59");
		messP.setQueue("serviceQueue");
		messP.setRoutingKey("0xfa32da59.routing.commands");

		// Send the protocol over to the message service to handle
		testDevice.addMessageProtocol("command_server", messP);

		// Create a protocol for ACKS from MQ Servers 
		MessageProtocol messA = new MessageProtocol();
		messA.setExchange("deviceExchange.0xfa32da59");
		messA.setQueue("serviceQueue");
		messA.setRoutingKey("0xfa32da59.routing.acks");

		// The application will create and destroy the exchange used to
		// communicate
		messA.setManageExchange(true);
		
		// Add the protocol to our registration message
		testDevice.addMessageProtocol("on_ack", messA);

		// Notify the MessageService of the new protocol and a protocol server
		ms.addProtocol(messA, new ProtocolHandlerAck());


		// Create a protocol for ON Publish 
		MessageProtocol messOP = new MessageProtocol();
		messOP.setExchange("deviceExchange.0xfa32da59");
		messOP.setQueue("serviceQueue");
		messOP.setRoutingKey("0xfa32da59.routing.on_publish");
		messOP.setManageExchange(true);
		testDevice.addMessageProtocol("on_publish", messOP);
		regMessage.setRequestAck(true);

	//	 Create a protocol for the heartbeat message 
		MessageProtocol messHB = new MessageProtocol();
		messHB.setExchange("deviceExchange.0xfa32da59");
		messHB.setRoutingKey("0xfa32da59.routing.heartbeat");
		messHB.setManageExchange(true);
		testDevice.addMessageProtocol("heartbeat", messHB);
		
		HeartbeatMessage hb = new HeartbeatMessage("0xfa32da59.gateway");
		hb.setStatus(Node.STATUS.GREEN);
	
		// Notify the MessageService of the new protocol and a protocol server
		ms.addProtocol(messHB, new ProtocolHandlerHeartbeat(ms,hb));
		
		for (int i = 0; i < 1; i++) {
			ms.sendMessage(EXCHANGE_NAME, ROUTE, regMessage);
			// For test bump up the sequence number
			regMessage.setSeqId(i);
			System.out.println("\n [x] Sent '" + regMessage.toJsonString()
					+ "'");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		ms.close();
	}
}
