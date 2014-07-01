package com.topaz.communications.messages;

import com.google.gson.*;
import java.util.*;

public class Message {

	// Gson will provide means to covert between message objects and JSON
	private static Gson gson = null;

	// Timestamp for the message
	Date originTimestamp;
	
	// Node identification for originator of the message
	String nodeId;

	// Each message has a sequence ID
	long seqId;
	
	// Whether we want an ack on the message
	Boolean requestAck=false;

    public boolean getGenSeqNumber() {
        return genSeqNumber;
    }

    public void setGenSeqNumber(boolean genSeqNumber) {
        this.genSeqNumber = genSeqNumber;
    }

    // Whether we want a sequence number generated
    protected transient boolean genSeqNumber = true;


	public Message() {
		
		// If GSON has not been created then create it
		if (gson == null)
			gson = new Gson();
		
		// Creation time of the message
		originTimestamp = new Date();
		

	}

	/**
	 * @return the seqId
	 */
	public long getSeqId() {
		return seqId;
	}

	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(long seqId) {
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

		// Wrap the message in outer type of 'data'
		Map<String, Object> finalMap = new HashMap<String, Object>();
		finalMap.put("data", map);
		
		// Convert to a JSON and send back
		return gson.toJson(finalMap);
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
