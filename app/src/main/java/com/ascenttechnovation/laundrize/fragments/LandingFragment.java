package com.ascenttechnovation.laundrize.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.async.FetchAllSlotsAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 08-07-2015.
 */
public class LandingFragment extends Fragment {

    private FrameLayout placeOrder,quickOrder,weeklyOrder,trackOrder,completedOrder;
    private CustomButton placeOrderBtn,quickOrderBtn,weeklyOrderBtn,trackOrderBtn,completedOrderBtn;
    private ActionBar actionBar;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_landing,container,false);

        Log.d(Constants.LOG_TAG, Constants.LandingFragment);

        customActionBar();
        findViews(v);
        setViews();
        setClickListeners();

        return v;
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Home");
    }




    private void findViews(View v){

        placeOrder = (FrameLayout) v.findViewById(R.id.place_order_layout_order_now_fragment);
        quickOrder = (FrameLayout) v.findViewById(R.id.quick_order_layout_order_now_fragment);
        weeklyOrder = (FrameLayout) v.findViewById(R.id.weekly_order_layout_order_now_fragment);
        trackOrder = (FrameLayout) v.findViewById(R.id.track_order_layout_order_now_fragment);
        completedOrder = (FrameLayout) v.findViewById(R.id.completed_order_layout_order_now_fragment);

    }

    private void setViews(){

        placeOrderBtn = (CustomButton) placeOrder.findViewById(R.id.styled_button_included);
        placeOrderBtn.setText("Place Order");

        quickOrderBtn = (CustomButton) quickOrder.findViewById(R.id.styled_button_included);
        quickOrderBtn.setText("Quick Order");


        weeklyOrderBtn = (CustomButton) weeklyOrder.findViewById(R.id.styled_button_included);
        weeklyOrderBtn.setText("Weekly Order");


        trackOrderBtn = (CustomButton) trackOrder.findViewById(R.id.styled_button_included);
        trackOrderBtn.setText("Track Order");
        trackOrderBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_watch,0,0,0);


        completedOrderBtn = (CustomButton) completedOrder.findViewById(R.id.styled_button_included);
        completedOrderBtn.setText("Completed Order");
        completedOrderBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_shirt,0,0,0);

    }

    private void setClickListeners(){

        placeOrder.setOnClickListener(listener);
        quickOrder.setOnClickListener(listener);
        weeklyOrder.setOnClickListener(listener);
        trackOrder.setOnClickListener(listener);
        completedOrder.setOnClickListener(listener);

    }


    public void placeOrder(){

        Bundle bundle = new Bundle();
        bundle.putString("orderType", "place");
        Fragment fragment = new AddressFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment);
    }

    public void quickOrder(){

        Bundle bundle = new Bundle();
        bundle.putString("orderType", "quick");
        Fragment fragment = new AddressFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment);

    }

    public void weeklyOrder(){

        Bundle bundle = new Bundle();
        bundle.putString("orderType", "weekly");
        Fragment fragment = new AddressFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment);

    }

    public void trackOrder(){

        replaceFragment(new TrackOrdersFragment());

    }
    public void completedOrder(){

        replaceFragment(new CompletedOrdersFragment());

    }

    public void replaceFragment(Fragment fragment){

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.place_order_layout_order_now_fragment: placeOrder();
                    break;
                case R.id.quick_order_layout_order_now_fragment:quickOrder();
                    break;
                case R.id.weekly_order_layout_order_now_fragment:weeklyOrder();
                    break;
                case R.id.track_order_layout_order_now_fragment:trackOrder();
                    break;
                case R.id.completed_order_layout_order_now_fragment:completedOrder();
                    break;

            }

        }
    };
}
