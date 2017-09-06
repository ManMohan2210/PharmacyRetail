
package com.medicare.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medicare.app.adapters.MedicinesListAdapter;
import com.medicare.app.models.UploadImage;
import com.medicare.launch.app.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
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
    private UserTypeModel user ;
    //@Bind(R.id.auto_search)
    AutoCompleteTextView mAutoTextView;
    private DatabaseReference mDatabase;

    private FirebaseAuth.AuthStateListener mAuthListener;
   // @Bind(R.id.iv_selected_image)
    ImageView mSelectedImage;
    //@Bind(R.id.layoutCamera)
    RelativeLayout mCameraLayout;
   // @Bind(R.id.layoutGallery)
   RelativeLayout mGalleryLayout;
  //  @Bind(R.id.layoutPrescription)
  RelativeLayout mPrescriptionLayout;
    Button mBtnContinue;
    private static final int CAMERA_REQUEST = 1111;
    private static final int GALLERY_REQUEST = 2222;
    private ArrayAdapter<String> adapter;
    Firebase ref = new Firebase("https://medicare-b5a7b.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://medicare-b5a7b.appspot.com/medicine images");
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    Uri uri;
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
        mBtnContinue=(Button)findViewById(R.id.btn_continue);
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
        mBtnContinue.setOnClickListener(this);
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
                                intent = new Intent(HomeScreenSearchActivity.this, RetailerMapActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_logout:
                                showToast("Logout");
                                firebaseAuth.signOut();
                                intent = new Intent(HomeScreenSearchActivity.this, LoginPageActivity.class);
                                startActivity(intent);
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
                startActivity(new Intent(this, ShowImagesActivity.class));
                break;
            case R.id.layoutGallery:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST);
                break;
            case R.id.btn_continue:
                Intent continu = new Intent(HomeScreenSearchActivity.this, ShapeRibbleActivity.class);
                startActivity(continu);
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
         uri = data.getData();

            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mSelectedImage.setImageBitmap(photo);
                saveImageToFirebase(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }


    }



    private void handleCameraResult(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        mSelectedImage.setImageBitmap(photo);
        saveImageToFirebase(photo);
    }
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public void saveImageToFirebase(Bitmap bitmap) {
       final String userId = firebaseAuth.getCurrentUser().getUid();
        if (uri != null) {
        final long timestamp = new Date().getTime();
        final String uploadId = ref.push().getKey();
        StorageReference ImagesRef = storageRef.child("images/" +uploadId  + timestamp +".jpg" );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = ImagesRef.putBytes(data);
        //displaying progress dialog while image is uploading
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                showToast("Oops ! something went wrong, please try again.");
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("downloadUrl-->", "" + downloadUrl);

                //creating the upload object to store uploaded image details
                UploadImage upload = new UploadImage(uploadId,downloadUrl.toString(),timestamp);

                //adding an upload to firebase database
               // String uploadId = mDatabase.push().getKey();
                ref.child("users").child(userId).child("imageUrl").child(uploadId).setValue(upload);

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //displaying the upload progress
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                progressDialog.dismiss();
            }

        });}
        else{
            //TODO
        }
}

    private void setAutoCompleteSource() {
        LinkedList<String> mLinked = new LinkedList<String>();
        for (int i = 0; i < item.length; i++) {
            mLinked.add(item[i]);
        }
        Collections.sort(mLinked);
        MedicinesListAdapter medicinesListAdapter = new MedicinesListAdapter(this, mLinked);
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
