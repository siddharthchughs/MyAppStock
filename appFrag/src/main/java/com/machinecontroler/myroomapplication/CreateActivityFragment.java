package com.machinecontroler.myroomapplication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateActivityFragment extends Fragment {


    protected RecyclerView.LayoutManager mLayoutManager;
    // private FirebaseAnalytics fbAnalytics;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeView;
    RecyclerView mRecyclerView;
    public List<Dashboard> mdeicalType;
    private Dashboard home_Items;
    private MedicalAdapter medicalAdapter;



    public CreateActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v  = inflater.inflate(R.layout.fragment_create, container, false);;

        mRecyclerView = (RecyclerView) v.findViewById(R.id.createPanel);

updated();


        return v;
    }

    public static List<CreatePanel> getData(){

        List<CreatePanel> ls = new ArrayList<>();
        String[] lk = {"Create Supervisor","Create Client"};
        //    int[] in = {R.drawable.acmln,R.drawable.upstn,R.drawable.stockpn,R.drawable.paydn };
        //int[] img = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher };

        for (int i = 0; i<lk.length ; ++i) {
            CreatePanel info = new CreatePanel();
            info.title = lk[i];
            //      info.img = in[i];
            ls.add(info);
        }
        return ls;
    }

    protected void updated() {
            medicalAdapter = new MedicalAdapter(getActivity(),getData());

            mRecyclerView.setAdapter(medicalAdapter);
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);

    }


    public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MyHolder> {

        private LayoutInflater inflater;
        // List<Dashboard_items> ls = Collections.emptyList();
        List<CreatePanel> ls;
        Intent d_Intent;
        Context mcontext;
        CreatePanel current;
        String id = null;
        String title = null;
        private final String CATEGORY_PREFERENCE = "UserCategory";

        int[] in = {R.drawable.acmln,R.drawable.upstn};

        public MedicalAdapter(Context con, List<CreatePanel> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);
            View vw = inflater.inflate(R.layout.create_items, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, final int position) {

            current = ls.get(position);

            myHolder.categoryTitle.setText(current.title);

            myHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=null;
                    switch (position){
                        case 0:
                            intent =  new Intent(getContext(), AddSupervisorActivity.class);

                            break;

                        case 1:
                            intent =  new Intent(getContext(),AddClientsActivity.class);
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
            ImageView im;
            View mView;

            public MyHolder(View itemView) {
                super(itemView);

                //              itemView.setOnClickListener(this);
                im = (ImageView) itemView.findViewById(R.id.imgview);
                categoryTitle = (TextView) itemView.findViewById(R.id.create_Title);
                mView = itemView ;
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
