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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 02-07-2015.
 */
public class PrivacyPolicyFragment extends Fragment {

    private ActionBar actionBar;
    private TextView firstQuestion,firstAnswer,secondQuestion,secondAnswer,thirdQuestion,thirdAnswer,fourthQuestion,fourthAnswer,fifthQuestion,fifthAnswer,sixthQuestion,sixthAnswer,seventhQuestion,seventhAnswer;
    private LinearLayout firstLayout,secondLayout,thirdLayout,fourthLayout,fifthLayout,sixthLayout,seventhLayout;
    private String questions[],answers[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_privacy_policy,container,false);


        customActionBar();
        questions = getActivity().getApplicationContext().getResources().getStringArray(R.array.privacy_policy_questions);
        answers = getActivity().getApplicationContext().getResources().getStringArray(R.array.privacy_policy_answers);

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

        firstLayout = (LinearLayout) v.findViewById(R.id.first_question_static_faq_fragment);
        firstQuestion = (TextView) firstLayout.findViewById(R.id.question_text_faq_fragment);
        firstAnswer = (TextView) firstLayout.findViewById(R.id.answer_text_faq_fragment);

        secondLayout = (LinearLayout) v.findViewById(R.id.second_question_static_faq_fragment);
        secondQuestion = (TextView) secondLayout.findViewById(R.id.question_text_faq_fragment);
        secondAnswer = (TextView) secondLayout.findViewById(R.id.answer_text_faq_fragment);

        thirdLayout = (LinearLayout) v.findViewById(R.id.third_question_static_faq_fragment);
        thirdQuestion = (TextView) thirdLayout.findViewById(R.id.question_text_faq_fragment);
        thirdAnswer = (TextView) thirdLayout.findViewById(R.id.answer_text_faq_fragment);

        fourthLayout = (LinearLayout) v.findViewById(R.id.fourth_question_static_faq_fragment);
        fourthQuestion = (TextView) fourthLayout.findViewById(R.id.question_text_faq_fragment);
        fourthAnswer = (TextView) fourthLayout.findViewById(R.id.answer_text_faq_fragment);

        fifthLayout = (LinearLayout) v.findViewById(R.id.fifth_question_static_faq_fragment);
        fifthQuestion = (TextView) fifthLayout.findViewById(R.id.question_text_faq_fragment);
        fifthAnswer = (TextView) fifthLayout.findViewById(R.id.answer_text_faq_fragment);

        sixthLayout = (LinearLayout) v.findViewById(R.id.sixth_question_static_faq_fragment);
        sixthQuestion = (TextView) sixthLayout.findViewById(R.id.question_text_faq_fragment);
        sixthAnswer = (TextView) sixthLayout.findViewById(R.id.answer_text_faq_fragment);

        seventhLayout = (LinearLayout) v.findViewById(R.id.seventh_question_static_faq_fragment);
        seventhQuestion = (TextView) seventhLayout.findViewById(R.id.question_text_faq_fragment);
        seventhAnswer = (TextView) seventhLayout.findViewById(R.id.answer_text_faq_fragment);


    }

    public void setViews(){

        firstQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
        firstQuestion.setText(questions[0]);
        firstAnswer.setText(answers[0]);
        firstQuestion.setTag("question_1");
        firstQuestion.setOnClickListener(listener);

        secondQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
        secondQuestion.setText(questions[1]);
        secondAnswer.setText(answers[1]);
        secondQuestion.setTag("question_2");
        secondQuestion.setOnClickListener(listener);

        thirdQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
        thirdQuestion.setText(questions[2]);
        thirdAnswer.setText(answers[2]);
        thirdQuestion.setTag("question_3");
        thirdQuestion.setOnClickListener(listener);

        fourthQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
        fourthQuestion.setText(questions[3]);
        fourthAnswer.setText(answers[3]);
        fourthQuestion.setTag("question_4");
        fourthQuestion.setOnClickListener(listener);

        fifthQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
        fifthQuestion.setText(questions[4]);
        fifthAnswer.setText(answers[4]);
        fifthQuestion.setTag("question_5");
        fifthQuestion.setOnClickListener(listener);

        sixthQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
        sixthQuestion.setText(questions[5]);
        sixthAnswer.setText(answers[5]);
        sixthQuestion.setTag("question_6");
        sixthQuestion.setOnClickListener(listener);

        seventhQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
        seventhQuestion.setText(questions[6]);
        seventhAnswer.setText(answers[6]);
        seventhQuestion.setTag("question_7");
        seventhQuestion.setOnClickListener(listener);

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

            String tag = view.getTag().toString();
            String pos[] = tag.split("_");
            int position = Integer.parseInt(pos[1]);

            switch (position){

                case 1:
                    if(firstAnswer.getVisibility()==View.GONE){

                        firstQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_minus,0);
                        expand(firstAnswer);

                    }
                    else{

                        firstQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
                        collapse(firstAnswer);
                    }
                    break;

                case 2:
                    if(secondAnswer.getVisibility()==View.GONE){

                        secondQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_minus,0);
                        expand(secondAnswer);
                    }
                    else{

                        secondQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
                        collapse(secondAnswer);
                    }
                    break;
                case 3:
                    if(thirdAnswer.getVisibility()==View.GONE){

                        thirdQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_minus,0);
                        expand(thirdAnswer);
                    }
                    else{
                            thirdQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
                            collapse(thirdAnswer);
                    }
                    break;
                case 4:
                    if(fourthAnswer.getVisibility()==View.GONE){

                        fourthQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_minus,0);
                        expand(fourthAnswer);
                    }
                    else{

                        fourthQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
                        collapse(fourthAnswer);
                    }
                    break;
                case 5:
                    if(fifthAnswer.getVisibility()==View.GONE){

                        fifthQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_minus,0);
                        expand(fifthAnswer);
                    }
                    else{

                        fifthQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
                        collapse(fifthAnswer);
                    }
                    break;
                case 6:
                    if(sixthAnswer.getVisibility()==View.GONE){

                        sixthQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_minus,0);
                        expand(sixthAnswer);
                    }
                    else{

                        sixthQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
                        collapse(sixthAnswer);
                    }
                    break;
                case 7:
                    if(seventhAnswer.getVisibility()==View.GONE){

                        seventhQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_minus,0);
                        expand(seventhAnswer);
                    }
                    else{
                        seventhQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_plus,0);
                        collapse(seventhAnswer);
                    }
                    break;

            }

        }
    };

}
