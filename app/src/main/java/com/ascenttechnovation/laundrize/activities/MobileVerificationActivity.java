package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ascenttechnovation.laundrize.R;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class MobileVerificationActivity extends Activity {

    Button confirmVerification,verifyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        findViews();
        setViews();
    }

    private void findViews(){

        verifyNow = (Button) findViewById(R.id.verify_now_button_mobile_verification_activity);
        confirmVerification = (Button) findViewById(R.id.confirm_verification_button_mobile_verification_activity);
    }

    private void setViews(){

        verifyNow.setOnClickListener(listener);
        confirmVerification.setOnClickListener(listener);
    }

    public void verifyNow(){

        Intent i = new Intent(MobileVerificationActivity.this,LandingActivity.class);
        startActivity(i);

    }

    public void confirmVerification(){

        Intent i = new Intent(MobileVerificationActivity.this,LandingActivity.class);
        startActivity(i);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.verify_now_button_mobile_verification_activity: verifyNow();
                    break;
                case R.id.confirm_verification_button_mobile_verification_activity: confirmVerification();
                    break;

            }
        }
    };
}
