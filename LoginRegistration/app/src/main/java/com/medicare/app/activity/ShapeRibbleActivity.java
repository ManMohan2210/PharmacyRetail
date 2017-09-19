package com.medicare.app.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medicare.launch.app.R;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShapeRibbleActivity extends BaseActivty  {


    private ImageView foundDevice;
    private double latitude;
    private double longitude;
    private LocationManager locationManager;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseMediCare;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_ribble);
        databaseMediCare = FirebaseDatabase.getInstance().getReference();
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);

        final Handler handler=new Handler();

        foundDevice=(ImageView)findViewById(R.id.foundDevice);
        ImageView button=(ImageView)findViewById(R.id.centerImage);
        rippleBackground.startRippleAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                foundDevice();
            }
        },3000);
    }

    void getCurrentLocation(Location mLocation) {
        latitude = mLocation.getLatitude();
        longitude = mLocation.getLongitude();
        LatLng latLong = new
                LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }
 private void searchRetailer(){

     double radius = 100; // in km
     final Set<String> runnersNearby = new HashSet<String>();
     databaseMediCare = FirebaseDatabase.getInstance().getReference();
     mAuth = FirebaseAuth.getInstance();
     databaseMediCare.child("available").child(mAuth.getCurrentUser().getUid()).child("latitude").setValue(latitude);
     databaseMediCare.child("available").child(mAuth.getCurrentUser().getUid()).child("longitude").setValue(longitude);
     databaseMediCare.child("available").child(mAuth.getCurrentUser().getUid()).child("availability").setValue(true);
     DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("userLocation");
     GeoFire geoFire = new GeoFire(ref);
     geoFire.setLocation(mAuth.getCurrentUser().getUid(), new GeoLocation(latitude, longitude));
     GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latitude, longitude), 100);
     geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
         @Override
         public void onKeyEntered(String key, GeoLocation location) {
              showToast("Successfully Found");
             runnersNearby.add(key);
             Log.i("geofire: Number of users", String.valueOf(runnersNearby.size()));

         }

         @Override
         public void onKeyExited(String key) {
             Log.i("geofire", "left the place");
             runnersNearby.remove(key);
         }

         @Override
         public void onKeyMoved(String key, GeoLocation location) {
             Log.i("geofire", "Akey moved but here");
         }

         @Override
         public void onGeoQueryReady() {
             Log.i("geofire", "All initial data has been loaded and events have been fired!");
         }

         @Override
         public void onGeoQueryError(DatabaseError error) {
             Log.i("geofire", "Error Occured");
         }
     });


     //end of code for geofire
 }
    private void foundDevice(){
        searchRetailer();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList=new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        foundDevice.setVisibility(View.VISIBLE);
        animatorSet.start();
    }
}
