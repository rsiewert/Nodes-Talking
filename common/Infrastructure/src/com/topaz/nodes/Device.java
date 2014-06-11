package com.topaz.nodes;
import com.topaz.communications.apis.Api;
import com.topaz.controls.*;
import com.topaz.sensors.*;

public class Device extends Node  {

	// A device has a list of controls and sensors
	ControlList controls = new ControlList();
	SensorList sensors = new SensorList();
	
	public static abstract class builder<T extends builder<T>> extends Node.builder<T> {

		// list of controls and sensors
		ControlList controls;
		SensorList sensors;

		
		public T controlList(ControlList controls) {
			this.controls = controls;
			return self();
		}

		public T sensors(SensorList sensors) {
			this.sensors = sensors;
			return self();
		}
		
		public Device build() {
			return new Device(this);
		}
	}
	
	 private static class builder2 extends builder<builder2> {
	        @Override
	        protected builder2 self() {
	            return this;
	        }
	    }

    public static builder<?> builder() {
	        return new builder2();
	    }

    protected Device(builder<?> builder) {
		super(builder);
		this.sensors = builder.sensors;
		this.controls = builder.controls;
 	}
	
	public Device() {}

}
