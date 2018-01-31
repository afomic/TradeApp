package com.afomic.tradeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.afomic.tradeapp.adapter.TradeAdsAdapter;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.fragment.ChatListFragment;
import com.afomic.tradeapp.fragment.HomeFragment;
import com.afomic.tradeapp.fragment.MyAdsFragment;
import com.afomic.tradeapp.fragment.UserDetailsFragment;
import com.afomic.tradeapp.model.Currency;
import com.afomic.tradeapp.model.TradeAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawer;
    FragmentManager fm;
    NavigationView mNavigationView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
//        mPreferenceManager=new PreferenceManager(this);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Home");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze);
        }
        fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.main_container);
        if (fragment == null) {
            HomeFragment homeFragment = HomeFragment.newInstance();
            fm.beginTransaction().add(R.id.main_container, homeFragment)
                    .commit();
        }


        mNavigationView = findViewById(R.id.navigation_view);
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawer.closeDrawers();
                        menuItem.setCheckable(true);
                        switch (menuItem.getItemId()) {
                            case R.id.menu_ad_list:
                                HomeFragment homeFragment = HomeFragment.newInstance();
                                displayFragment(homeFragment);
                                actionBar.setTitle("Find Traders");


                                break;
                            case R.id.menu_my_ads:
                                MyAdsFragment myFragment = MyAdsFragment.newInstance();
                                displayFragment(myFragment);
                                actionBar.setTitle("My Ads");

                                break;
                            case R.id.menu_chats:
                                ChatListFragment chatListFragment = ChatListFragment.newInstance();
                                displayFragment(chatListFragment);
                                actionBar.setTitle("My Chats");
                                break;

                            case R.id.menu_my_info:
                                UserDetailsFragment userDetailsFragment = UserDetailsFragment.newInstance();
                                displayFragment(userDetailsFragment);
                                actionBar.setTitle("My Details");
                                break;


                        }
                        return true;
                    }
                });
    }
    public void displayFragment(Fragment frag){
        mDrawer.closeDrawers();
        fm.beginTransaction().replace(R.id.main_container,frag).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(Gravity.START,true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
