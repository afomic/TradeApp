package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afomic.tradeapp.ChatActivity;
import com.afomic.tradeapp.R;
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Chat;
import com.afomic.tradeapp.model.Message;
import com.afomic.tradeapp.util.DateUtil;
import com.amulyakhare.textdrawable.TextDrawable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by afo mic on 1/24/18.
 *
 */

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ChatHolder>{
    private Context mContext;
    private ArrayList<Chat> mChats;
    private String username;
    private PreferenceManager mPreferenceManager;

    public ChatAdapter(Context context,ArrayList<Chat> chats){
        mChats=chats;
        mContext=context;
        mPreferenceManager=new PreferenceManager(context);
        username=mPreferenceManager.getUsername();

    }
    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_chat,parent,false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(final ChatHolder holder, int position) {
        Chat chatItem=mChats.get(position);
        String firstLetter;
        // if i am user one then the person am chating with is userTwo
        if(chatItem.getUserOne().equals(username)){
            holder.recipientTextView.setText(chatItem.getUserTwo());
            firstLetter=String.valueOf(chatItem.getUserTwo().charAt(0)).toUpperCase();
        }else {
            holder.recipientTextView.setText(chatItem.getUserOne());
            firstLetter=String.valueOf(chatItem.getUserOne().charAt(0)).toUpperCase();
        }

        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(firstLetter,chatItem.getColor());
        holder.recipientInitial.setImageDrawable(myDrawable);
        holder.lastMessageTextView.setText(chatItem.getLastMessage());
        String lastUpdateTime= DateUtil.formatDate(chatItem.getLastUpdate());
        holder.lastUpdateTextView.setText(lastUpdateTime);

        FirebaseDatabase.getInstance()
                .getReference(Constants.CHATS_REF)
                .child(mPreferenceManager.getUsername())
                .child(chatItem.getId())
                .child(Constants.MESSAGES_REF)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count =0;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Message message=snapshot.getValue(Message.class);
                            if(!message.getSenderId().equals(mPreferenceManager.getUserId())
                                    &&!message.isRead()){
                                count++;
                            }
                        }
                        if(count>0){
                            holder.unreadMentionTextView.setText(String.valueOf(count));
                            holder.unreadMentionTextView.setVisibility(View.VISIBLE);
                        }else {
                            holder.unreadMentionTextView.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        if(mChats!=null){
            return mChats.size();
        }
        return 0;
    }

    public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipientTextView, lastMessageTextView,lastUpdateTextView,unreadMentionTextView;
        ImageView recipientInitial;
        public ChatHolder(View itemView) {
            super(itemView);
             itemView.setOnClickListener(this);
             recipientTextView=itemView.findViewById(R.id.tv_recipient);
             lastMessageTextView=itemView.findViewById(R.id.tv_last_message);
             lastUpdateTextView=itemView.findViewById(R.id.tv_last_update);
             unreadMentionTextView=itemView.findViewById(R.id.tv_unread_messages);
             recipientInitial=itemView.findViewById(R.id.imv_recipient_initials);
        }

        @Override
        public void onClick(View v) {
            Chat selectedChat=mChats.get(getAdapterPosition());
            Intent intent=new Intent(mContext, ChatActivity.class);
            intent.putExtra(Constants.EXTRA_CHAT,selectedChat);
            mContext.startActivity(intent);
        }
    }
}
