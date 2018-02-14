package com.afomic.tradeapp.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afomic.tradeapp.ChatActivity;
import com.afomic.tradeapp.CreateTradeAdActivity;
import com.afomic.tradeapp.R;
import com.afomic.tradeapp.SelectLocationActivity;
import com.afomic.tradeapp.TradeAdsDetailsActivity;
import com.afomic.tradeapp.adapter.TradeAdsAdapter;
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Chat;
import com.afomic.tradeapp.model.TradeAd;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by afomic on 1/23/18.
 *
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.rv_trade_ads)
    RecyclerView tradeAdsRecyclerView;
    @BindView(R.id.tv_location)
    TextView userLocationTextView;



    private TradeAdsAdapter mTradeAdsAdapter;
    private Unbinder mUnbinder;
    private PreferenceManager mPreferenceManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private Geocoder geocoder;
    private TradeAdsAdapter.TradeAdsListener mTradeAdsListener;
    private ArrayList<TradeAd> mTradeAds;


    private DatabaseReference tradeAdRef,userRef;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferenceManager=new PreferenceManager(getActivity());
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        String currentLocation=mPreferenceManager.getUserLocation();
        if(currentLocation.equals("not found")){
            setUserLocation();
        }
        tradeAdRef= FirebaseDatabase.getInstance().getReference(Constants.TRADE_ADS_REF);
        userRef= FirebaseDatabase.getInstance().getReference(Constants.USERS_REF);
    }

    @Override
    public void onResume() {
        super.onResume();
        userLocationTextView.setText(mPreferenceManager.getUserLocation());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        mUnbinder= ButterKnife.bind(this,v);

        mTradeAds=new ArrayList<>();
        tradeAdsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTradeAdsListener= new TradeAdsAdapter.TradeAdsListener() {
            @Override
            public void onClick(int position) {
                TradeAd tradeAd=mTradeAds.get(position);
                if(tradeAd.getUserId().equals(mPreferenceManager.getUserId())){
                    Intent intent=new Intent(getActivity(),CreateTradeAdActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(getActivity(),TradeAdsDetailsActivity.class);
                    intent.putExtra(Constants.EXTRA_TRADE_AD,tradeAd);
                    startActivity(intent);
                }

            }
        };
        mTradeAdsAdapter=new TradeAdsAdapter(getActivity(),mTradeAds,mTradeAdsListener);
        tradeAdsRecyclerView.setAdapter(mTradeAdsAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(
               getActivity(),
                DividerItemDecoration.VERTICAL
        );
        tradeAdsRecyclerView.addItemDecoration(divider);
        userLocationTextView.setText(mPreferenceManager.getUserLocation());


        tradeAdRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TradeAd ad=dataSnapshot.getValue(TradeAd.class);
                mTradeAds.add(0,ad);
                mTradeAdsAdapter.notifyItemInserted(0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                TradeAd ad=dataSnapshot.getValue(TradeAd.class);
                int position=findAdById(ad.getId());
                if(position!=-1){
                    mTradeAds.remove(position);
                    mTradeAds.add(position,ad);
                    mTradeAdsAdapter.notifyItemChanged(position);
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                TradeAd ad=dataSnapshot.getValue(TradeAd.class);
                int position=findAdById(ad.getId());
                if(position!=-1){
                    mTradeAds.remove(position);
                    mTradeAdsAdapter.notifyItemRemoved(position);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }
    @OnClick(R.id.btn_change_location)
    public void changeCurrentLocation(){
        Intent intent=new Intent(getActivity(), SelectLocationActivity.class);
        startActivity(intent);
    }

    private class GeoCoderTask extends AsyncTask<Location,String,String> {
        @Override
        protected String doInBackground(Location... locations) {

            try {
                List<Address> addresses =geocoder.getFromLocation(locations[0].getLatitude(),locations[0].getLongitude(), 1);
                Address address=addresses.get(0);
                return address.getAdminArea()+", "+address.getCountryName();

            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                mPreferenceManager.setUserLocation(s);
                userLocationTextView.setText(s);
            }
        }
    }
    public void setUserLocation(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions( new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },100);

        }else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                if(geocoder.isPresent()){
                                    Log.e("Tag", "onSuccess: is present ");
                                    mPreferenceManager.setUserLatitude(location.getLatitude());
                                    mPreferenceManager.setUserLongitude(location.getLongitude());

                                    new GeoCoderTask().execute(location);
                                }else {
                                    Log.e("Tag", "onSuccess: not present ");

                                }

                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            setUserLocation();
        }
        Log.e("tag", "onRequestPermissionsResult: am called" );
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public int  findAdById(String id){
        for(int i=0;i<mTradeAds.size();i++){
            TradeAd ad=mTradeAds.get(i);
            if(ad.getId().equals(id)){
                return i;
            }

        }
        return -1;
    }
}
