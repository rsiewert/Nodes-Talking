package com.topaz.communications.messages;
import com.topaz.nodes.*;

/* Registration Messages will have a protocol which may contain a REST endpoint(s) 
 * and/or message connections 
 */	

public class Registration extends Message {

	// The node we are registering
	Node node;
	
	public Node getRegisteringNode() {
		return node;
	}

	public void setRegisteringNode(Node registeringNode) {
		this.node = registeringNode;
	}
	 
	public Registration() {
		
	}
	
	public Registration(String nodeId) {
		
		this.nodeId = nodeId;
		
	}

	
}

