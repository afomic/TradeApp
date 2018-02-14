package com.afomic.tradeapp;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.afomic.tradeapp.data.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle data=getIntent().getExtras();
        if(data!=null){
            longitude=data.getDouble(Constants.EXTRA_TRADE_LONGITUDE);
            latitude=data.getDouble(Constants.EXTRA_TRADE_LATITUDE);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        LatLng tradeLocation = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(tradeLocation).title("Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tradeLocation));
    }
}
