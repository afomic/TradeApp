package com.afomic.tradeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.tradeapp.CreateTradeAdActivity;
import com.afomic.tradeapp.R;
import com.afomic.tradeapp.adapter.TradeAdsAdapter;
import com.afomic.tradeapp.model.Currency;
import com.afomic.tradeapp.model.TradeAd;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by afomic on 1/30/18.
 */

public class MyAdsFragment extends Fragment {
    @BindView(R.id.rv_trade_ads)
    RecyclerView tradeAdsRecyclerView;

    private Unbinder mUnbinder;


    private TradeAdsAdapter.TradeAdsListener mTradeAdsListener;
    private TradeAdsAdapter mTradeAdsAdapter;
    private ArrayList<TradeAd> mTradeAds;



    public static MyAdsFragment newInstance(){
        return new MyAdsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mTradeAds=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_my_ads,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        tradeAdsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTradeAdsListener= new TradeAdsAdapter.TradeAdsListener() {
            @Override
            public void onClick() {
                Intent intent=new Intent(getActivity(),CreateTradeAdActivity.class);
                startActivity(intent);
            }
        };
        mTradeAdsAdapter=new TradeAdsAdapter(getActivity(),mTradeAds,mTradeAdsListener);
        tradeAdsRecyclerView.setAdapter(mTradeAdsAdapter);
        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.trade_ad_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_create_ad){
            Intent intent=new Intent(getActivity(), CreateTradeAdActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
