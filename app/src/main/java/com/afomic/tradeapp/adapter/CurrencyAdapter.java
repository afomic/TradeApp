package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.model.Currency;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by afomic on 1/7/18.
 *
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>{
    private Context mContext;
    private ArrayList<Currency> mCurrencies;
    private Map<Integer,Currency> mSelectedCurrencies;
    private int totalSelectedCurrency=0;
    public CurrencyAdapter(Context context,ArrayList<Currency> currencies,Map<Integer,Currency> selectedCurrencies){
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
        holder.currencyCheckbox.setTag(position);
        holder.currencyCheckbox.setChecked(currency.isSelected());
        if(totalSelectedCurrency==4){//after four currency has been selected
            Toast.makeText(mContext,"You have reached the Maximum Selection",
                    Toast.LENGTH_SHORT).show();
            holder.currencyCheckbox.setEnabled(currency.isSelected());
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
            currencyCheckbox.setOnCheckedChangeListener(new CheckBoxListener());

        }


    }
    public class CheckBoxListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position=(int) buttonView.getTag();
            Currency selectedCurrency=mCurrencies.get(position);
            if(isChecked){
               selectedCurrency.setSelected(true);
               totalSelectedCurrency++;
               mSelectedCurrencies.put(position,selectedCurrency);
            }else {
                selectedCurrency.setSelected(false);
                mSelectedCurrencies.remove(position);
                totalSelectedCurrency--;
            }
            // redraw the list each time an item state change
            Log.e("tag", "onCheckedChanged: "+position );
        }
    }
    public boolean isValidSelection(){
        return mSelectedCurrencies.size()==0;
    }
}
