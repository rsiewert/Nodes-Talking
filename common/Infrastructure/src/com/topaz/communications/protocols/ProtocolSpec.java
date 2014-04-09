package com.topaz.communications.protocols;

import java.util.*;


public class ProtocolSpec {
	
	// Registrations have protocol information. Either RESTful API's or message protocols
	HashMap<String,RestProtocol> rest = null;
	HashMap<String,MessageProtocol> messenger = null;
		
	public ProtocolSpec() {
		
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

	
}
