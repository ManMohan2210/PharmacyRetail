


package com.medicare.app.activity;

/**
 * Created by satveer on 07-06-2017.
 */


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medicare.app.Util.MyLocation;
import com.medicare.launch.app.R;

import java.util.List;
import java.util.Locale;
import static com.medicare.launch.app.R.id.map;


public class RetailerMapActivity extends BaseActivty implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, MyLocation.LocationResult {
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String TAG = "MAP LOCATION";
    Context mContext;
    TextView mLocationMarkerText;
    private LatLng mCenterLatLong;
    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    LocationManager locationManager;
    private AddressResultReceiver mResultReceiver;
    private ImageView mMarker;
    Location mLastLocation;
    Location mLocation;
    DatabaseReference databaseMediCare;
    //GoogleApiClient mGoogleApiClient;
    Marker mCurrLocationMarker;
    /**
     * The formatted location address.
     */

    protected String mAddressOutput;
    protected String mAreaOutput;
    protected String mCityOutput;
    protected String mStateOutput;
    TextView mLocationAddress;
    TextView mLocationText;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private Button mAddLocation;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        databaseMediCare = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_map);
        mContext = this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mLocationMarkerText = (TextView) findViewById(R.id.locationMarkertext);
        mLocationAddress = (TextView) findViewById(R.id.Address);
        mMarker= (ImageView)findViewById(R.id.imageMarker);

        final MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this,this);

        FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                String user = firebaseAuth.getCurrentUser().getUid();
                String address = mLocationAddress.getText().toString().trim();
                double longitude = 0;
                double latitude = 0;
                if(mLocation!=null){
                    latitude = mLocation.getLatitude();
                    longitude =mLocation.getLongitude();
                }

                UserTypeModel userDetail = new UserTypeModel(address, longitude, latitude);
                databaseMediCare.child("users").child(user).child("geocordinates").setValue(userDetail);

                showToast("added succesfully!");
                Intent intent = new Intent(RetailerMapActivity.this, HomeScreenSearchActivity.class);
                startActivity(intent);
            }
        });

        mapFragment.getMapAsync(this);
        mResultReceiver = new AddressResultReceiver(new Handler());

        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            if (!AppUtils.isLocationEnabled(mContext)) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setMessage("Location not enabled!");
                dialog.setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.show();
            }
            buildGoogleApiClient();
        } else {
            Toast.makeText(mContext, "Location not supported in this device", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "OnMapReady");
        mMap = googleMap;

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition cameraPosition = mMap.getCameraPosition();
                mCenterLatLong = cameraPosition.target;
                String fullAddress = "";
                mMap.clear();
                try {
                    mLocation.setLatitude(mCenterLatLong.latitude);
                    mLocation.setLongitude(mCenterLatLong.longitude);

                    startIntentService(mLocation);
                    fullAddress =getCompleteAddressString(mLocation.getLatitude(),mLocation.getLongitude());
                    mLocationMarkerText.setText(fullAddress);
                    mLocationAddress.setText(fullAddress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleApiClient();
     mMap.setMyLocationEnabled(true);

//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//
//        // Add a marker in current location and move the camera

    }




    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            changeMap(mLastLocation);
            Log.d(TAG, "ON connected");

        } else
            try {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        mGoogleApiClient, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        String fullAddress;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        fullAddress =getCompleteAddressString(location.getLatitude(),location.getLongitude());
        mLocationMarkerText.setText(fullAddress);
        mLocationAddress.setText(fullAddress);
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        // start of code for geofire
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("retialerAvailable");
        GeoFire geoFire = new GeoFire(ref);
        geoFire.setLocation(user_id, new GeoLocation(location.getLatitude(),location.getLongitude()));

        //end of code for geofire
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mGoogleApiClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //finish();
            }
            return false;
        }
        return true;
    }

    private void changeMap(Location location) {

        Log.d(TAG, "Reaching map" + mMap);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // check if map is created successfully or not
        if (mMap != null) {
            mMap.getUiSettings().setZoomControlsEnabled(true);
            LatLng latLong;
            String fullAddress = "";

            latLong = new LatLng(location.getLatitude(), location.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(19f).tilt(0).build();

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.moveCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));


            //mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(20.5937,78.9629) , 19f) );

            mLocationMarkerText.setText("Lat : " + location.getLatitude() + "," + "Long : " + location.getLongitude());
            fullAddress =getCompleteAddressString(location.getLatitude(),location.getLongitude());
            mLocationMarkerText.setText(fullAddress);
            mLocationAddress.setText(fullAddress);
            startIntentService(location);
            // showToast("hii" + getCompleteAddressString(location.getLatitude(),location.getLongitude()));

        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void gotLocation(Location location) {
        mLocation = location;
        double lat= location.getLatitude();
        double lon=location.getLongitude();

        LatLng latLong = new LatLng(lat,lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong,12));
    }


    /**
     * Receiver for data sent from FetchAddressIntentService.
     */

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }


        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(AppUtils.LocationConstants.RESULT_DATA_KEY);

            mAreaOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_AREA);

            mCityOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_CITY);
            mStateOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);

            displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == AppUtils.LocationConstants.SUCCESS_RESULT) {
                //  showToast(getString(R.string.address_found));


            }


        }

    }


    /**
     * Updates the address in the UI.
     */

    protected void displayAddressOutput() {
        // mLocationAddressTextView.setText(mAddressOutput);
        try {
            if (mAreaOutput != null)
                // mLocationText.setText(mAreaOutput+ "");

                mLocationAddress.setText(mAddressOutput);
            //  mLocationText.setText(mAreaOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */

    protected void startIntentService(Location mLocation) {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(AppUtils.LocationConstants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(AppUtils.LocationConstants.LOCATION_DATA_EXTRA, mLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }


    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
            e.printStackTrace();

        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            e.printStackTrace();
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Called after the autocomplete activity has finished to return its result.
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(mContext, data);

                // TODO call location based filter


                LatLng latLong;


                latLong = place.getLatLng();

                //mLocationText.setText(place.getName() + "");

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLong).zoom(19f).tilt(70).build();


                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.moveCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));


            }


        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(mContext, data);
        } else if (resultCode == RESULT_CANCELED) {
            // Indicates that the activity closed before a selection was made. For example if
            // the user pressed the back button.
        }
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My", "" + strReturnedAddress.toString());
            } else {
                Log.w("My ", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My ", "Canont get Address!");

            Log.w("exception",e.getMessage());
        }
        return strAdd;
    }



    void getCurrentLocation(Location mLocation) {
        double lat= mLocation.getLatitude();
        double lon=mLocation.getLongitude();

        LatLng latLong = new
                LatLng(mLocation.getLatitude(),mLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong,12));


    }


    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }}


