package com.afomic.tradeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
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
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Currency;
import com.afomic.tradeapp.model.TradeAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by afomic on 1/30/18.
 *
 */

public class MyAdsFragment extends Fragment {
    @BindView(R.id.rv_trade_ads)
    RecyclerView tradeAdsRecyclerView;

    private Unbinder mUnbinder;


    private TradeAdsAdapter.TradeAdsListener mTradeAdsListener;
    private TradeAdsAdapter mTradeAdsAdapter;
    private ArrayList<TradeAd> mTradeAds;
    private Query myTradeAdRef;
    private PreferenceManager mPreferenceManager;



    public static MyAdsFragment newInstance(){
        return new MyAdsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPreferenceManager=new PreferenceManager(getActivity());
        mTradeAds=new ArrayList<>();
        myTradeAdRef= FirebaseDatabase
                .getInstance()
                .getReference(Constants.TRADE_ADS_REF)
                .orderByChild("userId")
                .equalTo(mPreferenceManager.getUserId());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_my_ads,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        tradeAdsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTradeAdsListener= new TradeAdsAdapter.TradeAdsListener() {
            @Override
            public void onClick(int position) {
                TradeAd tradeAd=mTradeAds.get(position);
                Intent intent=new Intent(getActivity(),CreateTradeAdActivity.class);
                intent.putExtra(Constants.EXTRA_TRADE_AD,tradeAd);
                startActivity(intent);
            }
        };
        DividerItemDecoration divider = new DividerItemDecoration(
                getActivity(),
                DividerItemDecoration.VERTICAL
        );
        tradeAdsRecyclerView.addItemDecoration(divider);
        mTradeAdsAdapter=new TradeAdsAdapter(getActivity(),mTradeAds,mTradeAdsListener);
        tradeAdsRecyclerView.setAdapter(mTradeAdsAdapter);
        myTradeAdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTradeAds.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    TradeAd myAd=snapshot.getValue(TradeAd.class);
                    mTradeAds.add(myAd);
                }
                mTradeAdsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            if(mPreferenceManager.isUserLoggedIn()){
                Intent intent=new Intent(getActivity(), CreateTradeAdActivity.class);
                startActivity(intent);
            }else {
                AddAccountDialog dialog=AddAccountDialog.newInstance();
                dialog.show(getFragmentManager(),null);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
