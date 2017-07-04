package com.machinecontroler.myroomapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private EditText editUsername;
    private EditText editPwd;
    private EditText editUserid;
    private Button btLogin;
    CoordinatorLayout coordinatorLayout;
    private TextView userForgotpwd;
    private Button registerUser;
    private List<MoviewGrid> grid;
    private List<Login> moviedetails;
    // private List<Dashboard> mh;
    private TextView status;
    private AppSession mSession;
    private CheckBox checkBox1;
    private String userData;
    private String userPwd;
    private String url = "http://malukartapi.trianglesoft.in/api/login/login?iUserName=admin&iPassword=admin1";
    static final String URL_LOGIN = "nearesthospitals.in/FragnanceLogin.php?";
    private Login lg;
    private boolean isCorrect = true;
    private LoginActivity mActivity;
    private ProgressBar pb;
    private String username = null;
    private String password = null;
    SwitchCompat switchCompat;
    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        editUsername = (EditText) v.findViewById(R.id.userName);
        editPwd = (EditText) v.findViewById(R.id.userPassword);
        checkBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
        pb = (ProgressBar) v.findViewById(R.id.progressBar2);
        //switchCompat=(SwitchCompat)v.findViewById(R.id.switchButton);

        mActivity = (LoginActivity) getActivity();
//        Intent in = getActivity().getIntent();
//        Uri data = in.getData();

        grid = new ArrayList<>();

        mSession = AppSession.getInstance(mActivity);
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(isChecked) {
//                    username = editUsername.getText().toString().trim();
//                    password = editPwd.getText().toString();
//                    if (isConnected()) {
//                        requestData("http://nearesthospitals.in/FragnanceLogin.php?useremail=" + username + "&phonenumber=" + username + "&pwd=" + password);
//                    }
//                }
//            }
//        });

        pb.setVisibility(View.INVISIBLE);
        btLogin = (Button) v.findViewById(R.id.click);
        // btLogin.setOnClickListener(this);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     doLogin();
//                grid = new ArrayList<>();
                LoginSection();
//doLogin();
            }
        });

        return v;
    }


//    @Override
//    public void onClick(View v) {
//
//
//        grid = new ArrayList<>();
////        startActivity(new Intent(getContext(), DashboardActivity.class)
////                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//
//        LoginSection();
////  doLogin();
//    }


    public void LoginSection() {

        username = editUsername.getText().toString().trim();
        password = editPwd.getText().toString();
        if (isConnected()) {
            requestData("http://nearesthospitals.in/FragnanceLogin.php?useremail=" + username + "&phonenumber=" + username + "&pwd=" + password);
        }

    }

    private void requestData(String uri) {

        Requestdata data = new Requestdata();

        data.setMethod("POST");
        data.setUri(uri);
        MoviewGrid task = new MoviewGrid();
        task.execute(data);
        // task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"task1","task2","task3");
    }


    @SuppressLint("WrongConstant")
    public void update(String content) {

        Login list;

        try {
            JSONObject ar = new JSONObject(content);

            if (ar.getBoolean("Result") == true) {
                if (checkBox1.isChecked()) { // keep me signed in

                    mSession.setHasLoging(true);
                    mSession.setSkipped(true);
//                mSession.setUserId(ar.getInt("ID"));
//                mSession.setEmail(ar.getString("Email_id"));
                    mSession.setUserName(ar.getString("name"));
                    mSession.setFirstName(ar.getString("email"));
                    mSession.setPhone(ar.getString("phonenumber"));
                    //          mSession.setMobileNumber(ar.getString("PhoneNumber"));

                } else { // not want to keep me sign in
                    mSession.setHasLoging(true);
                    mSession.setUserName(ar.getString("name"));
                    mSession.setFirstName(ar.getString("email"));
                    //        mSession.setMobileNumber(ar.getString("PhoneNumber"));
                    mSession.setPhone(ar.getString("phonenumber"));

                }
                Intent in = new Intent(getContext(), AdminActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                Toast.makeText(getContext(), "User" + ar.getString("Message"), Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(getContext(), "User" + ar.getString("Message"), Toast.LENGTH_SHORT).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        JSONObject object = new JSONObject(response);
//        if (object.getBoolean("Result")) {
//
//            if (checkBox1.isChecked()) { // keep me signed in
//                mSession.setHasLoging(true);
//                mSession.setSkipped(true);
//                mSession.setUserId(object.getInt("ID"));
//                mSession.setEmail(object.getString("Email_id"));
//                mSession.setUserName(object.getString("UserName"));
//                mSession.setFirstName(object.getString("First_name"));
//                mSession.setMobileNumber(object.getString("PhoneNumber"));
//
//            }
//            else { // not want to keep me sign in
//                mSession.setHasLoging(true);
//                mSession.setUserId(object.getInt("ID"));
//                mSession.setEmail(object.getString("Email_id"));
//                mSession.setUserName(object.getString("UserName"));
//                mSession.setFirstName(object.getString("First_name"));
//                mSession.setMobileNumber(object.getString("PhoneNumber"));
//            }
//            Intent i = new Intent(mActivity, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//            //startActivity(new Intent(mActivity, MainActivity.class));
//            Toast.makeText(mActivity, object.getString("Message"), Toast.LENGTH_SHORT).show();
//
//        } else {
//            Utils.showDialog(mActivity, "There was a problem", object.getString("Message"));
//            //object.getBoolean("Result")
//        }
//
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }

    }


    public boolean isConnected() {
        @SuppressLint("WrongConstant") ConnectivityManager manage = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

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

    private void doLogin() {
        pb.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN + "useremail=sarvajit@gmail.com&pwd=ss12345",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            pb.setVisibility(View.GONE);
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("Result")) {

                                if (checkBox1.isChecked()) { // keep me signed in
                                    mSession.setHasLoging(true);
                                    mSession.setSkipped(true);
                                    mSession.setEmail(object.getString("email"));
                                    mSession.setUserName(object.getString("name"));

                                } else { // not want to keep me sign in
                                    mSession.setHasLoging(true);
                                    mSession.setEmail(object.getString("email"));
                                    mSession.setUserName(object.getString("name"));
                                }
                                Intent i = new Intent(mActivity, AdminActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                //startActivity(new Intent(mActivity, MainActivity.class));
                                Toast.makeText(mActivity, object.getString("Message"), Toast.LENGTH_SHORT).show();

                            } else {
//                                Utils.showDialog(mActivity, "There was a problem", object.getString("Message"));
                                //object.getBoolean("Result")
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        pb.setVisibility(View.GONE);
                        //mActivity.displayViewOther(1, null);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        if (mActivity != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
            requestQueue.add(stringRequest);
        }
    }


//    private void doLogin() {
//        pb.setVisibility(View.VISIBLE);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN + "?iUserName=" + "sarvajit@gmail.com" + "&iPassword=" + "ss12345",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            pb.setVisibility(View.GONE);
//                            if (ar.getBoolean("Result") == true) {
////                if (checkBox1.isChecked()) { // keep me signed in
//
//                                mSession.setHasLoging(true);
//                                mSession.setSkipped(true);
////                mSession.setUserId(ar.getInt("ID"));
////                mSession.setEmail(ar.getString("Email_id"));
//                                mSession.setUserName(ar.getString("name"));
//                                mSession.setFirstName(ar.getString("email"));
//                                mSession.setMobileNumber(ar.getString("PhoneNumber"));
//                }
//// else { // not want to keep me sign in
////                    mSession.setHasLoging(true);
////                    mSession.setUserName(ar.getString("name"));
////                    mSession.setFirstName(ar.getString("email"));
////                    mSession.setMobileNumber(ar.getString("PhoneNumber"));
////
////                }
//
//                                else { // not want to keep me sign in
//                                    mSession.setHasLoging(true);
//                                    mSession.setUserId(object.getInt("ID"));
//                                    mSession.setEmail(object.getString("Email_id"));
//                                    mSession.setUserName(object.getString("UserName"));
//                                    mSession.setFirstName(object.getString("First_name"));
//                                    mSession.setMobileNumber(object.getString("PhoneNumber"));
//                                }
//                                Intent in = new Intent(getContext(), DashboardActivity.class);
//                                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(in);
//                                //startActivity(new Intent(mActivity, MainActivity.class));
//                                Toast.makeText(mActivity, object.getString("Message"), Toast.LENGTH_SHORT).show();
//
//                            } else {
//                              //  Utils.showDialog(mActivity, "There was a problem", object.getString("Message"));
//                                //object.getBoolean("Result")
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        pb.setVisibility(View.GONE);
//                 //       mActivity.displayViewOther(1, null);
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//        if (mActivity != null) {
//            RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
//            requestQueue.add(stringRequest);
//        }
//    }


    public class MoviewGrid extends AsyncTask<Requestdata, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (grid.size() == 0) {

                pb.setVisibility(View.VISIBLE);
            }
            grid.add(this);


        }

        @Override
        protected String doInBackground(Requestdata... params) {


            String content = HttpManger.getdata(params[0]);

            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            grid.remove(this);
            if (grid.size() == 0) {
                pb.setVisibility(View.INVISIBLE);

            }

            if (s != null) {
                moviedetails = parseFeed(s);
                update(s);
            } else {
                Toast.makeText(getContext(), "Please connect to Intenet ", Toast.LENGTH_SHORT).show();
            }
        }

        public List<Login> parseFeed(String content) {

            Login list;

            try {
                JSONObject ar = new JSONObject(content);
                List<Login> movieList = new ArrayList<>();

//            for (int i = 0; i < ar.length(); i++) {
                list = new Login();
//                JSONObject obj = ar.getJSONObject();
                //    list.setC_id(obj.getString("C_ID"));
                list.setUsername(ar.getString("name"));
                list.setUseremail(ar.getString("email"));
                list.setPassword(ar.getString("Phonenumber"));
                movieList.add(list);

                //          }
                return movieList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }


        }


//        public List<Login> parseFeed(String content) {
//
//            Login list;
//
//            try {
//
//                JSONObject ar = new JSONObject(content);
//                List<Login> movieList = new ArrayList<>();
//
////                for (int i = 0; i < ar.length(); i++) {
//
//
////                    JSONObject obj = ar.getJSONObject(i);
//                    list = new Login();
////           if(ar.getBoolean("Result"))
//               //    list.setMailAddress(ar.getString("Result"));
//                    list.setPositionid(ar.getString("Message"));
//                    movieList.add(list);
//
//  //              }
//                return movieList;
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return null;
//            }
//
//
//        }
//


    }

}
