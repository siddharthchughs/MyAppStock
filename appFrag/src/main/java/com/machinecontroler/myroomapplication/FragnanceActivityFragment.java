package com.machinecontroler.myroomapplication;

import android.app.AlertDialog;
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

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragnanceActivityFragment extends Fragment {

    private static final String URL_PROVIDERLSIT = "http://nearesthospitals.in/Fragnances.php";
    private RecyclerView rvprovidersList;
    private LinearLayoutManager layoutManager;
    // public Provdiers pd;
    List<Fragnance> ds;
    private ProgressBar pbList;
    private FragnanceAdapter provideAdapter;
    private List<MyProvidersList> providerList;
    private TextView td;
    private final String NETBAMKING_PREFERENCE = "UserAuthentication";
    Intent intent;


    public FragnanceActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fragnance, container, false);

        rvprovidersList = (RecyclerView) v.findViewById(R.id.clientFragnance);

        pbList = (ProgressBar) v.findViewById(R.id.progressDashboard);
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


    public void showList() {
        if (ds != null) {

            provideAdapter = new FragnanceAdapter(getActivity(), ds);

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
            requestProviderList(URL_PROVIDERLSIT);
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
            Provdiers[] ds = gs.fromJson(content, Provdiers[].class);


            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            providerList.remove(this);

            if (providerList.size() == 0) {

                pbList.setVisibility(View.INVISIBLE);


            }
            if (s != null) {
                ds = parseProviders(s);
                showList();
            } else {
                Toast.makeText(getContext(), "Kindly connect to the internet !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public List<Fragnance> parseProviders(String content) {

        Fragnance list;

        try {

            JSONArray ar = new JSONArray(content);
            List<Fragnance> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {


                JSONObject obj = ar.getJSONObject(i);
          String name=(obj.getString("F_Name"));
                String quantity= (obj.getString("F_UpdateQuantity"));
                list = new Fragnance(name,quantity);

                movieList.add(list);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    public class FragnanceAdapter extends RecyclerView.Adapter<FragnanceAdapter.MyHolder> {

        private LayoutInflater inflater;
        List<Fragnance> ls;
        Intent d_Intent;
        Fragnance pd;
        Context mcontext;
        String id = null;
        String title = null;
        private final String CATEGORY_PREFERENCE = "UserCategory";

        public FragnanceAdapter(Context con, List<Fragnance> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);
            View vw = inflater.inflate(R.layout.fragnance_items, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, final int position) {

            try {
                final  Fragnance pd = ls.get(position);
                myHolder.frag_quantity.setText(pd.getQuantity());
                myHolder.frag_Title.setText(pd.getName());
            }
            catch (Exception e){
                e.printStackTrace();
            }
//            myHolder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Fragnance pd = ls.get(position);
//                    Intent intent = null;
//
//                    switch (position) {
//                        case 0:
//                            intent = new Intent(getContext(), SupervisorsActivity.class);
//
//                            break;
//
//                        case 1:
//                            intent = new Intent(getContext(), MainActivity.class);
//                            break;
//
//                        default:
//                            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
//                    }
//                    getActivity().startActivity(intent);
//
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return ls.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            TextView frag_Title;
            TextView frag_quantity;
            ImageView im;
            List<Fragnance> lp;
            View mView;
            TextView category_Type;

            public MyHolder(View itemView) {
                super(itemView);


               frag_Title = (TextView) itemView.findViewById(R.id.fragnance_Name);
                frag_quantity = (TextView) itemView.findViewById(R.id.fragnance_Quantity);

                mView = itemView;
            }
        }
//            @Override
//            public void onClick(View view) {
//
////                int itemPosition =  rvprovidersList.getChildLayoutPosition(view);
//                int itemPosition = rvprovidersList.getChildAdapterPosition(view);
//
//                pd = ds.get(itemPosition);
////                String id = pd.getTitle();
//
//
////                Toast.makeText(getContext(), "Clicked" + id, Toast.LENGTH_SHORT).show();
////
////                Intent i = new Intent(getContext(), MainActivity.class);
////                i.putExtra("id", id);
////                startActivity(i);
////                SharedPreferences savenb = getContext().getSharedPreferences(NETBAMKING_PREFERENCE, Context.MODE_PRIVATE);
////                SharedPreferences.Editor editNtb = savenb.edit();
////                editNtb.putString("title", id);
////                editNtb.commit();
//
//                Intent intent = null;
//
//                Toast.makeText(getContext(), "Position" + id, Toast.LENGTH_SHORT).show();
//                switch (itemPosition) {
//                    case 0:
//                        intent = new Intent(getContext(), SupervisorsActivity.class);
//
//                        break;
//
//                    case 1:
//                        intent = new Intent(getContext(), MainActivity.class);
//                        break;
//
//                    default:
//                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
//                }
//                getActivity().startActivity(intent);
//
//            }
//
////
////                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
////                //     if (home_Items != null) {
////                Intent d_Intent;
////                String dt = mdeicalType.get(itemPosition);
//
//
////                id = home_Items.getId();
//                //              title = home_Items.getTitle();
////                SharedPreferences savenb = getContext().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
////                SharedPreferences.Editor editNtb = savenb.edit();
////                editNtb.putString("id", id);
////                editNtb.putString("title", title);
////                editNtb.commit();
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
//            }
//
//            //          }
    }

}



