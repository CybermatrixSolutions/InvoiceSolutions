package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/16/2018.
 */

public  class MakeModel {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}
