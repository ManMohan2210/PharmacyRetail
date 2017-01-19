package Util;

/**
 * Created by satveer on 15-01-2017.
 */

public class Constants {

    public static final String REGEX_EMAIL_VALIDATION = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}";
    public static final String REGEX_PASSWORD_VALIDATION = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20}";
    public static final String REGEX_MOBILE_VALIDATION = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
    public static  final String EMPTY_STRING = "";
    public static  final String SPACE_STRING = " ";
    //private final int CODE_SUCCESS = 1111;

    //private final int CODE_SIGNUP = 1234;

}
