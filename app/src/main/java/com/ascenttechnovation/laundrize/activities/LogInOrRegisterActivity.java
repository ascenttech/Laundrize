package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 29-06-2015.
 */
public class LoginOrRegisterActivity extends Activity {

    Button signInNow,registerNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        Log.d(Constants.LOG_TAG,Constants.LoginOrRegisterActivity);

        findViews();
        setViews();
    }

    private void findViews(){

        signInNow = (Button) findViewById(R.id.sign_in_now_button_login_or_register_activity);
        registerNow = (Button) findViewById(R.id.register_now_button_login_or_register_activity);

    }

    private void setViews(){

        signInNow.setOnClickListener(listener);
        registerNow.setOnClickListener(listener);

    }

    public void signInNow(){

        Intent i = new Intent(LoginOrRegisterActivity.this,LoginActivity.class);
        startActivity(i);
    }
    public void registerNow(){

        Intent i = new Intent(LoginOrRegisterActivity.this,RegisterActivity.class);
        startActivity(i);

    }



    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.sign_in_now_button_login_or_register_activity: signInNow();
                    break;
                case R.id.register_now_button_login_or_register_activity: registerNow();
                    break;
                default :
                    break;
            }

        }
    };
}
