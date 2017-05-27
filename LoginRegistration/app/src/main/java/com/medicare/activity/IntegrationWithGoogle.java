
package com.medicare.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.medicare.fragment.GPlusFragment;


public class IntegrationWithGoogle extends BaseActivty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.google_fragment_container);


        if (fragment == null) {
            fragment = new GPlusFragment();
            fm.beginTransaction()
                    .add(R.id.google_fragment_container, fragment)
                    .commit();


        }


    }
}