package com.tricerionservices.laundrize.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.adapters.WashAndIronWearablesRecyclerAdapter;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class WashAndIronWearablesFragment extends Fragment {

    private RecyclerView washAndIronWearablesRecyclerView;
    private RecyclerView.Adapter washAndIronWearablesAdapter;
    private RecyclerView.LayoutManager washAndIronWearablesLayoutManager;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Log.d(Constants.LOG_TAG, Constants.WashAndIronWearablesFragement);
        View v = inflater.inflate(R.layout.fragment_wash_and_iron_wearables,container,false);

        settingTheAdapter(v);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(Constants.isInternetAvailable(getActivity().getApplicationContext())){

        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "Internet is required for this app.", 5000).show();
        }
    }

    private void settingTheAdapter(View v){

        washAndIronWearablesRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_wash_and_iron_wearables_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        washAndIronWearablesRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        washAndIronWearablesLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        washAndIronWearablesRecyclerView.setLayoutManager(washAndIronWearablesLayoutManager);

        // specify an adapter (see also next example)
        washAndIronWearablesAdapter = new WashAndIronWearablesRecyclerAdapter(getActivity().getApplicationContext(), Constants.washAndIronWearablesData);
        washAndIronWearablesRecyclerView.setAdapter(washAndIronWearablesAdapter);

    }
}
