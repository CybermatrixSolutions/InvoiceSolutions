package com.cybermatrixsolutions.invoicesolutions.customer_module.rest;




import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.FuelRequest;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dhiraj on 08-Jun-17.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("employeelogin.php")
    Call<LoginResponse> firstTimelogin(@Field("IMEI_No") String IMEI_No,@Field("pin") String pin,@Field("type") String type,@Field("empid") String empid);

    @FormUrlEncoded
    @POST("ViewFuelrequest.php")
    Call<FuelRequest> viewFuelRequest(@Field("key") String key, @Field("Customer_Code") String Customer_Code);

    @FormUrlEncoded
    @POST("ViewLuberequest.php")
    Call<FuelRequest> viewLubeRequest(@Field("key") String key, @Field("Customer_Code") String Customer_Code);

    @FormUrlEncoded
    @POST("Drivers.php")
    Call<LoginResponse> getDriverList(@Field("key") String key, @Field("Customer_Code") String Customer_Code);

    @FormUrlEncoded
    @POST("AddVehicle.php")
    Call<LoginResponse> AddVehicle(@Field("key") String key, @Field("Registration_Number") String Registration_Number,
                                   @Field("Fuel_Type") String Fuel_Type, @Field("insurance_due_date") String insurance_due_date, @Field("puc_date") String puc_date,
                                   @Field("van_color") String van_color, @Field("Model") String Model, @Field("Make") String Make, @Field("rc_valide") String rc_valide, @Field("capacity") String capacit, @Field("Customer_Code") String Customer_Code);

    @FormUrlEncoded
    @POST("Vehicle.php")
    Call<LoginResponse>getVehicleList(@Field("key") String key,@Field("Customer_Code") String Customer_Code);

    @FormUrlEncoded
    @POST("make.php")
    Call<LoginResponse> make(@Field("key") String key);
    @FormUrlEncoded
    @POST("capacity.php")
    Call<LoginResponse>capacity(@Field("key") String key,@Field("modelname") String modelname);
    @FormUrlEncoded
    @POST("model.php")
    Call<LoginResponse> model(@Field("key") String key,@Field("make") String make);

    @FormUrlEncoded
    @POST("AddlubeRequest.php")
    Call<LoginResponse> addLubeRequest(@Field("key") String key, @Field("Item_Code") String item_id, @Field("Vehicle_Reg_No") String Vehicle_Reg_No,
                                       @Field("request_id") String request_id,@Field("dmobile") String dmobile, @Field("Customer_Code") String Customer_Code,@Field("Item_qty") String Item_qty);

    @FormUrlEncoded
    @POST("employeelogin.php")
    Call<LoginResponse> employeeLogin(@Field("IMEI_No") String IMEI_No, @Field("pin") String pin);

    @FormUrlEncoded
    @POST("AddDriver.php")
    Call<LoginResponse>adddriver(@Field("key") String Key, @Field("Registration_Number") String REG_NO, @Field("Driver_Name") String Driver_name, @Field("email") String Driver_email, @Field("Driver_Licence_No") String Driver_licence_no, @Field("valid_upto") String Valid_upto, @Field("Driver_Mobile") String mobile, @Field("Customer_Code") String Customer_Code);

    @FormUrlEncoded
    @POST("AddfuelRequest.php")
    Call<LoginResponse> addFuelRequest(@Field("key") String Key, @Field("Vehicle_Reg_No") String REG_NO, @Field("PetrolDiesel_Type") String PetrolDiesel_Type, @Field("Request_Type") String Request_Type, @Field("Request_Value") String request_value, @Field("request_id") String request_id, @Field("dmobile") String dmobile, @Field("Customer_Code") String Customer_Code);
    @FormUrlEncoded
    @POST("customerpetrollist.php")
    Call<LoginResponse> getfueltype(@Field("key") String key);

    @FormUrlEncoded
    @POST("lubelist.php")
    Call<LoginResponse> getlubelist(@Field("key") String key);

    @FormUrlEncoded
    @POST("Get_Driver.php")
    Call<LoginResponse> getDriver(@Field("key") String key,@Field("vehicle") String vehicle);

    @FormUrlEncoded
    @POST("Get_Customer_RO.php")
    Call<LoginResponse>Get_Customer_RO(@Field("key")String key);


    @FormUrlEncoded
    @POST("vehiclefueltype.php")
    Call<LoginResponse> getVehicleType(@Field("key") String key, @Field("Registration_Number") String Registration_Number);

}
