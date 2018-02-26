package com.cybermatrixsolutions.invoicesolutions.model;

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

    @SerializedName("pump_legal_name")
    private String pump_legal_name;


    @SerializedName("pump_address")
    private String pump_address;



    @SerializedName("address_2")
    private String address_2;

    @SerializedName("address_3")
    private String address_3;

@SerializedName("city")
private String city;



    @SerializedName("state")
private String state;

@SerializedName("gstcode")
private String gstcode;

@SerializedName("pin_code")
private String pin_code;
    @SerializedName("customer_care")
    private String customer_care;

    @SerializedName("phone_no")
    private String phone_no;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("VAT_TIN")
    private String VAT_TIN;

    @SerializedName("GST_TIN")
    private String GST_TIN;

    @SerializedName("invoice_prefix")
    private String invoice_prefix;


    @SerializedName("GST_prefix")
    private String GST_prefix;
    public String getGST_prefix() {
        return GST_prefix;
    }



    @SerializedName("PAN_No")
    private String PAN_No;
    @SerializedName("TC_Delivery_Slip")
    private String TC_Delivery_Slip;


    @SerializedName("TC_Delivery_Slip2")
    private String TC_Delivery_Slip2;
    @SerializedName("TC_for_GST_Invoice_Slip")
    private String TC_for_GST_Invoice_Slip;
    @SerializedName("TC_for_GST_Invoice_Slip2")
    private String TC_for_GST_Invoice_Slip2;






    public String getId() {
        return id;
    }

    public String getPersonnel_Name() {
        return Personnel_Name;
    }

    public String getDesignation_Name() {
        return Designation_Name;
    }

    public String getDate_of_Birth() {
        return Date_of_Birth;
    }

    public String getDate_of_Appointment() {
        return Date_of_Appointment;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getEmployeecode() {
        return Employeecode;
    }

    public String getEmail() {
        return Email;
    }
    public String getPump_legal_name() {
        return pump_legal_name;
    }

    public String getPump_address() {
        return pump_address;
    }
    public String getAddress_2() {
        return address_2;
    }

    public String getAddress_3() {
        return address_3;
    } public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getGstcode() {
        return gstcode;
    }

    public String getPin_code() {
        return pin_code;
    }

    public String getCustomer_care() {
        return customer_care;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getVAT_TIN() {
        return VAT_TIN;
    }

    public String getGST_TIN() {
        return GST_TIN;
    }

    public String getInvoice_prefix() {
        return invoice_prefix;
    }

    public String getPAN_No() {
        return PAN_No;
    }
    public String getTC_Delivery_Slip() {
        return TC_Delivery_Slip;
    }

    public String getTC_Delivery_Slip2() {
        return TC_Delivery_Slip2;
    }

    public String getTC_for_GST_Invoice_Slip() {
        return TC_for_GST_Invoice_Slip;
    }

    public String getTC_for_GST_Invoice_Slip2() {
        return TC_for_GST_Invoice_Slip2;
    }
}
