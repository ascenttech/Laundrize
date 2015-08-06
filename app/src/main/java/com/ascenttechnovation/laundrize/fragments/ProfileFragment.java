package com.ascenttechnovation.laundrize.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.activities.LandingActivity;
import com.ascenttechnovation.laundrize.async.FetchUserProfileAsyncTask;
import com.ascenttechnovation.laundrize.custom.CustomButton;
import com.ascenttechnovation.laundrize.custom.CustomTextView;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 01-07-2015.
 */
public class ProfileFragment extends Fragment {

    private ActionBar actionBar;
    private ImageView profileImage;
    private Bitmap bitmap;
    private CustomTextView editYourProfile,address,mobileNumber,userName;
    private Dialog dialog;
    private CustomButton update,cancel,addNewAddress;
    private LinearLayout footer;
    private ViewGroup availableAddresses;
    private ProgressDialog progressDialog;
    private View v;
    private boolean inflateAddress;
    private LayoutInflater parentInflater;
    private View rowView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG,Constants.ProfileFragment);

        v = inflater.inflate(R.layout.fragment_profile,container,false);
        parentInflater = inflater;
        // create bitmap from resource
        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.profile);

        fetchData();

        return v;
    }

    public void fetchData(){



        String finalUrl = Constants.fetchProfileUrl + Constants.userId;
        new FetchUserProfileAsyncTask(getActivity().getApplicationContext(),new FetchUserProfileAsyncTask.FetchUserProfileCallback() {
            @Override
            public void onStart(boolean status) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(Constants.LOG_TAG);
                progressDialog.setMessage("Getting Your Profile");
                progressDialog.show();

            }
            @Override
            public void onResult(boolean result) {

                progressDialog.dismiss();
                if(result){
                    customActionBar();
                    findViews(v);
                    setViews();
                    displayAddress();

                }
                else{

                    Toast.makeText(getActivity().getApplicationContext(),"Couldn't Fetch Your Profile.\nTry Again Later",5000).show();

                }

            }
        }).execute(finalUrl);


    }

    private void customActionBar(){

        actionBar = ((LandingActivity)getActivity()).getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Profile");

    }

    private void findViews(View v){

        userName = (CustomTextView) v.findViewById(R.id.user_name_text_profile_fragment);
        profileImage = (ImageView) v.findViewById(R.id.profile_image_profile_fragment);
        editYourProfile = (CustomTextView) v.findViewById(R.id.edit_profile_text_profile_fragment);
        footer = (LinearLayout) v.findViewById(R.id.included_buttons_profile_fragment);
        update = (CustomButton) footer.findViewById(R.id.left_button_included);
        cancel = (CustomButton) footer.findViewById(R.id.right_button_included);
        addNewAddress = (CustomButton) v.findViewById(R.id.add_new_address_button_profile_fragment);
        availableAddresses = (ViewGroup) v.findViewById(R.id.available_address_container_profile_fragment);
    }

    private void setViews(){

        userName.setText(Constants.profileData.get(0).getFirstName());

        profileImage.setImageBitmap(getCircleBitmap(bitmap));
        editYourProfile.setOnClickListener(listener);

        update.setText("Update");
        update.setOnClickListener(listener);

        cancel.setText("Cancel");
        cancel.setOnClickListener(listener);

        addNewAddress.setOnClickListener(listener);
    }

    public void displayAddress(){


        if(Constants.addressFetched) {
            for(int i = 0;i<Constants.addressData.size();i++) {
                rowView = parentInflater.inflate(R.layout.include_available_address, null);

                address = (CustomTextView) rowView.findViewById(R.id.address_text_included);
                address.setText(Constants.addressData.get(i).getFullAddress());

                mobileNumber = (CustomTextView) rowView.findViewById(R.id.mobile_number_text_included);
                mobileNumber.setText(Constants.addressData.get(i).getMobileNumber());

                availableAddresses.addView(rowView);
            }

        } // end of IF statement

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
                .replace(R.id.container,new LandingFragment())
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
                case R.id.add_new_address_button_profile_fragment : editYourProfile();
                    break;
            }

        }
    };
}
