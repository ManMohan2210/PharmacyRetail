package com.medicare.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medicare.app.Util.MyLocation;
import com.medicare.launch.app.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by satveer on 20-09-2017.
 */

public class ShowRetailersActivity  extends FragmentActivity implements GeoQueryEventListener,OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, MyLocation.LocationResult {

      private static final GeoLocation INITIAL_CENTER = new GeoLocation(37.7789, -122.4017);
        private static final int INITIAL_ZOOM_LEVEL = 14;
       private static final String GEO_FIRE_DB = "";
       private static final String GEO_FIRE_REF = GEO_FIRE_DB + "/_geofire";

        private GoogleMap map;
        private Circle searchCircle;
        private GeoFire geoFire;
        private GeoQuery geoQuery;
    private double latitude;
    private double longitude;
    private LocationManager locationManager;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseMediCare;
    private String userID;
        private Map<String, Marker> markers;
    Location mLocation;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_retailer);

            // setup map and camera position
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            LatLng latLngCenter = getCurrentLocation(mLocation);
            this.searchCircle = this.map.addCircle(new CircleOptions().center(latLngCenter).radius(1000));
            this.searchCircle.setFillColor(Color.argb(66, 255, 0, 255));
            this.searchCircle.setStrokeColor(Color.argb(66, 0, 0, 0));
            this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCenter, INITIAL_ZOOM_LEVEL));
          //  this.map.setOnCameraChangeListener(this);

            FirebaseOptions options = new FirebaseOptions.Builder().setApplicationId("geofire").setDatabaseUrl(GEO_FIRE_DB).build();
            FirebaseApp app = FirebaseApp.initializeApp(this, options);

            // setup GeoFire
            this.geoFire = new GeoFire(FirebaseDatabase.getInstance(app).getReferenceFromUrl(GEO_FIRE_REF));
            // radius in km
            this.geoQuery = this.geoFire.queryAtLocation(INITIAL_CENTER, 1);

            // setup markers
            this.markers = new HashMap<String, Marker>();
        }
    LatLng getCurrentLocation(Location mLocation) {
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

return latLong;
    }
        @Override
        protected void onStop() {
            super.onStop();
            // remove all event listeners to stop updating in the background
            this.geoQuery.removeAllListeners();
            for (Marker marker : this.markers.values()) {
                marker.remove();
            }
            this.markers.clear();
        }

        @Override
        protected void onStart() {
            super.onStart();
            // add an event listener to start updating locations again
            this.geoQuery.addGeoQueryEventListener(this);
        }

        @Override
        public void onKeyEntered(String key, GeoLocation location) {
            // Add a new marker to the map
            Marker marker = this.map.addMarker(new MarkerOptions().position(new LatLng(location.latitude, location.longitude)));
            this.markers.put(key, marker);
        }

        @Override
        public void onKeyExited(String key) {
            // Remove any old marker
            Marker marker = this.markers.get(key);
            if (marker != null) {
                marker.remove();
                this.markers.remove(key);
            }
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location) {
            // Move the marker
            Marker marker = this.markers.get(key);
            if (marker != null) {
                this.animateMarkerTo(marker, location.latitude, location.longitude);
            }
        }

        @Override
        public void onGeoQueryReady() {
        }

        @Override
        public void onGeoQueryError(DatabaseError error) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("There was an unexpected error querying GeoFire: " + error.getMessage())
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        // Animation handler for old APIs without animation support
        private void animateMarkerTo(final Marker marker, final double lat, final double lng) {
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final long DURATION_MS = 3000;
            final Interpolator interpolator = new AccelerateDecelerateInterpolator();
            final LatLng startPosition = marker.getPosition();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    float elapsed = SystemClock.uptimeMillis() - start;
                    float t = elapsed / DURATION_MS;
                    float v = interpolator.getInterpolation(t);

                    double currentLat = (lat - startPosition.latitude) * v + startPosition.latitude;
                    double currentLng = (lng - startPosition.longitude) * v + startPosition.longitude;
                    marker.setPosition(new LatLng(currentLat, currentLng));

                    // if animation is not finished yet, repeat
                    if (t < 1) {
                        handler.postDelayed(this, 16);
                    }
                }
            });
        }

        private double zoomLevelToRadius(double zoomLevel) {
            // Approximation to fit circle into view
            return 16384000 / Math.pow(2, zoomLevel);
        }

        //@Override
        public void onCameraChange(CameraPosition cameraPosition) {
            // Update the search criteria for this geoQuery and the circle on the map
            LatLng center = cameraPosition.target;
            double radius = zoomLevelToRadius(cameraPosition.zoom);
            this.searchCircle.setCenter(center);
            this.searchCircle.setRadius(radius);
            this.geoQuery.setCenter(new GeoLocation(center.latitude, center.longitude));
            // radius in km
            this.geoQuery.setRadius(radius / 1000);
        }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void gotLocation(Location location) {

    }
}