package com.medicare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.medicare.app.R;

public class SuccessActivity extends BaseActivty {

    private TextView textView;
    private Button btnUpload;
    private Button btnSelectCity;
    private Button btnNoNetwork;
    private Button btnSearchMedicine;
    private Button btnAddMedicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Intent intent = getIntent();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("hiii");

        btnUpload = (Button)findViewById(R.id.btn_upload);
        btnSelectCity=(Button)findViewById(R.id.btn_select_city);
        btnNoNetwork=(Button)findViewById(R.id.btn_no_network);
        btnSearchMedicine=(Button)findViewById(R.id.btn_searchmedicine);
        btnAddMedicine=(Button)findViewById(R.id.btn_add_medicine);

        btnUpload.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, UploadPrescriptionActivity.class);
                startActivity(intent);
            }
        });

        btnSelectCity.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, ListDisplayActivity.class);
                startActivity(intent);
            }
        });
       /* btnSelectCity.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, UserType.class);
                startActivity(intent);
            }
        });*/
        btnNoNetwork.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(SuccessActivity.this, NoNetworkActivity.class);
                Intent intent = new Intent(SuccessActivity.this, UberMapActivity.class);
                startActivity(intent);
            }
        });
        btnSearchMedicine.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SuccessActivity.this, HomeScreenActivity.class);
                Intent intent = new Intent(SuccessActivity.this, TokenSending.class);
                startActivity(intent);
            }
        });
        btnAddMedicine.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, AddMedicineActivity.class);
//                Intent intent = new Intent(SuccessActivity.this, GoogleMapActivity.class);
                startActivity(intent);
            }
        });

    }





}