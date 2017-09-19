package com.medicare.app.activity;

/**
 * Created by satveer on 01-08-2017.
 */

public class UserManager {
    private static UserManager userInstance;
    private String userName;
    //no outer class can initialize this class's object
    private UserManager() {}

    public static UserManager Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (userInstance == null)
        {
            userInstance = new UserManager();
        }
        return userInstance;
    }

    public static UserManager getUserInstance() {
        return userInstance;
    }

    public static void setUserInstance(UserManager userInstance) {
        UserManager.userInstance = userInstance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
