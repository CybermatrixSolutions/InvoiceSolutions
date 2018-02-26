package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dhiraj on 15-07-2017.
 */

public class CustomerRequest implements Serializable {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private CustomerRequestResponse customerRequestResponse;

    public String getStatus() {
        return status;
    }

    public CustomerRequestResponse getCustomerRequestResponse() {
        return customerRequestResponse;
    }



}
