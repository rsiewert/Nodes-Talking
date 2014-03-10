package com.topaz.message;

/* Registration Messages will have a protocol which may contain a REST endpoint(s) 
 * and/or message connections 
 */	

public class Registration extends Message {

	
	ProtocolSpec protocol = new ProtocolSpec();
	 
	public Registration() {
		
	}
	
	public Registration(String nodeId, ProtocolSpec.ActsAs actsAs) {
		
		this.setNodeId(nodeId);
	
		protocol.setActsAs(actsAs);
		

	}
	
	public void addRestProtocol(String name, RestProtocol rest) {
		
		protocol.addRestProtocol(name, rest);
	}

	public void addMessageProtocol(String name, MessageProtocol messP) {
		
		protocol.addMessageProtocol(name, messP);
	}

	
}

