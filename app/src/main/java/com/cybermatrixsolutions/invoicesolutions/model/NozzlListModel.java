package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/11/2018.
 */

public  class NozzlListModel {

    @SerializedName("Nozzle_Number")
    private String Nozzle_No;

    @SerializedName("id")
    private String id;
    @SerializedName("Nozzle_Start")
    private String Nozzle_Start;
    public String getNozzle_No() {
        return Nozzle_No;
    }

    public String getId() {
        return id;
    }

    public String getNozzle_Start() {
        return Nozzle_Start;
    }







}
