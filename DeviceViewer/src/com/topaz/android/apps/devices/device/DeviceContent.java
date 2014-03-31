package com.topaz.android.apps.devices.device;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.topaz.nodes.Device;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class DeviceContent {

	/**
	 * An array of sample (Device) items.
	 */
	public List<Device> DEVICES = new ArrayList<Device>();

	private static DeviceContent instance = null;
	
	/*
	/**
	 * A map of devices, by ID.
	 */
	public Map<String, Device> DEVICE_MAP = new HashMap<String, Device>();

	private void addDevice(Device device) {
		DEVICES.add(device);
		DEVICE_MAP.put(device.getNodeId(), device);
	}

	public static DeviceContent getInstance() {
	
		if(instance == null)
		{
			instance = new DeviceContent();
		}

		return instance;
	}
	
	private  DeviceContent() {
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://54.186.125.132:3000/getDevices", new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		        
		    	// Convert JSON into an object we can use in our gridview
		    	Gson gson = new Gson();	
		    	Device[] devices = gson.fromJson(response, Device[].class);
		    	
		    	for (int i=0; i < 100; i++) {
		    		addDevice(devices[i]);
		    		}
		    	
		    }
		    @Override
		    public void onFailure(Throwable throwable, String response) {
		    	System.out.println(response);
		    }
		});
	}
	
}
	
