package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 11/1/2017.
 */

public class CustomerRequestList implements Serializable {


    @SerializedName("Petrol_Diesel_Type")
    private String Petrol_Diesel_Type;

    @SerializedName("request_date")
    private String request_date;

    @SerializedName("request_id")
    private String request_id;

    @SerializedName("Request_Value")
    private String Request_Value;

    @SerializedName("Request_Type")
    private String Request_Type;


    ///for lube request
    @SerializedName("item_code")
    private String item_code;
    @SerializedName("id")
    private String id;
    public void setId(String id) {
        this.id = id;
    }



    @SerializedName("Price")
    private String Price;

    @SerializedName("price")
    private String Price1;

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @SerializedName("Volume_ltr")
    private String Volume_ltr;
    @SerializedName("current_driver_mobile")
    private String current_driver_mobile;

    @SerializedName("quantity")
    private String quantity;


    public String getId() {
        return id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getPrice1() {
        return Price1;
    }

    public void setPrice1(String price1) {
        Price1 = price1;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_Type(String request_Type) {
        Request_Type = request_Type;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setVolume_ltr(String volume_ltr) {
        Volume_ltr = volume_ltr;
    }

    public String getRequest_Type() {
        return Request_Type;
    }

    public String getItem_code() {
        return item_code;
    }

    public String getPrice() {
        return Price;
    }

    public String getVolume_ltr() {
        return Volume_ltr;
    }

    public void setPetrol_Diesel_Type(String petrol_Diesel_Type) {
        Petrol_Diesel_Type = petrol_Diesel_Type;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public void setRequest_Value(String request_Value) {
        Request_Value = request_Value;
    }

    public void setRegistration_Number(String Request_Type) {
        Request_Type = Request_Type;
    }

    public String getPetrol_Diesel_Type() {
        return Petrol_Diesel_Type;
    }

    public String getRequest_date() {
        return request_date;
    }

    public String getRequest_Value() {
        return Request_Value;
    }

    public String getRequestType() {
        return Request_Type;
    }
    public String getCurrent_driver_mobile() {
        return current_driver_mobile;
    }
    public String getQuantity() {
        return quantity;
    }


}
