package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.LandingFragmentData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 20-06-2015.
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d(Constants.LOG_TAG,Constants.SplashScreenActivity);

        initializeArrayList();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreenActivity.this,LoginOrRegisterActivity.class);
                startActivity(intent);
            }
        },3000);

    }

    public void initializeArrayList(){

        Constants.landingFragmentData = new ArrayList<LandingFragmentData>();

    }
}
