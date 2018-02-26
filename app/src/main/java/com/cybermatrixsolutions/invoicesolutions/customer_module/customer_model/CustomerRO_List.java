package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 2/1/2018.
 */

public class CustomerRO_List {
    @SerializedName("Customer_Code")
    private String Customer_Code;
    public String getCustomer_Code() {
        return Customer_Code;
    }

    @SerializedName("pump_legal_name")
    private String pump_legal_name;
    public String getPump_legal_name() {
        return pump_legal_name;
    }




}
