package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/18/2018.
 */

public class States {
    @SerializedName("name")
    private String name;
    public String getName() {
        return name;
    }
    @SerializedName("gstcode")
    private String gstcode;
    public String getGstcode() {
        return gstcode;
    }



}
