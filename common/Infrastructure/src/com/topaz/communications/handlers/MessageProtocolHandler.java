package com.topaz.communications.handlers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.topaz.communications.protocols.MessageProtocol;
import com.topaz.communications.servers.MessageService;
import com.topaz.nodes.Node;

public class MessageProtocolHandler extends ProtocolHandler {

	// The message protocol we are working with
	MessageProtocol messageProtocol;
		
	// We have communications channel to get to our message server
	Channel channel;
	
	// Can consume messages
	QueueingConsumer queueConsumer=null;

    // The Message Service we use
    MessageService messageService;

    final static String THREAD_NAME = "MessageProtocolHandler";

    public MessageProtocolHandler(MessageService ms, Node node, MessageProtocol mp) {

        super(node);

        // Set the message protocol
        this.setMessageProtocol(mp);

        // Set our message service
        this.messageService = ms;

        // Create our Queuing consumer
        this.setQueueConsumer(new QueueingConsumer(this.getChannel()));

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
                System.out.println("MessageProtocolHandler thread received:\n" +
                        new String(delivery.getBody()));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

	/**
	 * @return the messageProtocol
	 */
	public MessageProtocol getMessageProtocol() {
		return messageProtocol;
	}

	/**
	 * @param messageProtocol the messageProtocol to set
	 */
	public void setMessageProtocol(MessageProtocol messageProtocol) {
		this.messageProtocol = messageProtocol;
	}

	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	

}
