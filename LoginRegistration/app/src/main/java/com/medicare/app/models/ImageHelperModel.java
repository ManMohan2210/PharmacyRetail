package com.medicare.app.models;

/**
 * Created by satveer on 04-03-2017.
 */

public class ImageHelperModel {
    private String imageId;
    private byte[] imageByteArray;
    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public byte[] getImageByteArray() {
        return imageByteArray;
    }
    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }
}