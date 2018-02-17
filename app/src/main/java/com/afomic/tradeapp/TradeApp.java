package com.afomic.tradeapp;

import android.app.Application;
import android.content.Intent;

import com.afomic.tradeapp.services.FirebaseChatListener;
import com.google.firebase.database.FirebaseDatabase;

/**
 *
 * Created by afomic on 2/3/18.
 */

public class TradeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Intent intent=new Intent(getApplicationContext(),FirebaseChatListener.class);
        startService(intent);
    }
}
