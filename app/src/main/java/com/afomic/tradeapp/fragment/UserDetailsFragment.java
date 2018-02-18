package com.afomic.tradeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by afomic on 1/24/18.
 */

public class UserDetailsFragment extends Fragment {
    @BindView(R.id.tv_name)
    TextView usernameTextView;
    @BindView(R.id.tv_user_id)
    TextView userIdTextView;
    @BindView(R.id.tv_member_since)
    TextView userSinceTextView;
    @BindView(R.id.tv_location)
    TextView userLocationTextView;
    @BindView(R.id.tv_last_seen)
    TextView lastSeenTextView;


    private PreferenceManager mPreferenceManager;
    private Unbinder mUnbinder;
    
    public static UserDetailsFragment newInstance(){
        return new UserDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferenceManager=new PreferenceManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_user_details,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        userIdTextView.setText(mPreferenceManager.getUserId());
        usernameTextView.setText(mPreferenceManager.getUsername());
        userLocationTextView.setText(mPreferenceManager.getUserLocation());
        FirebaseDatabase.getInstance()
                .getReference(Constants.USERS_REF)
                .orderByChild("userId")
                .equalTo(mPreferenceManager.getUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            User user=snapshot.getValue(User.class);
                            CharSequence lastSeen= DateUtils.getRelativeTimeSpanString(user.getLastSeen());
                            lastSeenTextView.setText(lastSeen);
                            CharSequence memberSince=DateUtils.getRelativeTimeSpanString(user.getMemberSince());
                            userSinceTextView.setText(memberSince);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
