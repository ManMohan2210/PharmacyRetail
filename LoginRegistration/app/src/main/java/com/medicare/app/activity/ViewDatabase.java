package com.medicare.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pharma.medicare.app.R;

import java.util.ArrayList;

public class ViewDatabase extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private DatabaseReference mDatabase;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_database_layout);


        mListView = (ListView) findViewById(R.id.listview);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        myRef = mFirebaseDatabase.getReference("medicare");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();




    mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
               // toastMessage("Successfully signed in with: " + user.getEmail());
            } else {
                // User is signed out
                //Log.d(TAG, "onAuthStateChanged:signed_out");
               // toastMessage("Successfully signed out.");
            }
            // ...
        }
    };

  myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            showData(dataSnapshot);
        }



        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });}
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){

            MedicineTypeModel medicineType = new MedicineTypeModel();
            medicineType.setMedicineID(ds.child(userID).getValue(MedicineTypeModel.class).getMedicineID()); //set the name
            ArrayList<String> array  = new ArrayList<>();

            array.add(medicineType.getMedicineID());
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);
        /*  byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                  Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                  mImgMed.setImageBitmap(decodedByte);*/


        }
    }
}
