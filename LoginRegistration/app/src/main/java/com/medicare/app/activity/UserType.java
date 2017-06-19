package com.medicare.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pharma.medicare.app.R;

public class UserType extends BaseActivty {


   EditText userName;
    EditText userType;
    Button btnSave;

    DatabaseReference databaseMediCare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        databaseMediCare = FirebaseDatabase.getInstance().getReference();//.child("user" +
               // "");


        userName = (EditText)findViewById(R.id.edtName);
        userType= (EditText)findViewById(R.id.edtUserType);
        btnSave=(Button) findViewById(R.id.btn_save_user);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserType();
            }
        });
    }
    private void addUserType(){
String name =userName.getText().toString().trim();
        String type =  userType.getText().toString().trim();
        String id = databaseMediCare.push().getKey();
       /// UserTypeModel userType = new UserTypeModel(id,name,type);
        databaseMediCare.child("user").child(id).setValue(userType);
        //databaseMediCare.child(id).setValue(userType);
        showToast("user type added succesfully!");
    }
}
