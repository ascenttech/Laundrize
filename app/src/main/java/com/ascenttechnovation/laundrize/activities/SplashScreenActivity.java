package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.BagLaundryData;
import com.ascenttechnovation.laundrize.data.DryCleanHouseholdsData;
import com.ascenttechnovation.laundrize.data.DryCleanWearablesData;
import com.ascenttechnovation.laundrize.data.IroningHouseholdsData;
import com.ascenttechnovation.laundrize.data.IroningWearablesData;
import com.ascenttechnovation.laundrize.data.NavigationDrawerData;
import com.ascenttechnovation.laundrize.data.OthersData;
import com.ascenttechnovation.laundrize.data.ShoeLaundryData;
import com.ascenttechnovation.laundrize.data.WashAndIronHouseholdsData;
import com.ascenttechnovation.laundrize.data.WashAndIronWearablesData;
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
        initializeHashMaps();
        setData();
        findViews();

        GifRun gifRun = new GifRun();
        gifRun.LoadGiff(surface,getApplicationContext(),R.drawable.animated_logo);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","null");
        token = sharedPreferences.getString("token","null");
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

//                    Intent intent = new Intent(SplashScreenActivity.this,LogInOrRegisterActivity.class);
                    Intent intent = new Intent(SplashScreenActivity.this,LandingActivity.class);
                    startActivity(intent);
                }
            },3000);

        }


    }

    public void initializeArrayList(){

        Constants.bagLaundryData = new ArrayList<BagLaundryData>();
        Constants.dryCleanHouseholdsData = new ArrayList<DryCleanHouseholdsData>();
        Constants.dryCleanWearablesData = new ArrayList<DryCleanWearablesData>();
        Constants.ironingHouseholdsData = new ArrayList<IroningHouseholdsData>();
        Constants.ironingWearablesData = new ArrayList<IroningWearablesData>();
        Constants.navigationDrawerData = new ArrayList<NavigationDrawerData>();
        Constants.othersData = new ArrayList<OthersData>();
        Constants.shoeLaundryData = new ArrayList<ShoeLaundryData>();
        Constants.washAndIronHouseholdsData = new ArrayList<WashAndIronHouseholdsData>();
        Constants.washAndIronWearablesData = new ArrayList<WashAndIronWearablesData>();


    }
    public void initializeHashMaps(){

        Constants.ironingWearables = new HashMap<String,String>();
        Constants.ironingHouseholds = new HashMap<String,String>();
        Constants.washAndIronWearables = new HashMap<String,String>();
        Constants.washAndIronHouseholds = new HashMap<String,String>();
        Constants.dryCleanWearables = new HashMap<String,String>();
        Constants.dryCleanHouseholds = new HashMap<String,String>();
        Constants.shoeLaundry = new HashMap<String,String>();
        Constants.bagLaundry = new HashMap<String,String>();
        Constants.others = new HashMap<String,String>();

    }

    private void setData(){

        for(int i =0;i<1;i++) {
            Constants.bagLaundryData.add(new BagLaundryData("abc",Constants.bagLaundryTitles[i],"abc","45","0"));
        }
        for(int i =0;i<5;i++) {
            Constants.dryCleanHouseholdsData.add(new DryCleanHouseholdsData("abc",Constants.dryCleanHouseholdsTitles[i],"def","37","0"));
        }
        for(int i =0;i<3;i++) {
            Constants.dryCleanWearablesData.add(new DryCleanWearablesData("abc",Constants.dryCleanWearablesTitles[i],"ghi","23","0"));
        }
        for(int i =0;i<2;i++) {
            Constants.ironingHouseholdsData.add(new IroningHouseholdsData("abc",Constants.ironingHouseholdsTitles[i],"jkl","7","0"));
        }
        for(int i =0;i<6;i++) {
            Constants.ironingWearablesData.add(new IroningWearablesData("abc",Constants.ironingWearablesTitles[i],"mno","19","0"));
        }
        for(int i =0;i<1;i++) {
            Constants.othersData.add(new OthersData("abc",Constants.othersTitles[i],"pqr","11","0"));
        }
        for(int i =0;i<4;i++) {
            Constants.shoeLaundryData.add(new ShoeLaundryData("abc",Constants.shoeLaundryTitles[i],"stu","175","0"));
        }
        for(int i =0;i<7;i++) {
            Constants.washAndIronHouseholdsData.add(new WashAndIronHouseholdsData("abc",Constants.washAndIronHouseholdsTitles[i],"vwx","45","0"));
        }
        for(int i =0;i<2;i++) {
            Constants.washAndIronWearablesData.add(new WashAndIronWearablesData("abc",Constants.washAndIronWearablesTitles[i],"yz","119","0"));
        }


    }

    private void findViews(){


        surface = (SurfaceView) findViewById(R.id.gif_surface_splash_screen_activity);

    }

    private void setViews(){



    }

}
