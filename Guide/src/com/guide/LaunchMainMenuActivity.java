package com.guide;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.guide.db.GetPlacesMainMenuTask;
import com.guide.db.Place;
import com.guide.db.PlaceAdapter;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Location;
import android.os.Bundle;

public class LaunchMainMenuActivity extends ListFragment implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener{
	
	
	private ArrayList<Place> places; 
	
	Location currentLocation;
	
	private LocationRequest lr;
	private LocationClient lc;
	
	private static final String DEBUG_TAG = "GuideDebug";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		lr = LocationRequest.create();
		lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		lc = new LocationClient(this.getActivity().getApplicationContext(),
				this, this);
		
		places = new ArrayList<Place>();
		   
	    setListAdapter(new PlaceAdapter(getActivity(), places));
		
		if(LaunchMapActivity.mCurrentLocation != null){
			GetPlacesMainMenuTask getScoresTask = new GetPlacesMainMenuTask(getActivity(), this);
			Location location = LaunchMapActivity.mCurrentLocation;
			String latitude = String.valueOf(location.getLatitude());
		    String longitude = String.valueOf(location.getLongitude());
		    String maxDistance = "1000";
		    String limit = "30";
			getScoresTask.execute(longitude, latitude, maxDistance, limit);
		}
	}
	
	 @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		return super.onCreateView(inflater, container, savedInstanceState);
	  }
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	public void addScores(List<Place> list){
			
		 places.addAll(list);
		 final PlaceAdapter adapter = (PlaceAdapter) getListAdapter();
		 adapter.notifyDataSetChanged();
		 Log.d(DEBUG_TAG, "ENTRA ALLI");
		 Log.d(DEBUG_TAG, list.get(0).getCategory());
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		lc.connect();
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		lc.disconnect();
		super.onStop();
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		currentLocation = location;
	    if (currentLocation != null){
	    	GetPlacesMainMenuTask getScoresTask = new GetPlacesMainMenuTask(getActivity(), this);
	        String latitude = String.valueOf(currentLocation.getLatitude());
	        String longitude = String.valueOf(currentLocation.getLongitude());
	        String maxDistance = "1000";
	        String limit = "30";
	    	getScoresTask.execute(longitude, latitude, maxDistance, limit);
	    }
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		lc.requestLocationUpdates(lr, this);
	    // Global variable to hold the current location
	    currentLocation = lc.getLastLocation();
	    if (currentLocation != null){
	    	GetPlacesMainMenuTask getScoresTask = new GetPlacesMainMenuTask(getActivity(), this);
	        String latitude = String.valueOf(currentLocation.getLatitude());
	        String longitude = String.valueOf(currentLocation.getLongitude());
	        String maxDistance = "1000";
	        String limit = "30";
	    	getScoresTask.execute(longitude, latitude, maxDistance, limit);
	    }
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
