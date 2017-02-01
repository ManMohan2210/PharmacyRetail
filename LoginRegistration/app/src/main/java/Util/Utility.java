package Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by satveer on 15-01-2017.
 */

public class Utility {
    public static Typeface tf = null;
    public static boolean isNetworkAvailable() {
        return true;
    }

    public static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        } else if (str.equals(Constants.EMPTY_STRING) || str.equals(Constants.SPACE_STRING)) {
            return true;
        }

        return false;
    }

    public static void hideKeyboardFrom(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static Typeface getPTSansFontStyle(Context context) {
        if (tf == null)
            tf = Typeface.createFromAsset(context.getAssets(), "pt_sans.ttf");
        return tf;
    }
}