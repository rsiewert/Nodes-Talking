package com.topaz.android.apps;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeviceBriefFragment extends ListFragment {
    OnDeviceSelectedListener mCallback;

    static final String APP = "DeviceBriefFragment";
    // The container Activity must implement this interface so the frag can deliver messages

    public interface OnDeviceSelectedListener {

        /** Called by DeviceBriefFragment when a list item is selected */
        public void onDeviceSelected(int position);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(APP, "onCreate Entered:");

        // Create an array adapter for the list view, using the Devices headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1,
                Devices.Briefs));
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Log.d(APP, "onListItemClicked: Entered");

        // Notify the parent activity of selected item
        mCallback.onDeviceSelected(position);
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


}