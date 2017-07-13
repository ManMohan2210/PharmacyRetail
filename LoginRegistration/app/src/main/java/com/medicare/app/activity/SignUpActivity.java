package com.medicare.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicare.app.Util.ConstantsUtil;
import com.medicare.app.Util.StringsUtil;
import com.medicare.app.Util.UtilityUtil;
import com.medicare.launch.app.R;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SignUpActivity extends BaseActivty {

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
    @Bind(R.id.txtViewTerm)
    TextView mtxtViewTerm;

    @Bind(R.id.ll_input_name)
    TextInputLayout textInputLayoutName;
    @Bind(R.id.ll_input_mob)
    TextInputLayout textInputLayoutMNum;
    @Bind(R.id.ll_input_email)
    TextInputLayout textInputLayoutEmail;
    @Bind(R.id.ll_input_password)
    TextInputLayout textInputLayoutPwd;

    @Bind(R.id.rdb_retailer)
    RadioButton mRBRetailer;

    @Bind(R.id.rdb_customer)
    RadioButton mRBCustomer;
    private static final String TAG = "SignUpActivity";

    @Bind(R.id.radioGroupUserType)
    RadioGroup mRbGroup;

    DatabaseReference databaseMediCare;

    private FirebaseDatabase mFirebaseDatabase;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Firebase usersTimeStampsRef=null;


    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        //initView();
        initListener();
        progressDialog = new ProgressDialog(this);
        databaseMediCare = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //  Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        addEventFirebaseListener();
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
                final String name = mEdtName.getText().toString();
                final String mobNum = mEdtMobNum.getText().toString();
                final String email = mEdtEmail.getText().toString();
                final String password = mEdtPassword.getText().toString();
                Pattern pwd = Pattern.compile(ConstantsUtil.REGEX_PASSWORD_VALIDATION);
                Pattern mob = Pattern.compile(ConstantsUtil.REGEX_MOBILE_VALIDATION);
                mEdtName.setCursorVisible(false);
                mEdtMobNum.setCursorVisible(false);
                mEdtEmail.setCursorVisible(false);

                String userTypeStr ;

                final String uniUserId = SharedPrefManager.getInstance(getApplicationContext()).getUUID();
                String uniUId=null;

                int selectedId = mRbGroup.getCheckedRadioButtonId();

                // find the radio button by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                userTypeStr= (String) radioButton.getText();
                if (mRBCustomer.isSelected()) {

                    uniUId=ConstantsUtil.CUSTOMER+uniUserId;
                } else if (mRBRetailer.isSelected()) {
                   uniUId=ConstantsUtil.RETAILER.concat(uniUserId);
                }
                final String  userTypeString=userTypeStr;
                final String  uniqueUserId=uniUserId;
                if (UtilityUtil.isNullOrEmpty(name)) {
                    textInputLayoutName.setErrorEnabled(true);
                    textInputLayoutName.setError(StringsUtil.ENTER_NAME);
                    return;
                }
                if (UtilityUtil.isNullOrEmpty(mobNum)) {
                    textInputLayoutMNum.setErrorEnabled(true);
                    textInputLayoutMNum.setError(StringsUtil.ENTER_MOBILE);
                    return;
                } else if (Pattern.matches(mob.pattern(),mobNum) == false) {
                    textInputLayoutMNum.setErrorEnabled(true);
                    textInputLayoutMNum.setError(StringsUtil.ENTER_MOBILE);
                    return;
                }
                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    textInputLayoutEmail.setErrorEnabled(true);
                    textInputLayoutEmail.setError(StringsUtil.ENTER_EMAIL);
                    return;
                }

                if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    textInputLayoutPwd.setErrorEnabled(true);
                    textInputLayoutPwd.setError(StringsUtil.PASSWORD_NOT_VALID);
                    return;

                } else if (Pattern.matches(pwd.pattern(), password) == false) {
                    textInputLayoutPwd.setErrorEnabled(true);
                    textInputLayoutPwd.setError(StringsUtil.ENTER_PASSWORD);
                    return;
                }

                if (mChbSignIn.isChecked() == false) {
                    showSnackBar(StringsUtil.CHECK_TNC, null, null);
                    return;
                }

//after auth logs in
                progressDialog.setMessage("Signing up  plz wait...");
                progressDialog.show();

                //create user

                Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                showToast("createUserWithEmail:onComplete:" + task.isSuccessful());
                                if (task.isSuccessful()) {
                                    // String id = databaseMediCare.push().getKey();
                                    // UserTypeModel user = new UserTypeModel(uniqueUserId,name,userTypeString,mobNum,email,password, ServerValue.TIMESTAMP);
                                    //databaseMediCare.child("users").child(user.getUserId()).setValue(user);
                                    String accesstoken = SharedPrefManager.getInstance(getApplicationContext()).getToken();

                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    UserTypeModel user = UserTypeModel.getInastnce();
                                    user.createUser(firebaseUser.getUid(), name, userTypeString, mobNum, email, password,accesstoken);
                                    databaseMediCare.child("users").child(user.getUserId()).setValue(user);
//                                    usersTimeStampsRef = usersTimeStampsRef.child("users");
//                                    usersTimeStampsRef.setValue(ServerValue.TIMESTAMP);


                                }
                                progressDialog.dismiss();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    showToast("Authentication failed." + task.getException());
                                    Log.e("signup fail", String.valueOf(task.getException()));
                                } else {
                                    startActivity(new Intent(SignUpActivity.this, SuccessActivity.class));
                                    finish();
                                }
                            }
                        });

                //  Intent intent = new Intent(SignUpActivity.this, SuccessActivity.class);
                //  startActivity(intent);


            }

          /*  private void addUserType(UserTypeModel userTypeModel) {

                String id = databaseMediCare.push().getKey();


                databaseMediCare.child("user").child(id).setValue(userType);
                //databaseMediCare.child(id).setValue(userType);
                showToast("user type added succesfully!");
            }*/


        });




       /* mtxtViewTerm.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              showSnackBar(StringsUtil.WIP,5);
                                          }
                                      }

        );*/
        mtxtViewTerm.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                showSnackBar(StringsUtil.WIP, "OK", null); //StringsUtil.WIP,"ok",);

                                              /*CoordinatorLayout   coordinatorLayout = (CoordinatorLayout) findViewById(R.id.snackbarCoordinatorLayout);
                                              Snackbar snackbar = Snackbar.make(coordinatorLayout, "Text", Snackbar.LENGTH_LONG);
                                              View view = snackbar.getView();
                                              CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
                                              params.gravity = Gravity.TOP;
                                              view.setLayoutParams(params);
                                              snackbar.show();*/

                                            }
                                        }

        );


        mTvLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginPageActivity.class);
                startActivity(intent);
            }
        });

        mEdtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtName.setCursorVisible(true);
                textInputLayoutName.setError(null);
            }
        });
        mEdtMobNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtMobNum.setCursorVisible(true);
                textInputLayoutMNum.setError(null);
            }
        });
        mEdtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtEmail.setCursorVisible(true);
                textInputLayoutEmail.setError(null);
            }
        });
        mEdtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtPassword.setCursorVisible(true);
                textInputLayoutPwd.setError(null);
            }
        });
    }
    private void addEventFirebaseListener() {
        //Progressing

        databaseMediCare.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SignUpActivity Page") // TODO: Define a title for the content shown.
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
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}







