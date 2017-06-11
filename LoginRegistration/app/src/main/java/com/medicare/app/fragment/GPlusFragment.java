package com.medicare.app.fragment;

import android.support.v4.app.Fragment;

//import static com.medicare.app.loginregistration.R.id.postPhotoButton;
//import static com.medicare.app.loginregistration.R.id.postStatusUpdateButton;


public class GPlusFragment extends Fragment {
        //implements GoogleApiClient.OnConnectionFailedListener {
    /*

    private static final String TAG = "GPlusFragent";
    private int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private Button signInButton;
    private ImageButton mGoogle;
    private Button signOutButton;
    private Button disconnectButton;
    private LinearLayout signOutView;
    private TextView mStatusTextView;
    //private ProgressDialog mProgressDialog;
    private ImageView imgProfilePic;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
      //  mProgressDialog = new ProgressDialog(getActivity());
        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() *//* FragmentActivity *//*, this *//* OnConnectionFailedListener *//*)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }


    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            //handleSignInResult(result);
        } else {
           *//* // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);*//*
                }
            });
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gplus, parent, false);

        signInButton = (Button) v.findViewById(R.id.sign_in_button);
       // ImageButton mGoogle = (ImageButton) v.findViewById(R.id.btn_custom_google);
        //signOutButton = (Button) v.findViewById(R.id.sign_out_button);
        //imgProfilePic = (ImageView) v.findViewById(R.id.img_profile_pic);

        //mStatusTextView = (TextView) v.findViewById(R.id.status);
        //Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.user_default);
        //imgProfilePic.setImageBitmap(ImageHelperUtil.getRoundedCornerBitmap(getContext(), icon, 200, 200, 200, false, false, false, false));
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

        });
       *//* mGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInButton.performClick();
                //TO DO put login method here eg : authorizefacebook();
            }
        });*//*

       *//* mGoogle.post(new Runnable(){
            @Override
            public void run() {
                signInButton.performClick();
            }
        });
*//*
*//*

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                updateUI(false);
                            }
                        });
            }

        });
*//*

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
          //  mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //Similarly you can get the email and photourl using acct.getEmail() and  acct.getPhotoUrl()
*//*

            if (acct.getPhotoUrl() != null)
                new LoadProfileImage(imgProfilePic).execute(acct.getPhotoUrl().toString());
*//*

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }


    private void updateUI(boolean signedIn) {
       *//* if (signedIn) {
            signInButton.setVisibility(View.GONE);
          //  signOutButton.setVisibility(View.VISIBLE);
        } *//**//*else {
            mStatusTextView.setText(R.string.signed_out);
           *//**//* Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.user_default);
            imgProfilePic.setImageBitmap(ImageHelperUtil.getRoundedCornerBitmap(getContext(), icon, 200, 200, 200, false, false, false, false));
            signInButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);*//**//*
        }*//*
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

 *//*   private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }*//*

    *//*private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }

    }*//*


    *//**
     * Background Async task to load user profile picture from url
     *//*
   *//* private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... uri) {
            String url = uri[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
*//*
        *//*protected void onPostExecute(Bitmap result) {

            if (result != null) {


                Bitmap resized = Bitmap.createScaledBitmap(result, 200, 200, true);
                bmImage.setImageBitmap(ImageHelperUtil.getRoundedCornerBitmap(getContext(), resized, 250, 200, 200, false, false, false, false));

            }
        }*//*
    //}*/
}