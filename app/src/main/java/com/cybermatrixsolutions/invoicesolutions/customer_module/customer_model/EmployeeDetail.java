package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 11/14/2017.
 */

public class EmployeeDetail implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("Personnel_Name")
    private String Personnel_Name;

    @SerializedName("Designation_Name")
    private String Designation_Name;

    @SerializedName("Date_of_Birth")
    private String Date_of_Birth;

    @SerializedName("Date_of_Appointment")
    private String Date_of_Appointment;

    @SerializedName("Mobile")
    private String Mobile;

    @SerializedName("Employeecode")
    private String Employeecode;

    @SerializedName("Email")
    private String Email;

    public String getId() {
        return this.id;
    }

    public String getPersonnel_Name() {
        return this.Personnel_Name;
    }

    public String getDesignation_Name() {
        return this.Designation_Name;
    }

    public String getDate_of_Birth() {
        return this.Date_of_Birth;
    }

    public String getDate_of_Appointment() {
        return this.Date_of_Appointment;
    }

    public String getMobile() {
        return this.Mobile;
    }

    public String getEmployeecode() {
        return this.Employeecode;
    }

    public String getEmail() {
        return this.Email;
    }
}
