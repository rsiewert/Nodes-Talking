package com.topaz.nodes;
import com.topaz.utils.*;
import com.topaz.protocols.*;

public class Node {

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public ProtocolSpec getProtocolSpec() {
		return protocolSpec;
	}

	public void setProtocolSpec(ProtocolSpec protocolSpec) {
		this.protocolSpec = protocolSpec;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/* Status for a node is marked with a high-level
	 * Color indicator as follows:
	 * GREEN - Node is up and running within load limits and no errors
	 * YELLOW - Node is running but with some issues which might include
	 * 	        things such as low battery power, heavy load etc
	 * RED    - Node has issues that prevent it running now or in the very near future
	 * BLACK  - Node's status is unknown
	 */
	public enum STATUS { GREEN, YELLOW, RED, BLACK };
	
	// Each node has a unique nodeid
	String nodeId;
	
	// A node has a physical location.
	Location location;
	
	// The high level status of a node.
	STATUS status;
	// A node has a protocol spec that describes how it can 
	// interact with other nodes and clients
	ProtocolSpec protocolSpec;
	
	// A node has a description
	String description;
	
	
	
	
	
}
