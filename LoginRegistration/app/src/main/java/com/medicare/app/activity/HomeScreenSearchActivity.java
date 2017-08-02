
package com.medicare.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.medicare.app.adapters.MedicinesListAdapter;
import com.medicare.launch.app.R;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class HomeScreenSearchActivity extends BaseActivty implements OnItemClickListener,View.OnClickListener  {

    public static final String PREFS_NAME = "PingBusPrefs";
    public static final String PREFS_SEARCH_HISTORY = "SearchHistory";
    private SharedPreferences settings;
    private Set<String> history;
    Map<String, Integer> mapIndex;
    //ListView mLvMedicines;

    //@Bind(R.id.auto_search)
    AutoCompleteTextView mAutoTextView;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
   // @Bind(R.id.iv_selected_image)
    ImageView mSelectedImage;
    //@Bind(R.id.layoutCamera)
    RelativeLayout mCameraLayout;
   // @Bind(R.id.layoutGallery)
   RelativeLayout mGalleryLayout;
  //  @Bind(R.id.layoutPrescription)
  RelativeLayout mPrescriptionLayout;
    private static final int CAMERA_REQUEST = 1111;
    private static final int GALLERY_REQUEST = 2222;
    private ArrayAdapter<String> adapter;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
       // ButterKnife.bind(this);
            Intent intent = getIntent();
        setContentView(R.layout.activity_homescreen_search);
         final AutoCompleteTextView mAutoTextView = (AutoCompleteTextView) findViewById(R.id.auto_search);
mSelectedImage = (ImageView) findViewById(R.id.iv_selected_image);
        mCameraLayout=(RelativeLayout)findViewById(R.id.layoutCamera);
        mGalleryLayout=(RelativeLayout)findViewById(R.id.layoutGallery);
        mPrescriptionLayout=(RelativeLayout)findViewById(R.id.layoutPrescription);
        initListener();
        firebaseAuth =FirebaseAuth.getInstance();
        mAutoTextView.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    addSearchInput(mAutoTextView.getText().toString());
                    return true;
                }
                return false;
            }
        });

        mCameraLayout.setOnClickListener(this);
        mPrescriptionLayout.setOnClickListener( this);
        mGalleryLayout.setOnClickListener( this);
        setAutoCompleteSource();
        // Set the "Enter" event on the search input
navigation();

    }

    private void navigation() {


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                intent = new Intent(HomeScreenSearchActivity.this, SuccessActivity.class);
                                startActivity(intent);
//                                textFavorites.setVisibility(View.VISIBLE);
//                                textSchedules.setVisibility(View.GONE);
//                                textMusic.setVisibility(View.GONE);
                                break;
                            case R.id.action_search_medicine:
                                intent = new Intent(HomeScreenSearchActivity.this, UploadPrescriptionActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_add_location:
                                intent = new Intent(HomeScreenSearchActivity.this, UberMapActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_logout:
                                showToast("Logout");
                                firebaseAuth.signOut();
                                break;
                        }
                        return false;
                    }
                });
    }

    private void initListener() {


    }
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.layoutCamera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.layoutPrescription:
//TODO
                break;
            case R.id.layoutGallery:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST);
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            handleCameraResult(data);
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && null != data.getData()) {
            handleGallaryResult(data);
        }



       /* else if(){
            //TODO
        }*/
    }

    private void handleGallaryResult(Intent data) {
        Uri uri = data.getData();

            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mSelectedImage.setImageBitmap(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    private void handleCameraResult(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        mSelectedImage.setImageBitmap(photo);

    }
    private void setAutoCompleteSource() {
       // AutoCompleteTextView mAutoTextView  = (AutoCompleteTextView) findViewById(R.id.auto_search);
        /*//AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.textInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, history.toArray(new String[history.size()]));
        textView.setAdapter(adapter);*/
     //   ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, history.toArray(new String[history.size()]));
     //   mAutoTextView.setAdapter(adapter);
        LinkedList<String> mLinked = new LinkedList<String>();
        for (int i = 0; i < item.length; i++) {
            mLinked.add(item[i]);
        }
        Collections.sort(mLinked);
        MedicinesListAdapter medicinesListAdapter = new MedicinesListAdapter(this, mLinked);
        // mLvMedicines = (ListView) findViewById(R.id.lv_medicines);
        //mLvMedicines.setFastScrollEnabled(true);
        //  mLvMedicines.setAdapter(medicinesListAdapter);
        settings = getSharedPreferences(PREFS_NAME, 0);
        history = settings.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>());
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        mAutoTextView = (AutoCompleteTextView) findViewById(R.id.auto_search);
        mAutoTextView.setThreshold(1);
        mAutoTextView.setAdapter(adapter);
    }
    private void addSearchInput(String input)
    {
        if (!history.contains(input))
        {
            history.add(input);
            setAutoCompleteSource();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        savePrefs();
    }

    private void savePrefs()
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(PREFS_SEARCH_HISTORY, history);

        editor.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


}
