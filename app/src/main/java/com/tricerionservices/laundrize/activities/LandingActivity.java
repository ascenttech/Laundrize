package com.tricerionservices.laundrize.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.tricerionservices.laundrize.R;
import com.tricerionservices.laundrize.adapters.NavigationDrawerAdapter;
import com.tricerionservices.laundrize.custom.CustomTextView;
import com.tricerionservices.laundrize.data.NavigationDrawerData;
import com.tricerionservices.laundrize.fragments.AddressFragment;
import com.tricerionservices.laundrize.fragments.CompletedOrdersFragment;
import com.tricerionservices.laundrize.fragments.FAQFragment;
import com.tricerionservices.laundrize.fragments.PrivacyPolicyFragment;
import com.tricerionservices.laundrize.fragments.LandingFragment;
import com.tricerionservices.laundrize.fragments.ProfileFragment;
import com.tricerionservices.laundrize.fragments.TermAndConditionsFragment;
import com.tricerionservices.laundrize.fragments.TrackOrdersFragment;
import com.tricerionservices.laundrize.utils.Constants;

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
    int icons[]={R.drawable.drawer_icon_home,R.drawable.drawer_icon_checkbox,R.drawable.drawer_icon_checkbox,R.drawable.drawer_icon_checkbox,R.drawable.drawer_icon_watch,R.drawable.drawer_icon_shirt,R.drawable.drawer_icon_profile,R.drawable.drawer_icon_tnc,R.drawable.drawer_icon_privacy_policy,R.drawable.drawer_icon_faq,R.drawable.drawer_icon_contact_us,R.drawable.drawer_icon_logout};
    CustomTextView profileName;
    android.support.v7.app.AlertDialog.Builder builder;
    android.support.v7.app.AlertDialog alertDialog;


    int counter=0;

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
                R.drawable.profile_picture);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        sliderLayout = (LinearLayout) findViewById(R.id.sliderLayout);
        profileLayout = (RelativeLayout) findViewById(R.id.profile_layout_navigation_drawer);
        profileImage = (ImageView) findViewById(R.id.profile_image_navigation_drawer);
        profileName = (CustomTextView) findViewById(R.id.name_text_landing_activity);
        profileName.setText(Constants.profileName);


        profileImage.setImageBitmap(getCircleBitmap(bitmap));

        navigationDrawerData = new ArrayList<NavigationDrawerData>();

        for(int i=0;i<navMenuTitles.length;i++){

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            Bitmap b = BitmapFactory.decodeResource(getResources(),
                    icons[i],options);


//            navigationDrawerData.add(new NavigationDrawerData(icons[i],navMenuTitles[i]));
            navigationDrawerData.add(new NavigationDrawerData(b,navMenuTitles[i]));
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

        builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("This app requires internet connection");
        builder.setCancelable(false);
        builder.create();

        alertDialog = builder.create();

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean internetAvailable = Constants.isInternetAvailable(getApplicationContext());
        if(internetAvailable){

            if(alertDialog.isShowing()){
                alertDialog.dismiss();
            }
        }
        else{

            alertDialog.show();
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
                Bundle bundle = new Bundle();
                bundle.putString("orderType", "place");
                fragment = new AddressFragment();
                fragment.setArguments(bundle);
                break;
            case 2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("orderType", "quick");
                fragment = new AddressFragment();
                fragment.setArguments(bundle2);
                break;
            case 3:
                Bundle bundle3 = new Bundle();
                bundle3.putString("orderType", "weekly");
                fragment = new AddressFragment();
                fragment.setArguments(bundle3);
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
                fragment = new TermAndConditionsFragment();
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
            case 11:
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("loginRoute");
                editor.remove("profileId");
                editor.remove("userId");
                editor.remove("token");
                editor.remove("phoneNumber");
                editor.remove("password");
                editor.commit();

                Intent i = new Intent(LandingActivity.this,LogInOrRegisterActivity.class);
                startActivity(i);
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

//        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
//
//        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.badge).getActionView();
//        TextView tv = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
//        tv.setText("12");

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
//        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
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

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(fragment instanceof LandingFragment){
            counter++;
            if(counter %2 == 0){

                finish();
            }
            else{

                Toast.makeText(getApplicationContext()," Press back again to exit",5000).show();
            }

        }
        else{

            super.onBackPressed();
            counter = 0;
        }

        mDrawerLayout.closeDrawer(sliderLayout);

    }
}
