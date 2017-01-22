package mypackage.loginregistration;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import  android.view.View.OnClickListener;
public class PasswordChange extends BaseActivty {


    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change);
        Intent intent = getIntent();
       // btnReset = (Button) findViewById(R.id.btn_reset);

        if (intent != null) {
            String selectedValue = intent.getStringExtra("selection");
            if (selectedValue.equals("Via OTP")) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                FragmentOTP fragmentOTP = new FragmentOTP();
                fragmentTransaction.add(R.id.container, fragmentOTP, "fragOTP");
                fragmentTransaction.commit();
            } else if (selectedValue.equals("Via E-mail")) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();

                FragmentEmail fragmentEmail = new FragmentEmail();
                fragmentTransaction.add(R.id.container, fragmentEmail, "fragEmail");
                fragmentTransaction.commit();
            } else {
                showToast("Please select any option.");
            }

        }

    }







}