package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 11/3/2017.
 */

public class CustomerList implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("Customer_Code")
    private String Customer_Code;

    @SerializedName("Customer_Name")
    private String Customer_Name;

    @SerializedName("Credit_limit")
    private String Credit_limit;

    public String getId() {
        return id;
    }

    public String getCustomer_Code() {
        return Customer_Code;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public String getCredit_limit() {
        return Credit_limit;
    }
}
