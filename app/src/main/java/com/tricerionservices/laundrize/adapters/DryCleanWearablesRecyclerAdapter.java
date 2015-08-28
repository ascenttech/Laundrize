package com.tricerionservices.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.custom.CustomTextView;
import com.tricerionservices.laundrize.data.GeneralData;
import com.tricerionservices.laundrize.imageloader.ImageLoader;
import com.tricerionservices.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class DryCleanWearablesRecyclerAdapter extends RecyclerView.Adapter<DryCleanWearablesRecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<GeneralData> dryCleanWearablesData;
    private CustomTextView title,description,price,quantity;
    private ImageView add,subtract,backgroundImage;
    private ImageLoader imgLoader;

    public DryCleanWearablesRecyclerAdapter(Context context, ArrayList<GeneralData> dryCleanWearablesData) {
        this.context = context;
        this.dryCleanWearablesData = dryCleanWearablesData;
        imgLoader = new ImageLoader(context);

        Log.d(Constants.LOG_TAG, Constants.DryCleanWearablesRecyclerAdapter);
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

        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_data_holder,parent,false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        findViews(holder);
        setViews(position);

    }


    private void findViews(ViewHolder holder){

        backgroundImage = (ImageView) holder.v.findViewById(R.id.background_image_row_order);
        title = (CustomTextView) holder.v.findViewById(R.id.title_text_included);
        description = (CustomTextView) holder.v.findViewById(R.id.description_text_included);
        price = (CustomTextView) holder.v.findViewById(R.id.price_text_included);
        quantity = (CustomTextView) holder.v.findViewById(R.id.quantity_text_included);
        add = (ImageView) holder.v.findViewById(R.id.add_image_included);
        subtract = (ImageView) holder.v.findViewById(R.id.subtract_image_included);

    }

    private void setViews(int position){

        imgLoader.DisplayImage(Constants.dryCleanWearablesData.get(position).getLargeImage(),backgroundImage);
        title.setText(Constants.dryCleanWearablesData.get(position).getTitle());
        description.setText(Constants.dryCleanWearablesData.get(position).getDescription());
        price.setText(Constants.dryCleanWearablesData.get(position).getRegularCost());
        quantity.setText(Constants.dryCleanWearablesData.get(position).getQuantity());

        add.setTag("add_"+position);
        add.setOnClickListener(listener);

        subtract.setTag("subtract_"+position);
        subtract.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return dryCleanWearablesData.size();
    }

    private void add(int position){

        int value = Integer.parseInt(Constants.dryCleanWearablesData.get(position).getQuantity());
        value++;
        String quantity = String.valueOf(value);
        Constants.dryCleanWearablesData.get(position).setQuantity(quantity);

        int numberOfPieces = Integer.parseInt(Constants.dryCleanWearablesData.get(position).getQuantity());
        int price = Integer.parseInt(Constants.dryCleanWearablesData.get(position).getRegularCost());
        int totalAmount = numberOfPieces * price;
        String totalAmountValue = String.valueOf(totalAmount);
        String total = Constants.dryCleanWearablesData.get(position).getQuantity()+"_"+totalAmountValue;

        String orderId = Constants.dryCleanWearablesData.get(position).getCode();
        Constants.order.put(orderId,total);

    }

    private void subtract(int position){

        int value = Integer.parseInt(Constants.dryCleanWearablesData.get(position).getQuantity());
        if(value !=0){

            value--;
            String quantity = String.valueOf(value);
            Constants.dryCleanWearablesData.get(position).setQuantity(quantity);

            int numberOfPieces = Integer.parseInt(Constants.dryCleanWearablesData.get(position).getQuantity());
            int price = Integer.parseInt(Constants.dryCleanWearablesData.get(position).getRegularCost());
            int totalAmount = numberOfPieces * price;
            String totalAmountValue = String.valueOf(totalAmount);
            String total = Constants.dryCleanWearablesData.get(position).getQuantity()+"_"+totalAmountValue;

            String orderId = Constants.dryCleanWearablesData.get(position).getCode();
            Constants.order.put(orderId,total);

        }
        else if(value == 0){

            String orderId = Constants.dryCleanHouseholdsData.get(position).getCode();
            Constants.order.remove(orderId);

        }

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String tag = view.getTag().toString();
            String pos[] = tag.split("_");
            int position = Integer.parseInt(pos[1]);

            switch (view.getId()){


                case R.id.add_image_included: add(position);
                    notifyDataSetChanged();
                    break;
                case R.id.subtract_image_included: subtract(position);
                    notifyDataSetChanged();
                    break;


            }
        }
    };
}
