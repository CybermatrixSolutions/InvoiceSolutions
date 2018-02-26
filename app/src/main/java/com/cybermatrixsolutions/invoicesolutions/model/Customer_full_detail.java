package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 12/26/2017.
 */

public  class Customer_full_detail implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("RO_code")
    private String RO_code;
    @SerializedName("Customer_Code")
    private String Customer_Code;
    @SerializedName("Customer_Name")
    private String Customer_Name;
    @SerializedName("address_one")
    private String address_one;
    @SerializedName("Customer_Type")
    private String Customer_Type;
    @SerializedName("Phone_Number")
    private String Phone_Number;
    @SerializedName("Mobile")
    private String Mobile;
    @SerializedName("IMEI_No")
    private String IMEI_No;
    @SerializedName("Email")
    private String Email;
    @SerializedName("is_active")
    private String is_active;
    @SerializedName("Created_date")
    private String Created_date;
    @SerializedName("Credit_limit")
    private String Credit_limit;
    @SerializedName("country")
    private String country;
    @SerializedName("statefinal")
    private String state;
    @SerializedName("cityfinal")
    private String city;
    @SerializedName("gst_no")
    private String gst_no;
    @SerializedName("company_name")
    private String company_name;
    @SerializedName("address_two")
    private String address_two;
    @SerializedName("address_three")
    private String address_three;
    @SerializedName("pin_no")
    private String pin_no;
    @SerializedName("pan_no")
    private String pan_no;

    @SerializedName("state")
    private String state_id;
    @SerializedName("gstcode")
    private String gstcode;
    @SerializedName("pre_authori")
    private String pre_authori;






    public String getId() {
        return id;
    }

    public String getRO_code() {
        return RO_code;
    }

    public String getCustomer_Code() {
        return Customer_Code;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public String getAddress_one() {
        return address_one;
    }

    public String getCustomer_Type() {
        return Customer_Type;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getIMEI_No() {
        return IMEI_No;
    }

    public String getEmail() {
        return Email;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getCreated_date() {
        return Created_date;
    }

    public String getCredit_limit() {
        return Credit_limit;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getGst_no() {
        return gst_no;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getAddress_two() {
        return address_two;
    }

    public String getAddress_three() {
        return address_three;
    }

    public String getPin_no() {
        return pin_no;
    }

    public String getPan_no() {
        return pan_no;
    }

    public String getState_id() {
        return state_id;
    }
    public String getGstcode() {
        return gstcode;
    }
    public String getPre_authori() {
        return pre_authori;
    }



}
