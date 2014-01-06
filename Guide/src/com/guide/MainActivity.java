package com.guide;

import java.io.IOException;
import java.util.List;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.IntentSender.SendIntentException;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks, 
	GooglePlayServicesClient.OnConnectionFailedListener, LocationListener{
	
	//drawer
    private ActionBarDrawerToggle mDrawerToggle;
	private String[] menu;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

	LocationRequest request;
	LocationClient client;
	Location currentLocation;
	private GoogleMap map;
	
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

        // menu initialize over
        
//        // initialize map
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setInterval(10000);
		request.setFastestInterval(5000);
		client = new LocationClient(MainActivity.this, this, this);
		client.connect();
		
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
	    // Create a new fragment and specify what to show
	    Fragment fragment = new MenuItemFragment();
	    Bundle args = new Bundle();
	    args.putInt(MenuItemFragment.ARG_MENU_ITEM_NUMBER, position);
	    fragment.setArguments(args);

	    // Insert the fragment by replacing any existing fragment
	    FragmentManager fragmentManager = getFragmentManager();
	    // map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	    fragmentManager.beginTransaction().replace(R.id.map, fragment).commit();

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
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class MenuItemFragment extends Fragment {
        public static final String ARG_MENU_ITEM_NUMBER = "menu_number";
        

        public MenuItemFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	// initialize map    		
            View rootView = inflater.inflate(R.layout.fragment_menu_item, container, false);
            int i = getArguments().getInt(ARG_MENU_ITEM_NUMBER);
            String menu = getResources().getStringArray(R.array.menu)[i];
            getActivity().setTitle(menu);
            
            
            return rootView;
        }
    }

	@Override
	public void onLocationChanged(Location location) {
		//Toast.makeText(MainActivity.this, "Location changed", Toast.LENGTH_SHORT).show();
		new MyGeoder().execute();
		currentLocation = location;
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, (float) 15.0);
	    map.animateCamera(cameraUpdate);
		addMarker(location.getLatitude(), location.getLongitude(),"Casa Paco", "Restaurante");
	    client.disconnect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(MainActivity.this, 0);
			} catch (SendIntentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
		client.requestLocationUpdates(request, this);
		
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
		client.removeLocationUpdates(this);
	}
	
	private class MyGeoder extends AsyncTask<Void, Void, Void> {

		List<Address> address = null;

		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Geocoder geocoder = new Geocoder(MainActivity.this);
			try {
				if (Geocoder.isPresent()) {
					address = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if ((address != null) && (address.size() > 0)) {
				Toast.makeText(MainActivity.this, "Locality " + address.get(0).getLatitude() + 
						"," + address.get(0).getLongitude(), Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(MainActivity.this, "Geocoder unavailable", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public void addMarker(double latitude, double longitude, String title, String description) {
		LatLng position = new LatLng(latitude, longitude);
		MarkerOptions options = new MarkerOptions();
		options.position(position);
		options.title(title);
		options.snippet(description);
		options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		//options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
		map.addMarker(options);
	}
}
