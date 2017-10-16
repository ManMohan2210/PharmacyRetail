package com.medicare.app.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.location.Location;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medicare.app.Util.MyLocation;
import com.medicare.launch.app.R;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShapeRibbleActivity extends BaseActivty implements MyLocation.LocationResult {


    private ImageView foundDevice;
    private double latitude;
    private double longitude;
    private LocationManager locationManager;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseMediCare;
    private String userID;
    private Handler handler;
    private GeoLocation geoLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_ribble);
        databaseMediCare = FirebaseDatabase.getInstance().getReference();
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);

        handler=new Handler();

        foundDevice=(ImageView)findViewById(R.id.foundDevice);
        ImageView button=(ImageView)findViewById(R.id.centerImage);
        rippleBackground.startRippleAnimation();

        final MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this,this);
    }

    @Override
    public void gotLocation(final Location location) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                setCurrentLocation(location);
                foundDevice();
//            }
//        },3000);

    }

    void setCurrentLocation(Location mLocation) {
        latitude = mLocation.getLatitude();
        longitude = mLocation.getLongitude();
        geoLocation = new GeoLocation(latitude,longitude);
//        LatLng latLong = new
//                LatLng(mLocation.getLatitude(), mLocation.getLongitude());
//        try {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (LocationListener) this);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }


    }
 private void searchRetailer(){

     double radius = 1000; // in km
     final Set<String> runnersNearby = new HashSet<String>();
//     databaseMediCare = FirebaseDatabase.getInstance().getReference();
     mAuth = FirebaseAuth.getInstance();
//     databaseMediCare.child("available").child(mAuth.getCurrentUser().getUid()).child("latitude").setValue(latitude);
//     databaseMediCare.child("available").child(mAuth.getCurrentUser().getUid()).child("longitude").setValue(longitude);
//     databaseMediCare.child("available").child(mAuth.getCurrentUser().getUid()).child("availability").setValue(true);
     DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("userLocation");
     GeoFire geoFire = new GeoFire(ref);
     geoFire.setLocation(mAuth.getCurrentUser().getUid(), geoLocation);//new GeoLocation(latitude, longitude));
     GeoQuery geoQuery = geoFire.queryAtLocation(geoLocation, radius);// new GeoLocation(latitude, longitude), radius);
     geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
         @Override
         public void onKeyEntered(String key, GeoLocation location) {
              showToast("Successfully Found");
             runnersNearby.add(key);
             Log.i("geofire: Number of users", "Num of users "+ runnersNearby.size());

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        foundDevice.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 100);
        animatorSet.start();
    }


}
