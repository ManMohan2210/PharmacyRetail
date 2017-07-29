package com.medicare.app.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.medicare.app.Util.ConstantsUtil;
import com.medicare.app.Util.StringsUtil;
import com.medicare.app.Util.UtilityUtil;
import com.medicare.launch.app.R;

import java.util.regex.Pattern;

public class PasswordChangeActivity extends BaseActivty {

    private Button btnEmail;
    private EditText edtRgEmail;
    private TextInputLayout textInputLayoutRegEmail;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        btnEmail = (Button) findViewById(R.id.btn_email);
        edtRgEmail = (EditText) findViewById(R.id.edtRegEmail);
        textInputLayoutRegEmail = (TextInputLayout) findViewById(R.id.ll_input_reg_email);

        auth = FirebaseAuth.getInstance();
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtRgEmail.setCursorVisible(false);
                String etEmail = edtRgEmail.getText().toString();
                Pattern p = Pattern.compile(ConstantsUtil.REGEX_EMAIL_VALIDATION);

                if (etEmail == null) {
                    textInputLayoutRegEmail.setErrorEnabled(true);
                    textInputLayoutRegEmail.setError(StringsUtil.ENTER_EMAIL);
                    return;
                } else if (UtilityUtil.isNullOrEmpty(etEmail)) {
                    textInputLayoutRegEmail.setErrorEnabled(true);
                    textInputLayoutRegEmail.setError(StringsUtil.ENTER_EMAIL);
                    return;
                } else if (Pattern.matches(p.pattern(), etEmail.toString()) == false) {
                    textInputLayoutRegEmail.setErrorEnabled(true);
                    textInputLayoutRegEmail.setError(StringsUtil.ENTER_EMAIL);
                    return;
                }


                auth.sendPasswordResetEmail(etEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showToast("We have send  password reset instruction on your registed mail id");
                                } else {
                                    showToast("Failed to send reset mail!");
                                }


                            }
                        });

            }
        });
        edtRgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtRgEmail.setCursorVisible(true);
                textInputLayoutRegEmail.setError(null);
            }
        });


    }
}






