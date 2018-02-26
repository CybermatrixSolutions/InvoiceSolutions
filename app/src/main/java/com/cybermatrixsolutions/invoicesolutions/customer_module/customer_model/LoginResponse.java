package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dhiraj on 15-07-2017.
 */

public class LoginResponse implements Serializable {

    @SerializedName("status")
    private String status;


    @SerializedName("data")
    private FirstTimeLoginResponse Firsresponse;

    public String getStatus() {
        return this.status;
    }




    public FirstTimeLoginResponse getFirsresponse() {
        return this.Firsresponse;
    }




}
