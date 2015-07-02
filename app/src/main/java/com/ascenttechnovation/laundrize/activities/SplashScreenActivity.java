package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ascenttechnovation.laundrize.R;

/**
 * Created by ADMIN on 20-06-2015.
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreenActivity.this,LogInOrRegisterActivity.class);
                startActivity(intent);
            }
        },3000);

    }
}
