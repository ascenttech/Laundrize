package com.tricerionservices.laundrize.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.async.CheckIfUserExistsAsyncTask;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.utils.Constants;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by ADMIN on 29-06-2015.
 */
public class LogInOrRegisterActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    CustomButton signInNow,registerNow;
    int counter;
    private SliderLayout mDemoSlider;
    CallbackManager callbackManager;
    Button fb,google;
    LoginButton facebookLoginButton;
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    private ConnectionResult mConnectionResult;
    private SignInButton googleSignIn;
//    Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog progressDialog;
    private String googleName,googlePhotoUrl,googlePlusProfile,googleemail,googlefname,googlelname,googledisplayname,googleId;
    private boolean res;
    private boolean mSignInClicked;
    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // intialized the facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_or_register);

        Constants.reintializeTheValues(this);

//        / this is a callback manager for facebook
        callbackManager = CallbackManager.Factory.create();

        Log.d(Constants.LOG_TAG,Constants.LoginOrRegisterActivity);
        // This will give you the HashKey that is sent to the facebook
        getHashKey();
        findViews();
        setSlider();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();


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

        signInNow = (CustomButton) findViewById(R.id.sign_in_now_button_login_or_register_activity);
        registerNow = (CustomButton) findViewById(R.id.register_now_button_login_or_register_activity);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        google = (Button)findViewById(R.id.google);
        googleSignIn = (SignInButton) findViewById(R.id.google_button_login_or_register_activity);
        googleSignIn.setOnClickListener(listener);

        fb = (Button) findViewById(R.id.fb);
        facebookLoginButton =(LoginButton)findViewById(R.id.facebook_button_login_or_register_activity);
        facebookLoginButton.setReadPermissions("email");
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                    new GraphRequest(
                            AccessToken.getCurrentAccessToken(),
                            "/me",
                            null,
                            HttpMethod.GET,
                            new GraphRequest.Callback() {
                                public void onCompleted(GraphResponse response) {

                                    try {
                                        final String id = URLEncoder.encode(response.getJSONObject().getString("id"), "UTF-8");
                                        final String firstName = URLEncoder.encode(response.getJSONObject().getString("first_name"), "UTF-8");
                                        final String lastName = URLEncoder.encode(response.getJSONObject().getString("last_name"), "UTF-8");
                                        final String email = URLEncoder.encode(response.getJSONObject().getString("email"), "UTF-8");

                                        Log.d(Constants.LOG_TAG," Id "+ id);
                                        String finalUrl = Constants.checkUserExistsUrl+id+"&type="+"FB&device_id="+Constants.deviceId+"&gcm_id="+Constants.gcmToken;
//                                        String finalUrl = Constants.checkUserExistsUrl+id+"&type=FB";
                                        new CheckIfUserExistsAsyncTask(getApplicationContext(),new CheckIfUserExistsAsyncTask.CheckIfUserExistsCallback() {
                                            @Override
                                            public void onStart() {


                                            }
                                            @Override
                                            public void onResult(boolean result) {

                                                res = result;
                                                Log.d(Constants.LOG_TAG," Received result from background");

                                                if(result){


                                                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("loginRoute","facebook");
                                                    editor.putString("profileId",id);
                                                    editor.putString("userId",Constants.userId);
                                                    editor.putString("token",Constants.token);
                                                    editor.putString("mobileNumber","NA");
                                                    editor.putString("password","NA");
                                                    editor.commit();


                                                    Intent i = new Intent(LogInOrRegisterActivity.this,LandingActivity.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(i);
                                                }
                                                else{

                                                    Intent i = new Intent(LogInOrRegisterActivity.this, MobileVerificationActivity.class);
                                                    i.putExtra("from", "facebook");
                                                    i.putExtra("profileId", id);
                                                    i.putExtra("firstName", firstName);
                                                    i.putExtra("lastName", lastName);
                                                    i.putExtra("email", email);
                                                    i.putExtra("mobileNumber","NA");
                                                    i.putExtra("password","NA");
                                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(i);

                                                }
                                            }
                                        }).execute(finalUrl);


                                    }
                                    catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }).executeAsync();

                }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

    }

    private void setSlider(){


        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Fatest Delivery",R.drawable.icons_slider_1);
        file_maps.put("Best Quality Service",R.drawable.icons_slider_2);
        file_maps.put("Laundry Just a tap away",R.drawable.icons_slider_3);
        file_maps.put("Wide Range of Services", R.drawable.icons_slider_4);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
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
        mDemoSlider.setDuration(3000);
//        mDemoSlider.addOnPageChangeListener(this);


    }

    private void setClickListeners(){

        signInNow.setOnClickListener(listener);
        registerNow.setOnClickListener(listener);
        fb.setOnClickListener(listener);
        google.setOnClickListener(listener);
    }

    public void signInNow(){

        Intent i = new Intent(LogInOrRegisterActivity.this,LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
    public void registerNow(){

        Intent i = new Intent(LogInOrRegisterActivity.this,RegisterActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }

    }

    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }
    protected void onStop()
    {
        super.onStop();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution())
        {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
            if (mSignInClicked)
            {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    public void onConnected(Bundle arg0)
    {
        mSignInClicked = false;
        // Get user's information
        getProfileInformation();
        // Update the UI after signin
        updateUI(true);
    }

    private void getProfileInformation() {

        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {


                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);

                googlePlusProfile = currentPerson.getUrl();
                googleemail = Plus.AccountApi.getAccountName(mGoogleApiClient);
                googlefname = currentPerson.getName().getGivenName();
                googlelname = currentPerson.getName().getFamilyName();
                googleId = currentPerson.getId();

                googlePlusProfile = URLEncoder.encode(googlePlusProfile, "UTF-8");
                googleemail = URLEncoder.encode(googleemail, "UTF-8");
                googlefname = URLEncoder.encode(googlefname, "UTF-8");
                googlelname = URLEncoder.encode(googlelname, "UTF-8");
                googleId = URLEncoder.encode(googleId,"UTF-8");

                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                }

            }
        }
        catch(Exception e){

            e.printStackTrace();
        }
    }
    @Override
    public void onConnectionSuspended(int arg0)
    {
        mGoogleApiClient.connect();
        updateUI(false);
    }
    /**
     * Updating the UI, showing/hiding buttons and background_profile layout
     * */
    private void updateUI(boolean isSignedIn)
    {
        if (isSignedIn)
        {

            String finalUrl = Constants.checkUserExistsUrl+googleId+"&type="+"GP&device_id="+Constants.deviceId+"&gcm_id="+Constants.gcmToken;
            new CheckIfUserExistsAsyncTask(getApplicationContext(),new CheckIfUserExistsAsyncTask.CheckIfUserExistsCallback() {
                @Override
                public void onStart() {


                }
                @Override
                public void onResult(boolean result) {

                    res = result;
                    Log.d(Constants.LOG_TAG," Received result from background");

                    if(result){


                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("loginRoute","google");
                        editor.putString("profileId",googleId);
                        editor.putString("userId",Constants.userId);
                        editor.putString("token",Constants.token);
                        editor.putString("mobileNumber","NA");
                        editor.putString("password","NA");
                        editor.commit();


                        Intent i = new Intent(LogInOrRegisterActivity.this,LandingActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else{

                        Intent i = new Intent(LogInOrRegisterActivity.this, MobileVerificationActivity.class);
                        i.putExtra("from","google");
                        i.putExtra("profileId", googleId);
                        i.putExtra("firstName", googlefname);
                        i.putExtra("lastName", googlelname);
                        i.putExtra("email", googleemail);
                        i.putExtra("mobileNumber", "NA");
                        i.putExtra("password", "NA");
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }
                }
            }).execute(finalUrl);


        }
        else
        {

        }
    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }
    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }


    private void getHashKey(){

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ascenttechnovation.laundrize",
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
                case R.id.fb : facebookLoginButton.performClick();
                    break;
                case R.id.google : googleSignIn.performClick();
                    signInWithGplus();
                    break;

            }

        }
    };
}
