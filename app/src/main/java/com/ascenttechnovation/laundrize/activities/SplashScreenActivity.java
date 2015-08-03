package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
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
import com.ascenttechnovation.laundrize.gif.decoder.GifRun;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ADMIN on 20-06-2015.
 */
public class SplashScreenActivity extends Activity {

    SurfaceView surface;
    String userId,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d(Constants.LOG_TAG,Constants.SplashScreenActivity);


        initializeArrayList();
        findViews();
        getCredentials();

        GifRun gifRun = new GifRun();
        gifRun.LoadGiff(surface,getApplicationContext(),R.drawable.animated_logo);


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
//                    Intent intent = new Intent(SplashScreenActivity.this,LandingActivity.class);
                    startActivity(intent);
                }
            },3000);

        }


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

        // HashMap
        Constants.order = new HashMap<String,String>();
        Constants.slots = new HashMap<String,String>();


    }

    private void findViews(){


        surface = (SurfaceView) findViewById(R.id.gif_surface_splash_screen_activity);

    }

    public void getCredentials(){


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        Constants.userId = sharedPreferences.getString("userId","null");
        Constants.token = sharedPreferences.getString("token","null");
    };


    private void setViews(){



    }

}
