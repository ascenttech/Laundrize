package com.ascenttechnovation.laundrize.fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class ProfileFragment extends Fragment {

    private ActionBar actionBar;
    private ImageView profileImage;
    private Bitmap bitmap;
    private TextView editYourProfile;
    private Dialog dialog;
    private Button update,cancel;
    private LinearLayout footer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile,null);

        // create bitmap from resource
        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.profile);


        customActionBar();
        findViews(v);
        setViews();


        return v;
    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

    }

    private void findViews(View v){

        profileImage = (ImageView) v.findViewById(R.id.profile_image_profile_fragment);
        editYourProfile = (TextView) v.findViewById(R.id.edit_profile_text_profile_fragment);
        footer = (LinearLayout) v.findViewById(R.id.included_buttons_profile_fragment);
        update = (Button) footer.findViewById(R.id.left_button_included);
        cancel = (Button) footer.findViewById(R.id.right_button_included);
    }

    private void setViews(){

        profileImage.setImageBitmap(getCircleBitmap(bitmap));
        editYourProfile.setOnClickListener(listener);

        update.setText("Update");
        update.setOnClickListener(listener);

        cancel.setText("Cancel");
        cancel.setOnClickListener(listener);
    }

    // This will generate a pop up
    // Since the pop up is totally new screen
    // we wont find its id in the findViews
    public void editYourProfile(){

        // custom dialog
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Update Profile");

        Button dialogButton = (Button) dialog.findViewById(R.id.update_button_custom_dialog);
        EditText dateOfBirth = (EditText) dialog.findViewById(R.id.date_of_birth_edit_custom_dialog);
//        dateOfBirth.setOnClickListener();


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public Bitmap getCircleBitmap(Bitmap bitmap){


        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;

    }

    public void cancel(){

        ((LandingActivity)getActivity())
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new OrderNowFragment())
                .commit();

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.edit_profile_text_profile_fragment: editYourProfile();
                    break;
                case R.id.left_button_included: cancel();
                    break;
                case R.id.right_button_included: cancel();
                    break;

            }

        }
    };
}
