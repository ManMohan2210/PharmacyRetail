package com.medicare.app.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.medicare.launch.app.R;


public class SuccessActivity extends AppCompatActivity {

   private Toolbar toolbar;
    private TextView textView;
    private Button btnUpload;
    private Button btnSelectCity;
    private Button btnNoNetwork;
    private Button btnSearchMedicine;
    private Button btnAddMedicine;
    private Button btnShowNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("hiii");

        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnSelectCity = (Button) findViewById(R.id.btn_select_city);
        btnNoNetwork = (Button) findViewById(R.id.btn_no_network);
        btnSearchMedicine = (Button) findViewById(R.id.btn_searchmedicine);
        btnAddMedicine = (Button) findViewById(R.id.btn_add_medicine);
        btnShowNotification = (Button) findViewById(R.id.btn_show_notification);
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
        btnSelectCity.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, UserType.class);
                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(SuccessActivity.this, DirectionsMapActivity.class);
               // Intent intent = new Intent(SuccessActivity.this, ProfileActivity.class);
//                Intent intent = new Intent(SuccessActivity.this, SplashActivity.class);
                startActivity(intent);
            }
        });
        btnAddMedicine.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SuccessActivity.this, ImageDetail.class);
                Intent intent = new Intent(SuccessActivity.this, AddMedicineActivity.class);
//                Intent intent = new Intent(SuccessActivity.this, GoogleMapActivity.class);
                startActivity(intent);
            }
        });
        btnShowNotification.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(SuccessActivity.this, MyNotificationManager.class);
                Intent intent = new Intent(SuccessActivity.this, DrawerMain.class);
                startActivity(intent);
            }
        });


    }
}