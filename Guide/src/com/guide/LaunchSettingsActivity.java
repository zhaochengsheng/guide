package com.guide;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;;



public class LaunchSettingsActivity extends PreferenceFragment {

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.layout.activity_launch_settings);
	    }

}

