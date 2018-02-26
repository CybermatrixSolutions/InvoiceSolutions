package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 11/30/2017.
 */

public class FuelRequest implements Serializable{

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private FuelRequestModel fuelrequestmodel;

    public String getStatus() {
        return this.status;
    }




    public FuelRequestModel getFirsresponse() {
        return this.fuelrequestmodel;
    }


}
