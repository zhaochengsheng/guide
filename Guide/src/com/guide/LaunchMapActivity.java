package com.guide;

import com.google.android.gms.maps.*;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LaunchMapActivity extends Fragment{
/**
 * Note that this may be null if the Google Play services APK is not available.
 */
private GoogleMap mMap;

public LaunchMapActivity(){
    // this will be the constructur to overload some methods....
}

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setUpMapIfNeeded();
    //setContentView(R.layout.activity_launch_map);
}

public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
     //setUpMapIfNeeded();
     View rootView = inflater.inflate(R.layout.activity_launch_map, container, false);
     setUpMapIfNeeded();
     return rootView;
}

@Override
public void onResume() {
    super.onResume();
    setUpMapIfNeeded();
}

private void setUpMapIfNeeded() {
    // Do a null check to confirm that we have not already instantiated the map.
    if (mMap == null) {
        // Try to obtain the map from the SupportMapFragment.
        mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            setUpMap();
        }
    }
}

// setUpMap() sets up a map
private void setUpMap() {
	if (mMap.isMyLocationEnabled()==false){
	mMap.setMyLocationEnabled(true);
	}
}

// destroy the map if fragment is changed
private void deleteOldMap(){
	SupportMapFragment mapFragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
	if (mapFragment != null){
		FragmentManager fM = getFragmentManager();
        fM.beginTransaction().remove(mapFragment).commit();
	}
	 
}

// destroy this view
public void onDestroyView() {
	 super.onDestroyView();
	 deleteOldMap();
}

}
