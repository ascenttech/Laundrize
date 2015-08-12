package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.utils.Constants;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by ADMIN on 29-06-2015.
 */
public class LogInOrRegisterActivity extends Activity {

    CustomButton signInNow,registerNow;
    int counter;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_or_register);

        Log.d(Constants.LOG_TAG,Constants.LoginOrRegisterActivity);
        // This will give you the HashKey that is sent to the facebook
        getHashKey();
        findViews();
        setSlider();

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean internetAvailable = Constants.isInternetAvailable(getApplicationContext());
        if(internetAvailable){

            setClickListeners();
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(LogInOrRegisterActivity.this);
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

        signInNow = (CustomButton) findViewById(R.id.sign_in_now_button_login_or_register_activity);
        registerNow = (CustomButton) findViewById(R.id.register_now_button_login_or_register_activity);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);


    }

    private void setSlider(){


        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.icon_delivery);
        file_maps.put("Big Bang Theory",R.drawable.icon_collection);
        file_maps.put("House of Cards",R.drawable.icon_minus);
        file_maps.put("Game of Thrones", R.drawable.icon_clock);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
//                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
//        mDemoSlider.addOnPageChangeListener(this);


    }

    private void setClickListeners(){

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
    public void onBackPressed() {
        counter++;
        if(counter%2 ==0){
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),"Press Back Again to Exit",5000).show();

        }
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
