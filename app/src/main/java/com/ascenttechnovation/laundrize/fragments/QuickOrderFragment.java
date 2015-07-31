package com.ascenttechnovation.laundrize.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 31-07-2015.
 */
public class QuickOrderFragment extends Fragment {

    View v;
    private Dialog dialog;
    private CheckBox ironing,washing,bags;
    private CardView ironingLayout,washingLayout,bagsLayout,collectionLayout;
    private CustomButton done;
    private ActionBar actionBar;
    private CustomTextView ironingTitleText,ironingDateText,ironingTimeText,washingTitleText,washingDateText,washingTimeText,bagsTitleText,bagsDateText,bagsTimeText,collectionTitleText,collectionDateText,collectionTimeText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_quick_order,container,false);

        Log.d(Constants.LOG_TAG, Constants.QuickOrderFragement);

        customActionBar();
        popUp();
        findViews();
        setViews();

        return v;
    }

    public void popUp(){

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_quick_order);
        dialog.setTitle("Select The Services");
        dialog.setCancelable(false);
        dialog.show();


        ironing = (CheckBox) dialog.findViewById(R.id.quick_service_ironing);
        ironing.setOnClickListener(listener);

        washing = (CheckBox) dialog.findViewById(R.id.quick_service_washing);
        washing.setOnClickListener(listener);

        bags = (CheckBox) dialog.findViewById(R.id.quick_service_bags);
        bags.setOnClickListener(listener);

        done = (CustomButton) dialog.findViewById(R.id.quick_service_done);
        done.setOnClickListener(listener);

    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Quick Order");

    }

    private void findViews(){

        collectionLayout = (CardView) v.findViewById(R.id.collection_layout_quick_fragment);
        collectionTitleText = (CustomTextView) collectionLayout.findViewById(R.id.service_included);
        collectionDateText = (CustomTextView) collectionLayout.findViewById(R.id.select_date_slot_included);
        collectionTimeText = (CustomTextView) collectionLayout.findViewById(R.id.select_time_slot_included);

        ironingLayout = (CardView) v.findViewById(R.id.ironing_layout_quick_fragment);
        ironingTitleText = (CustomTextView) ironingLayout.findViewById(R.id.service_included);
        ironingDateText = (CustomTextView) ironingLayout.findViewById(R.id.select_date_slot_included);
        ironingTimeText = (CustomTextView) ironingLayout.findViewById(R.id.select_time_slot_included);


        washingLayout = (CardView) v.findViewById(R.id.washing_layout_quick_fragment);
        washingTitleText = (CustomTextView) washingLayout.findViewById(R.id.service_included);
        washingDateText = (CustomTextView) washingLayout.findViewById(R.id.select_date_slot_included);
        washingTimeText = (CustomTextView) washingLayout.findViewById(R.id.select_time_slot_included);

        bagsLayout = (CardView) v.findViewById(R.id.bags_layout_quick_fragment);
        bagsTitleText = (CustomTextView) bagsLayout.findViewById(R.id.service_included);
        bagsDateText = (CustomTextView) bagsLayout.findViewById(R.id.select_date_slot_included);
        bagsTimeText = (CustomTextView) bagsLayout.findViewById(R.id.select_time_slot_included);

    }

    private void setViews(){


        ironingLayout.setVisibility(View.GONE);
        washingLayout.setVisibility(View.GONE);
        bagsLayout.setVisibility(View.GONE);

        collectionTitleText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_collection,0,0,0);

        collectionTitleText.setText("Collection");
        ironingTitleText.setText("Delivery : Ironing");
        washingTitleText.setText("Delivery : Washables");
        bagsTitleText.setText("Delivery : Bags & Shoes");


    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.quick_service_ironing:
                    if(ironing.isChecked()){
                        ironingLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        ironingLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.quick_service_washing:
                    if(washing.isChecked()){
                        washingLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        washingLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.quick_service_bags:
                    if(bags.isChecked()){
                        bagsLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        bagsLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.quick_service_done: dialog.dismiss();
                    break;


            }

        }
    };


}
