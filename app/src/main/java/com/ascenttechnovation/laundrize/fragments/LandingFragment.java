package com.ascenttechnovation.laundrize.fragments;

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
 * Created by ADMIN on 08-07-2015.
 */
public class LandingFragment extends Fragment {

    private Button placeOrder,quickOrder,weeklyOrder,trackOrder,completedOrder;
    private ActionBar actionBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_landing,null);

        customActionBar();

        findViews(v);
        setViews();
        return v;
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    private void findViews(View v){

        placeOrder = (Button) v.findViewById(R.id.place_order_button_order_now_fragment);
        quickOrder = (Button) v.findViewById(R.id.quick_order_button_order_now_fragment);
        weeklyOrder = (Button) v.findViewById(R.id.weekly_order_button_order_now_fragment);
        trackOrder = (Button) v.findViewById(R.id.track_order_button_order_now_fragment);
        completedOrder = (Button) v.findViewById(R.id.completed_order_button_order_now_fragment);

    }

    private void setViews(){

        placeOrder.setOnClickListener(listener);
        quickOrder.setOnClickListener(listener);
        weeklyOrder.setOnClickListener(listener);
        trackOrder.setOnClickListener(listener);
        completedOrder.setOnClickListener(listener);
    }

    public void placeOrder(){

        replaceFragment(new AddressFragment());
    }

    public void quickOrder(){

        replaceFragment(new AddressFragment());

    }

    public void weeklyOrder(){

        replaceFragment(new AddressFragment());

    }

    public void trackOrder(){

        replaceFragment(new AddressFragment());

    }
    public void completedOrder(){

        replaceFragment(new AddressFragment());

    }

    public void replaceFragment(Fragment fragment){

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.place_order_button_order_now_fragment:placeOrder();
                    break;
                case R.id.quick_order_button_order_now_fragment:quickOrder();
                    break;
                case R.id.weekly_order_button_order_now_fragment:weeklyOrder();
                    break;
                case R.id.track_order_button_order_now_fragment:trackOrder();
                    break;
                case R.id.completed_order_button_order_now_fragment:completedOrder();
                    break;

            }

        }
    };
}
