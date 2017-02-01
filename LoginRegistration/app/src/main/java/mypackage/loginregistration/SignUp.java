package mypackage.loginregistration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.regex.Pattern;

import Util.Constants;
import Util.Strings;
import Util.Utility;
import butterknife.Bind;
import butterknife.ButterKnife;

import static mypackage.loginregistration.R.id.btnSignUp;
import static mypackage.loginregistration.R.id.edtName;


public class SignUp extends BaseActivty {

    // private Button btnLogin;
    //  public final int CODE_SIGNUP = 1111;
    // private EditText edtName;
    //private EditText edtEmail;
    //private EditText edtPassword;
    //private Button btnSignUp;
    // private TextView txtViewSignUp;
    // private EditText edtMobNum;
    // private TextView txtViewTerm;
    //private CheckBox chbSignIn;
    // private CheckBox checkBoxShowPwd;
    @Bind(R.id.edtName)
    EditText mEdtName;
    @Bind(R.id.edtMobNum)
    EditText mEdtMobNum;
    @Bind(R.id.edtEmail)
    EditText mEdtEmail;
    @Bind(R.id.edtNewPassword)
    EditText mEdtPassword;
    @Bind(R.id.chbSignIn)
    CheckBox mChbSignIn;
    @Bind(R.id.tv_login)
    TextView mTvLogin;
    @Bind(R.id.btnSignUp)
    Button mBtnSignUp;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        //initView();
        initListener();
    }

   /* private void initView() {
        edtName = (EditText) findViewById(edtName);
        edtPassword = (EditText) findViewById(R.id.edtNewPassword);
        btnSignUp = (Button) findViewById(btnSignUp);
        txtViewSignUp = (TextView) findViewById(R.id.txtViewSignUp);
        txtViewTerm = (TextView) findViewById(R.id.txtViewTerm);
        edtEmail = (EditText) findViewById(edtEmail);
        edtMobNum = (EditText) findViewById(edtMobNum);
        chbSignIn = (CheckBox) findViewById(chbSignIn) ;
       // checkBoxShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);
        txtViewTerm.setPaintFlags(txtViewTerm.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }*/

    private void initListener() {

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = mEdtName.getText().toString();
                String mobNum = mEdtMobNum.getText().toString();
                String email = mEdtEmail.getText().toString();
                String password = mEdtPassword.getText().toString();
                Pattern pwd = Pattern.compile(Constants.REGEX_PASSWORD_VALIDATION);
                Pattern mob = Pattern.compile(Constants.REGEX_MOBILE_VALIDATION);

                if (Utility.isNullOrEmpty(name)) {
                    mEdtName.setError(Strings.ENTER_NAME);
                    return;
                }else {
                    mEdtName.setError(null);
                }
                if (Utility.isNullOrEmpty(mobNum)) {
                    mEdtMobNum.setError(Strings.ENTER_MOBILE);
                    return;
                } else if (Pattern.matches(mob.pattern(),mobNum) == false) {
                    mEdtMobNum.setError(Strings.ENTER_MOBILE);
                    return;
                }else {
                    mEdtMobNum.setError(null);

                }
                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEdtEmail.setError(Strings.ENTER_EMAIL);
                    return;
                } else {
                    mEdtEmail.setError(null);
                }

                if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    mEdtPassword.setError(Strings.PASSWORD_NOT_VALID);
                    return;

                } else if (Pattern.matches(pwd.pattern(), password) == false) {
                    mEdtPassword.setError(Strings.ENTER_PASSWORD);
                    return;

                } else {
                    mEdtPassword.setError(null);
                }

                if (mChbSignIn.isChecked() == false) {
                    mChbSignIn.setError(Strings.CHECK_TNC);
                    return;
                }
                Intent intent = new Intent(SignUp.this, Success.class);
                startActivity(intent);


            }


        });


              /*  if (etEmail == null) {
                    showToast(Strings.ENTER_EMAIL);
                    return;
                } else if (Utility.isNullOrEmpty(etEmail)) {
                    showToast(Strings.ENTER_EMAIL);
                    return;
                } else if (Pattern.matches(p.pattern(), etEmail.toString()) == false) {
                    showToast(Strings.ENTER_EMAIL);
                    return;
                }*/


//                if (etPassword == null) {
//                    showToast(Strings.ENTER_PASSWORD);
//                    return;
//                } else if (Utility.isNullOrEmpty(etPassword)) {
//                    showToast(Strings.ENTER_PASSWORD);
//                    return;
//                }  else if (Pattern.matches(pwd.pattern(), etPassword.toString()) == false) {
//                    showToast(Strings.ENTER_PASSWORD);
//                    return;
//                }

        mChbSignIn.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              showToast(Strings.WIP);
                                          }
                                      }

        );

      /*  checkBoxShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });*/
        mTvLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LoginPage.class);
                startActivity(intent);
            }
        });

    }


    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SignUp Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


}







