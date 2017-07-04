package com.machinecontroler.myroomapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Holder_of_List extends ArrayAdapter<Clients> {

    public Context con;
    List<Clients> ls;
    Clients cl;
    public int[] in = {R.drawable.clml,R.drawable.stkup,R.drawable.truckdel,R.drawable.paymentdt };

    public Holder_of_List(Context context, int resource, List<Clients> lc) {
        super(context, resource, lc);
        this.con = context;
        this.ls = lc;
    }


    class VwHoder {

        ImageView iv;
        TextView tx;

        public VwHoder(View v) {

            iv = (ImageView) v.findViewById(R.id.imageView);
            tx = (TextView) v.findViewById(R.id.textView3);


        }

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View v = convertView;
        VwHoder vh;

        if (v == null) {

            LayoutInflater inflte = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflte.inflate(R.layout.clientlistempty, parent, false);

            vh = new VwHoder(v);
            v.setTag(vh);
            Log.d("Creating the View :", ":");

        } else {

            vh = (VwHoder) v.getTag();
            Log.d("Recycling the View :", "");

        }

//		vh.iv.setImageResource(images[position]);
        cl = ls.get(position);
        vh.tx.setText(cl.getClientAddress());


        return v;
    }


}
