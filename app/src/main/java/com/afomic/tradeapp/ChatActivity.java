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
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Chat;
import com.afomic.tradeapp.model.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    private MessageAdapter mMessageAdapter;
    private ArrayList<Message> mMessages;
    private PreferenceManager mPreferenceManager;
    private Chat currentChat;

    private DatabaseReference chatRef,messageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        
        currentChat=getIntent().getParcelableExtra(Constants.EXTRA_CHAT);

        setSupportActionBar(mToolbar);
        mPreferenceManager=new PreferenceManager(this);

        chatRef= FirebaseDatabase
                .getInstance()
                .getReference(Constants.CHATS_REF);
        messageRef=FirebaseDatabase
                .getInstance()
                .getReference(Constants.MESSAGES_REF)
                .child(currentChat.getId());


        ActionBar actionBar=getSupportActionBar();
        String myUsername=mPreferenceManager.getUsername();
        String recipient=currentChat.getUserTwo().equals(myUsername)?currentChat.getUserOne():currentChat.getUserTwo();

        if(actionBar!=null){
            actionBar.setTitle(recipient);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessages =new ArrayList<>();
        mMessageAdapter =new MessageAdapter(ChatActivity.this, mMessages);
        chatRecyclerView.setAdapter(mMessageAdapter);

        messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message=dataSnapshot.getValue(Message.class);
                mMessages.add(message);
                int insertedPosition=mMessages.size()-1;
                mMessageAdapter.notifyItemInserted(insertedPosition);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @OnClick(R.id.fab_send)
    public void sentMessage(){
        if(chatMessageEditText.getText().toString().trim().length()==0){
            Toast.makeText(ChatActivity.this,"You cant Send empty message",
                    Toast.LENGTH_SHORT).show();
        }else {
            String messageId=messageRef.push().getKey();
            Message message =new Message();
            message.setId(messageId);
            message.setChatId(currentChat.getId());
            message.setTime(System.currentTimeMillis());
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
