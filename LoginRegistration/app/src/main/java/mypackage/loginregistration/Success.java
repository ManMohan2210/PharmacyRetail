package mypackage.loginregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Success extends BaseActivty {

    private TextView textView;
    private Button btnUpload;
    private Button btnSelectCity;
    private Button btnNoNetwork;
    private Button btnSearchMedicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_page);

        Intent intent = getIntent();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("hiii");

        btnUpload = (Button)findViewById(R.id.btn_upload);
        btnSelectCity=(Button)findViewById(R.id.btn_select_city);
        btnNoNetwork=(Button)findViewById(R.id.btn_no_network);
        btnSearchMedicine=(Button)findViewById(R.id.btn_searchmedicine);

        btnUpload.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Success.this, UploadPrescription.class);
                startActivity(intent);
            }
        });

        btnSelectCity.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Success.this, ListDisplay.class);
                startActivity(intent);
            }
        });
        btnNoNetwork.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Success.this, NoNetwork.class);
                startActivity(intent);
            }
        });
        btnSearchMedicine.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Success.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }





}