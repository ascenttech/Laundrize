package com.ascenttechnovation.laundrize.utils;

import com.ascenttechnovation.laundrize.data.AddressData;
import com.ascenttechnovation.laundrize.data.AllSlotsData;
import com.ascenttechnovation.laundrize.data.BagOrderData;
import com.ascenttechnovation.laundrize.data.CompletedOrdersData;
import com.ascenttechnovation.laundrize.data.GeneralData;
import com.ascenttechnovation.laundrize.data.IroningOrderData;
import com.ascenttechnovation.laundrize.data.LaundryServicesMainCategoryData;
import com.ascenttechnovation.laundrize.data.LaundryServicesSubCategoryData;
import com.ascenttechnovation.laundrize.data.NavigationDrawerData;
import com.ascenttechnovation.laundrize.data.ProfileData;
import com.ascenttechnovation.laundrize.data.TrackOrdersData;
import com.ascenttechnovation.laundrize.data.WashingOrderData;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class Constants {

    public static final String LOG_TAG =" Laundrize ";
    public static final String APP_NAME ="Laundrize";


    public static boolean addressFetched;
    public static boolean ordersTracked;
    public static boolean completedOrdersFetched;
    public static boolean currentServerTimeFetched;
    public static boolean slotDifferenceFetched;
    public static boolean slotsFetched;

    public static String mobileNumber,userId,token,verificationCode,totalAmount,totalQuantity;
    public static String addressId,address;
    public static String collectionDate,collectionSlotId;
    public static String ironingDeliveryDate,ironingDeliverySlotId;
    public static String washingDeliverySlotId,washingDeliveryDate;
    public static String bagsDeliveryDate,bagsDeliverySlotId;
    public static String currentDate,currentTime;
    public static int totalQuantityToBeCollected,totalAmountToBeCollected;
    public static int collectionTimeSlot;

    // This is used to change the image of track orders.
    // 1 is order placed
    // 2 is picked up
    // 3 processing
    public static int orderProgress = 1;


    // This hashmap would containt the key as orderId and value as quantity
    public static Map<String,String> order;
    public static Map<String,String> slots;
    public static Map<String,String> getSlotsId;


    // All the arraylists
    public static ArrayList<GeneralData> subCategory;
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
    public static ArrayList<BagOrderData> bagOrderData;
    public static ArrayList<WashingOrderData> washingOrderData;
    public static ArrayList<AllSlotsData> allSlotsData;
    public static ArrayList<TrackOrdersData> trackOrdersData;


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
    public static final String ConfirmVerificationAsyncTask=" CONFIRM VERIFICATION ASYNC TASK ";
    public static final String FetchAddressAsyncTask=" FETCH ADDRESS ASYNC TASK ";
    public static final String FetchAllSlotsAsyncTask=" FETCH ALL SLOTS ASYNC TASK ";
    public static final String FetchCurrentServerTimeAsyncTask=" FETCH CURRENT SERVER TIME ASYNC TASK ";
    public static final String FetchLaundryServicesAsyncTask=" FETCH LAUNDRY SERVICES ASYNC TASK ";
    public static final String FetchSlotDifferenceAsyncTask=" FETCH SLOT DIFFERENCE ASYNC TASK ";
    public static final String FetchUserAddressAsyncTask=" FETCH USER ADDRESS ASYNC TASK ";
    public static final String FetchUserCityAsyncTask=" FETCH USER CITY ASYNC TASK ";
    public static final String FetchUserProfileAsyncTask=" FETCH USER PROFILE ASYNC TASK ";
    public static final String FetchVerificationCodeAsyncTask=" FETCH VERIFICATION CODE ASYNC TASK ";
    public static final String PlaceWeeklyOrderAsyncTask=" PLACE WEEKLY ORDER ASYNC TASK ";
    public static final String RegisterUserAsyncTask=" REGISTER USER ASYNC TASK ";
    public static final String SignInUserAsyncTask=" SIGN IN USER ASYNC TASK ";
    public static final String TrackOrdersAsyncTask=" TRACK ORDERS ASYNC TASK ";
    public static final String UpdateUserProfileAsyncTask=" UPDATE USER PROFILE ASYNC TASK ";

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
    public static final String WeeklyFragement =" WEEKLY FRAGMENT ";





    // LINKS with a harcoded link for testing
    // verify Now full Url

//    public static final String fetchAddressUrl = "http://dev.laundrize.com/api/api/v1/getuseraddress?user_id=2";
    public static final String fetchAddressUrl = "http://dev.laundrize.com/api/api/v1/getuseraddress?user_id=";

//    public static final String fetchProfileUrl = "http://dev.laundrize.com/api/api/v1/getuserinfo?user_id=2";
    public static final String fetchProfileUrl = "http://dev.laundrize.com/api/api/v1/getuserinfo?user_id=";

//    public static final String fetchLaundrySevicesUrl ="http://dev.laundrize.com/api/api/v1/getlaundryservice?user_id=50"
    public static final String fetchLaundrySevicesUrl ="http://dev.laundrize.com/api/api/v1/getlaundryservice?user_id=";

    //  public static final String verifyNowUrl = "http://dev.laundrize.com/api/api/v1/sendverificationcode?mobile_number=8879153143";
    public static final String verifyNowUrl = "http://dev.laundrize.com/api/api/v1/sendverificationcode?mobile_number=";


//    public static final String postOrderUrl = "http://dev.laundrize.com/api/api/v1/postorders?user_id=";
//    public static final String postOrderUrl = "http://dev.laundrize.com/api/api/v1/postorders?user_id=";
    public static final String postOrderUrl = "http://dev.laundrize.com/api/api/v1/placeorder";

//    public static final String forgotPasswordUrl = "http://dev.laundrize.com/api/api/v1/sendForPasswordRecovery?mobile_number=8097746236";
    public static final String forgotPasswordUrl = "http://dev.laundrize.com/api/api/v1/sendForPasswordRecovery?mobile_number=";

//    public static final String changePasswordUrl = "http://dev.laundrize.com/api/api/v1/changepassword?mobile_number=9752493189&verification_code=039293&new_password=Abc";
    public static final String changePasswordUrl = "http://dev.laundrize.com/api/api/v1/changepassword?mobile_number=";

    //  public static final String confirmVerificationUrl = "http://dev.laundrize.com/api/api/v1/registeruser?mobile_number=8879153143&verification_code=wFIqdi&password=qwerty&first_name=sagar&email=sagardevanga@gmail.com";
    public static final String confirmVerificationUrl = "http://dev.laundrize.com/api/api/v1/registeruser?mobile_number=";

    // public static final String signInUrl = "http://dev.laundrize.com/api/api/v1/postlogin?mobile_number=9752493189&password=laundry";
    public static final String signInUrl = "http://dev.laundrize.com/api/api/v1/postlogin?mobile_number=";
//    public static final String signInUrl = "http://dev.laundrize.com/api/api/v1/postlogin";

//    public static final String trackOrdersUrl="http://dev.laundrize.com/api/api/v1/gettrackorders?user_id=12";
    public static final String trackOrdersUrl="http://dev.laundrize.com/api/api/v1/gettrackorders?user_id=";

//    public static final String addNewAddressUrl ="http://dev.laundrize.com/api/api/v1/postAddress?user_id=12&city=Pune&city_zip=411027&city_zip_area=PimpleSaudagar&address=501RajGalaxy"
    public static final String addNewAddressUrl ="http://dev.laundrize.com/api/api/v1/postAddress?user_id=";

//    public static final String getTimeStampUrl ="http://dev.laundrize.com/api/api/v1/currentdatetime?";
    public static final String getTimeStampUrl ="http://dev.laundrize.com/api/api/v1/currentdatetime?";


//    public static final String getslotsUrl ="http://dev.laundrize.com/api/api/v1/allslots?user_id=2;
    public static final String getslotsUrl = "http://dev.laundrize.com/api/api/v1/allslots?user_id=";

//    public static final String getSlotDifferenceUrl ="http://dev.laundrize.com/api/api/v1/slotdiff?user_id=2";
    public static final String getSlotDifferenceUrl ="http://dev.laundrize.com/api/api/v1/slotdiff?user_id=";



}
