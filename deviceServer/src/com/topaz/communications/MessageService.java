package com.topaz.communications;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.AMQP.*;
import com.topaz.message.*;
import com.topaz.protocols.MessageProtocol;
import com.topaz.handlers.*;

import java.io.IOException;
import java.util.*;
public class MessageService {

	// Information about the connection to the message server 
	private Channel channel;
	private Connection connection;
	private BasicProperties bp;
	
	private static MessageService singleMS = null;
	
	private ArrayList<Thread> serviceThreads = new ArrayList<Thread>();
	
	//  Protocols managed by the MessageService
	private ArrayList<MessageProtocol> messageProtocols = 
			new ArrayList<MessageProtocol>();

	// Some of our messages will want to check if there is an ack in response 
	private HashMap<Integer, Message>  ackChecks = new HashMap<Integer, Message>();
	
	
	public static MessageService getMessageService() throws IOException {

		// Check if we have an instance of the message service
		if (MessageService.singleMS != null)
			return MessageService.singleMS;
	
		// If not then log an error and throw and io exception
		System.out.println("getMessageService: Error Null Message Service\n");
		IOException e = new IOException("Must insitialize with a port and IP");
		throw e;
	}
	
	public static MessageService getMessageService(String host, int port) {

		// Check if we have an instance of the message service
		if (MessageService.singleMS != null)
			return MessageService.singleMS;
	
		// If not then create one and return it
		MessageService.singleMS = new MessageService(host,port);
		return MessageService.singleMS;
		
	}
	
	// Constructor that connects to the message server 
	private MessageService(String host, int port)  {

		ConnectionFactory factory = new ConnectionFactory();	
		factory.setHost(host);
		if(port != 0)
			factory.setPort(port);
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			bp =  new BasicProperties();
			bp.builder().contentType("application/json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		singleMS = this;
	}
	
	// Convienience constructor that uses the default port for the message server
	public MessageService(String host)  throws java.io.IOException {
		
		this(host, 0);

	}

	// Clean up the connection to the message server
	public void close()  throws java.io.IOException {
		
		// Stop all our protocol handling threads
		for(Thread protocolHandlerThread: this.serviceThreads)
		{
			protocolHandlerThread.interrupt();
			try {
				protocolHandlerThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		// Delete all temporary exchanges
		for(MessageProtocol protocol: this.messageProtocols)
		{
			if(protocol.getManageExchange() == true) {
				this.channel.exchangeDelete(protocol.getExchange());
			}
			
		}
		// Close the channel and connection
		channel.close();
		connection.close();
		
		System.out.println("Closed Channel and Connection\n");
	
	}
		
	// Send a message out to a particular exhange with a given route
	public void sendMessage(String exchange, String route, Message msg)
			throws java.io.IOException {
		  
		// Check if we are requesting and ACK. If so put the message on the ACK List
		if(msg.getRequestAck())
			this.addAckMessage(msg);
		
		channel.basicPublish(exchange, route, bp, msg.toJsonString().getBytes());
		System.out.println("Sent:" + msg.toJsonString());	
		 
	}
	
	// Add a message to the group of messages waiting for an ACK
	private void addAckMessage(Message msg) {
		
		// Put the message on the ackable list with it's sequence number as the key
		this.ackChecks.put(msg.getSeqId(), msg);

	}
	
	// Create all the message exchanges and register 
	public void addProtocol(MessageProtocol protocol,MessageProtocolHandler protocolHandler){
	
		// Set the items the protocol handler needs for servicing the protocol
		protocolHandler.setMessageProtocol(protocol);
		protocolHandler.setConnection(this.connection);
		
		// Set the protocol handler's channel
		protocolHandler.setChannel(this.getChannel());
		
		// Create the thread for the handler function
		Thread handlerThread = new Thread(protocolHandler);

		// Add the thread to our list of threads 
		this.serviceThreads.add(handlerThread);
	
		// Add the Message protocol handler to our list of message protocol handlers
		this.messageProtocols.add(protocol);
		// Crank it up
		handlerThread.start();

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

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @return the serviceThreads
	 */
	public ArrayList<Thread> getServiceThreads() {
		return serviceThreads;
	}

	/**
	 * @param serviceThreads the serviceThreads to set
	 */
	public void setServiceThreads(ArrayList<Thread> serviceThreads) {
		this.serviceThreads = serviceThreads;
	}
	
}

