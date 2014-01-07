package com.guide;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends FragmentActivity{
	
	//drawer
    private ActionBarDrawerToggle mDrawerToggle;
	private String[] menu;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    
    

	
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
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
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
         * 
         * Launch first item, but before: check if the Android API is available 
         * 
         */
        
        selectItem(0);
		
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
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		// Depending on which item is pressed, perform different Activities
		if (position == 0){
			// Create a new mapfragment and specify what to show:
			// mapfragment can be overloaded
			Fragment mapfragment = new LaunchMapActivity();
		    // Insert the fragment by rep
			// Replace current view view (fragment_container)
		    mapfragment.getFragmentManager();
		    FragmentManager fragmentManager = getSupportFragmentManager();
		    fragmentManager.beginTransaction().replace(R.id.fragment_container, mapfragment).commit();
		} else {
		    Fragment fragment = new MenuItemFragment();
		    Bundle args = new Bundle();
		    args.putInt(MenuItemFragment.ARG_MENU_ITEM_NUMBER, position);
		    fragment.setArguments(args);
		    // Insert the fragment by replacing any existing fragment
		    FragmentManager fragmentManager = getSupportFragmentManager();
		    //fragment_container is the one to be replaced with sth
		    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
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
	
	/**
     * Fragment that appears in the "content_frame"
     * just exists for testing porpuses and creats the content
     * which is currently empty of item: 2,3,4
     * 
     */
    public static class MenuItemFragment extends Fragment {
        public static final String ARG_MENU_ITEM_NUMBER = "menu_number";

        public MenuItemFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.layout.fragment_menu_item, container, false);
	            int i = getArguments().getInt(ARG_MENU_ITEM_NUMBER);
	            String menu = getResources().getStringArray(R.array.menu)[i];
	            getActivity().setTitle(menu);
         return rootView;
        }
    }
}
