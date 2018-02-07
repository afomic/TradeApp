package com.afomic.tradeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.TradeAd;
import com.afomic.tradeapp.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTradeAdActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_user_location)
    TextView userLocation;
    @BindView(R.id.ch_offer_btc)
    CheckBox btcOfferCheckBox;
    @BindView(R.id.ch_offer_dcr)
    CheckBox drcOfferCheckBox;
    @BindView(R.id.ch_offer_cash)
    CheckBox cashOfferCheckBox;
    @BindView(R.id.ch_receive_btc)
    CheckBox btcTakingCheckBox;
    @BindView(R.id.ch_receive_dcr)
    CheckBox dcrTakingCheckBox;
    @BindView(R.id.ch_receive_cash)
    CheckBox cashTakingCheckBox;

    private static final int TYPE_OFFER=0;
    private static final int TYPE_TAKING=1;
    private static String offerCurrencyString="";
    private static String takingCurrencyString="";
    private PreferenceManager mPreferenceManager;
    private DatabaseReference tradeAdsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trade_ad);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle("Create Ad");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tradeAdsRef= FirebaseDatabase.getInstance().getReference(Constants.TRADE_ADS_REF);
        mPreferenceManager=new PreferenceManager(this);
        btcOfferCheckBox.setOnCheckedChangeListener(new CheckboxListener("BTC",TYPE_OFFER));
        drcOfferCheckBox.setOnCheckedChangeListener(new CheckboxListener("DCR",TYPE_OFFER));
        cashOfferCheckBox.setOnCheckedChangeListener(new CheckboxListener("cash",TYPE_OFFER));
        btcTakingCheckBox.setOnCheckedChangeListener(new CheckboxListener("BTC",TYPE_TAKING));
        dcrTakingCheckBox.setOnCheckedChangeListener(new CheckboxListener("DCR",TYPE_TAKING));
        cashTakingCheckBox.setOnCheckedChangeListener(new CheckboxListener("Cash",TYPE_TAKING));

        userLocation.setText(mPreferenceManager.getUserLocation());


    }
    @OnClick(R.id.btn_submit)
    public void submitTradeAd(){
        if(isValidEntry()){
            String tradeAdId=tradeAdsRef.push().getKey();
            TradeAd ads=new TradeAd();
            ads.setCurrencyToBuy(trimSelection(takingCurrencyString));
            ads.setCurrencyToSell(trimSelection(offerCurrencyString));
            ads.setLocationLatitude(mPreferenceManager.getUserLatitude());
            ads.setLocationLongitude(mPreferenceManager.getUserLongitude());
            ads.setUsername(mPreferenceManager.getUsername());
            ads.setId(tradeAdId);
            ads.setUserId(mPreferenceManager.getUserId());
            tradeAdsRef.child(tradeAdId)
                    .setValue(ads)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Util.makeToast(CreateTradeAdActivity.this,"Creating Post Failed");
                    Log.e("tag", "onFailure: ",e );
                }
            });
        }
    }
    @OnClick(R.id.btn_edit)
    public void editCurrentLocation(){
        Intent intent=new Intent(getApplicationContext(), SelectLocationActivity.class);
        startActivity(intent);
    }
    public boolean isValidEntry(){
        if(offerCurrencyString==null||offerCurrencyString.equals("")){
            Util.makeToast(CreateTradeAdActivity.this,
                    "You Must select at least one Offer Currency");
            return false;
        }
        if(takingCurrencyString==null||takingCurrencyString.equals("")){
            Util.makeToast(CreateTradeAdActivity.this,
                    "You Must select at least one Take Currency");
            return false;
        }
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public static class CheckboxListener implements CompoundButton.OnCheckedChangeListener {
        private String name;
        private int type;
        public CheckboxListener(String name, int type){
            this.name=name;
            this.type=type;
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String nameWithComma=name+", ";
            if(isChecked){
                if(type==TYPE_OFFER){
                  offerCurrencyString+=nameWithComma;
                }else {
                    takingCurrencyString+=nameWithComma;
                }
            }else {
                if(type==TYPE_OFFER){
                    offerCurrencyString=removeString(offerCurrencyString,nameWithComma);
                }else {
                    takingCurrencyString = removeString(takingCurrencyString,nameWithComma);
                }

            }
        }
    }
    public static String removeString(String container,String text){
        int start=container.indexOf( text);
        int end=start+text.length();
        StringBuilder builder=new StringBuilder(container);
        builder.delete(start,end);
        return builder.toString();
    }
    public String trimSelection(String selection){
        int lastCommaPosition=selection.lastIndexOf(",");
        StringBuilder builder=new StringBuilder(selection);
        builder.deleteCharAt(lastCommaPosition);
        return builder.toString().trim();
    }
}
