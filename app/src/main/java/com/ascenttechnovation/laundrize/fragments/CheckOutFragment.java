package com.ascenttechnovation.laundrize.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 25-07-2015.
 */
public class CheckOutFragment extends Fragment {

    private ActionBar actionBar;
    private ViewGroup addHere;
    private LayoutInflater layoutInflater;
    private Button back,placeOrder;
    private View rowView;
    private TextView date,timeSlot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_checkout,container,false);
        layoutInflater = inflater;

        customActionBar();
        findViews(v);
        setViews();

        Log.d(Constants.LOG_TAG, Constants.CheckOutFragement);

        return v;
    }
    public void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Check Out");

    }

    private void findViews(View v){

      addHere = (ViewGroup) v.findViewById(R.id.container_for_views_checkout_fragment);
      back = (Button) v.findViewById(R.id.left_button_included);
      placeOrder = (Button) v.findViewById(R.id.right_button_included);

    }
    private void setViews(){

        back.setText("BACK");
        back.setOnClickListener(listener);

        placeOrder.setText("PLACE ORDER");
        placeOrder.setOnClickListener(listener);

        for(int i =0;i<3;i++){

           rowView = layoutInflater.inflate(R.layout.row_fragment_checkout,addHere);
           date = (TextView) rowView.findViewById(R.id.select_date_slot_included);
           date.setTag("date_"+i);
           date.setOnClickListener(slotListener);

           timeSlot = (TextView) rowView.findViewById(R.id.select_time_slot_included);
           timeSlot.setTag("time_"+i);
           timeSlot.setOnClickListener(slotListener);
        }

    }

    public void back(){

    }

    public void placeOrder(){


    }

    public void selectDate(){

//        DialogFragment newFragment = new DatePickerDialogFragment();
//        newFragment.show(getFragmentManager(), "DatePicker");

    }
    public void selectTime(){


    }

    View.OnClickListener slotListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String tag = view.getTag().toString();
            String pos[] = tag.split("_");
            int position = Integer.parseInt(pos[1]);

            switch(view.getId()){


                case R.id.select_date_slot_included: selectDate();
                    Log.d(Constants.LOG_TAG," Position "+ position);
                    break;
                case R.id.select_time_slot_included: selectTime();
                    Log.d(Constants.LOG_TAG," Position "+ position);
                    break;


            }

        }
    };

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.left_button_included: back();
                    break;

                case R.id.right_button_included: placeOrder();
                    break;


            }
        }
    };

}
