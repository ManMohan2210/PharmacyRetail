/*
package com.medicare.app.activity;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

*/
/**
 * Created by satveer on 13-06-2017.
 *//*


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private MyNotificationManager myNotificationManager;
    private static final String TAG = "firebasemessagingservice";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TAG, "MESSAGE RECEIVED!!");
        if (remoteMessage.getNotification().getBody() != null) {
            Log.i(TAG, "RECEIVED MESSAGE: " + remoteMessage.getNotification().getBody());
        } else {
            Log.i(TAG, "RECEIVED MESSAGE: " + remoteMessage.getData().get("message"));
        }
    }
*/
/*
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

            // Manage data

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            Map<String, String> data = remoteMessage.getData();
           // String serverKey = data.get("AAAABYhAJXQ:APA91bGa7Xs7_excNTZ7ddUr25UnOPGbak58tX1dEgxGY_cWgSLRMFlF8fdYBP8rSGl35hrT5xrxepnfMyTDl4Za5uI3i6selh_ChtSmyUZvMJBwQDjdz0prFymKKFgVSORxlxowqVaz");

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        notifyUser(remoteMessage.getFrom(),remoteMessage.getNotification().getBody());

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
*//*





    public void notifyUser(String  from, String notification) {
        myNotificationManager = new MyNotificationManager(getApplicationContext()) ;
myNotificationManager.showSmallNotification(from,notification,new Intent(getApplicationContext(),ProfileActivity.class));
        //showNotification(from, notification,new Intent(getApplicationContext(),ProfileActivity.class));

    }

}*/
