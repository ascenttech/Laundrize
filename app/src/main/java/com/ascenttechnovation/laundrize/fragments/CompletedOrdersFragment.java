package com.ascenttechnovation.laundrize.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.adapters.CompletedOrdersRecyclerAdapter;
import com.ascenttechnovation.laundrize.adapters.TrackOrdersRecyclerAdapter;
import com.ascenttechnovation.laundrize.async.TrackOrdersAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.data.CompletedOrdersData;
import com.ascenttechnovation.laundrize.data.TrackOrdersData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class CompletedOrdersFragment extends Fragment {

    private RecyclerView completedOrdersRecyclerView;
    private RecyclerView.Adapter completedOrdersAdapter;
    private RecyclerView.LayoutManager completedOrdersLayoutManager;
    private ArrayList<CompletedOrdersData> completedOrdersData;
    private CustomButton placeOrder;

    Context context;
    ActionBar actionBar;
    private ProgressDialog progressDialog;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_completed_orders,container,false);

        findViews(v);
        setViews();

        customActionBar();
        settingTheAdapter(v);
        if(!Constants.completedOrdersFetched){

            getOrders();
        }

        Log.d(Constants.LOG_TAG, Constants.CompletedOrdersFragement);

        return v;
    }

    private void findViews(View v){

        placeOrder = (CustomButton) v.findViewById(R.id.footer_button_included);
    }

    private void setViews() {

        placeOrder.setText("Place New Order");
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Completed Orders");

    }

    private void settingTheAdapter(View v){

        completedOrdersRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_completed_orders_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        completedOrdersRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        completedOrdersLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        completedOrdersRecyclerView.setLayoutManager(completedOrdersLayoutManager);

        // specify an adapter (see also next example)
//        completedOrdersAdapter = new TrackOrdersRecyclerAdapter(getActivity().getApplicationContext(), Constants.laundryServicesSubCategory);
        completedOrdersAdapter = new CompletedOrdersRecyclerAdapter(getActivity().getApplicationContext(),Constants.completedOrdersData);
        completedOrdersRecyclerView.setAdapter(completedOrdersAdapter);

    }

    public void getOrders(){

        String finalUrl = Constants.trackOrdersUrl+Constants.userId;

        new TrackOrdersAsyncTask(getActivity().getApplicationContext(),new TrackOrdersAsyncTask.TrackOrdersCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.LOG_TAG);
                progressDialog.setMessage("Getting Your Orders");
                progressDialog.show();

            }

            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){

                    customActionBar();
                    settingTheAdapter(v);

                }
                else{

                    Toast.makeText(getActivity().getApplicationContext(),"Couldn't fetch your order \nTry Again Later",5000).show();
                }

            }
        }).execute(finalUrl);


    }

    public void placeOrder(){

        replaceFragment(new LandingFragment());
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

                case R.id.footer_button_included: placeOrder();
            }
        }
    };

}
