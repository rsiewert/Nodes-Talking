package com.topaz.nodes;
import com.topaz.sensors.*;

public class TestDevice extends Device {

	public TestDevice() {
		
			// This test device has a Temperature sensor
		this.sensors.add(new TemperatureSensor("Temperature Sensor",
				Sensor.TYPE.TEMPERATURE));
		
		}
	}

