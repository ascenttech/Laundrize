package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class LogInActivity extends Activity {

    TextView clickHere,logInNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        setViews();

    }

    private void findViews(){

        clickHere = (TextView) findViewById(R.id.click_here_static_text_log_in_now_activity);
        logInNow = (Button) findViewById(R.id.log_in_now_button_log_in_now_activity);

    }

    private void setViews(){

        clickHere.setOnClickListener(listener);
        logInNow.setOnClickListener(listener);
    }

    public void clickHere(){

        Intent i = new Intent(LogInActivity.this,ForgotPasswordActivity.class);
        startActivity(i);
    }

    public void logInNow(){

        Intent i = new Intent(LogInActivity.this,LandingActivity.class);
        startActivity(i);

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
