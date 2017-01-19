package mypackage.loginregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

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