package com.guide;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ImageView;
import android.widget.Toast;

public class LaunchMapActivity extends Fragment implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */
	private static GoogleMap mMap;
	private LocationRequest lr;
	private LocationClient lc;
	private MapFragment mapFragment;
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

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		} try {

			view = inflater.inflate(R.layout.activity_launch_map, container,
					false);
			mapFragment = ((MapFragment) this.getActivity()
					.getFragmentManager().findFragmentById(R.id.map));
			// iv = (ImageView) view.findViewById(R.id.iv);
			mMap = mapFragment.getMap();
			// Change here what the map should
			setUpMap();

			MapsInitializer.initialize(this.getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			Toast.makeText(getActivity(), "Google Play Services missing !",
					Toast.LENGTH_LONG).show();
		} catch (InflateException e) {
			Toast.makeText(getActivity(), "Problems inflating the view for Maps!",
					Toast.LENGTH_LONG).show();
		} catch (NullPointerException e) {
			Toast.makeText(getActivity(), "Google Play Services missing !",
					Toast.LENGTH_LONG).show();
		}

		// setup the map
		// setUpMapIfNeeded();
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

	// setUpMap() sets up a map
	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
	}
	
	// this function performs change on the map
	public static void alterMap(int option){
		mMap.clear();
		// if general show all
		if (option == 0){
			mMap.addMarker(new MarkerOptions()
	        .position(new LatLng(40, 10))
	        .title("Hello world"));
		} else if (option == 1) {
			mMap.addMarker(new MarkerOptions()
	        .position(new LatLng(39, 0))
	        .title("Dito!"));
		} else {
			
		}
	}

	public void onDestroyView() {
		super.onDestroyView();
	}

	public void onLocationChanged(Location l2) {
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
				new LatLng(l2.getLatitude(), l2.getLongitude()),12);
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
