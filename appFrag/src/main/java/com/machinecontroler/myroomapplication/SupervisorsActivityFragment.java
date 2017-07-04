package com.machinecontroler.myroomapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
public class SupervisorsActivityFragment extends Fragment {

    private static final String URL_SUPERVISORLIST = "http://nearesthospitals.in/Supervisors.php";
    private RecyclerView rvprovidersList;
    private LinearLayoutManager layoutManager;
    private Supervisor pd;
    List<Supervisor> ds;
    private ProgressBar pbList;
    private ProviderAdapter provideAdapter;
    private List<MyProvidersList> providerList;
    private TextView td;

    private String id = null;

    public SupervisorsActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_supervisors, container, false);

        rvprovidersList = (RecyclerView) v.findViewById(R.id.recycler_Supervisors);
        pbList = (ProgressBar) v.findViewById(R.id.progressBar);
        pbList.setVisibility(View.INVISIBLE);

        providerList = new ArrayList<>();


        Display();


        return v;
    }


    public boolean isConnected() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoNet = cm.getActiveNetworkInfo();
        if (infoNet.isConnected() && infoNet.isConnectedOrConnecting()) {

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

                    Display();
                }
            });


            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }


        return false;
    }


    public void showList(String msg) {
        if (ds != null) {

           provideAdapter = new ProviderAdapter(getActivity(), ds);

            rvprovidersList.setAdapter(provideAdapter);
            layoutManager = new LinearLayoutManager(getActivity());
            rvprovidersList.setLayoutManager(layoutManager);
            rvprovidersList.setHasFixedSize(true);

        } else {
            Toast.makeText(getContext(), "No internet available ! ", Toast.LENGTH_SHORT).show();
        }

    }


    private void Display() {

        if (isConnected()) {
                requestProviderList(URL_SUPERVISORLIST);

        } else {
            Toast.makeText(getContext(), "Not connected to internet !", Toast.LENGTH_SHORT).show();
        }
    }


    public void requestProviderList(String url) {

        MyProvidersList mpl = new MyProvidersList();
        mpl.execute(url);


    }


    public class MyProvidersList extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (providerList.size() == 0) {
                pbList.setVisibility(View.VISIBLE);

            }
            providerList.add(this);

        }

        @Override
        protected String doInBackground(String... strings) {

            String content = HttpManger.getData(strings[0]);


            Gson gs = new Gson();
            Supervisor[] ds = gs.fromJson(content,Supervisor[].class);


            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                providerList.remove(this);

                if (providerList.size() == 0) {

                    pbList.setVisibility(View.INVISIBLE);


                }
                if (s != null) {
                    ds = parseSupervisors(s);
                    showList(s);

                } else {
                    Toast.makeText(getContext(), "Kindly connect to the internet !", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
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

                movieList.add(list);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.MyHolder> {

        private LayoutInflater inflater;
        // List<Dashboard_items> ls = Collections.emptyList();
        List<Supervisor> ls;
        Intent d_Intent;
        Context mcontext;
        Supervisor current;
        String id = null;
        String title = null;
        private final String CATEGORY_PREFERENCE = "UserCategory";

//        int[] in = {R.drawable.acmln,R.drawable.upstn,R.drawable.stockpn,R.drawable.paydn };

        public ProviderAdapter(Context con, List<Supervisor> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);
            View vw = inflater.inflate(R.layout.supervisor_list, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, final int position) {

        Supervisor     current = ls.get(position);

            myHolder.categoryTitle.setText(current.getSupervisor_name());
//            myHolder.im.setImageResource(current.img);


//            myHolder.mView.setOnClickListener(new View.OnClickListener() {
           //     @Override
             //   public void onClick(View view) {
  //                  Intent intent;
//                    Toast.makeText(getContext(),"Position"+position,Toast.LENGTH_SHORT).show();
//                    switch (position){
//                        case 0:
//                            intent =  new Intent(getContext(), MainActivity.class);
//
//                            break;
//
//                        case 1:
//                            intent =  new Intent(getContext(),UpdateActivity.class);
//                            break;
//
//                        case 2:
//                            intent =  new Intent(getContext(), ProvideStockActivity.class);
//
//                            break;
//                        case 3:
//                            intent =  new Intent(getContext(), PaymentDetailActivity.class);
//                            break;
//
//                        default:
//                            intent =  new Intent(getContext(), MainActivity
//                                    .class);
//
//                    }
//                    getActivity().startActivity(intent);

         //       }
           // });

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

                //              itemView.setOnClickListener(this);
              //  im = (ImageView) itemView.findViewById(R.id.imgview);
                categoryTitle = (TextView) itemView.findViewById(R.id.supervisor_Title);
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
