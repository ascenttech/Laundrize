package com.tricerionservices.laundrize.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12-08-2015.
 */
public class CustomCityAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> generalAddressRelatedDatas;

    public CustomCityAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        generalAddressRelatedDatas = (ArrayList<String>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent){
//
////        LayoutInflater inflater = context.getLayoutInflater();
//        View mySpinner = inflater.inflate(R.layout.row_spinner_layout, parent, false);
//
//        TextView main_text = (TextView) mySpinner .findViewById(R.id.row_text_spinner);
////        main_text.setText(generalAddressRelatedDatas.get(position).getField());
//        return mySpinner;

        return null;
    }

}
