package com.medicare.app.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.medicare.app.Util.ConstantsUtil;
import com.medicare.app.Util.StringsUtil;
import com.medicare.app.Util.UtilityUtil;
import com.medicare.app.activity.BaseActivty;
import com.pharma.medicare.app.R;

import java.util.regex.Pattern;

/**
 * Created by satveer on 14-01-2017.
 */

public class EmailFragment extends Fragment {

private Button btnEmail;
    private EditText edtRgEmail;
    private TextInputLayout textInputLayoutRegEmail;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    BaseActivty baseActivity = (BaseActivty) getActivity();
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_email, container, false);
        btnEmail = (Button) view.findViewById(R.id.btn_email);
        edtRgEmail = (EditText) view.findViewById(R.id.edtRegEmail);
        textInputLayoutRegEmail=(TextInputLayout) view.findViewById(R.id.ll_input_reg_email);

        auth = FirebaseAuth.getInstance();
            btnEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtRgEmail.setCursorVisible(false);
                            String etEmail = edtRgEmail.getText().toString();
                            Pattern p = Pattern.compile(ConstantsUtil.REGEX_EMAIL_VALIDATION);

                            if (etEmail == null) {
                                textInputLayoutRegEmail.setErrorEnabled(true);
                                textInputLayoutRegEmail.setError(StringsUtil.ENTER_EMAIL);
                                return;
                            } else if (UtilityUtil.isNullOrEmpty(etEmail)) {
                                textInputLayoutRegEmail.setErrorEnabled(true);
                                textInputLayoutRegEmail.setError(StringsUtil.ENTER_EMAIL);
                                return;
                            } else if (Pattern.matches(p.pattern(), etEmail.toString()) == false) {
                                textInputLayoutRegEmail.setErrorEnabled(true);
                                textInputLayoutRegEmail.setError(StringsUtil.ENTER_EMAIL);
                                return;
                            }


                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            // Create new fragment and transaction
                            Fragment newFragment = new CreateNewPasswordFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment);
                            transaction.addToBackStack(null);
                    // Commit the transaction
                            transaction.commit();
                    progressDialog.setMessage("Wait plzz...");
                    progressDialog.show();
                    auth.sendPasswordResetEmail(etEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {



                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(baseActivity, "We have send  password reset instruction on your registed mail id", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(baseActivity,"Failed to send reset mail!",Toast.LENGTH_SHORT).show();
                                    }

                                    progressDialog.dismiss();
                                }
                            });
                        }});
        edtRgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtRgEmail.setCursorVisible(true);
                textInputLayoutRegEmail.setError(null);
            }
        });

                    return view;


                }
            }