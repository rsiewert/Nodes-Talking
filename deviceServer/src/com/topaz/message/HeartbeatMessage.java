package com.topaz.message;

public class HeartbeatMessage extends Message {

	// General status for the node. GREEN indicates all good,
	// YELLOW indicates some issues, RED indicates critical failure
	// BLACK indicates status of the device is unknown
	public enum Status { GREEN, YELLOW, RED, BLACK };
	
	Status status = Status.GREEN;

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
