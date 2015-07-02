package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.NavigationDrawerData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 29-06-2015.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    Context context;
    ArrayList<NavigationDrawerData> navigationDrawerData;
    View v;
    TextView menuItemName;
    ImageView menuItemLogo;

    public NavigationDrawerAdapter(Context context, ArrayList<NavigationDrawerData> navigationDrawerData) {
        this.context = context;
        this.navigationDrawerData = navigationDrawerData;

        Log.d(Constants.LOG_TAG,Constants.NavigationDrawerAdapter);

    }

    @Override
    public int getCount() {
        return navigationDrawerData.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationDrawerData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.row_navigation_drawer,null);

        findViews();
        setViews(position);

        return v;
    }

    private void findViews(){

        menuItemLogo = (ImageView) v.findViewById(R.id.menu_logo_image_navigation_drawer);
        menuItemName = (TextView) v.findViewById(R.id.menu_item_text_navigation_drawer);
    }

    private void setViews(int position){

        menuItemLogo.setImageResource(navigationDrawerData.get(position).getMenuItemLogo());
        menuItemName.setText(navigationDrawerData.get(position).getMenuItemName());

    }
}
