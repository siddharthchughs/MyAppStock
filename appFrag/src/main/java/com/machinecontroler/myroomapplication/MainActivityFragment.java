package com.machinecontroler.myroomapplication;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Intent.CATEGORY_PREFERENCE;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String STATE_KEY = "CLIENTLIST";
    private List<Clients> clientList;
    private SwipeRefreshLayout swipeList;
    private ListView rvClient;
    private List<MyClients> listAsycClient;
    private ProgressBar barClients;
    private LinearLayout ll_Progress;
    private Clients cl;
    private String content;
    private List<Clients> cl_Details;
    private List<Supervisor> cl_Provide;

    protected RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeView;
    ListView mRecyclerView;
    TextView tData;
    private final String NETBAMKING_PREFERENCE = "UserAuthentication";

    private RecyclerView rcy_Clients;
    private static final String URL_CLIENT = "http://nearesthospitals.in/Clients.php";

    MedicalAdapter adpter;
    //    SupervisorsAdapter adapt;
    private CoordinatorLayout coordinatorLayout;
    private Button btnSimpleSnackbar, btnActionCallback, btnCustomView;
    private ArrayList<Clients> ar = new ArrayList<>();
    private TextView td;
    private String id = null;
    private Intent in;
    static final String CLIENT = "Clients";
    static final String SLIENT = "Supervisor";

    public MainActivityFragment() {

//        setHasOptionsMenu(true);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        rcy_Clients = (RecyclerView) v.findViewById(R.id.recyclercl);
        //  swipeView = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        barClients = (ProgressBar) v.findViewById(R.id.pbHeaderProgress);
        barClients.setVisibility(View.INVISIBLE);
        listAsycClient = new ArrayList<>();
        // td = (TextView) v.findViewById(R.id.dataClient);
        // td.setMovementMethod(new ScrollingMovementMethod());
        Display();
        Log.i("Used", "onCreateView: ");


        return v;
    }


    public boolean isConnected() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

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


                }
            });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            // Changing action button text color
            return false;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_KEY, ar);
    }

    public void Display() {


        if (isConnected()) {
            requestShowClient(URL_CLIENT);

        }


    }

    public void requestShowClient(String url) {

        MyClients cl = new MyClients();
        cl.execute(url);

    }


    public void update() {


        try {
            if (cl_Details != null) {
                adpter = new MedicalAdapter(getActivity(), cl_Details);
                rcy_Clients.setAdapter(adpter);
                mLayoutManager = new LinearLayoutManager(getActivity());
                rcy_Clients.setLayoutManager(mLayoutManager);
                rcy_Clients.setHasFixedSize(true);

            } else {
                Toast.makeText(getContext(), "You are out of the network right now ! ", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    @Override
    public void onResume() {
        super.onResume();

        Display();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        int items = item.getItemId();

        switch (items) {



//            case R.id.searchClients:
//                Intent inDelete = new Intent(getActivity(), SearchClientsActivity.class);
//
//               startActivity(inDelete);
//                break;
        }


        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    public class MyClients extends AsyncTask<String, String, String> {


        @SuppressLint("WrongConstant")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (listAsycClient.size() == 0) {
                barClients.setVisibility(View.VISIBLE);

            }

            listAsycClient.add(this);

        }

        @Override
        protected String doInBackground(String... requestdata) {

            try {

                content = HttpManger.getData(requestdata[0]);
                Gson gs = new Gson();
                Clients[] ds = gs.fromJson(content, Clients[].class);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return content;
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            listAsycClient.remove(this);
            if (listAsycClient.size() == 0) {

                barClients.setVisibility(View.INVISIBLE);


            }
            if (s != null) {
                cl_Details = parseFeed(s);
                update();
            } else {
                Toast.makeText(getContext(), "Network problem !", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }


    public static List<Clients> parseFeed(String content) {

        Clients list;

        try {
            JSONArray ar = new JSONArray(content);
            List<Clients> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                //              list = new Clients();

//                String id = (obj.getString("ClientID"));
                String name = (obj.getString("Name"));
                String phone = (obj.getString("Phonenumber"));
                String address = (obj.getString("Address"));
                String mail = (obj.getString("Emailid"));
                String machine = (obj.getString("Machinenumber"));
                String fragnance = (obj.getString("Fragnance"));
                String dt = (obj.getString("RefillDate"));
                String lmk= obj.getString("Landmark");
                String mn = (obj.getString("Month"));
                list = new Clients(name,phone,address,mail,machine,fragnance,dt,lmk,mn);

                movieList.add(list);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public List<Supervisor> parseSupervisors(String content) {

        Supervisor list;

        try {

            JSONArray ar = new JSONArray(content);
            List<Supervisor> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {


                JSONObject obj = ar.getJSONObject(i);
//                list = new Supervisor();
//                list.setSupervisor_id(obj.getString("S_ID"));
//                list.setSupervisor_name(obj.getString("S_Name"));
//                list.setSupervisor_ph(obj.getString("Phonenumber"));
//                list.setSupervisor_mail(obj.getString("Emailid"));
                String sid = (obj.getString("S_ID"));
                String name = (obj.getString("S_Name"));
                String phno= (obj.getString("Phonenumber"));
                String email = (obj.getString("Emailid"));

                list = new Supervisor(sid,name,phno,email);

                movieList.add(list);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MyHolder> {

        private LayoutInflater inflater;
       //  List<Dashboard_items> ls = Collections.emptyList();
        List<Clients> ls;//= Collections.emptyList();
        Intent d_Intent;
        Context mcontext;
        Clients current;
        String id = null;
        String title = null;
        String address = null;
        String phonenumber = null;
        String mailid=null;
        String daterefill;
        String landmark = null;
        String frname=null;
        String month=null;
        private final String CATEGORY_PREFERENCE = "UserCategory";


        public MedicalAdapter(Context con, List<Clients> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);
            View vw = inflater.inflate(R.layout.clientlistempty, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, final int position) {

            Clients current = ls.get(position);
       //     myHolder.categoryID.setText(current.getClientID().toString());
            myHolder.categoryTitle.setText(current.getClientName().toString());

            myHolder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Clients current = ls.get(position);

                    title = current.getClientName();
                    address = current.getClientAddress();
                    phonenumber = current.getClientPhoneNumber();
                    landmark = current.getLmark();
                    daterefill = current.getDate();
                    frname = current.getClientUsing_Fragnance();
                     mailid = current.getClientEmailid();
                    month = current.getMonth();


                    SharedPreferences savenb = mcontext.getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editNtb = savenb.edit();
                    editNtb.putString("edname", title);
                    editNtb.putString("edaddress", address);
                    editNtb.putString("edphn", phonenumber);
                    editNtb.putString("edlm", landmark);
                    editNtb.putString("eddate", daterefill);
                    editNtb.putString("edfragnanceused",frname);
                    editNtb.putString("edemail",mailid);
                    editNtb.putString("monthchange",month);

                    editNtb.commit();


                    d_Intent = new Intent(mcontext, DetailActivity.class);
                    d_Intent.putExtra("name", title);
                    d_Intent.putExtra("address", address);
                    d_Intent.putExtra("phonenumber", phonenumber);
                    d_Intent.putExtra("lm", landmark);
                    d_Intent.putExtra("rd",daterefill);
                    d_Intent.putExtra("fm",frname);
                    d_Intent.putExtra("email",mailid);
                    d_Intent.putExtra("month",month);



                    startActivity(d_Intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return ls.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            TextView categoryTitle;
            TextView categoryID;
            ImageView iml;
            String address;
            String clientPhnumber;

            View v;

            public MyHolder(View itemView) {
                super(itemView);

                //               itemView.setOnClickListener(this);
                categoryTitle = (TextView) itemView.findViewById(R.id.textView3);
                v = itemView;
            }

//            @Override
//            public void onClick(View view) {
//
//                int itemPosition = getAdapterPosition();

////                String id = categoryID.getText().toString();
////                String name =
//                    Intent i = new Intent(getContext(), CategoryActivity.class);
//                    i.putExtra("id", id);
//                    startActivity(i);

//                int itemPosition = rcy_Clients.getChildLayoutPosition(view);
            //     if (home_Items != null) {
            //      Intent d_Intent;
//                home_Items = mdeicalType.get(itemPosition);


//                id = current.getClientID();
//                title = current.getClientName();
//                address = current.getClientAddress();
//                clientPhnumber = current.getClientPhoneNumber();
//                SharedPreferences savenb = getContext().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editNtb = savenb.edit();
//                editNtb.putString("id", id);
//                editNtb.putString("title", title);
//                editNtb.putString("address", address);
//                editNtb.putString("phonenumber", clientPhnumber);
//
//                editNtb.commit();


//                            d_Intent = new Intent(getContext(), DetailActivity.class);
//                          d_Intent.putExtra("id", id);
//                        d_Intent.putExtra("title", title);
//                d_Intent.putExtra("address", address);
//                d_Intent.putExtra("phonenumber", clientPhnumber);

            //                Bundle bndlanimation =
//                        ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_inleft, R.anim.sliderightout).toBundle();
//

//                      startActivity(d_Intent);
//

//                   d_Intent.putExtra("movietitle", title);
//                Bundle bndlanimation =
//                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();


//                    Intent d_Intent = new Intent(getContext(), CategoryActivity.class);
//                    d_Intent.putExtra("id", id);

//                   d_Intent.putExtra("movietitle", title);
//                Bundle bndlanimation =
//                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
//                    startActivity(d_Intent);
//Toast.makeText(getContext(),"Clicked"+id,Toast.LENGTH_SHORT).show();
//            }

        }
    }

}


