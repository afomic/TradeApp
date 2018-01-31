package com.afomic.tradeapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.MenuItem;

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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


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





    }
    @OnClick(R.id.btn_submit)
    public void submitTradeAd(){
       finish();

    }
    @OnClick(R.id.btn_edit)
    public void editCurrentLocation(){
        Intent intent=new Intent(getApplicationContext(), SelectLocationActivity.class);
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
