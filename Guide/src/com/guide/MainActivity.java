package com.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	// drawer
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] menu;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private Fragment mapfragment;
	private Fragment settingsFragment;

	FragmentManager fragmentManager = getSupportFragmentManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize drawer
		mTitle = mDrawerTitle = getTitle();
		menu = getResources().getStringArray(R.array.menu);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, menu));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		/*
		 * Setup the fragments (create them and attach the MapActivity to the
		 * View, therefore it should be shown as default while settings should
		 * be hidden by default
		 */
		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			}
			startMapActivity();
			startSettingsActivity();
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			performAction(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void performAction(int position) {

		/*
		 * Call on Fragment Change to make sure the right Fragment is shown.
		 */
		if (position == 0) {
			// TODO Call Menú principal Fragment
		} else if (position == 1) {
			onFragmentChange("map");
			LaunchMapActivity.alterMap();
		} else if (position == 3) {
			// Create View for SettingsMenu or Other
			onFragmentChange("settings");
		}

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(menu[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/*
	 * Switch to SettingsFragment and Back
	 */
	public void onFragmentChange(String name) {
		if (name == "settings") {
			if (settingsFragment.isHidden() == true) {
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.hide(mapfragment);
			transaction.show(settingsFragment);
			transaction.addToBackStack(null);
			transaction.commit();
			}
		} else if (name == "map") {
			if (mapfragment.isHidden() == true) {
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.hide(settingsFragment);
				transaction.show(mapfragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		}
	}

	/*
	 * Initializes the SettingsFragment
	 */
	public void startSettingsActivity() {
		settingsFragment = new LaunchSettingsActivity();
		settingsFragment.getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.fragment_container, settingsFragment);
		transaction.hide(settingsFragment);
		transaction.commit();
	}

	/*
	 * Initializes the MapFragment
	 */
	public void startMapActivity() {
		mapfragment = new LaunchMapActivity();
		mapfragment.getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.fragment_container, mapfragment);
		// if being added to the Backstack it will revert the fragment_container
		// and the view is empty.
		transaction.commit();
	}

}
