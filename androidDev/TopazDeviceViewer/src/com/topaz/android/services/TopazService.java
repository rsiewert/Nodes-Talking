package com.topaz.android.services;
import android.app.Service;
import android.content.Intent;
import android.os.*;

import java.io.FileDescriptor;
import java.util.*;

import android.util.Log;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.topaz.nodes.Device;


/**
 * Created by rbgodwin on 4/7/14.
 */
public class TopazService extends Service {

    //<editor-fold desc="Definitions">
    final static String APP = "TopazService";

    final static String MonitorThreadName = "TopazMonitor";

    // Message "whats"
    public final static int DEVICE_CHANGED = 1;

    //</editor-fold>

    //<editor-fold desc="Properties">
    TopazServiceBinder mBinder = new TopazServiceBinder();

    // Messengers that are notified when we have an update
    List<Messenger> mMessengers = new ArrayList<Messenger>();

    /*
    /**
     * A map of devices, by ID.
     */
    public Map<String, Device> DEVICE_MAP = new TreeMap<String, Device>();
    //</editor-fold>

    //<editor-fold desc="Private Methods">

    private void addDevice(Device device) {
        DEVICE_MAP.put(device.getNodeId(), device);
    }

    private void monitorDevices() {

        Device firstDevice = null;

        // For now we simply toggle the status of the first device
        while (true) {

            //If we do not have any devices check if some have been added
            if(this.DEVICE_MAP.size() == 0)
                refreshAllDevices();

            if(this.DEVICE_MAP.size() > 10)
                firstDevice = this.DEVICE_MAP.values().iterator().next();

            Log.d(APP, "monitorDevice: Have this many devices" + this.DEVICE_MAP.size());

            if (firstDevice != null) {
                firstDevice.setStatus(firstDevice.getStatus() ==
                                Device.STATUS.GREEN ? Device.STATUS.RED : Device.STATUS.GREEN);
                Log.d(APP, "monitorDevice: Toggled Status");

            int count=0;
                for(Device d : this.DEVICE_MAP.values())
                {
                    if(count == this.DEVICE_MAP.size()-1) {
                        this.DEVICE_MAP.remove(d.getNodeId());
                    }
                    count++;
                }
                notifyAllMessengers();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void notifyAllMessengers() {


        for(Messenger mess : this.mMessengers)
        {
            Message msg = Message.obtain();
            msg.what = this.DEVICE_CHANGED;
            try {
                mess.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private  void refreshAllDevices() {

        Log.d(APP, "refreshAllDevices:entered");

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://54.186.125.132:3000/getDevices", new AsyncHttpResponseHandler() {

            public void onSuccess(int statusCode,
                                  org.apache.http.Header[] headers,
                                  byte[] responseBody){

                Log.d(APP, "onSuccess:entered");

                // Convert JSON into an object
                Gson gson = new Gson();
                Device[] devices = gson.fromJson(new String(responseBody), Device[].class);

                for (int i=0; i < devices.length; i++) {
                    addDevice(devices[i]);
                }
                Log.d(APP, "onSuccess:exit after loading" + devices.length);
            }

            @Override
            public void onFailure(int statusCode,
                                  org.apache.http.Header[] headers,
                                  byte[] responseBody,
                                  java.lang.Throwable error)
            {
                Log.e(APP,"Error in communications with server:" +  error.getMessage()
                        + "\n");
            }
        });

    }
    //</editor-fold>

    //<editor-fold desc="Life Cycle Methods">
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(APP, "onStartCommand:entered");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(APP, "onDestroy:entered");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(APP, "onCreate:entered");
        Thread monitorThread = new Thread(new Runnable() {
            public void run() {
                monitorDevices();
            }
        });

        monitorThread.setName(this.MonitorThreadName);
        monitorThread.start();



    }
    //</editor-fold>

    //<editor-fold desc="Public Methods">
    @Override
    public IBinder onBind(Intent intent) {

        Log.d(APP, "onBind:entered");
        return mBinder;
    }

    public class TopazServiceBinder extends Binder {

        public TopazService getService() {

            // Return this instance of TopazService so clients can call public methods
            return TopazService.this;
        }
    }

    public Map<String,Device> getDeviceMap() {
        return this.DEVICE_MAP;
    }


    public void addMessenger(Messenger messenger)
    {
        this.mMessengers.add(messenger);
    }
    //</editor-fold>


}
