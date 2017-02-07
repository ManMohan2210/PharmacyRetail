package com.pharma.medicare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.regex.Pattern;

import com.pharma.medicare.Util.Constants;
import com.pharma.medicare.Util.Strings;
import com.pharma.medicare.Util.Utility;
import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginPage extends BaseActivty {
    private Toolbar toolbar;
    //@Bind(R.id.btn_login)
    // private Button btnLogin;
    //private EditText editViewEmail;
    //private EditText editViewPassword;
    //private TextView txtSignUp;
    private TextView txtWelcome;
    private boolean isLogin = false;
    //private TextView txtForgotPwd;
    private Button btnClosePopup;
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

    @Bind(R.id.ll_input_email)
    TextInputLayout textInputLayoutEmail;

    @Bind(R.id.ll_input_password)
    TextInputLayout textInputLayoutPassword;

    private static final int REQUEST_SIGNUP = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //  initView();
        initListener();
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

                if (!isValidationClear()) return;

                Utility.hideKeyboardFrom(LoginPage.this);

                Intent intent = new Intent(LoginPage.this, HomeScreen.class);
                startActivity(intent);

            }
        });

        mSignupLink.setOnClickListener(new OnClickListener() {

            public void onBackPressed() {
                // Disable going back to the MainActivity
                moveTaskToBack(true);
            }

            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, SignUp.class);
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

        mEmailText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmailText.setCursorVisible(true);
                textInputLayoutEmail.setError(null);
            }
        });

        mPasswordText.setOnClickListener(new OnClickListener() {
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
        Pattern pwd = Pattern.compile(Constants.REGEX_PASSWORD_VALIDATION);

        mEmailText.setCursorVisible(false);
        mPasswordText.setCursorVisible(false);


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError(Strings.ENTER_EMAIL);
            return false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            textInputLayoutPassword.setError(Strings.PASSWORD_NOT_VALID);
            return false;

        } else if (Pattern.matches(pwd.pattern(), password) == false) {
            textInputLayoutPassword.setError(Strings.ENTER_PASSWORD);
            return false;

        }
        return true;
    }

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater

            LayoutInflater inflater = (LayoutInflater) LoginPage.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View popupView = inflater.inflate(R.layout.popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, 800, 600, true);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            btnClosePopup = (Button) popupView.findViewById(R.id.btn_close_popup);
            RadioButton rdbEmail = (RadioButton) popupView.findViewById(R.id.rdbEmail);
            RadioButton rdbOtp = (RadioButton) popupView.findViewById(R.id.rdbOtp);
            final RadioGroup radioGroup = (RadioGroup) popupView.findViewById(R.id.radioGroup);
            ((Button) popupView.findViewById(R.id.btn_close_popup))
                    .setOnClickListener(new OnClickListener() {


                        // @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                        public void onClick(View arg0) {
                            if (radioGroup.getCheckedRadioButtonId() != -1) {
                                int id = radioGroup.getCheckedRadioButtonId();
                                View radioButton = radioGroup.findViewById(id);
                                int radioId = radioGroup.indexOfChild(radioButton);
                                RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                                selection = (String) btn.getText();

                            }

                            popupWindow.dismiss();
                            Intent intent = new Intent(getBaseContext(), PasswordChange.class);
                            intent.putExtra("selection", selection);
                            startActivity(intent);

                        }

                    });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}









