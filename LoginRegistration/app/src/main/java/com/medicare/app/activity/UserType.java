package com.medicare.app.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
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
import java.util.List;

public class UserType extends BaseActivty implements TextWatcher {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private DatabaseReference mDatabase;
    EditText userName;
    EditText userType;
    Button btnSave;
    Button btnSend;
    UserTypeModel mSender;
    UserTypeModel mReceiver;
    private static final String TAG = "UserType";
    DatabaseReference databaseMediCare;

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

//        myRef = mFirebaseDatabase.getReference("medicare");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsers();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSender != null) {


                   /* FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser  user = firebaseAuth.getCurrentUser();

                    UserTypeModel currentUser = UserTypeModel.getInastnce();
                    String from=currentUser.getUserId();
                    String to =mReceiver.getUserId();
                    String body="Value Changed for notify";

                    Message message = new Message(from,to,body);

                    databaseMediCare.child("messages").setValue(message);*/
                    UserTypeModel currentUser = UserTypeModel.getInastnce();
                    long timestamp = new Date().getTime();
                    String body = "Value Changed for notify";
                    String from="13CeeFlO7UR1YzO5aMVegoabWgi1";//currentUser.getUserId();
                    String to ="Bv98mqVC5DfEtuXKkSdmCaXLpV42";//mReceiver.getUserId();
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
                  //  inputEditText.setText("");

                 //   databaseMediCare.child("messages").child(currentUser.getUserId()).child("to").setValue(to);

//                    databaseMediCare.child("users").child(mReceiver.getUserId()).child("notifydata").child("from").setValue(mSender.getUserId());
//                    databaseMediCare.chilmessaged("users").child(mReceiver.getUserId()).child("notifydata").child("to").setValue(mSender.getUserId());

                  //  databaseMediCare.child("messages").child(currentUser.getUserId()).child("body").setValue(body);
                }
            }
        });
    }
/*    private long getDayTimestamp(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTimeInMillis();
    }*/
    private void getUsers() {
        //  addUserType();

        databaseMediCare.child("users").orderByChild("userType").equalTo("Customer").addValueEventListener(new ValueEventListener() {
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
                    boolean isTrue =  true;
                    for (UserTypeModel user : data) {
                        if(isTrue){
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


/*
    private void showData(DataSnapshot dataSnapshot) {
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            UserTypeModel userType = new UserTypeModel();
        userType.set.setMedicineID(ds.child(userID).getValue(MedicineTypeModel.class).getMedicineID()); //set the name





        }
*/

}
