package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.data.TrackOrdersData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class TrackOrdersRecyclerAdapter extends RecyclerView.Adapter<TrackOrdersRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TrackOrdersData> trackOrdersData;
    private LinearLayout numberOfItems,deliveryDate,totalAmount,totalBalance;
    private CustomTextView numberOfItemsValue,deliveryDateValue,totalAmountValue,totalBalanceValue;
    private CustomTextView numberOfItemsStaticText,deliveryDateStaticText,totalAmountStaticText,totalBalanceStaticText;
    private ImageView progressIndicator;

    public TrackOrdersRecyclerAdapter(Context context, ArrayList<TrackOrdersData> trackOrdersData) {
        this.context = context;
        this.trackOrdersData = trackOrdersData;

        Log.d(Constants.LOG_TAG, Constants.TrackOrderRecyclerAdapter);
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

        progressIndicator = (ImageView) holder.v.findViewById(R.id.progress_indicator_image_track_order);

        numberOfItems = (LinearLayout) holder.v.findViewById(R.id.number_of_items_included);
        deliveryDate = (LinearLayout) holder.v.findViewById(R.id.delivery_date_included);
        totalAmount = (LinearLayout) holder.v.findViewById(R.id.total_amount_included);
        totalBalance = (LinearLayout) holder.v.findViewById(R.id.total_balance_included);

        numberOfItemsValue = (CustomTextView) numberOfItems.findViewById(R.id.field_value_text_included);
        deliveryDateValue = (CustomTextView) deliveryDate.findViewById(R.id.field_value_text_included);
        totalAmountValue = (CustomTextView) totalAmount.findViewById(R.id.field_value_text_included);
        totalBalanceValue = (CustomTextView) totalBalance.findViewById(R.id.field_value_text_included);

        numberOfItemsStaticText = (CustomTextView) numberOfItems.findViewById(R.id.field_static_text);
        deliveryDateStaticText = (CustomTextView) deliveryDate.findViewById(R.id.field_static_text);
        totalAmountStaticText = (CustomTextView) totalAmount.findViewById(R.id.field_static_text);
        totalBalanceStaticText = (CustomTextView) totalBalance.findViewById(R.id.field_static_text);



    }

    private void setViews(int position){

//        progressIndicator.setImageResource();

        numberOfItemsStaticText.setText("Number of Items");
        deliveryDateStaticText.setText("Delivery Date");
        totalAmountStaticText.setText("Total Amount");
        totalBalanceStaticText.setText("Total Balance");


        numberOfItemsValue.setText("12");
        deliveryDateValue.setText(Constants.trackOrdersData.get(position).getDeliveryDate());
        totalAmountValue.setText(Constants.trackOrdersData.get(position).getPrice());
//        totalBalanceValue.setText(Constants.trackOrdersData.get(position).getTotalBalance());
        totalBalanceValue.setText("0");



    }

    @Override
    public int getItemCount() {
        return trackOrdersData.size();
    }


}
