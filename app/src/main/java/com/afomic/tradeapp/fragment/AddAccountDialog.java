package com.afomic.tradeapp.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by afomic on 2/7/18.
 *
 */

public class AddAccountDialog extends DialogFragment {
    EditText usernameEditText;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    PreferenceManager mPreferenceManager;
    public static AddAccountDialog newInstance(){
       return new AddAccountDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferenceManager=new PreferenceManager(getActivity());
        mAuth=FirebaseAuth.getInstance();
        userDatabaseRef= FirebaseDatabase.getInstance().getReference(Constants.USERS_REF);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Create Account");
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.create_account_view,null,false);
        builder.setView(v);
        usernameEditText=v.findViewById(R.id.edt_user_name);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username=usernameEditText.getText().toString().trim();
                if(!username.equals("")){
                    FirebaseDatabase.getInstance()
                            .getReference(Constants.USERS_REF)
                            .child(mPreferenceManager.getUserId())
                            .child("username")
                            .setValue(username);
                    mPreferenceManager.setUsername(username);
                    mPreferenceManager.setUserLogin(true);
                }
            }
        });

        return builder.create();
    }
}
