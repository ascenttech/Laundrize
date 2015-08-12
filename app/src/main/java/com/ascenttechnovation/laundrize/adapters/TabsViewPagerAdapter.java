package com.ascenttechnovation.laundrize.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.fragments.DryCleanHouseholdsFragment;
import com.ascenttechnovation.laundrize.fragments.DryCleanWearablesFragment;
import com.ascenttechnovation.laundrize.fragments.IroningHouseholdsFragment;
import com.ascenttechnovation.laundrize.fragments.IroningWearablesFragment;
import com.ascenttechnovation.laundrize.fragments.WashAndIronHouseholdsFragment;
import com.ascenttechnovation.laundrize.fragments.WashAndIronWearablesFragment;
import com.ascenttechnovation.laundrize.utils.Constants;

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

            case 0 : fragment = new IroningWearablesFragment();
                break;
            case 1 : fragment = new IroningHouseholdsFragment();
                break;
            case 2 : fragment = new WashAndIronWearablesFragment();
                break;
            case 3 : fragment = new WashAndIronHouseholdsFragment();
                break;
            case 4 : fragment = new DryCleanWearablesFragment();
                break;
            case 5 : fragment = new DryCleanHouseholdsFragment();
                break;
        }

        return fragment;

    }

    @Override
    public int getCount() {
        return 6;
    }
}
