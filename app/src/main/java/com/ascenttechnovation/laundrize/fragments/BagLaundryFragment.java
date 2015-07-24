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
import com.ascenttechnovation.laundrize.adapters.BagLaundryRecyclerAdapter;
import com.ascenttechnovation.laundrize.adapters.IroningWearablesRecyclerAdapter;
import com.ascenttechnovation.laundrize.data.GeneralData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class BagLaundryFragment extends Fragment {

    private RecyclerView bagLaundryRecyclerView;
    private RecyclerView.Adapter bagLaundryAdapter;
    private RecyclerView.LayoutManager bagLaundryLayoutManager;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bag_laundry,container,false);

        settingTheAdapter(v);

        return v;
    }

    private void settingTheAdapter(View v){

        bagLaundryRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_bag_laundry_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        bagLaundryRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        bagLaundryLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        bagLaundryRecyclerView.setLayoutManager(bagLaundryLayoutManager);

        // specify an adapter (see also next example)
        bagLaundryAdapter = new BagLaundryRecyclerAdapter(getActivity().getApplicationContext(), Constants.bagLaundryData);
        bagLaundryRecyclerView.setAdapter(bagLaundryAdapter);

    }
}
