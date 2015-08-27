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
import com.ascenttechnovation.laundrize.adapters.TrackOrdersRecyclerAdapter;
import com.ascenttechnovation.laundrize.async.FetchTrackOrdersAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.data.TrackOrdersData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class TrackOrdersFragment extends Fragment {

    private RecyclerView trackOrdersRecyclerView;
    private RecyclerView.Adapter trackOrdersAdapter;
    private RecyclerView.LayoutManager trackOrdersLayoutManager;
    private ArrayList<TrackOrdersData> trackOrdersData;
    Context context;
    ActionBar actionBar;
    private ProgressDialog progressDialog;
    private View v;
    private CustomButton placeOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG, Constants.TrackOrdersFragement);

        v = inflater.inflate(R.layout.fragment_track_orders,container,false);

        customActionBar();
        settingTheAdapter(v);

//        // we will check if the orders have been parsed from the server or not
//        if(!Constants.ordersTracked){

            getOrders();

//        }

        // The find Views and setViews are only for the footer button so it doesnt matter if the oreders are there
        // or not because you want to show the footer button
        findViews(v);
        setViews();

        return v;
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Track Orders");

    }

    private void findViews(View v){

        placeOrder = (CustomButton) v.findViewById(R.id.footer_button_included);

    }

    private void setViews(){

        placeOrder.setText("PLACE NEW ORDER");
        placeOrder.setOnClickListener(listener);

    }


    private void settingTheAdapter(View v){

        trackOrdersRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_track_orders_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        trackOrdersRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        trackOrdersLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        trackOrdersRecyclerView.setLayoutManager(trackOrdersLayoutManager);

        // specify an adapter (see also next example)
//        trackOrdersAdapter = new TrackOrdersRecyclerAdapter(getActivity().getApplicationContext(), Constants.laundryServicesSubCategory);
        trackOrdersAdapter = new TrackOrdersRecyclerAdapter(getActivity().getApplicationContext(),Constants.trackOrdersData);
        trackOrdersRecyclerView.setAdapter(trackOrdersAdapter);

    }

    public void getOrders(){

        String finalUrl = Constants.trackOrdersUrl+Constants.userId;

        new FetchTrackOrdersAsyncTask(getActivity().getApplicationContext(),new FetchTrackOrdersAsyncTask.FetchTrackOrdersCallback() {
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

                    if(Constants.noOrdersToTrack){

                        Toast.makeText(getActivity().getApplicationContext(),"No orders to be tracked",5000).show();
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
