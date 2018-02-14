package com.afomic.tradeapp;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.afomic.tradeapp.data.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLocationActivity extends BaseActivity {
    @BindView(R.id.edt_user_location)
    EditText userLocationEditText;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    PreferenceManager mPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        ButterKnife.bind(this);


        setSupportActionBar(mToolbar);
//        mPreferenceManager=new PreferenceManager(this);
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle("Set Location");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mPreferenceManager=new PreferenceManager(this);
        String currentLocation=mPreferenceManager.getUserLocation();
        if(!currentLocation.equals("not found")){
            userLocationEditText.setText(currentLocation);
        }
    }
    @OnClick(R.id.btn_submit_location)
    public void changeUserLocation(){
        if(userLocationEditText.getText().toString().equals("")){
            Toast.makeText(SelectLocationActivity.this,
                    "You cannot submit empty Location",Toast.LENGTH_SHORT).show();
        }else {
            mPreferenceManager.setUserLocation(userLocationEditText.getText().toString());
            finish();
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

