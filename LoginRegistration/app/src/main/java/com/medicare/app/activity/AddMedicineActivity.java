
package com.medicare.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.medicare.app.R;
import com.medicare.app.sqlite.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMedicineActivity extends BaseActivty {
    /*    @Bind(edt_medicine_name)
        EditText mEdtMedicine;
        @Bind(R.id.edt_medicine_description)
        EditText mEdtMedicineDesc;
        @Bind(R.id.medicine_img)
        ImageView imgMedicine;
        @Bind(R.id.btn_upload)
        Button mBtnUpload;
        @Bind(R.id.btn_click)
        Button mBtnClick;
        @Bind(R.id.btn_save)
        Button mBtnSave;*/
    // private DataHelper dbHelper;
    EditText mEdtMedicine, mEdtMedicineDesc;
    Button mBtnUpload, mBtnClick, mBtnSave;
    ImageView mImgMed;
    private static final int CAMERA_REQUEST = 1111;
    private static final int RESULT_LOAD_IMAGE = 2222;
    private static final int REQUEST_CODE_GALLERY = 111;
    public static SQLiteHelper sqLiteHelper;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final Intent intent = getIntent();
        setContentView(R.layout.add_medicines);
        init();
        sqLiteHelper = new SQLiteHelper(this, "MedicineDB.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MEDICINE (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, image BLOG)");
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ActivityCompat.requestPermissions(AddMedicineActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sqLiteHelper.insertData(mEdtMedicine.getText().toString().trim(),
                            mEdtMedicineDesc.getText().toString().trim(),imageViewToByte(mImgMed));
                    showToast("Added successfully!");
                    mEdtMedicine.setText("");
                            mEdtMedicineDesc.setText("");
                    mImgMed.setImageResource(R.drawable.prescription);//(R.mipmac.ic_launcher)

                }catch (Exception e ){
                    e.printStackTrace();
                }

            }
        });
        mBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent1= new Intent(AddMedicineActivity.this,MedicineListActivity.class);
                //startActivity(intent);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
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
        mImgMed.setImageBitmap(bitmap);
    }

    private void handleCameraResult(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        mImgMed.setImageBitmap(photo);

    }

    private byte[] imageViewToByte(ImageView imageView)
    {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();// ByteArrayInputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }





    public void onRequestPermissionResult(int requestCode, @NonNull String[] permission, int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                showToast("You dont have permission to access file location.");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mImgMed.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            handleCameraResult(data);
        } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            handleGallaryResult(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void init() {
        mEdtMedicine = (EditText) findViewById(R.id.edt_medicine_name);
        mEdtMedicineDesc = (EditText) findViewById(R.id.edt_medicine_description);
        mBtnUpload = (Button) findViewById(R.id.btn_upload);
        mBtnClick = (Button) findViewById(R.id.btn_click);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mImgMed = (ImageView) findViewById(R.id.medicine_img);
    }
}






