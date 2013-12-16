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
import android.content.IntentSender.SendIntentException;
import android.support.v7.app.ActionBarActivity;
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

public class MainActivity extends ActionBarActivity implements GooglePlayServicesClient.ConnectionCallbacks, 
	GooglePlayServicesClient.OnConnectionFailedListener, LocationListener{

	LocationRequest request;
	LocationClient client;
	Location currentLocation;
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setInterval(10000);
		request.setFastestInterval(5000);
		client = new LocationClient(MainActivity.this, this, this);
		client.connect();
	
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
