package com.topaz.nodes;
import com.topaz.communications.apis.Api;
import com.topaz.utils.*;
import com.topaz.communications.protocols.*;

import java.util.ArrayList;
import org.junit.Test;

public class Node {

	// Particular role played by the node
	public enum ActsAs {
		DEVICE,
		SERVER
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
	private String nodeId;

    // A node can be either local or remote. Mark as transient since
    // we do not want it serialized.
    private transient boolean remote = false;

    // A node has a description
    private String description;

    // Role of the node.
    private ActsAs actsAs;

    // The high level status of a node.
    private STATUS status;

    // A node has a physical location.
	private Location location;

    //. A node has a group of  APIs.
    ArrayList<Api> api = new ArrayList<Api>();

	// A node has a protocol spec that describes how it can 
	// interact with other nodes and clients
	private ProtocolSpec protocol = new ProtocolSpec();

	
	// Builder class for construction of a Node 
	protected static abstract class builder<T extends builder<T>> {
		
		protected abstract T self();

		// Role of the node.
		private ActsAs actsAs;
		
		// A node has a description
		private String description;
		
		// A node has a physical location.
		private Location location;
		
		// Each node has a unique nodeid
		private String nodeId;
		
		// The high level status of a node.
		private STATUS status;

        // Whether the node is remote or local
        private boolean remote;


        public T actsAs(ActsAs actsAs) {
			this.actsAs = actsAs;
			return self();
		}

		public T description(String description) {
			this.description = description;
			return self();
		}

		public T location(Location location) {
			this.location = location;
			return self();
		}

		public T nodeId(String nodeId) {
			this.nodeId = nodeId;
			return self();
		}

        public T remote(boolean remote) {
            this.remote = remote;
            return self();
        }

		public T status(STATUS status) {
			this.status = status;
			return self();
		}
		

		public builder(ActsAs actsAs, String description, String nodeId) {
			this.actsAs = actsAs;
			this.description = description;
			this.nodeId = nodeId;
		}

		protected builder() {}

		
    	public Node build() {
			return new Node(this);

    	}
	}
	
	// A hidden class that lets up take out the parameterized class
    private static class builder2 extends builder<builder2> {
        @Override
        protected builder2 self() {
            return this;
        }
    }

    public static builder<?> builder() {
        return new builder2();
    }
    
	// Create a node from a builder
	protected Node(builder<?> builder) {
		this.actsAs = builder.actsAs;
		this.description = builder.description;
		this.location = builder.location;
		this.nodeId = builder.nodeId;
		this.status = builder.status;

        // Add our APIs
        Api api= new Api();
        api.setName("setStatus");
        api.setDescription("Sets the status of the node.");
        api.setReturns("void");
        api.addParameter("status","Node.STATUS");
        this.api.add(api);

        Api apiLoc= new Api();
        apiLoc.setName("setLocation");
        apiLoc.setDescription("Sets the location of the node.");
        apiLoc.setReturns("void");
        apiLoc.addParameter("longitude","number");
        apiLoc.addParameter("latitude","number");
        apiLoc.addParameter("altitude", "number");
        this.api.add(apiLoc);

        Api apiGetStat= new Api();
        apiGetStat.setName("getStatus");
        apiGetStat.setDescription("Gets the status of the node.");
        apiGetStat.setReturns("Node.Status");
        this.api.add(apiGetStat);

        Api apiGetLoc= new Api();
        apiGetLoc.setName("getLocation");
        apiGetLoc.setDescription("Gets the location of the node.");
        apiGetLoc.setReturns("{longitude:number,latitude:number, altitude:number}");
        this.api.add(apiGetLoc);


    }

	public Node() {}
	
	// Accessor Functions
	public String getNodeId() {
		return nodeId;
	}

	public ActsAs getActsAs() {
		return actsAs;
	}

	public void setActsAs(ActsAs actsAs) {
		this.actsAs = actsAs;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public boolean isRemote() {

        return remote;
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
		return protocol;
	}

	public void setProtocolSpec(ProtocolSpec protocolSpec) {
		this.protocol = protocolSpec;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public void addRestProtocol(String name, RestProtocol rest) {

		protocol.addRestProtocol(name, rest);
	}

	public void addMessageProtocol(String name, MessageProtocol messP) {

		protocol.addMessageProtocol(name, messP);
	}
}
