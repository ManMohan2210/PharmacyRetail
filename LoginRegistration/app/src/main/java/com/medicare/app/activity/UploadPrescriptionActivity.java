package com.medicare.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.medicare.launch.app.R;

import java.util.ArrayList;
import java.util.List;

public class UploadPrescriptionActivity extends BaseActivty implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1111;
    private static final int RESULT_LOAD_IMAGE = 2222;
    private ImageView ivSelectedImage;
    private RelativeLayout rlCameraUpload;
    private RelativeLayout rlGallaryUpload;
    private List precData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_periscription);

        ivSelectedImage = (ImageView) findViewById(R.id.iv_selected_image);
        rlCameraUpload = (RelativeLayout) findViewById(R.id.layout_camera);
        rlGallaryUpload = (RelativeLayout) findViewById(R.id.layout_gallery);
        Intent intent = getIntent();
        rlCameraUpload.setOnClickListener(this);
        rlGallaryUpload.setOnClickListener(this);
        precData = new ArrayList<>();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.layout_camera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.layoutExisting:

                break;
            case R.id.layout_gallery:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            handleCameraResult(data);
        } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            handleGallaryResult(data);
        }
    }

    private void handleGallaryResult(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
        ivSelectedImage.setImageBitmap(bitmap);
    }

    private void handleCameraResult(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        ivSelectedImage.setImageBitmap(photo);

    }
}