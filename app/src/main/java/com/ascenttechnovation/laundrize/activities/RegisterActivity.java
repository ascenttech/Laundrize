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
import com.ascenttechnovation.laundrize.async.RegisterUserAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomEditText;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class RegisterActivity extends Activity {

    private CustomButton signUp;
    private String finalUrl;
    private ProgressDialog progressDialog;
    private CustomEditText name,email,mobileNumber,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d(Constants.LOG_TAG,Constants.RegisterActivity);

        findViews();
        setViews();
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
        signUp.setOnClickListener(listener);
    }

    public void signUp(){

        Intent i = new Intent(RegisterActivity.this,MobileVerificationActivity.class);
        i.putExtra("name",name.getText().toString());
        i.putExtra("emailId",email.getText().toString());
        i.putExtra("mobileNumber",mobileNumber.getText().toString());
        i.putExtra("password",password.getText().toString());
        startActivity(i);

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
