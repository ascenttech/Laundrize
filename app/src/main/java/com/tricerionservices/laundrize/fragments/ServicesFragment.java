package com.tricerionservices.laundrize.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.activities.LandingActivity;
import com.tricerionservices.laundrize.adapters.TabsViewPagerAdapter;
import com.tricerionservices.laundrize.async.FetchLaundryServicesAsyncTask;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class ServicesFragment extends Fragment implements ActionBar.TabListener,ViewPager.OnPageChangeListener {

    ActionBar actionBar;
    ActionBar.Tab tabs;

    String names[]= {"Ironing\nWearables","Ironing\nHouseholds","Wash & Iron\nWearables","Wash & Iron\nHouseholds","Dry Clean\nWearables","Dry Clean\nHouseholds","Shoe\nLaundry","Bag\nLaundry","Others"};
    int icons[] = {R.drawable.icon_ironing,R.drawable.icon_ironing,R.drawable.icon_washing,R.drawable.icon_washing,R.drawable.icon_dry_clean,R.drawable.icon_dry_clean,R.drawable.icon_washing,R.drawable.icon_dry_clean,R.drawable.icon_dry_clean};
    private LinearLayout footer;
    private CustomButton mainMenu,placeOrder;
    ViewPager viewPager;
    TabsViewPagerAdapter viewPagerAdapter;
    private ProgressDialog progressDialog;
    private View v;

    private String quantity,ordertotal,total,orderId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG,Constants.ServicesFragement);

        v = inflater.inflate(R.layout.fragment_services,container,false);

        viewPager = (ViewPager) v.findViewById(R.id.myviewpager);

        if(Constants.isInternetAvailable(getActivity().getApplicationContext())){

            Log.d(Constants.LOG_TAG," Fetching the services from services fragmen");
            fetchServices();
        }
        else{

            Toast.makeText(getActivity().getApplicationContext(),"Internet is required for this app.",5000).show();
        }


        return v;
    }



    public void fetchServices(){

        String finalUrl = Constants.fetchLaundrySevicesUrl+ Constants.userId+"&address_id="+Constants.addressId;
        new FetchLaundryServicesAsyncTask(getActivity().getApplicationContext(), new FetchLaundryServicesAsyncTask.FetchLaundryServicesCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.LOG_TAG);
                progressDialog.setMessage("Getting Laundry Services");
                progressDialog.show();
            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    customActionBar();
                    findViews(v);
                    setViews();
                    setClickListeners();

                }
                else{

                    Toast.makeText(getActivity().getApplicationContext(),"Couldnt fetch services \nTry Again Later",5000).show();
                }

            }
        }).execute(finalUrl);

    }

    public void customActionBar(){

        Log.d(Constants.LOG_TAG," Setting the actionbar ");
        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Services");

        for(int i =0 ;i<names.length;i++){

            actionBar.addTab(
                    actionBar.newTab()
                            .setCustomView(makeDummyTab(names[i],icons[i],i))
                            .setTabListener(this));
        }
    }


    private void findViews(View v){

        mainMenu = (CustomButton) v.findViewById(R.id.left_button_included);
        placeOrder = (CustomButton) v.findViewById(R.id.right_button_included);

    }

    private void setViews(){

        mainMenu.setText("Main Menu");
        placeOrder.setText("Place Order");


        viewPagerAdapter = new TabsViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(this);
    }

    private void setClickListeners(){

        mainMenu.setOnClickListener(listener);
        placeOrder.setOnClickListener(listener);
    }

    public View makeDummyTab(String tabName, int tabIcon,int position){

        View v = View.inflate(getActivity().getApplicationContext(),R.layout.custom_tab_layout,null);
        v.setTag("view_"+position);

        TextView tabText = (TextView)v.findViewById(R.id.tab_text);
        tabText.setText(tabName);
        tabText.setCompoundDrawablesWithIntrinsicBounds(tabIcon,0,0,0);
        tabText.setCompoundDrawablePadding(5);
        return v;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        Log.d(Constants.LOG_TAG,"Position "+tab.getPosition());
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.left_button_included: replaceFragment(new LandingFragment());
                    break;

                case R.id.right_button_included:
                    if(Constants.order.size()>0){

                        replaceFragment(new PlaceOrderFragment());
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),"No items selected",5000).show();
                    }
                    break;
            }
        }
    };



}
