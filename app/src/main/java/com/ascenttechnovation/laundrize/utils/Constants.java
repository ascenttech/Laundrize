package com.ascenttechnovation.laundrize.utils;

import com.ascenttechnovation.laundrize.data.LandingFragmentData;

import java.util.ArrayList;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class Constants {

    public static final String LOG_TAG =" Laundrize ";
    public static final String APP_NAME ="Laundrize";


    public static String userId,token,verificationCode;

    public static ArrayList<LandingFragmentData> landingFragmentData;

    // Log Tags for all the activities
    public static final String AddressActivity = " ADDRESS ACTIVITY ";
    public static final String ForgotPasswordActivity = " FORGOT PASSWORD ACTIVITY ";
    public static final String LandingActivity = " LANDING ACTIVITY ";
    public static final String LoginActivity = " LOGIN ACTIVITY ";
    public static final String LoginOrRegisterActivity = " LOGIN OR REGISTER ACTIVITY ";
    public static final String MobileVerificationActivity = " MOBILE VERIFICATION ACTIVITY ";
    public static final String ProfileActivity = " PROFILE ACTIVITY ";
    public static final String RegisterActivity = " REGISTER ACTIVITY ";
    public static final String SplashScreenActivity = " SPLASH SCREEN ACTIVITY ";

    // Log Tags for all the fragments
    public static final String LandingFragment = " LANDING FRAGMENT ";
    public static final String DataHolderFragment =" DATA HOLDER FRAGMENT ";

    // Log Tags for all the adapters
    public static final String LandingFragmentRecyclerAdapter = " LANDING FRAGMENT RECYCLER ADAPTER ";
    public static final String NavigationDrawerAdapter = " NAVIGATION ADAPTER ";
    public static final String YourbasketRecyclerAdapter = " YOUR BASKET RECYCLER ADAPTER ";

    // Log Tags for async Task
    public static final String ConfirmVerificationAsyncTask=" CONFIRM VERIFICATION ASYNC TASK ";
    public static final String FetchAddressAsyncTask=" FETCH ADDRESS ASYNC TASK ";
    public static final String FetchVerificationCodeAsyncTask=" FETCH VERIFICATION CODE ASYNC TASK ";
    public static final String PlaceWeeklyOrderAsyncTask=" PLACE WEEKLY ORDER ASYNC TASK ";
    public static final String RegisterUserAsyncTask=" REGISTER USER ASYNC TASK ";
    public static final String SignInUserAsyncTask=" SIGN IN USER ASYNC TASK ";
    public static final String UpdateUserProfileAsyncTask=" UPDATE USER PROFILE ASYNC TASK ";

    // LINKS with a harcoded link for testing
    // verify Now full Url

    //  public static final String verifyNowUrl = "http://dev.api.laundrize.com/api/api/v1/sendverificationcode?mobile_number=9876543210";
    public static final String verifyNowUrl = "http://dev.api.laundrize.com/api/api/v1/sendverificationcode?mobile_number=";


    // confirm verification url
    //  public static final String confirmVerificationUrl = "http://dev.api.laundrize.com/api/api/v1/registeruser?mobile_number=9820817089&verification_code=Fzj8GE&password=122345&first_name=sagar&email=abc@gmail.com";
    public static final String confirmVerificationUrl = "http://dev.api.laundrize.com/api/api/v1/registeruser?mobile_number=";

    // sign in url full
    // public static final String signInUrl = "http://dev.api.laundrize.com/api/api/v1/postlogin?mobile_number=9752493189&password=laundrize";
    public static final String signInUrl = "http://dev.api.laundrize.com/api/api/v1/postlogin?mobile_number=";



}
