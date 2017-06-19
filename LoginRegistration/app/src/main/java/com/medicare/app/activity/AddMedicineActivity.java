
package com.medicare.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pharma.medicare.app.R;

import java.io.IOException;

public class AddMedicineActivity extends BaseActivty implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 234;

    EditText mEdtMedicine, mEdtMedicineDesc;
    Button mBtnUpload, mBtnClick, mBtnSave,mBtnSaveData;
    ImageView mImgMed;
    ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST = 1111;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
private Uri filePath=null;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final Intent intent = getIntent();
        setContentView(R.layout.add_medicines);
        init();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
//
        //databaseMediCare = FirebaseDatabase.getInstance().getReference().child("user");
       // mDatabase = FirebaseDatabase.getInstance().getReference().child("medicine"+"");


//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        progressDialog = new ProgressDialog(AddMedicineActivity.this);
        mBtnUpload.setOnClickListener(this);
        mBtnSave.setOnClickListener( this );
        mBtnClick.setOnClickListener( this );
        mBtnSaveData.setOnClickListener( this );


    }


   private void init() {
       mEdtMedicine = (EditText) findViewById(R.id.edt_medicine_name);
       mEdtMedicineDesc = (EditText) findViewById(R.id.edt_medicine_description);
       mBtnUpload = (Button) findViewById(R.id.btn_upload);
       mBtnClick = (Button) findViewById(R.id.btn_click);
       mBtnSave = (Button) findViewById(R.id.btn_save);
       mImgMed = (ImageView) findViewById(R.id.medicine_img);
       mBtnSaveData=(Button) findViewById(R.id.btn_saveData);
   }



   public void onClick(View view)
   {
if (view ==mBtnUpload){
    //call image chooser
    showFileChooser();

}else if (view ==mBtnSave){
// call upload image
    uploadImage();
}
else if (view == mBtnSaveData)
{
    //call save data
    saveDetails(view);

}else if (view ==mBtnClick){
    //call camera click
    clickImage();
}
   }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null&& data.getData()!=null) {
            filePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mImgMed.setImageBitmap(bitmap);
        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImgMed.setImageBitmap(photo);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
    }

    private void clickImage()
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void saveDetails(View view){
        //showToast("hi");
        String medicineName = mEdtMedicine.getText().toString().trim();
        String medicineDesc = mEdtMedicineDesc.getText().toString().trim();
       // if(!TextUtils.isEmpty(medicineName)&&!TextUtils.isEmpty(medicineDesc) && filePath != null){
            progressDialog.setMessage("Details Saving...");
            progressDialog.show();

            String id = mDatabase.push().getKey();
            MedicineTypeModel medicineType = new MedicineTypeModel(id,medicineName,medicineDesc);
           // mDatabase.child("medicine").child(id).setValue(medicineType);
        mDatabase.child("medicine").child(id).setValue(medicineType);

            progressDialog.setMessage("Details Saved...");
       // Intent intent = new Intent(AddMedicineActivity.this, LoginPageActivity.class);
        //startActivity(intent);
            progressDialog.dismiss();



        }
    //}




    private void uploadImage(){
        if(filePath!=null) {
            progressDialog.setTitle("file is uploading...");
            progressDialog.show();
            StorageReference riversRef = mStorageRef.child("mediCareImages/medicine.jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            showToast("file uploaded");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            showToast(exception.getMessage());
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage(((int)progress)+"% uploaded");
                }
            })
            ;
        }else{
            //display error toast
            showToast("please select an image.");
        }
    }
}






