package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class ForgotPasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Log.d(Constants.LOG_TAG,Constants.ForgotPasswordActivity);
    }
}
