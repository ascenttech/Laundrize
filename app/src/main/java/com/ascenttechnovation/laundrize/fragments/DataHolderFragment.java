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
import com.ascenttechnovation.laundrize.adapters.DataHolderFragmentRecyclerAdapter;
import com.ascenttechnovation.laundrize.data.GeneralData;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 13-07-2015.
 */
public class DataHolderFragment extends Fragment {

    private RecyclerView dataholderRecyclerView;
    private RecyclerView.Adapter dataHolderAdapter;
    private RecyclerView.LayoutManager dataLayoutManager;
    private ArrayList<GeneralData> generalData;

//    public DataHolderFragment(ArrayList<GeneralData> generalData) {
//        this.generalData = generalData;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG,Constants.DataHolderFragment);
        View v = inflater.inflate(R.layout.fragment_data_holder,null);

        settingTheAdapter(v);

        return v;
    }

    public void settingTheAdapter(View v){

        dataholderRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_data_holder_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        dataholderRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        dataLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        dataholderRecyclerView.setLayoutManager(dataLayoutManager);

        // specify an adapter (see also next example)
        dataHolderAdapter = new DataHolderFragmentRecyclerAdapter(getActivity().getApplicationContext());
        dataholderRecyclerView.setAdapter(dataHolderAdapter);

    }
}
