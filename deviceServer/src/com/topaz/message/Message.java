package com.topaz.message;

import com.google.gson.*;
import java.util.*;

public class Message {

	// Gson will provide means to covert between message objects and JSON
	private static Gson gson = null;

	private static int sequencer = 0;

	// Timestamp for the message
	Date originTimestamp;
	
	// Node identification for originator of the message
	String nodeId;

	// Each message has a sequence ID
	int seqId;
	
	// Whether we want an ack on the message
	Boolean requestAck=false;
	
	public Message() {
		
		// If GSON has not been created then create it
		if (gson == null)
			gson = new Gson();
		
		// Creation time of the message
		originTimestamp = new Date();
		
		// Grab the current sequence ID and bump up for the next message
		seqId = sequencer++;
		
	}

	/**
	 * @return the seqId
	 */
	public int getSeqId() {
		return seqId;
	}

	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}

	// Set the timestamp of the message
	public void setOrigTimestamp(Date timestamp)
	{
		this.originTimestamp = timestamp;
	}

	// Get the nodeID of the message
	public String getNodeId()
	{
		return this.nodeId;
	}

	// Set the nodeId of the message
	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}

	// Get the timestamp of the message
	public Date getTimestamp()
	{
		return this.originTimestamp;
	}

	
	// Route to convert a Message into a Json String
	public String toJsonString() {
				
		// Wrap the message in outer type of 'message'
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", this);
		
		// Convert to a JSON and send back
		return gson.toJson(map);
	}

	/**
	 * @return the originTimestamp
	 */
	public Date getOriginTimestamp() {
		return originTimestamp;
	}

	/**
	 * @param originTimestamp the originTimestamp to set
	 */
	public void setOriginTimestamp(Date originTimestamp) {
		this.originTimestamp = originTimestamp;
	}

	/**
	 * @return the requestAck
	 */
	public Boolean getRequestAck() {
		return requestAck;
	}

	/**
	 * @param requestAck the requestAck to set
	 */
	public void setRequestAck(Boolean requestAck) {
		this.requestAck = requestAck;
	}
}
