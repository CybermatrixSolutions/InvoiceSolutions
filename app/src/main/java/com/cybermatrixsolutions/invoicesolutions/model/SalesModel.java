package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/11/2018.
 */

public  class SalesModel {
    @SerializedName("Personnel_Name")
    private String Personnel_Name;

    public String getPersonnel_Name() {
        return Personnel_Name;
    }
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }


}
