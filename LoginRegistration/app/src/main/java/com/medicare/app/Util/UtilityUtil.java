package com.medicare.app.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;
/**
 * Created by satveer on 15-01-2017.
 */

public class UtilityUtil {
    public static String UUID;
    public static Typeface tf = null;
    public static boolean isNetworkAvailable() {
        return true;
    }

    public static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        } else if (str.equals(ConstantsUtil.EMPTY_STRING) || str.equals(ConstantsUtil.SPACE_STRING)) {
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
            tf = Typeface.createFromAsset(context.getAssets(), "colab_reg.otf");
        return tf;
    }

        public static  long getDayTimestamp(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTimeInMillis();
    }
}