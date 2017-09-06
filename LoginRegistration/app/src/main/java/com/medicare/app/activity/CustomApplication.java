package com.medicare.app.activity;

import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;

import java.util.UUID;

/**
 * Created by satveer on 16-06-2017.
 */

public class CustomApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        Firebase.setAndroidContext(this);

        if(null != SharedPrefManager.getInstance(getApplicationContext()).getUUID()){
            final String uniUserId =UUID.randomUUID().toString();
            SharedPrefManager.getInstance(getApplicationContext()).storeUUID(uniUserId);
        }

        AppEventsLogger.activateApp(this);
    }
}
