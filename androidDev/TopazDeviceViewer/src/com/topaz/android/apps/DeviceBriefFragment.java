package com.topaz.android.apps;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class DeviceBriefFragment extends ListFragment
    {

    OnDeviceSelectedListener mCallback;

    static final String APP = "DeviceBriefFragment";

     // Currently selected item
     private int mSelected  = 0;

    // Source of our device data
    DeviceContent mDeviceContent = null;

    // An adapter for the device brief data
    BaseAdapter mBriefAdapter;

    public interface OnDeviceSelectedListener {
        /** Called by DeviceBriefFragment when a list item is selected */
        public void onDeviceSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(APP, "onCreate Entered:");

        this.mDeviceContent = DeviceContent.getInstance(this.getActivity());

        this.mBriefAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1,mDeviceContent.getBriefs());

        // Create an array adapter for the list view, using the Devices headlines array
        setListAdapter(this.mBriefAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(APP, "onStart Entered:");
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(APP, "onAttach: Entered");

         // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnDeviceSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDeviceSelectedListener");
        }
    }

        // Sets the selected item from the top and stores it's position
        public void setSelectionFromTop(int position){

            Log.d(APP,"setSelectionFromTop: " + position);
            getListView().setSelectionFromTop(position,0);
            this.mSelected = position;

        }
        // Sets the selected item and stores it's position
        public void setItemChecked(int position, boolean selected){

            Log.d(APP,"setItemChecked: " + position);
            getListView().setItemChecked(position, selected);
            this.mSelected = position;

        }

        @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Log.d(APP, "onListItemClicked: Entered");

        // Notify the parent activity of selected item
        mCallback.onDeviceSelected(position);

            this.mSelected = position;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(APP, "onCreateView Entered:");

        return super.onCreateView(inflater, container, savedInstanceState);
        }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(APP, "onPause Entered:");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(APP, "onStop Entered:");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(APP, "onDestroy Entered:");
    }


    // Callback for when the DeviceData is changed
    public void onDeviceDataChanged()
    {

       Log.d(APP, "onDeviceDataChanged:");
        this.mBriefAdapter.notifyDataSetChanged();

        // Adjust selected position if some devices have been removed at the tail of the list
        if(this.mBriefAdapter.getCount() <= this.mSelected)
        {
            this.mSelected = this.mBriefAdapter.getCount() -1;
            this.getListView().setItemChecked(this.mSelected, true);
        }
    }


    // Adapter for the underlying Device Data
    public class DeviceBriefAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }


        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

}