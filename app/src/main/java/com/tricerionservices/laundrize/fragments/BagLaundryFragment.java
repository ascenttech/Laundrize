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
import com.tricerionservices.laundrize.adapters.BagLaundryRecyclerAdapter;
import com.tricerionservices.laundrize.utils.Constants;

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

        Log.d(Constants.LOG_TAG, Constants.BagLaundryFragement);

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
