package com.medicare.app.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.medicare.app.R;
import com.medicare.app.fragment.EmailFragment;
import com.medicare.app.fragment.OtpFragment;

public class PasswordChangeActivity extends BaseActivty {


    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        Intent intent = getIntent();
       // btnReset = (Button) findViewById(R.id.btn_reset);

        if (intent != null) {
            String selectedValue = intent.getStringExtra("selection");
            if (selectedValue.equals("Via OTP")) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                OtpFragment fragmentOTP = new OtpFragment();
                fragmentTransaction.add(R.id.container, fragmentOTP, "fragOTP");
                fragmentTransaction.commit();
            } else if (selectedValue.equals("Via E-mail")) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();

                EmailFragment fragmentEmail = new EmailFragment();
                fragmentTransaction.add(R.id.container, fragmentEmail, "fragEmail");
                fragmentTransaction.commit();
            } else {
                showToast("Please select any option.");
            }

        }

    }







}