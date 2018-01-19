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
        TradeAds ads=mTradeAds.get(position);
        //load the date for the sell side
        List<Currency> sellCurrency=ads.getCurrencyToSell();
        List<Currency> buyCurrency=ads.getCurrencyToBuy();
        int sellCurrencyLength=sellCurrency.size();
        int buyCurrencyLength=buyCurrency.size();
        //each trade ad will have a minimum of one buy currency and sell currency
        holder.currencyOneBuyTextView.setText(buyCurrency.get(0).getName());
        holder.currencyOneSellTextView.setText(sellCurrency.get(0).getName());
        if(sellCurrencyLength>1){//there is more than 1 sell currency
            holder.currencyTwoSellTextView.setText(sellCurrency.get(1).getName());
        }else {
            holder.currencyTwoSellTextView.setVisibility(View.GONE);
        }
        if(sellCurrencyLength>2){//there is more than 2 sell currency
            holder.currencyThreeSellTextView.setText(sellCurrency.get(2).getName());
        }else {
            holder.currencyThreeSellTextView.setVisibility(View.GONE);
        }
        if(sellCurrencyLength>3){//there is more than 1 sell currency
            holder.currencyFourSellTextView.setText(sellCurrency.get(3).getName());
        }else {
            holder.currencyFourSellTextView.setVisibility(View.GONE);
        }
        //for the buy currency
        if(buyCurrencyLength>1){//there is more than 1 buy currency
            holder.currencyTwoBuyTextView.setText(buyCurrency.get(1).getName());
        }else {
            holder.currencyTwoBuyTextView.setVisibility(View.GONE);
        }
        if(buyCurrencyLength>2){//there is more than 1 buy currency
            holder.currencyThreeBuyTextView.setText(buyCurrency.get(2).getName());
        }else {
            holder.currencyThreeBuyTextView.setVisibility(View.GONE);
        }
        if(buyCurrencyLength>3){//there is more than 1 sell currency
            holder.currencyFourBuyTextView.setText(buyCurrency.get(3).getName());
        }else {
            holder.currencyFourBuyTextView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mTradeAds.size();
    }

    public class TradeAdsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView currencyOneBuyTextView, currencyTwoBuyTextView,  currencyTwoSellTextView,
                currencyThreeBuyTextView, currencyFourBuyTextView, currencyOneSellTextView,
        currencyThreeSellTextView, currencyFourSellTextView;
        public TradeAdsHolder(View itemView) {
            super(itemView);
            currencyOneBuyTextView=itemView.findViewById(R.id.tv_buy_1);
            currencyTwoBuyTextView=itemView.findViewById(R.id.tv_buy_2);
            currencyThreeBuyTextView=itemView.findViewById(R.id.tv_buy_3);
            currencyFourBuyTextView=itemView.findViewById(R.id.tv_buy_4);
            currencyOneSellTextView=itemView.findViewById(R.id.tv_sell_1);
            currencyTwoSellTextView=itemView.findViewById(R.id.tv_sell_2);
            currencyThreeSellTextView=itemView.findViewById(R.id.tv_sell_3);
            currencyFourSellTextView=itemView.findViewById(R.id.tv_sell_4);
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
