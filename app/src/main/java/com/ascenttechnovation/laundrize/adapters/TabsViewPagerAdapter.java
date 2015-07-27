package com.ascenttechnovation.laundrize.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.ascenttechnovation.laundrize.fragments.DryCleanHouseholdsFragment;
import com.ascenttechnovation.laundrize.fragments.DryCleanWearablesFragment;
import com.ascenttechnovation.laundrize.fragments.IroningHouseholdsFragment;
import com.ascenttechnovation.laundrize.fragments.IroningWearablesFragment;
import com.ascenttechnovation.laundrize.fragments.WashAndIronHouseholdsFragment;
import com.ascenttechnovation.laundrize.fragments.WashAndIronWearablesFragment;

/**
 * Created by ADMIN on 27-07-2015.
 */
public class TabsViewPagerAdapter extends FragmentStatePagerAdapter {

    public TabsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0 : return new IroningHouseholdsFragment();

            case 1 : return new IroningWearablesFragment();

            case 2 : return new DryCleanHouseholdsFragment();

            case 3 : return new DryCleanWearablesFragment();

            case 4 : return new WashAndIronHouseholdsFragment();

            case 5 : return new WashAndIronWearablesFragment();

            default:return new IroningHouseholdsFragment();

        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}
