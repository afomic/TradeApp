package com.afomic.tradeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by afomic on 1/19/18.
 */

public class Message  implements Parcelable{
    private String message;
    private String id;
    private long time;
    private String pictureUrl;
    private String chatId;
    private boolean delivered;
    private boolean read=false;
    private String senderId;

    public Message(){

    }

    protected Message(Parcel in) {
        message = in.readString();
        id = in.readString();
        time = in.readLong();
        pictureUrl = in.readString();
        chatId = in.readString();
        delivered = in.readByte() != 0;
        read = in.readByte() != 0;
        senderId = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(id);
        dest.writeLong(time);
        dest.writeString(pictureUrl);
        dest.writeString(chatId);
        dest.writeByte((byte) (delivered ? 1 : 0));
        dest.writeByte((byte) (read ? 1 : 0));
        dest.writeString(senderId);
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
