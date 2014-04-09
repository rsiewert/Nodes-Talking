package com.topaz.communications.handlers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.topaz.communications.protocols.MessageProtocol;

public class MessageProtocolHandler extends ProtocolHandler {

	// The message protocol we are working with
	MessageProtocol messageProtocol;
		
	// We have communications channel to get to our message server
	Channel channel;
	
	// Can consume messages
	QueueingConsumer queueConsumer=null;
	
	
	public QueueingConsumer getQueueConsumer() {
		return queueConsumer;
	}

	public void setQueueConsumer(QueueingConsumer queueConsumer) {
		this.queueConsumer = queueConsumer;
	}

	@Override
	public void run() {
	
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
