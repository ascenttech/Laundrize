package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.async.SignInUserAsyncTask;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class LoginActivity extends Activity {

    private EditText mobileNumber,password;
    private TextView clickHere,logInNow;
    private ProgressDialog progressDialog;
    private String finalUrl;
    private CheckBox keepLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(Constants.LOG_TAG,Constants.LoginActivity);

        findViews();
        setViews();

    }

    private void findViews(){

        mobileNumber = (EditText) findViewById(R.id.username_edit_log_in_now_activity);
        password = (EditText) findViewById(R.id.password_edit_log_in_now_activity);
        keepLoggedIn = (CheckBox) findViewById(R.id.keep_logged_in_checkbox_log_in_now_activity);
        clickHere = (TextView) findViewById(R.id.click_here_static_text_log_in_now_activity);
        logInNow = (Button) findViewById(R.id.log_in_now_button_log_in_now_activity);

    }

    private void setViews(){

        clickHere.setOnClickListener(listener);
        logInNow.setOnClickListener(listener);
    }

    public void clickHere(){

        Intent i = new Intent(LoginActivity.this,MobileVerificationActivity.class);
        i.putExtra("from","forgotPassword");
        startActivity(i);
    }

    public void logInNow(){

        String phoneNumber = mobileNumber.getText().toString();
        String pwd = password.getText().toString();

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

                    if(keepLoggedIn.isChecked()){

                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId",Constants.userId);
                        editor.putString("token",Constants.token);
                        editor.commit();
                    }
                    Intent i = new Intent(LoginActivity.this,LandingActivity.class);
                    startActivity(i);

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
