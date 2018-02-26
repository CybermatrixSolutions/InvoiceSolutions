package com.cybermatrixsolutions.invoicesolutions.model;

import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.DriverCustomer;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diwakar on 1/12/2018.
 */

public  class Driver_ResponceModel {
    @SerializedName("key")
    private String key;
    public String getKey() {
        return key;
    }
    @SerializedName("EMPDETAIL")
    List<EMPDETAIL>list;
    public List<EMPDETAIL> getList() {
        return list;
    }
    @SerializedName("Driver Customer")
    private ArrayList<DriverCustomer> Driver_Customer;
    public ArrayList<DriverCustomer> getDriver_Customer() {
        return Driver_Customer;
    }


}
