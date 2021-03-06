package com.afomic.tradeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.fragment.AddAccountDialog;
import com.afomic.tradeapp.fragment.ChatListFragment;
import com.afomic.tradeapp.fragment.HomeFragment;
import com.afomic.tradeapp.fragment.MyAdsFragment;
import com.afomic.tradeapp.fragment.SettingsFragment;
import com.afomic.tradeapp.fragment.UserDetailsFragment;
import com.afomic.tradeapp.model.User;
import com.afomic.tradeapp.services.FirebaseChatListener;
import com.afomic.tradeapp.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener,
        AddAccountDialog.AddAccountListener{

    DrawerLayout mDrawer;
    FragmentManager fm;
    NavigationView mNavigationView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    ActionBar actionBar;

    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference userDatabaseRef;
    PreferenceManager mPreferenceManager;
    private boolean darkTheme=false;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();
        mFirebaseUser=mAuth.getCurrentUser();
        if(mFirebaseUser==null){
            loginUser();
        }
        userDatabaseRef= FirebaseDatabase.getInstance().getReference(Constants.USERS_REF);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.key_dark_theme))) {
            recreate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mPreferenceManager=new PreferenceManager(this);
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
                            case R.id.menu_my_settings:
                                SettingsFragment settingsFragment=new SettingsFragment();
                                displayFragment(settingsFragment);
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
    public void loginUser(){
        mAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=task.getResult().getUser();
                            User currentUser=new User();
                            String userId=user.getUid();
                            currentUser.setUserId(userId);
                            mPreferenceManager.setUserId(userId);
                            currentUser.setMemberSince(System.currentTimeMillis());
                            userDatabaseRef.child(userId)
                                    .setValue(currentUser);
                        }


                    }
                });

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

    @Override
    public void onAddAccount(String username) {
        Intent serviceIntent=new Intent(getApplicationContext(),FirebaseChatListener.class);
        startService(serviceIntent);
        Intent intent=new Intent(MainActivity.this, CreateTradeAdActivity.class);
        startActivity(intent);
    }
}
