package com.medicare.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.medicare.app.Util.ConstantsUtil;
import com.medicare.app.Util.StringsUtil;
import com.pharma.medicare.app.R;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginPageActivity extends BaseActivty {
    private Toolbar toolbar;

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
    private static final String TAG = "LoginActivity";
    @Bind(R.id.ll_input_email)
    TextInputLayout textInputLayoutEmail;

    @Bind(R.id.ll_input_password)
    TextInputLayout textInputLayoutPassword;
    Firebase mRef;
    private static final int REQUEST_SIGNUP = 0;
    private static final int RC_SIGN_IN = 123;
    private GoogleApiClient mGoogleApiClient;
    // Set the dimensions of the sign-in button.
    private SignInButton googleSignInButton; //= (SignInButton) findViewById(R.id.btn_google_signin);
    FirebaseAuth.AuthStateListener mAuthListener;
    LoginButton loginButton;
    CallbackManager mCallbackManager;

//signInButton.setSize(SignInButton.SIZE_STANDARD);

    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    public UserTypeModel user;



    //Add YOUR Firebase Reference URL instead of the following URL



    public void onCreate(Bundle savedInstanceState) {

        //Get Firebase auth instance

        //Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        Firebase mRef=new Firebase("https://medicare-169109.firebaseapp.com/__/auth/handler");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        layout_MainMenu = (FrameLayout) findViewById(R.id.mainmenu);
       layout_MainMenu.getForeground().setAlpha(0);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);

        //  initView();
        initListener();

// ...
        FirebaseUser mUser = firebaseAuth.getCurrentUser();
        if (mUser != null) {
            // User is signed in
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            String uid = firebaseAuth.getCurrentUser().getUid();

            String image= firebaseAuth.getCurrentUser().getPhotoUrl() != null ? firebaseAuth.getCurrentUser().getPhotoUrl().toString() : null;

            //  String image=firebaseAuth.getCurrentUser().getPhotoUrl().toString();

            intent.putExtra("user_id", uid);
            if(image!=null || image!=""){
                intent.putExtra("profile_picture",image);
            }
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

        //FaceBook
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
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



    private void signIn() {
        progressDialog.setMessage("Logging in by Gmail");
        progressDialog.show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progressDialog.dismiss();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d("Gogle","Status code "+result.getStatus().getStatusCode());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    protected void setUpUser() {
        user = new UserTypeModel();
        user.setEmail(mEmailText.getText().toString());
        user.setPassword(mPasswordText.getText().toString());
    }




    private void signInWithFacebook(AccessToken token) {
        Log.d(TAG, "signInWithFacebook:" + token);
progressDialog.setMessage("sigin with fb");
        progressDialog.dismiss();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginPageActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            String uid=task.getResult().getUser().getUid();
                            String name=task.getResult().getUser().getDisplayName();
                            String email=task.getResult().getUser().getEmail();
                            String image=task.getResult().getUser().getPhotoUrl().toString();

                            //Create a new User and Save it in Firebase database
                            UserTypeModel user = new UserTypeModel(uid,name,email,null);

                            mRef.child(uid).setValue(user);

                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent.putExtra("user_id",uid);
                            intent.putExtra("profile_picture",image);
                            startActivity(intent);
                            finish();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    public void onSuccess (LoginResult loginResult){
        //Log.d(TAG, "facebook:onSuccess:" + loginResult);
        handleFacebookAccessToken(loginResult.getAccessToken());
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                              information information Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginPageActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //  updateUI(null);
                        }

                        // ...
                    }
                });
    }


    public void onCancel() {
          Log.d(TAG, "facebook:onCancel");
        // ...
    }


    public void onError(FacebookException error) {
            Log.d(TAG, "facebook:onError", error);
        // ...
    }




private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
@Override
public void onComplete(@NonNull Task<AuthResult> task){
        if(task.isSuccessful()){
        // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
        FirebaseUser user=firebaseAuth.getCurrentUser();
//                            updateUI(user);
            Intent intent = new Intent(LoginPageActivity.this, ProfileActivity.class);
            startActivity(intent);
        }else{
        //showToast("Authentication failed.");
        // If sign in fails, display a message to the user.
                          Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
        }

        // ...
        }
        });
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


        SignInButton googleSignInButton = (SignInButton) findViewById(R.id.btn_google_signin);

// google integration with firebase

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))//google_web_client_id))
                .requestEmail()
                .build();
//        googleSignInButton.setScopes(gso.getScopeArray());
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                showToast("Some error! ");
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signIn();
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginPageActivity.this, ProfileActivity.class));
                }
            }
        };
        if (firebaseAuth.getCurrentUser() != null) {
            //directly start the profile activity here
            finish();
            Intent intent = new Intent(LoginPageActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
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
                            Intent intent = new Intent(LoginPageActivity.this, SuccessActivity.class);
                            startActivity(intent);
                        } else {
                            showToast("login failed!");
                        }
                    }
                });


                // Intent intent = new Intent(LoginPageActivity.this, HomeScreenActivity.class);
                //startActivity(intent);

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
            layout_MainMenu.getForeground().setAlpha(220);
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
                            layout_MainMenu.getForeground().setAlpha(0);
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
                            layout_MainMenu.getForeground().setAlpha(0);

                        }

                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}









