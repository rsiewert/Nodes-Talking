package com.topaz.android.apps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.topaz.android.services.TopazService;

public class TopazDeviceViewActivity extends FragmentActivity
        implements DeviceBriefFragment.OnDeviceSelectedListener, DeviceContent.OnDeviceDataChangedListener {

    static final String APP = "TopazDeviceViewActivity";
    static final String LIST_SELECTION = "ListSelection";

    //List selection. We save it to allow keeping it between portrait and landscape modes
    int listSelection = 0;

    // Boolean indicating if the mode is landscape or portrait
    Boolean isLandscape;

    // Handy to have around to coordinate state between mode switches (landscape and portrait)
    DeviceDetailFragment detailFragment = null;
    DeviceBriefFragment briefFragment = null;

    // A binder for communications with the Topaz Service
    TopazService.TopazServiceBinder mTopazConnect = null;

    // Will use this to communicate with the TopazService
    TopazService mTopazService = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(APP, "onDestroy: Entered");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(APP, "onPause: Entered");
    }

    @Override
    protected void onResume() {
        Log.d(APP, "onResume: Entered");
        super.onResume();
        if (this.isLandscape) {

            // Landscape mode.  Load in the correct detail to match portrait
            briefFragment = getBriefFragment(R.id.device_brief_fragment);

            if (briefFragment == null) {
                Log.e(this.APP, "Error in retrieving briefFrag");
                return;
            }

            this.onDeviceSelected(this.listSelection);
        } else {

            Fragment fragment = getFragment(R.id.portrait_container);

            if (fragment == null) {
                Log.e(this.APP, "Error in retrieving briefFrag in Portrait");
                return;
            }

            if (fragment.getClass() == DeviceBriefFragment.class) {
                briefFragment = (DeviceBriefFragment) fragment;
            } else {
                briefFragment = null;
            }
        }
    }

    private DeviceBriefFragment getBriefFragment(int id) {
        return (DeviceBriefFragment) getSupportFragmentManager()
                .findFragmentById(id);
    }

    private Fragment getFragment(int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }


    private DeviceDetailFragment getDetailFragment(int id) {
        return (DeviceDetailFragment) getSupportFragmentManager()
                .findFragmentById(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(APP, "onStart: Entered");
        Log.d(APP, "Running in "+ Thread.currentThread().getName());

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(APP, "onStop: Entered");
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices);
        Log.d(APP, "onCreate: Enter");

        // Restore the listSelection if present
        if (savedInstanceState != null) {
            listSelection = savedInstanceState.getInt(LIST_SELECTION);
        }
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.portrait_container) != null) {

            this.isLandscape = false;

            // Check if we have an existing brief fragment for the portrait container
            Fragment fragment = getFragment(R.id.portrait_container);
            if (fragment != null) {

                // if we have a fragment then remove it. We want to
                // be consistent with any prior view (from a rotation for example)
                getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
            }
            // Check if we have an existing detail fragment left over from a landscape view
            fragment = getFragment(R.id.device_detail_fragment);

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
            }
            // Create a new brief fragment to display
            briefFragment = new DeviceBriefFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
               .add(R.id.portrait_container, briefFragment)
               .addToBackStack(null).commit();
        }
        // landscape layout
        else {

            Log.d(APP, "onCreate: brief frag container does not exist");
            this.isLandscape = true;

            // Check if the brief fragment has been instantiated for the landscape view. If not create it
            briefFragment = getBriefFragment(R.id.device_brief_fragment);
            if (briefFragment == null) {

                //  Device Brief fragment
                briefFragment = new DeviceBriefFragment();

                // Add the fragment to the 'device_brief' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.device_brief_fragment, briefFragment).commit();
            }

            // Check if the detail fragment has been instantiated for the landscape view. If not create it
            detailFragment = this.getDetailFragment(R.id.device_detail_fragment);

            if (detailFragment == null) {

                //  Device Detail fragment
                detailFragment = new DeviceDetailFragment();

                // In case this activity was started with special instructions from an Intent,
                // pass the Intent's extras to the fragment as arguments
                detailFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'device_detail' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.device_detail_fragment, detailFragment)
                        .commit();
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(APP, "onSaveInstanceState Entered:" );
        outState.putInt(LIST_SELECTION, this.listSelection);
    }

    public void onDeviceSelected(int position)
    {
        // The user selected a device from the DeviceBriefFragment
        Log.d(APP, "onDeviceSelected Entered:" + position);

        this.listSelection = position;

        // Check if we are horizontal
        if (this.isLandscape) {
            // Check if the detail fragment has been instantiated for the landscape view. If not create it
            detailFragment = this.getDetailFragment(R.id.device_detail_fragment);

            if (detailFragment != null) {

                // Call the method in the DeviceDetailFragment to update its content
                detailFragment.updateDeviceDetailView(position);
            }
            briefFragment = this.getBriefFragment(R.id.device_brief_fragment);
            briefFragment.setItemChecked(position,true);

        } else { // Portrait mode

           // Create a new fragment
            detailFragment = new DeviceDetailFragment();
            detailFragment.setCurrentPosition(position);

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.portrait_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    // Callback for when the DeviceData is changed
    public void onDeviceDataChanged() {
        Log.d(APP, "onDeviceDataChanged:" + this);

        if (findViewById(R.id.portrait_container) != null) {
            Fragment fragment = getFragment(R.id.portrait_container);
            if (fragment != null) {
                if (fragment instanceof DeviceBriefFragment)
                    ((DeviceBriefFragment) fragment).onDeviceDataChanged();
                else
                    ((DeviceDetailFragment) fragment).onDeviceDataChanged();
            }
        } else {
            briefFragment = this.getBriefFragment(R.id.device_brief_fragment);
            if (briefFragment != null) {
                briefFragment.onDeviceDataChanged();
            }

            detailFragment = this.getDetailFragment(R.id.device_detail_fragment);
            if (detailFragment != null) {
                detailFragment.onDeviceDataChanged();
            }
        }

    }
}