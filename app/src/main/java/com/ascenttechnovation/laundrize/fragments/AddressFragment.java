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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.async.AddNewAddressAsyncTask;
import com.ascenttechnovation.laundrize.async.FetchAddressAsyncTask;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.net.URLEncoder;

/**
 * Created by ADMIN on 08-07-2015.
 */
public class AddressFragment extends Fragment {

    private ViewGroup selectAddressChild;
    private Button selectAddress,addNewAddress,updateNewAddress;
    private LinearLayout addNewAddressChild;
    private int height;
    private ActionBar actionBar;
    private Animation animShow;
    private ProgressDialog progressDialog;
    private LayoutInflater layoutInflater;

    private Context context;
    private View v,rowView;
    private TextView address,mobileNumber;
    private EditText city,pincode,area,buildingName,houseNumber;
    private String cityValue,pincodeValue,areaValue,buildingNameValue,houseNumberValue,fullAddressValue;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_address,container,false);
        layoutInflater= inflater;

        customActionBar();

        findViews(v);

        setViews();

        return v;
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    private void findViews(View v){

        selectAddress = (Button) v.findViewById(R.id.select_address_button_address_fragment);
        selectAddressChild = (ViewGroup) v.findViewById(R.id.available_address_layout_container);
        addNewAddress = (Button) v.findViewById(R.id.add_new_address_button_address_fragment);

        addNewAddressChild = (LinearLayout) v.findViewById(R.id.add_new_address_linear_layout_address_fragment);
        city = (EditText) v.findViewById(R.id.city_add_new_address);
        pincode = (EditText) v.findViewById(R.id.pincode_add_new_address);
        area = (EditText) v.findViewById(R.id.area_add_new_address);
        buildingName = (EditText) v.findViewById(R.id.building_or_street_add_new_address);
        houseNumber = (EditText) v.findViewById(R.id.flat_or_house_number_add_new_address);


        updateNewAddress = (Button) v.findViewById(R.id.update_this_address_add_new_address);

    }

    private void fetchData(){

        String finalUrl = Constants.fetchAddressUrl+Constants.userId+"&default=0";
        new FetchAddressAsyncTask(getActivity().getApplicationContext(),new FetchAddressAsyncTask.FetchAddressCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.LOG_TAG);
                progressDialog.setMessage("Getting Your Address");
                progressDialog.show();

            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    Constants.addressFetched = true;
                    addAvailableAddresses();
                    changeVisibility();


                }
                else{
                    changeVisibility();
                    Toast.makeText(getActivity().getApplicationContext(),"Couldnt fetch your address.\nTry Again Later",5000).show();
                }

            }
        }).execute(finalUrl);


    }

    public void addAvailableAddresses(){

        for(int i =0;i< Constants.addressData.size();i++){

           rowView = layoutInflater.inflate(R.layout.include_available_address,selectAddressChild);

           address = (TextView) rowView.findViewById(R.id.address_text_included);
           address.setTag("address_"+i);
           Log.d(Constants.LOG_TAG," Setting the adress "+Constants.addressData.get(i).getFullAddress());
           address.setText(Constants.addressData.get(i).getFullAddress());
           address.setOnClickListener(inflatedViewListener);

           mobileNumber = (TextView) rowView.findViewById(R.id.mobile_number_text_included);
           mobileNumber.setTag("number_"+i);
           Log.d(Constants.LOG_TAG," Setting the adress "+Constants.addressData.get(i).getMobileNumber());
           mobileNumber.setText(Constants.addressData.get(i).getMobileNumber());
           mobileNumber.setOnClickListener(inflatedViewListener);
        }

    }

    private void changeVisibility(){

        if(selectAddressChild.getVisibility()==View.GONE){
            selectAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_minus,0,0,0);
            if(addNewAddressChild.getVisibility() == View.GONE){

                expand(selectAddressChild);
            }
            else{

                expand(selectAddressChild);
                selectAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus,0,0,0);
                collapse(addNewAddressChild);
            }


        }// if it is visible then execute the else part and collapse it
        else{
            selectAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus,0,0,0);
            collapse(selectAddressChild);
        }


    }




    private void setViews(){

        selectAddressChild.setVisibility(View.GONE);

        selectAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus, 0, 0, 0);
        selectAddress.setOnClickListener(listener);


        addNewAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_plus,0,0,0);
        addNewAddress.setOnClickListener(listener);
        updateNewAddress.setOnClickListener(listener);
    }

    public void expand(View v){

        Log.d(Constants.LOG_TAG," EXPANDED ");
        //set Visible
        v.setVisibility(View.VISIBLE);

//
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, v.getMeasuredHeight(),v);
        mAnimator.start();
    }
    public void collapse(final View v){

        Log.d(Constants.LOG_TAG," COLLAPSE ");
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

    public void updateNewAddress(){

        try {
            cityValue = URLEncoder.encode(city.getText().toString(), "UTF-8");
            pincodeValue = URLEncoder.encode(pincode.getText().toString(), "UTF-8");
            areaValue = URLEncoder.encode(area.getText().toString(), "UTF-8");
            buildingNameValue = URLEncoder.encode(buildingName.getText().toString(), "UTF-8");
            houseNumberValue = URLEncoder.encode(houseNumber.getText().toString(), "UTF-8");

            String temp = houseNumberValue+","+buildingNameValue;
            fullAddressValue = URLEncoder.encode(temp,"UTF-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String finalUrl=Constants.addNewAddress+Constants.userId+"&city="+cityValue+"&city_zip="+pincodeValue+"&city_zip_area="+areaValue+"&address="+fullAddressValue ;

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

                    ((LandingActivity)getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,new ServicesFragment())
                            .commit();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Unable to add adress now.\nPlease try again later",5000).show();
                }

            }
        }).execute(finalUrl);



    }

    public void goAhead(int position){

        Toast.makeText(getActivity().getApplicationContext(),"Position "+ position,5000).show();

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new ServicesFragment())
                .commit();


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
                    if(!Constants.addressFetched){

                        fetchData();
                    }
                    else{

                        changeVisibility();
                    }


                    break;
                case R.id.add_new_address_button_address_fragment:
                    // check if add new address child is visible or not
                    if(addNewAddressChild.getVisibility()==View.GONE){
                        addNewAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_minus,0,0,0);
                        if(selectAddressChild.getVisibility() == View.GONE){

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
                    break;
                case R.id.update_this_address_add_new_address: updateNewAddress();
                    break;


            }


        }
    };

}
