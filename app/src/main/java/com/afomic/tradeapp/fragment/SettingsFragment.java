package com.afomic.tradeapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;



import android.support.v7.preference.PreferenceFragmentCompat;

import com.afomic.tradeapp.R;


/**
 *
 * Created by afomic on 1/2/18.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);

    }
}
