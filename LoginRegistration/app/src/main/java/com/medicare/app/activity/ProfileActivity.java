package com.medicare.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.medicare.app.R;
import com.medicare.app.services.MyFirebaseInstanceIDService;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
   // private static  final String TAG="firebasemessagingservice";
    //@Bind(R.id.tv_user_email);
    TextView mTxtUserEmail;

    TextView mTxtToken;
private BroadcastReceiver broadcastReceiver;
    Button mLogoutButton;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //@Bind(R.id.btn_login)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth =FirebaseAuth.getInstance();
        mTxtUserEmail = (TextView) findViewById(R.id.tv_user_email);
        mLogoutButton = (Button)findViewById(R.id.btn_logout);
        mTxtToken = (TextView)findViewById(R.id.tv_token);
        mLogoutButton.setOnClickListener(this);

        broadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mTxtToken.setText(SharedPrefManager.getInstance(ProfileActivity.this).getToken());

            }
        };

        if (SharedPrefManager.getInstance(this).getToken()!= null)
        {
        mTxtToken.setText(SharedPrefManager.getInstance(ProfileActivity.this).getToken());
        Log.d("my firebaseToken",SharedPrefManager.getInstance(this).getToken());}
        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIDService.TOKEN_BROADCAST));

if (firebaseAuth.getCurrentUser()==null)
{
    finish();
    startActivity(new Intent(this, LoginPageActivity.class));
}
        FirebaseUser user =firebaseAuth.getCurrentUser();
        mTxtUserEmail.setText("Welcome  " +user.getEmail());
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
if(firebaseAuth.getCurrentUser()==null){
    startActivity(new Intent(ProfileActivity.this, LoginPageActivity.class));
}
            }
        };

    }

    @Override
        public void onClick(View v) {
        if (v==mLogoutButton){
            firebaseAuth.signOut();
            finish();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }
}
