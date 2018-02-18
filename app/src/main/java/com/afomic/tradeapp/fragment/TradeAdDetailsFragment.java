package com.afomic.tradeapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afomic.tradeapp.R;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.TradeAd;

import butterknife.BindView;

/**
 * Created by afomic on 2/18/18.
 */

public class TradeAdDetailsFragment extends Fragment {
    @BindView(R.id.tv_last_seen)
    TextView lastSeenTextView;
    @BindView(R.id.tv_username)
    TextView usernameTextView;
    @BindView(R.id.tv_takes)
    TextView takesTextView;
    @BindView(R.id.tv_offer)
    TextView offersTextView;



    private static final String BUNDLE_TRADE_AD="trade";


    private PreferenceManager mPreferenceManager;
    private TradeAd currentTradeAd;
    private ProgressDialog mProgressDialog;

    public TradeAdDetailsFragment newInstance(TradeAd ad){
        Bundle arg=new Bundle();
        arg.putParcelable(BUNDLE_TRADE_AD,ad);
        TradeAdDetailsFragment fragment=new TradeAdDetailsFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentTradeAd=getArguments().getParcelable(BUNDLE_TRADE_AD);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
