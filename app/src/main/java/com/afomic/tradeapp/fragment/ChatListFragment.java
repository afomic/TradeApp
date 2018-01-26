package com.afomic.tradeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.adapter.ChatAdapter;
import com.afomic.tradeapp.model.Chat;

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

    Unbinder mUnbinder;
    public static ChatListFragment newInstance(){
        return new ChatListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_chat_list,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mChatAdapter=new ChatAdapter(getActivity(),getDummyData());
        chatRecyclerView.setAdapter(mChatAdapter);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
    public ArrayList<Chat> getDummyData(){
        ArrayList<Chat> currencies=new ArrayList<>();
        for(int i=0;i<5;i++){
            currencies.add(new Chat());
        }
        return currencies;
    }
}
