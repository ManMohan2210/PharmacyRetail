package com.medicare.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.medicare.app.adapters.DrawerItemCustomAdapter;
import com.medicare.app.models.DataModel;
import com.medicare.app.services.MyFirebaseInstanceIDService;
import com.medicare.launch.app.R;


/**
 * Created by satveer on 24-07-2017.
 */

public class DrawerMain extends BaseActivty implements View.OnClickListener{

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private FirebaseAuth firebaseAuth;
    TextView mTxtUserEmail;
    TextView mTxtToken;
    private BroadcastReceiver broadcastReceiver;
    Button mLogoutButton;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        DataModel[] drawerItem = new DataModel[4];

        drawerItem[0] = new DataModel(R.drawable.ic_home_black_24dp, "Home");
        drawerItem[1] = new DataModel(R.drawable.ic_add_location_black_24dp, "Add Location");
        drawerItem[2] = new DataModel(R.drawable.ic_search_black_24dp, "Search Medicine");
        drawerItem[3] = new DataModel(R.drawable.ic_exit_to_app_black_24dp, "Logout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow);
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();

        firebaseAuth =FirebaseAuth.getInstance();
        mTxtUserEmail = (TextView) findViewById(R.id.tv_user_email);
        mTxtToken = (TextView)findViewById(R.id.tv_token);

        broadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mTxtToken.setText(SharedPrefManager.getInstance(DrawerMain.this).getToken());

            }
        };

        if (SharedPrefManager.getInstance(this).getToken()!= null)
        {
            mTxtToken.setText(SharedPrefManager.getInstance(DrawerMain.this).getToken());
            Log.d("my firebaseToken",SharedPrefManager.getInstance(this).getToken());}
        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIDService.TOKEN_BROADCAST));
        if (firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this, LoginPageActivity.class));
        }
        FirebaseUser user =firebaseAuth.getCurrentUser();
        mTxtUserEmail.setText("Welcome  " +user.getEmail());
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(DrawerMain.this, LoginPageActivity.class));
                }
            }
        };

    }
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onClick(View v) {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {
       Fragment fragment = null;

        Intent intent;
        switch (position) {
            case 0:
                showToast("Home");
                 intent = new Intent(DrawerMain.this, SuccessActivity.class);
                startActivity(intent);
                break;
            case 1:
                showToast("Add Location");
                 intent = new Intent(DrawerMain.this, RetailerMapActivity.class);
                startActivity(intent);
                break;
            case 2:
                showToast("search medicine");
                intent = new Intent(DrawerMain.this, HomeScreenSearchActivity.class);
                startActivity(intent);
                break;
            case 3:
                showToast("Logout");
                firebaseAuth.signOut();
               /* Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            public void onResult(Status status) {
                            }});
                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {

                        LoginManager.getInstance().logOut();

                    }
                }).executeAsync();*/
                finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }
}