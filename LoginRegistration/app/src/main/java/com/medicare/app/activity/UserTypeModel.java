package com.medicare.app.activity;

/**
 * Created by satveer on 08-06-2017.
 */

public class UserTypeModel {

private     String userId;
   private  String userName;
    private String userType;
   private  String mobileNumber;
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTypeModel(){


}


    public UserTypeModel(String userId, String userName, String userType, String mobileNumber, String email) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.mobileNumber = mobileNumber;
        this.email = email;

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public static class Builder {
//opional
        private String userId;
        private  String userName;
        private  String mobileNumber;
        //requried
        private    String userType;

        public Builder() {

            this.userId = userId;
            this.userName = userName;
            this.mobileNumber = mobileNumber;
            this.userType = userType;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder userType(String userType ) {
            this.userType = userType;
            return this;
        }

        public Builder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this ;
        }
        public UserTypeModel build() {

            return new UserTypeModel(this) ;
        }
    }
    private UserTypeModel(Builder builder) {
        userId = builder.userId;
        mobileNumber = builder.mobileNumber;
        userName = builder.userName;
        userType = builder.userType;
    }
}

/*
public class Pizza {
    private int size;
    private boolean cheese;
    private boolean pepperoni;
    private boolean bacon;

    public static class Builder {
        //required
        private final int size;

        //optional
        private boolean cheese = false;
        private boolean pepperoni = false;
        private boolean bacon = false;

        public Builder(int size) {
            this.size = size;
        }

        public Builder cheese(boolean value) {
            cheese = value;
            return this;
        }

        public Builder pepperoni(boolean value) {
            pepperoni = value;
            return this;
        }

        public Builder bacon(boolean value) {
            bacon = value;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }

    private Pizza(Builder builder) {
        size = builder.size;
        cheese = builder.cheese;
        pepperoni = builder.pepperoni;
        bacon = builder.bacon;
    }
}*/
