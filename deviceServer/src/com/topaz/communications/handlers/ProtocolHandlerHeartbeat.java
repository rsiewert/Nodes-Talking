package com.topaz.communications.handlers;

import java.io.IOException;
import com.topaz.nodes.Node;
import com.topaz.communications.servers.*;
import com.topaz.communications.messages.*;
import com.topaz.communications.protocols.*;

public class ProtocolHandlerHeartbeat extends MessageProtocolHandler {

	// Default value for the heart-beat message
	private static final int INTERVAL_SEC = 1;

	// The interval between heart-beat messages
	Integer heartbeatInterval = ProtocolHandlerHeartbeat.getIntervalSec();

	// The heartbeat message sent out
	HeartbeatMessage heartbeat;

	// A Message service used to send the heartbeat
	MessageService ms;

	// Set the heartbeat status. Will be sent out on the timer
	public void setHeartbeatStatus(Node.STATUS status) {
		this.heartbeat.setStatus(status);

	}

	public ProtocolHandlerHeartbeat(MessageService ms, HeartbeatMessage hb, MessageProtocol mp) {

		// Load up our message service
		this.ms = ms;

		// And our prototype heartbeat
		this.heartbeat = hb;

		this.setMessageProtocol(mp);
		
	}

	@Override
	public void run() {

		// get the exchange and routing information
		String exchange = this.getMessageProtocol().getExchange();
		String routingKey = this.getMessageProtocol().getRoutingKey();

		// Send the heartbeat
		while (!Thread.interrupted()) {
			try {
				ms.sendMessage(exchange, routingKey, this.heartbeat);
				Thread.sleep(1000 * this.heartbeatInterval);

			} catch (InterruptedException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getIntervalSec() {
		return INTERVAL_SEC;
	}

}
