package com.ascenttechnovation.laundrize.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.async.FetchCurrentServerTimeAsyncTask;
import com.ascenttechnovation.laundrize.async.FetchSlotDifferenceAsyncTask;
import com.ascenttechnovation.laundrize.async.PlaceOrderAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.data.BagOrderData;
import com.ascenttechnovation.laundrize.data.IroningOrderData;
import com.ascenttechnovation.laundrize.data.WashingOrderData;
import com.ascenttechnovation.laundrize.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ADMIN on 31-07-2015.
 */
public class PlaceOrderFragment extends Fragment {

    View v;
    private CardView ironingLayout,washingLayout,bagsLayout,collectionLayout;
    private ActionBar actionBar;
    private CustomTextView ironingTitleText,ironingDateText,washingTitleText,washingDateText,bagsTitleText,bagsDateText,collectionTitleText,collectionDateText,collectionStaticText,ironingStaticText,washingStaticText,bagsStaticText;
    private Spinner collectionTimeSlot, ironingTimeSlot, washingTimeSlot, bagsTimeSlot;
    private CustomButton placeOrder,newOrder;
    private JSONObject ironingNestedJsonObject,washingNestedJsonObject,bagsNestedJsonObject,ironingJsonObject,washingJsonObject,bagsJsonObject,postOrderJsonObject;
    private JSONArray ironingNestedJsonArray,washingNestedJsonArray,bagsNestedJsonArray,itemsJsonArray;
    private boolean ironingCreated,washingCreated,bagsCreated = false;
    private ProgressDialog progressDialog;
    private DatePickerDialog collectionsPickDate,ironingPickDate,washingPickDate,bagsPickDate;
    private int date,month,year;
    private ArrayAdapter<String> collectionAdapter,ironingAdapter,washingAdapter,bagsAdapter;
    // slot difference is 4 but +1 is added to get the correct index in the array same logic for washingDeliveryCounter
    private int ironingDeliveryCounter = 5;
    private int washingDeliveryCounter = 22;
    private int bagsDeliveryCounter = 22;


    private CustomTextView cloth,service,quantity,total;
    private ImageView add,subtract,remove;

    private LinearLayout yourItemsLayout;

    private ArrayList<String> minimumIroningSlots,minimumWashingSlots,minimumBagsSlots;

    // This will hold the index of the slot from the array of Collections Slots;
    private int j;
    private int mapCounter =0;

    int quantityValue,obtainedAmount,perPieceCost,totalQuantity,totalAmount;
    String newValue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_place_order,container,false);

        Log.d(Constants.LOG_TAG, Constants.PlaceOrderFragment);

        customActionBar();
        getServerTime();
        getTheValues();


        return v;
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Place Order");

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
                    String finalUrl = Constants.getSlotDifferenceUrl + Constants.userId;
                    new FetchSlotDifferenceAsyncTask(new FetchSlotDifferenceAsyncTask.FetchSlotDifferenceCallback() {
                        @Override
                        public void onStart(boolean status) {

                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle(Constants.APP_NAME);
                            progressDialog.setMessage("Please Wait...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                        }
                        @Override
                        public void onResult(boolean result) {

                            progressDialog.dismiss();
                            if(result){

                                Constants.slotDifferenceFetched = true;
                                findViews(v);
                                setViews();
                                inflateViews();

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

        placeOrder = (CustomButton) v.findViewById(R.id.right_button_included);
        newOrder = (CustomButton) v.findViewById(R.id.left_button_included);

        collectionLayout = (CardView) v.findViewById(R.id.collection_layout_place_order_fragment);
        collectionTitleText = (CustomTextView) collectionLayout.findViewById(R.id.service_included);
        collectionDateText = (CustomTextView) collectionLayout.findViewById(R.id.select_date_slot_included);
        collectionStaticText = (CustomTextView) collectionLayout.findViewById(R.id.select_time_slot_text);
        collectionTimeSlot = (Spinner) collectionLayout.findViewById(R.id.select_time_slot_included);

        ironingLayout = (CardView) v.findViewById(R.id.ironing_layout_place_order_fragment);
        ironingTitleText = (CustomTextView) ironingLayout.findViewById(R.id.service_included);
        ironingDateText = (CustomTextView) ironingLayout.findViewById(R.id.select_date_slot_included);
        ironingStaticText = (CustomTextView) ironingLayout.findViewById(R.id.select_time_slot_text);
        ironingTimeSlot = (Spinner) ironingLayout.findViewById(R.id.select_time_slot_included);


        washingLayout = (CardView) v.findViewById(R.id.washing_layout_place_order_fragment);
        washingTitleText = (CustomTextView) washingLayout.findViewById(R.id.service_included);
        washingDateText = (CustomTextView) washingLayout.findViewById(R.id.select_date_slot_included);
        washingStaticText = (CustomTextView) washingLayout.findViewById(R.id.select_time_slot_text);
        washingTimeSlot = (Spinner) washingLayout.findViewById(R.id.select_time_slot_included);

        bagsLayout = (CardView) v.findViewById(R.id.bags_layout_place_order_fragment);
        bagsTitleText = (CustomTextView) bagsLayout.findViewById(R.id.service_included);
        bagsDateText = (CustomTextView) bagsLayout.findViewById(R.id.select_date_slot_included);
        bagsStaticText = (CustomTextView) bagsLayout.findViewById(R.id.select_time_slot_text);
        bagsTimeSlot = (Spinner) bagsLayout.findViewById(R.id.select_time_slot_included);

        yourItemsLayout = (LinearLayout) v.findViewById(R.id.your_items_layout_place_order_fragment);


    }

    private void setViews(){


        newOrder.setText("NEW ORDER");
        newOrder.setOnClickListener(listener);

        placeOrder.setText("PLACE ORDER");
        placeOrder.setOnClickListener(listener);

        collectionDateText.setTag("date_1");
        collectionDateText.setOnClickListener(datelistener);

        collectionStaticText.setTag("collection");
        collectionStaticText.setOnClickListener(toastListener);

        ironingDateText.setTag("date_2");
        ironingDateText.setOnClickListener(datelistener);

        ironingStaticText.setTag("collection");
        ironingStaticText.setOnClickListener(toastListener);

        washingDateText.setTag("date_3");
        washingDateText.setOnClickListener(datelistener);


        washingStaticText.setTag("collection");
        washingStaticText.setOnClickListener(toastListener);

        bagsDateText.setTag("date_4");
        bagsDateText.setOnClickListener(datelistener);

        bagsStaticText.setTag("collection");
        bagsStaticText.setOnClickListener(toastListener);

        ironingLayout.setVisibility(View.GONE);
        washingLayout.setVisibility(View.GONE);
        bagsLayout.setVisibility(View.GONE);

        collectionTitleText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_collection,0,0,0);

        collectionTitleText.setText("Collection");
        ironingTitleText.setText("Delivery : Ironing");
        washingTitleText.setText("Delivery : Washables");
        bagsTitleText.setText("Delivery : Bags & Shoes");


    }

    public void inflateViews(){

        int ironingSize = Constants.ironingOrderData.size();
        int washingSize = Constants.washingOrderData.size();
        int bagsSize = Constants.bagOrderData.size();
        if(ironingSize>0){

            ironingLayout.setVisibility(View.VISIBLE);

        }
        if(washingSize>0){

            washingLayout.setVisibility(View.VISIBLE);

        }
        if(bagsSize>0){

            bagsLayout.setVisibility(View.VISIBLE);

        }

        setYourItems();


    }

    public void setYourItems(){


        Iterator it = Constants.order.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            inflateYourItems(pair.getKey().toString(), pair.getValue().toString(), mapCounter);
            mapCounter++;

        }

    }

    public void inflateYourItems(String key,String value,int position){

        String valueDetails [] = value.split("_");

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.row_your_items,null);
        v.setTag("view_"+position+"_"+key);

        cloth = (CustomTextView) v.findViewById(R.id.cloth_text_your_items);
        cloth.setText(Constants.servicesName.get(key));

        service = (CustomTextView) v.findViewById(R.id.service_text_your_items);
        String keyValue = key.substring(0,3);

        switch(keyValue){

            case "001": service.setText("Ironing");
                break;
            case "002": service.setText("Ironing");
                break;
            case "003": service.setText("Washing");
                break;
            case "004": service.setText("Washing");
                break;
            case "005": service.setText("Dry Cleaning");
                break;
            case "006": service.setText("Dry Cleaning");
                break;
            case "007": service.setText("Shoe Laundry");
                break;
            case "008": service.setText("Bag Laundry");
                break;
        }


        quantity = (CustomTextView) v.findViewById(R.id.quantity_text_your_items);
        total = (CustomTextView) v.findViewById(R.id.total_text_your_items);

        quantity.setTag("quantity_"+position+"_"+key);
        quantity.setText(valueDetails[0]);

        total.setTag("total_"+position+"_"+key);
        total.setText(valueDetails[1]);

        add = (ImageView) v.findViewById(R.id.add_image_your_items);
        add.setTag("add_"+position+"_"+key);
        add.setOnClickListener(inflatedClickListener);

        subtract = (ImageView) v.findViewById(R.id.subtract_image_your_items);
        subtract.setTag("subtract_"+position+"_"+key);
        subtract.setOnClickListener(inflatedClickListener);

        remove = (ImageView) v.findViewById(R.id.remove_image_your_items);
        remove.setTag("remove_"+position+"_"+key);
        remove.setOnClickListener(inflatedClickListener);
        yourItemsLayout.addView(v);

        View line = new View(getActivity().getApplicationContext());
        line.setBackgroundColor(R.color.grey);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1);
        line.setLayoutParams(params1);
        line.setTag("line_"+position);
        yourItemsLayout.addView(line);

    }

    private void collectionsDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);

        collectionsPickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth){
                    collectionDateText.setText("Today");
                    monthOfYear = month+1;
                    Constants.collectionDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    setCollectionsAdapter(Constants.collectionDate,"today");

                }
                else{
                    monthOfYear = month+1;
                    collectionDateText.setText(dayOfMonth+"/"+monthOfYear+"/"+yearofc);
                    Constants.collectionDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    setCollectionsAdapter(Constants.collectionDate, "later");
                }

            }

        },year, month, date);
        collectionsPickDate.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        collectionsPickDate.show();
    }

    public void setCollectionsAdapter(String d,final String when){

        collectionStaticText.setVisibility(View.GONE);
        collectionTimeSlot.setVisibility(View.VISIBLE);

        final String date = d;
        final ArrayList<String> collectionSlots = getSlots(date,when);
        if(collectionSlots != null){

            // d will always receive Constants.collectionDate
            collectionDateText.setText(Constants.collectionDate);
            collectionAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, collectionSlots);
            collectionAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            collectionTimeSlot.setAdapter(collectionAdapter);
            collectionAdapter.notifyDataSetChanged();

            collectionTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    // Collection Slot id
                    // minus 2 because the slots start from 1 instead of 1 and the slot 1 is not accessbile current
                    j = Integer.parseInt(Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString()))-2;
                    int collectionArrayIndex = j;
                    Constants.collectionSlotId = Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString());
                    setIroningAdapter(Constants.collectionDate,collectionArrayIndex);
                    setWashingAdapter(Constants.collectionDate, collectionArrayIndex);

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
        ironingPickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {
                    ironingDateText.setText("Today");

                    monthOfYear = month+1;
                    Constants.ironingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    setIroningAdapter(Constants.ironingDeliveryDate,-1);

                }
                else{
                    monthOfYear = month+1;
                    ironingDateText.setText(dayOfMonth+"/"+monthOfYear+"/"+yearofc);
                    Constants.ironingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    setIroningAdapter(Constants.ironingDeliveryDate,-1);

                }
            }

        },year, month, date);
        ironingPickDate.getDatePicker().setMinDate(getLongDate(Constants.minIroningDate));
        ironingPickDate.show();
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

        ironingStaticText.setVisibility(View.GONE);
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

                ironingSlots = getSlots(date,"later");
            }

        }
        else {
            ironingSlots = getSlotsForIroningAndWashing(date, ironingDeliveryCounter, "ironing", collectionArrayIndex);
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
        washingPickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {
                    washingDateText.setText("Today");

                    monthOfYear = month+1;
                    Constants.washingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    setWashingAdapter(Constants.washingDeliveryDate,-1);

                }
                else{
                    monthOfYear = month+1;
                    washingDateText.setText(dayOfMonth+"/"+monthOfYear+"/"+yearofc);
                    Constants.washingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    setWashingAdapter(Constants.washingDeliveryDate,-1);

                }
            }

        },year, month, date);
        washingPickDate.getDatePicker().setMinDate(getLongDate(Constants.minWashingDate));
        washingPickDate.show();
    }

    public void setWashingAdapter(String date,int collectionArrayIndex){


        washingStaticText.setVisibility(View.GONE);
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

                washingSlots = getSlots(date,"later");
            }

        }
        else {
            washingSlots = getSlotsForIroningAndWashing(date, washingDeliveryCounter, "washing", collectionArrayIndex);
            minimumWashingSlots = washingSlots;
            Constants.minWashingDate = Constants.washingDeliveryDate;
            Log.d(Constants.LOG_TAG," The minimum date for washing is "+ Constants.minWashingDate);
        }
        if(washingSlots != null){
            washingDateText.setText(Constants.washingDeliveryDate);
            washingAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, washingSlots);
            washingAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            washingTimeSlot.setAdapter(washingAdapter);
            washingAdapter.notifyDataSetChanged();
            washingTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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
        bagsPickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {

                    monthOfYear = month+1;
                    bagsDateText.setText("Today");
                    Constants.bagsDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    setBagsAdapter(Constants.bagsDeliveryDate,-1);

                }
                else{
                    monthOfYear = month+1;
                    bagsDateText.setText(dayOfMonth+"/"+monthOfYear+"/"+yearofc);
                    Constants.bagsDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    setBagsAdapter(Constants.bagsDeliveryDate,-1);

                }
            }

        },year, month, date);
        bagsPickDate.getDatePicker().setMinDate(Long.parseLong(Constants.minBagsDate));
        bagsPickDate.show();
    }

    public void setBagsAdapter(String date,int collectionArrayIndex){

        bagsStaticText.setVisibility(View.GONE);
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

                bagsSlots = getSlots(date,"later");
            }

        }
        else {
            bagsSlots = getSlotsForIroningAndWashing(date,bagsDeliveryCounter,"bags",collectionArrayIndex);
            minimumBagsSlots = bagsSlots;
            Constants.minBagsDate = Constants.bagsDeliveryDate;
        }
        if(bagsSlots != null) {
            bagsAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, bagsSlots);
            bagsAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            bagsTimeSlot.setAdapter(bagsAdapter);
        }
        else{

            String completedDate[] = date.split("/");
            int newDate = Integer.parseInt(completedDate[0]);
            newDate++;
            Constants.bagsDeliveryDate = String.valueOf(newDate)+"/"+completedDate[1]+"/"+completedDate[2];
            setBagsAdapter(Constants.bagsDeliveryDate, -1);

        }
    }

    // setting the available slots  for collection
    // date : the selected date
    // when : today or later
    public ArrayList<String> getSlots(String date, String when){

        Log.d(Constants.LOG_TAG,"when from function "+ when );
        Constants.collectionDate = date;
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

                        String dateDetails[] = date.split("/");
                        int dateForChange = Integer.parseInt(dateDetails[0]);
                        dateForChange++;
                        String dateForFunction = String.valueOf(dateForChange) + "/" + dateDetails[1] + "/" + dateDetails[2];
                        ArrayList<String> options1 =getSlots(dateForFunction,"later");
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

                String dateDetails[] = date.split("/");
                int dateForChange = Integer.parseInt(dateDetails[0]);
                dateForChange++;
                String dateForFunction = String.valueOf(dateForChange) + "/" + dateDetails[1] + "/" + dateDetails[2];
                ArrayList<String> options1 =getSlots(dateForFunction,"later");
                return options1;

            }


        }

        catch (Exception e){
            e.printStackTrace();
        }

        // return the array of available slots
        return options;
    }

    public String getAvailableSlots(String date){

        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Log.d(Constants.LOG_TAG," received date from getAvailable Slots "+ date);
            Date MyDate = newDateFormat.parse(date);
            newDateFormat.applyPattern("EEEE");
            String day = newDateFormat.format(MyDate);
            day = day.toLowerCase();
            Log.d(Constants.LOG_TAG," received day from getAvailable Slots "+Constants.slots.get(day));
            String availableSlots = (String) Constants.slots.get(day);


            return availableSlots;
        }
        catch (Exception e){

            return null;
        }


    }

    public ArrayList<String> getSlotsForIroningAndWashing(String date, int counter,String service ,int j){

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

//
        String dateDetails[] = date.split("/");
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

    public void placeOrder(){

        if(Constants.collectionDate != null) {
            formatDate();
            getTheValues();
            createJson();
            new PlaceOrderAsyncTask(getActivity().getApplicationContext(), new PlaceOrderAsyncTask.PlaceWeeklyOrderCallback() {
                @Override
                public void onStart(boolean status) {

                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle(Constants.APP_NAME);
                    progressDialog.setMessage("Placing Your Order");
                    progressDialog.setCancelable(false);
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

            Toast.makeText(getActivity().getApplicationContext(),"Please set the dates",5000).show();
        }

    }


    public void getTheValues(){

        Iterator iterator = Constants.order.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            Log.d(Constants.LOG_TAG, " key " + mapEntry.getKey() + " value " + mapEntry.getValue());

            String orderId = mapEntry.getKey().toString();
            String serviceType = String.valueOf(orderId.charAt(2));
            String orderDetails[] = mapEntry.getValue().toString().split("_");
            if(serviceType.equalsIgnoreCase("1") || serviceType.equalsIgnoreCase("2")){

                Constants.ironingOrderData.add(new IroningOrderData(orderId,orderDetails[1],orderDetails[0]));
                Constants.totalAmountToBeCollected += Integer.parseInt(orderDetails[1]);
                Constants.totalQuantityToBeCollected += Integer.parseInt(orderDetails[0]);
            }
            else if(serviceType.equalsIgnoreCase("3")||serviceType.equalsIgnoreCase("4")||serviceType.equalsIgnoreCase("5")||serviceType.equalsIgnoreCase("6")){

                Constants.washingOrderData.add(new WashingOrderData(orderId,orderDetails[1],orderDetails[0]));
                Constants.totalAmountToBeCollected += Integer.parseInt(orderDetails[1]);
                Constants.totalQuantityToBeCollected += Integer.parseInt(orderDetails[0]);
            }
            else if(serviceType.equalsIgnoreCase("7") || serviceType.equalsIgnoreCase("8")){

                Constants.bagOrderData.add(new BagOrderData(orderId,orderDetails[1],orderDetails[0]));
                Constants.totalAmountToBeCollected += Integer.parseInt(orderDetails[1]);
                Constants.totalQuantityToBeCollected += Integer.parseInt(orderDetails[0]);
            }

        }

        Log.d(Constants.LOG_TAG,"Total Quantity "+Constants.totalQuantityToBeCollected);
        Log.d(Constants.LOG_TAG,"Total Amount "+Constants.totalAmountToBeCollected);

    }


    // This function would be used to create JSON which contains the
    // users orders to be placed
    public void createJson(){

        Log.d(Constants.LOG_TAG," Creating JSOn ");

        itemsJsonArray = new JSONArray();
        washingNestedJsonArray = new JSONArray();
        bagsNestedJsonArray = new JSONArray();
        ironingNestedJsonArray = new JSONArray();

        try{

            if(Constants.ironingOrderData.size()>0){

                ironingCreated = true;
                for(int i=0;i < Constants.ironingOrderData.size();i++){
                    ironingNestedJsonObject = new JSONObject();
                    String orderId = Constants.ironingOrderData.get(i).getOrderId();
                    String quantity = Constants.ironingOrderData.get(i).getQuantity();
                    String amount = Constants.ironingOrderData.get(i).getAmount();


                    ironingNestedJsonObject.put("order_id",orderId);
                    ironingNestedJsonObject.put("amount",amount);
                    // just sending the price of a single piece
                    String temp = String.valueOf((Integer.parseInt(amount)/Integer.parseInt(quantity)));
                    Log.d(Constants.LOG_TAG,"Value for quantity"+temp);
                    ironingNestedJsonObject.put("quantity",temp);

                    ironingNestedJsonArray.put(ironingNestedJsonObject);

                }

                ironingJsonObject = new JSONObject();
                ironingJsonObject.put("user_delivery_date",Constants.ironingDeliveryDate);
                ironingJsonObject.put("user_delivery_slot",Constants.ironingDeliverySlotId);
                ironingJsonObject.put("itemarr",ironingNestedJsonArray);
            }
            if(Constants.washingOrderData.size()>0){

                washingCreated = true;
                for(int i=0;i<Constants.washingOrderData.size();i++){
                    washingNestedJsonObject = new JSONObject();
                    String orderId = Constants.washingOrderData.get(i).getOrderId();
                    String quantity = Constants.washingOrderData.get(i).getQuantity();
                    String amount = Constants.washingOrderData.get(i).getAmount();


                    washingNestedJsonObject.put("order_id",orderId);
                    washingNestedJsonObject.put("amount",amount);
                    String temp = String.valueOf((Integer.parseInt(amount)/Integer.parseInt(quantity)));
                    Log.d(Constants.LOG_TAG,"Value for quantity"+temp);
                    washingNestedJsonObject.put("quantity",temp);

                    washingNestedJsonArray.put(washingNestedJsonObject);
                }

                washingJsonObject = new JSONObject();
                washingJsonObject.put("user_delivery_date",Constants.washingDeliveryDate);
                washingJsonObject.put("user_delivery_slot",Constants.washingDeliverySlotId);
                washingJsonObject.put("itemarr",washingNestedJsonArray);


            }
            if(Constants.bagOrderData.size()>0){

                bagsCreated = true;
                for(int i=0;i<Constants.ironingOrderData.size();i++){
                    bagsNestedJsonObject = new JSONObject();
                    String orderId = Constants.bagOrderData.get(i).getOrderId();
                    String quantity = Constants.bagOrderData.get(i).getQuantity();
                    String amount = Constants.bagOrderData.get(i).getAmount();

                    bagsNestedJsonObject.put("order_id",orderId);
                    bagsNestedJsonObject.put("amount",amount);

                    String temp = String.valueOf((Integer.parseInt(amount)/Integer.parseInt(quantity)));
                    Log.d(Constants.LOG_TAG,"Value for quantity"+temp);
                    bagsNestedJsonObject.put("quantity",temp);


                    bagsNestedJsonArray.put(bagsNestedJsonObject);

                }

                bagsJsonObject = new JSONObject();
                bagsJsonObject.put("user_delivery_date",Constants.bagsDeliveryDate);
                bagsJsonObject.put("user_delivery_slot",Constants.bagsDeliverySlotId);
                bagsJsonObject.put("itemarr",bagsNestedJsonArray);
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

            postOrderJsonObject = new JSONObject();
            postOrderJsonObject.put("user_id",Constants.userId);
            postOrderJsonObject.put("addr_id",Constants.addressId);
            postOrderJsonObject.put("total_amt",String.valueOf(Constants.totalAmountToBeCollected));
            postOrderJsonObject.put("totalquantity",String.valueOf(Constants.totalQuantityToBeCollected));
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
        alert.setTitle("Laundrize");
        alert.setMessage("Your Order has been Placed");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                replaceFragment(new LandingFragment());
            }
        });
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
                Constants.washingDeliveryDate = newDateFormat.format(date);
                Log.d(Constants.LOG_TAG,"  NEW Washing Date "+ Constants.bagsDeliveryDate);
            }
        }
        catch (Exception e){

            e.printStackTrace();
        }
    }

    public void newOrder(){

        replaceFragment(new ServicesFragment());
    }

    // This function would be used to update the UI
    // and change the fragment
    public void replaceFragment(Fragment fragment){

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();

    }

    public void add(String key,String position){


        View v = yourItemsLayout.findViewWithTag("view_"+position+"_"+key);

        // This will first find the view based on the tag
        // then get the text value which is set
        // then convert it to int
        quantityValue = Integer.parseInt(((CustomTextView) v.findViewWithTag("quantity_"+position+"_"+key)).getText().toString());
        obtainedAmount = Integer.parseInt(((CustomTextView) v.findViewWithTag("total_"+position+"_"+key)).getText().toString());
        perPieceCost = obtainedAmount/quantityValue;
        totalQuantity = (quantityValue+1);
        totalAmount = (totalQuantity*perPieceCost);
        newValue = String.valueOf(totalQuantity)+"_"+totalAmount;
        Constants.order.put(key, newValue);
        updateUI(position,key,newValue);


    }
    public void subtract(String key,String position){

        View v = yourItemsLayout.findViewWithTag("view_"+position+"_"+key);

        // This will first find the view based on the tag
        // then get the text value which is set
        // then convert it to int
        quantityValue = Integer.parseInt(((CustomTextView) v.findViewWithTag("quantity_"+position+"_"+key)).getText().toString());
        obtainedAmount = Integer.parseInt(((CustomTextView) v.findViewWithTag("total_"+position+"_"+key)).getText().toString());
        perPieceCost = obtainedAmount/quantityValue;

        if(quantityValue!=0) {
            // when added then
            totalQuantity = (quantityValue - 1);
            totalAmount = (totalQuantity * perPieceCost);
            newValue = String.valueOf(totalQuantity) + "_" + totalAmount;
            Constants.order.put(key, newValue);
            updateUI(position, key, newValue);

        }

    }

    public void updateUI(String position,String key,String value){


        Log.d(Constants.LOG_TAG," Obtained position "+ position+" key "+key+" value "+value);
        String valueDetails[] = value.split("_");

        View v = yourItemsLayout.findViewWithTag("view_"+position+"_"+key);
        ((CustomTextView)v.findViewWithTag("quantity_" + position + "_" + key)).setText(valueDetails[0]);
        ((CustomTextView)v.findViewWithTag("total_" + position + "_" + key)).setText(valueDetails[1]);

    }

    public void remove(String key, String position){

        Constants.order.remove(key);

        yourItemsLayout.findViewWithTag("view_"+position+"_"+key).setVisibility(View.GONE);
        yourItemsLayout.findViewWithTag("line_"+position).setVisibility(View.GONE);

    }

    View.OnClickListener toastListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getTag().toString()){

                case "collection":
                    if(Constants.collectionDate == null){
                        Toast.makeText(getActivity().getApplicationContext(),"Please Select the collection date",5000).show();
                    }

                    break;
                case "ironing":
                    if(Constants.collectionDate == null){
                        Toast.makeText(getActivity().getApplicationContext(),"Please Select the collection date",5000).show();
                    }
                    break;
                case "washing":
                    if(Constants.collectionDate == null){
                        Toast.makeText(getActivity().getApplicationContext(),"Please Select the collection date",5000).show();
                    }
                    break;
                case "bag":
                    if(Constants.collectionDate == null){
                        Toast.makeText(getActivity().getApplicationContext(),"Please Select the collection date",5000).show();
                    }
                    break;

            }

        }
    };


    View.OnClickListener inflatedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // The tag would be like this add_0_001001000_2_12
            String tag = view.getTag().toString();
            Log.d(Constants.LOG_TAG," Tag "+ tag);

            String tagDetails[] = tag.split("_");
            String tagValue = tagDetails[0];
            String position = tagDetails[1];
            String key = tagDetails[2];
            Log.d(Constants.LOG_TAG," Tag Value "+ tagValue+" position"+position+" key "+key);

            switch (tagValue){

                case "add": add(key,position);
                    break;
                case "subtract": subtract(key,position);
                    break;
                case "remove": remove(key,position);
                    break;

            }

        }
    };

    View.OnClickListener datelistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getTag().toString()) {

                case "date_1":
                    collectionsDatePicker();
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
            }
        }
    };

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.left_button_included: newOrder();
                    break;
                case R.id.right_button_included: placeOrder();
                    break;

            }

        }
    };



}
