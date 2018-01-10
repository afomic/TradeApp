package com.afomic.tradeapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.SparseArray;

import com.afomic.tradeapp.adapter.CurrencyAdapter;
import com.afomic.tradeapp.model.Currency;
import com.afomic.tradeapp.model.TradeAds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTradeAdActivity extends AppCompatActivity {
    @BindView(R.id.rv_currency_sell)
    RecyclerView sellCurrencyList;
    @BindView(R.id.rv_currency_buy)
    RecyclerView buyCurrencyList;

    CurrencyAdapter buyCurrencyAdapter;
    CurrencyAdapter sellCurrencyAdapter;

    Map<Integer,Currency> selectedBuyCurrency,selectedSellCurrency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trade_ad);
        ButterKnife.bind(this);

        int columnNumber=getColumnNo(CreateTradeAdActivity.this);

        selectedBuyCurrency=new HashMap<>();
        selectedSellCurrency=new HashMap<>();

        buyCurrencyList.setLayoutManager(new GridLayoutManager(this,columnNumber));
        sellCurrencyList.setLayoutManager(new GridLayoutManager(this,columnNumber));

        buyCurrencyAdapter=new CurrencyAdapter(this,getDummyData(),selectedBuyCurrency);

        buyCurrencyList.setAdapter(buyCurrencyAdapter);

        sellCurrencyAdapter=new CurrencyAdapter(this,getDummyData(),selectedSellCurrency);

        sellCurrencyList.setAdapter(sellCurrencyAdapter);




    }
    @OnClick(R.id.btn_submit)
    public void submitTradeAd(){
        TradeAds tradeAds=new TradeAds();
        List<Currency> buyCurrencyList=new ArrayList<>(selectedBuyCurrency.values());
        List<Currency> sellCurrencyList=new ArrayList<>(selectedSellCurrency.values());
        tradeAds.setCurrencyToBuy(buyCurrencyList);
        tradeAds.setCurrencyToSell(sellCurrencyList);
        tradeAds.save();

    }
    public static int getColumnNo(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 80;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

    public ArrayList<Currency> getDummyData(){
        ArrayList<Currency> currencies=new ArrayList<>();
        for(int i=0;i<10;i++){
            currencies.add(new Currency("BTC"+i));
        }
        return currencies;
    }
}
