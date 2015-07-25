package com.ascenttechnovation.laundrize.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;

/**
 * Created by ADMIN on 25-07-2015.
 */
public class ContactUsFragment extends Fragment {

    private ActionBar actionBar;
    private Button call;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_contact_us,container,false);

        customActionBar();
        findViews(v);
        setViews();

        return v;
    }
    public void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();

    }

    private void findViews(View v){


        call = (Button) v.findViewById(R.id.call_button_contact_us_fragment);

    }
    private void setViews(){

        call.setOnClickListener(listener);
    }

    public void call(){

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:8007012346"));
        startActivity(intent);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            call();

        }
    };

}
