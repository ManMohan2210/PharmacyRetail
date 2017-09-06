package com.medicare.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medicare.app.Util.ConstantsUtil;
import com.medicare.app.Util.StringsUtil;
import com.medicare.launch.app.R;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginPageActivity extends BaseActivty {
    private Toolbar toolbar;

    private TextView txtWelcome;
    private String selection;
    @Bind(R.id.txtForgotPwd)
    TextView txtForgotPwd;
    @Bind(R.id.editViewEmail)
    EditText mEmailText;
    @Bind(R.id.editViewPassword)
    EditText mPasswordText;
    @Bind(R.id.btn_login)
    Button mLoginButton;
    @Bind(R.id.txtViewSignUp)
    TextView mSignupLink;
    @Bind(R.id.loginLinearLayout)
    LinearLayout loginLinearLayout;

    @Bind(R.id.mainmenu)
    FrameLayout layout_MainMenu;
    private static final String TAG = "LoginActivity";
    @Bind(R.id.ll_input_email)
    TextInputLayout textInputLayoutEmail;

    @Bind(R.id.ll_input_password)
    TextInputLayout textInputLayoutPassword;
    Firebase mRef;
    private static final int REQUEST_SIGNUP = 0;
    private static final int RC_SIGN_IN = 123;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReference;


    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    public UserTypeModel user;


    public void onCreate(Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        Firebase mRef = new Firebase("https://medicare-169109.firebaseapp.com/__/auth/handler");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        layout_MainMenu = (FrameLayout) findViewById(R.id.mainmenu);
        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        initListener();


        FirebaseUser mUser = firebaseAuth.getCurrentUser();
        if (mUser != null) {
            // User is signed in
            Intent intent = new Intent(getApplicationContext(), HomeScreenSearchActivity.class);
            String uid = firebaseAuth.getCurrentUser().getUid();

            startActivity(intent);
            finish();
            Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
        }
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void setUpUser() {
        user = UserTypeModel.getInastnce();
        user.setEmail(mEmailText.getText().toString());
        user.setPassword(mPasswordText.getText().toString());
    }


    private void initListener() {


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpUser();
                String email = mEmailText.getText().toString();
                String password = mPasswordText.getText().toString();

                // if (!isValidationClear()) return;
                //  UtilityUtil.hideKeyboardFrom(LoginPageActivity.this);

                //after auth logs in
                progressDialog.setMessage("Logging in plz wait...");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginPageActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //start home activity
                            finish();
                            Intent intent = new Intent(LoginPageActivity.this, DrawerMain.class);
                            startActivity(intent);

                        } else {
                            showToast("login failed!");
                        }
                    }
                });


            }
            public void onBackPressed() {
                // Disable going back to the MainActivity
                moveTaskToBack(true);
            }
        });

        mSignupLink.setOnClickListener(new OnClickListener() {

            public void onBackPressed() {
                // Disable going back to the MainActivity
                moveTaskToBack(true);
            }

            public void onClick(View v) {
                Intent intent = new Intent(LoginPageActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        txtForgotPwd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPageActivity.this, PasswordChangeActivity.class);
                startActivity(intent);
            }
        });

        mEmailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmailText.setCursorVisible(true);
                textInputLayoutEmail.setError(null);
            }
        });

        mPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPasswordText.setCursorVisible(true);
                textInputLayoutPassword.setError(null);
            }
        });

    }

    private boolean isValidationClear() {
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        Pattern pwd = Pattern.compile(ConstantsUtil.REGEX_PASSWORD_VALIDATION);

        mEmailText.setCursorVisible(false);
        mPasswordText.setCursorVisible(false);


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError(StringsUtil.ENTER_EMAIL);
            return false;
        }

        if (password.isEmpty() /*|| password.length() < 4 || password.length() > 10*/) {
            textInputLayoutPassword.setError(StringsUtil.ENTER_PASSWORD);
            return false;

        } else if (Pattern.matches(pwd.pattern(), password) == false) {
            textInputLayoutPassword.setError(StringsUtil.ENTER_PASSWORD);
            return false;

        }
        return true;

    }


}









