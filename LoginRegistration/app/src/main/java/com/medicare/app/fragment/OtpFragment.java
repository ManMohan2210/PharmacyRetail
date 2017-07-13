package com.medicare.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.medicare.app.Util.ConstantsUtil;
import com.medicare.app.Util.StringsUtil;
import com.medicare.app.Util.UtilityUtil;
import com.medicare.launch.app.R;

import java.util.regex.Pattern;

/**
 * Created by satveer on 14-01-2017.
 */




public class OtpFragment extends Fragment implements OnClickListener {
    private Button btnRegMob;
    private EditText edtRegMob;
    private Activity mActivity;

    private TextInputLayout textInputLayoutOTP;

   /* @Bind(R.id.edt_reg_mob)
    EditText mEdtRegMob;

    @Bind(R.id.btn_otp)
    Button mBtnRegMob;

    @Bind(R.id.ll_input_otp)
    TextInputLayout textInputLayoutOTP;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
       btnRegMob = (Button) view.findViewById(R.id.btn_otp);
        edtRegMob = (EditText) view.findViewById(R.id.edt_reg_mob);
        textInputLayoutOTP=(TextInputLayout) view.findViewById(R.id.ll_input_otp);
       btnRegMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtRegMob.setCursorVisible(false);
               String edtReg = edtRegMob.getText().toString();
                Pattern mob = Pattern.compile(ConstantsUtil.REGEX_MOBILE_VALIDATION);
                //Matcher m = p.matcher(edtReg);
               // final boolean b = m.matches();
                if (UtilityUtil.isNullOrEmpty(edtReg)) {
                    textInputLayoutOTP.setErrorEnabled(true);
                    textInputLayoutOTP.setError(StringsUtil.ENTER_MOBILE);
                    return;
                } else if (Pattern.matches(mob.pattern(),edtReg) == false) {
                    textInputLayoutOTP.setErrorEnabled(true);
                    textInputLayoutOTP.setError(StringsUtil.ENTER_MOBILE);
                    return;
                }
               /* if (edtRegMob == null) {
                    textInputLayoutOTP.setErrorEnabled(true);
                    textInputLayoutOTP.setError(StringsUtil.ENTER_MOBILE);
                    //((BaseActivty)mActivity).showSnackBar(ConstantsUtil.strings.ENTER_MOBILE,null,null);
                    return;
                } else if (UtilityUtil.isNullOrEmpty(edtRegMob.toString())) {
                    textInputLayoutOTP.setErrorEnabled(true);
                    textInputLayoutOTP.setError(StringsUtil.ENTER_MOBILE);
                    return;
                } else if (Pattern.matches(mob.pattern(), edtRegMob.toString()) == false){//(Pattern.matches(p.pattern().toString(), edtRegMob.toString()) == false) {
                    textInputLayoutOTP.setErrorEnabled(true);
                    textInputLayoutOTP.setError(StringsUtil.ENTER_MOBILE);
                    return;
                }*/
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Create new fragment and transaction
                Fragment newFragment = new CreateNewPasswordFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

            }
        });
        edtRegMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtRegMob.setCursorVisible(true);
                textInputLayoutOTP.setError(null);
            }
        });

        return view;

    }


    public void onClick(View v) {

    }
}