package com.ascenttechnovation.laundrize.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.async.FetchCurrentServerTimeAsyncTask;
import com.ascenttechnovation.laundrize.async.FetchSlotDifferenceAsyncTask;
import com.ascenttechnovation.laundrize.async.PlaceWeeklyOrderAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ADMIN on 31-07-2015.
 */
public class PlaceOrderFragment extends Fragment {

    View v;
    private CardView ironingLayout,washingLayout,bagsLayout,collectionLayout;
    private ActionBar actionBar;
    private CustomTextView ironingTitleText,ironingDateText,washingTitleText,washingDateText,bagsTitleText,bagsDateText,collectionTitleText,collectionDateText;
    private Spinner collectionTimeSlot, ironingTimeSlot, washingTimeSlot, bagsTimeSlot;
    private CustomButton placeOrder,newOrder;
    private JSONObject ironingNestedJsonObject,washingNestedJsonObject,bagsNestedJsonObject,ironingJsonObject,washingJsonObject,bagsJsonObject,postOrderJsonObject;
    private JSONArray ironingNestedJsonArray,washingNestedJsonArray,bagsNestedJsonArray,itemsJsonArray;
    private boolean ironingCreated,washingCreated,bagsCreated = false;
    private ProgressDialog progressDialog;
    private DatePickerDialog pickDate;
    private int date,month,year;
    private ArrayAdapter<String> collectionAdapter,ironingAdapter,washingAdapter,bagsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_place_order,container,false);

        Log.d(Constants.LOG_TAG, Constants.PlaceOrderFragment);

        customActionBar();
        getServerTime();


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


                        }
                        @Override
                        public void onResult(boolean result) {

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
        collectionTimeSlot = (Spinner) collectionLayout.findViewById(R.id.select_time_slot_included);

        ironingLayout = (CardView) v.findViewById(R.id.ironing_layout_place_order_fragment);
        ironingTitleText = (CustomTextView) ironingLayout.findViewById(R.id.service_included);
        ironingDateText = (CustomTextView) ironingLayout.findViewById(R.id.select_date_slot_included);
        ironingTimeSlot = (Spinner) ironingLayout.findViewById(R.id.select_time_slot_included);


        washingLayout = (CardView) v.findViewById(R.id.washing_layout_place_order_fragment);
        washingTitleText = (CustomTextView) washingLayout.findViewById(R.id.service_included);
        washingDateText = (CustomTextView) washingLayout.findViewById(R.id.select_date_slot_included);
        washingTimeSlot = (Spinner) washingLayout.findViewById(R.id.select_time_slot_included);

        bagsLayout = (CardView) v.findViewById(R.id.bags_layout_place_order_fragment);
        bagsTitleText = (CustomTextView) bagsLayout.findViewById(R.id.service_included);
        bagsDateText = (CustomTextView) bagsLayout.findViewById(R.id.select_date_slot_included);
        bagsTimeSlot = (Spinner) bagsLayout.findViewById(R.id.select_time_slot_included);



    }

    private void setViews(){

        newOrder.setText("NEW ORDER");
        newOrder.setOnClickListener(listener);

        placeOrder.setText("PLACE ORDER");
        placeOrder.setOnClickListener(listener);

        collectionDateText.setTag("date_1");
        collectionDateText.setOnClickListener(datelistener);

        ironingDateText.setTag("date_2");
        ironingDateText.setOnClickListener(datelistener);

        washingDateText.setTag("date_3");
        washingDateText.setOnClickListener(datelistener);

        bagsDateText.setTag("date_4");
        bagsDateText.setOnClickListener(datelistener);

//      collectionLayout.setVisibility(View.GONE);
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

//        if(((ironingSize==0)&&(washingSize==0))&&(bagsSize==0)){
//            collectionLayout.setVisibility(View.INVISIBLE);
//            Toast.makeText(getActivity().getApplicationContext(),"No order Placed",5000).show();
//        }

    }


    private void collectionDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        pickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth){
                    collectionDateText.setText("Today");
                    monthOfYear = month+1;
                    Constants.collectionDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    String collect = Constants.collectionDate;
                    setCollectionsAdapter(collect,"today");

                }
                else{
                    monthOfYear = month+1;
                    collectionDateText.setText(dayOfMonth+"-"+monthOfYear+"-"+yearofc);
                    Constants.collectionDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    String collect = Constants.collectionDate;
                    setCollectionsAdapter(collect, "later");
                }

            }

        },year, month, date);
        pickDate.show();
    }

    public void setCollectionsAdapter(String date,String when){

        ArrayList<String> collectionSlots = getSlots(date,when,"collection");
        if(collectionSlots.size() != 0) {
            collectionAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, collectionSlots);
            collectionAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            collectionTimeSlot.setAdapter(collectionAdapter);
        }
        else{

            Toast.makeText(getActivity().getApplicationContext(),"Collection cannot be done on the given date.Please select another date",5000).show();
        }
    }

    private void ironingDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        pickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {
                    ironingDateText.setText("Today");

                    monthOfYear = month+1;
                    Constants.ironingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    String collect = Constants.ironingDeliveryDate;
                    setIroningAdapter(collect,"today");

                }
                else{
                    monthOfYear = month+1;
                    ironingDateText.setText(dayOfMonth+"-"+monthOfYear+"-"+yearofc);
                    Constants.ironingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    String collect = Constants.ironingDeliveryDate;
                    setIroningAdapter(collect,"later");

                }
            }

        },year, month, date);
        pickDate.show();
    }

    public void setIroningAdapter(String date,String when){

        ArrayList<String> ironingSlots = getSlots(date,when,"ironing");
        if(ironingSlots.size()!=0) {
            ironingAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, ironingSlots);
            ironingAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            ironingTimeSlot.setAdapter(ironingAdapter);
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Delivery of Ironed clothes cannot be done on the given date.Please select another date",5000).show();
        }

    }

    private void washingDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        pickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {
                    washingDateText.setText("Today");

                    monthOfYear = month+1;
                    Constants.washingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    String collect = Constants.washingDeliveryDate;
                    setWashingAdapter(collect,"today");

                }
                else{
                    monthOfYear = month+1;
                    washingDateText.setText(dayOfMonth+"-"+monthOfYear+"-"+yearofc);
                    Constants.washingDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    String collect = Constants.washingDeliveryDate;
                    setWashingAdapter(collect,"later");

                }
            }

        },year, month, date);
        pickDate.show();
    }

    public void setWashingAdapter(String date,String when){

        ArrayList<String> washingSlots = getSlots(date,when,"washing");
        if(washingSlots.size()!=0) {
            washingAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, washingSlots);
            washingAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            washingTimeSlot.setAdapter(washingAdapter);
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Delivery of Washed Clothes cannot be done on the given date.Please select another date",5000).show();
        }

    }

    private void bagsDatePicker() {
        Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date   = c.get(Calendar.DAY_OF_MONTH);
        pickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int yearofc, int monthOfYear, int dayOfMonth) {

                if(year==yearofc && month==monthOfYear && date==dayOfMonth) {

                    monthOfYear = month+1;
                    bagsDateText.setText("Today");
                    Constants.bagsDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    String collect = Constants.bagsDeliveryDate;
                    setBagsAdapter(collect,"today");

                }
                else{
                    monthOfYear = month+1;
                    bagsDateText.setText(dayOfMonth+"-"+monthOfYear+"-"+yearofc);
                    Constants.bagsDeliveryDate = String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(yearofc);
                    String collect = Constants.bagsDeliveryDate;
                    setBagsAdapter(collect,"later");

                }
            }

        },year, month, date);
        pickDate.show();
    }

    public void setBagsAdapter(String date,String when){

        ArrayList<String> bagsSlots = getSlots(date,when,"bags");
        if(bagsSlots.size()!=0) {
            bagsAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout, bagsSlots);
            bagsAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
            bagsTimeSlot.setAdapter(bagsAdapter);
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Delivery of Bags and shoes cannot be done on the given date.Please select another date",5000).show();
        }
    }

    public ArrayList<String> getSlots(String date, String when,String service){

        ArrayList<String> options = new ArrayList<String>();
        try {
            Log.d(Constants.LOG_TAG, " Collection Date " + date);
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date MyDate = newDateFormat.parse(date);
            newDateFormat.applyPattern("EEEE");
            String day = newDateFormat.format(MyDate);

            Log.d(Constants.LOG_TAG," my DATE STRING "+ day);

            if(when.equalsIgnoreCase("today")){

                String presentTime = Constants.currentTime;
                day = day.toLowerCase();
                String availableSlots = (String)Constants.slots.get(day);
                Log.d(Constants.LOG_TAG," Present Time "+presentTime);
                Log.d(Constants.LOG_TAG," Available slots "+availableSlots);

                String getSlots[] = availableSlots.split("_");
                for(int i=0 ;i<getSlots.length;i++){

                    int validSlot = Integer.parseInt(getSlots[i].substring(0, 2));
                    Log.d(Constants.LOG_TAG," Check Validity String "+ validSlot);
                    int now = Integer.parseInt(presentTime.substring(0,2));
                    Log.d(Constants.LOG_TAG," abc Strin "+now);

                    if(validSlot>now){

                        options.add(getSlots[i]);

                    }

                }
            } // if the date is set for today
            else{

                day = day.toLowerCase();
                String availableSlots = (String)Constants.slots.get(day);
                Log.d(Constants.LOG_TAG," Available slots "+availableSlots);

                String getSlots[] = availableSlots.split("_");
                if(service.equalsIgnoreCase("collection")){

                    for(int i=0;i<getSlots.length;i++){

                        options.add(getSlots[i]);
                    }

                }
                // check what service is it
                else if(service.equalsIgnoreCase("ironing")){

                }
                else if(service.equalsIgnoreCase("washing")){

                }
                else if(service.equalsIgnoreCase("bags")){

                }
                // Which day is set for collections then if it is the next day do step 1 2 3 if not display all slots
                // collection 1
                // if it is comming for ironing 1
                //  washing 2
                // bags = slotDifference 3

            }

        }

        catch (Exception e){
            e.printStackTrace();
        }

        // return the array of available slots
        return options;
    }

    public void placeOrder(){

        createJson();
        new PlaceWeeklyOrderAsyncTask(getActivity().getApplicationContext(),new PlaceWeeklyOrderAsyncTask.PlaceWeeklyOrderCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.LOG_TAG);
                progressDialog.setMessage("Placing Your Order");
                progressDialog.show();
            }

            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){
                    Toast.makeText(getActivity().getApplicationContext(),"Order Placed Successfully",5000).show();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Order couldnt be placed sucessfully\nTry Again Later",5000).show();
                }
            }
        }).execute(postOrderJsonObject);


    }



    // This function would be used to create JSON which contains the
    // users orders to be placed
    public void createJson(){

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
                    ironingNestedJsonObject.put("quantity",quantity);

                    ironingNestedJsonArray.put(ironingNestedJsonObject);

                }

                ironingJsonObject = new JSONObject();
                ironingJsonObject.put("user_delivery_date:","abcd");
                ironingJsonObject.put("user_delivery_slot:","xyz");
                ironingJsonObject.put("itemarr:",ironingNestedJsonArray);
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
                    washingNestedJsonObject.put("quantity",quantity);

                    washingNestedJsonArray.put(washingNestedJsonObject);
                }

                washingJsonObject = new JSONObject();
                washingJsonObject.put("user_delivery_date:","abcd");
                washingJsonObject.put("user_delivery_slot:","xyz");
                washingJsonObject.put("itemarr:",washingNestedJsonArray);


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
                    bagsNestedJsonObject.put("quantity",quantity);

                    bagsNestedJsonArray.put(bagsNestedJsonObject);

                }

                bagsJsonObject = new JSONObject();
                bagsJsonObject.put("user_delivery_date:","abcd");
                bagsJsonObject.put("user_delivery_slot:","xyz");
                bagsJsonObject.put("itemarr:",bagsNestedJsonArray);
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
            postOrderJsonObject.put("total_amt",Constants.totalAmountToBeCollected);
            postOrderJsonObject.put("totalquantity",Constants.totalQuantityToBeCollected);
            postOrderJsonObject.put("user_collection_time",Constants.collectionDate);
            postOrderJsonObject.put("user_collection_slot",12);
            postOrderJsonObject.put("items",itemsJsonArray);

            Log.d(Constants.LOG_TAG," Post Order Json "+ postOrderJsonObject);

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
