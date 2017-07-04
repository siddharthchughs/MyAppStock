package com.machinecontroler.myroomapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddClientsActivityFragment extends Fragment {


    private EditText userID;
    private EditText userName;
    private EditText userAddress;
    private EditText userMAcAdress;
    private EditText userPh;
    private EditText refilldate;
    private EditText refillmonth;
    private EditText userFragnance;
    private EditText mailAddress;
    private EditText clientlandmark;
    private TextView tdMonth;
    private Button sendData;
    private ProgressBar pb_Adddata;
    public List<Clients> moviedetails;
    List<MyEmployeeInformation> task;
   private Button clientrefillDate;
    private Spinner selectMonth;
    private Intent in;
    private String nm;
    private String msg;
    private String data=null;
    private TextView selectedMonth;
    private final String URL_EMPLOYEE = "http://nearesthospitals.in/Clients_Insert.php";

    public AddClientsActivityFragment() {
        setHasOptionsMenu(false);
    }

    //    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_clients, container, false);

//          if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
        userID = (EditText) v.findViewById(R.id.clientID);
        userName = (EditText) v.findViewById(R.id.clientName);
        userAddress = (EditText) v.findViewById(R.id.clientAddress);
        userPh = (EditText) v.findViewById(R.id.clientPhoneno);
        mailAddress = (EditText) v.findViewById(R.id.clientEmail);
        userMAcAdress = (EditText) v.findViewById(R.id.clientMachine);
        refilldate = (EditText) v.findViewById(R.id.clientRefilldate);
        userFragnance = (EditText) v.findViewById(R.id.clientFragnance);
        clientlandmark = (EditText) v.findViewById(R.id.clientLandmark);
         selectMonth = (Spinner) v.findViewById(R.id.selectMonth);
        selectedMonth = (TextView) v.findViewById(R.id.monthdata);
//        clientrefillDate = (Button) v.findViewById(R.id.dateSelection);
//        clientrefillDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                in = new Intent(getActivity(),ClientRefillDateActivity.class);
//                in.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                startActivity(in);
//            }
//        });


        pb_Adddata = (ProgressBar) v.findViewById(R.id.progressBar);
        pb_Adddata.setVisibility(View.INVISIBLE);

        task = new ArrayList<>();

        v.findViewById(R.id.Send).setOnClickListener(sent);
    //    in = getActivity().getIntent();
  //       msg = in.getStringExtra("refilldate");
//        // search_Name.setText(msg);
    //      nm = msg.toString();
  //   refilldate.setText(msg);
//
//
//        refilldate.setText(nm);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] rsfrg = getContext().getResources().getStringArray(R.array.month);
        ArrayAdapter<String> adFrag = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rsfrg);

        selectMonth.setAdapter(adFrag);

//        }
//        else
//        {
//            Toast.makeText(getContext(),"No values in the list",Toast.LENGTH_SHORT).show();
//        }


        selectMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


         String       select = (String) parent.getAdapter().getItem(position);
                selectedMonth.setText(select.toString());
                data = select;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }







    public void Display() {

        if (isConnection()) {

            String uid = userID.getText().toString();
            String cc = userName.getText().toString();
            String io = userAddress.getText().toString();
            String ph = userPh.getText().toString();
            String mail = mailAddress.getText().toString();
            String mc = userMAcAdress.getText().toString();
            String idev = userFragnance.getText().toString();
            String rdate =  refilldate.getText().toString();
            String landmark = clientlandmark.getText().toString();
            String mnth =  selectedMonth.getText().toString();
            requestData(URL_EMPLOYEE, uid, cc, io, ph, mail, mc, idev, rdate,landmark,mnth);
        }
//        idname = holderUser.getStringExtra("user");
//        idnumber = holderUser.getStringExtra("number");
//        idtype = holderUser.getStringExtra("type");
//
//        SharedPreferences shareAccountInformation = getContext().getSharedPreferences(ACCOUNT_PREFERENCE, MODE_PRIVATE);
//        String  restoreaccname = shareAccountInformation.getString("accname","No value found").toString();
//        String  restoreaccnumber = shareAccountInformation.getString("accnum","No value found").toString();
//
//        textAccountName.setText(restoreaccname);
//        textAccountNumber.setText(restoreaccnumber);

//        if (isConnection()) {
//
//            holderUser = getActivity().getIntent();
//            if (holderUser == null || holderUser.getData() == null) {
//               String idlink = holderUser.getStringExtra("user");
//            }
//
//            // edit text
//            if (isConnection()) {
//
//                String us = "Rakesh";
//                String pwd = "Rakesh123";
//                requestData(URL_NETBANKING, us, pwd);
//            }
//            else
//            {
//                Toast.makeText(getContext(),"No data available for now.",Toast.LENGTH_SHORT).show();
//            }
//
//        }
    }


    View.OnClickListener sent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideKeyboard(view);

            Display();

        }
    };

    private void hideKeyboard(View v) {
        // Check if no view has focus:
        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public boolean isConnection() {

        @SuppressLint("WrongConstant") ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setMessage("Your are not connected to the internet, try again later !");

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    getActivity().finish();
                }
            });

            alertDialogBuilder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    onResume();
                }
            });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            return false;
        }
    }


    public void update() {

        Log.i("datat", "sent");
    }

    private void requestData(String url, String uid, String name, String cl_address, String cl_ph, String email, String mn, String frg, String date,String lm,String mth) {

        Requestdata data = new Requestdata();
        data.setMethod("POST");
        data.setUri(url);
        data.setParam("ClientID", uid);
        data.setParam("Name", name);
        data.setParam("Address", cl_address);
        data.setParam("Phonenumber", cl_ph);
        data.setParam("Emailid", email);
        data.setParam("Machinenumber", mn);
        data.setParam("Fragnance", frg);
        data.setParam("Date", date);
        data.setParam("Landmark",lm);
        data.setParam("Month",mth);

        MyEmployeeInformation me = new MyEmployeeInformation();
        me.execute(data);
    }


    public class MyEmployeeInformation extends AsyncTask<Requestdata, String, String> {


        //  @SuppressLint("WrongConstant")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (task.size() == 0) {
                pb_Adddata.setVisibility(View.VISIBLE);
            }
            task.add(this);


        }

        @Override
        protected String doInBackground(Requestdata... params) {

            String content = HttpManger.getdata(params[0]);

            return content;// "Task on Completed ";

        }

        // onPostExecute displays the results of the AsyncTask.
         @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            task.remove(this);

            if (task.size() == 0) {
                pb_Adddata.setVisibility(View.INVISIBLE);

                userID.setText("");
                userName.setText("");
                userAddress.setText("");
                userPh.setText("");
                userMAcAdress.setText("");
                userFragnance.setText("");
                refilldate.setText("");
               mailAddress.setText("");
               clientlandmark.setText("");
            }


             moviedetails = parseFeed(result);
             if(!validate()){
                 Toast.makeText(getContext(),"Data sent",Toast.LENGTH_SHORT).show();
             }

        }


    }

    private boolean validate() {
        if (userID.getText().toString().trim().equals(""))
            return false;
        else if (userName.getText().toString().trim().equals(""))
            return false;
        else if (userAddress.getText().toString().trim().equals(""))
            return false;
        else if (userMAcAdress.getText().toString().trim().equals(""))
            return false;
        else if (userPh.getText().toString().trim().equals(""))
            return false;
        else if (userFragnance.getText().toString().trim().equals(""))
            return false;
        else if (refilldate.getText().toString().trim().equals(""))
            return false;
        else if (clientlandmark.getText().toString().trim().equals(""))
            return false;

        else

            return true;
    }


    public List<Clients> parseFeed(String content) {

        Clients list;

        try {

            JSONArray ar = new JSONArray(content);
            List<Clients> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {


                JSONObject obj = ar.getJSONObject(i);
//                list.setClientID(obj.getString("ClientID"));
//                list.setClientName(obj.getString("Name"));
//                list.setClientAddress(obj.getString("Address"));
//                list.setClientPhoneNumber(obj.getString("Phonenumber"));
//                list.setClientEmailid(obj.getString("Emailid"));
//                list.setClientMachinenumber(obj.getString("Machinenumber"));
//                list.setClientUsing_Fragnance(obj.getString("Fragnance"));
//                list = new Clients();
                String id = (obj.getString("ClientID"));
                String name = (obj.getString("Name"));
                String phone = (obj.getString("Phonenumber"));
                String address = (obj.getString("Address"));
                String machine = (obj.getString("Machinenumber"));
                String mail = (obj.getString("Emailid"));
                String fragnance = (obj.getString("Fragnance"));
               String dt = (obj.getString("Refilldate"));
                String lm = (obj.getString("Landmark"));
               String mn = (obj.getString("Month"));
                list = new Clients(name,phone,address,mail,machine,fragnance,dt,lm,mn);

                movieList.add(list);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

}


