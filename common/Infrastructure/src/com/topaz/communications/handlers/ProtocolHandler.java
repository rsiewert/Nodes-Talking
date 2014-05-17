package com.topaz.communications.handlers;
import com.topaz.communications.protocols.Protocol;
import com.topaz.nodes.Node;

import java.util.HashMap;
import java.util.Map;

public class ProtocolHandler implements Runnable {
	
	// The protocol the class handles
	Protocol protocol;

    // The nodes serviced by the protocol handler
    Map<String, Node> nodes = new HashMap<String, Node>();

    ProtocolHandler(Node node){
        if(node != null)
            this.nodes.put(node.getNodeId(),node);
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


    public void addNode(Node node) {

        this.nodes.put(node.getNodeId(),node);

    }

    @Override
    public void run() {

    }
}
