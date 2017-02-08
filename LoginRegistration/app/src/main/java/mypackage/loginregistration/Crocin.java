package mypackage.loginregistration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by satveer on 25-01-2017.
 */
public class  Crocin extends BaseActivty{

    private TextView textViewDetails;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crocin);
        Intent intent = getIntent();
        textViewDetails = (TextView) findViewById(R.id.tv_details);
        imgView = (ImageView) findViewById(R.id.imageViewPic);


    }



}
