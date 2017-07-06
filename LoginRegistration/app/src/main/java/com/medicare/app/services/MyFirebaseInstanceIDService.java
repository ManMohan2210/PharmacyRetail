package com.medicare.app.services;


import android.content.Intent;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.medicare.app.activity.SharedPrefManager;


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private FirebaseAnalytics mFirebaseAnalytics;
    public  static  final String TOKEN_BROADCAST="myfcmtokenbroadcast";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String accessToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + accessToken);
        if (firebaseUser != null)
        {
            FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(firebaseUser.getUid())
                    .child("accessToken")
                    .setValue(accessToken);
        }

        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
         storeToken(accessToken);
    }


private void storeToken(String token){
    SharedPrefManager.getInstance(getApplicationContext()).storeToken(token);
}
    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}