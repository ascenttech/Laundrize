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
import com.tricerionservices.laundrize.adapters.WashAndIronHouseholdsRecyclerAdapter;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class WashAndIronHouseholdsFragment extends Fragment {

    private RecyclerView washAndIronHouseholdsRecyclerView;
    private RecyclerView.Adapter washAndIronHouseholdsAdapter;
    private RecyclerView.LayoutManager washAndIronHouseholdsLayoutManager;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG, Constants.WashAndIronHouseholdsFragement);

        View v = inflater.inflate(R.layout.fragment_wash_and_iron_house_holds,container,false);

        settingTheAdapter(v);
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

        washAndIronHouseholdsRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_wash_and_iron_house_holds_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        washAndIronHouseholdsRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        washAndIronHouseholdsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        washAndIronHouseholdsRecyclerView.setLayoutManager(washAndIronHouseholdsLayoutManager);

        // specify an adapter (see also next example)
        washAndIronHouseholdsAdapter = new WashAndIronHouseholdsRecyclerAdapter(getActivity().getApplicationContext(), Constants.washAndIronHouseholdsData);
        washAndIronHouseholdsRecyclerView.setAdapter(washAndIronHouseholdsAdapter);

    }
}
