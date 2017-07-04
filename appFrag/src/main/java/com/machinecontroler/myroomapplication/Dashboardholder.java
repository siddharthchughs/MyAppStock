package com.machinecontroler.myroomapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richie on 07-05-2017.
 */

public class Dashboardholder extends BaseAdapter {



    TextView td;
    Dashboard di;
    Flower fl;
    List<Flower> ld;
    String[] lk = {"Clients","Update Stock","Provide Stock","Payment Details"};
    int[] in = {R.drawable.acmln,R.drawable.upstn,R.drawable.stockpn,R.drawable.paydn };
  Context con;

    public Dashboardholder( Context context,List<Flower> d) {
        this.con = context;
       this.ld= d;
    }

    @Override
    public int getCount() {
        return ld.size();
    }

    @Override
    public Object getItem(int position) {
        return ld.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class VwHoder {

        ImageView iv;
        TextView tx;

        public VwHoder(View v) {

//            iv = (ImageView) v.findViewById(R.id.imgview);
            tx = (TextView) v.findViewById(R.id.itemTitle);


        }

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        VwHoder vh;

//        if (v == null) {

            LayoutInflater inflate = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   //     LayoutInflater inflater = LayoutInflater.from(con.getApplicationContext());
//            v = View.inflate(con,R.layout.dashboard_item,parent,false);
  v = inflate.inflate(R.layout.dashboard_item,null);
            ImageView iv;
            TextView tx;
           //iv = (ImageView) v.findViewById(R.id.imgview);
            tx = (TextView) v.findViewById(R.id.itemTitle);

           tx.setText(ld.get(position).getName());

  //      }

        return v;
    }

}
