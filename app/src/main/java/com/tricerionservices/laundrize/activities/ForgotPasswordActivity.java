package com.tricerionservices.laundrize.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.async.ConfirmVerificationForPasswordRecoveryAsyncTask;
import com.tricerionservices.laundrize.async.FetchPasswordForRecoveryAsyncTask;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.custom.CustomEditText;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class ForgotPasswordActivity extends Activity {

    //    private CustomButton verifyDetailsButton,submitButton;
    private CustomButton submitButton;
    private Button verifyDetailsButton;
    private CustomEditText mobileNumberEdit,newPasswordEdit,confirmPasswordEdit,verificationCodeEdit;
    private ProgressDialog progressDialog;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Log.d(Constants.LOG_TAG,Constants.ForgotPasswordActivity);

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean internetAvailable = Constants.isInternetAvailable(getApplicationContext());
        if(internetAvailable){

            findViews();
            setViews();
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
            builder.setMessage("This app requires app connection")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            dialog.dismiss();
                        }
                    });
            builder.create();
            builder.show();

        }
    }

    private void findViews(){

        mobileNumberEdit = (CustomEditText) findViewById(R.id.mobileno_edit_forgot_password_activity);
        newPasswordEdit = (CustomEditText) findViewById(R.id.new_password_edit_forgot_password_activity);
        confirmPasswordEdit = (CustomEditText) findViewById(R.id.confirm_password_edit_forgot_password_activity);
        verificationCodeEdit = (CustomEditText) findViewById(R.id.verify_code_edit_forgot_password_activity);
//        verifyDetailsButton = (CustomButton) findViewById(R.id.verify_details_button_forgot_password_activity);
        verifyDetailsButton = (Button) findViewById(R.id.verify_details_button_forgot_password_activity);
        submitButton =(CustomButton) findViewById(R.id.submit_button_forgot_password_activity);
    }

    private void setViews(){

        verifyDetailsButton.setOnClickListener(listener);
        submitButton.setOnClickListener(listener);
    }

    public void verifyNow(){

        String s1=newPasswordEdit.getText().toString();
        String s2=confirmPasswordEdit.getText().toString();
        Log.d(Constants.LOG_TAG," Mobile Number length "+mobileNumberEdit.getText().length());
        if(mobileNumberEdit.getText().length()== 10){
            if(s1.equals(s2))
            {
                String finalUrl = Constants.forgotPasswordUrl + mobileNumberEdit.getText().toString();
                new FetchPasswordForRecoveryAsyncTask(getApplicationContext(), new FetchPasswordForRecoveryAsyncTask.FetchPasswordForRecoveryCallback() {
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
                            verificationCodeEdit.setVisibility(View.VISIBLE);
                            submitButton.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), " You will receive a message shortly", 5000).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Number already registered.", 5000).show();
                        }

                    }
                }).execute(finalUrl);
            }
            else
            {
                newPasswordEdit.setText("");
                confirmPasswordEdit.setText("");
                Toast.makeText(getApplicationContext(), "Password does not match", 5000).show();
            }

        }
        else{
            Toast.makeText(getApplicationContext(),"Mobile number should be 10 digit",5000).show();
        }
    }

    public void confirmVerification(){

//
        String verificationCodeValue = verificationCodeEdit.getText().toString();
        String mobileNumberValue = mobileNumberEdit.getText().toString();
        String password = newPasswordEdit.getText().toString();

        String finalUrl = Constants.resetPasswordUrl+mobileNumberValue+"&new_password="+password+"&verification_code="+verificationCodeValue;

        new ConfirmVerificationForPasswordRecoveryAsyncTask(getApplicationContext(), new ConfirmVerificationForPasswordRecoveryAsyncTask.ConfirmVerificationForPasswordRecoveryCallback() {
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
                if(result){

                    Toast.makeText(getApplicationContext(),"Password successfully changed",5000).show();
                    Intent i = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                    startActivity(i);

                }
                else{
                    Toast.makeText(getApplicationContext()," Enter a valid code",5000).show();
                }
            }
        }).execute(finalUrl);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.verify_details_button_forgot_password_activity:
                    verifyNow();
                    break;
                case R.id.submit_button_forgot_password_activity: confirmVerification();
                    break;

            }
        }
    };
}
