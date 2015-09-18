package com.tricerionservices.laundrize.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.custom.CustomEditText;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class RegisterActivity extends Activity {

    private CustomButton signUp;
    private String finalUrl;
    private ProgressDialog progressDialog;
    private CustomEditText name,email,mobileNumber,password;
    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d(Constants.LOG_TAG,Constants.RegisterActivity);
        findViews();
        setViews();
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

        name = (CustomEditText) findViewById(R.id.name_edit_register_activity);
        email = (CustomEditText) findViewById(R.id.email_edit_register_activity);
        mobileNumber = (CustomEditText) findViewById(R.id.mobileno_edit_register_activity);
        password = (CustomEditText) findViewById(R.id.password_edit_register_activity);
        signUp = (CustomButton) findViewById(R.id.footer_button_included);
    }

    private void setViews(){

        signUp.setText("SIGN UP");

    }

    private void setClickListeners(){
        signUp.setOnClickListener(listener);
    }

    public void signUp(){

        if(!name.getText().toString().equalsIgnoreCase("")){

            if(!email.getText().toString().equalsIgnoreCase("")) {

                if (isValidEmail(email.getText().toString())) {

                    if (!mobileNumber.getText().toString().equalsIgnoreCase("")) {

                        if (!password.getText().toString().equalsIgnoreCase("")) {

                            Intent i = new Intent(RegisterActivity.this, MobileVerificationActivity.class);
                            i.putExtra("from", "register");
                            i.putExtra("profileId", "NA");
                            i.putExtra("firstName", name.getText().toString());
                            i.putExtra("lastName", "NA");
                            i.putExtra("email", email.getText().toString());
                            i.putExtra("mobileNumber", mobileNumber.getText().toString());
                            i.putExtra("password", password.getText().toString());
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), " You need to enter the password", 5000).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), " You need to enter the mobile number", 5000).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), " You need to enter an email id", 5000).show();
                }

            }
            else{
                Toast.makeText(getApplicationContext()," You need to enter a valid email address",5000).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext()," You need to enter a name",5000).show();
        }


    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.footer_button_included : signUp();
                    break;
                default:
                    break;
            }
        }
    };

}
