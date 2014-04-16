package com.topaz.android.apps;
import java.util.*;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.topaz.android.services.TopazService;
import com.topaz.nodes.Device;

import javax.security.auth.callback.Callback;


public class DeviceContent {

    // Brief description for devices
    private List<String> Briefs = new ArrayList<String>();

    // Details for devices
    private List<String> Details = new ArrayList<String>();

    // Interface that activities must implement so they can be notified of changes
    public interface OnDeviceDataChangedListener {
        /** Called by DeviceContent when Device data has changed */
        public void onDeviceDataChanged();
    }

    // The instance is associated with a single activity
    private static Activity mActivity = null;
    private static DeviceContent instance = null;


    // Collection of callbacks to notify when device data changes
    private List<OnDeviceDataChangedListener> changedCallbacks = new ArrayList<OnDeviceDataChangedListener>();

    /*
    /**
     * A map of devices, by ID.
     */

    public Map<String, Device> DEVICE_MAP = new HashMap<String, Device>();

    final static String APP = "DeviceContent";

    // A binder for communications with the Topaz Service
    TopazService.TopazServiceBinder mTopazConnect = null;

    // Will use this to communicate with the TopazService
    TopazService mTopazService = null;

    // A context for communicating with the Device Service
    Context context = null;

    // A message handler to receive notifications of changes to items of interest
    private Handler mHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.d(APP, "IncomingHandler: Entered with:"+ msg.what);
            Log.d(APP, "IncomingHandler: Running in "+ Thread.currentThread().getName());

            // Switch on the type of message
            switch (msg.what) {
                case TopazService.DEVICE_CHANGED:
                {
                    // Update the devices
                    refreshDeviceContents();
                    notifyCallbacks();
                    break;
                }
                default:
                    Log.d(APP, "IncomingHandler: Default");
            }
        }
    };

    // Messenger to send over to the TopazService so we can receive notifications of changes
    final Messenger mMessenger = new Messenger(mHandler);

    // Notify all registered listeners that the underlying device data has changed
    private void notifyCallbacks() {

        Log.d(APP,"notifyCallbacks:entered");
        for(OnDeviceDataChangedListener cb : this.changedCallbacks) {
            Log.d(APP,"notifyCallbacks: Notifying:");
            cb.onDeviceDataChanged();
        }
    }


    public List<String> getBriefs() {
        return Briefs;
    }

    public List<String> getDetails() {
        return Details;
    }

    public Map<String,Device> getDeviceMap()
    {
        return this.DEVICE_MAP;
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder)
        {
            // Cast the supplied binder and get the interface (in this case the service)
            TopazService.TopazServiceBinder myBinder = (TopazService.TopazServiceBinder) binder;
            mTopazService = myBinder.getService();

            //Let the user know we are connected
            Toast.makeText(context, "Topaz Service Connected", Toast.LENGTH_SHORT).show();

            // Add our messenger to receive notifications of changes
            mTopazService.addMessenger(mMessenger);
        }

        public void onServiceDisconnected(ComponentName className) {
            mTopazConnect = null;
        }
    };

    //A callback to be notified by the server when Data changes

    public void onDevicesChange() {

        refreshDeviceContents();

    }
    protected void refreshDeviceContents() {

        Toast.makeText(context, "Refreshing Contents", Toast.LENGTH_SHORT).show();

        // Ask the Topaz Server for Devices
        this.DEVICE_MAP = mTopazService.getDeviceMap();
        this.Details.clear();
        this.Briefs.clear();


        for(Device d : this.DEVICE_MAP.values()) {
            this.Details.add("\tNode Id: " + d.getNodeId() + "\n\n \tStatus: " + d.getStatus() );
            this.Briefs.add(d.getDescription());
        }

        Log.d(APP, "refreshDeviceContents has this many details:" + this.Details.size());
    }

	private void addDevice(Device device) {
		DEVICE_MAP.put(device.getNodeId(), device);
	}

	public static DeviceContent getInstance(Activity activity) {

        Log.d(APP,"DeviceContent getInstance: Entered with activity:" + activity);

        // We have one instance for an activity. if the activity has changed then
        // create a new instance

		if(instance == null)
		{
			instance = new DeviceContent(activity);
            mActivity = activity;
		}
        else if(mActivity != activity)
        {
            instance = new DeviceContent(activity);
            mActivity = activity;
        }

		return instance;
	}
	
	private  DeviceContent(Activity activity) {

        Log.d(APP,"DeviceContent Constructor: Entered with activity:" + activity);
        // Check if the activity implements the required callback interface
        OnDeviceDataChangedListener mDeviceChangedListener;

        try {
            mDeviceChangedListener = (OnDeviceDataChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "Activity must implement OnDeviceDataChangedListener");
        }

        // Place the activity in our collection of activities to be notified
        this.changedCallbacks.add(mDeviceChangedListener);

        // Save our context
        this.context = activity;



        // Create an intent to crank up the TopazServer that interacts with the
        // Topaz infrastructure
        Intent intent = new Intent(this.context, TopazService.class);

//      Bind to it.
        this.context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}



    // Adapters
}
	
