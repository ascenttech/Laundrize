package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.AddressData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<AddressData> addressData;
    private TextView address,mobileNumber;

    public AddressRecyclerAdapter(Context context, ArrayList<AddressData> addressData) {
        this.context = context;
        this.addressData = addressData;

        Log.d(Constants.LOG_TAG,Constants.AddressRecyclerAdapter);
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
                .inflate(R.layout.include_available_address, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        findViews(holder);
        setViews(position);
    }

    private void findViews(ViewHolder holder){

            address = (TextView) holder.v.findViewById(R.id.address_text_included);
            mobileNumber = (TextView) holder.v.findViewById(R.id.mobile_number_text_included);


    }

    private void setViews(int position){

        address.setText(Constants.addressData.get(position).getFullAddress());
        address.setTag("address_"+position);
        address.setOnClickListener(listener);

        mobileNumber.setText(Constants.addressData.get(position).getMobileNumber());
        mobileNumber.setTag("number_"+position);
        mobileNumber.setOnClickListener(listener);

    }

    @Override
    public int getItemCount() {
        return addressData.size();
    }


    public void myAddress(int position){

        Toast.makeText(context,"Position "+ position,5000).show();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String tag = view.getTag().toString();
            String pos[] = tag.split("_");
            int position = Integer.parseInt(pos[1]);

            switch (view.getId()){


                case R.id.address_text_included: myAddress(position);
                    notifyDataSetChanged();
                    break;
                case R.id.mobile_number_text_included: myAddress(position);
                    notifyDataSetChanged();
                    break;



            }

        }
    };

}
