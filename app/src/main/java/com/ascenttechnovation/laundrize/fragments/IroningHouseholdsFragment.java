package com.ascenttechnovation.laundrize.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.adapters.DryCleanHouseholdsRecyclerAdapter;
import com.ascenttechnovation.laundrize.adapters.IroningHouseholdsRecyclerAdapter;
import com.ascenttechnovation.laundrize.data.DryCleanWearablesData;
import com.ascenttechnovation.laundrize.data.IroningHouseholdsData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class IroningHouseholdsFragment extends Fragment {

    private RecyclerView ironingHouseholdsRecyclerView;
    private RecyclerView.Adapter ironingHouseholdsAdapter;
    private RecyclerView.LayoutManager ironingHouseholdsLayoutManager;
    private ArrayList<IroningHouseholdsData> ironingHouseholdsData;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ironing_house_holds,container,false);

        settingTheAdapter(v);
        return v;
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
