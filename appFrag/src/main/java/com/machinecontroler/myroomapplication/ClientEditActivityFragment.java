package com.machinecontroler.myroomapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class ClientEditActivityFragment extends Fragment {


    private static final String URL_CLIENTUPDATE = "http://nearesthospitals.in/ClientDetailUpdate.php";
    private EditText userID;
    private EditText userName;
    private EditText userAddress;
    private EditText userMAcAdress;
    private EditText userPh;
    private EditText userrefilldate;
    private EditText userFragnance;
    private EditText mailAddress;
    private EditText clientlandmark;
    private EditText clientrefillMonth;
    private Button sendData;
    private ProgressBar pb_Adddata;
    public List<Clients> moviedetails;
    List<MyEmployeeInformation> task;
    private Button clientrefillDate;
    private Intent in;
    private String nm;
    private String msg;
    private String name = null;
    private String clientAddress = null;
    private String clientPhone = null;
    private String clientLm = null;
    private String fm = null;
    private String refillfm = null;
    private Button btSelectdate;
    String dt;

    private final String CATEGORY_PREFERENCE = "UserCategory";



//    public ClientEditActivityFragment() {
//        setHasOptionsMenu(true);
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_client_edit, container, false);

        userName = (EditText) v.findViewById(R.id.editclientName);
        userAddress = (EditText) v.findViewById(R.id.editclientAddress);
        userPh = (EditText) v.findViewById(R.id.editclientPhoneno);
        mailAddress = (EditText) v.findViewById(R.id.editclientEmail);
     //   userMAcAdress= (EditText) v.findViewById(R.id.editclientMachine);
        userrefilldate = (EditText) v.findViewById(R.id.editclientRefilldate);
        userFragnance = (EditText) v.findViewById(R.id.editclientFragnance);
        clientlandmark = (EditText) v.findViewById(R.id.editclientLandmark);
        clientrefillMonth = (EditText) v.findViewById(R.id.editclientMonth);
        pb_Adddata = (ProgressBar) v.findViewById(R.id.progressBar);
        //btSelectdate = (Button) v.findViewById(R.id.select);
         v.findViewById(R.id.updateClient).setOnClickListener(updateClientsdata);
        task = new ArrayList<>();
        pb_Adddata.setVisibility(View.INVISIBLE);

        SharedPreferences shareAccountInformation = getActivity().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
         name = shareAccountInformation.getString("edname", "No value found").toString();
        clientAddress = shareAccountInformation.getString("edaddress", "No value found").toString();
        String phone = shareAccountInformation.getString("edphn", "No value found").toString();
      String clientLm = shareAccountInformation.getString("edlm", "No value found").toString();
        String fraginuse = shareAccountInformation.getString("edfragnanceused", "No value found").toString();
        String rdate = shareAccountInformation.getString("eddate", "No value found").toString();
        String refillmonth = shareAccountInformation.getString("monthchange", "No value found").toString();

        userName.setText(name);
        userAddress.setText(clientAddress);
        userPh.setText(phone);
        clientlandmark.setText(clientLm);
        userFragnance.setText(fraginuse);
        userrefilldate.setText(rdate);
        clientrefillMonth.setText(refillmonth);


        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences shareAccountInformation = getActivity().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
        name = shareAccountInformation.getString("edname", "No value found").toString();
        clientAddress = shareAccountInformation.getString("edaddress", "No value found").toString();
        String phone = shareAccountInformation.getString("edphn", "No value found").toString();
        String clientLm = shareAccountInformation.getString("edlm", "No value found").toString();
        String fraginuse = shareAccountInformation.getString("edfragnanceused", "No value found").toString();
        String rdate = shareAccountInformation.getString("eddate", "No value found").toString();
        String refillmonth = shareAccountInformation.getString("monthchange", "No value found").toString();


        userName.setText(name);
        userAddress.setText(phone);
        userPh.setText(clientAddress);
        clientlandmark.setText(clientLm);
        userFragnance.setText(fraginuse);
        userrefilldate.setText(rdate);
        clientrefillMonth.setText(refillmonth);

    }


    View.OnClickListener updateClientsdata = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Display();
         //getActivity().finish();
//            Intent prev = new Intent(getContext(),MainActivity.class);
//            startActivity(prev);

        }
    };

    public void Display() {

        if (isConnection()) {

            String cc = userName.getText().toString();
            String io = userAddress.getText().toString();
            String ph = userPh.getText().toString();
            String mail = mailAddress.getText().toString();
        //    String mc ="jhk";// userMAcAdress.getText().toString();
            String idev = userFragnance.getText().toString();
            String rdate = userrefilldate.getText().toString();
            String landmark = clientlandmark.getText().toString();
            String month = clientrefillMonth.getText().toString();
            requestData(URL_CLIENTUPDATE, cc, io, ph, mail,idev, rdate, landmark,month);
        }
    }

//        @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//
//
//
//        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_client_edit, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_nothing){

showLogOutAlert();

        }


        return super.onOptionsItemSelected(item);
    }
    public void showLogOutAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(false);
        alert.setIcon(R.mipmap.info_launcher);
        alert.setTitle(R.string.notupdated);
        alert.setMessage(R.string.kindlyclose);
        alert.setPositiveButton(getResources().getString(R.string.kindlyclose),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                mSession.clear();
                        Intent intent = new Intent(getContext(), AdminActivity.class);

                        startActivity(intent);
                        getActivity().finish();

                    }
                });

        alert.setNegativeButton(getResources().getString(R.string.cancel), null);
        alert.show();
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


    private void requestData(String url, String name, String cl_address, String cl_ph, String email, String frg, String date, String lm,String mn) {

        Requestdata data = new Requestdata();
        data.setMethod("POST");
        data.setUri(url);
        data.setParam("Name", name);
        data.setParam("Address", cl_address);
        data.setParam("Phonenumber", cl_ph);
        data.setParam("Emailid", email);
    //    data.setParam("Machinenumber", mn);
        data.setParam("Fragnance", frg);
        data.setParam("Date", date);
        data.setParam("Landmark", lm);
        data.setParam("Month",mn);


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

//                userID.setText("");
                userName.setText("");
                userAddress.setText("");
                userPh.setText("");
                userFragnance.setText("");
                userrefilldate.setText("");
                mailAddress.setText("");
                clientlandmark.setText("");
                clientrefillMonth.setText("");
            }


            moviedetails = parseFeed(result);
            if (!validate()) {
                Toast.makeText(getContext(), "Data sent", Toast.LENGTH_SHORT).show();
            }

        }


    }

    private boolean validate() {
         if (userName.getText().toString().trim().equals(""))
            return false;
        else if (userAddress.getText().toString().trim().equals(""))
            return false;
        else if (userPh.getText().toString().trim().equals(""))
            return false;
        else if (userFragnance.getText().toString().trim().equals(""))
            return false;
        else if (userrefilldate.getText().toString().trim().equals(""))
            return false;
        else if (clientlandmark.getText().toString().trim().equals(""))
            return false;
         else if (clientrefillMonth.getText().toString().trim().equals(""))
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
//                String id = (obj.getString("ClientID"));
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


