package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.ascenttechnovation.laundrize.async.RegisterUserViaSocialAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomEditText;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class MobileVerificationActivity extends Activity {

    private CustomButton confirmVerification,verifyNow;
    private String mobileNumber,name,emailId,password;
    private CustomEditText mobileNumberEdit,verificationCode;
    private ProgressDialog progressDialog;
    private String from,id,firstName,lastName,url;
    private String finalUrl,finalVerificationUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        Log.d(Constants.LOG_TAG,Constants.MobileVerificationActivity);

        Intent i = getIntent();
        from = i.getStringExtra("from");
        if(from.equalsIgnoreCase("register")){

            mobileNumber = i.getStringExtra("mobileNumber");
            name = i.getStringExtra("name");
            emailId = i.getStringExtra("emailId");
            password = i.getStringExtra("password");
        }
        else if(from.equalsIgnoreCase("facebook")){

            mobileNumber = "";
            id = i.getStringExtra("id");
            firstName = i.getStringExtra("firstName");
            lastName = i.getStringExtra("lastName");
            emailId = i.getStringExtra("email");

        }
        else if(from.equalsIgnoreCase("google")){

            Log.d(Constants.LOG_TAG,"INside Gooogle ");

            mobileNumber = "";
            id = i.getStringExtra("id");
            firstName = i.getStringExtra("firstName");
            lastName = i.getStringExtra("lastName");
            emailId = i.getStringExtra("email");

        }


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

            AlertDialog.Builder builder = new AlertDialog.Builder(MobileVerificationActivity.this);
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

        if(!mobileNumberEdit.getText().toString().equalsIgnoreCase("null")){
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
        else{

            Toast.makeText(getApplicationContext(),"Please enter a mobile number",5000).show();
        }


    }

    public void confirmVerification(){

        String verificationCodeValue = verificationCode.getText().toString();
        mobileNumber = mobileNumberEdit.getText().toString();

        // verificationCode value (Wo8zxW) has been harcoded

        if(from.equalsIgnoreCase("register")){

            finalVerificationUrl = Constants.confirmVerificationUrl+mobileNumber+"&verification_code="+verificationCodeValue+"&password="+password+"&first_name="+name+"&email="+emailId;

        }
        else if(from.equalsIgnoreCase("facebook")){

            finalVerificationUrl = Constants.registerViaFBUrl+id+"&mobile_number="+mobileNumber+"&password=null&verification_code="+verificationCodeValue+"&first_name="+firstName+"&last_name="+lastName+"&email="+emailId;

        }
        else if(from.equalsIgnoreCase("google")){

            finalVerificationUrl = Constants.registerViaGoogleUrl+id+"&mobile_number="+mobileNumber+"&password=null&verification_code="+verificationCodeValue+"&first_name="+firstName+"&last_name="+lastName+"&email="+emailId;

        }

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
        }).execute(finalVerificationUrl);

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
