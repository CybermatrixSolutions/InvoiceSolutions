package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abcd on 12/5/2017.
 */

public class FuelType  implements Serializable{

    @SerializedName("id")
    private String item_code;

    @SerializedName("Item_Name")
    private String type;

    public String getItem_code() {
        return this.item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }






}
