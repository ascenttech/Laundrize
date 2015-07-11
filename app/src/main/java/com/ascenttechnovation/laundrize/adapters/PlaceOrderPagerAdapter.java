package com.ascenttechnovation.laundrize.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.fragments.AddressFragment;
import com.ascenttechnovation.laundrize.fragments.FAQFragment;
import com.ascenttechnovation.laundrize.fragments.LandingFragment;
import com.ascenttechnovation.laundrize.fragments.OrderNowFragment;
import com.ascenttechnovation.laundrize.fragments.ProfileFragment;

/**
 * Created by ADMIN on 09-07-2015.
 */
public class PlaceOrderPagerAdapter extends FragmentPagerAdapter {

    private String tabtitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    Context context;
    Drawable myDrawable;

    public PlaceOrderPagerAdapter(FragmentManager fm) {
        super(fm);

    }
    public PlaceOrderPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open FragmentTab1.java
            case 0:
                return new AddressFragment();

            // Open FragmentTab2.java
            case 1:
                return new FAQFragment();

            // Open FragmentTab3.java
            case 2:
                return new OrderNowFragment();
            case 3:
                return new ProfileFragment();
            case 4:
                return new FAQFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SpannableStringBuilder sb = new SpannableStringBuilder(" \nPage "+ position); // space added before text for convenience

        myDrawable = context.getResources().getDrawable(R.drawable.collection_icon);
        myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(myDrawable, ImageSpan.ALIGN_BASELINE);
        sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }
}
