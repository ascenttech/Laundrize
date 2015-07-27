package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.AddressData;
import com.ascenttechnovation.laundrize.data.LaundryServicesSubCategoryData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class TrackOrdersRecyclerAdapter extends RecyclerView.Adapter<TrackOrdersRecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<LaundryServicesSubCategoryData> laundryServicesSubCategoryData;
    private TextView address,mobileNumber;

    public TrackOrdersRecyclerAdapter(Context context) {
        this.context = context;
    }

    public TrackOrdersRecyclerAdapter(Context context, ArrayList<LaundryServicesSubCategoryData> laundryServicesSubCategoryData) {
        this.context = context;
        this.laundryServicesSubCategoryData = laundryServicesSubCategoryData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View v;
        public ViewHolder(View view) {
            super(view);
            v = view;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_track_order, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        findViews(holder);
        setViews(position);
    }

    private void findViews(ViewHolder holder){



    }

    private void setViews(int position){


    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public void myAddress(int position){

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

//            String tag = view.getTag().toString();
//            String pos[] = tag.split("_");
//            int position = Integer.parseInt(pos[1]);
//
//            switch (view.getId()){
//
//
//                case R.id.address_text_included: myAddress(position);
//                    notifyDataSetChanged();
//                    break;
//                case R.id.mobile_number_included: myAddress(position);
//                    notifyDataSetChanged();
//                    break;
//
//
//
//            }

        }
    };

}
