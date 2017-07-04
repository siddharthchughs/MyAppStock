package com.machinecontroler.myroomapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SpashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 1000;
    private AppSession mSession;
    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);
        mSession = AppSession.getInstance(SpashScreenActivity.this);
        Intent in = getIntent();
        Uri data = in.getData();

        refreshUI();
    }


    private void refreshUI() {
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {

                                          if (mSession.getSkipped()) {
                                              Intent in = new Intent(SpashScreenActivity.this, AdminActivity.class);
                                              in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                              startActivity(in);
                                              //finish();
                                          } else {
                                              Intent in = new Intent(SpashScreenActivity.this, LoginActivity.class);
                                              in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                              startActivity(in);
                                              // finish();
                                          }
                                      }
                                  },

                SPLASH_DISPLAY_LENGTH);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
   finish();
    }

}

