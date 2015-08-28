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
import com.tricerionservices.laundrize.data.NavigationDrawerData;
import com.tricerionservices.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11-08-2015.
 */
public class NavigationDrawerRecyclerAdapter extends RecyclerView.Adapter<NavigationDrawerRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<NavigationDrawerData> navigationDrawerData;
    CustomTextView menuItemName;
    ImageView menuItemLogo;

    public NavigationDrawerRecyclerAdapter(Context context, ArrayList<NavigationDrawerData> navigationDrawerData) {
        this.context = context;
        this.navigationDrawerData = navigationDrawerData;

        Log.d(Constants.LOG_TAG, Constants.NavigationDrawerAdapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View v;
        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_navigation_drawer, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        findViews(viewHolder);
        setViews(position);
    }

    @Override
    public int getItemCount() {
        return navigationDrawerData.size();
    }

    private void findViews(ViewHolder viewHolder){

        menuItemLogo = (ImageView) viewHolder.v.findViewById(R.id.menu_logo_image_navigation_drawer);
        menuItemName = (CustomTextView) viewHolder.v.findViewById(R.id.menu_item_text_navigation_drawer);
    }

    private void setViews(int position){

        menuItemLogo.setImageResource(navigationDrawerData.get(position).getMenuItemLogo());
        menuItemName.setText(navigationDrawerData.get(position).getMenuItemName());

    }

}