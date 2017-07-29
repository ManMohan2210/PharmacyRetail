package com.medicare.app.activity;

import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicare.app.Util.UtilityUtil;
import com.medicare.app.models.Message;
import com.medicare.launch.app.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserType extends BaseActivty implements TextWatcher {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private GetCurrentLocation currentLocation = new   GetCurrentLocation();
    //  private DatabaseReference mDatabase;
    EditText userName;
    EditText userType;
    Button btnSave;
    Button btnSend;
    Button btnGeoCodes;
    UserTypeModel mSender;
    UserTypeModel mReceiver;
    private static final String TAG = "UserType";
    DatabaseReference databaseMediCare;
    GeoFire geoFire;
    Set<GeoQuery> geoQueries = new HashSet<>();

    List<UserTypeModel> users = new ArrayList<>();
    ValueEventListener userValueListener;
    boolean fetchedUserIds;
    Set<String> userIdsWithListeners = new HashSet<>();
    Location me;
    Map<String, Location> userIdsToLocations = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_user_type);
        databaseMediCare = FirebaseDatabase.getInstance().getReference();//.child("user" +
        // "");
        userName = (EditText) findViewById(R.id.edtName);
        userType = (EditText) findViewById(R.id.edtUserType);
        btnSave = (Button) findViewById(R.id.btn_save_user);
        btnSend = (Button) findViewById(R.id.btn_send_data);
        btnGeoCodes = (Button) findViewById(R.id.btn_geo_codes);
        FirebaseUser user;
        FirebaseDatabase.getInstance().getReference()
                .child("geofire")
                .child("")
                .child("l").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // double latitude = dataSnapshot.child("0").getValue(Double.class);
                //double longitude = dataSnapshot.child("1").getValue(Double.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        myRef = mFirebaseDatabase.getReference("medicare");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   getUsers();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSender != null) {


                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    UserTypeModel currentUser = UserTypeModel.getInastnce();
                    String from = mSender.getUserId();
                    String to = mReceiver.getUserId();
                    String body = "Value Changed for notify";

                    // Message message = new Message(from,to,body);

                    //databaseMediCare.child("users").child(to).child("messages").setValue(message);
                    //UserTypeModel currentUser = UserTypeModel.getInastnce();
                    long timestamp = new Date().getTime();
                    // String body = "Value Changed for notify";
                    // String from="cRscPZIgt4ZqMdSodWaBWMw01z62";//currentUser.getUserId();
                    // String to ="zqWuVhcLJjdBUNABcSUo2Ua5oYH2";//mReceiver.getUserId();
                    long dayTimestamp = UtilityUtil.getDayTimestamp(timestamp);
                    //String ownerUid = owner.getUid();
                    // String userUid = user.getUid();
                    Message message =
                            new Message(timestamp, -timestamp, dayTimestamp, body, from, to);
                    databaseMediCare
                            .child("notifications")
                            .child("messages")
                            .push()
                            .setValue(message);
                    databaseMediCare
                            .child("messages")
                            .child(to)
                            .child(from)
                            .push()
                            .setValue(message);
                    if (!to.equals(from)) {
                        databaseMediCare
                                .child("messages")
                                .child(from)
                                .child(to)
                                .push()
                                .setValue(message);
                    }

                }
            }
        });

        btnGeoCodes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    String lon ="28.414479104172674";
                    String lat ="77.09754467010498";
                    double longitude =(Double.parseDouble(lon));
                    double latitude =(Double.parseDouble(lat));
                double radius = 100; // in km
                final Set<String> runnersNearby = new HashSet<String>();
                databaseMediCare = FirebaseDatabase.getInstance().getReference();
                mAuth = FirebaseAuth.getInstance();
               // double lat = gpsTracker.getLatitude();
               // double lang = gpsTracker.getLongitude();
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
                                                          Toast.makeText(UserType.this, "Successfully Found", Toast.LENGTH_SHORT).show();
                                                          runnersNearby.add(key);
                                                          Log.i("geofire: Number of users", String.valueOf(runnersNearby.size()));

                                                      }

                                                      @Override
                                                      public void onKeyExited(String key) {
                                                          Log.i("geofire","left the place");
                                                          runnersNearby.remove(key);
                                                      }

                                                      @Override
                                                      public void onKeyMoved(String key, GeoLocation location) {
                                                          Log.i("geofire","Akey moved but here");
                                                      }

                                                      @Override
                                                      public void onGeoQueryReady() {
                                                          Log.i("geofire","All initial data has been loaded and events have been fired!");
                                                      }

                                                      @Override
                                                      public void onGeoQueryError(DatabaseError error) {
                                                         Log.i("geofire","Error Occured");
                                                      }
                });}});}
//currentLocation.nearByUser(v);

           /* databaseMediCare.child("users").orderByChild("userType").equalTo("Customer").
                    addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                List<UserDetails> data = new ArrayList<>();


                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot snapshot :
                                            dataSnapshot.getChildren()) {
                                        UserDetails userDetails = snapshot.child("geocordinates").getValue(UserDetails.class);
                                        data.add(userDetails);

                                    }
                                    for (UserDetails userDetails : data) {

                                        //Log.i(TAG, "geocordinates " + userDetails.getUid());
                                        Log.i(TAG, "user lan: " + userDetails.getLan());
                                        Log.i(TAG, "user lon: " + userDetails.getLon());

                                        Location mylocation = new Location("");
                                        Location dest_location = new Location("");
                            String lat = userDetails.getLan();
                            String lon = userDetails.getLon();

                            *//*dest_location.setLatitude(Double.parseDouble(lat));
                            dest_location.setLongitude(Double.parseDouble(lon));
                            Double my_loc = 0.00;
                            mylocation.setLatitude(my_loc);
                            mylocation.setLongitude(my_loc);
                            float distance = mylocation.distanceTo(dest_location);//in meters
                            showToast("Distance"+Double.toString(distance));
                                        showToast("Distance in meter"+distance);*//*
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.i(TAG, "loadPost:onCancelled", databaseError.toException());
                            }
                        });*/

        private void getUsers() {
            //  addUserType();

            databaseMediCare.child("users").orderByChild("userType").equalTo("Customer").
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<UserTypeModel> data = new ArrayList<>();
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot :
                                        dataSnapshot.getChildren()) {
                                    UserTypeModel element = snapshot.getValue(UserTypeModel.class);
                                    element.setKey(snapshot.getKey());
                                    data.add(element);

                                }
                                boolean isTrue = true;
                                for (UserTypeModel user : data) {
                                    if (isTrue) {
                                        mReceiver = user;
                                        isTrue = false;
                                    }
                                    Log.i(TAG, "user name: " + user.getUserName());
                                    Log.i(TAG, "user email: " + user.getEmail());
                                    Log.i(TAG, "user dob: " + user.getUserType());
                                    Log.i(TAG, "user dob: " + user.getMobileNumber());
                                    mSender = user;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
        }
 /*       myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        private void addUserType() {
            String name = userName.getText().toString().trim();
            String type = userType.getText().toString().trim();
            String id = databaseMediCare.push().getKey();
            /// UserTypeModel userType = new UserTypeModel(id,name,type);
            databaseMediCare.child("user").child(id).setValue(userType);
            //databaseMediCare.child(id).setValue(userType);
            showToast("user type added succesfully!");
        }


/*
    private void showData(DataSnapshot dataSnapshot) {
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            UserTypeModel userType = new UserTypeModel();
        userType.set.setMedicineID(ds.child(userID).getValue(MedicineTypeModel.class).getMedicineID()); //set the name





        }
*/



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}