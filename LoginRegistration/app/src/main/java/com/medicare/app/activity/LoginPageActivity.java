package com.medicare.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.medicare.app.R;
import com.medicare.app.Util.ConstantsUtil;
import com.medicare.app.Util.StringsUtil;
import com.medicare.app.fragment.FacebookFragment;
import com.medicare.app.fragment.GPlusFragment;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;



public class LoginPageActivity extends BaseActivty {
    private Toolbar toolbar;

    //@Bind(R.id.btn_login)
    // private Button btnLogin;
    //private EditText editViewEmail;
    //private EditText editViewPassword;
    //private TextView txtSignUp;
    private TextView txtWelcome;
    private boolean isLogin = false;
    //private TextView txtForgotPwd;
    private Button btnOkPopup;
    private PopupWindow pwindo;
    private RadioButton rdbEmail;
    private RadioButton rdbOtp;
    private RadioGroup radioGroup;
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

    @Bind(R.id.ll_input_email)
    TextInputLayout textInputLayoutEmail;

    @Bind(R.id.ll_input_password)
    TextInputLayout textInputLayoutPassword;

    private static final int REQUEST_SIGNUP = 0;

    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    public void onCreate(Bundle savedInstanceState) {

        //Get Firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        layout_MainMenu = (FrameLayout) findViewById( R.id.mainmenu);
        layout_MainMenu.getForeground().setAlpha( 0);
        ButterKnife.bind(this);

          progressDialog=new ProgressDialog(this);

        //  initView();
        initListener();
        if(firebaseAuth.getCurrentUser() !=null)
        {
            //directly start the profile activity here
            finish();
            Intent intent = new Intent(LoginPageActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new FacebookFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        FragmentManager fmg = getSupportFragmentManager();
        Fragment fragmentg = fmg.findFragmentById(R.id.google_fragment_container);


        if (fragmentg == null) {
            fragmentg = new GPlusFragment();
            fmg.beginTransaction()
                    .add(R.id.google_fragment_container, fragmentg)
                    .commit();


        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }


    public void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
//        showToast("Login Failed!");

        mLoginButton.setEnabled(true);
    }


    private void initListener() {
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailText.getText().toString();
                String password = mPasswordText.getText().toString();

               // if (!isValidationClear()) return;
              //  UtilityUtil.hideKeyboardFrom(LoginPageActivity.this);

                //after auth logs in
                progressDialog.setMessage("Logging in plz wait...");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginPageActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start home activity
                            finish();
                            Intent intent = new Intent(LoginPageActivity.this, SuccessActivity.class);
                            startActivity(intent);
                        }else{
                           showToast("login failed!");
                        }
                    }
                });


               // Intent intent = new Intent(LoginPageActivity.this, HomeScreenActivity.class);
                //startActivity(intent);

            }
        });

        mSignupLink.setOnClickListener(new View.OnClickListener() {

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
                //loginLinearLayout.setAlpha(100);
                initiatePopupWindow();

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

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater

            LayoutInflater inflater = (LayoutInflater) LoginPageActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout_MainMenu.getForeground().setAlpha( 220);
            View popupView = inflater.inflate(R.layout.popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, 800, 600, true);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            btnOkPopup = (Button) popupView.findViewById(R.id.btn_ok);
            RadioButton rdbEmail = (RadioButton) popupView.findViewById(R.id.rdbEmail);
            RadioButton rdbOtp = (RadioButton) popupView.findViewById(R.id.rdbOtp);
            final RadioGroup radioGroup = (RadioGroup) popupView.findViewById(R.id.radioGroup);
            ((Button) popupView.findViewById(R.id.btn_ok))
                    .setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            if (radioGroup.getCheckedRadioButtonId() != -1) {
                                int id = radioGroup.getCheckedRadioButtonId();
                                View radioButton = radioGroup.findViewById(id);
                                int radioId = radioGroup.indexOfChild(radioButton);
                                RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                                selection = (String) btn.getText();

                            }

                            popupWindow.dismiss();
                            layout_MainMenu.getForeground().setAlpha( 0);
                            Intent intent = new Intent(getBaseContext(), PasswordChangeActivity.class);
                            intent.putExtra("selection", selection);
                            startActivity(intent);

                        }

                    });
            ((Button) popupView.findViewById(R.id.btn_close_popup))
                    .setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            popupWindow.dismiss();
                            finish();
                            layout_MainMenu.getForeground().setAlpha( 0);

                        }

                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}









