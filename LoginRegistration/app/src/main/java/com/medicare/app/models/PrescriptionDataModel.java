package com.medicare.app.models;

/**
 * Created by satveer on 05-02-2017.
 */

public class PrescriptionDataModel {


    private int imageId;



    private byte[] photoBitmap;
    private String dateTimeStamp;

    public PrescriptionDataModel(int i, String string, byte[] blob) {

    }

    public String getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(String dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    } public byte[] getPhotoBitmap() {
        return photoBitmap;
    }

    public void setPhotoBitmap(byte[] photoBitmap) {
        this.photoBitmap = photoBitmap;
    }
}
