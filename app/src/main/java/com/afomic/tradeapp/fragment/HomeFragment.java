package com.afomic.tradeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.tradeapp.MainActivity;
import com.afomic.tradeapp.R;
import com.afomic.tradeapp.adapter.TradeAdsAdapter;
import com.afomic.tradeapp.model.Currency;
import com.afomic.tradeapp.model.TradeAds;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by afomic on 1/23/18.
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.rv_trade_ads)
    RecyclerView tradeAdsRecyclerView;
    TradeAdsAdapter mTradeAdsAdapter;
    private Unbinder mUnbinder;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        tradeAdsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTradeAdsAdapter=new TradeAdsAdapter(getActivity(),getDummyData());
        tradeAdsRecyclerView.setAdapter(mTradeAdsAdapter);
        return v;
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
}
