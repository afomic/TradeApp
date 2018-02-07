package com.afomic.tradeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Chat;
import com.afomic.tradeapp.model.TradeAd;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TradeAdsDetailsActivity extends AppCompatActivity{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_last_seen)
    TextView lastSeenTextView;
    @BindView(R.id.tv_username)
    TextView usernameTextView;
    @BindView(R.id.tv_takes)
    TextView takesTextView;
    @BindView(R.id.tv_offer)
    TextView offersTextView;

    private PreferenceManager mPreferenceManager;
    private TradeAd currentTradeAd;

    private DatabaseReference chatRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_ads_details);
        ButterKnife.bind(this);

        currentTradeAd=getIntent().getParcelableExtra(Constants.EXTRA_TRADE_AD);

        chatRef= FirebaseDatabase.getInstance()
                .getReference(Constants.CHATS_REF);

        setSupportActionBar(mToolbar);
        mPreferenceManager=new PreferenceManager(this);
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle("Ad details");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        usernameTextView.setText(currentTradeAd.getUsername());
        takesTextView.setText(currentTradeAd.getCurrencyToBuy());
        offersTextView.setText(currentTradeAd.getCurrencyToSell());
    }
    @OnClick(R.id.btn_chat)
    public void onChatUser(){
        final Chat chat=new Chat();
        String chatId=mPreferenceManager.getUserId()+currentTradeAd.getUserId();
        chat.setId(chatId);
        chat.setUserOne(mPreferenceManager.getUsername());
        chat.setUserTwo(currentTradeAd.getUsername());
        chat.setColor(ColorGenerator.MATERIAL.getRandomColor());
        chat.setLastUpdate(System.currentTimeMillis());
        DatabaseReference myChatRef=chatRef.child(mPreferenceManager.getUsername());
        DatabaseReference recipientChatRef=chatRef.child(currentTradeAd.getUsername());
        recipientChatRef.child(chatId)
                .setValue(chat);
        myChatRef.child(chatId)
                .setValue(chat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
                        intent.putExtra(Constants.EXTRA_CHAT,chat);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("tag", "onFailure: ", e);
            }
        });

    }
    @OnClick(R.id.btn_locate)
    public void checkoutOnMap(){
        Intent intent= new Intent(getApplicationContext(),MapActivity.class);
        intent.putExtra(Constants.EXTRA_TRADE_LATITUDE,currentTradeAd.getLocationLatitude());
        intent.putExtra(Constants.EXTRA_TRADE_LONGITUDE,currentTradeAd.getLocationLongitude());
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
