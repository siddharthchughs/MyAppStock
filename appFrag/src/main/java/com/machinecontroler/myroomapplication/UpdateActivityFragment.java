package com.machinecontroler.myroomapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class UpdateActivityFragment extends Fragment {

    public static final String URL_UPDATE_STOCK = "http://nearesthospitals.in/UpdateStock_Insert.php?";
    private ImageView im_minus;
    private ImageView im_plus;
    private TextView incrementedValues;
    //String stringVal;
    private int count = 0;
    List<UpdateStock> upList;
    UpdateStock stUpdate;
    LinearLayout ll;
    private Spinner selectItems;
    private Spinner seletedfragnance;
    TextView type_Fragnance;
    Button btSend;
    String dataDate;
    static final String URl_UPDATE = "";
    List<MyStockUpdate> taskUpdate;
    MyStockUpdate task;
    String select = null;
    String selectFrag = null;
    private ProgressBar fragProg;
    String data;
    String stringVal = null;
    String qn = null;
    private Button upButton;
    private TextView td;
    private TextView dateData;
    private Button btdateSelect;
    private Intent indate;
    private String msg = null;
    private Intent in;
    private String stringValu = null;
    private String nm = null;

    public UpdateActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_update, container, false);
//selectItems = (Spinner) v.findViewById(R.id.Itemtype);
        seletedfragnance = (Spinner) v.findViewById(R.id.fragnanceName);
        type_Fragnance = (TextView) v.findViewById(R.id.fragnanceSelected);
        upButton = (Button) v.findViewById(R.id.updateStock);
        dateData = (TextView) v.findViewById(R.id.stockupdatedby);
        fragProg = (ProgressBar) v.findViewById(R.id.progressBarUpdate);
        td = (TextView) v.findViewById(R.id.fragnanceQuantity);
        incrementedValues = (TextView) v.findViewById(R.id.quantityProduct);
        btdateSelect = (Button) v.findViewById(R.id.selectDate);
        taskUpdate = new ArrayList<>();
        in = getActivity().getIntent();
        v.findViewById(R.id.updateStock).setOnClickListener(Send);
        v.findViewById(R.id.quantity_Decrease).setOnClickListener(imm);
        v.findViewById(R.id.quantity_Increase).setOnClickListener(imp);
        v.findViewById(R.id.selectDate).setOnClickListener(dateSelect);

        if (in != null) {
            in = getActivity().getIntent();
            dataDate = in.getStringExtra("date");
            dateData.setText(dataDate);


        }

        fragProg.setVisibility(View.INVISIBLE);




        return v;
    }


    View.OnClickListener dateSelect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), DateSelectedForStockActivity.class));

        }
    };

    View.OnClickListener Send = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Display();
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] rsfrg = getContext().getResources().getStringArray(R.array.types_of_fragnance);
        ArrayAdapter<String> adFrag = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rsfrg);

        seletedfragnance.setAdapter(adFrag);


        seletedfragnance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                select = (String) parent.getAdapter().getItem(position);
                type_Fragnance.setText(select.toString());
                data = select;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    View.OnClickListener imm = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (count != 0) {
                count--;
                stringValu = Integer.toString(count);
                incrementedValues.setText(stringValu);
//                View vg = LayoutInflater.from(getContext()).inflate(R.layout.addedextra,null);

//                Button ad = (Button) vg.findViewById(R.id.button);
//                ad.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getContext(),"Clicked",Toast.LENGTH_SHORT).show();
//                    }
//                });
                //              ll.addView(vg);
                //            ll.setVisibility(View.INVISIBLE);;
//                getActivity().findViewById(R.id.quantity_Increase).setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(getContext(), "No Quantity selected", Toast.LENGTH_SHORT).show();
            }
        }

    };

    View.OnClickListener imp = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            if (count != 35) {
                count++;
                stringValu = Integer.toString(count);
                incrementedValues.setText(stringValu);
            } else {

//              View vg = LayoutInflater.from(getContext()).inflate(R.layout.addedextra,null);
//
//              Button ad = (Button) vg.findViewById(R.id.button);
//              ad.setOnClickListener(new View.OnClickListener() {
//                  @Override
//                  public void onClick(View view) {
//                      Toast.makeText(getContext(),"Clicked",Toast.LENGTH_SHORT).show();
//                  }
//              });
//              ll.addView(vg);
//              ll.setVisibility(View.VISIBLE);;
                Toast.makeText(getContext(), "Quantity cannot be added", Toast.LENGTH_SHORT).show();
//              getActivity().findViewById(R.id.quantity_Increase).setVisibility(View.INVISIBLE);

            }

        }
    };


    public void Display() {

        String d = stringValu.toString();
        td.setText(d);


        if (isConnection()) {

            String name = data;
            String fragquantity = d;


            requestUpdate(URL_UPDATE_STOCK, data, fragquantity, dataDate);

        }
    }

//    View.OnClickListener sent = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//            Display();
//
//        }
//    };


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


    private void requestUpdate(String url, String fr, String qt, String dt) {

        Requestdata data = new Requestdata();
        data.setMethod("POST");
        data.setUri(url);
        data.setParam("Fragnance", fr);
        data.setParam("Quantity", qt);
        data.setParam("date", dt);
        MyStockUpdate me = new MyStockUpdate();
        me.execute(data);
    }


//    private void requestData(String url, String uid, String name, String cl_address, String cl_ph, String email, String mn, String frg, String date,String lm) {
//
//        Requestdata data = new Requestdata();
//        data.setMethod("POST");
//        data.setUri(url);
//        data.setParam("F_UpdateQuantity", uid);
//        data.setParam("F_Name", name);
//
//
//        MyStockUpdate me = new MyStockUpdate();
//        me.execute();
//    }


    public class MyStockUpdate extends AsyncTask<Requestdata, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (taskUpdate.size() == 0) {
                fragProg.setVisibility(View.VISIBLE);
            }
            taskUpdate.add(this);

        }

        @Override
        protected String doInBackground(Requestdata... params) {

            String content = HttpManger.getdata(params[0]);

//            Gson gd = new Gson();
//            UpdateStock[] cl = gd.fromJson(content, UpdateStock[].class);


            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            taskUpdate.remove(this);
            if (taskUpdate.size() == 0) {
                fragProg.setVisibility(View.INVISIBLE);
            }

            if (s != null) {
                upList = parseFeed(s);
//                updateList();

            } else {

                Toast.makeText(getContext(), "Not data avalable", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static List<UpdateStock> parseFeed(String content) {

        UpdateStock list;

        try {
            JSONArray ar = new JSONArray(content);
            List<UpdateStock> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                String quantity = (obj.getString("F_UpdateQuantity"));
                String name = (obj.getString("F_Name"));
                String update = (obj.getString("UpdatedStockDate"));

                list = new UpdateStock(quantity, name, update);

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


}



