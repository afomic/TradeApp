package com.afomic.tradeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by afomic on 1/24/18.
 */

public class Chat implements Parcelable{
    private String userOne;
    private String userTwo;
    private String  id;
    private String lastMessage;
    private long lastUpdate;

    public Chat(){

    }

    protected Chat(Parcel in) {
        userOne = in.readString();
        userTwo = in.readString();
        id = in.readString();
        lastMessage = in.readString();
        lastUpdate = in.readLong();
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userOne);
        dest.writeString(userTwo);
        dest.writeString(id);
        dest.writeString(lastMessage);
        dest.writeLong(lastUpdate);
    }

    public String getUserOne() {
        return userOne;
    }

    public void setUserOne(String userOne) {
        this.userOne = userOne;
    }

    public String getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(String userTwo) {
        this.userTwo = userTwo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
