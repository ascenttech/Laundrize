package com.tricerionservices.laundrize.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.async.CheckIfUserExistsAsyncTask;
import com.tricerionservices.laundrize.async.SignInUserAsyncTask;
import com.tricerionservices.laundrize.data.AddressData;
import com.tricerionservices.laundrize.data.BagOrderData;
import com.tricerionservices.laundrize.data.CompletedOrdersData;
import com.tricerionservices.laundrize.data.GeneralData;
import com.tricerionservices.laundrize.data.IroningOrderData;
import com.tricerionservices.laundrize.data.LaundryServicesMainCategoryData;
import com.tricerionservices.laundrize.data.LaundryServicesSubCategoryData;
import com.tricerionservices.laundrize.data.NavigationDrawerData;
import com.tricerionservices.laundrize.data.TrackOrdersData;
import com.tricerionservices.laundrize.data.WashingOrderData;
import com.tricerionservices.laundrize.utils.Constants;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ADMIN on 20-06-2015.
 */
public class SplashScreenActivity extends Activity {

    private ImageView logo;
    private ProgressDialog progressDialog;
    private String finalUrl,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d(Constants.LOG_TAG,Constants.SplashScreenActivity);

        initializeArrayList();
        intializeHashMap();
        findViews();
        setViews();

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        Constants.loginRoute = sharedPreferences.getString("loginRoute", "null");
        Constants.profileId = sharedPreferences.getString("profileId", "null");
        Constants.userId = sharedPreferences.getString("userId","null");
        Constants.token = sharedPreferences.getString("token","null");
        Constants.password = sharedPreferences.getString("password","null");
        Constants.phoneNumber = sharedPreferences.getString("phoneNumber","null");


        if(Constants.loginRoute.equalsIgnoreCase("register")){

            finalUrl = Constants.signInUrl+Constants.phoneNumber+"&password="+Constants.password;
            type ="register";
            new SignInUserAsyncTask(getApplicationContext(),new SignInUserAsyncTask.SignInUserCallback() {
                @Override
                public void onStart(boolean status) {


                }
                @Override
                public void onResult(boolean result) {

                    if(result){

                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("loginRoute",type);
                        editor.putString("profileId",Constants.profileId);
                        editor.putString("userId",Constants.userId);
                        editor.putString("token",Constants.token);
                        editor.putString("password",Constants.password);
                        editor.putString("phoneNumber",Constants.phoneNumber);
                        editor.commit();

                        Intent i = new Intent(SplashScreenActivity.this,LandingActivity.class);
                        startActivity(i);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Not a valid user", 5000).show();
                    }

                }
            }).execute(finalUrl);


        }
        else if(Constants.loginRoute.equalsIgnoreCase("facebook")||Constants.loginRoute.equalsIgnoreCase("google")){

            if(Constants.loginRoute.equalsIgnoreCase("facebook")){
                finalUrl = Constants.checkUserExistsUrl+Constants.profileId+"&type=FB";
                type= "facebook";
            }
            else if(Constants.loginRoute.equalsIgnoreCase("google")){

                finalUrl = Constants.checkUserExistsUrl+Constants.profileId+"&type=GP";
                type= "google";
            }

            new CheckIfUserExistsAsyncTask(getApplicationContext(),new CheckIfUserExistsAsyncTask.CheckIfUserExistsCallback() {
                @Override
                public void onStart() {


                }
                @Override
                public void onResult(boolean result) {

                    if(result){


                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("loginRoute",type);
                        editor.putString("profileId",Constants.profileId);
                        editor.putString("userId",Constants.userId);
                        editor.putString("token",Constants.token);
                        editor.putString("mobileNumber","NA");
                        editor.putString("password","NA");
                        editor.commit();


                        Intent i = new Intent(SplashScreenActivity.this,LandingActivity.class);
                        startActivity(i);
                    }
                    else{

                        Toast.makeText(getApplicationContext(),"There was an error.\nTry Again After Some time",5000).show();

                    }
                }
            }).execute(finalUrl);

        }
        else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i = new Intent(SplashScreenActivity.this,LogInOrRegisterActivity.class);
                    startActivity(i);
                }
            },3000);


        }


    }



    public void initializeArrayList(){

        Log.d(Constants.LOG_TAG," Initializng the arrayList");

        Constants.subCategory = new ArrayList<GeneralData>();
        Constants.addressData = new ArrayList<AddressData>();
        Constants.bagLaundryData = new ArrayList<GeneralData>();
        Constants.dryCleanHouseholdsData = new ArrayList<GeneralData>();
        Constants.dryCleanWearablesData = new ArrayList<GeneralData>();
        Constants.ironingHouseholdsData = new ArrayList<GeneralData>();
        Constants.ironingWearablesData = new ArrayList<GeneralData>();
        Constants.laundryServicesMainCategory = new ArrayList<LaundryServicesMainCategoryData>();
        Constants.laundryServicesSubCategory = new ArrayList<LaundryServicesSubCategoryData>();
        Constants.navigationDrawerData = new ArrayList<NavigationDrawerData>();
        Constants.othersData = new ArrayList<GeneralData>();
        Constants.shoeLaundryData = new ArrayList<GeneralData>();
        Constants.washAndIronHouseholdsData = new ArrayList<GeneralData>();
        Constants.washAndIronWearablesData = new ArrayList<GeneralData>();
        Constants.ironingOrderData = new ArrayList<IroningOrderData>();
        Constants.bagOrderData = new ArrayList<BagOrderData>();
        Constants.washingOrderData = new ArrayList<WashingOrderData>();
        Constants.trackOrdersData = new ArrayList<TrackOrdersData>();
        Constants.completedOrdersData = new ArrayList<CompletedOrdersData>();
        Constants.areas = new ArrayList<String>();
        Constants.cities = new ArrayList<String>();
        Constants.zipcodes = new ArrayList<String>();
        Constants.weekdays = new ArrayList<String>();
        Constants.weekdays.add("Monday");
        Constants.weekdays.add("Tuesday");
        Constants.weekdays.add("Wednesday");
        Constants.weekdays.add("Thursday");
        Constants.weekdays.add("Friday");
        Constants.weekdays.add("Saturday");
        Constants.weekdays.add("Sunday");



    }

    public void intializeHashMap(){

        Log.d(Constants.LOG_TAG," Initializng the hashmap");

        Constants.areasMap = new HashMap<String,String>();
        Constants.citiesMap = new HashMap<String,String>();
        Constants.servicesName = new HashMap<String,String>();
        Constants.zipcodesMap = new HashMap<String,String>();
        Constants.order = new HashMap<String,String>();
        Constants.slots = new HashMap<String,String>();
        Constants.getSlotsId = new HashMap<String,String>();
        Constants.getSlotsId.put("08:00 AM - 10:00 AM","1");
        Constants.getSlotsId.put("10:00 AM - 12:00 PM","2");
        Constants.getSlotsId.put("12:00 PM - 14:00 PM","3");
        Constants.getSlotsId.put("14:00 PM - 16:00 PM","4");
        Constants.getSlotsId.put("16:00 PM - 18:00 PM","5");
        Constants.getSlotsId.put("18:00 PM - 20:00 PM","6");
        Constants.getSlotsId.put("20:00 PM - 22:00 PM","7");

    }

    private void findViews(){


        logo = (ImageView) findViewById(R.id.animated_logo_image_splash_screen_activity);

    }

    private void setViews(){


        Ion.with(logo).load("android.resource://com.ascenttechnovation.laundrize/" + R.drawable.animated_logo);

    }

}
