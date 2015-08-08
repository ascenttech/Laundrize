package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.data.CompletedOrdersData;
import com.ascenttechnovation.laundrize.data.LaundryServicesSubCategoryData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class CompletedOrdersRecyclerAdapter extends RecyclerView.Adapter<CompletedOrdersRecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<CompletedOrdersData> completedOrdersData;
    private LinearLayout numberOfItems,deliveryDate,totalAmount,totalBalance;
    private CustomTextView numberOfItemsValue,deliveryDateValue,totalAmountValue,totalBalanceValue;
    private CustomTextView numberOfItemsStaticText,deliveryDateStaticText,totalAmountStaticText,totalBalanceStaticText;

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

        numberOfItemsStaticText.setText("Number of Items");
        deliveryDateStaticText.setText("Delivery Date");
        totalAmountStaticText.setText("Total Amount");
        totalBalanceStaticText.setText("Total Balance");

        numberOfItemsValue.setText(Constants.completedOrdersData.get(position).getQuantity());
        deliveryDateValue.setText(Constants.completedOrdersData.get(position).getDeliveryDate());
        totalAmountValue.setText(Constants.completedOrdersData.get(position).getPrice());
        totalBalanceValue.setText("0");

    }

    @Override
    public int getItemCount() {
        return completedOrdersData.size();
    }



}
