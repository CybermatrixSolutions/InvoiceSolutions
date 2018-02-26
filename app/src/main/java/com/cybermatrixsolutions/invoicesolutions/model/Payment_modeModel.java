package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/11/2018.
 */

public class Payment_modeModel {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String ro_code;

    public String getId() {
        return id;
    }

    public String getRo_code() {
        return ro_code;
    }
    private String price;
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }





}
