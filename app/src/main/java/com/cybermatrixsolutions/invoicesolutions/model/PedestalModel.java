package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/11/2018.
 */

public class PedestalModel {
    @SerializedName("Pedestal_Number")
    private String Pedestal_Number;

    public String getPedestal_Number() {
        return Pedestal_Number;
    }
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }
}
