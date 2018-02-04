package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.TradeAd;

import java.util.List;

/**
 * Created by afomic on 1/7/18.
 *
 */

public class TradeAdsAdapter extends RecyclerView.Adapter<TradeAdsAdapter.TradeAdsHolder>{
    private List<TradeAd> mTradeAds;
    private Context mContext;
    private TradeAdsListener mTradeAdsListener;
    private PreferenceManager mPreferenceManager;
    public TradeAdsAdapter(Context context, List<TradeAd> tradeAds, TradeAdsListener listener){
        mContext=context;
        mTradeAds=tradeAds;
        mTradeAdsListener=listener;
        mPreferenceManager=new PreferenceManager(mContext);
    }

    @Override
    public TradeAdsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_trade_ad,parent,false);
        return new TradeAdsHolder(v);
    }

    @Override
    public void onBindViewHolder(TradeAdsHolder holder, int position) {
        TradeAd ad=mTradeAds.get(position);
        holder.usernameTextView.setText(ad.getUsername());
        holder.offerTextView.setText(ad.getCurrencyToSell());
        holder.takingTextView.setText(ad.getCurrencyToBuy());
        float distanceBtwTrade=getLocationDifference(ad);
        holder.distanceTextView.setText(String.valueOf(distanceBtwTrade));
    }

    @Override
    public int getItemCount() {
        return mTradeAds.size();
    }

    public class TradeAdsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView usernameTextView,distanceTextView,offerTextView,takingTextView,
        lastSeenTextView;
        public TradeAdsHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            usernameTextView=itemView.findViewById(R.id.tv_username);
            distanceTextView=itemView.findViewById(R.id.tv_distance);
            takingTextView=itemView.findViewById(R.id.tv_taking);
            offerTextView=itemView.findViewById(R.id.tv_offer);
            lastSeenTextView=itemView.findViewById(R.id.tv_last_seen);
        }

        @Override
        public void onClick(View v) {
            mTradeAdsListener.onClick();
        }
    }
    public  interface  TradeAdsListener{
        void onClick();
    }

    public float getLocationDifference(TradeAd ads){
        Location loc1 = new Location("");
        loc1.setLatitude(mPreferenceManager.getUserLatitude());
        loc1.setLongitude(mPreferenceManager.getUserLongitude());
        Location loc2 = new Location("");
        loc2.setLatitude(ads.getLocationLatitude());
        loc2.setLongitude(ads.getLocationLatitude());
        return loc1.distanceTo(loc2);
    }
}
