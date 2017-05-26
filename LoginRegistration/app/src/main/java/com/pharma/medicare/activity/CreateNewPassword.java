package com.pharma.medicare.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pharma.medicare.Util.Constants;
import com.pharma.medicare.Util.Strings;
import com.pharma.medicare.Util.Utility;



/**
 * Created by satveer on 14-01-2017.
 */

public class CreateNewPassword extends Fragment implements View.OnClickListener {

    private EditText edtCreatePassword;
    private Button btnResetPwd;
    private EditText edtConfirmPassword;
    private CheckBox checkBoxShowPwdNew;
    private CheckBox checkBoxShowPwdCnf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       //View view = inflater.inflate(R.layout.create_new_password, container, false);
        final View rootView = inflater.inflate(R.layout.create_new_password, container,
                false);
        edtCreatePassword = (EditText) rootView.findViewById(R.id.edtNewPassword);
        edtConfirmPassword = (EditText) rootView.findViewById(R.id.edtCnfPassword);
        btnResetPwd = (Button) rootView.findViewById(R.id.btn_reset);
        checkBoxShowPwdNew = (CheckBox) rootView.findViewById(R.id.cbShowPwdNew);
        checkBoxShowPwdCnf = (CheckBox) rootView.findViewById(R.id.cbShowCnfPwd);
        btnResetPwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String etPassword = edtCreatePassword.getText().toString();
                String etCnfPassword = edtConfirmPassword.getText().toString();

                Pattern pwd = Pattern.compile(Constants.REGEX_PASSWORD_VALIDATION);
                Matcher m1 = pwd.matcher(etPassword);


                if (etPassword == null) {
                    Toast.makeText(getActivity(), Strings.ENTER_PASSWORD, Toast.LENGTH_LONG).show();
                    return;
                } else if (Utility.isNullOrEmpty(etPassword)) {
                    Toast.makeText(getActivity(), Strings.ENTER_PASSWORD, Toast.LENGTH_LONG).show();
                    return;
                } else if (Pattern.matches(pwd.pattern(), etPassword.toString()) == false) {
                    Toast.makeText(getActivity(), Strings.ENTER_PASSWORD, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!etCnfPassword.matches(etPassword))
                {
                    Toast.makeText(getActivity(), Strings.PASSWORD_NOT_MATCHED, Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getActivity(), "Password changed successfully.", Toast.LENGTH_LONG).show();
            }

        });
        checkBoxShowPwdNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edtCreatePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    edtCreatePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        checkBoxShowPwdCnf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        return rootView;
    }


    @Override
    public void onClick(View v) {

    }
}