package com.tricerionservices.laundrize.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.adapters.NavigationDrawerRecyclerAdapter;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 11-08-2015.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment {

    View v;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_navigation_drawer);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new NavigationDrawerRecyclerAdapter(getActivity().getApplicationContext(), Constants.navigationDrawerData);
        mRecyclerView.setAdapter(mAdapter);


        return v;
    }
}