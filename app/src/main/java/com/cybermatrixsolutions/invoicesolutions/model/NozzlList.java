package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NozzlList {

    @SerializedName("Nozzle_No")
    private String Nozzle_No;

    @SerializedName("Nozzle_Start")
    private String Nozzle_Start;

    @SerializedName("Nozzle_End")
    private String Nozzle_End;

    @SerializedName("price")
    private String price;


    @SerializedName("test")
    private String test;
    @SerializedName("reading")
    private String reading;

    public String getTest() {
        return test;
    }

    public String getReading() {
        return reading;
    }



    public String getNozzle_No() {
        return Nozzle_No;
    }

    public void setNozzle_No(String nozzle_No) {
        Nozzle_No = nozzle_No;
    }

    public String getNozzle_Start() {
        return Nozzle_Start;
    }

    public void setNozzle_Start(String nozzle_Start) {
        Nozzle_Start = nozzle_Start;
    }

    public String getNozzle_End() {
        return Nozzle_End;
    }

    public void setNozzle_End(String nozzle_End) {
        Nozzle_End = nozzle_End;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}