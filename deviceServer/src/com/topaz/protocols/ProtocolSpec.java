package com.topaz.protocols;

import java.util.*;


public class ProtocolSpec {

	// Particular roles played by the protocol
	public enum ActsAs {
		DEVICE,
		SERVER
	}
	 
	// Role of the protocol.
	ActsAs actsAs;
	
	// Registrations have protocol information. Either RESTful API's or message protocols
	HashMap<String,RestProtocol> rest = null;
	HashMap<String,MessageProtocol> messenger = null;
		
	public void setActsAs(ProtocolSpec.ActsAs actsAs) {
		
		this.actsAs = actsAs;
	}

	public ProtocolSpec.ActsAs getActsAs() {
		
		return this.actsAs;
	}

	public ProtocolSpec() {
		
	}
	
	public ProtocolSpec(ProtocolSpec.ActsAs actsAs) {
	
		this.actsAs = actsAs;
	}
	
	public void addRestProtocol(String name, RestProtocol restP) {
		
		if(rest == null)
			rest = new HashMap<String,RestProtocol>();
		this.rest.put(name, restP);
		
	}
	
	public void addMessageProtocol(String name, MessageProtocol messP) {
		
		if(messenger == null)
			messenger = new HashMap<String,MessageProtocol>();

		messenger.put(name, messP);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProtocolSpec [actsAs=" + actsAs + ", rest=" + rest
				+ ", messenger=" + messenger + "]";
	}
}
