package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.data.LandingFragmentData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 20-06-2015.
 */
public class SplashScreenActivity extends Activity {

    WebView webView;
    String userId,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d(Constants.LOG_TAG,Constants.SplashScreenActivity);

        initializeArrayList();
        findViews();

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

                    Intent intent = new Intent(SplashScreenActivity.this,LoginOrRegisterActivity.class);
                    startActivity(intent);
                }
            },3000);

        }


    }

    public void initializeArrayList(){

        Constants.landingFragmentData = new ArrayList<LandingFragmentData>();

    }

    private void findViews(){

//        webView = (WebView) findViewById(R.id.logo_web_view_splash_screen_activity);
    }

    private void setViews(){

//        webView.loadUrl("file:///drawable/logo.gif");
    }

}
