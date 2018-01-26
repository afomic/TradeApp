package com.afomic.tradeapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TradeAdsDetailsActivity extends AppCompatActivity{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_ads_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
//        mPreferenceManager=new PreferenceManager(this);
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle("Ad details");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }
    @OnClick(R.id.btn_chat)
    public void onChatUser(){
        Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_locate)
    public void checkoutOnMap(){
        Intent intent= new Intent(getApplicationContext(),MapActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
