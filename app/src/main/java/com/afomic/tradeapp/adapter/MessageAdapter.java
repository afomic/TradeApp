package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.model.Message;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by afomic on 1/19/18.
 *
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private ArrayList<Message> mChats;
    private static final int MESSAGE_TYPE_SENT=101;
    private static final int MESSAGE_TYPE_RECEIVED=102;


    public MessageAdapter(Context context, ArrayList<Message> messages){
        mContext=context;
        mChats= messages;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==MESSAGE_TYPE_RECEIVED){
            View view= LayoutInflater.from(mContext)
                    .inflate(R.layout.item_recieved_message,parent,false);
            return new ReceivedViewHolder(view);
        }
        View view= LayoutInflater.from(mContext)
                .inflate(R.layout.item_sent_message,parent,false);
        return new SentViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return MESSAGE_TYPE_RECEIVED;
        }
        return MESSAGE_TYPE_SENT;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message =mChats.get(position);
        int viewType=getItemViewType(position);
        String firstLetter=getSaltString();
        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.SERIF)
                .fontSize(18)
                .toUpperCase()
                .endConfig()
                .buildRound(firstLetter, ColorGenerator.MATERIAL.getRandomColor());
        if(viewType==MESSAGE_TYPE_RECEIVED){
            ReceivedViewHolder receivedViewHolder=(ReceivedViewHolder)holder;
            receivedViewHolder.receivedMessageTextView.setText(message.getMessage());
            receivedViewHolder.senderNameImageView.setImageDrawable(myDrawable);

        }else {
            SentViewHolder sentViewHolder=(SentViewHolder) holder;
            sentViewHolder.senderNameImageView.setImageDrawable(myDrawable);
            sentViewHolder.sentMessageTextView.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        if(mChats!=null){
           return mChats.size();
        }
        return 0;
    }

    public class SentViewHolder extends RecyclerView.ViewHolder{
        TextView sentMessageTextView;
        ImageView senderNameImageView;
        public SentViewHolder(View itemView) {
            super(itemView);
            sentMessageTextView=itemView.findViewById(R.id.tv_sent_message);
            senderNameImageView=itemView.findViewById(R.id.imv_sender_name);
        }
    }
    public class ReceivedViewHolder extends RecyclerView.ViewHolder{
        TextView receivedMessageTextView;
        ImageView senderNameImageView;
        public ReceivedViewHolder(View itemView) {
            super(itemView);
            senderNameImageView=itemView.findViewById(R.id.imv_sender_name);
            receivedMessageTextView=itemView.findViewById(R.id.tv_received_message);
        }
    }
    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 2) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
