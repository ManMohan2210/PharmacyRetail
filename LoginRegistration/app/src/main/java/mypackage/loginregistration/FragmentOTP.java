package mypackage.loginregistration;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Util.Constants;
import Util.Strings;
import Util.Utility;

/**
 * Created by satveer on 14-01-2017.
 */




public class FragmentOTP extends Fragment implements OnClickListener {

    private Button btnRegMob;
    private EditText edtRegMob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_otp, container, false);
        btnRegMob = (Button) view.findViewById(R.id.btn_otp);
        edtRegMob = (EditText) view.findViewById(R.id.edtRegMob);
        btnRegMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Editable edtReg = edtRegMob.getText();
                Pattern p = Pattern.compile(Constants.REGEX_MOBILE_VALIDATION);
                Matcher m = p.matcher(edtReg);
                final boolean b = m.matches();
                if (edtReg == null) {
                    Toast.makeText(getActivity(), Strings.ENTER_MOBILE, Toast.LENGTH_LONG).show();
                    return;
                } else if (Utility.isNullOrEmpty(edtReg.toString())) {
                    Toast.makeText(getActivity(), Strings.ENTER_MOBILE, Toast.LENGTH_LONG).show();
                    return;
                } else if (Pattern.matches(m.pattern().toString(), edtReg.toString()) == false) {
                    Toast.makeText(getActivity(), Strings.ENTER_MOBILE, Toast.LENGTH_LONG).show();
                    return;
                }
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Create new fragment and transaction
                Fragment newFragment = new CreateNewPassword();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

            }
        });
        return view;
    }


    public void onClick(View v) {

    }
}