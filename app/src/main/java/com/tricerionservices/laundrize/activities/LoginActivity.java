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
import com.tricerionservices.laundrize.async.FetchAddressAsyncTask;
import com.tricerionservices.laundrize.async.FetchUserProfileAsyncTask;
import com.tricerionservices.laundrize.async.SignInUserAsyncTask;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.custom.CustomEditText;
import com.tricerionservices.laundrize.custom.CustomTextView;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class LoginActivity extends Activity {

    private CustomEditText mobileNumber,password;
    private CustomTextView clickHere;
    private CustomButton logInNow;
    private ProgressDialog progressDialog;
    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(Constants.LOG_TAG,Constants.LoginActivity);

        findViews();
        Constants.reintializeTheValues(this);

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
            setClickListeners();
        }
        else{

            alertDialog.show();
        }
    }



    private void findViews(){

        mobileNumber = (CustomEditText) findViewById(R.id.username_edit_log_in_now_activity);
        password = (CustomEditText) findViewById(R.id.password_edit_log_in_now_activity);
        clickHere = (CustomTextView) findViewById(R.id.click_here_static_text_log_in_now_activity);
        logInNow = (CustomButton) findViewById(R.id.log_in_now_button_log_in_now_activity);

    }

    private void setClickListeners(){

        clickHere.setOnClickListener(listener);
        logInNow.setOnClickListener(listener);
    }

    public void clickHere(){

        Intent i = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
        startActivity(i);
    }

    public void logInNow(){

        final String phoneNumber = mobileNumber.getText().toString();
        final String pwd = password.getText().toString();

        String finalUrl = Constants.signInUrl+phoneNumber+"&password="+pwd;

        new SignInUserAsyncTask(getApplicationContext(),new SignInUserAsyncTask.SignInUserCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle(Constants.LOG_TAG);
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
                    editor.putString("loginRoute","register");
                    editor.putString("profileId", "NA");
                    editor.putString("userId",Constants.userId);
                    editor.putString("token",Constants.token);
                    editor.putString("phoneNumber",phoneNumber);
                    editor.putString("password",pwd);
                    editor.commit();


                    String url = Constants.fetchProfileUrl + Constants.userId;
                    new FetchUserProfileAsyncTask(getApplicationContext(),new FetchUserProfileAsyncTask.FetchUserProfileCallback() {
                        @Override
                        public void onStart(boolean status) {


                        }

                        @Override
                        public void onResult(boolean result) {
                            if (result) {

                                SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("profileName", Constants.profileName);
                                editor.commit();

                                Intent i = new Intent(LoginActivity.this, LandingActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Couldn't fetch your profile",5000).show();
                            }
                        }
                    }).execute(url);

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Not a valid user",5000).show();
                    }

                }
            }).execute(finalUrl);

        }

                View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){

                    case R.id.click_here_static_text_log_in_now_activity: clickHere();
                        break;
                    case R.id.log_in_now_button_log_in_now_activity: logInNow();
                        break;
                    default:
                        break;

                }

            }
        };
    }
