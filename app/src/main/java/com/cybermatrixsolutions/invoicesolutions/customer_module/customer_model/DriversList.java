package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 12/1/2017.
 */

public class DriversList implements Serializable{

    @SerializedName("id")
    private String id;

    @SerializedName("customer_code")
    private String customer_code;

    @SerializedName("Registration_Number")
    private String Registration_Number;

    @SerializedName("Driver_Name")
    private String Driver_Name;

    @SerializedName("Driver_Mobile")
    private String Driver_Mobile;

    @SerializedName("Driver_Licence_No")
    private String Driver_Licence_No;

    @SerializedName("email")
    private String email;

    @SerializedName("valid_upto")
    private String valid_upto;


    @SerializedName("Make")
    private String Make;

    @SerializedName("Model")
    private String Model;

    @SerializedName("van_color")
    private String van_color;

    @SerializedName("puc_date")
    private String puc_date;

    public String getMake() {
        return this.Make;
    }

    public String getModel() {
        return this.Model;
    }

    public String getVan_color() {
        return this.van_color;
    }

    public String getPuc_date() {
        return this.puc_date;
    }

    public String getId() {
        return this.id;
    }

    public String getCustomer_code() {
        return this.customer_code;
    }

    public String getRegistration_Number() {
        return this.Registration_Number;
    }

    public String getDriver_Name() {
        return this.Driver_Name;
    }

    public String getDriver_Mobile() {
        return this.Driver_Mobile;
    }

    public String getDriver_Licence_No() {
        return this.Driver_Licence_No;
    }

    public String getEmail() {
        return this.email;
    }

    public String getValid_upto() {
        return this.valid_upto;
    }
}
