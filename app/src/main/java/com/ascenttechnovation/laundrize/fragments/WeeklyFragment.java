package com.ascenttechnovation.laundrize.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;

/**
 * Created by ADMIN on 23-07-2015.
 */
public class WeeklyFragment extends Fragment {

    private Button back,placeOrder;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_weekly,container,false);
        findViews(v);
        setViews();

        return v;
    }

    private void findViews(View v){

        back = (Button) v.findViewById(R.id.left_button_included);
        placeOrder = (Button) v.findViewById(R.id.right_button_included);
    }

    private void setViews(){

        back.setText("BACK");
        back.setOnClickListener(listener);

        placeOrder.setText("PLACE ORDER");
        placeOrder.setOnClickListener(listener);

    }

    public void back(){

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new AddressFragment())
                .commit();

    }

    public void placeOrder(){

        ((LandingActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new LandingFragment())
                .commit();


//        new PlaceWeeklyOrderAsyncTask(getActivity().getApplicationContext(),new PlaceWeeklyOrderAsyncTask.PlaceWeeklyOrderCallback() {
//            @Override
//            public void onStart(boolean status) {
//
//                progressDialog = new ProgressDialog(getActivity());
//                progressDialog.setTitle(Constants.LOG_TAG);
//                progressDialog.setMessage("Placing your order");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//            }
//            @Override
//            public void onResult(boolean result) {
//
//                progressDialog.dismiss();
//
//            }
//        });

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){

                case R.id.left_button_included: back();
                    break;
                case R.id.right_button_included: placeOrder();
                    break;

            }

        }
    };

}
