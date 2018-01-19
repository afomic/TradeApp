package com.afomic.tradeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.afomic.tradeapp.adapter.TradeAdsAdapter;
import com.afomic.tradeapp.model.Currency;
import com.afomic.tradeapp.model.TradeAds;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TradeAdsAdapter.TradeAdsListener {
    @BindView(R.id.rv_trade_ads)
    RecyclerView tradeAdsRecyclerView;
    TradeAdsAdapter mTradeAdsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tradeAdsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTradeAdsAdapter=new TradeAdsAdapter(MainActivity.this,getDummyData());
        tradeAdsRecyclerView.setAdapter(mTradeAdsAdapter);

    }
    @OnClick(R.id.fab_add_trade)
    public void showAddTradeUi(){
        Intent intent =new Intent(MainActivity.this,CreateTradeAdActivity.class);
        startActivity(intent);
    }
    public ArrayList<TradeAds> getDummyData(){
        ArrayList<TradeAds> dummyData=new ArrayList<>();
        for(int i=0;i<5;i++){
            TradeAds ads=new TradeAds();
            ads.setCurrencyToSell(getDummyCurrencyData());
            ads.setCurrencyToBuy(getDummyCurrencyData());
            dummyData.add(ads);
        }
        return dummyData;
    }
    public ArrayList<Currency> getDummyCurrencyData(){
        int length=new Random().nextInt(4)+1;
        ArrayList<Currency> currencies=new ArrayList<>();
        for(int i=0;i<length;i++){
            currencies.add(new Currency("BTC"+i));
        }
        return currencies;
    }

    @Override
    public void onClick() {
        Intent intent=new Intent(MainActivity.this,ChatActivity.class);
        startActivity(intent);
    }
}
