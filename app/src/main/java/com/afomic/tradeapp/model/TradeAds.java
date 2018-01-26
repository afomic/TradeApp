package com.afomic.tradeapp.model;

import android.content.Context;


import java.util.List;

/**
 * Created by afomic on 1/7/18.
 */

public class TradeAds {
    private long id;
    private String userId;
    private List<Currency> currencyToSell;
    private List<Currency> currencyToBuy;

    public TradeAds(){

    }
    public long getTradeId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Currency> getCurrencyToSell() {
        return currencyToSell;
    }

    public void setCurrencyToSell(List<Currency> currencyToSell) {
        this.currencyToSell = currencyToSell;
    }

    public List<Currency> getCurrencyToBuy() {
        return currencyToBuy;
    }

    public void setCurrencyToBuy(List<Currency> currencyToBuy) {
        this.currencyToBuy = currencyToBuy;
    }
}
