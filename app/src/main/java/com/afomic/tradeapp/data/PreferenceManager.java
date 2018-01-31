package com.afomic.tradeapp.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by afomic on 1/31/18.
 */

public class PreferenceManager {
    private SharedPreferences mSharedPreferences;

    private static final String PREFERENCE_FILE_NAME="com.afomic.tradeapp";
    private static final String PREF_USER_LOCATION="location";



    public PreferenceManager(Context context){
        mSharedPreferences=context.getSharedPreferences(PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
    }
    public void setUserLocation(String location){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PREF_USER_LOCATION,location);
        mEditor.apply();
    }

    public String getUserLocation(){
        return mSharedPreferences.getString(PREF_USER_LOCATION,"not found");
    }

}
