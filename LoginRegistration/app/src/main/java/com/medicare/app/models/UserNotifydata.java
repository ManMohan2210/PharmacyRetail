package com.medicare.app.models;

/**
 * Created by satveer on 01-07-2017.
 */

public class UserNotifydata {
    private String customerId;
    private String customerinput;
    private Retailerinput retailerinput;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerinput() {
        return customerinput;
    }

    public void setCustomerinput(String customerinput) {
        this.customerinput = customerinput;
    }

    public Retailerinput getRetailerinput() {
        return retailerinput;
    }

    public void setRetailerinput(Retailerinput retailerinput) {
        this.retailerinput = retailerinput;
    }


}
