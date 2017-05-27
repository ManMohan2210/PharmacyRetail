
package com.medicare.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.medicare.sqlite.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMedicine extends BaseActivty {
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
    private static final int REQUEST_CODE_GALLERY = 9999;
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
                ActivityCompat.requestPermissions(AddMedicine.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
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
                Intent intent1= new Intent(AddMedicine.this,MedicineList.class);
                startActivity(intent);
            }
        });
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






