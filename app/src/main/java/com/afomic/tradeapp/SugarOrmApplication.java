package com.afomic.tradeapp;

import android.content.res.Configuration;

import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by afomic on 1/10/18.
 */

public class SugarOrmApplication extends SugarApp {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
