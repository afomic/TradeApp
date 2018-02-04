package com.afomic.tradeapp.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

/**
 * Created by afomic on 1/7/18.
 *
 */

public class TradeAd implements Parcelable {
    private String id;
    private String userId;
    private String currencyToSell;
    private String currencyToBuy;
    private double locationLatitude;
    private double locationLongitude;
    private String username;


    public TradeAd(){

    }

    protected TradeAd(Parcel in) {
        id = in.readString();
        userId = in.readString();
        currencyToSell = in.readString();
        currencyToBuy = in.readString();
        locationLatitude = in.readDouble();
        locationLongitude = in.readDouble();
        username = in.readString();
    }

    public static final Creator<TradeAd> CREATOR = new Creator<TradeAd>() {
        @Override
        public TradeAd createFromParcel(Parcel in) {
            return new TradeAd(in);
        }

        @Override
        public TradeAd[] newArray(int size) {
            return new TradeAd[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(currencyToSell);
        dest.writeString(currencyToBuy);
        dest.writeDouble(locationLatitude);
        dest.writeDouble(locationLongitude);
        dest.writeString(username);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrencyToSell() {
        return currencyToSell;
    }

    public void setCurrencyToSell(String currencyToSell) {
        this.currencyToSell = currencyToSell;
    }

    public String getCurrencyToBuy() {
        return currencyToBuy;
    }

    public void setCurrencyToBuy(String currencyToBuy) {
        this.currencyToBuy = currencyToBuy;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude( double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public  double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude( double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
}
