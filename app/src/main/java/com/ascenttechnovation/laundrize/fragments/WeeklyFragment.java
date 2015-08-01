package com.ascenttechnovation.laundrize.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.Calendar;

/**
 * Created by ADMIN on 23-07-2015.
 */
public class WeeklyFragment extends Fragment {

    private Button back,placeOrder;
    private ProgressDialog progressDialog;
    private Spinner timeSlot;
    private CustomTextView dateText;
    private DatePickerDialog Pickdate;
    private int date,month,year;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG, Constants.WeeklyFragement);

        View v = inflater.inflate(R.layout.fragment_weekly,container,false);
        findViews(v);
        setViews();

        return v;
    }

    private void findViews(View v){

        back = (Button) v.findViewById(R.id.left_button_included);
        placeOrder = (Button) v.findViewById(R.id.right_button_included);
        dateText = (CustomTextView) v.findViewById(R.id.select_date_slot_included);
        timeSlot = (Spinner) v.findViewById(R.id.select_time_slot_included);
    }

    private void setViews(){

        back.setText("BACK");
        back.setOnClickListener(listener);

        placeOrder.setText("PLACE ORDER");
        placeOrder.setOnClickListener(listener);

        dateText.setOnClickListener(listener);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.time_slot, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSlot.setAdapter(adapter);

    }

    public void back(){

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new AddressFragment("weekly"))
                .commit();

    }

    public void placeOrder(){

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new LandingFragment())
                .commit();


//        new PlaceWeeklyOrderAsyncTask(getActivity().getApplicationContext(),new PlaceWeeklyOrderAsyncTask.PlaceWeeklyOrderCallback() {
//            @Override
//            public void onStart(boolean status) {
//
//                progressDialog = new ProgressDialog(getActivity());
//                progressDialog.setTitle(Constants.LOG_TAG);
//                progressDialog.setMessage("Placing your order");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//            }
//            @Override
//            public void onResult(boolean result) {
//
//                progressDialog.dismiss();
//
//            }
//        });

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){

                case R.id.left_button_included: back();
                    break;
                case R.id.right_button_included: placeOrder();
                    break;
                case R.id.select_date_slot_included: dateset();
                    break;

            }

        }
    };

    public void dateset() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        Pickdate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth)
                    dateText.setText("Today");
                else
                    dateText.setText(dayOfMonth+"-"+monthOfYear+"-"+yearofc);
            }

        },year, month, date);
        Pickdate.show();
    }

}
