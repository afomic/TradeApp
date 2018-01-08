package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.model.Currency;

import java.util.ArrayList;

/**
 * Created by afomic on 1/7/18.
 *
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>{
    private Context mContext;
    private ArrayList<Currency> mCurrencies;
    private int selectedNumber=0;
    public CurrencyAdapter(Context context,ArrayList<Currency> currencies){
        mContext=context;
        mCurrencies=currencies;
    }
    @Override
    public CurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_currency,parent,false);
        return new CurrencyHolder(v);
    }

    @Override
    public void onBindViewHolder(CurrencyHolder holder, int position) {
        Currency currency=mCurrencies.get(position);
        holder.currencyCheckbox.setText(currency.getName());
        holder.currencyCheckbox.setSelected(currency.isSelected());

    }

    @Override
    public int getItemCount() {
        if(mCurrencies!=null){
            return mCurrencies.size();
        }
        return 0;
    }


    public class CurrencyHolder extends RecyclerView.ViewHolder{
        CheckBox currencyCheckbox;
        public CurrencyHolder(View itemView) {
            super(itemView);
            currencyCheckbox=itemView.findViewById(R.id.cb_currency);
        }

    }
}
