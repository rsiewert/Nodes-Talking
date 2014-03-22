package com.topaz.communications.protocols;
import com.topaz.communications.handlers.*;

public class Protocol {
	
	ProtocolHandler protocolHandler;

	/**
	 * @return the protocolHandler
	 */
	public ProtocolHandler getProtocolHandler() {
		return protocolHandler;
	}

	/**
	 * @param protocolHandler the protocolHandler to set
	 */
	public void setProtocolHandler(ProtocolHandler protocolHandler) {
		this.protocolHandler = protocolHandler;
	}
	

}
