package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 2/17/2018.
 */

public class DriverCustomer {
    @SerializedName("Customer_Code")
    private String Customer_Code;
    @SerializedName("company_name")
    private String company_name;
    @SerializedName("pump_legal_name")
    private String pump_legal_name;




    public String getCustomer_Code() {
        return Customer_Code;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getPump_legal_name() {
        return pump_legal_name;
    }
}
