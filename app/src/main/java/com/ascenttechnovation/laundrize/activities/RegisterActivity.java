package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ascenttechnovation.laundrize.R;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class RegisterActivity extends Activity {

    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
        setViews();
    }

    private void findViews(){

        signUp = (Button) findViewById(R.id.signup_button_register_activity);
    }

    private void setViews(){

        signUp.setOnClickListener(listener);
    }

    public void signUp(){

        Intent i = new Intent(RegisterActivity.this,LandingActivity.class);
        startActivity(i);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.signup_button_register_activity : signUp();
                    break;
                default:
                    break;
            }
        }
    };

}
