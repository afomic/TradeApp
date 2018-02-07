package com.afomic.tradeapp.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by afomic on 2/7/18.
 *
 */

public class AddAccountDialog extends DialogFragment {
    public static AddAccountDialog newInstance(){
       return new AddAccountDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.create();
    }
}
