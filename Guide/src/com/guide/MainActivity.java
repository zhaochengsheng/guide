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
	private Fragment mainMenuFragment;
	private Fragment productsFragment;
	
	private String PREVIOUS_FRAGMENT = "PREVIOUS_FRAGMENT";
	private final int mapF = 0;
	private final int settingsF = 1;
	private final int mainF = 2;
	private final int productsF = 3;
	
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
		
		if (savedInstanceState != null) {
			int previous_instance = savedInstanceState.getInt(PREVIOUS_FRAGMENT);
			switch(previous_instance){
				case mapF:
					startMapActivity();
					break;
				case mainF:
					startMainMenuActivity();
					break;
				case settingsF:
					startSettingsActivity();
					break;
				case productsF:
					startProductsActivity();
					break;
			}
		}
		else{
			startMainMenuActivity();
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
			onFragmentChange("mainmenu");
		}else if (position == 1) {
			// Create View for Products
			onFragmentChange("products");
		}else if (position == 2) {
			onFragmentChange("map");
			LaunchMapActivity.alterMap();
		} else if (position == 3) {
			// Create View for SettingsMenu
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
		if (name == "settings" && settingsFragment == null) {
			stopMapActivity();
			stopMainMenuActivity();
			stopProductsActivity();
			startSettingsActivity();
		} else if (name == "map") {
			stopSettingsActivity();
			stopMainMenuActivity();
			stopProductsActivity();
			startMapActivity();
		} else if(name == "mainmenu"){
			stopSettingsActivity();
			stopMapActivity();
			stopProductsActivity();
			startMainMenuActivity();
		} else if(name == "products"){
			stopSettingsActivity();
			stopMapActivity();
			stopMainMenuActivity();
			startProductsActivity();
		}
	}

	/*
	 * Initializes the SettingsFragment
	 */
	public void startSettingsActivity() {
		settingsFragment = new LaunchSettingsActivity();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container_preferences, settingsFragment);
		transaction.commit();
	}

	/*
	 * Initializes the MapFragment
	 */
	public void startMapActivity() {
		mapfragment = new LaunchMapActivity();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container_map, mapfragment);
		// if being added to the Backstack it will revert the fragment_container
		// and the view is empty.
		transaction.commit();
	}
	
	/*
	 * Initializes the MainMenu Fragment
	 */
	public void startMainMenuActivity(){
		mainMenuFragment = new LaunchMainMenuActivity();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container_main_menu, mainMenuFragment);
		// if being added to the Backstack it will revert the fragment_container
		// and the view is empty.
		transaction.commit();
	}
	
	/*
	 * Initializes the Products Fragment
	 */
	public void startProductsActivity(){
		productsFragment = new LaunchProductsActivity();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container_products, productsFragment);
		// if being added to the Backstack it will revert the fragment_container
		// and the view is empty.
		transaction.commit();
	}
	
	public void stopMapActivity(){
		if(mapfragment != null){
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.remove(mapfragment);
			transaction.commit();
			mapfragment = null;
		}
	}
	
	public void stopMainMenuActivity(){
		if(mainMenuFragment != null){
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.remove(mainMenuFragment);
			transaction.commit();
			mainMenuFragment = null;
		}
	}
	
	public void stopSettingsActivity(){
		if(settingsFragment != null){
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.remove(settingsFragment);
			transaction.commit();
			settingsFragment = null;
		}
	}
	
	public void stopProductsActivity(){
		if(productsFragment != null){
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.remove(productsFragment);
			transaction.commit();
			productsFragment = null;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

		if(mapfragment != null){
			outState.putInt(PREVIOUS_FRAGMENT, mapF);
		}else if(mainMenuFragment != null){
			outState.putInt(PREVIOUS_FRAGMENT, mainF);
		}else if(productsFragment != null){
			outState.putInt(PREVIOUS_FRAGMENT, productsF);
		}else{
			outState.putInt(PREVIOUS_FRAGMENT, settingsF);
		}	
		
		stopMainMenuActivity();
		stopMapActivity();
		stopSettingsActivity();
		stopProductsActivity();
		
		
		super.onSaveInstanceState(outState);
	}


	
	

}
