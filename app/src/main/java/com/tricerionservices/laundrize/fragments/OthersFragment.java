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

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.adapters.OthersRecyclerAdapter;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 24-07-2015.
 */
public class OthersFragment extends Fragment {

    private RecyclerView othersRecyclerView;
    private RecyclerView.Adapter othersAdapter;
    private RecyclerView.LayoutManager othersLayoutManager;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_others,container,false);

        Log.d(Constants.LOG_TAG, Constants.OtherFragement);

        settingTheAdapter(v);
        return v;
    }

    private void settingTheAdapter(View v){

        othersRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_others_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        othersRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        othersLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        othersRecyclerView.setLayoutManager(othersLayoutManager);

        // specify an adapter (see also next example)
        othersAdapter = new OthersRecyclerAdapter(getActivity().getApplicationContext(), Constants.othersData);
        othersRecyclerView.setAdapter(othersAdapter);

    }
}
