package com.machinecontroler.myroomapplication;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DeletingActivityFragment extends Fragment {

    private static final String STATE_KEY = "CLIENTLIST";
    private List<Clients> clientList;
    private SwipeRefreshLayout swipeList;
    private ListView rvClient;
    private List<MyClients> listAsycClient;
    private List<MyDeleteClients> listDeleteClient;

    private ProgressBar barClients;
    private LinearLayout ll_Progress;
    private Clients cl;
    private String content;
    private List<Clients> cl_Details;
    protected RecyclerView.LayoutManager mLayoutManager;
    // private FirebaseAnalytics fbAnalytics;
    private SwipeRefreshLayout swipeView;
    ListView mRecyclerView;
    TextView tData;
    private RecyclerView rcy_Clients;
    String CLIENT = "http://nearesthospitals.in/Clients.php";
    String DELETE_ALL = "http://nearesthospitals.in/Clients_Delete.php";
    MedicalAdapter adpter;
    private CoordinatorLayout coordinatorLayout;
    private Button btnSimpleSnackbar, btnActionCallback, btnCustomView;
    private ArrayList<Clients> ar = new ArrayList<>();
    private TextView td;

    public DeletingActivityFragment() {

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

        View v = inflater.inflate(R.layout.fragment_deleting, container, false);
        rcy_Clients = (RecyclerView) v.findViewById(R.id.recyclercl);
       // swipeView = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        barClients = (ProgressBar) v.findViewById(R.id.pbHeaderProgress);
        barClients.setVisibility(View.INVISIBLE);
        rcy_Clients.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                return false;

            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        listAsycClient = new ArrayList<>();
        if(savedInstanceState!=null )
        {
            Log.i("Used", "onCreateView: "+adpter.getItemCount());

        }
        else {

            Display();
            Log.i("Used", "onCreateView: ");


        }

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
            requestShowClient(CLIENT);
        }

    }

    public void requestShowClient(String url) {

//        Requestdata rd = new Requestdata();
//        rd.setMethod("GET");
//        rd.setUri(url);
        MyClients cl = new MyClients();
        cl.execute(url);

    }


    public void requestDeleteClient(String url) {

//        Requestdata rd = new Requestdata();
//        rd.setMethod("GET");
//        rd.setUri(url);

        MyDeleteClients cl = new MyDeleteClients();
        cl.execute(url);

    }

    //    @SuppressLint("WrongConstant")
    public void update() {
        if (cl_Details != null) {

//                Holder_of_List hl = new Holder_of_List(getContext(),R.layout.clientlistempty,cl_Details);
//                rvClient.setAdapter(hl);

            adpter = new MedicalAdapter(getActivity(), cl_Details);

            rcy_Clients.setAdapter(adpter);
            mLayoutManager = new LinearLayoutManager(getActivity());
            rcy_Clients.setLayoutManager(mLayoutManager);
            rcy_Clients.setHasFixedSize(true);
            Toast.makeText(getContext()," data available", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(getContext(),"NO data available", Toast.LENGTH_SHORT).show();
        }
//             spinDash.setAdapter((SpinnerAdapter) medicalAdapter);

    }

    public void updateDeleted() {
        if (cl_Details != null) {

////                Holder_of_List hl = new Holder_of_List(getContext(),R.layout.clientlistempty,cl_Details);
////                rvClient.setAdapter(hl);
//
            adpter = new MedicalAdapter(getActivity(), cl_Details);

            rcy_Clients.setAdapter(adpter);

             mLayoutManager = new LinearLayoutManager(getActivity());
            rcy_Clients.setLayoutManager(mLayoutManager);
            rcy_Clients.setHasFixedSize(true);
            Toast.makeText(getContext()," data available", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(getContext(),"No data available !", Toast.LENGTH_SHORT).show();
        }
//             spinDash.setAdapter((SpinnerAdapter) medicalAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();
                        Display();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        int items = item.getItemId();

        switch (items){

            case R.id.action_delete_All:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("Are you sure you want to delete all !");
               alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       if(isConnected()){
//                           adpter.clear();
                           requestDeleteClient(DELETE_ALL);
                       }
                   }
               });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
      //              getActivity().finish();
        Display();
                }
            });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

               break;
        }


        return super.onOptionsItemSelected(item);



    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_deleting,menu);
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

            try{
                Thread.sleep(2000);

                content = HttpManger.getData(requestdata[0]);

            }catch(Exception e){
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
            if(s!=null) {
                cl_Details = parseFeed(s);
                update();
            }
            else {
                Toast.makeText(getContext(),"No data available",Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }


    public class MyDeleteClients extends AsyncTask<String, String, String> {

        @SuppressLint("WrongConstant")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (listDeleteClient.size() == 0) {
                barClients.setVisibility(View.VISIBLE);

            }

            listDeleteClient.add(this);

        }

        @Override
        protected String doInBackground(String... requestdata) {

            try{
                Thread.sleep(2000);

                content = HttpManger.getData(requestdata[0]);

            }catch(Exception e){
                e.printStackTrace();
            }

            return content;
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            listDeleteClient.remove(this);
            if (listDeleteClient.size() == 0) {
                barClients.setVisibility(View.VISIBLE);

            }
            if(s!=null) {
                cl_Details = parseFeed(s);
                update();
            }
            else {
                Toast.makeText(getContext(),"No data available",Toast.LENGTH_SHORT).show();
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

                String id = (obj.getString("ClientID"));
                String name = (obj.getString("Name"));
                String phone = (obj.getString("Phonenumber"));
                String address = (obj.getString("Address"));
                String machine = (obj.getString("Machinenumber"));
                String mail = (obj.getString("Emailid"));
                String fragnance = (obj.getString("Fragnance"));
                String dt = (obj.getString("RefillDate"));
                String lmk= obj.getString("Landmark");
                String mn = (obj.getString("Month"));
                list = new Clients(name,phone,address,mail,machine,fragnance,dt,lmk,mn);

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
        List<Clients> ls;
        Intent d_Intent;
        Context mcontext;
        Clients current;
        String id = null;
        String title = null;
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
            myHolder.categoryID.setText(current.getClientID().toString());
            myHolder.categoryTitle.setText(current.getClientName().toString());
myHolder.v.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {

        deleteItems(position);


        return false;
    }
});

        }

        @Override
        public int getItemCount() {
            return ls.size();
        }


        public void deleteItems(int position){

            ls.remove(position);
            notifyItemChanged(position);

        }

        public class MyHolder extends RecyclerView.ViewHolder {

            TextView categoryTitle;
            TextView categoryID;
            ImageView iml;
            View v;

            public MyHolder(View itemView) {
                super(itemView);

                //              itemView.setOnClickListener(this);
                categoryTitle = (TextView) itemView.findViewById(R.id.textView3);
///                categoryID = (TextView) itemView.findViewById(R.id.textid);
               v= itemView;
              }

//            @Override
//            public void onClick(View view) {
//
////                int itemPosition = getAdapterPosition();
//
////                String id = categoryID.getText().toString();
////
////                    Intent i = new Intent(getContext(), CategoryActivity.class);
////                    i.putExtra("id", id);
////                    startActivity(i);
//
//                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
//                //     if (home_Items != null) {
//                Intent d_Intent;
//                home_Items = mdeicalType.get(itemPosition);
//
//
////                id = home_Items.getId();
//                //              title = home_Items.getTitle();
//                SharedPreferences savenb = getContext().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editNtb = savenb.edit();
//                editNtb.putString("id", id);
//                editNtb.putString("title", title);
//                editNtb.commit();
//
//
//                //            d_Intent = new Intent(getContext(), CategoryActivity.class);
//                //          d_Intent.putExtra("id", id);
//                //        d_Intent.putExtra("title", title);
////                Bundle bndlanimation =
////                        ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_inleft, R.anim.sliderightout).toBundle();
//
//
//                //      startActivity(d_Intent);
////
//
////                   d_Intent.putExtra("movietitle", title);
////                Bundle bndlanimation =
////                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
//
//
////                    Intent d_Intent = new Intent(getContext(), CategoryActivity.class);
////                    d_Intent.putExtra("id", id);
//
////                   d_Intent.putExtra("movietitle", title);
////                Bundle bndlanimation =
////                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
////                    startActivity(d_Intent);
////Toast.makeText(getContext(),"Clicked"+id,Toast.LENGTH_SHORT).show();
//            }

            //          }
        }

    }
}