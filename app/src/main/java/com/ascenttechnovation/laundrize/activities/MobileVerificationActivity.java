package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
public class MobileVerificationActivity extends Activity {

    private CustomButton confirmVerification,verifyNow;
    private String mobileNumber,name,emailId,password,from;
    private CustomEditText mobileNumberEdit,verificationCode;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        Log.d(Constants.LOG_TAG,Constants.MobileVerificationActivity);

        Intent i = getIntent();
        mobileNumber = i.getStringExtra("mobileNumber");
        name = i.getStringExtra("name");
        emailId = i.getStringExtra("emailId");
        password = i.getStringExtra("password");

        findViews();
        setViews();
    }

    private void findViews(){

        mobileNumberEdit = (CustomEditText) findViewById(R.id.mobileno_edit_verification_activity);
        verificationCode = (CustomEditText) findViewById(R.id.username_edit_verification_activity);
        verifyNow = (CustomButton) findViewById(R.id.verify_now_button_mobile_verification_activity);
        confirmVerification = (CustomButton) findViewById(R.id.confirm_verification_button_mobile_verification_activity);
    }

    private void setViews(){
        mobileNumberEdit.setText(mobileNumber);
        verifyNow.setOnClickListener(listener);
        confirmVerification.setOnClickListener(listener);
    }

    public void verifyNow(){

        String finalUrl = Constants.verifyNowUrl+mobileNumberEdit.getText().toString();
        new FetchVerificationCodeAsyncTask(getApplicationContext(), new FetchVerificationCodeAsyncTask.FetchVerificationCodeCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(MobileVerificationActivity.this);
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Loading,Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){
                    Toast.makeText(getApplicationContext()," You will receive a message shortly",5000).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"There was an error \nTry Again Later",5000).show();
                }

            }
        }).execute(finalUrl);

    }

    public void confirmVerification(){

        String verificationCodeValue = verificationCode.getText().toString();

        // verificationCode value (Wo8zxW) has been harcoded

        String finalUrl = Constants.confirmVerificationUrl+mobileNumber+"&verification_code="+verificationCodeValue+"&password="+password+"&first_name="+name+"&email="+emailId;

        new ConfirmVerificationAsyncTask(getApplicationContext(), new ConfirmVerificationAsyncTask.ConfirmVerificationCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(MobileVerificationActivity.this);
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Loading,Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    Intent i = new Intent(MobileVerificationActivity.this,LoginActivity.class);
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

                case R.id.verify_now_button_mobile_verification_activity: verifyNow();
                    break;
                case R.id.confirm_verification_button_mobile_verification_activity: confirmVerification();
                    break;

            }
        }
    };
}
