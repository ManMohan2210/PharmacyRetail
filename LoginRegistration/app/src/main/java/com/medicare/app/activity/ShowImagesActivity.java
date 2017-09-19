package com.medicare.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicare.app.Util.UtilityUtil;
import com.medicare.app.adapters.ImageAdaptor;
import com.medicare.app.models.UploadImage;
import com.medicare.launch.app.R;

import java.util.ArrayList;
import java.util.List;

public class ShowImagesActivity extends BaseActivty {
    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabase;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<UploadImage> uploads;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userId = firebaseAuth.getCurrentUser().getUid();
    StringBuffer sb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        uploads = getImages();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isImageChecked()) {
                    Intent intent = new Intent(ShowImagesActivity.this, HomeScreenSearchActivity.class);
                    startActivity(intent);


                } else {
                    showToast("Please select image.");
                }

            }
        });
        getImages();
    }

    private boolean isImageChecked() {
        if (!UtilityUtil.isCollectionNullOrEmpty(uploads)) {
            for (UploadImage uploadImage : uploads) {
                if (uploadImage.isChecked()) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<UploadImage> getImages() {
        final ArrayList<UploadImage> imageList = new ArrayList<>();


//adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.child("users").child(userId).child("imageUrl").getChildren()) {
                    UploadImage upload = postSnapshot.getValue(UploadImage.class);
                    //  uploads.add(upload);
                    imageList.add(upload);
                }
                progressDialog.dismiss();
                uploads =  imageList;
                //creating adapter
                adapter = new ImageAdaptor(getApplicationContext(), (ArrayList<UploadImage>) imageList);

                //adding adapter to recyclerview
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        return imageList;
    }
}