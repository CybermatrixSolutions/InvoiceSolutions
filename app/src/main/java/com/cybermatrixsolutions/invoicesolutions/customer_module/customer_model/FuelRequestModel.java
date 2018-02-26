package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Diwakar on 11/30/2017.
 */

public class FuelRequestModel implements Serializable {

   @SerializedName("Vehicle_Reg_No" )
    private String vehicle_reg_No;

    @SerializedName("request_date")
    private String request_date;

    @SerializedName("Item_Name")
    private String Item_Name;

    @SerializedName("Request_Type")
    private String Request_Type;

    @SerializedName("Request_Value")
    private String Request_Value;

    @SerializedName("Execution_date")
    private String Execution_date;

    @SerializedName("Registration_Number")
    private String Registration_Number;

    @SerializedName("id")
    private String id;



    public ArrayList<FuelRequestModel> getFuelRequestModels() {

        return this.fuelRequestModels;
    }

    public ArrayList<FuelRequestModel> getLubeRequestModels() {

        return this.lubeRequestList;
    }
    public ArrayList<FuelRequestModel> getVehiclesList() {

        return this.VehiclesList;
    }
    @SerializedName("quantity")
    private String quantity;
    public String getQuantity() {
        return quantity;
    }


    @SerializedName("FUELREQUEST")
    private ArrayList<FuelRequestModel>fuelRequestModels;

    @SerializedName("LUBEREQUEST")
    private ArrayList<FuelRequestModel> lubeRequestList;

    @SerializedName("Vehicles")
    private ArrayList<FuelRequestModel> VehiclesList;

    @SerializedName("request_id")
    private String request_id;

    public String getVehicle_reg_No() {
        return this.vehicle_reg_No;
    }

    public void setVehicle_reg_No(String vehicle_reg_No) {
        this.vehicle_reg_No = vehicle_reg_No;
    }

    public String getRequest_date() {
        return this.request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getItem_Name() {
        return this.Item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.Item_Name = item_Name;
    }

    public String getRequest_Type() {
        return this.Request_Type;
    }

    public void setRequest_Type(String request_Type) {
        this.Request_Type = request_Type;
    }

    public String getRequest_Value() {
        return this.Request_Value;
    }

    public void setRequest_Value(String request_Value) {
        this.Request_Value = request_Value;
    }

    public String getExecution_date() {
        return this.Execution_date;
    }

    public void setExecution_date(String execution_date) {
        this.Execution_date = execution_date;
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }





}
