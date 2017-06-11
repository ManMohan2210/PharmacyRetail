package com.medicare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.medicare.app.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    //@Bind(R.id.tv_user_email);
    TextView mTxtUserEmail;


    Button mLogoutButton;
    //@Bind(R.id.btn_login)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth =FirebaseAuth.getInstance();
        mTxtUserEmail = (TextView) findViewById(R.id.tv_user_email);
                mLogoutButton = (Button)findViewById(R.id.btn_logout);
        mLogoutButton.setOnClickListener(this);

if (firebaseAuth.getCurrentUser()==null)
{
    finish();
    startActivity(new Intent(this, LoginPageActivity.class));
}
        FirebaseUser user =firebaseAuth.getCurrentUser();
        mTxtUserEmail.setText("Welcome" +user.getEmail());

    }

    @Override
    public void onClick(View v) {
        if (v==mLogoutButton){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginPageActivity.class));
        }

    }
}
