package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afomic.tradeapp.ChatActivity;
import com.afomic.tradeapp.R;
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Chat;

import java.util.ArrayList;

/**
 * Created by afomic on 1/24/18.
 */

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ChatHolder>{
    private Context mContext;
    private ArrayList<Chat> mChats;
    private String username;

    public ChatAdapter(Context context,ArrayList<Chat> chats){
        mChats=chats;
        mContext=context;
        PreferenceManager mPreferenceManager=new PreferenceManager(context);
        username=mPreferenceManager.getUsername();
    }
    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_chat,parent,false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        Chat chatItem=mChats.get(position);
        // if i am user one then the person am chating with is userTwo
        if(chatItem.getUserOne().equals(username)){
            holder.recipientTextView.setText(chatItem.getUserTwo());
        }else {
            holder.recipientTextView.setText(chatItem.getUserOne());
        }
        holder.lastMessageTextView.setText(chatItem.getLastMessage());
        CharSequence lastUpdateTime= DateUtils.getRelativeTimeSpanString(chatItem.getLastUpdate());
        holder.lastUpdateTextView.setText(lastUpdateTime);

    }

    @Override
    public int getItemCount() {
        if(mChats!=null){
            return mChats.size();
        }
        return 0;
    }

    public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipientTextView, lastMessageTextView,lastUpdateTextView;
        public ChatHolder(View itemView) {
            super(itemView);
             itemView.setOnClickListener(this);
             recipientTextView=itemView.findViewById(R.id.tv_recipient);
             lastMessageTextView=itemView.findViewById(R.id.tv_last_message);
             lastUpdateTextView=itemView.findViewById(R.id.tv_last_update);
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
