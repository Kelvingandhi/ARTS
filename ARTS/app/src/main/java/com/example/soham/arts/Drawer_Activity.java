package com.example.soham.arts;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
        import android.os.Handler;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.NavigationView;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;

        import com.example.soham.arts.R;
import com.example.soham.fragment.Add_money;
import com.example.soham.fragment.HomeFragment;
import com.example.soham.fragment.Invite_Friend;
import com.example.soham.fragment.Invite_Friends;
import com.example.soham.fragment.Offer;
import com.example.soham.fragment.Profile;
        import com.example.soham.fragment.NotificationsFragment;

        import com.example.soham.fragment.SettingsFragment;
import com.example.soham.googlemapdemo.MapsActivity;
import com.example.soham.other.CircleTransform;
import com.google.android.gcm.GCMRegistrar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Drawer_Activity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;



    // urls to load navigation header background image
    // and profile image
    //private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "Wallet";
    private static final String TAG_INVITE = "Invite Friend";
    private static final String TAG_MOVIES = "Profile";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;




    public static String email;
    public static String regId;
    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = Login.URL;


    public static String METHOD_NAME = "fetch_name";
    public static String SOAP_ACTION = "http://tempuri.org/fetch_name";

    public static String METHOD_NAME0 = "getnotification";
    public static String SOAP_ACTION0 = "http://tempuri.org/getnotification";

    public static String METHOD_NAME1 = "Delete_pushid";
    public static String SOAP_ACTION1 = "http://tempuri.org/Delete_pushid";

    public static String resulturl;

    //public  static String name ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  name = Login.e11.getText().toString();
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        //imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        final Activity activity = this;
        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();

            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        callapi1();
    }

    private void callapi1() {

        GCMRegistrar.checkDevice(Drawer_Activity.this);
        GCMRegistrar.checkManifest(Drawer_Activity.this);
        regId = GCMRegistrar.getRegistrationId(Drawer_Activity.this);
        if (regId.equals("")) {
            GCMRegistrar.register(Drawer_Activity.this, "590542472124");
            Log.v("Push", regId + "hi");


        } else {

            Log.v("Push", regId);

            new getnoti().execute();

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(this,"You cancelled the Scanning",Toast.LENGTH_LONG).show();
            }
            else
            {

                // Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
               /* String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                */

                resulturl = "";
                resulturl = result.getContents();
                Toast.makeText(this,resulturl.toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("qr_bus_no",resulturl);
                startActivity(intent);

                /*Uri uri = Uri.parse(resulturl);

                ////Check if scanned result is valid URL or not...........
                //////if not then simply Toast it
                //if(URLUtil.isValidUrl(String.valueOf(uri)) ){
                if(Patterns.WEB_URL.matcher(String.valueOf(uri)).matches()) {


                    //Intent intent = new Intent(QR_Scanner.this,after_scan.class);
                    //startActivity(intent);

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                }*/
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website

        SharedPreferences applicationpreferences =  getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE);

        email = applicationpreferences.getString("user_email","");
        //new fetch_name();
        //txtName.setText(Login.e11.toString());
        txtName.setText(email.toString());
        txtWebsite.setText("A.R.T.S.");

        // loading header background image
       /* Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
                */

        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos

                Add_money add_money = new Add_money();
                return add_money;

            case 2:
                // offer

                Invite_Friend invite_friend  = new Invite_Friend();
                return invite_friend;


            case 3:
                // movies fragment
                Profile profile = new Profile();
                return profile;
            case 4:
                // notifications fragment
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;


            default:
                return new HomeFragment();
                //HomeFragment homeFragmnt = new HomeFragment();
                //return homeFragmnt;
                //return new loadHomeFragment();
                //return  homeFragmnt;
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                      // new HomeFragment();
                       //break;
                        //return true;
                    case R.id.nav_photos:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PHOTOS;
                        break;
                    case R.id.nav_invite:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_INVITE;
                        break;
                    case R.id.nav_movies:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_MOVIES;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        break;

                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(Drawer_Activity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.FAQs:
                        // launch new intent instead of loading fragment

                        startActivity(new Intent(Drawer_Activity.this, FAQs.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.Feedback:
                        // launch new intent instead of loading fragment

                        startActivity(new Intent(Drawer_Activity.this, Feedback.class));
                        drawer.closeDrawers();
                        return true;

                    default:
                        //startActivity(new Intent(Drawer_Activity.this, HomeFragment.class));
                        //drawer.closeDrawers();
                        //break;
                        //navItemIndex = 0;
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        if (navItemIndex == 0)
        {
            moveTaskToBack(true);
        }

            //super.onBackPressed();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            SharedPreferences.Editor editor = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE).edit();
            editor.clear();

            editor.putBoolean("flag", false);
            editor.commit();
            finish();

            new delete_push_id().execute();

            Intent i1 = new Intent(Drawer_Activity.this, Login.class);
            startActivity(i1);
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();



        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }



    public class getnoti extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Drawer_Activity.this);
            dialog.setTitle("Loading  ..");
            dialog.setMessage("Please Wait");
            dialog.setIcon(R.drawable.ic_launcher);

            dialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub


            try {
                SharedPreferences applicationpreferences = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);

                email = applicationpreferences.getString("user_email","");

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME0);

                request.addProperty("pushid", regId);
                request.addProperty("email_id", email);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION0, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "MEssage : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return "fail";

            }


        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            dialog.dismiss();


            Toast.makeText(Drawer_Activity.this, result, Toast.LENGTH_LONG).show();


        }

    }






    public class delete_push_id extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Drawer_Activity.this);
            dialog.setTitle("Loading  ..");
            dialog.setMessage("Please Wait");
            dialog.setIcon(R.drawable.ic_launcher);

            dialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub


            try {
                SharedPreferences applicationpreferences =  getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);

                String email = applicationpreferences.getString("user_email","");
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);

                request.addProperty("pushid", regId);
                request.addProperty("email_id", email);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "MEssage : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return "fail";

            }


        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            dialog.dismiss();


            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


        }

    }



}

