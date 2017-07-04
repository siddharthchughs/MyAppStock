package com.machinecontroler.myroomapplication;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import okhttp3.Call;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

/**
 * A placeholder fragment containing a simple view.
 */

public class DashboardActivityFragment extends Fragment {

    private ListView forecastList;

    int position = 0;
    private TextView vCount;
    int count;
    private View rootView;
    private ProgressBar bar;
    Dashboardholder hl;
    private final String URL_HOSPITAL_LINK = "http://nearesthospitals.in/Dashboard_Select.php?dashboardid=1";
    private final String STATE_MOVIES = "movie_list";
    private TextView movieData;
    public List<Dashboard> mdeicalType;
    private Dashboard home_Items;
    private List<MoviewGrid> grid;
    private Toolbar tb;
    boolean mDualPane;
    private Spinner choose;
    int mCurCheckPosition = 0;
    private MedicalAdapter medicalAdapter;
    private Button bt_Location;
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    private final int MINIMUM_SESSION = 5000;
    private static final String MEDICAL_SERVICES = "MedicalCategory";
    private final String CATEGORY_PREFERENCE = "UserCategory";

    private int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;
    private LinearLayout ll_Manage;
    private TextView txt_Refillmanage;
    protected RecyclerView.LayoutManager mLayoutManager;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeView;
    RecyclerView mRecyclerView;
    TextView td;
    private Spinner spinDash;
    ListView lv;
    private ProgressBar pdDashboard;
    String[] lk = {"Clients", "Update Stock", "Provide Stock", "Payment Details"};
    Gson gs = new Gson();
    private static DashboardActivity mInstance;
    boolean connected = false;
   private DashboardActivity mActivity;
    private AppSession mSession;


    public DashboardActivityFragment() {

        setHasOptionsMenu(true);
    }


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mInstance = ;
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
        //      Toast.makeText(getContext(),"Android version lollipop",Toast.LENGTH_SHORT).show();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.dashboard);
        pdDashboard = (ProgressBar) rootView.findViewById(R.id.progressDashboard);
        mSession = AppSession.getInstance(getActivity());

//        ll_Manage = (LinearLayout) rootView.findViewById(R.id.vManage);
//        ll_Manage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(getContext(),ManagementSystem.class));
//            }
//        });

        //    LinearLayout layout = new LinearLayout(getContext());
//            mRecyclerView = new RecyclerView(getContext());
        //  td = (TextView) rootView.findViewById(R.id.data);
        //      swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
//                      lv = (ListView) rootView.findViewById(R.id.list);
//        swipeView.setColorScheme(R.color.colorAccent,
//                android.R.color.holo_blue_light,
//                android.R.color.holo_green_light,
//                android.R.color.holo_green_light);
//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//
//
//
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//
//
//            }
//        });

        pdDashboard.setVisibility(View.INVISIBLE);
        grid = new ArrayList<>();

//                updated();

        //}
        //  else
        // {
//                LinearLayout layout = new LinearLayout(getContext());
        //              lv = new ListView(getContext());
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                    (getActivity(),android.R.layout.simple_list_item_1, lk);
        //     lv = (ListView) rootView.findViewById(R.id.list);
        //  mdeicalType = new ArrayList<>();

//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
//                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                params.setMargins(15, 5, 5, 5);
//                layout.setLayoutParams(params);
//
//
//                layout.setOrientation(LinearLayout.HORIZONTAL);
//                layout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
//                layout.setPadding(10, 10, 5, 5);
//        getActivity().setContentView(layout);
        //            Toast.makeText(getContext(),"Android lower version",Toast.LENGTH_SHORT).show();
        //              grid = new ArrayList<>();
        //            Display();

        //        }

        return rootView;

    }


//    public static List<Dashboard> getData() {
//
//        List<Dashboard> ls = new ArrayList<>();
//        String[] lk = {"Clients", "Update Stock", "Provide Stock", "Payment Details"};
//        //    int[] in = {R.drawable.acmln,R.drawable.upstn,R.drawable.stockpn,R.drawable.paydn };
//        //int[] img = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher };
//
//        for (int i = 0; i < lk.length; ++i) {
//            Dashboard info = new Dashboard();
//            info.name = lk[i];
//            //      info.img = in[i];
//            ls.add(info);
//        }
//        return ls;
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState==null){


        }
        else {
            Display();

        }




//        fbAnalytics = FirebaseAnalytics.getInstance(getContext());
//        fbAnalytics.setMinimumSessionDuration(MINIMUM_SESSION);


    }



    protected void updated() {

        try{
            if (mdeicalType != null) {
                medicalAdapter = new MedicalAdapter(getActivity(), mdeicalType);

                mRecyclerView.setAdapter(medicalAdapter);
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setHasFixedSize(true);

            } else {
               }


        }catch(Exception e){
e.printStackTrace();
            Toast.makeText(getContext(), "No internet available ! ", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_dashboard, menu);

//        MenuItem it = menu.findItem(R.id.submenu_one);
//
//        getActivity().getMenuInflater().inflate(R.menu.submenu, it.getSubMenu());



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id) {
            case R.id.action_create:
                startActivity(new Intent(getContext(), CreateActivity.class));

                break;

            case R.id.action_find:
                startActivity(new Intent(getContext(), ManagementSystem.class));
                break;

            case R.id.action_logout:
      showLogOutAlert();
                break;

//            case R.id.adminPanel:
//               // showLogOutAlert();
//               startActivity(new Intent(getContext(),AdminPanelActivity.class));
//                break;

        }


        return super.onOptionsItemSelected(item);
    }

    public void showLogOutAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(false);
        alert.setIcon(R.mipmap.info_launcher);
        alert.setTitle(R.string.confirm);
        alert.setMessage(R.string.areyousure);
        alert.setPositiveButton(getResources().getString(R.string.userlogout),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSession.clear();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        //getActivity().finish();
                    }
                });

        alert.setNegativeButton(getResources().getString(R.string.cancel), null);
        alert.show();
    }


    @Override
    public void onResume() {
        super.onResume();
//        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeView.setRefreshing(true);
//                (new Handler()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeView.setRefreshing(false);
        Display();
//                    }
//                }, 2000);
//            }
//        });

    }


    public void Display() {

        if (isOnline()) {

            requestData(URL_HOSPITAL_LINK);

        }
    }

    private void requestData(String url) {

        MoviewGrid mg = new MoviewGrid();
        mg.execute(url);

    }


    protected boolean isOnline() {
        @SuppressLint("WrongConstant") ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Toast.makeText(getActivity(), "Connected to Wifi  !", Toast.LENGTH_SHORT).show();

//            if (netInfo.getType() != ConnectivityManager.TYPE_MOBILE) {
//                Toast.makeText(getActivity(), "Connected to Mobile  Data !", Toast.LENGTH_SHORT).show();
//
//            }

            return true;
        }
        else {
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

                    Display();
                }
            });

        }
//        try {
//            @SuppressLint("WrongConstant") ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            cm = (ConnectivityManager) getContext()
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//            connected = networkInfo != null && networkInfo.isAvailable() &&
//                    networkInfo.isConnected();
//
//
//    }catch (Exception e){
//            e.printStackTrace();
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
//            alertDialogBuilder.setMessage("Your are not connected to the internet, try again later !");
//
//            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                @Override
//
//                public void onClick(DialogInterface arg0, int arg1) {
//                    getActivity().finish();
//                }
//            });
//            alertDialogBuilder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    Display();
//                }
//            });


//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//
//        }
        return false;

    }


    public class MoviewGrid extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (grid.size() == 0) {

                pdDashboard.setVisibility(View.VISIBLE);
            }
            grid.add(this);


        }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManger.getData(params[0]);

            Gson gs = new Gson();
            Dashboard[] ds = gs.fromJson(content, Dashboard[].class);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                grid.remove(this);
                if (grid.size() == 0) {
                    pdDashboard.setVisibility(View.INVISIBLE);
                }

                if (s != null) {

                    mdeicalType = parseFeed(s);
                    updated();
// run();
                } else {
                    Toast.makeText(getActivity(), "No data available  !", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        void run() throws IOException {
//
//            OkHttpClient client = new OkHttpClient();
//
//            Request request = new Request.Builder()
//                    .url(URL_HOSPITAL_LINK)
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    call.cancel();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//
//                    final String myResponse = response.body().string();
//
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            txtString.setText(myResponse);
//                            updated();
//                        }
//                    });
//
//                }
//            });
//        }

        public List<Dashboard> parseFeed(String content) {

            Dashboard list;

            try {

                JSONArray ar = new JSONArray(content);
                List<Dashboard> movieList = new ArrayList<>();

                for (int i = 0; i < ar.length(); i++) {


                    JSONObject obj = ar.getJSONObject(i);
                    list = new Dashboard();
                    list.setName(obj.getString("D_title"));
                    movieList.add(list);

                }
                return movieList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }


        }




    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        Display();

    }

    public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MyHolder> {

        private LayoutInflater inflater;
        // List<Dashboard_items> ls = Collections.emptyList();
        List<Dashboard> ls;
        Intent d_Intent;
        Context mcontext;
        Dashboard current;
        String id = null;
        String title = null;
        private final String CATEGORY_PREFERENCE = "UserCategory";
        int row_index=0;
        public MyHolder holder;

        int[] in = {R.drawable.acmln, R.drawable.upstn, R.drawable.stockpn, R.drawable.paydn};

        public MedicalAdapter(Context con, List<Dashboard> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);
            View vw = inflater.inflate(R.layout.dashboard_item, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(final MyHolder myHolder, final int position) {

//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    current = ls.get(position);

                    row_index = position;
                    myHolder.categoryTitle.setText(current.getName());


                    myHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=null;
                            row_index=position;
                            notifyDataSetChanged();
                            switch (position) {
                                case 0:
                                    intent = new Intent(getContext(), MainActivity.class);

                                    break;

                                case 1:
                                    intent = new Intent(getContext(), StockActivity.class);
                                    break;

                                case 2:
                                    intent = new Intent(getContext(), ProvideStockActivity.class);

                                    break;
                                case 3:
                                    intent = new Intent(getContext(), PaymentDetailActivity.class);
                                    break;
                                case 4:
                                    intent = new Intent(getContext(), StatusRequestActivity.class);
                                    break;

                                case 5:
                                    intent = new Intent(getContext(), SupervisorsActivity.class);
                                    break;

                            }
                            getActivity().startActivity(intent);

                        }
                    });

            //    }


//            myHolder.mView.setOnTouchListener(new View.OnTouchListener() {
//                @Override public boolean onTouch(View v, MotionEvent event) {
//
//                    if (event.getAction()==MotionEvent.ACTION_DOWN){
////                        Resources res = getResources();
////                        Drawable img = res.getDrawable(R.drawable.ripple_dashoboard);
//                        myHolder.mView.setBackgroundColor(Color.DKGRAY);
//                    }if (event.getAction()==MotionEvent.ACTION_UP){
//                        myHolder.mView.setBackgroundColor(Color.WHITE);
//                    }
//
//                    return false;
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return ls.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            TextView categoryTitle;
            ImageView im;
            View mView;

            public MyHolder(View itemView) {
                super(itemView);
                im = (ImageView) itemView.findViewById(R.id.imgview);
                categoryTitle = (TextView) itemView.findViewById(R.id.itemTitle);
                mView = itemView;
            }

        }

    }


}



