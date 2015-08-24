package com.ascenttechnovation.laundrize.fragments;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.async.AddNewAddressAsyncTask;
import com.ascenttechnovation.laundrize.async.FetchAddressAsyncTask;
import com.ascenttechnovation.laundrize.async.FetchAllSlotsAsyncTask;
import com.ascenttechnovation.laundrize.async.FetchAreasAsyncTask;
import com.ascenttechnovation.laundrize.async.FetchCitiesAsyncTask;
import com.ascenttechnovation.laundrize.async.FetchZipcodesAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.net.URLEncoder;

/**
 * Created by ADMIN on 08-07-2015.
 */
public class AddressFragment extends Fragment {

    private ViewGroup selectAddressChild;
    private CustomButton selectAddress,addNewAddress, saveNewAddress;
    private ScrollView scrollView;
    private ScrollView addNewAddressChild;
    private int height;
    private ActionBar actionBar;
    private Animation animShow;
    private ProgressDialog progressDialog;
    private LayoutInflater layoutInflater;

    private Context context;
    private View v,rowView;
    private CustomTextView address,mobileNumber;
    private EditText buildingName,houseNumber;
    private Spinner city,zipcode,area;
    private String cityValue, zipcodeValue,areaValue,buildingNameValue,houseNumberValue,fullAddressValue;
    private String orderType;

    private CustomTextView cityStaticText,zipcodeStaticText,areaStaticText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG,Constants.AddressFragement);

        v = inflater.inflate(R.layout.fragment_address,container,false);

        orderType = this.getArguments().getString("orderType");
        layoutInflater= inflater;

        customActionBar();
        findViews(v);
        setViews();

        fetchAddresses();
        return v;
    }

//    @Override
//    protected void onResume() {
//        boolean internetAvailable = Constants.isInternetAvailable(getActivity());
//        if(internetAvailable){
//
//
//        }
//        else{
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage("This app requires app connection")
//                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // FIRE ZE MISSILES!
//                            dialog.dismiss();
//                        }
//                    });
//            builder.create();
//            builder.show();
//
//        }
//    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Select Address");
    }

    private void findViews(View v){

        selectAddress = (CustomButton) v.findViewById(R.id.select_address_button_address_fragment);
        scrollView = (ScrollView)v.findViewById(R.id.select_address_scroll_address_fragment);
        selectAddressChild = (ViewGroup) v.findViewById(R.id.available_address_layout_container);
        addNewAddress = (CustomButton) v.findViewById(R.id.add_new_address_button_address_fragment);

//        addNewAddressChild = (LinearLayout) v.findViewById(R.id.add_new_address_linear_layout_address_fragment);
        addNewAddressChild = (ScrollView) v.findViewById(R.id.add_new_address_linear_layout_address_fragment);

        city = (Spinner) v.findViewById(R.id.city_add_new_address);
        cityStaticText = (CustomTextView) v.findViewById(R.id.city_add_new_address_static);

        zipcode = (Spinner) v.findViewById(R.id.zipcode_add_new_address);
        zipcodeStaticText = (CustomTextView) v.findViewById(R.id.zipcode_add_new_address_static);

        area = (Spinner) v.findViewById(R.id.area_add_new_address);
        areaStaticText = (CustomTextView) v.findViewById(R.id.area_add_new_address_static);

        buildingName = (EditText) v.findViewById(R.id.building_or_street_add_new_address);
        houseNumber = (EditText) v.findViewById(R.id.flat_or_house_number_add_new_address);

        saveNewAddress = (CustomButton) v.findViewById(R.id.save_new_address_add_new_address);

    }

    private void setViews(){

        selectAddressChild.setVisibility(View.GONE);
        selectAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus, 0, 0, 0);
        addNewAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus,0,0,0);

    }

    private void fetchAddresses(){

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

                setClickListeners();
                addAvailableAddresses();

            }
        }).execute(finalURL);




    }

    private void setClickListeners(){
        selectAddress.setOnClickListener(listener);
        addNewAddress.setOnClickListener(listener);
        saveNewAddress.setOnClickListener(listener);
    }



    public void addAvailableAddresses(){

        for(int i =0;i< Constants.addressData.size();i++){

            rowView = layoutInflater.inflate(R.layout.include_available_address,null);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,5);
            rowView.setLayoutParams(params);

            address = (CustomTextView) rowView.findViewById(R.id.address_text_included);
            address.setTag("address_"+i);
            address.setVisibility(View.VISIBLE);
            Log.d(Constants.LOG_TAG," Setting the adress "+Constants.addressData.get(i).getFullAddress());
            address.setText(Constants.addressData.get(i).getFullAddress());
            address.setOnClickListener(inflatedViewListener);

            mobileNumber = (CustomTextView) rowView.findViewById(R.id.mobile_number_text_included);
            mobileNumber.setTag("number_"+i);
            mobileNumber.setVisibility(View.VISIBLE);
            Log.d(Constants.LOG_TAG," Setting the mobile number "+Constants.addressData.get(i).getMobileNumber());
            mobileNumber.setText(Constants.addressData.get(i).getMobileNumber());
            mobileNumber.setOnClickListener(inflatedViewListener);

            selectAddressChild.addView(rowView);
        }

    }

    private void changeVisibility(){

        scrollView.setVisibility(View.VISIBLE);

        if(selectAddressChild.getVisibility()==View.GONE){
            selectAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_minus,0,0,0);
            if(addNewAddressChild.getVisibility() == View.GONE){

                expand(selectAddressChild);
            }
            else{

                expand(selectAddressChild);
                selectAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus,0,0,0);
//                addNewAddressChild.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus,0,0,0);
                collapse(addNewAddressChild);
            }


        }// if it is visible then execute the else part and collapse it
        else{
            selectAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus,0,0,0);
            collapse(selectAddressChild);
        }


    }

    public void expand(View v){

        //set Visible
        v.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, v.getMeasuredHeight(),v);
        mAnimator.start();
    }
    public void collapse(final View v){

        int finalHeight = v.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0,v);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnimator.start();

    }

    private ValueAnimator slideAnimator(int start, int end, final View v) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    public void validateNewAddress(){

        if(!buildingName.getText().toString().equalsIgnoreCase("")){

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

        try {

            String cityId = Constants.citiesMap.get(city.getSelectedItem().toString()).toString();
            cityValue = URLEncoder.encode(cityId,"UTF-8");

            String zipId = Constants.zipcodesMap.get(zipcode.getSelectedItem().toString());
            zipcodeValue = URLEncoder.encode(zipId, "UTF-8");

            String areaId = Constants.areasMap.get(area.getSelectedItem().toString()).toString();
            areaValue = URLEncoder.encode(areaId, "UTF-8");
            buildingNameValue = URLEncoder.encode(buildingName.getText().toString(), "UTF-8");
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
                progressDialog.setCancelable(false);
                progressDialog.show();


            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    replaceFragment(new ServicesFragment());


                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Unable to add adress now.\nPlease try again later",5000).show();
                }

            }
        }).execute(finalUrl);



    }

    public void replaceFragment(Fragment fragment){

        Log.d(Constants.LOG_TAG," Entering Replace Fragment "+ fragment.getClass().getName());

        getSlots();

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();

    }

    public void goAhead(int position){

        Constants.addressId = Constants.addressData.get(position).getId();

        if(orderType.equalsIgnoreCase("place")){

            replaceFragment(new ServicesFragment());

        }
        else if(orderType.equalsIgnoreCase("quick")){

            replaceFragment(new QuickOrderFragment());

        }
        else if(orderType.equalsIgnoreCase("weekly")){

            replaceFragment(new WeeklyOrderFragment());

        }
    }

    public void populateCity(){

        cityStaticText.setVisibility(View.GONE);
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

        Log.d(Constants.LOG_TAG,"Setting zip code adapter "+ Constants.zipcodes.size());

        zipcodeStaticText.setVisibility(View.GONE);
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
                    if(result) {
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

        Log.d(Constants.LOG_TAG," Setting Areas Adapter "+ Constants.areas.size());

        areaStaticText.setVisibility(View.GONE);
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


    public void getSlots(){


        String finalUrl = Constants.getslotsUrl+Constants.userId+"&address_id="+Constants.addressId;
//        String finalUrl = Constants.getslotsUrl+Constants.userId;
        new FetchAllSlotsAsyncTask(new FetchAllSlotsAsyncTask.FetchAllSlotsCallback(){
            @Override
            public void onStart(boolean status) {


            }
            @Override
            public void onResult(boolean result) {

                if(result){

                    Constants.slotsFetched = true;

                }
                else{

                    Toast.makeText(getActivity().getApplicationContext(), "Unable to connect to the internet.\nTry again Later", 5000).show();
                }

            }
        }).execute(finalUrl);

    }




    View.OnClickListener inflatedViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String tag = view.getTag().toString();
            String pos[] = tag.split("_");
            int position = Integer.parseInt(pos[1]);

            switch (view.getId()){

                case R.id.address_text_included: goAhead(position);
                    break;
                case R.id.mobile_number_text_included : goAhead(position);
                    break;

            }


        }
    };


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.select_address_button_address_fragment:
                    changeVisibility();
                    break;
                case R.id.add_new_address_button_address_fragment:

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

                                // check if add new address child is visible or not
                                if(addNewAddressChild.getVisibility()==View.GONE){
                                    addNewAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_minus,0,0,0);
                                    if(selectAddressChild.getVisibility() == View.GONE){

                                        populateCity();
                                        expand(addNewAddressChild);

                                    }
                                    else{

                                        expand(addNewAddressChild);
                                        selectAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus,0,0,0);
                                        collapse(selectAddressChild);
                                    }


                                }// if it is visible then execute the else part and collapse it
                                else{
                                    addNewAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus,0,0,0);
                                    collapse(addNewAddressChild);
                                }


                            }
                            else{

                                Toast.makeText(getActivity().getApplicationContext()," Cannot add address now.\nTry Again Later",5000).show();
                            }

                        }
                    }).execute(finalUrl);


                    break;
                case R.id.save_new_address_add_new_address: validateNewAddress();
                    break;


            }


        }
    };

}
