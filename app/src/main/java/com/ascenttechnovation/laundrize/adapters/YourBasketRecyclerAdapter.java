package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ascenttechnovation.laundrize.data.YourBasketData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 09-07-2015.
 */
public class YourBasketRecyclerAdapter extends RecyclerView.Adapter<YourBasketRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<YourBasketData> yourBasketData;

    public YourBasketRecyclerAdapter(Context context, ArrayList<YourBasketData> yourBasketData) {
        this.context = context;
        this.yourBasketData = yourBasketData;
        Log.d(Constants.LOG_TAG,Constants.YourbasketRecyclerAdapter);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
//        return yourBasketData.size();
    }


}
