package com.ascenttechnovation.laundrize.fragments;

import android.animation.Animator;
import android.animation.ValueAnimator;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 08-07-2015.
 */
public class AddressFragment extends Fragment {

    private TextView selectAddressChild;
    private Button selectAddress,addNewAddress,updateNewAddress;
    private LinearLayout addNewAddressChild;
    private int height;
    private ActionBar actionBar;
    private Animation animShow;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_address,null);

        customActionBar();
        findViews(v);
        setViews();

        height = selectAddressChild.getHeight();

        return v;
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    private void findViews(View v){

        selectAddress = (Button) v.findViewById(R.id.select_address_button_address_fragment);
        selectAddressChild = (TextView) v.findViewById(R.id.available_address_text_address_fragment);
        addNewAddress = (Button) v.findViewById(R.id.add_new_address_button_address_fragment);
        addNewAddressChild = (LinearLayout) v.findViewById(R.id.add_new_address_linear_layout_address_fragment);
        updateNewAddress = (Button) v.findViewById(R.id.update_this_address_add_new_address);

    }

    private void setViews(){

        selectAddress.setOnClickListener(listener);
        addNewAddress.setOnClickListener(listener);
        updateNewAddress.setOnClickListener(listener);
    }

    public void expand(View v){

        Log.d(Constants.LOG_TAG," EXPANDED ");
        //set Visible
        v.setVisibility(View.VISIBLE);

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

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new ServicesFragment())
                .commit();

    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.select_address_button_address_fragment:
                    if(selectAddressChild.getVisibility()==View.GONE){
                        if(addNewAddressChild.getVisibility() == View.GONE){

                            expand(selectAddressChild);

                        }
                        else{

                            expand(selectAddressChild);
                            collapse(addNewAddressChild);
                        }

                    }
                    else{

                        collapse(selectAddressChild);
                    }

                    break;
                case R.id.add_new_address_button_address_fragment:
                    if(addNewAddressChild.getVisibility()==View.GONE){
                        if(selectAddressChild.getVisibility() == View.GONE){

                            expand(addNewAddressChild);
                        }
                        else{

                            expand(addNewAddressChild);
                            collapse(selectAddressChild);
                        }


                    }
                    else{

                        collapse(addNewAddressChild);
                    }
                    break;
                case R.id.update_this_address_add_new_address: updateNewAddress();
                    break;

            }


        }
    };

}
