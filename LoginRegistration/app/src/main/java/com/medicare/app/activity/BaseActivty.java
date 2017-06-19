package com.medicare.app.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.medicare.app.Util.QuickDialogsUtil;
import com.medicare.app.Util.UtilityUtil;
import com.medicare.app.models.PrescriptionDataModel;
import com.pharma.medicare.app.R;

import java.util.List;

/**
 * Created by satveer on 15-01-2017.
 */

public class BaseActivty extends AppCompatActivity {


//    @Override
//    public void setContentView(@LayoutRes int layoutResID) {
//
//
//        if(UtilityUtil.isNetworkAvailable()){
//            layoutResID = layoutResID;
//        }else {
//            layoutResID = R.layout.activity_base;
//        }
//
//        super.setContentView(layoutResID);
//    }
    static final   String item[] = {
        "Allegra", "Asprin", "Azithromycin", "Combiflame", "CrocinActivity","Disprin", "ParacetamolActivity",  "Pudinhara",
        "VicksAction-500", "Vomikind", "Benadryl","Afghanistan", "Albania",
            "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
            "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
            "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin",
            "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
            "Botswana", "Bouvet Island", "Brazil",
            "British Indian Ocean Territory", "British Virgin Islands",
            "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cote d'Ivoire",
            "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
            "Central African Republic", "Chad", "Chile", "China",
            "Christmas Island", "Cocos (Keeling) Islands", "Colombia",
            "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia",
            "Cuba", "Cyprus", "Czech Republic",
            "Democratic Republic of the Congo", "Denmark", "Djibouti",
            "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt",
            "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
            "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji",
            "Finland", "Former Yugoslav Republic of Macedonia", "France",
            "French Guiana", "French Polynesia", "French Southern Territories",
            "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",
            "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala",
            "Guinea", "Guinea-Bissau", "Guyana", "Haiti",
            "Heard Island and McDonald Islands", "Honduras", "Hong Kong",
            "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq"};

    List<PrescriptionDataModel> precData;
    static final  String[] cityArray = {"New Delhi","Gurgaon","Pune","Mumbai",
            "Bangalore","Kolkata","Chennai","Chandhigarh","Nagpur"};

private FrameLayout mMainContainer;
    private FrameLayout mErrorContainer;
    private ViewGroup mMainContent;
    private ViewGroup mErrorContent;
    private ProgressDialog mLoadingDialog;
    private Button mRetryButton;
    private int showDialogCount = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     *
     * @param message
     */
    public void showToast(String message) {

        Toast toast = Toast.makeText(BaseActivty.this, message, Toast.LENGTH_LONG);
        toast.show();

    }

    /**
     *
     * @param message
     */
    public void showToastInCentre(String message) {

        Toast toast = Toast.makeText(BaseActivty.this,message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
//--






    @Override
    public void setContentView(int layoutResID) {

        super.setContentView(R.layout.activity_app_base);

        mMainContainer = (FrameLayout) findViewById(R.id.main_container);
        mErrorContainer = (FrameLayout) findViewById(R.id.error_container);
        mErrorContent =  (ViewGroup) findViewById(R.id.error_content);
        // add layout of BaseActivities inside framelayout.i.e. frame_container
        mMainContent = (ViewGroup) getLayoutInflater().inflate(layoutResID, mMainContainer, true);
        if (UtilityUtil.isNetworkAvailable()) {
            //loadActivity();
        } else {
            mErrorContainer.setVisibility(View.VISIBLE);
            mMainContainer.setVisibility(View.GONE);
        }

        mRetryButton = (Button) mErrorContent.findViewById(R.id.btn_retry);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilityUtil.isNetworkAvailable()) {
                    mErrorContainer.setVisibility(View.GONE);
                    mMainContainer.setVisibility(View.VISIBLE);
              //      loadActivity();
                }

            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
       // String screenName = getScreenName();
    }
/*
* Derive class will provide screen name to be used by TagManager
* */



    public void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionBar.setTitle(title);
        }

    }

    /**
     * Sets display home enabled.
     */
    public void setDisplayHomeEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Show loading dialog.
     */
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = QuickDialogsUtil.showLoadingDialog(this, null, null);
            mLoadingDialog.show();
        } else if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
        showDialogCount++;
    }

    /**
     * Dismiss dialog.
     */
    public void dismissDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
    }

    /**
     * Soft dismiss dialog.
     */
    public void softDismissDialog() {
        showDialogCount --;
        if(showDialogCount <= 0) {
            dismissDialog();
        }
    }

    /**
     * Show snack bar.
     *
     * @param msg      the msg
     * @param duration the duration
     */
    public void showSnackBar(String msg, int duration) {
        Snackbar snackbar = Snackbar
                .make(mMainContainer, msg, duration);
        snackbar.show();
    }

    /**
     * Show toast.
     *
     * @param msg      the msg
     * @param duration the duration
     */
    protected void showToast(String msg, int duration) {
        Toast.makeText(this,msg,duration).show();
    }

    /**
     * Show snack bar with action button which give callback on click of action button.
     *
     * @param msg              the msg
     * @param callBackAction   the call back action
     * @param callBackListener the call back listener
     */
    protected void showSnackBar(String msg, String
            callBackAction, View.OnClickListener callBackListener) {
       /* Snackbar snackbar = Snackbar
                .make(mMainContainer, msg, Snackbar.LENGTH_LONG)
                .setAction(callBackAction, callBackListener);
        snackbar.show();*/
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.snackbarCoordinatorLayout);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snackbar.show();
    }

}


