package mypackage.loginregistration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.view.Gravity;
import android.graphics.PorterDuff;
import Util.Constants;
import Util.Strings;

public class LoginPage extends BaseActivty {
    private Toolbar toolbar;
    private Button btnLogin;
    private EditText editViewEmail;
    private EditText editViewPassword;
    private TextView txtSignUp;
    private TextView txtWelcome;
    private boolean isLogin = false;
    private TextView txtForgotPwd;
    //---
    Button btnClosePopup;
    Button btnCreatePopup;
    private PopupWindow pwindo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        //---

    }

    private void initView() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        editViewEmail = (EditText) findViewById(R.id.editViewEmail);
        editViewPassword = (EditText) findViewById(R.id.editViewPassword);
        txtSignUp = (TextView) findViewById(R.id.txtViewSignUp);
        txtForgotPwd = (TextView) findViewById(R.id.txtForgotPwd);
        final Intent intent = getIntent();
        editViewEmail.getBackground().setColorFilter(getResources().getColor(R.color.cyan), PorterDuff.Mode.SRC_ATOP);
        editViewPassword.getBackground().setColorFilter(getResources().getColor(R.color.cyan), PorterDuff.Mode.SRC_ATOP);
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
                Matcher m = p.matcher(etEmail);
                final boolean b = m.matches();
                Pattern pwd = Pattern.compile(Constants.REGEX_PASSWORD_VALIDATION);
                Matcher m1 = pwd.matcher(etPassword);
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
                        Intent intent = new Intent(LoginPage.this, Success.class);
                        startActivity(intent);
                    }
                });
            }

        });
        //--
        txtForgotPwd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                initiatePopupWindow();
            }
        });

    }

    //-------------

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater

            LayoutInflater inflater = (LayoutInflater) LoginPage.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 900, 700, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnClickListener cancel_button_click_listener = new OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };

}










