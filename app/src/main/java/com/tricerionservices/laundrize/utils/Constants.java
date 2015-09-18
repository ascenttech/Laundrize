package com.tricerionservices.laundrize.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.tricerionservices.laundrize.data.AddressData;
import com.tricerionservices.laundrize.data.AllSlotsData;
import com.tricerionservices.laundrize.data.BagOrderData;
import com.tricerionservices.laundrize.data.CompletedOrdersData;
import com.tricerionservices.laundrize.data.GeneralAddressRelatedData;
import com.tricerionservices.laundrize.data.GeneralData;
import com.tricerionservices.laundrize.data.IroningOrderData;
import com.tricerionservices.laundrize.data.LaundryServicesMainCategoryData;
import com.tricerionservices.laundrize.data.LaundryServicesSubCategoryData;
import com.tricerionservices.laundrize.data.NavigationDrawerData;
import com.tricerionservices.laundrize.data.OthersOrderData;
import com.tricerionservices.laundrize.data.ProfileData;
import com.tricerionservices.laundrize.data.TrackOrdersData;
import com.tricerionservices.laundrize.data.WashingOrderData;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class Constants {

    public static final String LOG_TAG =" Laundrize ";
    public static final String APP_NAME ="Laundrize";


    public static boolean addressFetched;
    public static boolean profileAddressPopulated = false;
    public static boolean addressPopulated = false;
    public static boolean ordersTracked;
    public static boolean completedOrdersFetched;
    public static boolean currentServerTimeFetched;
    public static boolean slotDifferenceFetched;
    public static boolean slotsFetched;
    public static boolean noCompletedOrders;
    public static boolean noOrdersToTrack;

    public static String userId,token,password,phoneNumber,weeklyOrderDay,todaysDay;
    public static String addressId,address;
    public static String minIroningDate;
    public static String minWashingDate;
    public static String minBagsDate;
    public static String minOthersDate;
    public static String collectionDate,collectionSlotId;
    public static String ironingDeliveryDate,ironingDeliverySlotId;
    public static String washingDeliverySlotId,washingDeliveryDate;
    public static String bagsDeliveryDate,bagsDeliverySlotId;
    public static String othersDeliveryDate,othersDeliverySlotId;
    public static String currentDate,currentTime;
    public static int totalQuantityToBeCollected,totalAmountToBeCollected;
    public static int collectionTimeSlot;
    public static String fetchPasswordErrorMessage,verificationCodeError;
    public static String profileName;

    public static int jCounter = 10;

    public static int ironingDeliveryCounter;
    public static int washingDeliveryCounter;
    public static int bagsDeliveryCounter;
    public static int othersDeliveryCounter;


    // This is used to change the image of track orders.
    // 1 is order placed
    // 2 is picked up
    // 3 processing
    public static int orderProgress = 1;

    // This is used to store the profileId of the user if he enters from FB or google
    // The loginRoute will save the route from which the user entered
    // register, facebook or google
    public static String profileId,loginRoute;



    // This hashmap would containt the key as orderId and value as quantity
    public static Map<String,String> order;
    public static Map<String,String> slots;
    public static Map<String,String> getSlotsId;
    public static Map<String,String> areasMap;
    public static Map<String,String> citiesMap;
    public static Map<String,String> zipcodesMap;

    // This is used only to store the service code and the serviceName
    // This is used in place order screen
    public static Map<String,String> servicesName;

    // All the arraylists
    public static ArrayList<GeneralData> subCategory;
    public static ArrayList<GeneralAddressRelatedData> generalAddressRelatedData;
    public static ArrayList<AddressData> addressData;
    public static ArrayList<GeneralData> bagLaundryData;
    public static ArrayList<CompletedOrdersData> completedOrdersData;
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
    public static ArrayList<ProfileData> profileData;
    public static ArrayList<IroningOrderData> ironingOrderData;
    public static ArrayList<WashingOrderData> washingOrderData;
    public static ArrayList<BagOrderData> bagOrderData;
    public static ArrayList<OthersOrderData> othersOrderData;
    public static ArrayList<AllSlotsData> allSlotsData;
    public static ArrayList<TrackOrdersData> trackOrdersData;
    public static ArrayList<String> areas;
    public static ArrayList<String> cities;
    public static ArrayList<String> zipcodes;
    public static ArrayList<String> weekdays;



    // Log Tags for all the activities
    public static final String ForgotPasswordActivity = " FORGOT PASSWORD ACTIVITY ";
    public static final String LandingActivity = " LANDING ACTIVITY ";
    public static final String LoginActivity = " LOGIN ACTIVITY ";
    public static final String LoginOrRegisterActivity = " LOGIN OR REGISTER ACTIVITY ";
    public static final String MobileVerificationActivity = " MOBILE VERIFICATION ACTIVITY ";
    public static final String RegisterActivity = " REGISTER ACTIVITY ";
    public static final String SplashScreenActivity = " SPLASH SCREEN ACTIVITY ";

    // Log Tags for all the adapters
    public static final String AddressRecyclerAdapter = " ADDRESS RECYCLER ADAPTER ";
    public static final String BagLaundryRecyclerAdapter = " BAG LAUNDRY RECYCLER ADAPTER ";
    public static final String CompleteOrdersRecyclerAdapter = " COMPLETED ORDERS RECYCLER ADAPTER ";
    public static final String DryCleanHouseholdsRecyclerAdapter = " DRY CLEAN HOUSEHOLD RECYCLER ADAPTER ";
    public static final String DryCleanWearablesRecyclerAdapter = " DRY CLEAN WEARABLES RECYCLER ADAPTER ";
    public static final String IroningHouseholdsRecyclerAdapter = " IRONING HOUSEHOLDS RECYCLER ADAPTER ";
    public static final String IroningWearablesRecyclerAdapter = " IRONING WEARABLES RECYCLER ADAPTER ";
    public static final String NavigationDrawerAdapter = " NAVIGATION DRAWER ADAPTER ";
    public static final String OtherRecyclerAdapter = " OTHER RECYCLER ADAPTER ";
    public static final String ShoeLaundryRecyclerAdapter = " SHOE LAUNDRY RECYCLER ADAPTER ";
    public static final String TabsViewPagerAdapter = " TABS VIEW PAGER ADAPTER ";
    public static final String TrackOrderRecyclerAdapter = " TRACK ORDERS RECYCLER ADAPTER ";
    public static final String WashAndIronHouseholdsRecyclerAdapter = " WASH IRON HOUSEHOLDS RECYCLER ADAPTER ";
    public static final String WashAndIronWearableRecyclerAdapter = " WASH AND IRON WEARABLES RECYCLER ADAPTER ";

    // Log Tags for async Task
    public static final String AddNewAddressAsyncTask=" ADD NEW ADDRESS ASYNC TASK ";
    public static final String CheckIfUserExistsAsyncTask=" CHECK IF USER EXISTS ASYNC TASK ";
    public static final String ConfirmVerificationAsyncTask=" CONFIRM VERIFICATION ASYNC TASK ";
    public static final String ConfirmVerificationForPasswordRecoveryAsyncTask=" CONFIRM VERIFICATION FOR PASSWORD RECOVERY ASYNC TASK ";
    public static final String ConfirmVerificationFromSocialAsyncTask=" CONFIRM VERIFICATION FROM SOCIAL ASYNC TASK ";
    public static final String FetchAddressAsyncTask=" FETCH ADDRESS ASYNC TASK ";
    public static final String FetchAllSlotsAsyncTask=" FETCH ALL SLOTS ASYNC TASK ";
    public static final String FetchAreasAsyncTask=" FETCH AREAS ASYNC TASK ";
    public static final String FetchCitiesAsyncTask=" FETCH CITIES ASYNC TASK ";
    public static final String FetchCompletedOrdersAsyncTask=" FETCH COMPLETED ORDER ASYNC TASK ";
    public static final String FetchCurrentServerTimeAsyncTask=" FETCH CURRENT SERVER TIME ASYNC TASK ";
    public static final String FetchLaundryServicesAsyncTask=" FETCH LAUNDRY SERVICES ASYNC TASK ";
    public static final String FetchPasswordForRecoveryAsyncTask=" FETCH PASSWORD FOR RECOVERY ASYNC TASK ";
    public static final String FetchSlotDifferenceAsyncTask=" FETCH SLOT DIFFERENCE ASYNC TASK ";
    public static final String FetchUserAddressAsyncTask=" FETCH USER ADDRESS ASYNC TASK ";
    public static final String FetchUserCityAsyncTask=" FETCH USER CITY ASYNC TASK ";
    public static final String FetchUserProfileAsyncTask=" FETCH USER PROFILE ASYNC TASK ";
    public static final String FetchVerificationCodeAsyncTask=" FETCH VERIFICATION CODE ASYNC TASK ";
    public static final String FetchZipcodesAsyncTask=" FETCH ZIPCODES ASYNC TASK ";
    public static final String PlaceOrderAsyncTask =" PLACE ORDER ASYNC TASK ";
    public static final String RegisterUserAsyncTask=" REGISTER USER ASYNC TASK ";
    public static final String RegisterUserViaSocialAsyncTask=" REGISTER USER VIA SOCIAL ASYNC TASK ";
    public static final String SignInUserAsyncTask=" SIGN IN USER ASYNC TASK ";
    public static final String TrackOrdersAsyncTask=" TRACK ORDERS ASYNC TASK ";
    public static final String UpdateUserProfileAsyncTask=" UPDATE USER PROFILE ASYNC TASK ";
    public static final String WeeklyOrderAsyncTask=" WEEKLY ORDER ASYNC TASK ";

    // Log Tags for all the fragments
    public static final String AddressFragement =" ADDRESS FRAGMENT ";
    public static final String BagLaundryFragement =" BAG LAUNDRY FRAGMENT ";
    public static final String CompletedOrdersFragement =" COMPLETED ORDERS FRAGMENT ";
    public static final String DryCleanHouseholdsFragement =" DRY CLEAN HOUSEHOLDS FRAGMENT ";
    public static final String DryCleanWearablesFragement =" DRY CLEAN WEARABLES FRAGMENT ";
    public static final String FAQFragement =" FAQ FRAGMENT ";
    public static final String IroningHouseholdsFragement =" IRONING HOUSEHOLDS FRAGMENT ";
    public static final String IroningWearablesFragement =" IRONING WEARABLES FRAGMENT ";
    public static final String LandingFragment = " LANDING FRAGMENT ";
    public static final String OtherFragement = " OTHER FRAGMENT ";
    public static final String PlaceOrderFragment =" PLACE ORDER FRAGMENT ";
    public static final String PrivacyPolicyFragement =" PRIVACY POLICY FRAGMENT ";
    public static final String ProfileFragment =" PROFILE FRAGMENT ";
    public static final String QuickOrderFragement =" QUICK ORDER FRAGMENT ";
    public static final String ServicesFragement =" SERVICES FRAGMENT ";
    public static final String ShoeLaundryFragement =" SHOE LAUNDRY FRAGMENT ";
    public static final String TrackOrdersFragement =" TRACK ORDERS FRAGMENT ";
    public static final String WashAndIronHouseholdsFragement =" WASH AND IRON HOUSEHOLDS FRAGMENT ";
    public static final String WashAndIronWearablesFragement =" WASH AND IRON WEARABLES FRAGMENT ";
    public static final String WeeklyOrderFragement =" WEEKLY Order FRAGMENT ";


    // LINKS with a harcoded link for testing
    // verify Now full Url

//    public static final String fetchAddressUrl = "http://dev.laundrize.com/api/api/v1/getuseraddress?user_id=2";
//    public static final String fetchAddressUrl = "http://dev.laundrize.com/api/api/v1/getuseraddress?user_id=";
    public static final String fetchAddressUrl = "http://www.laundrize.com/api/api/v1/getuseraddress?user_id=";

//    public static final String fetchProfileUrl = "http://dev.laundrize.com/api/api/v1/getuserinfo?user_id=2";
//    public static final String fetchProfileUrl = "http://dev.laundrize.com/api/api/v1/getuserinfo?user_id=";
    public static final String fetchProfileUrl = "http://www.laundrize.com/api/api/v1/getuserinfo?user_id=";

//    public static final String fetchLaundrySevicesUrl ="http://dev.laundrize.com/api/api/v1/getlaundryservice?user_id=50&address_id=48"
//    public static final String fetchLaundrySevicesUrl ="http://dev.laundrize.com/api/api/v1/getlaundryservice?user_id=";
    public static final String fetchLaundrySevicesUrl ="http://www.laundrize.com/api/api/v1/getlaundryservice?user_id=";

    //  public static final String verifyNowUrl = "http://dev.laundrize.com/api/api/v1/sendverificationcode?mobile_number=8879153143";
//    public static final String verifyNowUrl = "http://dev.laundrize.com/api/api/v1/sendverificationcode?mobile_number=";
    public static final String verifyNowUrl = "http://www.laundrize.com/api/api/v1/sendverificationcode?mobile_number=";

//    public static final String forgotPasswordUrl = "http://dev.laundrize.com/api/api/v1/sendForPasswordRecovery?mobile_number=";
//    public static final String forgotPasswordUrl = "http://dev.laundrize.com/api/api/v1/sendForPasswordRecovery?mobile_number=";
    public static final String forgotPasswordUrl = "http://www.laundrize.com/api/api/v1/sendForPasswordRecovery?mobile_number=";

//    public static final String postOrderUrl = "http://dev.laundrize.com/api/api/v1/placeorder";
//    public static final String postOrderUrl = "http://dev.laundrize.com/api/api/v1/placeorder?";
        public static final String postOrderUrl = "http://www.laundrize.com/api/api/v1/placeorder";

//    public static final String changePasswordUrl = "http://dev.laundrize.com/api/api/v1/changepassword?mobile_number=9752493189&verification_code=039293&new_password=Abc";
//    public static final String changePasswordUrl = "http://dev.laundrize.com/api/api/v1/changepassword?mobile_number=";
    public static final String changePasswordUrl = "http://www.laundrize.com/api/api/v1/changepassword?mobile_number=";

    //  public static final String confirmVerificationUrl = "http://dev.laundrize.com/api/api/v1/registeruser?mobile_number=8879153143&verification_code=wFIqdi&password=qwerty&first_name=sagar&email=sagardevanga@gmail.com";
//    public static final String confirmVerificationUrl = "http://dev.laundrize.com/api/api/v1/registeruser?mobile_number=";
    public static final String confirmVerificationUrl = "http://www.laundrize.com/api/api/v1/registeruser?mobile_number=";

    // public static final String signInUrl = "http://dev.laundrize.com/api/api/v1/postlogin?mobile_number=9752493189&password=laundry";
//    public static final String signInUrl = "http://dev.laundrize.com/api/api/v1/postlogin?mobile_number=";
    public static final String signInUrl = "http://www.laundrize.com/api/api/v1/postlogin?mobile_number=";

//    public static final String trackOrdersUrl="http://dev.laundrize.com/api/api/v1/gettrackorders?user_id=12";
//    public static final String trackOrdersUrl="http://dev.laundrize.com/api/api/v1/gettrackorders?user_id=";
    public static final String trackOrdersUrl="http://www.laundrize.com/api/api/v1/gettrackorders?user_id=";

//  public static final String addNewAddressUrl ="http://dev.laundrize.com/api/api/v1/postAddress?user_id=12&city=Pune&city_zip=411027&city_zip_area=PimpleSaudagar&address=501RajGalaxy"
//    public static final String addNewAddressUrl ="http://dev.laundrize.com/api/api/v1/postAddress?user_id=";
    public static final String addNewAddressUrl ="http://www.laundrize.com/api/api/v1/postAddress?user_id=";

//    public static final String getTimeStampUrl ="http://dev.laundrize.com/api/api/v1/currentdatetime?";
//    public static final String getTimeStampUrl ="http://dev.laundrize.com/api/api/v1/currentdatetime?";
    public static final String getTimeStampUrl ="http://www.laundrize.com/api/api/v1/currentdatetime?";


//    public static final String getslotsUrl ="http://dev.laundrize.com/api/api/v1/allslots?user_id=2&address_id=48;
//    public static final String getslotsUrl = "http://dev.laundrize.com/api/api/v1/allslots?user_id=";
    public static final String getslotsUrl = "http://www.laundrize.com/api/api/v1/allslots?user_id=";

//    public static final String getSlotDifferenceUrl ="http://dev.laundrize.com/api/api/v1/slotdiff?user_id=2&address_id=48";
//    public static final String getSlotDifferenceUrl ="http://dev.laundrize.com/api/api/v1/slotdiff?user_id=";
    public static final String getSlotDifferenceUrl ="http://www.laundrize.com/api/api/v1/slotdiff?user_id=";

//    public static final String getCityUrl ="http://dev.laundrize.com/api/api/v1/getCity
//    public static final String getCityUrl ="http://dev.laundrize.com/api/api/v1/getCity";
    public static final String getCityUrl ="http://www.laundrize.com/api/api/v1/getCity";

//    public static final String getZipCodeUrl ="http://dev.laundrize.com/api/api/v1/getCityZipCode?city_id=1";
//    public static final String getZipCodeUrl ="http://dev.laundrize.com/api/api/v1/getCityZipCode?city_id=";
    public static final String getZipCodeUrl ="http://www.laundrize.com/api/api/v1/getCityZipCode?city_id=";

//    public static final String getZipAreaUrl ="http://dev.laundrize.com/api/api/v1/getZipArea?zip_code_id=1";
//    public static final String getZipAreaUrl ="http://dev.laundrize.com/api/api/v1/getZipArea?zip_code_id=";
    public static final String getZipAreaUrl ="http://www.laundrize.com/api/api/v1/getZipArea?zip_code_id=";


//    public static final String registerViaFBUrl ="http://dev.laundrize.com/api/api/v1/registerSocialUser?facebookId=891231289&mobile_number=98765454323&password=null&verification_code=909juew&first_name=sagar&last_name=devanga&email=abc@gmail.com";
//    public static final String registerViaFBUrl ="http://dev.laundrize.com/api/api/v1/registerSocialUser?facebookId=";
    public static final String registerViaFBUrl ="http://www.laundrize.com/api/api/v1/registerSocialUser?facebookId=";

//    public static final String registerViaGoogleUrl ="http://dev.laundrize.com/api/api/v1/registerSocialUser?googleId=758924621&mobile_number=98765454323&password=null&verification_code=909juew&first_name=sagar&last_name=devanga&email=abc@gmail.com";
//    public static final String registerViaGoogleUrl ="http://dev.laundrize.com/api/api/v1/registerSocialUser?googleId=";
    public static final String registerViaGoogleUrl ="http://www.laundrize.com/api/api/v1/registerSocialUser?googleId=";


    // type = GP for google
//    public static final String checkUserExistsUrl ="http://dev.laundrize.com/api/api/v1/checkuserExist?id=874750579238699&type=FB";
//    public static final String checkUserExistsUrl ="http://dev.laundrize.com/api/api/v1/checkuserExist?id=";
    public static final String checkUserExistsUrl ="http://www.laundrize.com/api/api/v1/checkuserExist?id=";

//    public static final String updateUserProfile = "http://dev.laundrize.com/api/api/v1/updateUserProfile?email=sagardevanga@gmail.com&first_name=sagar&last_name=devanga";
//    public static final String updateUserProfile = "http://dev.laundrize.com/api/api/v1/updateUserProfile?user_id=";
    public static final String updateUserProfile = "http://www.laundrize.com/api/api/v1/updateUserProfile?user_id=";


//    public static final String resetPasswordUrl = "http://dev.laundrize.com/api/api/v1/changePassword?mobile_number=7666441398&new_password=poiuy&verification_code=5329";
//    public static final String resetPasswordUrl = "http://dev.laundrize.com/api/api/v1/changePassword?mobile_number=";
    public static final String resetPasswordUrl = "http://www.laundrize.com/api/api/v1/changePassword?mobile_number=";

//    public static final String completedOrdersUrl = "http://dev.laundrize.com/api/api/v1/gettrackorders?user_id=3&type=completed";
//    public static final String completedOrdersUrl = "http://dev.laundrize.com/api/api/v1/gettrackorders?user_id=";
    public static final String completedOrdersUrl = "http://www.laundrize.com/api/api/v1/gettrackorders?user_id=";

//    public static final String weeklyOrderUrl = "http://dev.laundrize.com/api/api/v1/postWeeklyOrder?user_id=2&address_id=2&order_day=monday&order_slot=2";
//    public static final String weeklyOrderUrl = "http://dev.laundrize.com/api/api/v1/postWeeklyOrder?user_id=";
    public static final String weeklyOrderUrl = "http://www.laundrize.com/api/api/v1/postWeeklyOrder?user_id=";

    public static void showInternetErrorDialog(Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("This app requires internet connection");
            builder.setCancelable(false);
            builder.create();
            builder.show();


    }

    public static String reintializeTheValues(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        Constants.userId = sharedPreferences.getString("userId","null");
        Constants.token = sharedPreferences.getString("token","null");
//        sharedPreferences.getString("loginRoute");
//        sharedPreferences.getString("profileId");
//        sharedPreferences.getString("phoneNumber");
//        sharedPreferences.getString("password");

    }

    public static boolean isInternetAvailable(Context context){

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;

    }

}
