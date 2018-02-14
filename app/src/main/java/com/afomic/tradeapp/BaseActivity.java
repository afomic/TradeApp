package com.afomic.tradeapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by afomic on 2/14/18.
 *
 */

public abstract class BaseActivity extends AppCompatActivity {
    boolean darkTheme;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPref = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        darkTheme = sharedPref.getBoolean(getString(R.string.key_dark_theme),false);
        setTheme(darkTheme?R.style.darkTheme:R.style.lightTheme);
        super.onCreate(savedInstanceState);
    }

}
