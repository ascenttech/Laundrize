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
public class TabsViewPagerAdapter extends FragmentPagerAdapter {

    FragmentManager fm;

    public TabsViewPagerAdapter(FragmentManager fm) {

        super(fm);
        this.fm = fm;

        Log.d(Constants.LOG_TAG, Constants.TabsViewPagerAdapter);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        switch (position){

            case 0 : return new IroningWearablesFragment();

            case 1 : return new IroningHouseholdsFragment();

            case 2 : return new WashAndIronWearablesFragment();

            case 3 : return new WashAndIronHouseholdsFragment();

            case 4 : return new DryCleanWearablesFragment();

            case 5 : return new DryCleanHouseholdsFragment();

        }

        return null;

    }

    @Override
    public int getCount() {
        return 6;
    }
}
