package com.medicare.app.activity;


import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Firebase.setAndroidContext(getApplicationContext());
    }

}
