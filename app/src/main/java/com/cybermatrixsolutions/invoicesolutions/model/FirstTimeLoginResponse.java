package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Diwakar on 11/1/2017.
 */

public class FirstTimeLoginResponse {

    @SerializedName("msg")
    private String msg;

    @SerializedName("key")
    private String key;

    @SerializedName("ID")
    private String ID;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("Usertype")
    private String Usertype;

    public String getUsertype() {
        return Usertype;
    }


////    Customer
//    Sales
    @SerializedName("EMPDETAIL")
    private List<EmployeeDetail> employeeDetail;

    public List<EmployeeDetail> getEmployeeDetail() {
        return employeeDetail;
    }

    public String getID() {
        return ID;
    }

    public String getMobile() {
        return mobile;
    }

    public String getMsg() {
        return msg;
    }

    public String getKey() {
        return key;
    }
}
