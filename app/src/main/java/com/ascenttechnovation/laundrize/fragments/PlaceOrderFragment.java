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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.async.PlaceWeeklyOrderAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by ADMIN on 31-07-2015.
 */
public class PlaceOrderFragment extends Fragment {

    View v;
    private CardView ironingLayout,washingLayout,bagsLayout,collectionLayout;
    private CustomButton done;
    private ActionBar actionBar;
    private CustomTextView ironingTitleText,ironingDateText,washingTitleText,washingDateText,bagsTitleText,bagsDateText,collectionTitleText,collectionDateText;
    private Spinner collectionTimeSlot, ironingTimeSlot, washingTimeSlot, bagsTimeSlot;
    private CustomButton placeOrder,cancel;
    private JSONObject ironingNestedJsonObject,washingNestedJsonObject,bagsNestedJsonObject,ironingJsonObject,washingJsonObject,bagsJsonObject,postOrderJsonObject;
    private JSONArray ironingNestedJsonArray,washingNestedJsonArray,bagsNestedJsonArray,itemsJsonArray;
    private boolean ironingCreated,washingCreated,bagsCreated = false;
    private ProgressDialog progressDialog;
    private DatePickerDialog pickDate;
    private int date,month,year;
    private ArrayAdapter<CharSequence> collectionAdapter,ironingAdapter,washingAdapter,bagsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_place_order,container,false);

        Log.d(Constants.LOG_TAG, Constants.PlaceOrderFragment);

        customActionBar();
        findViews(v);
        setViews();
        inflateViews();

        return v;
    }

    public void inflateViews(){

        if(Constants.ironingOrderData.size()>0){

            ironingLayout.setVisibility(View.VISIBLE);

        }
        if(Constants.washingOrderData.size()>0){

            washingLayout.setVisibility(View.VISIBLE);
        }
        if(Constants.bagOrderData.size()>0){

            bagsLayout.setVisibility(View.VISIBLE);
        }

    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Place Order");

    }

    private void findViews(View v){

        placeOrder = (CustomButton) v.findViewById(R.id.right_button_included);

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

        collectionDateText.setTag("date_1");
        collectionDateText.setOnClickListener(datelistener);

        ironingDateText.setTag("date_2");
        ironingDateText.setOnClickListener(datelistener);

        washingDateText.setTag("date_3");
        washingDateText.setOnClickListener(datelistener);

        bagsDateText.setTag("date_4");
        bagsDateText.setOnClickListener(datelistener);

        ironingLayout.setVisibility(View.GONE);
        washingLayout.setVisibility(View.GONE);
        bagsLayout.setVisibility(View.GONE);

        collectionTitleText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_collection,0,0,0);

        collectionTitleText.setText("Collection");
        ironingTitleText.setText("Delivery : Ironing");
        washingTitleText.setText("Delivery : Washables");
        bagsTitleText.setText("Delivery : Bags & Shoes");

        placeOrder.setOnClickListener(listener);

        setTheAdapters();

    }

    public void setTheAdapters(){

        collectionAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.time_slot, android.R.layout.simple_spinner_item);

        ironingAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.time_slot, android.R.layout.simple_spinner_item);

        washingAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.time_slot, android.R.layout.simple_spinner_item);

        bagsAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.time_slot, android.R.layout.simple_spinner_item);

        collectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ironingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        washingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bagsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        collectionTimeSlot.setAdapter(collectionAdapter);
        ironingTimeSlot.setAdapter(ironingAdapter);
        washingTimeSlot.setAdapter(washingAdapter);
        bagsTimeSlot.setAdapter(bagsAdapter);
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
            postOrderJsonObject.put("addr_id",12);
            postOrderJsonObject.put("total_amt",350);
            postOrderJsonObject.put("totalquantity",12);
            postOrderJsonObject.put("user_collection_time",12);
            postOrderJsonObject.put("user_collection_slot",12);
            postOrderJsonObject.put("items",itemsJsonArray);

            Log.d(Constants.LOG_TAG," Post Order Json "+ postOrderJsonObject);

        }
        catch (Exception e){

            e.printStackTrace();
        }


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

            placeOrder();
        }
    };

}
