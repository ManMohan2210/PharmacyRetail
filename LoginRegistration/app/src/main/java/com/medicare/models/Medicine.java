package com.medicare.models;

/**
 * Created by satveer on 26-05-2017.
 */

public class Medicine {



    private int id;

    private String medicineName;
    private String description;
    private byte[] imageByteArray;

    public Medicine(int id, String name, String description, byte[] image) {
this.id = id;
        this.medicineName = name;
        this.description= description;
        this.imageByteArray=image;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return medicineName;
    }
}
