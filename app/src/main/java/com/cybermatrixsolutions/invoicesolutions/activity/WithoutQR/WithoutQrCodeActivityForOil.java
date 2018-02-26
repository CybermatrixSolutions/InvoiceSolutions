package com.cybermatrixsolutions.invoicesolutions.activity.WithoutQR;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequestList;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequestResponse;
import com.cybermatrixsolutions.invoicesolutions.model.Customer_full_detail;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithoutQrCodeActivityForOil extends AppCompatActivity {
    private String KEY,Customer_Code,Vehicle_No,Ro_code;
    List<CustomerRequestList> customerRequestLists;
    Intent i;
    private Button verifyDriverMobileNUmber;
    private EditText et_mobile,et_otp;
    String Mobile, Otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witout_qr_for_oil);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        PrefsManager manager=new PrefsManager(this);
        KEY=manager.getKey("key");
        verifyDriverMobileNUmber=(Button)findViewById(R.id.send);
        et_mobile=(EditText)findViewById(R.id.mobileNumber);
        et_otp=(EditText)findViewById(R.id.et_pin);
        verifyDriverMobileNUmber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();

            }
        });
        checkshiftOpen();

    }
    private boolean Validation() {
         Mobile=et_mobile.getText().toString().trim();
         Otp=et_otp.getText().toString().trim();
        if(Mobile.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(WithoutQrCodeActivityForOil.this,"Please Enter Mobile Number",R.drawable.dont_sign);
            return false;
        }
        if(Mobile.length()!=10){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(WithoutQrCodeActivityForOil.this,"Please Enter 10 digits Mobile Number...!!!",R.drawable.dont_sign);
            return false;
        }
        if(Otp.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(WithoutQrCodeActivityForOil.this,"Please Enter Otp...!!!",R.drawable.dont_sign);
            return false;
        }
        CallCustomerRequestApi(Mobile,Otp);
        return true;
    }
    public void CallCustomerRequestApi(final String mobile, final String Otp){
        final ProgressDialog pb = new ProgressDialog(WithoutQrCodeActivityForOil.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.withoutQrCodeRequestresponse(mobile, Otp, KEY,"petrol");
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse=response.body();
                String status=requestResponse.getStatus();
                pb.dismiss();
                CustomerRequestResponse customerRequestResponse = requestResponse.getCustomerRequestResponse();
                String msg=customerRequestResponse.getMsg();
                if(status.equalsIgnoreCase("success")) {
                    String key = customerRequestResponse.getKey();
                    List<Customer_full_detail>customer_full_detail=customerRequestResponse.getCustomer_full_detail();
                    if(customer_full_detail!=null){
                        i=new Intent(WithoutQrCodeActivityForOil.this,PetrolCustomerDetailWithoutQrCodeActivity.class);
                        i.putExtra("Customer_Name",customerRequestResponse.getCustomer_Name());
                        i.putExtra("Customer_Code",customerRequestResponse.getCustomer_Code());
                        i.putExtra("Credit_limit",customerRequestResponse.getCredit_limit());
                        i.putExtra("Registration_Number",customerRequestResponse.getRegistration_Number());
                        i.putExtra("Driver_Name",customerRequestResponse.getDriver_Name());
                        i.putExtra("Driver_Mobile",customerRequestResponse.getDriver_Mobile());
                        i.putExtra("id",customer_full_detail.get(0).getId());
                        i.putExtra("RO_code",customer_full_detail.get(0).getRO_code());
                        i.putExtra("address_one",customer_full_detail.get(0).getAddress_one());
                        i.putExtra("Customer_Type",customer_full_detail.get(0).getCustomer_Type());
                        i.putExtra("Phone_Number",customer_full_detail.get(0).getPhone_Number());
                        i.putExtra("Mobile",customer_full_detail.get(0).getMobile());
                        i.putExtra("IMEI_No",customer_full_detail.get(0).getIMEI_No());
                        i.putExtra("Email",customer_full_detail.get(0).getEmail());
                        i.putExtra("gst_no",customer_full_detail.get(0).getGst_no());
                        i.putExtra("company_name",customer_full_detail.get(0).getCompany_name());
                        i.putExtra("address_two",customer_full_detail.get(0).getAddress_two());
                        i.putExtra("address_three",customer_full_detail.get(0).getAddress_three());
                        i.putExtra("pin_no",customer_full_detail.get(0).getPin_no());
                        i.putExtra("pan_no",customer_full_detail.get(0).getPan_no());
                        i.putExtra("statefinal",customer_full_detail.get(0).getState());
                        i.putExtra("cityfinal",customer_full_detail.get(0).getCity());
                        i.putExtra("state_code",customer_full_detail.get(0).getGstcode());
                        customerRequestLists=customerRequestResponse.getCustomerRequestLists();
                        if(customerRequestLists!=null){
                            String date="";
                            try {
                                 date=customerRequestLists.get(0).getRequest_date();
                                 String year=customerRequestLists.get(0).getRequest_date().substring(0,4);
                                 String month=customerRequestLists.get(0).getRequest_date().substring(5,7);
                                 String day=customerRequestLists.get(0).getRequest_date().substring(8,10);
                                 date=day+"/"+month+"/"+year;
                            }catch (Exception e){

                            }i.putExtra("item_code",customerRequestLists.get(0).getItem_code());
                            i.putExtra("price",customerRequestLists.get(0).getPrice1());
                            i.putExtra("id",customerRequestLists.get(0).getId());
                            i.putExtra("request_id",customerRequestLists.get(0).getRequest_id());
                            i.putExtra("Request_Type",customerRequestLists.get(0).getRequest_Type());
                            i.putExtra("Request_Value",customerRequestLists.get(0).getRequest_Value());
                            i.putExtra("Petrol_Diesel_Type",customerRequestLists.get(0).getPetrol_Diesel_Type());
                            i.putExtra("request_date",date);
                            i.putExtra("current_driver_mobile",customerRequestLists.get(0).getCurrent_driver_mobile());
                            customerRequestLists.clear();
                        }else {
                            i.putExtra("item_code","");
                            i.putExtra("price","");
                            i.putExtra("id","");
                            i.putExtra("request_id","");
                            i.putExtra("Request_Type","");
                            i.putExtra("Request_Value","");
                            i.putExtra("Petrol_Diesel_Type","");
                            i.putExtra("request_date","");
                            i.putExtra("current_driver_mobile","");
                        }
                        CallLubeCustomerRequestApi(Mobile,Otp);
                    }
                }
                else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(WithoutQrCodeActivityForOil.this,msg,R.drawable.dont_sign);
                }
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(WithoutQrCodeActivityForOil.this,"Network Error...!!!",R.drawable.dont_sign);
            }
        });
    }
    public void CallLubeCustomerRequestApi(String mobile, final String Otp){
        final ProgressDialog pb = new ProgressDialog(WithoutQrCodeActivityForOil.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.withoutQrCodeRequestresponse(mobile, Otp, KEY,"lube");
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse=response.body();
                String status=requestResponse.getStatus();
                if(status.equalsIgnoreCase("success")) {
                    CustomerRequestResponse customerRequestResponse = requestResponse.getCustomerRequestResponse();
                    String key = customerRequestResponse.getKey();
                    pb.dismiss();
                    customerRequestLists=customerRequestResponse.getCustomerRequestLists();
                    double total = 0;
                    if(customerRequestLists!=null){
                        JSONArray array=new JSONArray();
                        for(int j=0;j<customerRequestLists.size();j++){
                            String date="";
                            try {
                                try {date=customerRequestLists.get(0).getRequest_date();
                                    String year=customerRequestLists.get(0).getRequest_date().substring(0,4);
                                    String month=customerRequestLists.get(0).getRequest_date().substring(5,7);
                                    String day=customerRequestLists.get(0).getRequest_date().substring(8,10);
                                    date=day+"/"+month+"/"+year;
                                }catch (Exception e){
                                }
                                JSONObject object=new JSONObject();
                                object.put("luberequest_id",customerRequestLists.get(j).getRequest_id());
                                object.put("luberequest_date",date);
                                object.put("lubeid",customerRequestLists.get(j).getId());
                                object.put("lubeprice",customerRequestLists.get(j).getPrice());
                                object.put("lubeitem_name",customerRequestLists.get(j).getItem_code());
                                object.put("lubecurrent_driver_mobile",customerRequestLists.get(j).getCurrent_driver_mobile());
                                object.put("quantity",customerRequestLists.get(j).getQuantity());
                                array.put(object);
                            }catch (Exception e) {
                            }
                        }
                        i.putExtra("lubedata",array.toString());
                    }
                    startActivity(i);
                    finish();
                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(WithoutQrCodeActivityForOil.this,"Invalid Request...!!!",R.drawable.dont_sign);
                }
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(WithoutQrCodeActivityForOil.this,"Connection Failed...!!!",R.drawable.dont_sign);

            }
        });
    }
    private void checkshiftOpen() {
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait Checking Shift.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.checkshiftopen(KEY);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse=response.body();
                String status=requestResponse.getStatus();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")) {
                }
                else {
                    CustomDialog.customDialogwithsingleButton(WithoutQrCodeActivityForOil.this,"Shift Not Open...!!!",R.drawable.dont_sign);
                }
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(WithoutQrCodeActivityForOil.this,"Connection Failed...!!!",R.drawable.dont_sign);

            }
        });


    }

}
