package com.medicare.app.activity;

/**
 * Created by satveer on 01-08-2017.
 */

public class UserApplication {
    private static UserApplication userInstance;
    private String userName;
    //no outer class can initialize this class's object
    private UserApplication() {}

    public static UserApplication Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (userInstance == null)
        {
            userInstance = new UserApplication();
        }
        return userInstance;
    }

    public static UserApplication getUserInstance() {
        return userInstance;
    }

    public static void setUserInstance(UserApplication userInstance) {
        UserApplication.userInstance = userInstance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
