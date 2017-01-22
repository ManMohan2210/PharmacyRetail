package mypackage.loginregistration;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

public class FragmentEmail extends Fragment {

private Button btnEmail;
    private EditText edtRgEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.password_email, container, false);
        btnEmail = (Button) view.findViewById(R.id.btn_email);
        edtRgEmail = (EditText) view.findViewById(R.id.edtRegEmail);

            btnEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            String etEmail = edtRgEmail.getText().toString();
                            Pattern p = Pattern.compile(Constants.REGEX_EMAIL_VALIDATION);

                            if (etEmail == null) {
                                Toast.makeText (getActivity(),Strings.ENTER_EMAIL, Toast.LENGTH_LONG).show();
                                return;
                            } else if (Utility.isNullOrEmpty(etEmail)) {
                                Toast.makeText (getActivity(),Strings.ENTER_EMAIL, Toast.LENGTH_LONG).show();
                                return;
                            } else if (Pattern.matches(p.pattern(), etEmail.toString()) == false) {
                                Toast.makeText (getActivity(),Strings.ENTER_EMAIL, Toast.LENGTH_LONG).show();
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
                        }});


                    return view;


                }
            }