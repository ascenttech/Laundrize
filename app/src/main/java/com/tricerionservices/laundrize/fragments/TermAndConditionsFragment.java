package com.tricerionservices.laundrize.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.activities.LandingActivity;

/**
 * Created by ADMIN on 08-08-2015.
 */
public class TermAndConditionsFragment extends Fragment {

    ActionBar actionBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_terms_and_condition,container,false);

        customActionBar();
        return v;

    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Terms And Condiditons");
    }



}

