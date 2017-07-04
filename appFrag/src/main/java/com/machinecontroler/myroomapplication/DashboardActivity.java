package com.machinecontroler.myroomapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DashboardActivity extends AppCompatActivity {


    private AppSession mSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         toolbar.setTitleTextColor(Color.WHITE);
        mSession = AppSession.getInstance(this);
        Intent in = getIntent();
        Uri data = in.getData();

        toolbar.setSubtitle(mSession.getUserName());
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

  //    getSupportActionBar().setHomeButtonEnabled(true);
//       getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }


//    public static synchronized DashboardActivity getInstance() {
//        return mInstance;
//    }

//    public void setConnectivityListener(ConnectivityMaanger.ConnectivityReceiverListener listener) {
//        ConnectivityMaanger.connectivityReceiverListener = listener;
//    }





}