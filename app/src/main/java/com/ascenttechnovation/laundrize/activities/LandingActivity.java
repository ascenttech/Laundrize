package com.ascenttechnovation.laundrize.activities;

import android.content.Intent;
import android.content.res.Configuration;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.adapters.NavigationDrawerAdapter;
import com.ascenttechnovation.laundrize.data.NavigationDrawerData;
import com.ascenttechnovation.laundrize.fragments.AddressFragment;
import com.ascenttechnovation.laundrize.fragments.CompletedOrdersFragment;
import com.ascenttechnovation.laundrize.fragments.FAQFragment;
import com.ascenttechnovation.laundrize.fragments.PrivacyPolicyFragment;
import com.ascenttechnovation.laundrize.fragments.LandingFragment;
import com.ascenttechnovation.laundrize.fragments.ProfileFragment;
import com.ascenttechnovation.laundrize.fragments.TrackOrdersFragment;
import com.ascenttechnovation.laundrize.utils.Constants;

import java.util.ArrayList;


/**
 * Created by ADMIN on 29-06-2015.
 */
public class LandingActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;

    private ArrayList<NavigationDrawerData> navigationDrawerData;
    private NavigationDrawerAdapter navigationDrawerAdapter;

    LinearLayout sliderLayout;
    RelativeLayout profileLayout;
    ImageView profileImage;
    int icons[]={R.drawable.icon_checkbox,R.drawable.icon_checkbox,R.drawable.icon_checkbox,R.drawable.icon_checkbox,R.drawable.icon_watch,R.drawable.icon_shirt,R.drawable.drawer_logo_profile,R.drawable.drawer_logo_tnc,R.drawable.drawer_logo_privacy_policy,R.drawable.drawer_logo_faq,R.drawable.drawer_logo_contact_us};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Log.d(Constants.LOG_TAG,Constants.LandingActivity);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.navigation_drawer_items);

        // create bitmap from resource
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.profile);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        sliderLayout = (LinearLayout) findViewById(R.id.sliderLayout);
        profileLayout = (RelativeLayout) findViewById(R.id.profile_layout_navigation_drawer);
        profileImage = (ImageView) findViewById(R.id.profile_image_navigation_drawer);

        profileImage.setImageBitmap(getCircleBitmap(bitmap));

        navigationDrawerData = new ArrayList<NavigationDrawerData>();

        for(int i=0;i<navMenuTitles.length;i++){

            navigationDrawerData.add(new NavigationDrawerData(icons[i],navMenuTitles[i]));
        }

        // setting the nav drawer list adapter
        navigationDrawerAdapter = new NavigationDrawerAdapter(getApplicationContext(),
                navigationDrawerData);
        mDrawerList.setAdapter(navigationDrawerAdapter);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        profileLayout.setOnClickListener(listener);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
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

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ProfileFragment())
                    .commit();
            setTitle("Profile");
            mDrawerLayout.closeDrawer(sliderLayout);


        }
    };

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new LandingFragment();
                break;
            case 1:
                fragment = new AddressFragment("place");
                break;
            case 2:
                fragment = new AddressFragment("quick");
                break;
            case 3:
                fragment = new AddressFragment("weekly");
                break;
            case 4:
                fragment = new TrackOrdersFragment();
                break;
            case 5:
                fragment = new CompletedOrdersFragment();
                break;
            case 6:
                fragment = new ProfileFragment();
                break;
            case 7:
                fragment = new ProfileFragment();
                break;
            case 8:
                fragment = new PrivacyPolicyFragment();
                break;
            case 9:
                fragment = new FAQFragment();
                break;
            case 10:
                mDrawerLayout.closeDrawer(sliderLayout);
                mailToSupport();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(sliderLayout);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void mailToSupport(){

        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "support@laundrize.com" });
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
        i.putExtra(Intent.EXTRA_TEXT, "Email message");
        i.setType("plain/text");
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(sliderLayout);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        setTitle(navMenuTitles[position]);
        mDrawerLayout.closeDrawer(sliderLayout);
    }
}
