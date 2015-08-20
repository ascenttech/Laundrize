package com.ascenttechnovation.laundrize.activities;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.async.FetchAddressAsyncTask;
import com.ascenttechnovation.laundrize.async.SignInUserAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomEditText;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class LoginActivity extends Activity {

    private CustomEditText mobileNumber,password;
    private CustomTextView clickHere;
    private CustomButton logInNow;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(Constants.LOG_TAG,Constants.LoginActivity);

        findViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean internetAvailable = Constants.isInternetAvailable(getApplicationContext());
        if(internetAvailable){

            setClickListeners();
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

                    Intent i = new Intent(LoginActivity.this,LandingActivity.class);
                    startActivity(i);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Not a valid user",5000).show();
                }

            }
        }).execute(finalUrl,phoneNumber,pwd);



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
