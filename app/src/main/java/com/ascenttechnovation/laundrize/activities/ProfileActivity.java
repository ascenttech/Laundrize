package com.ascenttechnovation.laundrize.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.ascenttechnovation.laundrize.R;

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
        imageView = (ImageView) findViewById(R.id.img);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
        roundedImage = new RoundedImage(bm);
        imageView.setImageDrawable(roundedImage);

    }
}
