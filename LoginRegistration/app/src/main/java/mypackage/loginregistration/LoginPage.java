package mypackage.loginregistration;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.regex.Pattern;

import Util.Constants;
import Util.Strings;
import butterknife.Bind;

import static mypackage.loginregistration.R.layout.activity_login;

;

public class LoginPage extends BaseActivty {
    private Toolbar toolbar;
    //@Bind(R.id.btn_login)
    private Button btnLogin;
    private EditText editViewEmail;
    private EditText editViewPassword;
    private TextView txtSignUp;
    private TextView txtWelcome;
    private boolean isLogin = false;
    private TextView txtForgotPwd;
    private Button btnClosePopup;
    private PopupWindow pwindo;
    private RadioButton rdbEmail;
    private RadioButton rdbOtp;
    private RadioGroup radioGroup;
    private String selection;
    private CheckBox checkBoxShowPwd;
    private ImageView imageViewFb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        initView();
        initListener();

    }

    private void initView() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        editViewEmail = (EditText) findViewById(R.id.editViewEmail);
        editViewPassword = (EditText) findViewById(R.id.editViewPassword);
        txtSignUp = (TextView) findViewById(R.id.txtViewSignUp);
        txtForgotPwd = (TextView) findViewById(R.id.txtForgotPwd);
        checkBoxShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);
        final Intent intent = getIntent();
        editViewEmail.getBackground().setColorFilter(getResources().getColor(R.color.cyan), PorterDuff.Mode.SRC_ATOP);
        editViewPassword.getBackground().setColorFilter(getResources().getColor(R.color.cyan), PorterDuff.Mode.SRC_ATOP);
        imageViewFb = (ImageView) findViewById(R.id.imgViewFacebook);
    }

    private void initListener() {
        txtSignUp.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, SignUp.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Editable etPassword = editViewPassword.getText();
                Editable etEmail = editViewEmail.getText();
                Pattern p = Pattern.compile(Constants.REGEX_EMAIL_VALIDATION);
                Pattern pwd = Pattern.compile(Constants.REGEX_PASSWORD_VALIDATION);
                if (etEmail != null && etEmail.equals("")) {
                    showToast(Strings.ENTER_EMAIL);
                    return;
                } else if (etEmail == null) {
                    showToast(Strings.ENTER_EMAIL);
                    return;
                } else if (Pattern.matches(p.pattern(), etEmail.toString()) == false) {
                    showToast(Strings.ENTER_EMAIL);
                    return;
                }
                if (etPassword != null && etPassword.equals("")) {
                    showToast(Strings.ENTER_PASSWORD);
                    return;
                } else if (etPassword == null) {
                    showToast(Strings.ENTER_PASSWORD);
                    return;
                } else if (Pattern.matches(pwd.pattern(), etPassword.toString()) == false) {
                    showToast(Strings.ENTER_PASSWORD);
                    return;
                }


                btnLogin.setOnClickListener(new View.OnClickListener(
                ) {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginPage.this, HomeScreen.class);
                        startActivity(intent);
                    }
                });
            }

        });

        txtForgotPwd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                initiatePopupWindow();
            }
        });


        checkBoxShowPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    editViewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editViewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        imageViewFb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setClassName("com.google.android.apps.plus",
                        "com.google.android.apps.plus.phone.UrlGatewayActivity");
                intent.putExtra("customAppUri", "FAN_PAGE_ID");
                startActivity(intent);
            }
        });
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









