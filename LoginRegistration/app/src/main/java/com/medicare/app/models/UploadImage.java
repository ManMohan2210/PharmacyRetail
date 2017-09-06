package com.medicare.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by satveer on 10-08-2017.
 */

@IgnoreExtraProperties
public class UploadImage{

    public String name;
    public String url,position;

public long timeStamp;
    // Default constructor required for calls to

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // DataSnapshot.getValue(User.class)
    public UploadImage() {
    }


    public UploadImage(String name, String url,long timestamp) {
        this.name = name;
        this.url= url;
        this.timeStamp=timestamp;
    }
    public UploadImage(String name, String url,long timestamp, String position) {
        this.name = name;
        this.url= url;
        this.timeStamp=timestamp;
        this.position= position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }



    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}