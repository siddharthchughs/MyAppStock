package com.machinecontroler.myroomapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class DateOnway extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    static final int DIALOG = 0;
    private android.support.v7.widget.Toolbar tb;
   private TextView departure_date,return_Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_onway);

         departure_date = (TextView) findViewById(R.id.values);
        departure_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                final Calendar cal = Calendar.getInstance();
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                showDialog(DIALOG);
*/
                Toast.makeText(DateOnway.this, mYear + "/" + mMonth + "/" + +mDay, Toast.LENGTH_SHORT).show();

            }
        });
 /*       tb = (android.support.v7.widget.Toolbar) findViewById(R.id.toolBar);
        tb.setTitleTextColor(Color.rgb(255, 152, 0));

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
*/


    }



  @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOG)
            return new DatePickerDialog(this,datepickerlistener,mYear,mMonth,mDay);
        return null;
    }

    private DatePickerDialog.OnDateSetListener datepickerlistener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                    mYear = year;
                    mMonth= month+1;
                    mDay = day;
                  departure_date = (TextView) findViewById(R.id.values);
                  departure_date.setText(mYear + "/" + mMonth + "/" + +mDay);

                }
            };




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
