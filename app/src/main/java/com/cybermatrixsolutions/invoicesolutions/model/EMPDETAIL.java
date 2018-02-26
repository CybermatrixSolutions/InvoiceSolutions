package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/12/2018.
 */

public  class EMPDETAIL {

    @SerializedName("id")
    private String id;
    @SerializedName("Driver_Name")
    private String Driver_Name;
    @SerializedName("Registration_Number")
    private String Registration_Number;
    @SerializedName("Customer_Name")
    private String Customer_Name;
    @SerializedName("pump_legal_name")
    private String pump_legal_name;
    public String getId() {
        return id;
    }

    public String getDriver_Name() {
        return Driver_Name;
    }

    public String getRegistration_Number() {
        return Registration_Number;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public String getPump_legal_name() {
        return pump_legal_name;
    }






}
