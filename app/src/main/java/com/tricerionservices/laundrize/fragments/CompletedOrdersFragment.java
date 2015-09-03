package com.tricerionservices.laundrize.fragments;

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

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.activities.LandingActivity;
import com.tricerionservices.laundrize.adapters.CompletedOrdersRecyclerAdapter;
import com.tricerionservices.laundrize.async.FetchCompletedOrdersAsyncTask;
import com.tricerionservices.laundrize.custom.CustomButton;
import com.tricerionservices.laundrize.data.CompletedOrdersData;
import com.tricerionservices.laundrize.utils.Constants;

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
//        if(!Constants.completedOrdersFetched){


//        }

        Log.d(Constants.LOG_TAG, Constants.CompletedOrdersFragement);

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(Constants.isInternetAvailable(getActivity().getApplicationContext())){

            getOrders();
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Internet is required for this app.",5000).show();
        }
    }

    private void findViews(View v){

        placeOrder = (CustomButton) v.findViewById(R.id.footer_button_included);
    }

    private void setViews() {

        placeOrder.setText("Place New Order");
        placeOrder.setOnClickListener(listener);
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

        completedOrdersAdapter = new CompletedOrdersRecyclerAdapter(getActivity().getApplicationContext(),Constants.completedOrdersData);
        completedOrdersRecyclerView.setAdapter(completedOrdersAdapter);

    }

    public void getOrders(){

        String finalUrl = Constants.completedOrdersUrl+Constants.userId+"&type=completed";

        new FetchCompletedOrdersAsyncTask(getActivity().getApplicationContext(),new FetchCompletedOrdersAsyncTask.FetchCompletedOrdersCallback() {
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

                    if(Constants.noCompletedOrders){

                        Toast.makeText(getActivity().getApplicationContext(),"There are no completed orders for yet for you",5000).show();
                    }
                    else{

                        Toast.makeText(getActivity().getApplicationContext(),"Couldn't fetch your order \nTry Again Later",5000).show();
                    }

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
