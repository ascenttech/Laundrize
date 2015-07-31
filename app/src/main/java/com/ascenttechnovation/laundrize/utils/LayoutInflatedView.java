package com.ascenttechnovation.laundrize.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;

/**
 * Created by ADMIN on 31-07-2015.
 */
public class LayoutInflatedView extends LinearLayout {

    TextView address,mobileNumber;

    public LayoutInflatedView(Context context, ViewGroup v)
    {
        super(context);
        LayoutInflater layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.include_available_address,v);
        address = (TextView)findViewById(R.id.address_text_included);
        mobileNumber = (TextView) findViewById(R.id.mobile_number_text_included);
    }
    public void setUserName(String address,String mobileNumber)
    {
        this.address.setText(address);
        this.mobileNumber.setText(mobileNumber);
    }


}
