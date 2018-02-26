package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 9/15/2017.
 */

public class NozzleReadingModel implements Serializable {

    @SerializedName("Medicine")
    String nozzleNumber;

    @SerializedName("Days")
    String price;

    @SerializedName("Days")
    String lubeName;

    @SerializedName("Days")
    String starReading;

    @SerializedName("Frequency")
    String qty;

    public String getLubeName() {
        return lubeName;
    }

    public void setLubeName(String lubeName) {
        this.lubeName = lubeName;
    }

    public String getStarReading() {
        return starReading;
    }

    public void setStarReading(String starReading) {
        this.starReading = starReading;
    }

    public String getNozzleNumber() {
        return nozzleNumber;
    }

    public void setNozzleNumber(String nozzleNumber) {
        this.nozzleNumber = nozzleNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
