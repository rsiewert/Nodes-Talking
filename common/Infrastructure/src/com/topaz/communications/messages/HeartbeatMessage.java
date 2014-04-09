package com.topaz.communications.messages;
import com.topaz.nodes.*;

public class HeartbeatMessage extends Message {

	// Inner class to hold the heartbeat specific information
	public class Heartbeat {

		// Default to BLACK status  
		Node.STATUS status =  Node.STATUS.BLACK;
	
	}
	
	// THe message will have a heartbeat object
	public Heartbeat heartbeat = new Heartbeat();
	
	// Set the node ID for the message source
	public HeartbeatMessage(String nodeId) {
		this.setNodeId(nodeId);
	}

	/**
	 * @return the status
	 */
	public Node.STATUS getStatus() {
		return this.heartbeat.status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Node.STATUS status) {
		this.heartbeat.status = status;
	}
	
	
}
