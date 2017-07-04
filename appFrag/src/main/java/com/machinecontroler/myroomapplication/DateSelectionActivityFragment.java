//package com.machinecontroler.myroomapplication;
//
//import android.annotation.SuppressLint;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.support.v4.app.DialogFragment;
//import android.support.v4.app.Fragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.Calendar;
//
///**
// * A placeholder fragment containing a simple view.
// */
//public class DateSelectionActivityFragment extends Fragment {
//
//
//    private TextView tvDisplayDate;
//    private DatePicker dpResult;
//    private Button btnChangeDate;
//
//    private int year;
//    private int month;
//    private int day;
//    private Button btPicker;
//    static final int DATE_DIALOG_ID = 999;
//    String dateClient=null;
//    private int mYear, mMonth, mDay, mHour, mMinute;
//    private int to_year, to_month, to_day;
//    final    int DATE_PICKER_TO = 0;
//    final int DATE_PICKER_FROM = 1;
//    private TextView trip_Date,trip_Month,trip_Year;
//  private TextView tc_newdate;
//    public DateSelectionActivityFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//  View v= inflater.inflate(R.layout.fragment_date_selection, container, false);
////        tvDisplayDate = (TextView) v.findViewById(R.id.tvDate);
//        tc_newdate = (TextView) v.findViewById(R.id.tvnewDate);
//        dpResult = (DatePicker) v.findViewById(R.id.dpResult);
////        btPicker = (Button) v.findViewById(R.id.dialogButtonOK);
////        final Calendar c = Calendar.getInstance();
////        year = c.get(Calendar.YEAR);
////        month = c.get(Calendar.MONTH);
////        day = c.get(Calendar.DAY_OF_MONTH);
////
////        // set current date into textview
//////        tvDisplayDate.setText(new StringBuilder()
//////                // Month is 0 based, just add 1
//////                .append(month + 1).append("-").append(day).append("-")
//////                .append(year).append(" "));
////        tc_newdate.setText(new StringBuilder().append(month+1).append("-").append(day));
////
////  btPicker.setOnClickListener(new View.OnClickListener() {
////      @Override
////      public void onClick(View v) {
//////          tvDisplayDate.setText(Integer.parseInt(String.valueOf(Calendar.MONTH)));
////          DialogFragment newFragment = new SelectDateFragment();
////          newFragment.show(getFragmentManager(), "DatePicker");
////
////      }
////  });
////
//
//        // set current date into datepicker
//        dpResult.init(year, month, day, null);
////
//
//        new DatePickerDialog(getContext(), datePickerListener,
//                year, month,day);
//
//
//        return v;
//    }
//
//
//    @SuppressLint("ValidFragment")
//    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            final Calendar calendar = Calendar.getInstance();
//            int yy = calendar.get(Calendar.YEAR);
//            int mm = calendar.get(Calendar.MONTH);
//            int dd = calendar.get(Calendar.DAY_OF_MONTH);
//            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
//        }
//
//        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
//            populateSetDate(yy, mm+1, dd);
//        }
//        public void populateSetDate(int year, int month, int day) {
// //           dob.setText(month+"/"+day+"/"+year);
//            tvDisplayDate.setText(new StringBuilder().append(month + 1)
//                    .append("-").append(day).append("-").append(year)
//                    .append(" "));
//        }
//
//    }
////    @Override
////    public Dialog onCreateDialog(int id) {
////        switch (id) {
////            case DATE_DIALOG_ID:
////                // set date picker as current date
////                return new DatePickerDialog(getContext(), datePickerListener,
////                        year, month,day);
////        }
////        return null;
////    }
//
//    private DatePickerDialog.OnDateSetListener datePickerListener
//            = new DatePickerDialog.OnDateSetListener() {
//
//        // when dialog box is closed, below method will be called.
//        public void onDateSet(DatePicker view, int selectedYear,
//                              int selectedMonth, int selectedDay) {
//            year = selectedYear;
//            month = selectedMonth;
//            day = selectedDay;
//
//            // set selected date into textview
//            tvDisplayDate.setText(new StringBuilder().append(month + 1)
//                    .append("-").append(day).append("-").append(year)
//                    .append(" "));
////
//            trip_Date.setText(to_day + "-" + to_month+ "-" + +to_year);
//            Toast.makeText(getContext(),"month is "+to_month,Toast.LENGTH_SHORT).show();
//            // set selected date into datepicker also
//            dpResult.init(year, month, day, null);
//
//        }
//    };
//
//    private DatePickerDialog.OnDateSetListener to_dateListener =
//            new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//
//                    to_year = year;
//                    to_month= month+1;
//                    to_day = day;
////                    tvDisplayDate = (TextView) getActivity().findViewById(R.id.tvDate);
//                    Toast.makeText(getContext(),"month is "+to_month,Toast.LENGTH_SHORT).show();
//
////                    trip_Date.setText(to_day + "-" + to_month+ "-" + +to_year);
//
//                }
//            };
////
//
//}
