package com.gogbuehi.android.apps;

import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SimpleWorldClockActivity extends Activity {
	static final String PREFS_FILE="com.gogbuehi.android.SimpleWorldClock";
	static final String PREFS_CITIES="com.gogbuehi.android.SimpleWorldClock.cities";
	static final String[] MAJOR_CITIES = new String[] {
		"San Francisco, CA, USA",
		"Washington, DC, USA",
		"London, England, UK",
		"Addis Ababa, Ethiopia"
	};
	ListView mListView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if (isFirstRun()) {
        	//Put default cities into SharedPrefs
        	putDefaultCities();
        }
        
        mListView = (ListView)findViewById(R.id.listView1);
        mListView.setAdapter(new ArrayAdapter<String>(this,R.layout.major_cities,retrieveStoredValues()));
    }
    
    private boolean isFirstRun() {
    	SharedPreferences settings = getSharedPreferences(PREFS_FILE,0);
    	Map<String,String> allSettings = (Map<String,String>)settings.getAll();
    	
    	if (allSettings.containsKey("started")) {
    		return false;
    	} else {
    		SharedPreferences.Editor allSettingsEditor = settings.edit();
    		PackageInfo packageInfo;
			try {
				packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
	    		//int versionNumber = packageInfo.versionCode;
	    		String versionName = packageInfo.versionName;
	    		allSettingsEditor.putString("started", "v"+versionName);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return true;
    }
    
    private void putDefaultCities() {
    	SharedPreferences settings = getSharedPreferences(PREFS_CITIES,0);
    	SharedPreferences.Editor settingsEditor = settings.edit();
    	for (int i = 0; i < MAJOR_CITIES.length; i++) {
    		settingsEditor.putString("city_"+i,MAJOR_CITIES[i]);
    	}
    	settingsEditor.commit();
    }
    
    private String[] retrieveStoredValues() {
    	// Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_CITIES, 0);
        Map<String,String> allCities = (Map<String,String>)settings.getAll();
        int citiesCount = allCities.size();
        String[] storedCities = new String[citiesCount];
        for (int i = 0; i < allCities.size(); i++) {
        	storedCities[i] = allCities.get("city_"+i);
        }
        return storedCities;
    }
    
    
}