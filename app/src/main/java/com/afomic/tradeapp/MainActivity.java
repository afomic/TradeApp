package com.afomic.tradeapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.afomic.tradeapp.adapter.TradeAdsAdapter;
import com.afomic.tradeapp.fragment.ChatListFragment;
import com.afomic.tradeapp.fragment.HomeFragment;
import com.afomic.tradeapp.fragment.UserDetailsFragment;
import com.afomic.tradeapp.model.Currency;
import com.afomic.tradeapp.model.TradeAds;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TradeAdsAdapter.TradeAdsListener {

    DrawerLayout mDrawer;
    FragmentManager fm;
    NavigationView mNavigationView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab_add_trade)
    FloatingActionButton fab;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
//        mPreferenceManager=new PreferenceManager(this);
         actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle("Home");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze);
        }
        fm=getSupportFragmentManager();

        Fragment fragment=fm.findFragmentById(R.id.main_container);
        if(fragment==null){
            HomeFragment homeFragment=HomeFragment.newInstance();
            fm.beginTransaction().add(R.id.main_container,homeFragment)
                    .commit();
        }

        mNavigationView= findViewById(R.id.navigation_view);
        mDrawer=findViewById(R.id.drawer_layout);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawer.closeDrawers();
                        switch (menuItem.getItemId()){
                            case R.id.menu_ad_list:
                                HomeFragment homeFragment=HomeFragment.newInstance();
                                displayFragment(homeFragment,0);
                                actionBar.setTitle("Trade Ads");

                                break;
                            case R.id.menu_chats:
                                ChatListFragment chatListFragment=ChatListFragment.newInstance();
                                displayFragment(chatListFragment,1);
                                actionBar.setTitle("My Chats");
                                break;

                            case R.id.menu_my_info:
                                UserDetailsFragment userDetailsFragment=UserDetailsFragment.newInstance();
                                displayFragment(userDetailsFragment,2);
                                actionBar.setTitle("My Details");
                                break;


                        }
                        return true;
                    }
                });


    }
    @OnClick(R.id.fab_add_trade)
    public void showAddTradeUi(){
        Intent intent =new Intent(MainActivity.this,CreateTradeAdActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick() {
        Intent intent=new Intent(getApplicationContext(),TradeAdsDetailsActivity.class);
        startActivity(intent);
    }
    public void displayFragment(Fragment frag,int position){
        mDrawer.closeDrawers();
        switch (position){
            case 0:
                fab.setImageResource(R.drawable.ic_add);
                break;
            case 1:
                fab.setImageResource(R.drawable.ic_add);
                break;
            case 2:
                fab.setImageResource(R.drawable.ic_edit);
                break;

        }
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
