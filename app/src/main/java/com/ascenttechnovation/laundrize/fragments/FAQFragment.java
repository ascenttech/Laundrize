package com.ascenttechnovation.laundrize.fragments;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class FAQFragment extends Fragment {

    private ActionBar actionBar;
    private TextView firstQuestion,firstAnswer,secondQuestion,secondAnswer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_faq, null);


        customActionBar();
        findViews(v);
        setViews();

        return v;
    }

    public void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

    }

    private void findViews(View v){

        firstQuestion = (TextView) v.findViewById(R.id.how_can_i_start_question_text_faq_fragment);
        firstAnswer = (TextView) v.findViewById(R.id.how_can_i_start_answer_text_faq_fragment);
        secondQuestion = (TextView) v.findViewById(R.id.how_do_i_get_delivered_question_text_faq_fragment);
        secondAnswer = (TextView) v.findViewById(R.id.how_do_i_get_delivered_answer_text_faq_fragment);
    }

    public void setViews(){

        firstQuestion.setOnClickListener(listener);
        secondQuestion.setOnClickListener(listener);

    }

    public void expand(View v){

        Log.d(Constants.LOG_TAG, " EXPANDED ");
        //set Visible
        v.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, v.getMeasuredHeight(),v);
        mAnimator.start();
    }
    public void collapse(final View v){

        Log.d(Constants.LOG_TAG," COLLAPSE ");
        int finalHeight = v.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0,v);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnimator.start();

    }

    private ValueAnimator slideAnimator(int start, int end, final View v) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.how_can_i_start_question_text_faq_fragment:
                    if(firstAnswer.getVisibility()==View.GONE){

                        expand(firstAnswer);
                    }
                    else{

                        collapse(firstAnswer);
                    }
                    break;

                case R.id.how_do_i_get_delivered_question_text_faq_fragment:
                    if(secondAnswer.getVisibility()==View.GONE){

                        expand(secondAnswer);
                    }
                    else{

                        collapse(secondAnswer);
                    }
                    break;

            }

        }
    };

}
