package com.pharma.medicare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.pharma.medicare.activity.R;

public class Success extends BaseActivty {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_page);

        Intent intent = getIntent();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("hiii");

    }


}