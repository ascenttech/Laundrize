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
import com.tricerionservices.laundrize.adapters.IroningHouseholdsRecyclerAdapter;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class IroningHouseholdsFragment extends Fragment {

    private RecyclerView ironingHouseholdsRecyclerView;
    private RecyclerView.Adapter ironingHouseholdsAdapter;
    private RecyclerView.LayoutManager ironingHouseholdsLayoutManager;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG, Constants.IroningHouseholdsFragement);

        View v = inflater.inflate(R.layout.fragment_ironing_house_holds,container,false);
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

        ironingHouseholdsRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_ironing_house_holds_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        ironingHouseholdsRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        ironingHouseholdsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        ironingHouseholdsRecyclerView.setLayoutManager(ironingHouseholdsLayoutManager);

        // specify an adapter (see also next example)
        ironingHouseholdsAdapter = new IroningHouseholdsRecyclerAdapter(getActivity().getApplicationContext(), Constants.ironingHouseholdsData);
        ironingHouseholdsRecyclerView.setAdapter(ironingHouseholdsAdapter);

    }
}
