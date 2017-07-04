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
import java.util.List;

import static android.content.Intent.CATEGORY_PREFERENCE;

/**
 * A placeholder fragment containing a simple view.
 */
public class StockActivityFragment extends Fragment {

    private static final String STATE_KEY = "CLIENTLIST";
    private List<Clients> clientList;
    private SwipeRefreshLayout swipeList;
    private ListView rvClient;
    private List<MyStockInoformation> listAsycStock;
    //private List<MySupervisors> listAsycProviders;

    private ProgressBar barClients;
    private LinearLayout ll_Progress;
    private String content;
    private List<Stock> stock_Details;

    protected RecyclerView.LayoutManager mLayoutManager;
    // private FirebaseAnalytics fbAnalytics;
    private SwipeRefreshLayout swipeView;
    ListView mRecyclerView;
    TextView tData;
    private final String NETBAMKING_PREFERENCE = "UserAuthentication";

    private RecyclerView rcy_Clients;
    private static final String URL_STOCK = "http://nearesthospitals.in/Stock.php";
//    private static final String URL_SUPERVISORLIST = "http://nearesthospitals.in/Supervisors.php";

    MedicalAdapter adpter;
    private CoordinatorLayout coordinatorLayout;
    private Button btnSimpleSnackbar, btnActionCallback, btnCustomView;
    private ArrayList<Stock> ar = new ArrayList<>();
    private TextView td;
    private String id = null;
    private Intent in;
    static final String CLIENT = "Clients";
    static final String SLIENT = "Supervisor";

    public StockActivityFragment() {

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_stock, container, false);
        rcy_Clients = (RecyclerView) v.findViewById(R.id.recyclerStockList);
        //  swipeView = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        barClients = (ProgressBar) v.findViewById(R.id.pbHeaderProgress);
        barClients.setVisibility(View.INVISIBLE);
        listAsycStock = new ArrayList<>();
        // td = (TextView) v.findViewById(R.id.dataClient);
        // td.setMovementMethod(new ScrollingMovementMethod());
   //     DisplayStock();
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

    public void DisplayStock() {


        if (isConnected()) {
            requestShowClient(URL_STOCK);

        }


    }




    public void requestShowClient(String url) {

        MyStockInoformation stk_Info = new MyStockInoformation();
        stk_Info.execute(url);

    }


    public void update() {

        try {
            if (stock_Details != null) {

                adpter = new MedicalAdapter(getActivity(), stock_Details);
                rcy_Clients.setAdapter(adpter);
                mLayoutManager = new LinearLayoutManager(getActivity());
                rcy_Clients.setLayoutManager(mLayoutManager);
                rcy_Clients.setHasFixedSize(true);

            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "NO data available", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
//
//        SharedPreferences shareAccountInformation = getActivity().getSharedPreferences(NETBAMKING_PREFERENCE, Context.MODE_PRIVATE);
//        final String pl_Lat = shareAccountInformation.getString("title", "No value found").toString();

        DisplayStock();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
   DisplayStock();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int items = item.getItemId();

        switch (items) {

            case R.id.action_update:
                Intent inDelete = new Intent(getActivity(), UpdateActivity.class);
                // ActivityOptions.makeCustomAnimation(getContext(), R.anim.anim, R.anim.animate1).toBundle();
                startActivity(inDelete);
                break;
        }


        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_stock, menu);
    }

    public class MyStockInoformation extends AsyncTask<String, String, String> {


        @SuppressLint("WrongConstant")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (listAsycStock.size() == 0) {
                barClients.setVisibility(View.VISIBLE);

            }

            listAsycStock.add(this);

        }

        @Override
        protected String doInBackground(String... requestdata) {

            try {
//                Thread.sleep(2000);

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

            listAsycStock.remove(this);
            if (listAsycStock.size() == 0) {

                barClients.setVisibility(View.INVISIBLE);


            }
            if (s != null) {
                stock_Details = parseFeed(s);
                update();
            } else {
                Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }


    public static List<Stock> parseFeed(String content) {

        Stock list;

        try {
            JSONArray ar = new JSONArray(content);
            List<Stock> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                //              list = new Clients();

                String id = (obj.getString("Item_ID"));
                String type = (obj.getString("Item_Type"));

                list = new Stock(id, type);

//                list.setClientID(obj.getString("ClientID"));
//                list.setClientName(obj.getString("Name"));
//                list.setClientAddress(obj.getString("Address"));
//                list.setClientPhoneNumber(obj.getString("Phonenumber"));
//                list.setClientEmailid(obj.getString("Emailid"));
//                list.setClientMachinenumber(obj.getString("Machinenumber"));
//                list.setClientUsing_Fragnance(obj.getString("Fragnance"));

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
        // List<Dashboard_items> ls = Collections.emptyList();
        List<Stock> ls;
        Intent d_Intent;
        Context mcontext;
        Stock currentStock = null;
        String id = null;
        String title = null;
        private final String CATEGORY_PREFERENCE = "UserCategory";


        public MedicalAdapter(Context con, List<Stock> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);
            View vw = inflater.inflate(R.layout.mystock_items, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, final int position) {
            try {

            Stock currentStock = ls.get(position);
            myHolder.categoryTitle.setText(currentStock.getStock_item_type().toString());

    }catch(Exception e){
    e.printStackTrace();
}
            myHolder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Stock currentStock = ls.get(position);
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(getContext(), FragnanceActivity.class);

                            break;

                        case 1:
                            Toast.makeText(getContext(), "Name is: " + currentStock.getStock_item_type(), Toast.LENGTH_SHORT).show();
                            break;


                    }
                    getActivity().startActivity(intent);


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

                categoryTitle = (TextView) itemView.findViewById(R.id.stock_Type);
                v = itemView;
            }

        }
    }
}


