package com.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class LaunchSettingsActivity extends Fragment {

	Fragment settingsFragment;
	View view;
	
	public LaunchSettingsActivity(){
		// empty constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_launch_settings);
	}
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            
    	try {
			view = inflater.inflate(R.layout.activity_launch_settings, container,
					false);
			settingsFragment = getFragmentManager().findFragmentById(R.id.settings);
		} catch (InflateException e) {
			Toast.makeText(getActivity(), "Problems inflating the view !",
					Toast.LENGTH_LONG).show();
		}
		
    	return view;
    }
    
	public void onDestroyView() {
		super.onDestroyView();
		// This removes the map properly so it can be reinitialized.
		try {
		getFragmentManager().beginTransaction().remove(settingsFragment).commit();
		} catch (InflateException e) {
			Toast.makeText(getActivity(), "Problem on Destroy View !",
					Toast.LENGTH_LONG).show();
		}
	}
}
