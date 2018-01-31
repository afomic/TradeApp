package com.afomic.tradeapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.afomic.tradeapp.adapter.MessageAdapter;
import com.afomic.tradeapp.model.Message;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.rv_chats)
    RecyclerView chatRecyclerView;
    @BindView(R.id.edt_chat_message)
    EditText chatMessageEditText;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    int count=0;
    MessageAdapter mMessageAdapter;
    ArrayList<Message> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
//        mPreferenceManager=new PreferenceManager(this);
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle("Chat");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessages =new ArrayList<>();
        mMessageAdapter =new MessageAdapter(ChatActivity.this, mMessages);
        chatRecyclerView.setAdapter(mMessageAdapter);
    }
    @OnClick(R.id.fab_send)
    public void sentMessage(){
        if(chatMessageEditText.getText().toString().trim().length()==0){
            Toast.makeText(ChatActivity.this,"You cant Send empty message",
                    Toast.LENGTH_SHORT).show();
        }else {
            Message message =new Message();
            message.setMessage(chatMessageEditText.getText().toString());
            mMessages.add(message);
            chatMessageEditText.setText("");
            chatRecyclerView.scrollToPosition(count);
            mMessageAdapter.notifyItemInserted(count);
            count++;

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
