package com.machinecontroler.myroomapplication;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class ClientRefillDateActivity extends AppCompatActivity {

    private TextView tvDisplayDate;
    //    private DatePicker dpResult;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;
    private Button send;
    static final int DATE_DIALOG_ID = 999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clieint_refill_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setCurrentDateOnView();
        addListenerOnButton();
        tvDisplayDate = (TextView) findViewById(R.id.tvDate);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder().append(year).append("-")
                .append(month + 1).append("-").append(day)
        );
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = new StringBuilder().append(year).append("-").append(month + 1).append("-") .append(day).append(" ").toString();
////        Toast.makeText(getApplicationContext(),"Clicked"+data,Toast.LENGTH_SHORT).show();

                Intent in = new Intent(getApplicationContext(),ClientEditActivity.class);
                in.putExtra("refilldate",data);
//               in.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP;
//                in.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP );
                startActivity(in);
                finish();
            }
        });

    }

    // display current date
    public void setCurrentDateOnView() {

        tvDisplayDate = (TextView) findViewById(R.id.tvDate);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder().append(year).append("-")
                .append(month + 1).append("-").append(day)
        );

//        String data = new StringBuilder().append(month + 1).append("-").append(day).append("-") .append(year).append(" ").toString();
//        Toast.makeText(getApplicationContext(),"Clicked"+data,Toast.LENGTH_SHORT).show();
//        Intent in = new Intent(DateSelectionActivity.this,StatusRequestActivity.class);
//        in.putExtra("date",data);
//        startActivity(in);



        // set current date into datepicker
//        dpResult.init(year, month, day, null);

    }



    public void send(){
        send = (Button) findViewById(R.id.send);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        Toast.makeText(getApplicationContext(),"Clicked"+day+month+1+year,Toast.LENGTH_SHORT).show();
//
        Intent in = new Intent(ClientRefillDateActivity.this,StatusRequestActivity.class);

        in.putExtra("month",month);
        in.putExtra("day",day);
        in.putExtra("year",year);
        in.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        startActivity(in);
    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);

        btnChangeDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvDisplayDate.setText(new StringBuilder().append(year).append("-")
                    .append(month + 1).append("-").append(day)
            );


        }
    };

}