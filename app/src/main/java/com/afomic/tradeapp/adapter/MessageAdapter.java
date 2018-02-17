package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Message;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.firebase.database.FirebaseDatabase;

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
    private PreferenceManager mPreferenceManager;
    private boolean darkTheme;


    public MessageAdapter(Context context, ArrayList<Message> messages,boolean darkTheme){
        mContext=context;
        mChats= messages;
        mPreferenceManager=new PreferenceManager(context);
        this.darkTheme=darkTheme;
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
        Message message=mChats.get(position);
        if(mPreferenceManager.getUserId().equals(message.getSenderId())){
            return MESSAGE_TYPE_SENT;
        }
        return MESSAGE_TYPE_RECEIVED;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message =mChats.get(position);
        int viewType=getItemViewType(position);
        if(viewType==MESSAGE_TYPE_RECEIVED){
            ReceivedViewHolder receivedViewHolder=(ReceivedViewHolder)holder;
            receivedViewHolder.receivedMessageTextView.setText(message.getMessage());

        }else {
            SentViewHolder sentViewHolder=(SentViewHolder) holder;
            sentViewHolder.sentMessageTextView.setText(message.getMessage());
            if(message.isDelivered()){
                sentViewHolder.messageIndicatorImageView.setImageResource(
                        darkTheme?R.drawable.ic_done_all_white:R.drawable.ic_done_all);
            }else {
                sentViewHolder.messageIndicatorImageView.setImageResource(
                        darkTheme?R.drawable.ic_waiting_white:R.drawable.ic_waiting);
            }
        }
        if(!message.isRead()&&!message.getSenderId().equals(mPreferenceManager.getUserId())){
            FirebaseDatabase.getInstance()
                    .getReference(Constants.MESSAGES_REF)
                    .child(message.getChatId())
                    .child(message.getId())
                    .child("read")
                    .setValue(true);
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
        ImageView messageIndicatorImageView;
        LinearLayout messageLayout;
        public SentViewHolder(View itemView) {
            super(itemView);
            sentMessageTextView=itemView.findViewById(R.id.tv_sent_message);
            messageIndicatorImageView=itemView.findViewById(R.id.imv_message_indicator);
            messageLayout=itemView.findViewById(R.id.message_layout);
            if(darkTheme){
                messageLayout.setBackgroundColor(
                        mContext.getResources().getColor(R.color.darkThemeSentChatMessageBackground));
            }
        }
    }
    public class ReceivedViewHolder extends RecyclerView.ViewHolder{
        TextView receivedMessageTextView;
        LinearLayout messageLayout;
        public ReceivedViewHolder(View itemView) {
            super(itemView);
            receivedMessageTextView=itemView.findViewById(R.id.tv_received_message);
            messageLayout=itemView.findViewById(R.id.message_layout);
            if(darkTheme){
                messageLayout.setBackgroundColor(
                        mContext.getResources().getColor(R.color.darkThemeReceivedChatMessageBackground));
            }
        }
    }
}
