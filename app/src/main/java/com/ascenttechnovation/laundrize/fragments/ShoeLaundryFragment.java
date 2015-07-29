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
import com.ascenttechnovation.laundrize.adapters.ShoeLaundryRecyclerAdapter;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class ShoeLaundryFragment extends Fragment {

    private RecyclerView shoeLaundryRecyclerView;
    private RecyclerView.Adapter shoeLaundryAdapter;
    private RecyclerView.LayoutManager shoeLaundryLayoutManager;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shoe_laundry,container,false);

        settingTheAdapter(v);
        return v;
    }

    private void settingTheAdapter(View v){

        shoeLaundryRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_shoe_laundry_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        shoeLaundryRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        shoeLaundryLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        shoeLaundryRecyclerView.setLayoutManager(shoeLaundryLayoutManager);

        // specify an adapter (see also next example)
        shoeLaundryAdapter = new ShoeLaundryRecyclerAdapter(getActivity().getApplicationContext(), Constants.shoeLaundryData);
        shoeLaundryRecyclerView.setAdapter(shoeLaundryAdapter);

    }
}
