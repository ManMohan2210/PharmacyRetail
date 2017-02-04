package com.pharma.medicare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Toast;

/**
 * Created by satveer on 15-01-2017.
 */

public class BaseActivty extends AppCompatActivity {


//    @Override
//    public void setContentView(@LayoutRes int layoutResID) {
//
//
//        if(Utility.isNetworkAvailable()){
//            layoutResID = layoutResID;
//        }else {
//            layoutResID = R.layout.activity_base;
//        }
//
//        super.setContentView(layoutResID);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     *
     * @param message
     */
    public void showToast(String message) {

        Toast toast = Toast.makeText(BaseActivty.this, message, Toast.LENGTH_LONG);
        toast.show();

    }

    /**
     *
     * @param message
     */
    public void showToastInCentre(String message) {

        Toast toast = Toast.makeText(BaseActivty.this,message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
