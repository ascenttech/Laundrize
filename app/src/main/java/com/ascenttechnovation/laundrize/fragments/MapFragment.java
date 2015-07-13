package com.ascenttechnovation.laundrize.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class MapFragment extends Fragment {

    ActionBar actionBar;
    ActionBar.Tab tab;
    String names[]= {"Wash & Iron","Ironing","Dry Cleaning","Wash & Dry"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_trial_and_error,container,false);

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        for(int i =0 ;i<4;i++){

            tab = actionBar.newTab()
                    .setIcon(R.drawable.collection_icon)
                    .setCustomView(R.layout.custom_tab_layout)
                    .setText(names[i])
                    .setTabListener(listener);
            actionBar.addTab(tab);
        }

        return v;
    }

    ActionBar.TabListener listener = new ActionBar.TabListener() {
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            Log.d(Constants.LOG_TAG," Tab Name "+ tab.getText().toString());
            switch(tab.getText().toString()){

                case "Wash & Iron": getChildFragmentManager().beginTransaction()
                        .replace(R.id.frame,new DataHolderFragment())
                        .commit();
                    break;
                case "Ironing": getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new DataHolderFragment())
                        .commit();
                     break;
                case "Dry Cleaning": getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new AddressFragment())
                        .commit();
                    break;
                case "Wash & Dry": getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new AddressFragment())
                        .commit();
                    break;


            }

        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    };
}
