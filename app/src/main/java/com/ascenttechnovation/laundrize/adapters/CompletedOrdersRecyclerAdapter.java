package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.data.CompletedOrdersData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class CompletedOrdersRecyclerAdapter extends RecyclerView.Adapter<CompletedOrdersRecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<CompletedOrdersData> completedOrdersData;
    private LinearLayout numberOfItems,deliveryDate, deliverySlot, totalAmount;
    private CustomTextView numberOfItemsValue,deliveryDateValue, deliverySlotValue, totalAmountValue,title;
    private CustomTextView numberOfItemsStaticText,deliveryDateStaticText, deliverySlotStaticText, totalAmountStaticText;

    public CompletedOrdersRecyclerAdapter(Context context) {
        this.context = context;
    }

    public CompletedOrdersRecyclerAdapter(Context context, ArrayList<CompletedOrdersData> completedOrdersData) {
        this.context = context;
        this.completedOrdersData = completedOrdersData;

        Log.d(Constants.LOG_TAG, Constants.CompleteOrdersRecyclerAdapter);
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
                .inflate(R.layout.row_completed_order, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        findViews(holder);
        setViews(position);
    }

    private void findViews(ViewHolder holder){

        numberOfItems = (LinearLayout) holder.v.findViewById(R.id.number_of_items_included);
        deliveryDate = (LinearLayout) holder.v.findViewById(R.id.delivery_date_included);
        deliverySlot = (LinearLayout) holder.v.findViewById(R.id.delivery_slot_included);
        totalAmount = (LinearLayout) holder.v.findViewById(R.id.total_amount_included);

        title = (CustomTextView) holder.v.findViewById(R.id.static_text_completed_order_fragment);
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

        numberOfItemsStaticText.setText("Number of Items");
        deliveryDateStaticText.setText("Delivered Date");
        deliverySlotStaticText.setText("Delivered Time");
        totalAmountStaticText.setText("Total Amount");

        title.setText(Constants.completedOrdersData.get(position).getTypeOfService()+" | "+ Constants.completedOrdersData.get(position).getOrderId());

        numberOfItemsValue.setText(Constants.completedOrdersData.get(position).getQuantity());
        deliveryDateValue.setText(Constants.completedOrdersData.get(position).getDeliveryDate());
        deliverySlotValue.setText(getTheKey(Constants.completedOrdersData.get(position).getDeliverySlot()));
        totalAmountValue.setText(Constants.completedOrdersData.get(position).getPrice());

    }

    @Override
    public int getItemCount() {

        return completedOrdersData.size();
    }

    public String getTheKey(String value) {

        for (String key : Constants.getSlotsId.keySet()) {
            if (Constants.getSlotsId.get(key).equals(value)) {
                Log.d(Constants.LOG_TAG," Returning the key "+key);
                return key;
            }
        }

        return "NA";
    }

}
