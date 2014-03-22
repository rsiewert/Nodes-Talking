package com.topaz.sensors;

public class TemperatureSensor extends Sensor {

	double temperature;

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public TemperatureSensor(String description, Sensor.TYPE type) {
		
		super(description, type);
	
	}
}
