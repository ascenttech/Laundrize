package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ascenttechnovation.laundrize.data.NavigationDrawerData;

import java.util.ArrayList;

/**
 * Created by ADMIN on 29-06-2015.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    Context context;
    ArrayList<NavigationDrawerData> navigationDrawerData;

    public NavigationDrawerAdapter(Context context, ArrayList<NavigationDrawerData> navigationDrawerData) {
        this.context = context;
        this.navigationDrawerData = navigationDrawerData;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
