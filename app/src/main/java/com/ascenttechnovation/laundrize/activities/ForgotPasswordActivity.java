package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class ForgotPasswordActivity extends Activity {

    private EditText oldPassword,newPassword,confirmPassword;
    private Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Log.d(Constants.LOG_TAG,Constants.ForgotPasswordActivity);

        findViews();
        setViews();
    }

    private void findViews(){

        oldPassword = (EditText) findViewById(R.id.old_password_edit_forgot_password_activity);
        newPassword = (EditText) findViewById(R.id.new_password_edit_forgot_password_activity);
        confirmPassword = (EditText) findViewById(R.id.confirm_password_edit_forgot_password_activity);
//        changePassword = (EditText) findViewById(R.id.footer_button_included);

    }

    private void setViews(){

        changePassword.setText("CHANGE BUTTON");

    }

}
