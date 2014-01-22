package com.guide.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.guide.LaunchMainMenuActivity;
import com.guide.R;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class GetPlacesMainMenuTask extends AsyncTask<String, Integer, Boolean> {

	private static final String DEBUG_TAG = "GuideDebug";
	
	List<Place> places;
	Context context;
	GoogleMap mMap;
	LaunchMainMenuActivity activity;
	
	public GetPlacesMainMenuTask(Context context, LaunchMainMenuActivity a) {
		this.context = context;
		this.activity = a;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
		if(result == true && places != null){

			activity.addScores(places);
				
			/*
			CharSequence text = places.get(0).getName();
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			*/
			
		}
		else{
			CharSequence text = "Error retrieving places from server.";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		HttpClient client= new DefaultHttpClient();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		String hostUrl = "http://ec2-54-229-235-183.eu-west-1.compute.amazonaws.com:3000";
		String longitude = params[0];
		String latitude = params[1];
		String maxDistance= params[2];
		String limit = "10";
		if(params.length == 4){
			limit = params[3];
		}
		
		pairs.add(new BasicNameValuePair("longitude", longitude));
		pairs.add(new BasicNameValuePair("latitude", latitude));
		pairs.add(new BasicNameValuePair("maxdistance", maxDistance));
		pairs.add(new BasicNameValuePair("limit", limit));
		
		HttpGet request = 
				new HttpGet(hostUrl + "/places/near?" + URLEncodedUtils.format(pairs, "utf-8"));
		try {
			HttpResponse response = client.execute(request);
			Log.d(DEBUG_TAG, String.valueOf(response.getStatusLine().getStatusCode()));
			Log.d(DEBUG_TAG, hostUrl + "/places/near?" + URLEncodedUtils.format(pairs, "utf-8"));
			if(response.getStatusLine().getStatusCode() != 200){
				return false;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream stream = entity.getContent(); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				stream.close();
				String responseString = sb.toString();
				Log.d(DEBUG_TAG, responseString);
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				Type type = new TypeToken<List<Place>>(){}.getType();
				places = gson.fromJson(responseString, type);
				Log.d(DEBUG_TAG, "ENTRA ALLI");
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (JsonSyntaxException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	

}
