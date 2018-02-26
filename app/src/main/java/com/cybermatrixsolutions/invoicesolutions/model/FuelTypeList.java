package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 11/1/2017.
 */

public class FuelTypeList implements Serializable {

    @SerializedName("Petrol_Diesel_Type")
    private String Petrol_Diesel_Type;

    @SerializedName("Price")
    private String Price;

    public String getPetrol_Diesel_Type() {
        return Petrol_Diesel_Type;
    }

    public String getPrice() {
        return Price;
    }
}
