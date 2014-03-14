package com.topaz.communications.protocols;
import com.topaz.communications.handlers.*;

public class MessageProtocol extends Protocol {

	String exchange;
	//  Mark as transient since we do not want to have it serialized
	transient String  queue;
	String routing_key;

	// Each protocol has a handler for the protocol.  These handlers can either 
	// service requests coming in (via messages or other protocols) or can themselves
	// generate outgoing requests and messages
	transient MessageProtocolHandler protocolHandler;
	
	// Indicates if the application should create and destroy the exchange
	private boolean manageExchange;
		
	/**
	 * @return the manageExchange
	 */
	public boolean getManageExchange() {
		return manageExchange;
	}

	/**
	 * @param manageExchange the manageExchange to set
	 */
	public void setManageExchange(boolean manageExchange) {
		this.manageExchange = manageExchange;
	}

	/**
	 * @return the exchange
	 */
	public String getExchange() {
		return exchange;
	}

	/**
	 * @param exchange
	 *            the exchange to set
	 */
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	/**
	 * @return the queue
	 */
	public String getQueue() {
		return queue;
	}

	/**
	 * @param queue
	 *            the queue to set
	 */
	public void setQueue(String queue) {
		this.queue = queue;
	}

	/**
	 * @return the routingKey
	 */
	public String getRoutingKey() {
		return routing_key;
	}

	/**
	 * @param routingKey
	 *            the routingKey to set
	 */
	public void setRoutingKey(String routingKey) {
		this.routing_key = routingKey;
	}


	/**
	 * @return the protocolHandler
	 */
	public MessageProtocolHandler getProtocolHandler() {
		return protocolHandler;
	}

	/**
	 * @param protocolHandler the protocolHandler to set
	 */
	public void setProtocolHandler(MessageProtocolHandler protocolHandler) {
		this.protocolHandler = protocolHandler;
	}

}
