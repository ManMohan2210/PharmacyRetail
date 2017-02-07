package com.pharma.medicare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Paracetamol extends BaseActivty {

    private TextView textViewDetails;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paracetamol);
        Intent intent = getIntent();
        textViewDetails = (TextView) findViewById(R.id.tv_details);
        imgView = (ImageView) findViewById(R.id.imageViewPic);


    }


}