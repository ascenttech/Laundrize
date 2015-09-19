package com.tricerionservices.laundrize.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.activities.LandingActivity;
import com.tricerionservices.laundrize.async.FetchCurrentServerTimeAsyncTask;
import com.tricerionservices.laundrize.async.FetchSlotDifferenceAsyncTask;
import com.tricerionservices.laundrize.async.PlaceOrderAsyncTask;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.custom.CustomTextView;
import com.tricerionservices.laundrize.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ADMIN on 31-07-2015.
 */
public class QuickOrderFragment extends Fragment {

    View v;
    private CardView collectionLayout;
    private CustomTextView collectionTitleText,collectionDateText;
    private Spinner collectionTimeSlot;
    private DatePickerDialog collectionDatePicker;
    private CustomTextView collectionTimeSlotText;
    private ArrayAdapter<String> collectionAdapter;


    private CardView ironingLayout;
    private CheckBox ironing;
    private boolean ironingCreated = false;
    private CustomTextView ironingTitleText,ironingDateText;
    private Spinner ironingTimeSlot;
    private DatePickerDialog ironingDatePicker;
    private CustomTextView ironingTimeSlotText;
    private ArrayAdapter<String> ironingAdapter;
    private JSONObject ironingNestedJsonObject,ironingJsonObject;
    private JSONArray ironingNestedJsonArray;
    private ArrayList<String> minimumIroningSlots;


    private CardView washingLayout;
    private CheckBox washing;
    private boolean washingCreated = false;
    private CustomTextView washingTitleText,washingDateText;
    private Spinner washingTimeSlot;
    private DatePickerDialog washingDatePicker;
    private CustomTextView washingTimeSlotText;
    private ArrayAdapter<String> washingAdapter;
    private JSONObject washingNestedJsonObject,washingJsonObject;
    private JSONArray washingNestedJsonArray;
    private ArrayList<String> minimumWashingSlots;

    private CardView bagsLayout;
    private CheckBox bags;
    private boolean bagsCreated = false;
    private CustomTextView bagsTitleText,bagsDateText;
    private Spinner bagsTimeSlot;
    private DatePickerDialog bagsDatePicker;
    private CustomTextView bagsTimeSlotText;
    private ArrayAdapter<String> bagsAdapter;
    private JSONObject bagsNestedJsonObject,bagsJsonObject;
    private JSONArray bagsNestedJsonArray;
    private ArrayList<String> minimumBagsSlots;

    private CardView othersLayout;
    private CheckBox others;
    private boolean othersCreated = false;
    private CustomTextView othersTitleText,othersDateText,othersStaticText;
    private Spinner othersTimeSlot;
    private DatePickerDialog othersDatePicker;
    private CustomTextView othersTimeSlotText;
    private ArrayAdapter<String> othersAdapter;
    private JSONObject othersNestedJsonObject,othersJsonObject;
    private JSONArray othersNestedJsonArray;
    private ArrayList<String> minimumOthersSlots;

    private JSONObject postOrderJsonObject;
    private JSONArray itemsJsonArray;

    private Dialog dialog;


    private CustomButton done,newOrder,placeOrder;
    private ActionBar actionBar;
    private int date,month,year;


    private ProgressDialog progressDialog;



    // This will hold the index of the slot from the array of Collections Slots;
    private int j;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_quick_order,container,false);

        Log.d(Constants.LOG_TAG, Constants.QuickOrderFragement);

        // we are doing this because the date is saved once the session is once
        // so for multiple orders this date will have values even if you dont select
        // to avoid this we are re initializing it to null
        Constants.collectionDate = null;

        customActionBar();
        Constants.reintializeTheValues(getActivity().getApplicationContext());

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(Constants.isInternetAvailable(getActivity().getApplicationContext())){

            getServerTime();
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Internet is required for this app.",5000).show();
        }
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

        others = (CheckBox) dialog.findViewById(R.id.quick_service_others);
        others.setOnClickListener(listener);

        done = (CustomButton) dialog.findViewById(R.id.quick_service_done);
        done.setOnClickListener(listener);

    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Quick Order");

    }


    public void getServerTime(){

        new FetchCurrentServerTimeAsyncTask(new FetchCurrentServerTimeAsyncTask.FetchCurrentServerTimeCallBack() {
            @Override
            public void onStart(boolean status) {

            }

            @Override
            public void onResult(boolean result) {
                if(result){

                    Constants.currentServerTimeFetched = true;
                    String finalUrl = Constants.getSlotDifferenceUrl + Constants.userId+"&address_id="+Constants.addressId;
                    new FetchSlotDifferenceAsyncTask(new FetchSlotDifferenceAsyncTask.FetchSlotDifferenceCallback() {
                        @Override
                        public void onStart(boolean status) {


                        }
                        @Override
                        public void onResult(boolean result) {

                            if(result){

                                Constants.slotDifferenceFetched = true;
                                findViews(v);
                                setViews();
                                popUp();

                            }
                            else{

                                Constants.slotDifferenceFetched = false;
                                Toast.makeText(getActivity().getApplicationContext(),"Unable to connect to the Internet.\nTry Again Later",5000).show();
                            }

                        }
                    }).execute(finalUrl);

                }
                else{
                    Constants.currentServerTimeFetched = false;
                    Toast.makeText(getActivity().getApplicationContext(),"Unable to connect to the Internet.\nTry Again Later",5000).show();
                }
            }
        }).execute(Constants.getTimeStampUrl);


    }



    private void findViews(View v){

        collectionLayout = (CardView) v.findViewById(R.id.collection_layout_quick_fragment);
        collectionTitleText = (CustomTextView) collectionLayout.findViewById(R.id.service_included);
        collectionDateText = (CustomTextView) collectionLayout.findViewById(R.id.select_date_slot_included);
        collectionTimeSlotText = (CustomTextView) collectionLayout.findViewById(R.id.select_time_slot_text);
        collectionTimeSlot = (Spinner) collectionLayout.findViewById(R.id.select_time_slot_included);

        ironingLayout = (CardView) v.findViewById(R.id.ironing_layout_quick_fragment);
        ironingTitleText = (CustomTextView) ironingLayout.findViewById(R.id.service_included);
        ironingDateText = (CustomTextView) ironingLayout.findViewById(R.id.select_date_slot_included);
        ironingTimeSlotText = (CustomTextView) ironingLayout.findViewById(R.id.select_time_slot_text);
        ironingTimeSlot = (Spinner) ironingLayout.findViewById(R.id.select_time_slot_included);


        washingLayout = (CardView) v.findViewById(R.id.washing_layout_quick_fragment);
        washingTitleText = (CustomTextView) washingLayout.findViewById(R.id.service_included);
        washingDateText = (CustomTextView) washingLayout.findViewById(R.id.select_date_slot_included);
        washingTimeSlotText = (CustomTextView) washingLayout.findViewById(R.id.select_time_slot_text);
        washingTimeSlot = (Spinner) washingLayout.findViewById(R.id.select_time_slot_included);

        bagsLayout = (CardView) v.findViewById(R.id.bags_layout_quick_fragment);
        bagsTitleText = (CustomTextView) bagsLayout.findViewById(R.id.service_included);
        bagsDateText = (CustomTextView) bagsLayout.findViewById(R.id.select_date_slot_included);
        bagsTimeSlotText = (CustomTextView) bagsLayout.findViewById(R.id.select_time_slot_text);
        bagsTimeSlot = (Spinner) bagsLayout.findViewById(R.id.select_time_slot_included);

        othersLayout = (CardView) v.findViewById(R.id.others_layout_quick_fragment);
        othersTitleText = (CustomTextView) othersLayout.findViewById(R.id.service_included);
        othersDateText = (CustomTextView) othersLayout.findViewById(R.id.select_date_slot_included);
        othersTimeSlotText = (CustomTextView) othersLayout.findViewById(R.id.select_time_slot_text);
        othersTimeSlot = (Spinner) othersLayout.findViewById(R.id.select_time_slot_included);

        newOrder = (CustomButton) v.findViewById(R.id.left_button_included);
        placeOrder = (CustomButton) v.findViewById(R.id.right_button_included);

    }

    private void setViews(){

        ironingLayout.setVisibility(View.GONE);
        washingLayout.setVisibility(View.GONE);
        bagsLayout.setVisibility(View.GONE);
        othersLayout.setVisibility(View.GONE);

        collectionDateText.setTag("date_1");
        collectionDateText.setOnClickListener(datelistener);
        collectionTimeSlotText.setOnClickListener(toastListener);

        ironingDateText.setTag("date_2");
        ironingDateText.setOnClickListener(datelistener);
        ironingTimeSlotText.setOnClickListener(toastListener);

        washingDateText.setTag("date_3");
        washingDateText.setOnClickListener(datelistener);
        washingTimeSlotText.setOnClickListener(toastListener);

        bagsDateText.setTag("date_4");
        bagsDateText.setOnClickListener(datelistener);
        bagsTimeSlotText.setOnClickListener(toastListener);

        othersDateText.setTag("date_5");
        othersDateText.setOnClickListener(datelistener);
        othersTimeSlotText.setOnClickListener(toastListener);

        collectionTitleText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_collection,0,0,0);

        collectionTitleText.setText("Collection");
        ironingTitleText.setText("Delivery : Ironing");
        washingTitleText.setText("Delivery : Wash & Iron");
        bagsTitleText.setText("Delivery : Bags & Shoes");
        othersTitleText.setText("Delivery : Others");

        newOrder.setText("NEW ORDER");
        newOrder.setOnClickListener(listener);

        placeOrder.setText("PLACE ORDER");
        placeOrder.setOnClickListener(listener);


    }

    private void collectionDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        collectionDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth){
                    collectionDateText.setText("Today");
                    month = monthOfYear+1;
                    Constants.collectionDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(yearofc);
                    setCollectionsAdapter(Constants.collectionDate,"today");

                }
                else{
                    month = monthOfYear+1;
                    collectionDateText.setText(dayOfMonth+"/"+month+"/"+yearofc);
                    Constants.collectionDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(yearofc);
                    setCollectionsAdapter(Constants.collectionDate, "later");
                }
            }

        },year, month, date);
        collectionDatePicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        collectionDatePicker.show();
    }


    public void setCollectionsAdapter(final String d,final String when){

        Log.d(Constants.LOG_TAG," Date obtained in collections adapter "+ date);

        collectionTimeSlotText.setVisibility(View.GONE);
        collectionTimeSlot.setVisibility(View.VISIBLE);

        final String date = d;
        final ArrayList<String> collectionSlots = getSlots(date,when,"collection");
        Constants.jCounter = getJCounter(Constants.collectionDate);

        // previous
//        final ArrayList<String> collectionSlots = getSlots(date,when);
        if(collectionSlots != null) {

            collectionDateText.setText(Constants.collectionDate);
            collectionAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, collectionSlots);
            collectionAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            collectionTimeSlot.setAdapter(collectionAdapter);
            collectionAdapter.notifyDataSetChanged();

            collectionTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    // Collection Slot id
                    // -1 because our slots start from id number 1 so we are makieng th array index j = 0
                    // by subtracting 1
                    j = Integer.parseInt(Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString()))-Constants.jCounter;
                    int collectionArrayIndex = j;
                    Constants.collectionSlotId = Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString());
                    if(ironing.isChecked()){

                        setIroningAdapter(Constants.collectionDate,collectionArrayIndex);
                    }
                    if(washing.isChecked()){

                        setWashingAdapter(Constants.collectionDate, collectionArrayIndex);
                    }
                    if(bags.isChecked()){
                        setBagsAdapter(Constants.collectionDate,collectionArrayIndex);
                    }
                    if(others.isChecked()){

                        setOthersAdapter(Constants.collectionDate,collectionArrayIndex);
                    }

                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        else{

            String completedDate[] = date.split("/");
            int newDate = Integer.parseInt(completedDate[0]);
            newDate++;
            Constants.collectionDate = String.valueOf(newDate)+"/"+completedDate[1]+"/"+completedDate[2];
            setCollectionsAdapter(Constants.collectionDate, "later");
        }
    }



    private void ironingDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        ironingDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {


                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {
                    ironingDateText.setText("Today");

                    month = monthOfYear+1;
                    Constants.ironingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(yearofc);
                    setIroningAdapter(Constants.ironingDeliveryDate,-1);

                }
                else {
                    month = monthOfYear+1;
                    ironingDateText.setText(dayOfMonth + "/" + monthOfYear + "/" + yearofc);
                    Constants.ironingDeliveryDate = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(yearofc);
                    setIroningAdapter(Constants.ironingDeliveryDate, -1);


                }

            }

        },year, month, date);
        ironingDatePicker.getDatePicker().setMinDate(getLongDate(Constants.minIroningDate));
        ironingDatePicker.show();
    }


    public long getLongDate(String receivedDate){

        try {
            String dateString = receivedDate;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);

            long startDate = date.getTime();
            return startDate;
        }
        catch (Exception e){

            return 0L;
        }

    }

    public void setIroningAdapter(String date,int collectionArrayIndex){

        ironingTimeSlotText.setVisibility(View.GONE);
        ironingTimeSlot.setVisibility(View.VISIBLE);


        ArrayList<String> ironingSlots;
        if(collectionArrayIndex == -1){

            String minimumDate[] = Constants.minIroningDate.split("/");
            String ironingDate[] = date.split("/");

            int min = Integer.parseInt(minimumDate[0]);
            int iron = Integer.parseInt(ironingDate[0]);

            if(iron == min){

                ironingSlots = minimumIroningSlots;

            }
            else{

                ironingSlots = getSlots(date,"later","ironing");
            }

        }else {
            ironingSlots = getSlotsForIroningAndWashing(date, Constants.ironingDeliveryCounter, "ironing", collectionArrayIndex);
            minimumIroningSlots = ironingSlots;
            Constants.minIroningDate = Constants.ironingDeliveryDate;
            Log.d(Constants.LOG_TAG," The minimum date for washing is "+ Constants.minIroningDate);
        }
        if(ironingSlots != null) {
            ironingDateText.setText(Constants.ironingDeliveryDate);
            ironingAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, ironingSlots);
            ironingAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            ironingTimeSlot.setAdapter(ironingAdapter);
            ironingAdapter.notifyDataSetChanged();

            ironingTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Constants.ironingDeliverySlotId = Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        else{
            String completedDate[] = date.split("/");
            int newDate = Integer.parseInt(completedDate[0]);
            newDate++;
            Constants.ironingDeliveryDate = String.valueOf(newDate)+"/"+completedDate[1]+"/"+completedDate[2];
            setIroningAdapter(Constants.ironingDeliveryDate, -1);
        }

    }


    private void washingDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        washingDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {
                    washingDateText.setText("Today");

                    month = monthOfYear+1;
                    Constants.washingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(yearofc);
                    setWashingAdapter(Constants.washingDeliveryDate,-1);

                }
                else{
                    month = monthOfYear+1;
                    washingDateText.setText(dayOfMonth+"/"+monthOfYear+"/"+yearofc);
                    Constants.washingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(yearofc);
                    setWashingAdapter(Constants.washingDeliveryDate,-1);

                }
            }

        },year, month, date);
        washingDatePicker.getDatePicker().setMinDate(getLongDate(Constants.minWashingDate));
        washingDatePicker.show();
    }

    public void setWashingAdapter(String date,int collectionArrayIndex){


        washingTimeSlotText.setVisibility(View.GONE);
        washingTimeSlot.setVisibility(View.VISIBLE);

        ArrayList<String> washingSlots;
        if(collectionArrayIndex == -1){

            String minDate[] = Constants.minWashingDate.split("/");
            String washingDate[] = date.split("/");

            int min = Integer.parseInt(minDate[0]);
            int wash = Integer.parseInt(washingDate[0]);

            if(wash == min){

                washingSlots = minimumWashingSlots;

            }
            else{

                washingSlots = getSlots(date,"later","washing");
            }

        }
        else {
            washingSlots = getSlotsForIroningAndWashing(date, Constants.washingDeliveryCounter, "washing", collectionArrayIndex);
            minimumWashingSlots = washingSlots;
            Constants.minWashingDate = Constants.washingDeliveryDate;
            Log.d(Constants.LOG_TAG," The minimum date for washing is "+ Constants.minWashingDate);
        }
        if(washingSlots != null) {
            washingDateText.setText(Constants.washingDeliveryDate);
            washingAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, washingSlots);
            washingAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            washingTimeSlot.setAdapter(washingAdapter);
            washingAdapter.notifyDataSetChanged();
            washingTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.d(Constants.LOG_TAG," The delivery slot id is "+Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString()));
                    Constants.washingDeliverySlotId = Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        else{

            String completedDate[] = date.split("/");
            int newDate = Integer.parseInt(completedDate[0]);
            newDate++;
            Constants.washingDeliveryDate = String.valueOf(newDate)+"/"+completedDate[1]+"/"+completedDate[2];
            setWashingAdapter(Constants.washingDeliveryDate, -1);
        }

    }


    private void bagsDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        bagsDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {

                    month = monthOfYear+1;
                    bagsDateText.setText("Today");
                    Constants.bagsDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(yearofc);
                    setBagsAdapter(Constants.bagsDeliveryDate,-1);

                }
                else{
                    month = monthOfYear+1;
                    bagsDateText.setText(dayOfMonth+"/"+monthOfYear+"/"+yearofc);
                    Constants.bagsDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(yearofc);
                    setBagsAdapter(Constants.bagsDeliveryDate,-1);

                }
            }

        },year, month, date);
        bagsDatePicker.getDatePicker().setMinDate(getLongDate(Constants.minBagsDate));
        bagsDatePicker.show();
    }

    public void setBagsAdapter(String date,int collectionArrayIndex){

        bagsTimeSlotText.setVisibility(View.GONE);
        bagsTimeSlot.setVisibility(View.VISIBLE);

        // This is pending for now as he not sending bags data
        ArrayList<String> bagsSlots;
        if(collectionArrayIndex == -1){

            String minimumDate[] = Constants.minBagsDate.split("/");
            String bagsDate[] = date.split("/");

            int min = Integer.parseInt(minimumDate[0]);
            int bags = Integer.parseInt(bagsDate[0]);

            if(bags == min){

                bagsSlots = minimumBagsSlots;

            }
            else{

                bagsSlots = getSlots(date,"later","bags");
            }

        }
        else {
            bagsSlots = getSlotsForIroningAndWashing(date,Constants.bagsDeliveryCounter,"bags",collectionArrayIndex);
            minimumBagsSlots = bagsSlots;
            Constants.minBagsDate = Constants.bagsDeliveryDate;
        }
        if(bagsSlots != null) {
            bagsDateText.setText(Constants.bagsDeliveryDate);
            bagsAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, bagsSlots);
            bagsAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            bagsTimeSlot.setAdapter(bagsAdapter);
            bagsAdapter.notifyDataSetChanged();
            bagsTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.d(Constants.LOG_TAG," The delivery slot id is "+Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString()));
                    Constants.bagsDeliverySlotId = Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        else{
            String completedDate[] = date.split("/");
            int newDate = Integer.parseInt(completedDate[0]);
            newDate++;
            Constants.bagsDeliveryDate = String.valueOf(newDate)+"/"+completedDate[1]+"/"+completedDate[2];
            setBagsAdapter(Constants.bagsDeliveryDate, -1);
        }
    }

    private void othersDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        othersDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {

                    month = monthOfYear+1;
                    othersDateText.setText("Today");
                    Constants.othersDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(yearofc);
                    setOthersAdapter(Constants.othersDeliveryDate,-1);

                }
                else{
                    month = monthOfYear+1;
                    othersDateText.setText(dayOfMonth+"/"+monthOfYear+"/"+yearofc);
                    Constants.othersDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(yearofc);
                    setOthersAdapter(Constants.othersDeliveryDate,-1);

                }
            }

        },year, month, date);
        othersDatePicker.getDatePicker().setMinDate(getLongDate(Constants.minOthersDate));
        othersDatePicker.show();
    }

    public void setOthersAdapter(String date,int collectionArrayIndex){

        othersTimeSlotText.setVisibility(View.GONE);
        othersTimeSlot.setVisibility(View.VISIBLE);

        ArrayList<String> othersSlots;
        if(collectionArrayIndex == -1){

            String minimumDate[] = Constants.minOthersDate.split("/");
            String othersDate[] = date.split("/");

            int min = Integer.parseInt(minimumDate[0]);
            int others = Integer.parseInt(othersDate[0]);

            if(others == min){

                othersSlots = minimumOthersSlots;

            }
            else{

                othersSlots = getSlots(date,"later","others");
            }

        }
        else {
            othersSlots = getSlotsForIroningAndWashing(date,Constants.othersDeliveryCounter,"others",collectionArrayIndex);
            minimumOthersSlots = othersSlots;
            Constants.minOthersDate = Constants.othersDeliveryDate;
        }
        if(othersSlots != null) {
            othersDateText.setText(Constants.othersDeliveryDate);
            othersAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, othersSlots);
            othersAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            othersTimeSlot.setAdapter(othersAdapter);
            othersAdapter.notifyDataSetChanged();
            othersTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.d(Constants.LOG_TAG," The delivery slot id is "+Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString()));
                    Constants.othersDeliverySlotId = Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        else{
            String completedDate[] = date.split("/");
            int newDate = Integer.parseInt(completedDate[0]);
            newDate++;
            Constants.othersDeliveryDate = String.valueOf(newDate)+"/"+completedDate[1]+"/"+completedDate[2];
            setOthersAdapter(Constants.othersDeliveryDate, -1);
        }
    }


    // setting the available slots  for collection
    // date : the selected date
    // when : today or later
    public ArrayList<String> getSlots(String date, String when,String service){

        Log.d(Constants.LOG_TAG," Date is "+date);
        String dateDetails[] = date.split("/");

        int year = Integer.parseInt(dateDetails[2]);

        // we are subtracting one because months start range from 0 to 11
        // while in our calender month ranges from 1 to 12
        int month = Integer.parseInt(dateDetails[1])-1;
        int day = Integer.parseInt(dateDetails[0]);

        Calendar c = Calendar.getInstance();
        c.set(year,month,day);

        Log.d(Constants.LOG_TAG," The set date is "+ c.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Log.d(Constants.LOG_TAG," The set date after fomating "+ simpleDateFormat.format(c.getTime()));
        date = simpleDateFormat.format(c.getTime());



        Log.d(Constants.LOG_TAG,"Date "+date+" when "+when+" service "+service);
        if(service.equalsIgnoreCase("collection")){
            Constants.collectionDate = date;
            Log.d(Constants.LOG_TAG," Collection Date set "+ Constants.collectionDate);
        }
        else if(service.equalsIgnoreCase("ironing")){
            Constants.ironingDeliveryDate = date;
            Log.d(Constants.LOG_TAG," Ironing Date set "+ Constants.ironingDeliveryDate);
        }
        else if(service.equalsIgnoreCase("washing")){
            Constants.washingDeliveryDate = date;
            Log.d(Constants.LOG_TAG,"Washing Date set "+ Constants.washingDeliveryDate);
        }
        else if(service.equalsIgnoreCase("bags")){
            Constants.bagsDeliveryDate = date;
            Log.d(Constants.LOG_TAG," Bags Date set "+ Constants.bagsDeliveryDate);
        }
        else if(service.equalsIgnoreCase("others")){
            Constants.othersDeliveryDate = date;
            Log.d(Constants.LOG_TAG," Others Date set "+ Constants.othersDeliveryDate);
        }
        ArrayList<String> options = new ArrayList<String>();
        try {

            Log.d(Constants.LOG_TAG," The Date which is received in the getSlots "+date);
            String availableSlots = getAvailableSlots(date);

            if(availableSlots != null){
                String getSlots[] = availableSlots.split("_");
                Log.d(Constants.LOG_TAG,"when before checking "+when.equalsIgnoreCase("today"));
                if(when.equalsIgnoreCase("today")){

                    String presentTime = Constants.currentTime;

                    for(int i=0 ;i<getSlots.length;i++){

                        int validSlot = Integer.parseInt(getSlots[i].substring(0, 2));
                        int now = Integer.parseInt(presentTime.substring(0,2));

                        if(validSlot>now){

                            options.add(getSlots[i]);

                        }

                    }

                    if(options.size() == 0){

                        String detailedDate[] = date.split("/");
                        int dateForChange = Integer.parseInt(detailedDate[0]);
                        dateForChange++;
                        String dateForFunction = String.valueOf(dateForChange) + "/" + detailedDate[1] + "/" + detailedDate[2];
                        ArrayList<String> options1 =getSlots(dateForFunction,"later",service);
                        return options1;

                    }

                } // if the date is set for today
                else if(when.equalsIgnoreCase("later")){

                    Log.d(Constants.LOG_TAG," Available slots "+availableSlots);
                    String availableOptions[] = availableSlots.split("_");
                    for(int i=0;i<availableOptions.length;i++){

                        options.add(availableOptions[i]);
                    }

                }
            }
            else{

                String detailedDate[] = date.split("/");
                int dateForChange = Integer.parseInt(detailedDate[0]);
                dateForChange++;
                String dateForFunction = String.valueOf(dateForChange) + "/" + detailedDate[1] + "/" + detailedDate[2];
                ArrayList<String> options1 =getSlots(dateForFunction,"later",service);
                return options1;

            }


        }

        catch (Exception e){
            e.printStackTrace();
        }

        // return the array of available slots
        return options;
    }



    // previous one
    // setting the available slots  for collection
    // date : the selected date
    // when : today or later
//    public ArrayList<String> getSlots(String date, String when){
//
//        Log.d(Constants.LOG_TAG," Date is "+date);
//        String dateDetails[] = date.split("/");
//
//        int year = Integer.parseInt(dateDetails[2]);
//
//        // we are subtracting one because months start range from 0 to 11
//        // while in our calender month ranges from 1 to 12
//        int month = Integer.parseInt(dateDetails[1])-1;
//        int day = Integer.parseInt(dateDetails[0]);
//
//        Calendar c = Calendar.getInstance();
//        c.set(year,month,day);
//
//        Log.d(Constants.LOG_TAG," The set date is "+ c.getTime());
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Log.d(Constants.LOG_TAG," The set date after fomating "+ simpleDateFormat.format(c.getTime()));
//        date = simpleDateFormat.format(c.getTime());
//
//
//
//
//        Constants.collectionDate = date;
//        ArrayList<String> options = new ArrayList<String>();
//        try {
//
//            String availableSlots = getAvailableSlots(date);
//
//            if(availableSlots != null){
//                String getSlots[] = availableSlots.split("_");
//                if(when.equalsIgnoreCase("today")){
//
//                    String presentTime = Constants.currentTime;
//
//                    for(int i=0 ;i<getSlots.length;i++){
//
//                        int validSlot = Integer.parseInt(getSlots[i].substring(0, 2));
//                        int now = Integer.parseInt(presentTime.substring(0,2));
//
//                        if(validSlot>now){
//
//                            options.add(getSlots[i]);
//
//                        }
//
//                    }
//
//                    if(options.size() == 0){
//
//                        String dateDetails[] = date.split("/");
//                        int dateForChange = Integer.parseInt(dateDetails[0]);
//                        dateForChange++;
//                        String dateForFunction = String.valueOf(dateForChange) + "/" + dateDetails[1] + "/" + dateDetails[2];
//                        ArrayList<String> options1 =getSlots(dateForFunction,"later");
//                        return options1;
//
//                    }
//
//                } // if the date is set for today
//                else{
//
//                    Log.d(Constants.LOG_TAG," Available slots "+availableSlots);
//                    String availableOptions[] = availableSlots.split("_");
//                    for(int i=0;i<availableOptions.length;i++){
//
//                        options.add(availableOptions[i]);
//                    }
//
//                }
//            }
//            else{
//                    String dateDetails[] = date.split("/");
//                    int dateForChange = Integer.parseInt(dateDetails[0]);
//                    dateForChange++;
//                    String dateForFunction = String.valueOf(dateForChange) + "/" + dateDetails[1] + "/" + dateDetails[2];
//                    ArrayList<String> options1 =getSlots(dateForFunction,"later");
//                    return options1;
//
//            }
//
//
//        }
//
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        // return the array of available slots
//        return options;
//    }

    public String getAvailableSlots(String date){

        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date MyDate = newDateFormat.parse(date);
            newDateFormat.applyPattern("EEEE");
            String day = newDateFormat.format(MyDate);
            day = day.toLowerCase();
            String availableSlots = (String) Constants.slots.get(day);

            return availableSlots;
        }
        catch (Exception e){

            return null;
        }


    }

    public int getJCounter(String date){

        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date MyDate = newDateFormat.parse(date);
            newDateFormat.applyPattern("EEEE");
            String day = newDateFormat.format(MyDate);
            day = day.toLowerCase();
            String availableSlots[] = Constants.slots.get(day).split("_");
            String minSlot = Constants.getSlotsId .get(availableSlots[0]);

            Log.d(Constants.LOG_TAG," Minimum Slot for the date "+ date+" is "+ Integer.parseInt(minSlot));
            return Integer.parseInt(minSlot);

        }
        catch (Exception e){

            return 0;
        }



    }

    public ArrayList<String> getSlotsForIroningAndWashing(String date, int counter,String service ,int j){

        Log.d(Constants.LOG_TAG," Date is "+date);
        String dateDetails[] = date.split("/");

        int year = Integer.parseInt(dateDetails[2]);

        // we are subtracting one because months start range from 0 to 11
        // while in our calender month ranges from 1 to 12
        int month = Integer.parseInt(dateDetails[1])-1;
        int day = Integer.parseInt(dateDetails[0]);

        Calendar c = Calendar.getInstance();
        c.set(year,month,day);

        Log.d(Constants.LOG_TAG," The set date is "+ c.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Log.d(Constants.LOG_TAG," The set date after fomating "+ simpleDateFormat.format(c.getTime()));
        date = simpleDateFormat.format(c.getTime());


        ArrayList<String> options = new ArrayList<String>();
        if(service.equalsIgnoreCase("ironing")){
            Constants.ironingDeliveryDate = date;
        }
        else if(service.equalsIgnoreCase("washing")){
            Constants.washingDeliveryDate = date;
        }
        else if(service.equalsIgnoreCase("bags")){
            Constants.bagsDeliveryDate = date;
        }
        else if(service.equalsIgnoreCase("others")){
            Constants.othersDeliveryDate = date;
        }


        try {
            String availableSlots = getAvailableSlots(date);
            Log.d(Constants.LOG_TAG," SLOTS AVAILABLE "+ availableSlots);

            if(availableSlots!= null) {

                String getSlots[] = availableSlots.split("_");
                Log.d(Constants.LOG_TAG, " SLOTS LENGHT " + getSlots.length);
                int arrayLength = getSlots.length;
                while (counter != 0) {

                    counter--;
                    Log.d(Constants.LOG_TAG, " DELIVERY COUNTER " + counter);
                    // check if the slot of the collection time is less than
                    // the (size-1) of the given day's slots
                    //if that is the case then increase the slot counter
                    // else check if it is equal to the last index in the array
                    // if that is the case then change the date and call the function again.

                    int checkLength = arrayLength - 1;
                    if (j < checkLength) {
                        j++;
                        Log.d(Constants.LOG_TAG, " Value of J after incementing  From If " + j);


                    } else if (j == checkLength) {

                        j = 0;
                        Log.d(Constants.LOG_TAG, " Value of J after setting  from else" + j);
                        int dateForChange = Integer.parseInt(dateDetails[0]);
                        dateForChange++;
                        String dateForFunction = String.valueOf(dateForChange) + "/" + dateDetails[1] + "/" + dateDetails[2];

                        Log.d(Constants.LOG_TAG," About to call the function again "+ " date For FUntion "+dateForFunction+" counter "+counter+" service "+ service+" j "+j);
                        ArrayList<String> options1 = getSlotsForIroningAndWashing(dateForFunction, counter, service, j);
                        return options1;

                    }

                }// end of whileloop

                Log.d(Constants.LOG_TAG," Entering the while loop for service "+service);
                int i = j;
                while(i < getSlots.length){

                    Log.d(Constants.LOG_TAG," The value about to add "+getSlots[i]);
                    options.add(getSlots[i]);
                    i++;

                }

                // IF this is not added then 2 times it takes place
                return options;
            } //end of IF part to check if the string is equal to null
            else{


                j = 0;
                int dateForChange = Integer.parseInt(dateDetails[0]);
                dateForChange++;
                String dateForFunction = String.valueOf(dateForChange) + "/" + dateDetails[1] + "/" + dateDetails[2];
                ArrayList<String> options1 = getSlotsForIroningAndWashing(dateForFunction, counter,service,j);
                return options1;

            } // end of ELSE part to check if the string is equal to null
        } // end of TRY
        catch (Exception e){
            e.printStackTrace();
            return null;
        }


    } // end of function


    @Override
    public void onPause() {
        super.onPause();

        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public void newOrder(){

        replaceFragment(new LandingFragment());

    }

    public void placeOrder(){

        if(Constants.collectionDate !=null) {
            formatDate();
            createJson();

            new PlaceOrderAsyncTask(getActivity().getApplicationContext(), new PlaceOrderAsyncTask.PlaceWeeklyOrderCallback() {
                @Override
                public void onStart(boolean status) {

                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle(Constants.APP_NAME);
                    progressDialog.setMessage("Placing Your Order");
                    progressDialog.show();
                }

                @Override
                public void onResult(boolean result) {

                    progressDialog.dismiss();
                    if (result) {
                        showDialog();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Order couldnt be placed sucessfully\nTry Again Later", 5000).show();
                    }
                }
            }).execute(postOrderJsonObject);
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Please select the collection date.",5000).show();
        }
    }

    public void createJson(){

        Log.d(Constants.LOG_TAG," Create Json Entereed");
        itemsJsonArray = new JSONArray();
        ironingNestedJsonArray = new JSONArray();
        washingNestedJsonArray = new JSONArray();
        bagsNestedJsonArray = new JSONArray();
        othersNestedJsonArray = new JSONArray();

        try{

            if(ironing.isChecked()){

                ironingCreated = true;
                for(int i=0;i < 1;i++){
                    ironingNestedJsonObject = new JSONObject();
                    ironingNestedJsonObject.put("order_id","001001001");
                    ironingNestedJsonObject.put("amount","0");
                    ironingNestedJsonObject.put("quantity","0");

                    ironingNestedJsonArray.put(ironingNestedJsonObject);

                }

                ironingJsonObject = new JSONObject();
                ironingJsonObject.put("user_delivery_date",Constants.ironingDeliveryDate);
                ironingJsonObject.put("user_delivery_slot",Constants.ironingDeliverySlotId);
                ironingJsonObject.put("itemarr",ironingNestedJsonArray);
            }
            if(washing.isChecked()){

                washingCreated = true;
                for(int i=0;i<1;i++){
                    washingNestedJsonObject = new JSONObject();
                    washingNestedJsonObject.put("order_id","003001001");
                    washingNestedJsonObject.put("amount","0");
                    washingNestedJsonObject.put("quantity","0");

                    washingNestedJsonArray.put(washingNestedJsonObject);
                }

                washingJsonObject = new JSONObject();
                washingJsonObject.put("user_delivery_date",Constants.washingDeliveryDate);
                washingJsonObject.put("user_delivery_slot",Constants.washingDeliverySlotId);
                washingJsonObject.put("itemarr",washingNestedJsonArray);


            }
            if(bags.isChecked()){

                bagsCreated = true;
                for(int i=0;i<1;i++){
                    bagsNestedJsonObject = new JSONObject();
                    bagsNestedJsonObject.put("order_id","007001001");
                    bagsNestedJsonObject.put("amount","0");
                    bagsNestedJsonObject.put("quantity","0");

                    bagsNestedJsonArray.put(bagsNestedJsonObject);

                }

                bagsJsonObject = new JSONObject();
                bagsJsonObject.put("user_delivery_date",Constants.bagsDeliveryDate);
                bagsJsonObject.put("user_delivery_slot",Constants.bagsDeliverySlotId);
                bagsJsonObject.put("itemarr",bagsNestedJsonArray);
            }
            if(others.isChecked()){

                othersCreated = true;
                for(int i=0;i<1;i++){
                    othersNestedJsonObject = new JSONObject();
                    othersNestedJsonObject.put("order_id","009001001");
                    othersNestedJsonObject.put("amount","0");
                    othersNestedJsonObject.put("quantity","0");

                    othersNestedJsonArray.put(othersNestedJsonObject);

                }

                othersJsonObject = new JSONObject();
                othersJsonObject.put("user_delivery_date",Constants.othersDeliveryDate);
                othersJsonObject.put("user_delivery_slot",Constants.othersDeliverySlotId);
                othersJsonObject.put("itemarr",othersNestedJsonArray);
            }


            if (ironingCreated){

                itemsJsonArray.put(ironingJsonObject);

            }
            if(washingCreated){

                itemsJsonArray.put(washingJsonObject);
            }
            if(bagsCreated){
                itemsJsonArray.put(bagsJsonObject);
            }
            if(othersCreated){
                itemsJsonArray.put(othersJsonObject);
            }

            postOrderJsonObject = new JSONObject();
            postOrderJsonObject.put("user_id",Constants.userId);
            postOrderJsonObject.put("addr_id",Constants.addressId);
            postOrderJsonObject.put("total_amt","0");
            postOrderJsonObject.put("totalquantity","0");
            postOrderJsonObject.put("user_collection_time",Constants.collectionDate);
            postOrderJsonObject.put("user_collection_slot",Constants.collectionSlotId);
            postOrderJsonObject.put("items",itemsJsonArray);

            Log.d(Constants.LOG_TAG," Post Order Json "+ postOrderJsonObject);

        }
        catch (Exception e){

            e.printStackTrace();
        }

    }
    public void showDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(Constants.APP_NAME);
        alert.setMessage("Your order has been placed");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                replaceFragment(new TrackOrdersFragment());
            }
        });
        alert.setCancelable(false);
        alert.show();


    }

    public void formatDate(){


        try {
            if (Constants.collectionDate != null) {

                Log.d(Constants.LOG_TAG," collection date "+Constants.collectionDate);
                SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = originalFormat.parse(Constants.collectionDate);


                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Constants.collectionDate = newDateFormat.format(date);
                Log.d(Constants.LOG_TAG,"  NEW Collections Date "+ Constants.collectionDate);
            }
            if (Constants.ironingDeliveryDate != null) {

                Log.d(Constants.LOG_TAG," Ironing date "+Constants.ironingDeliveryDate);
                SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = originalFormat.parse(Constants.ironingDeliveryDate);


                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Constants.ironingDeliveryDate = newDateFormat.format(date);
                Log.d(Constants.LOG_TAG,"  NEW Ironing Date "+ Constants.ironingDeliveryDate);
            }
            if (Constants.washingDeliveryDate != null) {

                Log.d(Constants.LOG_TAG," Old washing delivery Date"+Constants.washingDeliveryDate);
                SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = originalFormat.parse(Constants.washingDeliveryDate);

                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Constants.washingDeliveryDate = newDateFormat.format(date);
                Log.d(Constants.LOG_TAG,"  NEW Washing Date "+ Constants.washingDeliveryDate);
            }
            if (Constants.bagsDeliveryDate != null) {

                Log.d(Constants.LOG_TAG," Old washing delivery Date"+Constants.bagsDeliveryDate);
                SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = originalFormat.parse(Constants.bagsDeliveryDate);

                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Constants.bagsDeliveryDate = newDateFormat.format(date);
                Log.d(Constants.LOG_TAG,"  NEW Washing Date "+ Constants.bagsDeliveryDate);
            }
            if (Constants.othersDeliveryDate != null) {

                Log.d(Constants.LOG_TAG," Old washing delivery Date"+Constants.othersDeliveryDate);
                SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = originalFormat.parse(Constants.othersDeliveryDate);

                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Constants.othersDeliveryDate = newDateFormat.format(date);
                Log.d(Constants.LOG_TAG,"  New Others Date "+ Constants.othersDeliveryDate);
            }
        }
        catch (Exception e){

            e.printStackTrace();
        }
    }

    public void replaceFragment(Fragment fragment){

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();

    }

    View.OnClickListener toastListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(Constants.collectionDate == null){
                Toast.makeText(getActivity().getApplicationContext(),"Please Select the collection date",5000).show();
            }

        }
    };


    View.OnClickListener datelistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getTag().toString()) {

                case "date_1":
                    collectionDatePicker();
                    break;
                case "date_2":
                    if(Constants.collectionDate != null){
                        ironingDatePicker();
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),"Please Select the Collection Date",5000).show();
                    }
                    break;
                case "date_3":
                    if(Constants.collectionDate != null){
                        washingDatePicker();
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),"Please Select the Collection Date",5000).show();
                    }
                    break;
                case "date_4":
                    if(Constants.collectionDate != null) {
                        bagsDatePicker();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(),"Please Select the Collection Date",5000).show();
                    }
                    break;
                case "date_5":
                    if(Constants.collectionDate != null) {
                        othersDatePicker();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(),"Please Select the Collection Date",5000).show();
                    }
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
                case R.id.quick_service_others:
                    if(others.isChecked()){
                        othersLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        othersLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.quick_service_done:
                    if(ironing.isChecked()){

                        dialog.dismiss();
                    }
                    else if(washing.isChecked()){

                        dialog.dismiss();
                    }
                    else if(bags.isChecked()){

                        dialog.dismiss();
                    }
                    else if(others.isChecked()){

                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),"You need to select atleast one service",5000).show();
                    }
                    break;
                case R.id.left_button_included : newOrder();
                    break;
                case R.id.right_button_included : placeOrder();
                    break;


            }

        }
    };


}
