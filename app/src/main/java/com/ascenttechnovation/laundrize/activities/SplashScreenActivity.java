package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.async.FetchAllSlotsAsyncTask;
import com.ascenttechnovation.laundrize.data.AddressData;
import com.ascenttechnovation.laundrize.data.BagOrderData;
import com.ascenttechnovation.laundrize.data.CompletedOrdersData;
import com.ascenttechnovation.laundrize.data.GeneralData;
import com.ascenttechnovation.laundrize.data.IroningOrderData;
import com.ascenttechnovation.laundrize.data.LaundryServicesMainCategoryData;
import com.ascenttechnovation.laundrize.data.LaundryServicesSubCategoryData;
import com.ascenttechnovation.laundrize.data.NavigationDrawerData;
import com.ascenttechnovation.laundrize.data.TrackOrdersData;
import com.ascenttechnovation.laundrize.data.WashingOrderData;
import com.ascenttechnovation.laundrize.utils.Constants;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ADMIN on 20-06-2015.
 */
public class SplashScreenActivity extends Activity {

    private ImageView logo;
    String userId,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d(Constants.LOG_TAG,Constants.SplashScreenActivity);

        fetchAreasCitiesPincodes();
        initializeArrayList();
        intializeHashMap();
        findViews();
        setViews();

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","null");
        token = sharedPreferences.getString("token","null");
        // if user Id is not equal null then we move to landing Activity
        if(!userId.equalsIgnoreCase("null")){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashScreenActivity.this,LandingActivity.class);
                    startActivity(intent);
                }
            },3000);

        }
        else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashScreenActivity.this,LogInOrRegisterActivity.class);
//                    Intent intent = new Intent(SplashScreenActivity.this,LandActivity.class);
                    startActivity(intent);
                }
            },3000);

        }


    }

    private void fetchAreasCitiesPincodes(){



    }



    public void initializeArrayList(){

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
        Constants.pincodes = new ArrayList<String>();


    }

    public void intializeHashMap(){

        Constants.order = new HashMap<String,String>();
        Constants.slots = new HashMap<String,String>();
        Constants.getSlotsId = new HashMap<String,String>();
        Constants.getSlotsId.put("8:00 AM - 10:00 PM","1");
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
