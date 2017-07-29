package com.medicare.app.activity;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.test.mock.MockPackageManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.medicare.app.services.GPSTrackerService;
import com.medicare.launch.app.R;

/**
 * Created by satveer on 24-07-2017.
 */

public class GetCurrentLocation extends BaseActivty  {

    LocationManager locationManager;
    String provider;

    public String lon ="77.03313007950783";
    public String lat ="28.43537228867524";
    public double longitude =(Double.parseDouble(lon));
    public double latitude =(Double.parseDouble(lat));
    Location location;
    GetCurrentLocation currentLocation;
    DatabaseReference databaseMediCare;
    private FirebaseAuth mAuth;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTrackerService gps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }}


/*
        public void nearByUser(View v){
             String lon ="77.03313007950783";
             String lat ="28.43537228867524";
             double longitude =(Double.parseDouble(lon));
             double latitude =(Double.parseDouble(lat));
            databaseMediCare = FirebaseDatabase.getInstance().getReference();
            GeoFire geoFire = new GeoFire(databaseMediCare);



            mAuth = FirebaseAuth.getInstance();
            String uid = mAuth.getCurrentUser().getUid();
            DatabaseReference databaseMediCare = FirebaseDatabase.getInstance().getReference().child("userLocation");
            final List<String> data = new ArrayList<>();

            gps = new GPSTrackerService(GetCurrentLocation.this);

            // check if GPS enabled
            if(gps.canGetLocation()){

               //  latitude = gps.getLatitude();
                 //longitude = gps.getLongitude();

                // \n is for new line
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                        + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
            databaseMediCare.child("users").child(mAuth.getCurrentUser().getUid()).child("geocordinates").child("latitude").setValue(latitude);
            databaseMediCare.child("users").child(mAuth.getCurrentUser().getUid()).child("longitude").setValue(longitude);
            databaseMediCare.child("users").child(mAuth.getCurrentUser().getUid()).child("availability").setValue(true);
            geoFire.setLocation(uid, new GeoLocation(latitude, latitude));
            GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latitude, longitude), 1000);
            geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                @Override
                public void onKeyEntered(String key, GeoLocation location) {
                    //showToast("geocodes successfully found");
                    data.add(key);
                    Log.i("geocodes Number of users", String.valueOf(data.size()));
                }

                @Override
                public void onKeyExited(String key) {
                    Log.i(key, "geocodes left the place");
                    data.remove(key);
                }

                @Override
                public void onKeyMoved(String key, GeoLocation location) {
                    Log.i(key, "geocodes key moved but here");
                }

                @Override
                public void onGeoQueryReady() {
                    Log.i("geocodes","All initial data has been loaded and events have been fired!");
                }

                @Override
                public void onGeoQueryError(DatabaseError error) {
                    Log.i("geocodes", "error occured");
                }

            });
        }*/
    }