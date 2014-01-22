package com.guide;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.preference.PreferenceFragment;



public class LaunchSettingsActivity extends PreferenceFragment {

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.layout.activity_launch_settings);
	        
	        PreferenceManager.setDefaultValues(getActivity(),R.layout.activity_launch_settings , false);
	        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
	        //boolean category_restaurant = sharedPreferences.getBoolean("pref_category_restaurant", true);
	    }

}

