package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
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
import com.ascenttechnovation.laundrize.async.CheckIfUserExistsAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.utils.Constants;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // intialized the facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_or_register);


//        / this is a callback manager for facebook
        callbackManager = CallbackManager.Factory.create();

        Log.d(Constants.LOG_TAG,Constants.LoginOrRegisterActivity);
        // This will give you the HashKey that is sent to the facebook
        getHashKey();
        findViews();
        setSlider();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

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

//        google = (Button)findViewById(R.id.google);
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
                                        String id = URLEncoder.encode(response.getJSONObject().getString("id"), "UTF-8");
                                        String firstName = URLEncoder.encode(response.getJSONObject().getString("first_name"), "UTF-8");
                                        String lastName = URLEncoder.encode(response.getJSONObject().getString("last_name"), "UTF-8");
                                        String email = URLEncoder.encode(response.getJSONObject().getString("email"), "UTF-8");

                                        Log.d(Constants.LOG_TAG," Valued of already Registered "+isAlreadyRegistered(id,"FB"));

                                        if(isAlreadyRegistered(id,"FB")){

                                            Intent i = new Intent(LogInOrRegisterActivity.this,LandingActivity.class);
                                            startActivity(i);
                                        }else{
                                            Intent i = new Intent(LogInOrRegisterActivity.this, MobileVerificationActivity.class);
                                            i.putExtra("from", "facebook");
                                            i.putExtra("id", id);
                                            i.putExtra("firstName", firstName);
                                            i.putExtra("lastName", lastName);
                                            i.putExtra("email", email);
                                            startActivity(i);

                                        }

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
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
//        mDemoSlider.addOnPageChangeListener(this);


    }

    private void setClickListeners(){

        signInNow.setOnClickListener(listener);
        registerNow.setOnClickListener(listener);
        fb.setOnClickListener(listener);
//        google.setOnClickListener(listener);
    }

    public void signInNow(){

        Intent i = new Intent(LogInOrRegisterActivity.this,LoginActivity.class);
        startActivity(i);
    }
    public void registerNow(){

        Intent i = new Intent(LogInOrRegisterActivity.this,RegisterActivity.class);
        startActivity(i);

    }

    public boolean isAlreadyRegistered(String id,String type){


        String finalUrl = Constants.checkUserExistsUrl+id+"&type="+type;
        new CheckIfUserExistsAsyncTask(getApplicationContext(),new CheckIfUserExistsAsyncTask.CheckIfUserExistsCallback() {
            @Override
            public void onStart() {


            }
            @Override
            public void onResult(boolean result) {

                res = result;

            }
        }).execute(finalUrl);

        return res;

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

            if(isAlreadyRegistered(googleId,"GP")){

                Intent i = new Intent(LogInOrRegisterActivity.this,LandingActivity.class);
                startActivity(i);
            }
            else{

                Intent i = new Intent(LogInOrRegisterActivity.this, MobileVerificationActivity.class);
                i.putExtra("from","google");
                i.putExtra("id", googleId);
                i.putExtra("firstName", googlefname);
                i.putExtra("lastName", googlelname);
                i.putExtra("email", googleemail);
                startActivity(i);

            }


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
                case R.id.google_button_login_or_register_activity: signInWithGplus();
                    break;
//                case R.id.google : googleSignIn.performClick();
//                    signInWithGplus();
//                    break;

            }

        }
    };
}
