package com.afomic.tradeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.adapter.ChatAdapter;
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Chat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by afomic on 1/24/18.
 *
 */

public class ChatListFragment extends Fragment {

    @BindView(R.id.rv_chat_list)
    RecyclerView chatRecyclerView;

    private ChatAdapter mChatAdapter;
    private DatabaseReference chatRef;
    private ArrayList<Chat> mChats;

    private Unbinder mUnbinder;
    public static ChatListFragment newInstance(){
        return new ChatListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatRef= FirebaseDatabase.getInstance().getReference(Constants.CHATS_REF);
        mChats=new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_chat_list,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mChatAdapter=new ChatAdapter(getActivity(),mChats);
        chatRecyclerView.setAdapter(mChatAdapter);
        chatRef.orderByChild("lastUpdate")
        .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat chat=dataSnapshot.getValue(Chat.class);
                mChats.add(0,chat);
                mChatAdapter.notifyItemInserted(0);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Chat chat=dataSnapshot.getValue(Chat.class);
                int position=findAdById(chat.getId());

                if(position!=-1){
                    mChats.remove(position);
                    mChats.add(position,chat);
                    mChatAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Chat chat=dataSnapshot.getValue(Chat.class);
                int position=findAdById(chat.getId());
                if(position!=-1){
                    mChats.remove(position);
                    mChatAdapter.notifyItemRemoved(position);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Chat movedChat=dataSnapshot.getValue(Chat.class);
                int fromPosition=findAdById(movedChat.getId());
                if(fromPosition!=-1){
                    mChats.remove(fromPosition);
                    mChats.add(0,movedChat);
                    mChatAdapter.notifyItemMoved(fromPosition,0);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error has Occured", "onCancelled: ",databaseError.toException() );
            }
        });
        return v;
    }
    public int  findAdById(String id){
        for(int i=0;i<mChats.size();i++){
            Chat chat=mChats.get(i);
            if(chat.getId().equals(id)){
                return i;
            }

        }
        return -1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
