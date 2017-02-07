package com.pharma.medicare.Model;

import android.graphics.Bitmap;

/**
 * Created by satveer on 05-02-2017.
 */

public class PrescriptionData {

    private Bitmap photoBitmap;
    private Long dateTimeStamp;

    public Long getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(Long dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public void setPhotoBitmap(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }
}
