package com.afomic.tradeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.tradeapp.ChatActivity;
import com.afomic.tradeapp.R;
import com.afomic.tradeapp.model.Chat;

import java.util.ArrayList;

/**
 * Created by afomic on 1/24/18.
 */

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ChatHolder>{
    private Context mContext;
    private ArrayList<Chat> mChats;

    public ChatAdapter(Context context,ArrayList<Chat> chats){
        mChats=chats;
        mContext=context;
    }
    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_chat,parent,false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        if(mChats!=null){
            return mChats.size();
        }
        return 0;
    }

    public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ChatHolder(View itemView) {
            super(itemView);
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(mContext, ChatActivity.class);
            mContext.startActivity(intent);
        }
    }
}
