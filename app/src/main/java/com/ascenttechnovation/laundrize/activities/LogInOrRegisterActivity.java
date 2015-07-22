package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.utils.Constants;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ADMIN on 29-06-2015.
 */
public class LogInOrRegisterActivity extends Activity {

    Button signInNow,registerNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_or_register);

        Log.d(Constants.LOG_TAG,Constants.LoginOrRegisterActivity);
        // This will give you the HashKey that is sent to the facebook
        getHashKey();
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

        Intent i = new Intent(LogInOrRegisterActivity.this,LoginActivity.class);
        startActivity(i);
    }
    public void registerNow(){

        Intent i = new Intent(LogInOrRegisterActivity.this,RegisterActivity.class);
        startActivity(i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void getHashKey(){

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.facebook.samples.hellofacebook",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

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
