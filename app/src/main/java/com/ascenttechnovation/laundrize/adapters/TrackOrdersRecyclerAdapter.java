package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.data.TrackOrdersData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class TrackOrdersRecyclerAdapter extends RecyclerView.Adapter<TrackOrdersRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TrackOrdersData> trackOrdersData;
    private LinearLayout numberOfItems,deliveryDate, deliverySlot, totalAmount;
    private CustomTextView numberOfItemsValue,deliveryDateValue, deliverySlotValue, totalAmountValue,title;
    private CustomTextView numberOfItemsStaticText,deliveryDateStaticText, deliverySlotStaticText, totalAmountStaticText;
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
        deliverySlot = (LinearLayout) holder.v.findViewById(R.id.delivery_slot_included);
        totalAmount = (LinearLayout) holder.v.findViewById(R.id.total_amount_included);

        title = (CustomTextView) holder.v.findViewById(R.id.status_static_text_track_order_fragment);
        numberOfItemsValue = (CustomTextView) numberOfItems.findViewById(R.id.field_value_text_included);
        deliveryDateValue = (CustomTextView) deliveryDate.findViewById(R.id.field_value_text_included);
        deliverySlotValue = (CustomTextView) deliverySlot.findViewById(R.id.field_value_text_included);
        totalAmountValue = (CustomTextView) totalAmount.findViewById(R.id.field_value_text_included);

        numberOfItemsStaticText = (CustomTextView) numberOfItems.findViewById(R.id.field_static_text);
        deliveryDateStaticText = (CustomTextView) deliveryDate.findViewById(R.id.field_static_text);
        deliverySlotStaticText = (CustomTextView) deliverySlot.findViewById(R.id.field_static_text);
        totalAmountStaticText = (CustomTextView) totalAmount.findViewById(R.id.field_static_text);



    }

    private void setViews(int position){

        if(Constants.trackOrdersData.get(position).getOrderProgress() == 1){
            progressIndicator.setImageResource(R.drawable.track_progress_1);
        }
        else if(Constants.trackOrdersData.get(position).getOrderProgress() == 2){
            progressIndicator.setImageResource(R.drawable.track_progress_2);
        }
        else if(Constants.trackOrdersData.get(position).getOrderProgress() == 3){
            progressIndicator.setImageResource(R.drawable.track_progress_3);
        }
        else if(Constants.trackOrdersData.get(position).getOrderProgress() == 4){
            progressIndicator.setImageResource(R.drawable.track_progress_4);
        }

        numberOfItemsStaticText.setText("Number of Items");
        deliveryDateStaticText.setText("Delivery Date");
        deliverySlotStaticText.setText("Delivery Slot");
        totalAmountStaticText.setText("Total Amount");

        title.setText(Constants.trackOrdersData.get(position).getTypeOfService()+" | "+ Constants.trackOrdersData.get(position).getOrderId());

        numberOfItemsValue.setText(Constants.trackOrdersData.get(position).getQuantity());
        deliveryDateValue.setText(Constants.trackOrdersData.get(position).getDeliveryDate());

        deliverySlotValue.setText(getTheKey(Constants.trackOrdersData.get(position).getDeliverySlot()));
        totalAmountValue.setText(Constants.trackOrdersData.get(position).getPrice());



    }

    @Override
    public int getItemCount() {
        return trackOrdersData.size();
    }

    public String getTheKey(String value) {

        for (String key : Constants.slots.keySet()) {
            if (Constants.slots.get(key).equals(value)) {
                Log.d(Constants.LOG_TAG," Returning the key "+key);
                return key;
            }
        }

        return "NA";
    }
}
