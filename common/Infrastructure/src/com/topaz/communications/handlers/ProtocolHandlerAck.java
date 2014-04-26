/*  author: Randy Godwin */
package com.topaz.communications.handlers;
import com.topaz.communications.protocols.*;

import com.rabbitmq.client.QueueingConsumer;
import com.topaz.communications.servers.MessageService;
import com.topaz.nodes.Node;

// Handles ACK Messages. Maintains a set of messages that we expect an ACK back from
// the recipient.  If no ACK send within a specified time then an error is logged and the
// message is either resent or dropped according to the protocols preferences

public class ProtocolHandlerAck extends MessageProtocolHandler {

    final static String THREAD_NAME = "MessageProtocolHandler";

	public ProtocolHandlerAck( MessageService ms, Node node, MessageProtocol mp) {

        super(ms,node,mp);

	}

	public QueueingConsumer getQueueConsumer() {
		return queueConsumer;
	}

	public void setQueueConsumer(QueueingConsumer queueConsumer) {
		this.queueConsumer = queueConsumer;
	}

	@Override
	public void run() {

        //Set the name of this thread
        Thread.currentThread().setName(THREAD_NAME);

		// Setup the exchange and subscribe to the route we need to service
		while (!Thread.interrupted()) {
			try {
				QueueingConsumer.Delivery delivery = queueConsumer.nextDelivery();
				System.out.println("Ack Service thread running got:\n" + 
						new String(delivery.getBody()));
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}
