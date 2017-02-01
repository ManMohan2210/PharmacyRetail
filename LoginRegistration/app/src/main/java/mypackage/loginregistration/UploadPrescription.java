package mypackage.loginregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;

public class UploadPrescription extends BaseActivty {

   // private TextView textView;
    private Button btnUpload;
    private TextView tvCamera;
    private TextView tvExisting;
    private TextView tvGallery;
    private TextView tvChoose;
    private ImageView imgCamera;
    private ImageView imgGallery;
    private ImageView imgExisting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_periscription);
        Intent intent = getIntent();
        btnUpload = (Button)findViewById(R.id.btn_continue);
         tvCamera =  (TextView) findViewById(R.id.tv_camera);
         tvExisting=(TextView) findViewById(R.id.tv_existing);
         tvGallery=(TextView) findViewById(R.id.tv_gallery);
         tvChoose=(TextView) findViewById(R.id.choseOpt);
         imgCamera=(ImageView) findViewById(R.id.imageViewCamera);
         imgGallery=(ImageView) findViewById(R.id.imageViewGallery);
         imgExisting=(ImageView) findViewById(R.id.imageViewExisting);
    }




}