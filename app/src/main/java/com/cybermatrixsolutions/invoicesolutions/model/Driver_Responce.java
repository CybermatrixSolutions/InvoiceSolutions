package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dhiraj on 15-07-2017.
 */

public class Driver_Responce implements Serializable {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Driver_ResponceModel Firsresponse;

    public String getStatus() {
        return status;
    }

    public Driver_ResponceModel getFirsresponse() {
        return Firsresponse;
    }
}
