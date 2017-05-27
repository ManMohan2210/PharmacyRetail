package com.medicare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.medicare.services.GPSTracker;


public class ListDisplay extends BaseActivty {


        // Array of strings...
       /* String[] cityArray = {"New Delhi","Gurgaon","Pune","Mumbai",
                "Bangalore","Kolkata","Chennai"};*/
        private TextView label;
    private TextView txtViewForgot;
    private TextView txtViewAutodetect;
    private ImageView imgViewArrow;
    private ImageView imgViewDirection;
    public GPSTracker gps;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            txtViewForgot =(TextView)findViewById(R.id.tv_forgot_pwd);
            txtViewAutodetect=(TextView)findViewById(R.id.tv_autodetect);
           imgViewArrow =(ImageView)findViewById(R.id.img_location);
             imgViewDirection=(ImageView)findViewById(R.id.img_direction);
            label=(TextView)findViewById(R.id.label);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.activity_listview, cityArray);

            ListView listView = (ListView) findViewById(R.id.city_list);
            listView.setAdapter(adapter);

            imgViewDirection.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {/*
                    gps = new GPSTracker(ListDisplay.this);
                    if(gps.canGetLocation()){
                        double latitude = gps.getLatitute();
                        double longitude = gps.getLongitude();
                        Toast.makeText(getApplicationContext(),"Your Location is -\nLat: " + latitude + "\nLon" + longitude, Toast.LENGTH_LONG).show();
                    }
                else
                    {
                        gps.showSettingsAlert();
                    }*/
                    GPSTracker gps = new GPSTracker(ListDisplay.this);
                    if(gps.canGetLocation()){
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        Toast.makeText(getApplicationContext(),"Your Location is -\nLat: " + latitude + "\nLon" + longitude, Toast.LENGTH_LONG).show();
                        // \n is for new line
                    }else{
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }}

            });

        }

    }

