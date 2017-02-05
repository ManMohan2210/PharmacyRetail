package mypackage.loginregistration;

import android.app.Activity;
import android.app.Presentation;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mypackage.loginregistration.Model.PrescriptionData;

public class UploadPrescription extends BaseActivty implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1111;
    private static final int RESULT_LOAD_IMAGE = 2222;
    ImageView ivSelectedImage;
    RelativeLayout rlCameraUpload;
    RelativeLayout rlGallaryUpload;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_periscription);

        ivSelectedImage = (ImageView) findViewById(R.id.iv_selected_image);
        rlCameraUpload = (RelativeLayout) findViewById(R.id.layoutCamera);
        rlGallaryUpload = (RelativeLayout) findViewById(R.id.layoutGallery);
        Intent intent = getIntent();
        rlCameraUpload.setOnClickListener(this);
        rlGallaryUpload.setOnClickListener(this);
        precData = new ArrayList<PrescriptionData>();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.layoutCamera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.layoutExisting:

                break;
            case R.id.layoutGallery:
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
        PrescriptionData presData = new PrescriptionData();
        presData.setPhotoBitmap(bitmap);
        presData.setDateTimeStamp(System.currentTimeMillis());
        precData.add(presData);
    }

    private void handleCameraResult(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        ivSelectedImage.setImageBitmap(photo);

        PrescriptionData presData = new PrescriptionData();
        presData.setPhotoBitmap(photo);
        presData.setDateTimeStamp(System.currentTimeMillis());
        precData.add(presData);
    }
}