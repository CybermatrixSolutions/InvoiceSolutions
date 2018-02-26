package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diwakar on 11/1/2017.
 */

public class FirstTimeLoginResponse {

    @SerializedName("msg")
    private String msg;

    @SerializedName("key")
    private String key;

    @SerializedName("ID")
    private String ID;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("Vehicle Fuel Type")
    private String vehiclefueltype;
    @SerializedName("Driver")
    private String Driver;



    @SerializedName("Drivers")
    private List<DriversList> driversLists;

    @SerializedName("Vehicles")
    private List<DriversList> vehicleList;

    public List<DriversList> getVehicleList() {
        return this.vehicleList;
    }

    public List<DriversList> getDriversLists() {
        return this.driversLists;
    }

    @SerializedName("EMPDETAIL")
    private List<EmployeeDetail> employeeDetail;

    @SerializedName("Fule list")
    private List<FuelType> fuelTypes;

    @SerializedName("Item Name list")
    private ArrayList<LubeType>lubeTypes;

    public String getVehiclefueltype() {
        return this.vehiclefueltype;
    }

    public ArrayList<LubeType>getLubeTypes(){

        return this.lubeTypes;
    }
    @SerializedName("Make")
    private ArrayList<MakeModel>Make;
    public ArrayList<MakeModel> getMake() {
        return Make;
    }

    @SerializedName("Model")
    private ArrayList<MakeModel>Model;
    public ArrayList<MakeModel> getModel() {
        return Model;
    }

    @SerializedName("capacity")
    private ArrayList<Capacity>capacity;
    public ArrayList<Capacity> getCapacity() {
        return capacity;
    }

    @SerializedName("Customer RO")
    private ArrayList<CustomerRO_List>Customer_RO;
    public ArrayList<CustomerRO_List> getCustomer_RO() {
        return Customer_RO;
    }







    public List<FuelType> getFuelTypes() {
        return this.fuelTypes;
    }

    public List<EmployeeDetail> getEmployeeDetail() {
        return this.employeeDetail;
    }
    public String getID() {
        return this.ID;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getKey() {
        return this.key;
    }

    public String getDriver() {
        return Driver;
    }

}
