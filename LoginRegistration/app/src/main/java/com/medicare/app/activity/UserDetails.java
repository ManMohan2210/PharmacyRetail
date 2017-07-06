package com.medicare.app.activity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by satveer on 25-06-2017.
 */

public class UserDetails {
private String uid;
        private     Double geocordinates;
        private  String address;
    private String lan;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String lon;
    public UserDetails() {
    }

    public UserDetails(String address, String lan, String lon) {

        this.address = address;
        this.lan = lan;
        this.lon = lon;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("lan", lan);
        result.put("lon", lon);
        result.put("address", address);

        return result;
    }
    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Double getGeocordinates() {
        return geocordinates;
    }

    public void setGeocordinates(Double geocordinates) {
        this.geocordinates = geocordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
