package com.tricerionservices.laundrize.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.tricerionservices.laundrize.fragments.BagLaundryFragment;
import com.tricerionservices.laundrize.fragments.DryCleanHouseholdsFragment;
import com.tricerionservices.laundrize.fragments.DryCleanWearablesFragment;
import com.tricerionservices.laundrize.fragments.IroningHouseholdsFragment;
import com.tricerionservices.laundrize.fragments.IroningWearablesFragment;
import com.tricerionservices.laundrize.fragments.OthersFragment;
import com.tricerionservices.laundrize.fragments.ShoeLaundryFragment;
import com.tricerionservices.laundrize.fragments.WashAndIronHouseholdsFragment;
import com.tricerionservices.laundrize.fragments.WashAndIronWearablesFragment;
import com.tricerionservices.laundrize.utils.Constants;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class TabsViewPagerAdapter extends FragmentStatePagerAdapter  {

    FragmentManager fm;
    Fragment fragment;

    public TabsViewPagerAdapter(FragmentManager fm) {

        super(fm);
        this.fm = fm;

        Log.d(Constants.LOG_TAG, Constants.TabsViewPagerAdapter);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0 : return new IroningWearablesFragment();
            case 1 : return new IroningHouseholdsFragment();
            case 2 : return new WashAndIronWearablesFragment();
            case 3 : return new WashAndIronHouseholdsFragment();
            case 4 : return new DryCleanWearablesFragment();
            case 5 : return new DryCleanHouseholdsFragment();
            case 6 : return new ShoeLaundryFragment();
            case 7 : return new BagLaundryFragment();
            case 8 : return new OthersFragment();
            default: return null;
        }


    }

    @Override
    public int getCount() {
        return 9;
    }
}
