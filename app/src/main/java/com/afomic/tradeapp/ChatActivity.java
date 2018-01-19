package com.afomic.tradeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.afomic.tradeapp.adapter.ChatAdapter;
import com.afomic.tradeapp.model.ChatMessage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.rv_chats)
    RecyclerView chatRecyclerView;
    @BindView(R.id.edt_chat_message)
    EditText chatMessageEditText;
    int count=0;
    ChatAdapter mChatAdapter;
    ArrayList<ChatMessage> mChatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatMessages=new ArrayList<>();
        mChatAdapter=new ChatAdapter(ChatActivity.this,mChatMessages);
        chatRecyclerView.setAdapter(mChatAdapter);
    }
    @OnClick(R.id.fab_send)
    public void sentMessage(){
        if(chatMessageEditText.getText().toString().trim().length()==0){
            Toast.makeText(ChatActivity.this,"You cant Send empty message",
                    Toast.LENGTH_SHORT).show();
        }else {
            ChatMessage chatMessage=new ChatMessage();
            chatMessage.setMessage(chatMessageEditText.getText().toString());
            mChatMessages.add(chatMessage);
            chatMessageEditText.setText("");
            chatRecyclerView.scrollToPosition(count);
            mChatAdapter.notifyItemInserted(count);
            count++;

        }
    }
}
