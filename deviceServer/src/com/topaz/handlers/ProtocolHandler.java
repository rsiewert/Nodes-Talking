package com.topaz.handlers;
import com.topaz.message.*;
import com.rabbitmq.client.Connection;

public abstract class ProtocolHandler implements Runnable {
	
	// The protocol the class handles
	Protocol protocol;
	
	// The connection used by the handler
	Connection connection;
	

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
	 * @return the protocol
	 */
	public Protocol getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	
}
