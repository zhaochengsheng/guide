package com.guide;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ImageView;
import android.widget.Toast;

public class LaunchMapActivity extends Fragment implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */
	private GoogleMap mMap;
	LatLng latlng;
	private LocationRequest lr;
	private LocationClient lc;
	MapFragment mapFragment;
	// ImageView iv;
	private static View view;

	public LaunchMapActivity() {
		// this will be the constructur to overload some methods....
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 lr = LocationRequest.create();
         lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
         lc = new LocationClient(this.getActivity().getApplicationContext(),
                 this, this);
         lc.connect();
		
		// setUpMapIfNeeded();
		// setContentView(R.layout.activity_launch_map);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}

		try {
			view = inflater.inflate(R.layout.activity_launch_map, container,
					false);

			mapFragment = ((MapFragment) this.getActivity()
					.getFragmentManager().findFragmentById(R.id.map));
			//iv = (ImageView) view.findViewById(R.id.iv);

			mMap = mapFragment.getMap();
			mMap.getUiSettings().setAllGesturesEnabled(false);
			mMap.getUiSettings().setMyLocationButtonEnabled(false);
			mMap.setMyLocationEnabled(true);
			mMap.getUiSettings().setZoomControlsEnabled(false);

			MapsInitializer.initialize(this.getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			Toast.makeText(getActivity(), "Google Play Services missing !",
					Toast.LENGTH_LONG).show();
		} catch (InflateException e) {
			Toast.makeText(getActivity(), "Problems inflating the view !",
					Toast.LENGTH_LONG).show();
		} catch (NullPointerException e) {
			Toast.makeText(getActivity(), "Google Play Services missing !",
					Toast.LENGTH_LONG).show();
		}


		// setup the map
		setUpMapIfNeeded();
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	// setUpMap() sets up a map
	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
	}

	// destroy the map if fragment is changed
	private void deleteOldMap() {
		SupportMapFragment mapFragment = ((SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.map));
		if (mapFragment != null) {
			FragmentManager fM = getFragmentManager();
			fM.beginTransaction().remove(mapFragment).commit();
		}

	}

	// destroy this view
	public void onDestroyView() {
		super.onDestroyView();
		deleteOldMap();
	}

	public void onLocationChanged(Location l2) {
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                new LatLng(l2.getLatitude(), l2.getLongitude()), 15);
        mMap.animateCamera(cameraUpdate);
	}
	
    @Override
    public void onConnectionFailed(ConnectionResult arg0) {

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        lc.requestLocationUpdates(lr, this);

    }

    @Override
    public void onDisconnected() {

    }



}
