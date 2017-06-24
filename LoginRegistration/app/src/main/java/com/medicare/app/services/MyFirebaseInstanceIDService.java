package com.medicare.app.services;


import android.content.Intent;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
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
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
storeToken(refreshedToken);
    }


private void storeToken(String token){
    SharedPrefManager.getInstance(getApplicationContext()).storeToken(token);
}
    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}