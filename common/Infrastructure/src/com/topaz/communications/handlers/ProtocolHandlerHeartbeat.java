package com.topaz.communications.handlers;

import java.io.IOException;

import com.rabbitmq.client.QueueingConsumer;
import com.topaz.nodes.Node;
import com.topaz.communications.servers.*;
import com.topaz.communications.messages.*;
import com.topaz.communications.protocols.*;

public class ProtocolHandlerHeartbeat extends MessageProtocolHandler {

	// Default value for the heart-beat message
	private static final int INTERVAL_SEC = 1;

    private static final String THREAD_NAME = "ProtocolHandlerHeartbeat";

	// The interval between heart-beat messages
	Integer heartbeatInterval = ProtocolHandlerHeartbeat.getIntervalSec();


    public ProtocolHandlerHeartbeat(MessageService ms, Node node, MessageProtocol mp) {

        super(ms, node, mp);

	}

	@Override
	public void run() {
    int count=0;

        //Set the name of this thread
        Thread.currentThread().setName(THREAD_NAME);

        // get the exchange and routing information
		String exchange = this.getMessageProtocol().getExchange();
		String routingKey = this.getMessageProtocol().getRoutingKey();

        HeartbeatMessage heartbeat = new HeartbeatMessage();

		// Send the heartbeat
		while (!Thread.interrupted()) {
			try {
                for(Node n : this.nodes.values()) {
                    heartbeat.setStatus(n.getStatus());
                    heartbeat.setNodeId(n.getNodeId());
                    messageService.sendMessage(exchange, routingKey, heartbeat);
                    Thread.sleep(1000 * this.heartbeatInterval);

                    if (count % 100 == 0)
                        System.out.println(heartbeat.getNodeId() + " Sent " + count + " heartbeats on exchange: " +
                                exchange + " routingKey: " + routingKey);
                }
                count++;
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
