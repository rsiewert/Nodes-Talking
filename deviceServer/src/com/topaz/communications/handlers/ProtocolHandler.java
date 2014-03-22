package com.topaz.communications.handlers;
import com.topaz.communications.protocols.Protocol;

public abstract class ProtocolHandler implements Runnable {
	
	// The protocol the class handles
	Protocol protocol;

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
