package com.ascenttechnovation.laundrize.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ascenttechnovation.laundrize.R;

/**
 * Created by ADMIN on 08-07-2015.
 */
public class AddressFragment extends Fragment {

    LinearLayout child,parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_address,null);

        findViews(v);
        setViews();


        return v;
    }

    private void findViews(View v){

        parent = (LinearLayout) v.findViewById(R.id.parent);
        child = (LinearLayout) v.findViewById(R.id.child);

    }

    private void setViews(){

        parent.setOnClickListener(listener);
    }

    public void expand(){

    }
    public void collapse(){


    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(child.getVisibility()==View.GONE){

                expand();
            }
            else{

                collapse();
            }

        }
    };
}
