/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.topaz.android.apps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.topaz.nodes.Device;

public class DeviceDetailFragment extends Fragment {

    int mCurrentPosition = 0;
    static final String APP = "DeviceDetailFragment";

    DeviceContent mDeviceContent = null;

    //<editor-fold desc="Get-sets">
    // Return the position being displayed by the fragment
    public int getCurrentPosition(){

        return this.mCurrentPosition;
    }

    // Set the position being displayed by the fragment
    public void setCurrentPosition(int position){

        mCurrentPosition = position;
    }
    //</editor-fold>

    //<editor-fold desc="Life Cycle">
    @Override
    public void onPause() {
        super.onPause();
        Log.d(APP, "onPause Entered:");

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(APP, "onSaveInstanceState Entered:");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(APP, "onStop Entered:");


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mDeviceContent = DeviceContent.getInstance(this.getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(APP, "onDestroy Entered:");
    }
    //</editor-fold>

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(APP, "onCreateView Entered:");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.device_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(APP, "onStart Entered:");
        this.updateDeviceDetailView(getCurrentPosition());

    }

    public void updateDeviceDetailView(int position) {

        mCurrentPosition = position;

        TextView deviceDetail = (TextView) getActivity().findViewById(R.id.device_detail);

        if(deviceDetail == null)
        {
            Log.d(APP, "updateDeviceDetailView Error in retrieving deviceDetail:");
            return;
        }

        Log.d(APP, "updateDeviceDetailView position:"+ position);
        if(this.mDeviceContent.getDetails().size() != 0) {
           deviceDetail.setText(this.mDeviceContent.getDetails().get(position));
        }
    }

    // Callback method to update view when underlying device data changes
    public void onDeviceChange(){

        this.updateDeviceDetailView(this.mCurrentPosition);
    }

    public void onDeviceDataChanged() {

        Log.d(APP, "onDeviceDataChanged: Entered");
        updateDeviceDetailView(this.getCurrentPosition());

    }
}