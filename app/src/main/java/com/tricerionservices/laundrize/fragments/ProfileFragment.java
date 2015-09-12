package com.tricerionservices.laundrize.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.activities.LandingActivity;
import com.tricerionservices.laundrize.async.AddNewAddressAsyncTask;
import com.tricerionservices.laundrize.async.FetchAddressAsyncTask;
import com.tricerionservices.laundrize.async.FetchAreasAsyncTask;
import com.tricerionservices.laundrize.async.FetchCitiesAsyncTask;
import com.tricerionservices.laundrize.async.FetchUserProfileAsyncTask;
import com.tricerionservices.laundrize.async.FetchZipcodesAsyncTask;
import com.tricerionservices.laundrize.async.UpdateUserProfileAsyncTask;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.custom.CustomEditText;
import com.tricerionservices.laundrize.custom.CustomTextView;
import com.tricerionservices.laundrize.utils.Constants;

import java.net.URLEncoder;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class ProfileFragment extends Fragment {

    private ActionBar actionBar;
    private ImageView profileImage;
    private Bitmap bitmap;
    private CustomTextView editYourProfile,address,mobileNumber,userName;
    private Dialog profileDialog,addressDialog;
    private CustomButton update,cancel,addNewAddress;
    private LinearLayout footer;
    private ViewGroup availableAddresses;
    private ProgressDialog progressDialog;
    private View v;
    private boolean inflateAddress;
    private LayoutInflater parentInflater;
    private View rowView;

    Spinner city,zipcode,area;
    EditText building,houseNumber;

    String cityValue,zipcodeValue,areaValue,buildingNameValue,houseNumberValue,fullAddressValue;

    CustomEditText firstName,lastName,emailId;
    CustomButton dialogButton;

    CustomTextView cityText,zipText,areaText;
    Bitmap output;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG,Constants.ProfileFragment);

        v = inflater.inflate(R.layout.fragment_profile,container,false);
        parentInflater = inflater;
        // create bitmap from resource
        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.profile_picture);



        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(Constants.isInternetAvailable(getActivity().getApplicationContext())){

            fetchData();
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Internet is required for this app.",5000).show();
        }
    }

    public void fetchData(){

        String finalUrl = Constants.fetchProfileUrl + Constants.userId;
        new FetchUserProfileAsyncTask(getActivity().getApplicationContext(),new FetchUserProfileAsyncTask.FetchUserProfileCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.LOG_TAG);
                progressDialog.setMessage("Getting Your Profile");
                progressDialog.show();

            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){
                    customActionBar();
                    findViews(v);
                    setViews();

                    String finalURL = Constants.fetchAddressUrl + Constants.userId;
                    new FetchAddressAsyncTask(getActivity().getApplicationContext(),new FetchAddressAsyncTask.FetchAddressCallback() {
                        @Override
                        public void onStart(boolean status) {

                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle(Constants.LOG_TAG);
                            progressDialog.setMessage("Loading,Please Wait...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                        }
                        @Override
                        public void onResult(boolean result) {

                            progressDialog.dismiss();
                            Constants.profileAddressPopulated = true;
                            displayAddress();

                        }
                    }).execute(finalURL);
                }
                else{

                    Toast.makeText(getActivity().getApplicationContext(),"Couldn't Fetch Your Profile.\nTry Again Later",5000).show();

                }

            }
        }).execute(finalUrl);


    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Profile");

    }

    private void findViews(View v){

        userName = (CustomTextView) v.findViewById(R.id.user_name_text_profile_fragment);
        profileImage = (ImageView) v.findViewById(R.id.profile_image_profile_fragment);
        editYourProfile = (CustomTextView) v.findViewById(R.id.edit_profile_text_profile_fragment);
        footer = (LinearLayout) v.findViewById(R.id.included_buttons_profile_fragment);
        update = (CustomButton) footer.findViewById(R.id.left_button_included);
        cancel = (CustomButton) footer.findViewById(R.id.right_button_included);
        addNewAddress = (CustomButton) v.findViewById(R.id.add_new_address_button_profile_fragment);
        availableAddresses = (ViewGroup) v.findViewById(R.id.available_address_container_profile_fragment);


    }

    private void setViews(){

        userName.setText(Constants.profileData.get(0).getFirstName());

        profileImage.setImageBitmap(getCircleBitmap(bitmap));
        editYourProfile.setOnClickListener(listener);

        update.setText("Update");
        update.setOnClickListener(listener);

        cancel.setText("Cancel");
        cancel.setOnClickListener(listener);

        addNewAddress.setOnClickListener(listener);
    }

    public void displayAddress(){


        if(Constants.addressFetched) {
            for(int i = 0;i<Constants.addressData.size();i++) {
                rowView = parentInflater.inflate(R.layout.include_available_address, null);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(5,0,5,5);
                rowView.setLayoutParams(params);

                address = (CustomTextView) rowView.findViewById(R.id.address_text_included);
                address.setVisibility(View.VISIBLE);
                address.setText(Constants.addressData.get(i).getFullAddress());

                mobileNumber = (CustomTextView) rowView.findViewById(R.id.mobile_number_text_included);
                mobileNumber.setVisibility(View.VISIBLE);
                mobileNumber.setText(Constants.addressData.get(i).getMobileNumber());

                availableAddresses.addView(rowView);
            }
        } // end of IF statement

    }

    @Override
    public void onPause() {
        super.onPause();
        if(Constants.profileAddressPopulated){

            availableAddresses.removeAllViews();
            Constants.profileAddressPopulated = false;
        }
    }

    public Bitmap getCircleBitmap(Bitmap bitmap){

//        if (bitmap != null && ! bitmap.isRecycled()) {
//            return output;
//        }
//        else{

        output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;


    }

    public void cancel(){

        ((LandingActivity)getActivity())
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new LandingFragment())
                .commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        bitmap.recycle();
        bitmap = null;
        System.gc();

    }

    // This will generate a pop up
    // Since the pop up is totally new screen
    // we wont find its id in the findViews
    public void editYourProfile(){

        // custom dialog
        profileDialog = new Dialog(getActivity());
        profileDialog.setContentView(R.layout.custom_dialog);
        profileDialog.setTitle("Update Profile");

        firstName = (CustomEditText) profileDialog.findViewById(R.id.first_name_edit_custom_dialog);
        lastName = (CustomEditText) profileDialog.findViewById(R.id.last_name_edit_custom_dialog);
        emailId = (CustomEditText) profileDialog.findViewById(R.id.email_id_edit_custom_dialog);
        dialogButton = (CustomButton) profileDialog.findViewById(R.id.update_button_custom_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProfile();
            }
        });
        profileDialog.show();

    }

    public void validateProfile(){

        if(!firstName.getText().toString().equalsIgnoreCase("")){

            if(!lastName.getText().toString().equalsIgnoreCase("")){

                if(!emailId.getText().toString().equalsIgnoreCase("")){

                    updateProfile();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Plese enter the Email Address",5000).show();
                }
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(),"Please enter the Last Name",5000).show();
            }

        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Please enter the First Name",5000).show();
        }


    }

    public void updateProfile(){

        profileDialog.dismiss();

        String emailValue = emailId.getText().toString();
        String firstNameValue = firstName.getText().toString();
        String lastNameValue = lastName.getText().toString();

        String finalUrl = Constants.updateUserProfile+Constants.userId+"&email="+emailValue+"&first_name="+firstNameValue+"&last_name="+lastNameValue;
        new UpdateUserProfileAsyncTask(getActivity().getApplicationContext(),new UpdateUserProfileAsyncTask.UpdateUserProfileCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Updating Your Address, Please wait...");
                progressDialog.show();
            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){
                    replaceFragment(new ProfileFragment());
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Profile couldn't be updated\nTry Again Later",5000).show();
                }
            }
        }).execute(finalUrl);
    }

    public void update(){

        // custom dialog
        addressDialog = new Dialog(getActivity());
        addressDialog.setContentView(R.layout.custom_dialog_for_address);
        addressDialog.setTitle("Add New Address");

        city = (Spinner) addressDialog.findViewById(R.id.city_profile_fragment);
        zipcode = (Spinner) addressDialog.findViewById(R.id.zipcode_profile_fragment);
        area = (Spinner) addressDialog.findViewById(R.id.area_profile_fragment);
        building = (EditText) addressDialog.findViewById(R.id.building_or_street_profile_fragment);
        houseNumber = (EditText) addressDialog.findViewById(R.id.flat_or_house_number_profile_fragment);
        update = (CustomButton) addressDialog.findViewById(R.id.update_this_address_profile_fragment);

        cityText = (CustomTextView) addressDialog.findViewById(R.id.city_add_new_address_static);
        zipText = (CustomTextView) addressDialog.findViewById(R.id.zipcode_add_new_address_static);
        areaText = (CustomTextView) addressDialog.findViewById(R.id.area_add_new_address_static);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateNewAddress();
            }
        });
        populateCities();

        addressDialog.show();

    }

    public void validateNewAddress(){

        if(!building.getText().toString().equalsIgnoreCase("")){

            if(!houseNumber.getText().toString().equalsIgnoreCase("")){

                saveNewAddress();
            } // end of house Number
            else{
                Toast.makeText(getActivity().getApplicationContext(),"Please enter the houseNumber",5000).show();
            }
        } // end of buildiing name
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Please enter the building name",5000).show();
        }



    }

    public void saveNewAddress(){

        addressDialog.dismiss();
        try {

            String c = Constants.citiesMap.get(city.getSelectedItem().toString());
            cityValue = URLEncoder.encode(c,"UTF-8");

            String z = Constants.zipcodesMap.get(zipcode.getSelectedItem().toString());
            zipcodeValue = URLEncoder.encode(z, "UTF-8");

            String a = Constants.areasMap.get(area.getSelectedItem().toString());
            areaValue = URLEncoder.encode(a, "UTF-8");
            buildingNameValue = URLEncoder.encode(building.getText().toString(), "UTF-8");
            houseNumberValue = URLEncoder.encode(houseNumber.getText().toString(), "UTF-8");

            String temp = houseNumberValue+","+buildingNameValue;
            fullAddressValue = URLEncoder.encode(temp,"UTF-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String finalUrl=Constants.addNewAddressUrl +Constants.userId+"&city="+cityValue+"&city_zip="+ zipcodeValue +"&city_zip_area="+areaValue+"&address="+fullAddressValue ;

        new AddNewAddressAsyncTask(getActivity().getApplicationContext(),new AddNewAddressAsyncTask.AddNewAddressCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.LOG_TAG);
                progressDialog.setMessage("Adding Your Address");
                progressDialog.show();


            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    replaceFragment(new ProfileFragment());

                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Unable to add adress now.\nPlease try again later",5000).show();
                }

            }
        }).execute(finalUrl);

    }




    public void populateCities(){

        String finalUrl = Constants.getCityUrl;
        new FetchCitiesAsyncTask(getActivity().getApplicationContext(),new FetchCitiesAsyncTask.FetchCitiesCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Please Wait");
                progressDialog.show();
            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    setCityAdapter();

                }
                else{

                    Toast.makeText(getActivity().getApplicationContext()," Cannot add address now.\nTry Again Later",5000).show();
                }

            }
        }).execute(finalUrl);


    }

    public void setCityAdapter(){


        cityText.setVisibility(View.GONE);
        city.setVisibility(View.VISIBLE);

        city.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.row_spinner_layout,Constants.cities));
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String cityValueFromSpinner = city.getSelectedItem().toString();
                String id = Constants.citiesMap.get(cityValueFromSpinner);
                populateZipcodes(id);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void populateZipcodes(String cityId){

        String finalUrl = Constants.getZipCodeUrl+cityId;
        new FetchZipcodesAsyncTask(getActivity().getApplicationContext(),new FetchZipcodesAsyncTask.FetchZipcodesCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            @Override
            public void onResult(boolean result) {
                progressDialog.dismiss();
                if(result){
                    zipcode.setVisibility(View.VISIBLE);
                    setZipcodeAdapter();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"There was error.Please Try Again Later",5000).show();
                }
            }
        }).execute(finalUrl);


    }

    public void setZipcodeAdapter(){

        zipText.setVisibility(View.GONE);
        zipcode.setVisibility(View.VISIBLE);

        zipcode.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.row_spinner_layout,Constants.zipcodes));
        zipcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String zipcodeValueFromSpinner = zipcode.getSelectedItem().toString();
                String id = Constants.zipcodesMap.get(zipcodeValueFromSpinner);
                populateAreas(id);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void populateAreas(String zipcodeId){


        String finalUrl = Constants.getZipAreaUrl+zipcodeId;
        new FetchAreasAsyncTask(getActivity().getApplicationContext(),new FetchAreasAsyncTask.FetchAreasCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.APP_NAME);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }
            @Override
            public void onResult(boolean result) {
                progressDialog.dismiss();
                if(result){
                    area.setVisibility(View.VISIBLE);
                    setAreasAdapter();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"There was error.Please Try Again Later",5000).show();
                }
            }
        }).execute(finalUrl);


    }
    public void setAreasAdapter(){


        areaText.setVisibility(View.GONE);
        area.setVisibility(View.VISIBLE);

        area.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.row_spinner_layout,Constants.areas));
        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String areaValueFromSpinner = area.getSelectedItem().toString();
                String id = Constants.areasMap.get(areaValueFromSpinner);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void replaceFragment(Fragment fragment){

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.edit_profile_text_profile_fragment: editYourProfile();
                    break;
                case R.id.left_button_included: update();
                    break;
                case R.id.right_button_included: cancel();
                    break;
                case R.id.add_new_address_button_profile_fragment :
                    update();
                    break;
            }

        }
    };
}
