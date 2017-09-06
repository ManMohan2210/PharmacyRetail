package com.medicare.app.activity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

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

    private     Double geocordinates;
    private  String address;
    private double latitude ;
    private double longitude ;
    private String accessToken;
    //private String uniqueUId;
    //private Timestamp signUpTime;
    private Map<String, String> signUpTime=new HashMap<String, String>();
    private String photoUrl;
   // private String instanceId;

    private static UserTypeModel userTypeModel = new UserTypeModel();

    public UserTypeModel(String address, double longitude, double latitude) {
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static UserTypeModel getInastnce(){
        return  userTypeModel;
    }

    public static void createUser(String userId, String userName, String userType, String mobileNumber,String email,String password,String accessToken) {
        userTypeModel.userId = userId;
        userTypeModel.userName = userName;
        userTypeModel.userType = userType;
        userTypeModel.mobileNumber = mobileNumber;
        userTypeModel.password = password;
        userTypeModel.email = email;
        userTypeModel.accessToken = accessToken;

    }

    public UserTypeModel(String userId, String userName, String userType, String mobileNumber,String email,String password,String accessToken) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.email = email;
        this.accessToken = accessToken;

    }






    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("latitude", latitude);
        result.put("longitude ", longitude );
        result.put("address", address);

        return result;
    }
    public double getlatitude() {
        return latitude;
    }

    public void setlatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getlongitude () {
        return longitude ;
    }

    public void setlongitude (double longitude ) {
        this.longitude  = longitude ;
    }

    public Double getGeocordinates() {
        return geocordinates;
    }

    public void setGeocordinates(Double geocordinates) {
        this.geocordinates = geocordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Map<String, String> getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(Map<String, String> signUpTime) {
        this.signUpTime = signUpTime;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    private UserTypeModel(){


}

   /* public String getuniqueUId() {
        return uniqueUId;
    }

    public void setuniqueUId(String uniqueUId) {
        this.uniqueUId = uniqueUId;
    }
*/
/*    public UserTypeModel(String userId, String userName, String userType, String mobileNumber, String email, String password)
    {
        //, String uniqueUId) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;

        //this.uniqueUId = uniqueUId;
    }*/
    private UserTypeModel(String userId, String userName, String userType, String mobileNumber) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.mobileNumber = mobileNumber;


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

    public void setKey(String key) {
    }

    public static class Builder {
//opional
        private String userId;
        private  String userName;
        private  String mobileNumber;
        //requried
        private    String userType;
        private String uniqueUId;
        public Builder() {

            this.userId = userId;
            this.userName = userName;
            this.mobileNumber = mobileNumber;
            this.userType = userType;
            this.uniqueUId=uniqueUId;

        }

        public Builder uniqueUId(String userId) {
            this.uniqueUId = uniqueUId;
            return this;
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
       // uniqueUId=builder.uniqueUId;
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
