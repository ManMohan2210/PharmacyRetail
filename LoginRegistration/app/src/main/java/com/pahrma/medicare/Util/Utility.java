package com.pahrma.medicare.Util;

/**
 * Created by satveer on 15-01-2017.
 */

public class Utility {

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
}
