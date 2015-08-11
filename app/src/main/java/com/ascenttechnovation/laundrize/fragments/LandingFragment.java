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
        getSlots();

        return v;
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Home");
    }


    public void getSlots(){

        String finalUrl = Constants.getslotsUrl+Constants.userId;
        new FetchAllSlotsAsyncTask(new FetchAllSlotsAsyncTask.FetchAllSlotsCallback() {
            @Override
            public void onStart(boolean status) {


            }
            @Override
            public void onResult(boolean result) {

                if(result){

                    Constants.slotsFetched = true;
                    setClickListeners();

                }
                else{

                    Toast.makeText(getActivity().getApplicationContext(), "Unable to connect to the internet.\nTry again Later", 5000).show();
                }

            }
        }).execute(finalUrl);

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
        placeOrderBtn.setText("PLACE ORDER");

        quickOrderBtn = (CustomButton) quickOrder.findViewById(R.id.styled_button_included);
        quickOrderBtn.setText("QUICK ORDER");


        weeklyOrderBtn = (CustomButton) weeklyOrder.findViewById(R.id.styled_button_included);
        weeklyOrderBtn.setText("WEEKLY ORDER");


        trackOrderBtn = (CustomButton) trackOrder.findViewById(R.id.styled_button_included);
        trackOrderBtn.setText("TRACK ORDER");
        trackOrderBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_watch,0,0,0);


        completedOrderBtn = (CustomButton) completedOrder.findViewById(R.id.styled_button_included);
        completedOrderBtn.setText("COMPLETED ORDER");
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

        replaceFragment(new AddressFragment("place"));
    }

    public void quickOrder(){

        replaceFragment(new AddressFragment("quick"));

    }

    public void weeklyOrder(){

        replaceFragment(new AddressFragment("weekly"));

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
