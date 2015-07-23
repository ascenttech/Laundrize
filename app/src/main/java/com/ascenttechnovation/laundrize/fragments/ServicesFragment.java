package com.ascenttechnovation.laundrize.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class ServicesFragment extends Fragment {

    ActionBar actionBar;
    ActionBar.Tab tab;

    String names[]= {"Ironing: Wearables","Ironing: Households","Dry Cleaning","Wash & Iron: Wearables","Wash & Iron: Households","Dry Clean: Wearables","Dry Clean: Households","Shoe Laundry","Bag Laundry","Others"};
    private LinearLayout footer;
    Button mainMenu,placeOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_services,container,false);

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        for(int i =0 ;i<names.length;i++){

            tab = actionBar.newTab()
                    .setCustomView(makeDummyTab(names[i], R.drawable.baniyan,i))
                    .setTabListener(actionBarListener);
            actionBar.addTab(tab);
        }

        findViews(v);
        setViews();
        return v;
    }


    public View makeDummyTab(String tabName, int tabIcon,int position){

        View v = View.inflate(getActivity().getApplicationContext(),R.layout.custom_tab_layout,null);
        v.setTag("view_"+position);

        ImageView tabLogo = (ImageView)v.findViewById(R.id.tab_icon);
        TextView tabText = (TextView)v.findViewById(R.id.tab_text);

        tabLogo.setImageResource(tabIcon);
        tabText.setText(tabName);
        return v;
    }

    private void findViews(View v){

        mainMenu = (Button) v.findViewById(R.id.left_button_included);
        placeOrder = (Button) v.findViewById(R.id.right_button_included);

    }

    private void setViews(){

        mainMenu.setText("Main Menu");
        mainMenu.setOnClickListener(listener);

        placeOrder.setText("Place Order");
        placeOrder.setOnClickListener(listener);

    }

    ActionBar.TabListener actionBarListener = new ActionBar.TabListener() {
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            String tag = tab.getCustomView().getTag().toString();
            String view[] = tag.split("_");
            int position = Integer.parseInt(view[1]);

            switch(position){

                case 0: getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new DataHolderFragment())
                        .commit();
                    break;
                case 1: getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new DataHolderFragment())
                        .commit();
                     break;
                case 2: getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new DataHolderFragment())
                        .commit();
                    break;
                case 3: getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new DataHolderFragment())
                        .commit();
                    break;
                case 4: getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new DataHolderFragment())
                        .commit();
                    break;
                case 5: getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new DataHolderFragment())
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

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.left_button_included: ((LandingActivity)getActivity()).getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.container,new FAQFragment())
                                                    .commit();
                    break;

                case R.id.right_button_included: ((LandingActivity)getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,new FAQFragment())
                        .commit();
                    break;
            }
        }
    };
}
