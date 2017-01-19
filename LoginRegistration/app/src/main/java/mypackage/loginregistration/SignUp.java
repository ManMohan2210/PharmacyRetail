package mypackage.loginregistration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Paint;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Util.Constants;
import Util.Strings;
import Util.Utility;


public class SignUp extends BaseActivty {

    private Button btnLogin;
    public final int CODE_SIGNUP = 1111;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSignUp;
    private TextView txtViewSignUp;
    private EditText edtMobNum;
    private TextView txtViewTerm;
    private CheckBox chbSignIn;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final Intent intent = getIntent();
        initView();
        initListener();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        edtName = (EditText) findViewById(R.id.edtName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtViewSignUp = (TextView) findViewById(R.id.txtViewSignUp);
        txtViewTerm = (TextView) findViewById(R.id.txtViewTerm);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtMobNum = (EditText) findViewById(R.id.edtMobNum);
        chbSignIn = (CheckBox) findViewById(R.id.chbSignIn) ;
        txtViewTerm.setPaintFlags(txtViewTerm.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void initListener() {
        txtViewSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LoginPage.class);
                startActivityForResult(intent, CODE_SIGNUP);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String etEmail = edtEmail.getText().toString();
                String etPassword = edtPassword.getText().toString();
                String edtMobText = edtMobNum.getText().toString();
                String edtNameText= edtName.getText().toString();
                Pattern p = Pattern.compile(Constants.REGEX_EMAIL_VALIDATION);
                Matcher m = p.matcher(etEmail);
                final boolean b = m.matches();
                Pattern pwd = Pattern.compile(Constants.REGEX_PASSWORD_VALIDATION);
                Matcher m1 = pwd.matcher(etPassword);
                Pattern mob = Pattern.compile(Constants.REGEX_MOBILE_VALIDATION);
                if (edtNameText == null) {
                    showToast(Strings.ENTER_NAME);
                    return;
                } else if (Utility.isNullOrEmpty(edtNameText)) {
                    showToast(Strings.ENTER_NAME);
                    return;
                }
                if (etEmail == null) {
                    showToast(Strings.ENTER_EMAIL);
                    return;
                } else if (Utility.isNullOrEmpty(etEmail)) {
                    showToast(Strings.ENTER_EMAIL);
                    return;
                } else if (Pattern.matches(p.pattern(), etEmail.toString()) == false) {
                    showToast(Strings.ENTER_EMAIL);
                    return;
                }

                if (edtMobText == null) {
                    showToast(Strings.ENTER_MOBILE);
                    return;
                } else if (Utility.isNullOrEmpty(edtMobText)) {
                    showToast(Strings.ENTER_MOBILE);
                    return;
                } else if(Pattern.matches(mob.pattern(),edtMobText.toString())==false)
                {
                    showToast(Strings.ENTER_MOBILE);
                    return;
                }
                if (etPassword == null) {
                    showToast(Strings.ENTER_PASSWORD);
                    return;
                } else if (Utility.isNullOrEmpty(etPassword)) {
                    showToast(Strings.ENTER_PASSWORD);
                    return;
                }  else if (Pattern.matches(pwd.pattern(), etPassword.toString()) == false) {
                    showToast(Strings.ENTER_PASSWORD);
                    return;
                }
                if (chbSignIn.isChecked()==false)
                {
                    showToast(Strings.CHECK_TNC);
                    return;
                }
                Intent intent = new Intent(SignUp.this, Success.class);
                startActivity(intent);


            }


        });
        txtViewTerm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                showToast(Strings.WIP);
                                           }
        }

        );



    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}







