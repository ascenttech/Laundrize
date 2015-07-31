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
import com.ascenttechnovation.laundrize.async.TrackOrdersAsyncTask;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG, Constants.TrackOrdersFragement);

        v = inflater.inflate(R.layout.fragment_track_orders,container,false);

        customActionBar();
        settingTheAdapter(v);

        if(!Constants.ordersTracked){

            getOrders();
        }

        return v;
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Track Orders");

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
        trackOrdersAdapter = new TrackOrdersRecyclerAdapter(getActivity().getApplicationContext());
        trackOrdersRecyclerView.setAdapter(trackOrdersAdapter);

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

}
