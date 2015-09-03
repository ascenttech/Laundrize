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
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.async.ConfirmVerificationAsyncTask;
import com.tricerionservices.laundrize.async.FetchVerificationCodeAsyncTask;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.custom.CustomEditText;
import com.tricerionservices.laundrize.utils.Constants;

import java.net.URLEncoder;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class MobileVerificationActivity extends Activity {

    private CustomButton confirmVerification,verifyNow;
    private String mobileNumber,name,emailId,password;
    private CustomEditText mobileNumberEdit,verificationCode;
    private ProgressDialog progressDialog;
    private String from, profileId,firstName,lastName,url;
    private String finalUrl,finalVerificationUrl;
    String verificationCodeValue;
    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        Log.d(Constants.LOG_TAG,Constants.MobileVerificationActivity);

        Intent i = getIntent();
        from = i.getStringExtra("from");
        profileId = i.getStringExtra("profileId");
        firstName = i.getStringExtra("firstName");
        lastName = i.getStringExtra("lastName");
        emailId = i.getStringExtra("email");
        mobileNumber = i.getStringExtra("mobileNumber");
        password = i.getStringExtra("password");

        builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("This app requires internet connection");
        builder.setCancelable(false);
        builder.create();

        alertDialog = builder.create();


    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean internetAvailable = Constants.isInternetAvailable(getApplicationContext());
        if(internetAvailable){

            if(alertDialog.isShowing()){
                alertDialog.dismiss();
            }
            findViews();
            setViews();
        }
        else{

            alertDialog.show();
        }
    }

    private void findViews(){

        mobileNumberEdit = (CustomEditText) findViewById(R.id.mobileno_edit_verification_activity);
        verificationCode = (CustomEditText) findViewById(R.id.username_edit_verification_activity);
        verifyNow = (CustomButton) findViewById(R.id.verify_now_button_mobile_verification_activity);
        confirmVerification = (CustomButton) findViewById(R.id.confirm_verification_button_mobile_verification_activity);
    }

    private void setViews(){

        if(from.equalsIgnoreCase("register")){

            mobileNumberEdit.setText(mobileNumber);
        }

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
                        if(Constants.verificationCodeError != null){

                            Toast.makeText(getApplicationContext(),Constants.verificationCodeError,5000).show();
                        }
                        else{

                            Toast.makeText(getApplicationContext(),"There was an error \nTry Again Later",5000).show();
                        }


                    }

                }
            }).execute(finalUrl);

        }
        else{

            Toast.makeText(getApplicationContext(),"Please enter a mobile number",5000).show();
        }


    }

    public void confirmVerification(){

        try {
            verificationCodeValue = URLEncoder.encode(verificationCode.getText().toString(), "UTF-8");
            mobileNumber = URLEncoder.encode(mobileNumberEdit.getText().toString(), "UTF-8");

            // verificationCode value (Wo8zxW) has been harcoded

        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(from.equalsIgnoreCase("register")){

            finalVerificationUrl = Constants.confirmVerificationUrl+mobileNumber+"&verification_code="+verificationCodeValue+"&password="+password+"&first_name="+firstName+"&email="+emailId;

        }
        else if(from.equalsIgnoreCase("facebook")){

            finalVerificationUrl = Constants.registerViaFBUrl+ profileId +"&mobile_number="+mobileNumber+"&password=null&verification_code="+verificationCodeValue+"&first_name="+firstName+"&last_name="+lastName+"&email="+emailId;

        }
        else if(from.equalsIgnoreCase("google")){

            finalVerificationUrl = Constants.registerViaGoogleUrl+ profileId +"&mobile_number="+mobileNumber+"&password=null&verification_code="+verificationCodeValue+"&first_name="+firstName+"&last_name="+lastName+"&email="+emailId;

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

                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("loginRoute",from);
                    editor.putString("profileId", profileId);
                    editor.putString("userId",Constants.userId);
                    editor.putString("token",Constants.token);
                    editor.putString("mobileNumber",mobileNumber);
                    editor.putString("password",password);
                    editor.commit();

                    Intent i = new Intent(MobileVerificationActivity.this,LandingActivity.class);
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
