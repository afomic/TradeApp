package com.afomic.tradeapp;

import android.content.SharedPreferences;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.afomic.tradeapp.adapter.MessageAdapter;
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Chat;
import com.afomic.tradeapp.model.Message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {
    @BindView(R.id.rv_chats)
    RecyclerView chatRecyclerView;
    @BindView(R.id.edt_chat_message)
    EditText chatMessageEditText;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;



    private MessageAdapter mMessageAdapter;
    private ArrayList<Message> mMessages;
    private PreferenceManager mPreferenceManager;
    private Chat currentChat;
    private String recipientUsername;

    private DatabaseReference chatRef;

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
                .getReference(Constants.CHATS_REF)
                .child(mPreferenceManager.getUsername())
                .child(currentChat.getId());



        ActionBar actionBar=getSupportActionBar();
        String myUsername=mPreferenceManager.getUsername();
        recipientUsername=currentChat.getUserTwo().equals(myUsername)?currentChat.getUserOne():currentChat.getUserTwo();

        if(actionBar!=null){
            actionBar.setTitle(recipientUsername);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessages =new ArrayList<>();
        mMessageAdapter =new MessageAdapter(ChatActivity.this, mMessages);
        chatRecyclerView.setAdapter(mMessageAdapter);

        chatRef.child(Constants.MESSAGES_REF)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message=dataSnapshot.getValue(Message.class);

                mMessages.add(message);
                scrollToTheLastItem();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Message message=dataSnapshot.getValue(Message.class);
                int messagePosition=findAdById(message.getId());
                if(messagePosition!=-1){
                    mMessages.remove(messagePosition);
                    mMessages.add(messagePosition,message);
                    mMessageAdapter.notifyItemChanged(messagePosition);
                }
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
            final DatabaseReference userMessageRef=chatRef.child(Constants.MESSAGES_REF);
            DatabaseReference recipientChatRef=FirebaseDatabase.getInstance()
                    .getReference(Constants.CHATS_REF)
                    .child(recipientUsername)
                    .child(currentChat.getId());
            DatabaseReference recipientMessageRef=recipientChatRef
                    .child(Constants.MESSAGES_REF);
            final String messageId=userMessageRef.push().getKey();
            Message message =new Message();
            message.setId(messageId);
            message.setChatId(currentChat.getId());
            message.setTime(System.currentTimeMillis());
            message.setSenderId(mPreferenceManager.getUserId());
            message.setMessage(chatMessageEditText.getText().toString());
            chatMessageEditText.setText("");
            //update the message to delivered before sending to firebase
            userMessageRef.child(messageId)
                    .setValue(message)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            userMessageRef.child(messageId)
                                    .child("delivered")
                                    .setValue(true);
                        }
                    });
            recipientMessageRef.child(messageId)
                    .setValue(message);
            chatRef.child("lastMessage").setValue(message.getMessage());
            chatRef.child("lastUpdate").setValue(message.getTime());
            recipientChatRef.child("lastMessage").setValue(message.getMessage());
            recipientChatRef.child("lastUpdate").setValue(message.getTime());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }else if(item.getItemId()==R.id.menu_delete_chat) {
            chatRef.removeValue();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void scrollToTheLastItem(){
        int lastItemCount=mMessages.size()-1;
        mMessageAdapter.notifyItemInserted(lastItemCount);
        chatRecyclerView.scrollToPosition(lastItemCount);
    }
    public int  findAdById(String id){
        int length=mMessages.size()-1;
        for(int i=length;i>=0;i--){
            Message message=mMessages.get(i);
            if(message.getId().equals(id)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
