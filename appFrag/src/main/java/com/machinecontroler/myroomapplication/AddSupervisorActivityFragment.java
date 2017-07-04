package com.machinecontroler.myroomapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


//import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddSupervisorActivityFragment extends Fragment {


    private EditText userID;
    private EditText userCompanyCode;
    private EditText userIO;
    private EditText userMAcAdress;
    private EditText userPh;
    private EditText refilldate;
    private EditText userInputDevices;
    private EditText mailAddress;
    private Button sendData;
    private ProgressBar pb_Adddata;
    public List<Supervisor> moviedetails;
    List<MyEmployeeInformation> task;


    private final String URL_SUPERVISOR = "http://nearesthospitals.in/Supervisor_Insert.php";

    public AddSupervisorActivityFragment() {
        setHasOptionsMenu(false);
    }

    //    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_supervisor, container, false);

//          if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
        userID = (EditText) v.findViewById(R.id.supervisorID);
        userCompanyCode = (EditText) v.findViewById(R.id.supervisorName);
        userPh = (EditText) v.findViewById(R.id.supervisorPhoneno);
        mailAddress = (EditText) v.findViewById(R.id.supervisorEmail);
        v.findViewById(R.id.Send).setOnClickListener(sent);
        pb_Adddata = (ProgressBar) v.findViewById(R.id.progressBarsupervisor);

        pb_Adddata.setVisibility(View.INVISIBLE);
        task = new ArrayList<>();
        return v;
    }


    public void Display() {

        if (isConnection()) {

            String uid = userID.getText().toString();
            String cc = userCompanyCode.getText().toString();
            String ph = userPh.getText().toString();
            String mail =mailAddress.getText().toString();

            requestData(URL_SUPERVISOR, uid, cc,ph, mail );
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

            Display();

        }
    };



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

    private void requestData(String url, String uid, String name, String cl_ph, String email) {
        Requestdata data = new Requestdata();
        data.setMethod("POST");
        data.setUri(url);
        data.setParam("id", uid);
        data.setParam("Name", name);
        // data.setParam("Address", cl_address);
        data.setParam("Phonenumber", cl_ph);
        data.setParam("Emailid", email);



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

//            Gson gsSeatch = new Gson();
//            Supervisor[] cl = gsSeatch.fromJson(content, Supervisor[].class);
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
                userPh.setText("");
                userCompanyCode.setText("");
                mailAddress.setText("");

            }



            moviedetails = parseSupervisors(result);
            if(!validate()){
                Toast.makeText(getContext(),"Data sent",Toast.LENGTH_SHORT).show();
            }


        }


    }

    private boolean validate() {
        if (userID.getText().toString().trim().equals(""))
            return false;
        else if (userCompanyCode.getText().toString().trim().equals(""))
            return false;
        else if (mailAddress.getText().toString().trim().equals(""))
            return false;
        else if (userPh.getText().toString().trim().equals(""))
            return false;
        else

            return true;
    }


    public List<Supervisor> parseSupervisors(String content) {

        Supervisor list;

        try {

            JSONArray ar = new JSONArray(content);
            List<Supervisor> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {


                JSONObject obj = ar.getJSONObject(i);
                String sid = (obj.getString("S_ID"));
                String name = (obj.getString("S_Name"));
                String phno= (obj.getString("Phonenumber"));
                String email = (obj.getString("Emailid"));

                list = new Supervisor(sid,name,phno,email);
                //                list = new Supervisor();
//                list.setSupervisor_id(obj.getString("S_ID"));
//                list.setSupervisor_name(obj.getString("S_Name"));
//                list.setSupervisor_ph(obj.getString("Phonenumber"));
//                list.setSupervisor_mail(obj.getString("Emailid"));

                movieList.add(list);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

}


