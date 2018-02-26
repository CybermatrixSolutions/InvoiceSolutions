package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Diwakar on 11/1/2017.
 */

public class CustomerRequestResponse implements Serializable {

    @SerializedName("Customer_Name")
    private String Customer_Name;

    @SerializedName("Customer_Code")
    private String Customer_Code;

    @SerializedName("Credit_limit")
    private String Credit_limit;

    @SerializedName("Registration_Number")
    private String Registration_Number;

    @SerializedName("Driver_Name")
    private String Driver_Name;

    @SerializedName("Credit_lube")
    private String Credit_lube;

    @SerializedName("key")
    private String key;

    @SerializedName("Credit_Petrol")
    private String Credit_Petrol;

    @SerializedName("request_date")
    private String request_date;

    @SerializedName("Driver_Mobile")
    private String Driver_Mobile;
    @SerializedName("pre_authori")
    private String pre_authori;




    @SerializedName("Customer_Request")
    private List<CustomerRequestList> customerRequestLists;


    @SerializedName("States")
    private List<States> getState;
    public List<States> getGetState() {
        return getState;
    }




    @SerializedName("Petrol Diesel")
    private List<CustomerRequestList> fuelTypeList;

    public List<Customer_full_detail> getCustomer_full_detail() {
        return customer_full_detail;
    }

    @SerializedName("Customer_full_detail")
    List<Customer_full_detail>customer_full_detail;

    public String getCredit_lube() {
        return Credit_lube;
    }

    public String getCredit_Petrol() {
        return Credit_Petrol;
    }

    @SerializedName("Nozzle")
    private List<NozzlListModel> Nozzle;
    public List<NozzlListModel> getNozzle() {
        return Nozzle;
    }

    @SerializedName("QR")
    private List<QRModel> QR;
    public List<QRModel> getQR() {
        return QR;
    }


    @SerializedName("OTP")
    private List<OTPMODEL>OTP;
    public List<OTPMODEL> getOTP() {
        return OTP;
    }
    @SerializedName("Shift Detail")
    private List<ShiftDetailModel>Shift_Detail;
    public List<ShiftDetailModel> getShift_Detail() {
        return Shift_Detail;
    }





    @SerializedName("Nozzle_No")
    private List<NozzlList> nozzlLists;

    public List<NozzlList> getNozzlLists() {
        return nozzlLists;
    }

    @SerializedName("Product")
    private List<LubeList> lubeLists;

    @SerializedName("Itemtax")
    private List<ItemtaxModel> Itemtax;

    public List<ItemtaxModel> getItemtax() {
        return Itemtax;
    }

    @SerializedName("Pedestal")
    private List<PedestalModel>Pedestal;
    public List<PedestalModel> getPedestal() {
        return Pedestal;
    }
    @SerializedName("Sales")
    private List<SalesModel>Sales;
    public List<SalesModel> getSales() {
        return Sales;
    }

    @SerializedName("Payment_mode")
    private List<Payment_modeModel>Payment_mode;
    public List<Payment_modeModel> getPayment_mode() {
        return Payment_mode;
    }
    @SerializedName("Customer")
    private List<CustomerList> customerLists;

    @SerializedName("msg")
    private String msg;
    @SerializedName("Invoice")
    private String Invoice;






    public List<CustomerList> getCustomerLists() {
        return customerLists;
    }

    public String getRequest_date() {
        return request_date;
    }

    public List<LubeList> getLubeLists() {
        return lubeLists;
    }

    public List<CustomerRequestList> getFuelTypeList() {
        return fuelTypeList;
    }

    public String getKey() {
        return key;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public String getCustomer_Code() {
        return Customer_Code;
    }

    public String getCredit_limit() {
        return Credit_limit;
    }

    public String getRegistration_Number() {
        return Registration_Number;
    }

    public String getDriver_Name() {
        return Driver_Name;
    }

    public String getDriver_Mobile() {
        return Driver_Mobile;
    }

    public List<CustomerRequestList> getCustomerRequestLists() {
        return customerRequestLists;
    }
    public String getMsg() {
        return msg;
    }
    public String getInvoice() {
        return Invoice;
    }

    public String getPre_authori() {
        return pre_authori;
    }

}
