package com.medicare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medicare.app.Util.ConstantsUtil;
import com.medicare.app.Util.IntentConstants;
import com.medicare.app.Util.StringsUtil;
import com.medicare.app.Util.UtilityUtil;
import com.medicare.launch.app.R;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddExtraUserDeatils extends AppCompatActivity {
    @Bind(R.id.btn_next)
    Button mNextBtn;

    @Bind(R.id.edtMobNum)
    EditText mEdtMobile;

    @Bind(R.id.radioGroupUserType)
    RadioGroup mRgUserType;

    @Bind(R.id.rdb_retailer)
    RadioButton mRdbRetailer;

    @Bind(R.id.rdb_customer)
    RadioButton mRdbCustomer;

    @Bind(R.id.ll_input_mob)
    TextInputLayout textInputLayoutMNum;
    DatabaseReference databaseMediCare;

    private String userName;
    private String emailId;
    private String userId;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_extra_user_deatils);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        userName =  intent.getStringExtra(IntentConstants.EXTRA_USER_NAME);
        emailId =  intent.getStringExtra(IntentConstants.EXTRA_EMAIL);
        userId = intent.getStringExtra(IntentConstants.EXTRA_USER_ID);
        accessToken = intent.getStringExtra(IntentConstants.EXTRA_ACCESS_TOKEN);
        final boolean loginViaSocial  = true;
         databaseMediCare = FirebaseDatabase.getInstance().getReference();
         mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate mobile no
                String mobNum = mEdtMobile.getText().toString();
                Pattern mob = Pattern.compile(ConstantsUtil.REGEX_MOBILE_VALIDATION);
                if (UtilityUtil.isNullOrEmpty(mobNum)) {
                    textInputLayoutMNum.setErrorEnabled(true);
                    textInputLayoutMNum.setError(StringsUtil.ENTER_MOBILE);
                    return;
                } else if (Pattern.matches(mob.pattern(),mobNum) == false) {
                    textInputLayoutMNum.setErrorEnabled(true);
                    textInputLayoutMNum.setError(StringsUtil.ENTER_MOBILE);
                    return;
                }
                //get radio button selected value
                int selectedId = mRgUserType.getCheckedRadioButtonId();
                // find the radio button by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String userType= (String) radioButton.getText();

                //get current user
                String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

                //save into database
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                String EXTRA_USER_ID, String EXTRA_USER_NAME, String userType, String mobileNumber,String EXTRA_EMAIL,String password,String accessToken) {
                UserTypeModel userDetail = new UserTypeModel(userId,userName,userType,mobNum,emailId,null,accessToken);
                databaseMediCare.child("users").child(uid).setValue(userDetail);
                // start map activity
                if (userType.equals("Retailer")) {
                    Intent intent = new Intent(AddExtraUserDeatils.this, UberMapActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(AddExtraUserDeatils.this, DrawerMain.class);
                    startActivity(intent);
                }
            }
        });
    }
}