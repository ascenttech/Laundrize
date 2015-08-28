package com.tricerionservices.laundrize.fragments;

import android.app.DatePickerDialog;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.activities.LandingActivity;
import com.tricerionservices.laundrize.async.FetchCurrentServerTimeAsyncTask;
import com.tricerionservices.laundrize.async.WeeklyOrdersAsyncTask;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.custom.CustomTextView;
import com.tricerionservices.laundrize.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ADMIN on 31-07-2015.
 */
public class WeeklyOrderFragmentDynamic extends Fragment {

    View v;
    private CardView collectionLayout;
    private CustomButton newOrder,placeOrder;
    private ActionBar actionBar;
    private CustomTextView collectionTitleText,collectionDateText, collectionTimeText;
    private Spinner collectionDateSlot,collectionTimeSlot;
    private DatePickerDialog collectionDatePicker;
    private int date,month,year;
    private ArrayAdapter<String> collectionAdapter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_weekly_order,container,false);

        Log.d(Constants.LOG_TAG, Constants.WeeklyOrderFragement);

        customActionBar();
        getServerTime();

        return v;
    }


    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Weekly Order");

    }


    public void getServerTime(){

        new FetchCurrentServerTimeAsyncTask(new FetchCurrentServerTimeAsyncTask.FetchCurrentServerTimeCallBack() {
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

                    Constants.currentServerTimeFetched = true;
                    findViews(v);
                    setViews();
                    setAdapters();
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

        collectionDateText = (CustomTextView) collectionLayout.findViewById(R.id.select_date_slot_text);
        collectionDateSlot = (Spinner) collectionLayout.findViewById(R.id.select_date_slot_included);

        collectionTimeText = (CustomTextView) collectionLayout.findViewById(R.id.select_time_slot_text);
        collectionTimeSlot = (Spinner) collectionLayout.findViewById(R.id.select_time_slot_included);

        newOrder = (CustomButton) v.findViewById(R.id.left_button_included);
        placeOrder = (CustomButton) v.findViewById(R.id.right_button_included);

    }

    private void setViews(){

        collectionDateText.setVisibility(View.GONE);
        collectionDateSlot.setVisibility(View.VISIBLE);

        collectionTimeText.setVisibility(View.GONE);
        collectionTimeSlot.setVisibility(View.VISIBLE);

        collectionTitleText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_collection,0,0,0);
        collectionTitleText.setText("Collection");

        newOrder.setText("NEW ORDER");
        newOrder.setOnClickListener(listener);

        placeOrder.setText("PLACE ORDER");
        placeOrder.setOnClickListener(listener);

    }

    public void setAdapters(){

        Constants.todaysDay = getTodaysDay();
        ArrayAdapter<String> collectionAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout,Constants.weekdays);
        collectionAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
        collectionDateSlot.setAdapter(collectionAdapter);
        collectionDateSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Constants.weeklyOrderDay = adapterView.getSelectedItem().toString();
                if (Constants.weeklyOrderDay.equalsIgnoreCase(Constants.todaysDay))
                {
                    setTimeSlots(Constants.weeklyOrderDay, "today");
                }
                else{
                    setTimeSlots(Constants.weeklyOrderDay, "later");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void setTimeSlots(String day,String when){


        ArrayList<String> availableSlots = getSlots(day,when);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.row_spinner_layout,availableSlots);
        timeAdapter.setDropDownViewResource(R.layout.row_spinner_layout);
        collectionTimeSlot.setAdapter(timeAdapter);
        collectionTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Constants.collectionSlotId = Constants.getSlotsId.get(adapterView.getItemAtPosition(i).toString());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    public ArrayList<String> getSlots(String day, String when){


        ArrayList<String> options = new ArrayList<String>();
        try {

            String availableSlots = Constants.slots.get(day);

            String getSlots[] = availableSlots.split("_");
            if(when.equalsIgnoreCase("today")){

                String presentTime = Constants.currentTime;

                for(int i=0 ;i<getSlots.length;i++){

                    int validSlot = Integer.parseInt(getSlots[i].substring(0, 2));
                    int now = Integer.parseInt(presentTime.substring(0,2));

                    if(validSlot>now){

                        options.add(getSlots[i]);

                    }

                }

            } // if the date is set for today
            else{

                Log.d(Constants.LOG_TAG," Available slots "+availableSlots);
                String availableOptions[] = availableSlots.split("_");
                for(int i=0;i<availableOptions.length;i++){

                    options.add(availableOptions[i]);
                }

            }

        }

        catch (Exception e){
            e.printStackTrace();
        }

        // return the array of available slots
        return options;
    }


    public String getTodaysDay(){


        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
        return simpleDateFormat.format(c.getTime());

    }



    public void newOrder(){

        replaceFragment(new LandingFragment());

    }

    public void placeOrder(){

        formatDate();

        String finalUrl = Constants.weeklyOrderUrl+Constants.userId+"&address_id="+Constants.addressId+"&order_day="+Constants.weeklyOrderDay+"&order_slot="+ Constants.collectionSlotId;
        new WeeklyOrdersAsyncTask(getActivity().getApplicationContext(),new WeeklyOrdersAsyncTask.WeeklyOrdersCallback() {
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
                if(result){
                    showDialog();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Order couldnt be placed sucessfully\nTry Again Later",5000).show();
                }

            }
        }).execute(finalUrl);


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



    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.left_button_included : newOrder();
                    break;
                case R.id.right_button_included : placeOrder();
                    break;


            }

        }
    };


}
