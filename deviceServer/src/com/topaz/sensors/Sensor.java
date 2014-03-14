package com.topaz.sensors;

public class Sensor {

	public static enum TYPE {TEMPERATURE, AIRPRESSURE, RAINFALL, MOTIONDETECTION}
	
	// A description of the sensor
	String description;

	// The type of sensor 
	Sensor.TYPE type;

	public Sensor.TYPE getType() {
		return type;
	}

	public void setType(Sensor.TYPE type) {
		this.type = type;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Sensor(String description, Sensor.TYPE type) {
		
		// Set the description and type 
		this.description = description;
		this.type = type;
	}
	
	public Sensor() {}
	
}
