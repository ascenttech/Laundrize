package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.utils.Constants;

/**
 * Created by ADMIN on 30-06-2015.
 */
public class ProfileActivity extends Activity {

    ImageView imageView;
    RoundedImage roundedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d(Constants.LOG_TAG,Constants.ProfileActivity);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
        roundedImage = new RoundedImage(bm);

        findViews();
        setViews();

    }

    private void findViews(){

        imageView = (ImageView) findViewById(R.id.img);
    }
    private void setViews(){

        imageView.setImageDrawable(roundedImage);
    }

}
