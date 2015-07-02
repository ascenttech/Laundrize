package com.ascenttechnovation.laundrize.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.adapters.LandingFragmentRecyclerAdapter;
import com.ascenttechnovation.laundrize.data.LandingFragmentData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class LandingFragment extends Fragment {

    private RecyclerView landingFragmentRecyclerView;
    private RecyclerView.Adapter landingFragmentAdapter;
    private RecyclerView.LayoutManager landingFragmentLayoutManager;
    ArrayList<LandingFragmentData> landingFragmentData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG,Constants.LandingFragment);

        View v = inflater.inflate(R.layout.fragment_landing,null);

        findViews(v);
        setViews();

        for(int i =0 ; i<5;i++){

            Constants.landingFragmentData.add(new LandingFragmentData("abc","tittle ","hello there"));

        }

        settingAdapter();

        return v;
    }

    private void findViews(View v){

        landingFragmentRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_landing_fragment);

    }
    private void setViews(){

        landingFragmentRecyclerView.setHasFixedSize(true);


    }

    private void settingAdapter(){

        // use a linear layout manager
        landingFragmentLayoutManager = new LinearLayoutManager(getActivity());
        landingFragmentRecyclerView.setLayoutManager(landingFragmentLayoutManager);

        // specify an adapter (see also next example)
        landingFragmentAdapter = new LandingFragmentRecyclerAdapter(getActivity().getApplicationContext(),Constants.landingFragmentData);
        landingFragmentRecyclerView.setAdapter(landingFragmentAdapter);

    }
}

