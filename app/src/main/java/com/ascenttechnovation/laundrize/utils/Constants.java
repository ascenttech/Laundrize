package com.ascenttechnovation.laundrize.utils;

import com.ascenttechnovation.laundrize.data.AddressData;
import com.ascenttechnovation.laundrize.data.GeneralData;
import com.ascenttechnovation.laundrize.data.LaundryServicesMainCategoryData;
import com.ascenttechnovation.laundrize.data.LaundryServicesSubCategoryData;
import com.ascenttechnovation.laundrize.data.NavigationDrawerData;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class Constants {

    public static final String LOG_TAG =" Laundrize ";
    public static final String APP_NAME ="Laundrize";


    public static String userId,token,verificationCode;
    public static boolean addressFetched;

    public static String address;
    public static String mobileNumber;

    // This hashmap would containt the key as orderId and value as quantity
    public static Map<String,String> order;

    // All the arraylists
    public static ArrayList<GeneralData> subCategory;
    public static ArrayList<AddressData> addressData;
    public static ArrayList<GeneralData> bagLaundryData;
    public static ArrayList<GeneralData> dryCleanHouseholdsData;
    public static ArrayList<GeneralData> dryCleanWearablesData;
    public static ArrayList<GeneralData> ironingHouseholdsData;
    public static ArrayList<GeneralData> ironingWearablesData;
    public static ArrayList<LaundryServicesMainCategoryData> laundryServicesMainCategory;
    public static ArrayList<LaundryServicesSubCategoryData> laundryServicesSubCategory;
    public static ArrayList<NavigationDrawerData> navigationDrawerData;
    public static ArrayList<GeneralData> othersData;
    public static ArrayList<GeneralData> shoeLaundryData;
    public static ArrayList<GeneralData> washAndIronHouseholdsData;
    public static ArrayList<GeneralData> washAndIronWearablesData;


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
    public static final String AddNewAddressAsyncTask=" ADD NEW ADDRESS ASYNC TASK ";
    public static final String ConfirmVerificationAsyncTask=" CONFIRM VERIFICATION ASYNC TASK ";
    public static final String FetchAddressAsyncTask=" FETCH ADDRESS ASYNC TASK ";
    public static final String FetchLaundryServicesAsyncTask=" FETCH LAUNDRY SERVICES ASYNC TASK ";
    public static final String FetchVerificationCodeAsyncTask=" FETCH VERIFICATION CODE ASYNC TASK ";
    public static final String PlaceWeeklyOrderAsyncTask=" PLACE WEEKLY ORDER ASYNC TASK ";
    public static final String RegisterUserAsyncTask=" REGISTER USER ASYNC TASK ";
    public static final String SignInUserAsyncTask=" SIGN IN USER ASYNC TASK ";
    public static final String TrackOrdersAsyncTask=" TRACK ORDERS ASYNC TASK ";
    public static final String UpdateUserProfileAsyncTask=" UPDATE USER PROFILE ASYNC TASK ";

    // LINKS with a harcoded link for testing
    // verify Now full Url

//    public static final String fetchAddressUrl = "http://dev.laundrize.com/api/api/v1/getuseraddress?user_id=";
    public static final String fetchAddressUrl = "http://dev.laundrize.com/api/api/v1/getuseraddress?user_id=";

//    public static final String fetchLaundrySevicesUrl ="http://dev.laundrize.com/api/api/v1/getlaundryservice?user_id=50"
    public static final String fetchLaundrySevicesUrl ="http://dev.laundrize.com/api/api/v1/getlaundryservice?user_id=";

    //  public static final String verifyNowUrl = "http://dev.laundrize.com/api/api/v1/sendverificationcode?mobile_number=8879153143";
    public static final String verifyNowUrl = "http://dev.laundrize.com/api/api/v1/sendverificationcode?mobile_number=";


//    public static final String forgotPasswordUrl = "http://dev.laundrize.com/api/api/v1/sendForPasswordRecovery?mobile_number=8097746236";
    public static final String forgotPasswordUrl = "http://dev.laundrize.com/api/api/v1/sendForPasswordRecovery?mobile_number=";


    //  public static final String confirmVerificationUrl = "http://dev.laundrize.com/api/api/v1/registeruser?mobile_number=8879153143&verification_code=wFIqdi&password=qwerty&first_name=sagar&email=sagardevanga@gmail.com";
    public static final String confirmVerificationUrl = "http://dev.laundrize.com/api/api/v1/registeruser?mobile_number=";

    // public static final String signInUrl = "http://dev.laundrize.com/api/api/v1/postlogin?mobile_number=9752493189&password=laundry";
    public static final String signInUrl = "http://dev.laundrize.com/api/api/v1/postlogin?mobile_number=";

//    public static final String trackOrdersUrl="http://dev.laundrize.com/api/api/v1/gettrackorders?user_id=12";
    public static final String trackOrdersUrl="http://dev.laundrize.com/api/api/v1/gettrackorders?user_id=";

//    public static final String addNewAddress ="http://dev.laundrize.com/api/api/v1/postAddress?user_id=12&city=Pune&city_zip=411027&city_zip_area=PimpleSaudagar&address=501RajGalaxy"
    public static final String addNewAddress ="http://dev.laundrize.com/api/api/v1/postAddress?user_id=";


}
