/*  author: Randy Godwin */
package com.topaz.communications.handlers;

import java.io.IOException;


import com.rabbitmq.client.QueueingConsumer;

// Handles ACK Messages. Maintains a set of messages that we expect an ACK back from
// the recipient.  If no ACK send within a specified time then an error is logged and the
// message is either resent or dropped according to the protocols preferences

public class ProtocolHandlerAck extends MessageProtocolHandler {

	public ProtocolHandlerAck() {

	}

	@Override
	public void run() {

		// get the exchange and routing information
		String exchange = this.getMessageProtocol().getExchange();
		String routingKey = this.getMessageProtocol().getRoutingKey();
		String queueName = this.getMessageProtocol().getQueue();

		// Create our Queuing consumer
		QueueingConsumer queueConsumer = new QueueingConsumer(this.getChannel());;
		
		// Set up the communications
		try {
			// Create our exchange
			this.getChannel().exchangeDeclare(exchange, "topic");
			// Add our queue 
			this.getChannel().queueDeclare(queueName, true, false, false, null);
			this.getChannel().queueBind(queueName, exchange, routingKey);
			// Bind our consumer to the queue
			this.getChannel().basicConsume(queueName, true, queueConsumer);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

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
