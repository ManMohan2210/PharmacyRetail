
package com.medicare.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pharma.medicare.app.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddMedicineActivity extends BaseActivty implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 234;

    // EditText mEdtMedicine, mEdtMedicineDesc;
    Button mBtnUpload, mBtnClick, mBtnSave, mBtnSaveData;
    ImageView mImgMed;
    ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST = 1111;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private Uri filePath = null;
    String encodedImage;
    private ListView mListView;
    private static final String TAG = "AddMedicine";
    public List<MedicineTypeModel> medicineData = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final Intent intent = getIntent();
        setContentView(R.layout.add_medicines);
        init();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mListView = (ListView) findViewById(R.id.listview);
        progressDialog = new ProgressDialog(AddMedicineActivity.this);
        mBtnUpload.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mBtnClick.setOnClickListener(this);
        mBtnSaveData.setOnClickListener(this);
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Toast.makeText(getBaseContext(),
                        "pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void init() {
        // mEdtMedicine = (EditText) findViewById(R.id.edt_medicine_name);
        // mEdtMedicineDesc = (EditText) findViewById(R.id.edt_medicine_description);
        mBtnUpload = (Button) findViewById(R.id.btn_upload);
        mBtnClick = (Button) findViewById(R.id.btn_click);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mImgMed = (ImageView) findViewById(R.id.medicine_img);
        mBtnSaveData = (Button) findViewById(R.id.btn_saveData);
    }


    public void onClick(View view) {
        if (view == mBtnUpload) {
            //call image chooser
            showFileChooser();

        } else if (view == mBtnSave) {
// call upload image
            uploadImage();
        } else if (view == mBtnSaveData) {
            String user = mAuth.getCurrentUser().getUid();
            myRef.child("users").child(user).child("search").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                     List<MedicineTypeModel> codedImageList = new ArrayList<>();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot :
                                dataSnapshot.getChildren()) {
                            MedicineTypeModel medicine = snapshot.getValue(MedicineTypeModel.class);
                            medicine.setKey(snapshot.getKey());
                            medicineData.add(medicine);

                            encodedImage = medicine.getSearchMedicine();
                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            mImgMed.setImageBitmap(decodedByte);
                        }
                        for (MedicineTypeModel medicine : medicineData) {
                            Log.i(TAG, "medicine search: " + medicine.getSearchMedicine());
                            Log.i(TAG, " search time: " + medicine.getSearchTime());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else if (view == mBtnClick) {
            //call camera click
            clickImage();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG.JPEG, 100, stream);
                byte[] byteFormat = stream.toByteArray();
                encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mImgMed.setImageBitmap(bitmap);
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImgMed.setImageBitmap(photo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG.JPEG, 100, stream);
            byte[] byteFormat = stream.toByteArray();
            String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
    }

    private void clickImage() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void saveDetails(View view) {
        //showToast("hi");
        //String medicineName = mEdtMedicine.getText().toString().trim();
        //  String medicineDesc = mEdtMedicineDesc.getText().toString().trim();
        // if(!TextUtils.isEmpty(medicineName)&&!TextUtils.isEmpty(medicineDesc) && filePath != null){
        progressDialog.setMessage("Details Saving...");
        progressDialog.show();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String user = firebaseAuth.getCurrentUser().getUid();
        String id = mDatabase.push().getKey();


        progressDialog.setMessage("Details Saved...");
        // Intent intent = new Intent(AddMedicineActivity.this, LoginPageActivity.class);
        //startActivity(intent);
        progressDialog.dismiss();


    }


    private void uploadImage() {
//        String medicineDesc = mEdtMedicineDesc.getText().toString().trim();
        // if(!TextUtils.isEmpty(medicineName)&&!TextUtils.isEmpty(medicineDesc) && filePath != null){
        progressDialog.setMessage("Details Saving...");
        progressDialog.show();
        long timestamp = new Date().getTime();
        String id = mDatabase.push().getKey();
        String searchMedicine = encodedImage;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String user = firebaseAuth.getCurrentUser().getUid();
        MedicineTypeModel medicineType = new MedicineTypeModel(searchMedicine, timestamp);
        mDatabase.child("users").child(user).child("search").push().setValue(medicineType);
        // mDatabase.child("users").child(user).child("search").updateChildren((Map<String, Object>) medicineType);
        // mDatabase.child("users").child(id).setValue(medicineType);
        showToast("saved");
        progressDialog.setMessage("Details Saved...");
        // Intent intent = new Intent(AddMedicineActivity.this, LoginPageActivity.class);
        //startActivity(intent);
        progressDialog.dismiss();

    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        //---returns the number of images---
        public int getCount() {
           return medicineData.size();
        }

        //---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;
            }
           // imageView.setImageResource(medicineData.get(position));
            return imageView;
        }
    }

}



/*

package com.medicare.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pharma.medicare.app.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddMedicineActivity extends BaseActivty implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 234;

    EditText mEdtMedicine, mEdtMedicineDesc;
    Button mBtnUpload, mBtnClick, mBtnSave,mBtnSaveData;
    ImageView mImgMed;
    ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST = 1111;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
private Uri filePath=null;
    String encodedImage;
    private ListView mListView;
    private static final String TAG = "AddMedicine";
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final Intent intent = getIntent();
        setContentView(R.layout.add_medicines);
        init();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mListView = (ListView) findViewById(R.id.listview);
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
{          mDatabase.child("users").equalTo("search").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<MedicineTypeModel> data = new ArrayList<>();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot :
                                dataSnapshot.getChildren()) {
                            MedicineTypeModel element = snapshot.getValue(MedicineTypeModel.class);
                            element.setKey(snapshot.getKey());
                            data.add(element);

                        }
                        for (MedicineTypeModel user : data) {
                            Log.i(TAG, "medicine : " + user.getMedicineID());
                            encodedImage = user.getMedicineID();
                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            mImgMed.setImageBitmap(decodedByte);
                        }
                    }}
                @Override
                public void onCancelled (DatabaseError databaseError){
                    Log.i(TAG, "loadPost:onCancelled", databaseError.toException());
                }
            });

*/
/*
            Intent intent = new Intent(AddMedicineActivity.this, ViewDatabase.class);
            startActivity(intent);*//*




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
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG.JPEG, 100, stream);
                byte[] byteFormat = stream.toByteArray();
                 encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mImgMed.setImageBitmap(bitmap);
        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImgMed.setImageBitmap(photo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG.JPEG, 100, stream);
            byte[] byteFormat = stream.toByteArray();
            String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image*/
/*");
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
        FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
        String user = firebaseAuth.getCurrentUser().getUid();
            String id = mDatabase.push().getKey();
           // MedicineTypeModel medicineType = new MedicineTypeModel(searchMedicine,);
           // mDatabase.child("medicine").child(id).setValue(medicineType);
       // mDatabase.child("users").child(user).child("search").setValue(medicineType);

            progressDialog.setMessage("Details Saved...");
       // Intent intent = new Intent(AddMedicineActivity.this, LoginPageActivity.class);
        //startActivity(intent);
            progressDialog.dismiss();



        }
    //}




    private void uploadImage(){



        String medicineDesc = mEdtMedicineDesc.getText().toString().trim();
        // if(!TextUtils.isEmpty(medicineName)&&!TextUtils.isEmpty(medicineDesc) && filePath != null){
        progressDialog.setMessage("Details Saving...");
        progressDialog.show();
        long timestamp = new Date().getTime();
        String id = mDatabase.push().getKey();
        String searchMedicine=encodedImage;
        FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
        String user = firebaseAuth.getCurrentUser().getUid();
        MedicineTypeModel medicineType = new MedicineTypeModel(searchMedicine,timestamp);
       // mDatabase.child("users").child(user).child("search").updateChildren((Map<String, Object>) medicineType);
       // mDatabase.child("users").child(id).setValue(medicineType);
showToast("saved");
        progressDialog.setMessage("Details Saved...");
        // Intent intent = new Intent(AddMedicineActivity.this, LoginPageActivity.class);
        //startActivity(intent);
        progressDialog.dismiss();

   */
/*     if(filePath!=null) {
            progressDialog.setTitle("file is uploading...");
            progressDialog.show();
           *//*
*/
/* StorageReference riversRef = mStorageRef.child("mediCareImages/medicine.jpg");

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
            ;*//*
*/
/*
        }else{
            //display error toast
            showToast("please select an image.");
        }*//*

    }




  }


*/
/* private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            UserInformation uInfo = new UserInformation();
            uInfo.setName(ds.child(userID).getValue(UserInformation.class).getName()); //set the name
            uInfo.setEmail(ds.child(userID).getValue(UserInformation.class).getEmail()); //set the email
            uInfo.setPhone_num(ds.child(userID).getValue(UserInformation.class).getPhone_num()); //set the phone_num

            //display all the information
            Log.d(TAG, "showData: name: " + uInfo.getName());
            Log.d(TAG, "showData: email: " + uInfo.getEmail());
            Log.d(TAG, "showData: phone_num: " + uInfo.getPhone_num());

            ArrayList<String> array  = new ArrayList<>();
            array.add(uInfo.getName());
            array.add(uInfo.getEmail());
            array.add(uInfo.getPhone_num());
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);
        }
    }*//*


*/
