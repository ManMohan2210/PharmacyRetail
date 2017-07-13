package com.medicare.app.activity;

/**
 * Created by satveer on 11-07-2017.
 */


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.medicare.launch.app.R;


public class NotificationView extends Activity {
    String title;
    String text;
    TextView txttitle;
    TextView txttext;
    Button btnShow;
    Button btnCancel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationview);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Dismiss Notification
        notificationmanager.cancel(0);

        // Retrive the data from MainActivity.java
        Intent i = getIntent();

        title = i.getStringExtra("title");
        text = i.getStringExtra("text");

        // Locate the TextView
        txttitle = (TextView) findViewById(R.id.title);
        txttext = (TextView) findViewById(R.id.text);

        btnShow=(Button)findViewById(R.id.btn_show_notif);
        btnCancel=(Button)findViewById(R.id.btn_cancel_notif);
        // Set the data into TextView
        txttitle.setText(title);
        txttext.setText(text);


    }
}