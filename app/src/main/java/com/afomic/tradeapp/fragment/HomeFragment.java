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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afomic.tradeapp.CreateTradeAdActivity;
import com.afomic.tradeapp.MainActivity;
import com.afomic.tradeapp.R;
import com.afomic.tradeapp.SelectLocationActivity;
import com.afomic.tradeapp.TradeAdsDetailsActivity;
import com.afomic.tradeapp.adapter.TradeAdsAdapter;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Currency;
import com.afomic.tradeapp.model.TradeAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by afomic on 1/23/18.
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.rv_trade_ads)
    RecyclerView tradeAdsRecyclerView;
    @BindView(R.id.tv_location)
    TextView userLocationTextView;
    TradeAdsAdapter mTradeAdsAdapter;
    private Unbinder mUnbinder;
    private PreferenceManager mPreferenceManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private Geocoder geocoder;
    private TradeAdsAdapter.TradeAdsListener mTradeAdsListener;

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


        tradeAdsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTradeAdsListener= new TradeAdsAdapter.TradeAdsListener() {
            @Override
            public void onClick() {
                Intent intent=new Intent(getActivity(),TradeAdsDetailsActivity.class);
                startActivity(intent);
            }
        };
        mTradeAdsAdapter=new TradeAdsAdapter(getActivity(),getDummyData(),mTradeAdsListener);
        tradeAdsRecyclerView.setAdapter(mTradeAdsAdapter);
        userLocationTextView.setText(mPreferenceManager.getUserLocation());
        return v;
    }
    public ArrayList<TradeAds> getDummyData(){
        ArrayList<TradeAds> dummyData=new ArrayList<>();
        for(int i=0;i<5;i++){
            TradeAds ads=new TradeAds();
            ads.setCurrencyToSell(getDummyCurrencyData());
            ads.setCurrencyToBuy(getDummyCurrencyData());
            dummyData.add(ads);
        }
        return dummyData;
    }
    public ArrayList<Currency> getDummyCurrencyData(){
        int length=new Random().nextInt(4)+1;
        ArrayList<Currency> currencies=new ArrayList<>();
        for(int i=0;i<length;i++){
            currencies.add(new Currency("BTC"+i));
        }
        return currencies;
    }
    @OnClick(R.id.btn_change_location)
    public void changeCurrentLocation(){
        Intent intent=new Intent(getActivity(), SelectLocationActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.trade_ad_menu,menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==R.id.menu_create_ad){
//            Intent intent=new Intent(getActivity(), CreateTradeAdActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private class GeoCoderTask extends AsyncTask<Location,String,String> {
        @Override
        protected String doInBackground(Location... locations) {

            try {
                Log.e("tag", "onPostExecute: latitude"+locations[0].getLatitude()+": "+locations[0].getLongitude());
                List<Address> addresses =geocoder.getFromLocation(locations[0].getLatitude(),locations[0].getLongitude(), 1);
                Address address=addresses.get(0);
                String userLocation= address.getAdminArea()+", "+address.getCountryName();
                return userLocation;

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            setUserLocation();
        }
        Log.e("", "onRequestPermissionsResult: am called" );
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
