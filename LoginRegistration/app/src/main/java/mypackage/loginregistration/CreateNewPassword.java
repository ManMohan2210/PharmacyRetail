package mypackage.loginregistration;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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

import Util.Constants;
import Util.Strings;
import Util.Utility;

import static mypackage.loginregistration.R.drawable.show;


/**
 * Created by satveer on 14-01-2017.
 */

public class CreateNewPassword extends Fragment implements View.OnClickListener {

    private EditText edtCreatePassword;
    private Button btnResetPwd;
    private EditText edtConfirmPassword;
    //private CheckBox checkBoxShowPwdNew;
   // private CheckBox checkBoxShowPwdCnf;
    private TextInputLayout textInputLayoutNewPwd;
    private TextInputLayout textInputLayoutCnfPwd;
    private Activity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.create_new_password, container, false);
        //final View rootView = inflater.inflate(R.layout.create_new_password, container,
               // false);
        edtCreatePassword = (EditText) view.findViewById(R.id.edtNewPassword);
        edtConfirmPassword = (EditText) view.findViewById(R.id.edtCnfPassword);
        btnResetPwd = (Button) view.findViewById(R.id.btn_reset);
        textInputLayoutNewPwd=(TextInputLayout) view.findViewById(R.id.ll_input_new_password);
        textInputLayoutCnfPwd=(TextInputLayout) view.findViewById(R.id.ll_input_cnf_password);
        edtCreatePassword.setCursorVisible(false);
        //checkBoxShowPwdNew = (CheckBox) rootView.findViewById(R.id.cbShowPwdNew);
        //checkBoxShowPwdCnf = (CheckBox) rootView.findViewById(R.id.cbShowCnfPwd);
        btnResetPwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String etPassword = edtCreatePassword.getText().toString();
                String etCnfPassword = edtConfirmPassword.getText().toString();

                Pattern pwd = Pattern.compile(Constants.REGEX_PASSWORD_VALIDATION);
                Matcher m1 = pwd.matcher(etPassword);

                if (etPassword.isEmpty() || etPassword.length() < 4 || etPassword.length() > 10) {
                    textInputLayoutNewPwd.setError(Strings.PASSWORD_NOT_VALID);
                    return ;

                } else if (Pattern.matches(pwd.pattern(), etPassword) == false) {
                    textInputLayoutNewPwd.setError(Strings.ENTER_PASSWORD);
                    return ;

                }

               /* if (etPassword == null) {
                    textInputLayoutNewPwd.setErrorEnabled(true);
                    textInputLayoutNewPwd.setError(Strings.ENTER_PASSWORD);//Toast.makeText(getActivity(), Strings.ENTER_PASSWORD, Toast.LENGTH_LONG).show();
                    return;
                } else if (Utility.isNullOrEmpty(etPassword)) {
                    Toast.makeText(getActivity(), Strings.ENTER_PASSWORD, Toast.LENGTH_LONG).show();
                    return;
                } else if (Pattern.matches(pwd.pattern(), etPassword.toString()) == false) {
                    Toast.makeText(getActivity(), Strings.ENTER_PASSWORD, Toast.LENGTH_LONG).show();
                    return;
                }*/
                if (!etCnfPassword.matches(etPassword))
                {

                    textInputLayoutCnfPwd.setError(Strings.PASSWORD_NOT_MATCHED);// Toast.makeText(getActivity(), Strings.PASSWORD_NOT_MATCHED, Toast.LENGTH_LONG).show();
                    return;
                }
                ((BaseActivty)mActivity).showSnackBar(Strings.PASSWORD_CHANGED,6);  //Toast.makeText(getActivity(), "Password changed successfully.", Toast.LENGTH_LONG).show();
            }

        });
       /* checkBoxShowPwdNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

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
        });*/
        edtCreatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtCreatePassword.setCursorVisible(true);
                textInputLayoutNewPwd.setError(null);
            }
        });
        edtConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtConfirmPassword.setCursorVisible(true);
                textInputLayoutCnfPwd.setError(null);
            }
        });
        return view;
    }


    @Override
    public void onClick(View v) {

    }
}