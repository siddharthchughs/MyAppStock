package com.machinecontroler.myroomapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.conn.ClientConnectionManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class RefillMOnthActivityFragment extends Fragment {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private int to_year, to_month, to_day;
    final int DATE_PICKER_TO = 0;
    final int DATE_PICKER_FROM = 1;
    private TextView trip_Date, trip_Month, trip_Year;
    private LinearLayout rt;
    String format = "dd-MM-yyyy";
    private TextView search_Name=null;
    private List<MyClientSearch> mySearch;
    private RecyclerView rv_SearchClientName;
    private LinearLayoutManager layoutManager;
    private TextView txtSearchdata;
    private List<Clients> lsClients;
    private ProgressBar pbSerach;
    private Button SearchClient;
    MyClientSearchAdapter clientSearchAdapter;
    private Spinner seletedfragnance;
    TextView type_Fragnance;
    String data;
    private TextView td;


    String nm=null;

    static final String URL_REFILL_BY_MONTH = "http://nearesthospitals.in/SearchClientsMonth.php?";
    String name=null;

    public RefillMOnthActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_refill_month, container, false);
        seletedfragnance = (Spinner) v.findViewById(R.id.fragnanceName);
        rv_SearchClientName = (RecyclerView) v.findViewById(R.id.clientListSearch);
        type_Fragnance = (TextView) v.findViewById(R.id.fragnanceSelected);
        search_Name = (TextView) v.findViewById(R.id.add_frag_name);
        td = (TextView) v.findViewById(R.id.fragnanceQuantity);
        txtSearchdata = (TextView) v.findViewById(R.id.dataClient);
        SearchClient = (Button) v.findViewById(R.id.SearchClient);
        pbSerach = (ProgressBar) v.findViewById(R.id.progressSearch);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
//      String formattedNow = simpleDateFormat.format(new Date());
//        txtSearchdata.setMovementMethod(new ScrollingMovementMethod());

        pbSerach.setVisibility(View.INVISIBLE);
        mySearch = new ArrayList<>();
//        name  = search_Name.getText().toString();
//        hidekeyboard();
//        rv_SearchClientName.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int positionView = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//                txtSearchdata.setText(Integer.toString(positionView)); //The TextView you want to update
//            }
//        });
        String[] rsfrg = getContext().getResources().getStringArray(R.array.month);
        ArrayAdapter<String> adFrag = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rsfrg);

        seletedfragnance.setAdapter(adFrag);

//        }
//        else
//        {
//            Toast.makeText(getContext(),"No values in the list",Toast.LENGTH_SHORT).show();
//        }


        seletedfragnance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String select = (String) parent.getAdapter().getItem(position);
                search_Name.setText(select.toString());
                data = select;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SearchClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//hidekeyboard(v);
//                nm = search_Name.getText().toString();

                Display(data);


            }
        });


        return v;

    }


//    public void searchByName(View v){
//
//        Toast.
//
//    }






    public void hidekeyboard(View v){
        InputMethodManager inputManager =
                (InputMethodManager) getContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }


    public boolean isConnected() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());
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
                    name = search_Name.getText().toString();

//                    Display();

                }
            });


            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


        return false;
    }


    public void Display(String nm) {


        if (isConnected()) {



            requestSearchClient(URL_REFILL_BY_MONTH,nm);
        }
    }


    public void updateList() {

//try{
//        if(rv_SearchClientName.getAdapter().getItemCount()!=0) {
//        }
//}catch (Exception e){
//    e.printStackTrace();
//}
//
        if (lsClients != null) {


            clientSearchAdapter = new MyClientSearchAdapter(getActivity(), lsClients);
            rv_SearchClientName.setAdapter(clientSearchAdapter);
            layoutManager = new LinearLayoutManager(getActivity());
            rv_SearchClientName.setLayoutManager(layoutManager);
            rv_SearchClientName.setHasFixedSize(true);

//            shownumberItems();

        }
        else

        {
            Toast.makeText(getContext(),"No data avaliable !",Toast.LENGTH_SHORT).show();
        }


    }


    public void shownumberItems(){
        txtSearchdata.setText(clientSearchAdapter.getItemCount());

    }

    public void requestSearchClient(String uri,String mn) {

        Requestdata rd = new Requestdata();
        rd.setMethod("POST");
        rd.setUri(uri);
        rd.setParam("month", mn);
        MyClientSearch clientSearch = new MyClientSearch();
        clientSearch.execute(rd);


    }


    public class MyClientSearch extends AsyncTask<Requestdata, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mySearch.size() == 0) {
                pbSerach.setVisibility(View.VISIBLE);
            }
            mySearch.add(this);

        }

        @Override
        protected String doInBackground(Requestdata... requestdata) {


            String content = HttpManger.getdata(requestdata[0]);

            Gson gsSeatch = new Gson();
            Clients[] cl = gsSeatch.fromJson(content, Clients[].class);

            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mySearch.remove(this);
            if (mySearch.size() == 0) {
                pbSerach.setVisibility(View.INVISIBLE);
            }

            if (s != null) {
                lsClients = parseFeed(s);
                InputMethodManager inputManager =
                        (InputMethodManager) getContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                updateList();

            } else {

                Toast.makeText(getContext(), "Not data avalable", Toast.LENGTH_SHORT).show();
            }


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
                String machine = (obj.getString("Machinenumber"));
                String mail = (obj.getString("Emailid"));
                String fragnance = (obj.getString("Fragnance"));
                String refillDate = (obj.getString("RefillDate"));
                String lmk= obj.getString("Landmark");
                String mn = (obj.getString("Month"));
                list = new Clients(name,phone,address,mail,machine,fragnance,refillDate,lmk,mn);

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


    public class MyClientSearchAdapter extends RecyclerView.Adapter<MyClientSearchAdapter.MyHolder> {

        private LayoutInflater inflater;
        // List<Dashboard_items> ls = Collections.emptyList();
        List<Clients> ls;
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

        int[] in = {R.drawable.acmln, R.drawable.upstn, R.drawable.stockpn, R.drawable.paydn};

        public MyClientSearchAdapter(Context con, List<Clients> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);
            View vw = inflater.inflate(R.layout.clients_by_month_item, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, final int position) {

            current = ls.get(position);
            String count = Integer.toString(position);
            Log.i("", " Processing: " + count);
            myHolder.categoryTitle.setText(current.getClientName());
            myHolder.date_by_month.setText(current.getDate());
            myHolder.mView.setOnClickListener(new View.OnClickListener() {
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
                    editNtb.putString("monthchange",mailid);

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
            TextView date_by_month;
            ImageView im;
            View mView;

            public MyHolder(View itemView) {
                super(itemView);

                //              itemView.setOnClickListener(this);
                im = (ImageView) itemView.findViewById(R.id.imgview);
                categoryTitle = (TextView) itemView.findViewById(R.id.search_Title);
                date_by_month = (TextView) itemView.findViewById(R.id.search_date);
                      mView = itemView;
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
