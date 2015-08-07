package com.ascenttechnovation.laundrize.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.Calendar;

/**
 * Created by ADMIN on 31-07-2015.
 */
public class QuickOrderFragment extends Fragment {

    View v;
    private Dialog dialog;
    private CheckBox ironing,washing,bags;
    private CardView ironingLayout,washingLayout,bagsLayout,collectionLayout;
    private CustomButton done,newOrder,placeOrder;
    private ActionBar actionBar;
    private CustomTextView ironingTitleText,ironingDateText,washingTitleText,washingDateText,bagsTitleText,bagsDateText,collectionTitleText,collectionDateText;
    private Spinner collectionTimeSlot, ironingTimeSlot, washingTimeSlot, bagsTimeSlot;
    private DatePickerDialog pickDate;
    private int date,month,year;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_quick_order,container,false);

        Log.d(Constants.LOG_TAG, Constants.QuickOrderFragement);

        customActionBar();
        popUp();
        findViews();
        setViews();

        return v;
    }

    public void popUp(){

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_quick_order);
        dialog.setTitle("Select The Services");
        dialog.setCancelable(false);
        dialog.show();


        ironing = (CheckBox) dialog.findViewById(R.id.quick_service_ironing);
        ironing.setOnClickListener(listener);

        washing = (CheckBox) dialog.findViewById(R.id.quick_service_washing);
        washing.setOnClickListener(listener);

        bags = (CheckBox) dialog.findViewById(R.id.quick_service_bags);
        bags.setOnClickListener(listener);

        done = (CustomButton) dialog.findViewById(R.id.quick_service_done);
        done.setOnClickListener(listener);

    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Quick Order");

    }

    private void findViews(){

        collectionLayout = (CardView) v.findViewById(R.id.collection_layout_quick_fragment);
        collectionTitleText = (CustomTextView) collectionLayout.findViewById(R.id.service_included);
        collectionDateText = (CustomTextView) collectionLayout.findViewById(R.id.select_date_slot_included);
        collectionTimeSlot = (Spinner) collectionLayout.findViewById(R.id.select_time_slot_included);

        ironingLayout = (CardView) v.findViewById(R.id.ironing_layout_quick_fragment);
        ironingTitleText = (CustomTextView) ironingLayout.findViewById(R.id.service_included);
        ironingDateText = (CustomTextView) ironingLayout.findViewById(R.id.select_date_slot_included);
        ironingTimeSlot = (Spinner) ironingLayout.findViewById(R.id.select_time_slot_included);


        washingLayout = (CardView) v.findViewById(R.id.washing_layout_quick_fragment);
        washingTitleText = (CustomTextView) washingLayout.findViewById(R.id.service_included);
        washingDateText = (CustomTextView) washingLayout.findViewById(R.id.select_date_slot_included);
        washingTimeSlot = (Spinner) washingLayout.findViewById(R.id.select_time_slot_included);

        bagsLayout = (CardView) v.findViewById(R.id.bags_layout_quick_fragment);
        bagsTitleText = (CustomTextView) bagsLayout.findViewById(R.id.service_included);
        bagsDateText = (CustomTextView) bagsLayout.findViewById(R.id.select_date_slot_included);
        bagsTimeSlot = (Spinner) bagsLayout.findViewById(R.id.select_time_slot_included);

        newOrder = (CustomButton) v.findViewById(R.id.left_button_included);
        placeOrder = (CustomButton) v.findViewById(R.id.right_button_included);

    }

    private void setViews(){

        collectionDateText.setTag("date_1");
        collectionDateText.setOnClickListener(datelistener);

        ironingDateText.setTag("date_2");
        ironingDateText.setOnClickListener(datelistener);

        washingDateText.setTag("date_3");
        washingDateText.setOnClickListener(datelistener);

        bagsDateText.setTag("date_4");
        bagsDateText.setOnClickListener(datelistener);

        setSpinner();

        ironingLayout.setVisibility(View.GONE);
        washingLayout.setVisibility(View.GONE);
        bagsLayout.setVisibility(View.GONE);

        collectionTitleText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_collection,0,0,0);

        collectionTitleText.setText("Collection");
        ironingTitleText.setText("Delivery : Ironing");
        washingTitleText.setText("Delivery : Washables");
        bagsTitleText.setText("Delivery : Bags & Shoes");

        newOrder.setText("NEW ORDER");
        newOrder.setOnClickListener(listener);

        placeOrder.setText("PLACE ORDER");
        placeOrder.setOnClickListener(listener);


    }
    private void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.time_slot, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collectionTimeSlot.setAdapter(adapter);
        ironingTimeSlot.setAdapter(adapter);
        washingTimeSlot.setAdapter(adapter);
        bagsTimeSlot.setAdapter(adapter);
    }

    private void collectionDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        pickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth)
                    collectionDateText.setText("Today");
                else
                    collectionDateText.setText(dayOfMonth+"-"+monthOfYear+"-"+yearofc);
            }

        },year, month, date);
        pickDate.show();
    }

    private void ironingDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        pickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth)
                    ironingDateText.setText("Today");
                else
                    ironingDateText.setText(dayOfMonth+"-"+monthOfYear+"-"+yearofc);
            }

        },year, month, date);
        pickDate.show();
    }
    private void washingDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        pickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth)
                    washingDateText.setText("Today");
                else
                    washingDateText.setText(dayOfMonth+"-"+monthOfYear+"-"+yearofc);
            }

        },year, month, date);
        pickDate.show();
    }
    private void bagsDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        pickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth)
                    bagsDateText.setText("Today");
                else
                    bagsDateText.setText(dayOfMonth+"-"+monthOfYear+"-"+yearofc);
            }

        },year, month, date);
        pickDate.show();
    }

    public void newOrder(){

        replaceFragment(new LandingFragment());

    }

    public void placeOrder(){

        // we need to send a json from here
        replaceFragment(new LandingFragment());

    }

    public void replaceFragment(Fragment fragment){

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();

    }

    View.OnClickListener datelistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getTag().toString()) {

                case "date_1":
                    collectionDatePicker();
                    break;
                case "date_2":
                    ironingDatePicker();
                    break;
                case "date_3":
                    washingDatePicker();
                    break;
                case "date_4":
                    bagsDatePicker();
                    break;
            }
        }
    };

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.quick_service_ironing:
                    if(ironing.isChecked()){
                        ironingLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        ironingLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.quick_service_washing:
                    if(washing.isChecked()){
                        washingLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        washingLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.quick_service_bags:
                    if(bags.isChecked()){
                        bagsLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        bagsLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.quick_service_done: dialog.dismiss();
                    break;
                case R.id.left_button_included : newOrder();
                    break;
                case R.id.right_button_included : placeOrder();
                    break;


            }

        }
    };


}
