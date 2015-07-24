package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.BagLaundryData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class BagLaundryRecyclerAdapter extends RecyclerView.Adapter<BagLaundryRecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<BagLaundryData> bagLaundryData;
    private TextView title,description,price,quantity;
    private ImageView add,subtract;

    public BagLaundryRecyclerAdapter(Context context, ArrayList<BagLaundryData> bagLaundryData) {
        this.context = context;
        this.bagLaundryData = bagLaundryData;
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
                .inflate(R.layout.row_data_holder, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        findViews(holder);
        setViews(position);
    }

    private void findViews(ViewHolder holder){

        title = (TextView) holder.v.findViewById(R.id.title_text_included);
        description = (TextView)holder.v.findViewById(R.id.description_text_included);
        price = (TextView)holder.v.findViewById(R.id.price_text_included);
        quantity = (TextView)holder.v.findViewById(R.id.quantity_text_included);
        add = (ImageView) holder.v.findViewById(R.id.add_image_included);
        subtract = (ImageView) holder.v.findViewById(R.id.subtract_image_included);

    }

    private void setViews(int position){

        title.setText(Constants.bagLaundryData.get(position).getTitle());
        description.setText(Constants.bagLaundryData.get(position).getDescription());
        price.setText(Constants.bagLaundryData.get(position).getPrice());
        quantity.setText(Constants.bagLaundryData.get(position).getQuantity());

        add.setTag("add_"+position);
        add.setOnClickListener(listener);

        subtract.setTag("subtract_"+position);
        subtract.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return bagLaundryData.size();
    }

    public void add(int position){

        int value = Integer.parseInt(Constants.bagLaundryData.get(position).getQuantity());
        value++;
        String quantity = String.valueOf(value);
        Constants.bagLaundryData.get(position).setQuantity(quantity);

    }

    public void subtract(int position){

        int value = Integer.parseInt(Constants.bagLaundryData.get(position).getQuantity());
        if(value!=0){

            value--;
            String quantity = String.valueOf(value);
            Constants.bagLaundryData.get(position).setQuantity(quantity);
        }

    }

    View.OnClickListener listener = new View.OnClickListener() {
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
