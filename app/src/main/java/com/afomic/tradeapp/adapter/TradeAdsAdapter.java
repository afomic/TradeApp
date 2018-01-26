package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.model.Currency;
import com.afomic.tradeapp.model.TradeAds;

import java.util.List;

/**
 * Created by afomic on 1/7/18.
 */

public class TradeAdsAdapter extends RecyclerView.Adapter<TradeAdsAdapter.TradeAdsHolder>{
    private List<TradeAds> mTradeAds;
    private Context mContext;
    private TradeAdsListener mTradeAdsListener;
    public TradeAdsAdapter(Context context,List<TradeAds> tradeAds){
        mContext=context;
        mTradeAds=tradeAds;
        mTradeAdsListener=(TradeAdsListener) context;
    }

    @Override
    public TradeAdsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_trade_ad,parent,false);
        return new TradeAdsHolder(v);
    }

    @Override
    public void onBindViewHolder(TradeAdsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTradeAds.size();
    }

    public class TradeAdsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TradeAdsHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mTradeAdsListener.onClick();
        }
    }
    public  interface  TradeAdsListener{
        void onClick();
    }
}
