package com.afomic.tradeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.tradeapp.R;

/**
 * Created by afomic on 1/24/18.
 */

public class UserDetailsFragment extends Fragment {
    public static UserDetailsFragment newInstance(){
        return new UserDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_user_details,container,false);
        return v;
    }
}
