package com.medicare.app.Util;

/**
 * Created by satveer on 15-01-2017.
 */

public class ConstantsUtil {

    public static final String REGEX_EMAIL_VALIDATION = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}";
    public static final String REGEX_PASSWORD_VALIDATION = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20}";
    public static final String REGEX_MOBILE_VALIDATION = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
    public static final String EMPTY_STRING = "";
    public static final String SPACE_STRING = " ";

   public static interface strings {
        String ENTER_NAME = "Please enter name.";
        String ENTER_EMAIL = "Please enter valid Email Id.";
        String ENTER_MOBILE = "Please enter a valid 10 digit Mobile Number.";
        String CHECK_TNC = "Please check Term and Conditions.";
        String ENTER_PASSWORD = "\"Please enter valid password , it should contains at least a digit, a lower case letter, an upper case letter, a special character,no whitespace and atleast eight in length.";
        String WIP = "WORK IN PROGRESS. ";
        String PASSWORD_NOT_MATCHED = "Password not matched, please check ! ";
    }
    //private final int CODE_SUCCESS = 1111;

    //private final int CODE_SIGNUP = 1234;

}
