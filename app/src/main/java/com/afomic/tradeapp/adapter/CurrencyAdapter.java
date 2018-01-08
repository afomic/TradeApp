package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.model.Currency;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by afomic on 1/7/18.
 *
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>{
    private Context mContext;
    private ArrayList<Currency> mCurrencies;
    private HashMap<Integer,Currency> mSelectedCurrencies;
    private int selectedNumber=0;
    public CurrencyAdapter(Context context,ArrayList<Currency> currencies,HashMap<Integer,Currency> selectedCurrencies){
        mContext=context;
        mCurrencies=currencies;
        mSelectedCurrencies=selectedCurrencies;
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
        if(selectedNumber==4){//after four currency has been selected
            if(!currency.isSelected()){// if currency is not selected then disable it
                holder.currencyCheckbox.setEnabled(false);
            }
        }

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
    public class CheckBoxListener implements CompoundButton.OnCheckedChangeListener{
        private int position;
        public CheckBoxListener(int position){
            this.position=position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Currency selectedCurrency=mCurrencies.get(position);
            if(isChecked){
               selectedCurrency.setSelected(true);
               selectedNumber++;
               mSelectedCurrencies.put(position,selectedCurrency);
            }else {
                selectedCurrency.setSelected(false);
                mSelectedCurrencies.remove(position);
                selectedNumber--;
            }
            if(selectedNumber==4){// 4 currency has been selected re draw the whole list
                notifyDataSetChanged();
            }
        }
    }
    public boolean isValidSelection(){
        return mSelectedCurrencies.size()==0;
    }
}
