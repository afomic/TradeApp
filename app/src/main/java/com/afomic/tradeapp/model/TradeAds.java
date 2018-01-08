package com.afomic.tradeapp.model;

import java.util.List;

/**
 * Created by afomic on 1/7/18.
 */

public class TradeAds {
    private String id;
    private String userId;
    private List<Currency> currencyToSell;
    private List<Currency> currencyToBuy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
