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
import com.tricerionservices.laundrize.adapters.DryCleanWearablesRecyclerAdapter;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class DryCleanWearablesFragment extends Fragment {

    private RecyclerView dryCleanWearablesRecyclerView;
    private RecyclerView.Adapter dryCleanWearablesAdapter;
    private RecyclerView.LayoutManager dryCleanWearablesLayoutManager;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dry_clean_wearables,container,false);

        settingTheAdapter(v);

        Log.d(Constants.LOG_TAG, Constants.DryCleanWearablesFragement);

        Constants.reintializeTheValues(getActivity().getApplicationContext());
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

        dryCleanWearablesRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_dry_clean_wearables_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        dryCleanWearablesRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        dryCleanWearablesLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        dryCleanWearablesRecyclerView.setLayoutManager(dryCleanWearablesLayoutManager);

        // specify an adapter (see also next example)
        dryCleanWearablesAdapter = new DryCleanWearablesRecyclerAdapter(getActivity().getApplicationContext(), Constants.dryCleanWearablesData);
        dryCleanWearablesRecyclerView.setAdapter(dryCleanWearablesAdapter);

    }
}
