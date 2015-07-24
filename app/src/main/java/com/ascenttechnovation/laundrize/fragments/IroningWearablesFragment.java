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
import com.ascenttechnovation.laundrize.adapters.IroningWearablesRecyclerAdapter;
import com.ascenttechnovation.laundrize.data.IroningHouseholdsData;
import com.ascenttechnovation.laundrize.data.IroningWearablesData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class IroningWearablesFragment extends Fragment {

    private RecyclerView ironingWearablesRecyclerView;
    private RecyclerView.Adapter ironingWearablesAdapter;
    private RecyclerView.LayoutManager ironingWearablesLayoutManager;
    private ArrayList<IroningWearablesData> ironingWearablesData;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ironing_wearables,container,false);

        settingTheAdapter(v);
        return v;
    }

    private void settingTheAdapter(View v){

        ironingWearablesRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_ironing_wearables_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        ironingWearablesRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        ironingWearablesLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        ironingWearablesRecyclerView.setLayoutManager(ironingWearablesLayoutManager);

        // specify an adapter (see also next example)
        ironingWearablesAdapter = new IroningWearablesRecyclerAdapter(getActivity().getApplicationContext(), Constants.ironingWearablesData);
        ironingWearablesRecyclerView.setAdapter(ironingWearablesAdapter);

    }
}
