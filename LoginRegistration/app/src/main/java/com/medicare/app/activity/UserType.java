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

import static android.R.id.message;

public class UserType extends BaseActivty implements TextWatcher {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;
    private GetCurrentLocation currentLocation = new GetCurrentLocation();
    private EditText userName;
    private EditText userType;
    private  Button btnSave;
    private    Button btnSend;
    private Button btnGeoCodes;
    private UserTypeModel mSender;
    private    UserTypeModel mReceiver;
    private static final String TAG = "UserType";
    private DatabaseReference databaseMediCare;
    private  GeoFire geoFire;
    private    Set<GeoQuery> geoQueries = new HashSet<>();

    private List<UserTypeModel> users = new ArrayList<>();
    private ValueEventListener userValueListener;
    private boolean fetchedUserIds;
    private Set<String> userIdsWithListeners = new HashSet<>();
    private  Location me;
    private  Map<String, Location> userIdsToLocations = new HashMap<>();

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

                    long timestamp = new Date().getTime();
                    long dayTimestamp = UtilityUtil.getDayTimestamp(timestamp);
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
              String lon = "28.414479104172674";
            String lat = "77.09754467010498";
           double longitude = (Double.parseDouble(lon));
           double latitude = (Double.parseDouble(lat));
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
                        Toast.makeText(UserType.this, "Successfully Found", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    public void sendNotification() {
        {
            getUsers();
            if (mSender != null) {


                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();

                UserTypeModel currentUser = UserTypeModel.getInastnce();
                String from = mSender.getUserId();
                String to = mReceiver.getUserId();
                String body = "Value Changed for notify";
                long timestamp = new Date().getTime();
                long dayTimestamp = UtilityUtil.getDayTimestamp(timestamp);
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
    }

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


    private void addUserType() {
        String name = userName.getText().toString().trim();
        String type = userType.getText().toString().trim();
        String id = databaseMediCare.push().getKey();
        /// UserTypeModel userType = new UserTypeModel(id,name,type);
        databaseMediCare.child("user").child(id).setValue(userType);
        //databaseMediCare.child(id).setValue(userType);
        showToast("user type added succesfully!");
    }


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