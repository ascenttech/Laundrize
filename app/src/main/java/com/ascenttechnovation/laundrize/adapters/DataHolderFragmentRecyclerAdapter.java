package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.GeneralData;

import java.util.ArrayList;

/**
 * Created by ADMIN on 13-07-2015.
 */
public class DataHolderFragmentRecyclerAdapter extends RecyclerView.Adapter<DataHolderFragmentRecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<GeneralData> data;
    ImageView backgroundImage;

    public DataHolderFragmentRecyclerAdapter(Context context) {
        this.context = context;
    }

    public DataHolderFragmentRecyclerAdapter(Context context, ArrayList<GeneralData> data) {
        this.context = context;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View v;
        public ViewHolder(View view) {
            super(view);

            v = view;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        findViews(holder);
        setViews(position);

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void findViews(ViewHolder holder){

        backgroundImage = (ImageView) holder.v.findViewById(R.id.background_image_row_order);

    }

    public void setViews(int position){

        backgroundImage.setTag("position_"+position);
        backgroundImage.setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String tag = view.getTag().toString();
            String position[] = tag.split("_");

            switch (Integer.parseInt(position[1])){

                case 0 : new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        backgroundImage.setBackgroundColor(R.color.text_color_white);
                    }
                },1000);
                    break;

            }

        }
    };

}
