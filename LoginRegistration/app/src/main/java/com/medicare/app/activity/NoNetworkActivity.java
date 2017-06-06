package com.medicare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medicare.app.R;

/**
 * Created by satveer on 14-01-2017.
 */

public class NoNetworkActivity extends BaseActivty {

    private Button btnRetry;
    private TextView tvOops;
    private TextView tvNoNetwork;
    private ImageView imageViewExm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_network);
       btnRetry = (Button)findViewById(R.id.btn_retry);
       tvOops = (TextView) findViewById(R.id.tvOops);
        tvNoNetwork= (TextView) findViewById(R.id.tvNoNetwork);
       imageViewExm= (ImageView) findViewById(R.id.imgViewEx);
        Intent intent = getIntent();
    }





}

