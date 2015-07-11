package com.ascenttechnovation.laundrize.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class MapFragment extends Fragment {

    ActionBar actionBar;
    ActionBar.Tab tab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_trial_and_error,container,false);

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        for(int i =0 ;i<4;i++){

            tab = actionBar.newTab()
                    .setText("A")
                    .setIcon(R.drawable.collection_icon)
                    .setCustomView(R.layout.custom_tab_layout)
                    .setTabListener(listener);
            actionBar.addTab(tab);
        }


//
//        tab = actionBar.newTab()
//                .setText("B")
//                .setIcon(R.drawable.collection_icon)
//                .setTabListener(listener);
//        actionBar.addTab(tab);
//        tab = actionBar.newTab()
//                .setText("C")
//                .setIcon(R.drawable.collection_icon)
//                .setTabListener(listener);
//        actionBar.addTab(tab);
//        tab = actionBar.newTab()
//                .setText("D")
//                .setIcon(R.drawable.collection_icon)
//                .setTabListener(listener);
//        actionBar.addTab(tab);

        return v;
    }

    ActionBar.TabListener listener = new ActionBar.TabListener() {
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {


            switch(tab.getText().toString()){

//                case "A": ft.replace(android.R.id.content,new FAQFragment());
//                    ft.commit();
//                    break;
                case "B": getChildFragmentManager().beginTransaction()
                        .replace(R.id.frame,new AddressFragment())
                        .commit();
                     break;
                case "C": Toast.makeText(getActivity().getApplicationContext(),"C selected",3000).show();
                    break;
                case "D": Toast.makeText(getActivity().getApplicationContext(),"D selected",3000).show();
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
