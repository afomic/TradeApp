package com.afomic.tradeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_trade_ads)
    RecyclerView tradeAdsRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tradeAdsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    @OnClick(R.id.fab_add_trade)
    public void showAddTradeUi(){
        Intent intent =new Intent(MainActivity.this,CreateTradeAdActivity.class);
        startActivity(intent);
    }
}
