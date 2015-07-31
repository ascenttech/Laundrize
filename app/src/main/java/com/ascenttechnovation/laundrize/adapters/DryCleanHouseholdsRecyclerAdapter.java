package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.GeneralData;
import com.ascenttechnovation.laundrize.imageloader.ImageLoader;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class DryCleanHouseholdsRecyclerAdapter extends RecyclerView.Adapter<DryCleanHouseholdsRecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<GeneralData> dryCleanHouseholdsData;
    private TextView title,description,price,quantity;
    private ImageView add,subtract;
    private ImageLoader imgLoader;
    private ImageView backgroundImage;

    public DryCleanHouseholdsRecyclerAdapter(Context context, ArrayList<GeneralData> dryCleanHouseholdsData) {
        this.context = context;
        this.dryCleanHouseholdsData = dryCleanHouseholdsData;
        imgLoader = new ImageLoader(context);

        Log.d(Constants.LOG_TAG, Constants.DryCleanHouseholdsRecyclerAdapter);
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
        title = (TextView) holder.v.findViewById(R.id.title_text_included);
        description = (TextView) holder.v.findViewById(R.id.description_text_included);
        price = (TextView) holder.v.findViewById(R.id.price_text_included);
        quantity = (TextView) holder.v.findViewById(R.id.quantity_text_included);
        add = (ImageView) holder.v.findViewById(R.id.add_image_included);
        subtract = (ImageView) holder.v.findViewById(R.id.subtract_image_included);

    }

    private void setViews(int position){

        imgLoader.DisplayImage(Constants.ironingWearablesData.get(position).getLargeImage(),backgroundImage);
        title.setText(Constants.dryCleanHouseholdsData.get(position).getTitle());
        description.setText(Constants.dryCleanHouseholdsData.get(position).getDescription());
        price.setText(Constants.dryCleanHouseholdsData.get(position).getRegularCost());
        quantity.setText(Constants.dryCleanHouseholdsData.get(position).getQuantity());

        add.setTag("add_"+position);
        add.setOnClickListener(listener);

        subtract.setTag("subtract_"+position);
        subtract.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return dryCleanHouseholdsData.size();
    }

    private void add(int position){

        int value = Integer.parseInt(Constants.dryCleanHouseholdsData.get(position).getQuantity());
        value++;
        String quantity = String.valueOf(value);
        Constants.dryCleanHouseholdsData.get(position).setQuantity(quantity);
        String orderId = Constants.dryCleanHouseholdsData.get(position).getCode();
        Constants.order.put(orderId,quantity);

    }

    private void subtract(int position){

        int value = Integer.parseInt(Constants.dryCleanHouseholdsData.get(position).getQuantity());
        if(value !=0){

            value--;
            String quantity = String.valueOf(value);
            Constants.dryCleanHouseholdsData.get(position).setQuantity(quantity);
            String orderId = Constants.dryCleanHouseholdsData.get(position).getCode();
            Constants.order.put(orderId,quantity);

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
