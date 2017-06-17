package com.medicare.app.activity;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;

/**
 * Created by satveer on 16-06-2017.
 */

public class CustomApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Firebase.setAndroidContext(this);


        AppEventsLogger.activateApp(this);
    }
}
