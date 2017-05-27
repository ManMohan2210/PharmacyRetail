package com.medicare.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.medicare.fragment.CreateNewPassword;

public class PasswordChangeFrag extends BaseActivty {


    private Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
       btnChange = (Button) findViewById(R.id.btn_email);

        btnChange.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();

                    CreateNewPassword replaceFrag = new CreateNewPassword();

                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    if (replaceFrag != null) {

                        fragmentTransaction.replace(R.id.container, replaceFrag, "replace");
                        fragmentTransaction.commit();
                    } else {
                        showToast("Please wait!");
                    }
                }
            });
        }

    }




