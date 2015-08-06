package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.async.ConfirmVerificationAsyncTask;
import com.ascenttechnovation.laundrize.async.FetchVerificationCodeAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomEditText;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class ForgotPasswordActivity extends Activity {

    private CustomButton verifyDetailsButton,submitButton;
    private CustomEditText mobileNumberEdit,newPasswordEdit,confirmPasswordEdit,verificationCodeEdit;
    private ProgressDialog progressDialog;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Log.d(Constants.LOG_TAG,Constants.ForgotPasswordActivity);

        findViews();
        setViews();
    }

    private void findViews(){

        mobileNumberEdit = (CustomEditText) findViewById(R.id.mobileno_edit_forgot_password_activity);
        newPasswordEdit = (CustomEditText) findViewById(R.id.new_password_edit_forgot_password_activity);
        confirmPasswordEdit = (CustomEditText) findViewById(R.id.confirm_password_edit_forgot_password_activity);
        verificationCodeEdit = (CustomEditText) findViewById(R.id.verify_code_edit_forgot_password_activity);
        verifyDetailsButton = (CustomButton) findViewById(R.id.verify_details_button_forgot_password_activity);
        submitButton =(CustomButton) findViewById(R.id.submit_button_forgot_password_activity);
    }

    private void setViews(){

        verifyDetailsButton.setOnClickListener(listener);
        submitButton.setOnClickListener(listener);
    }

    public void verifyNow(){

        String s1=newPasswordEdit.getText().toString();
        String s2=confirmPasswordEdit.getText().toString();
        if(s1.equals(s2))
        {
            String finalUrl = Constants.verifyNowUrl + mobileNumberEdit.getText().toString();
            new FetchVerificationCodeAsyncTask(getApplicationContext(), new FetchVerificationCodeAsyncTask.FetchVerificationCodeCallback() {
                @Override
                public void onStart(boolean status) {

                    progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
                    progressDialog.setTitle(Constants.APP_NAME);
                    progressDialog.setMessage("Loading,Please Wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                }

                @Override
                public void onResult(boolean result) {

                    progressDialog.dismiss();
                    if (result) {
                        Toast.makeText(getApplicationContext(), " You will receive a message shortly", 5000).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "There was an error \nTry Again Later", 5000).show();
                    }

                }
            }).execute(finalUrl);
        }
        else
        {
            newPasswordEdit.setText("");
            confirmPasswordEdit.setText("");
            Toast.makeText(getApplicationContext(), " New Password and Confirm Password does not Match ", 5000).show();
        }
    }

    public void confirmVerification(){

        String verificationCode = prefs.getString("vc","");
        if(verificationCode.equals(verificationCodeEdit.getText().toString()))
        {

        }
//        String verificationCodeValue = verificationCode.getText().toString();
//
//        String finalUrl = Constants.confirmVerificationUrl+mobileNumber+"&verification_code="+verificationCodeValue+"&password="+password+"&first_name="+name+"&email="+emailId;
//
//        new ConfirmVerificationAsyncTask(getApplicationContext(), new ConfirmVerificationAsyncTask.ConfirmVerificationCallback() {
//            @Override
//            public void onStart(boolean status) {
//
//                progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
//                progressDialog.setTitle(Constants.APP_NAME);
//                progressDialog.setMessage("Loading,Please Wait...");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//            }
//            @Override
//            public void onResult(boolean result) {
//
//                progressDialog.dismiss();
//                if(result){
//
//                    Intent i = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
//                    startActivity(i);
//
//                }
//                else{
//                    Toast.makeText(getApplicationContext()," Enter a valid code",5000).show();
//                }
//            }
//        }).execute(finalUrl);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.verify_details_button_forgot_password_activity: verifyNow();
                    break;
                case R.id.submit_button_forgot_password_activity: confirmVerification();
                    break;

            }
        }
    };
}
