package com.topaz.android.apps.devices;

import com.topaz.android.apps.devices.device.DeviceContent;
import com.topaz.devices.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DeviceListActivity extends FragmentActivity implements
		DeviceListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_list);


		DeviceContent deviceContent = DeviceContent.getInstance();
		if(deviceContent == null)
			System.out.println("Error in Device");
		
		if (findViewById(R.id.device_detail_container) != null) {
			// Running two pane mode
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((DeviceListFragment) getSupportFragmentManager().findFragmentById(
					R.id.device_list)).setActivateOnItemClick(true);
		}

	}

	/**
	 * Callback method from {@link DeviceListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(DeviceDetailFragment.ARG_ITEM_ID, id);
			DeviceDetailFragment fragment = new DeviceDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.device_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, DeviceDetailActivity.class);
			detailIntent.putExtra(DeviceDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
