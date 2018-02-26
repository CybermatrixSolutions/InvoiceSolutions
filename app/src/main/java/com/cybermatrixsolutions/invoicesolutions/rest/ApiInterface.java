package com.cybermatrixsolutions.invoicesolutions.rest;

import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.Driver_Responce;
import com.cybermatrixsolutions.invoicesolutions.model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.model.Ropedestal;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dhiraj on 08-Jun-17.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> firstTimelogin(@Field("mobile") String email, @Field("IMEI_No") String IMEI_No);
    @FormUrlEncoded
    @POST("setpin.php")
    Call<LoginResponse> generatepin(@Field("mobile") String mobile,@Field("id") String id,@Field("pin") String pin,@Field("type") String type);
    @FormUrlEncoded
    @POST("employeelogin.php")
    Call<LoginResponse> employeeLogin(@Field("IMEI_No") String IMEI_No,@Field("pin") String pin,@Field("type") String type,@Field("mobile") String mobile);
    @FormUrlEncoded
    @POST("QRcode.php")
    Call<CustomerRequest> requestForDieselPetrol(@Field("Vehicle_No") String Vehicle_No,@Field("Customer_Code") String Customer_Code,
                                                 @Field("key") String key,@Field("type") String type,@Field("qrcode") String qrcode);@FormUrlEncoded
    @POST("WithoutQRcode.php")
    Call<CustomerRequest> withoutQrCodeRequestresponse(@Field("Driver_Mobile") String Driver_Mobile,@Field("OTP") String OTP,@Field("key") String key,@Field("type") String type);
    @FormUrlEncoded
    @POST("Petroltype.php")
    Call<CustomerRequest>fueltypeList(@Field("key") String key);
    @FormUrlEncoded
    @POST("stateslist.php")
    Call<CustomerRequest> getStatelist(@Field("key") String key);
    @FormUrlEncoded
    @POST("checkshiftopen.php")
    Call<CustomerRequest> checkshiftopen(@Field("key") String key);
    @FormUrlEncoded
    @POST("showqr.php")
    Call<CustomerRequest> showqr(@Field("key") String key,@Field("customer_code") String customer_code);
    @FormUrlEncoded
    @POST("showotp.php")
    Call<CustomerRequest> showotp(@Field("key") String key);
    @FormUrlEncoded
    @POST("product.php")
    Call<CustomerRequest>lubetypeList(@Field("key") String key);
    @FormUrlEncoded
    @POST("productmisc.php")
    Call<CustomerRequest> productmisc(@Field("key") String key);
    @FormUrlEncoded
    @POST("RO_Pedestal.php")
    Call<Ropedestal> Ropedestal(@Field("key") String key);
    @FormUrlEncoded
    @POST("NOZZLE_READING.php")
    Call<CustomerRequest>sendNozzleEndReading(@Field("key") String key, @Field("nozzle_nos") String nozzle_nos, @Field("nozzle_read") String nozzle_read, @Field("Reading_Date")String Reading_Date, @Field("Reading_type") String Reading_type, @Field("test") String test, @Field("reading") String reading);
    @FormUrlEncoded
    @POST("RO_Pedestal_NOZZLE.php")
    Call<CustomerRequest> nozzleReading(@Field("key") String key);
    @FormUrlEncoded
    @POST("settlement.php")
    Call<CustomerRequest> settlement(@Field("key") String key, @Field("trans_date")String trans_date, @Field("shift_id")String shift_id);
    @FormUrlEncoded
    @POST("product_tax.php")
    Call<CustomerRequest>product_tax(@Field("key") String key, @Field("items")String items, @Field("ro_state")String ro_state, @Field("cus_state")String cus_state);
    @FormUrlEncoded
    @POST("settlement_trans.php")
    Call<CustomerRequest> settlementdone(@Field("key") String key,@Field("shift_id") String shift_id,@Field("Nozzle_Amount")String Nozzle_Amount,
                                         @Field("Credit_fuel")String Credit_fuel,@Field("mode")String mode,
                                         @Field("amount")String amount,
                                         @Field("Diff")String Diff
                                        );
    @FormUrlEncoded
    @POST("settlement_trans_others.php")
    Call<CustomerRequest> settlement_trans_others(@Field("key") String key,@Field("shift_id") String shift_id,
                                         @Field("Credit_lube")String Credit_lube,@Field("mode")String mode,
                                         @Field("amount")String amount,
                                         @Field("Diff")String Diff);
    @FormUrlEncoded
    @POST("Cust_trans.php")
    Call<CustomerRequest> transaction(@Field("key") String key, @Field("customer_code")String customer_code,@Field("id")
            String item_code,@Field("item_price") String item_price,@Field("petrol_or_lube")String petrol_or_lube,@Field("slip_detail")
            String slip_detail,@Field("petroldiesel_type")String petroldiesel_type,@Field("petroldiesel_qty")String petroldiesel_qty,@Field("Request_id")
            String Request_id,@Field("Vehicle_Reg_No")String Vehicle_Reg_No,@Field("Driver_Mobile")
            String Driver_Mobile,@Field("Trans_By")String Trans_By,
            @Field("cust_type")String cust_type,@Field("Total")String Total,@Field("cust_name")String cust_name,@Field("Cust_GST")String Cust_GST,@Field("Cust_mobile")String Cust_mobile,@Field("Trans_Mobile")String Trans_Mobile);

    @FormUrlEncoded
    @POST("Cust_trans.php")
    Call<CustomerRequest> transaction1(@Field("key") String key, @Field("customer_code")String customer_code,@Field("id")
            String item_code,@Field("item_price") String item_price,@Field("petrol_or_lube")String petrol_or_lube,@Field("slip_detail")
                                              String slip_detail,@Field("petroldiesel_type")String petroldiesel_type,@Field("petroldiesel_qty")String petroldiesel_qty,@Field("Request_id")
                                              String Request_id,@Field("Vehicle_Reg_No")String Vehicle_Reg_No,@Field("Driver_Mobile")
                                              String Driver_Mobile,@Field("Trans_By")String Trans_By,
                                      @Field("cust_type")String cust_type,@Field("Total")String Total,@Field("cust_name")String cust_name,@Field("Cust_GST")String Cust_GST,@Field("Cust_mobile")String Cust_mobile,@Field("Trans_Mobile")String Trans_Mobile,@Field("qty")String qty);



    @FormUrlEncoded
    @POST("shiftallocation.php")
    Call<CustomerRequest> getshiftallocation(@Field("key") String key);
    @FormUrlEncoded
    @POST("shiftdetail.php")
    Call<CustomerRequest> shiftdetail(@Field("key") String key,@Field("type") String type);
    @FormUrlEncoded
    @POST("paymentmode.php")
    Call<CustomerRequest>paymentmode(@Field("key") String key);
    @FormUrlEncoded
    @POST("settlement_others.php")
    Call<CustomerRequest>settlement_others(@Field("key") String key,@Field("shift_id") String shift_id);



    @FormUrlEncoded
    @POST("employeelogin.php")
    Call<Driver_Responce>employeeLogindriver(@Field("IMEI_No") String IMEI_No,@Field("pin") String pin,@Field("type") String type);

    @FormUrlEncoded
    @POST("Get_Customer_Driver.php")
    Call<Driver_Responce>Customer_Driver(@Field("key")String key);


    @FormUrlEncoded
    @POST("shiftallocation_trans.php")
    Call<CustomerRequest> shift_trans(@Field("key") String key,@Field("pedestal") String pedestal,@Field("personal") String personal);
    @FormUrlEncoded
    @POST("validateOtp.php")
    Call<CustomerRequest>verify_otp(@Field("key") String key,@Field("mobile") String pedestal,@Field("OTP") String personal);
}
