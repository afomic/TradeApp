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
    private static final String PREF_USER_LOCATION_LATITUDE="latitude";
    private static final String PREF_USER_LOCATION_LONGITUDE="longitude";
    private static final String PREF_USER_ID="user_id";
    private static final String PREF_USER_NAME="user_name";



    public PreferenceManager(Context context){
        mSharedPreferences=context.getSharedPreferences(PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
    }
    public void setUserLocation(String location){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PREF_USER_LOCATION,location);
        mEditor.apply();
    }
    public void setUserLatitude(float latitude){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putFloat(PREF_USER_LOCATION_LATITUDE,latitude);
        mEditor.apply();
    }
    public void setUserLongitude(float longitude){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putFloat(PREF_USER_LOCATION_LONGITUDE,longitude);
        mEditor.apply();
    }
    public void setUserId(String userId){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PREF_USER_ID,userId);
        mEditor.apply();
    }
    public void setUsername(String username){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PREF_USER_NAME,username);
        mEditor.apply();
    }
    public float getUserLongitude(){
        return mSharedPreferences.getFloat(PREF_USER_LOCATION_LONGITUDE,0);
    }
    public float getUserLatitude(){
        return mSharedPreferences.getFloat(PREF_USER_LOCATION_LATITUDE,0);
    }
    public String getUserLocation(){
        return mSharedPreferences.getString(PREF_USER_LOCATION,"not found");
    }
    public String getUserId(){
        return mSharedPreferences.getString(PREF_USER_ID,"");
    }
    public String getUsername(){
        return mSharedPreferences.getString(PREF_USER_NAME,"");
    }

}
