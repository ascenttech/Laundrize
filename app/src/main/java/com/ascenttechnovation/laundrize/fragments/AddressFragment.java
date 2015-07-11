package com.ascenttechnovation.laundrize.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 08-07-2015.
 */
public class AddressFragment extends Fragment {

    LinearLayout child,parent;
    int height;
    ActionBar actionBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_address,null);

        findViews(v);
        setViews();

        height = child.getHeight();

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
        //set Visible
        child.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        child.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, child.getMeasuredHeight());
        mAnimator.start();
    }
    public void collapse(){

        int finalHeight = child.getHeight();
        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);
//        mAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                //Height=0, but it set visibility to GONE
//                child.setVisibility(View.GONE);
//            }
//        });
        mAnimator.start();

    }

    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
                layoutParams.height = value;
                child.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(child.getVisibility()==View.INVISIBLE){

                expand();
            }
            else{

                collapse();
            }

        }
    };
}
