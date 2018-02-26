package com.cybermatrixsolutions.invoicesolutions.customer_module.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.With_QR.Driver_DashBoard;
import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.CustomerRO_List;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.DriverCustomer;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.model.Driver_Responce;
import com.cybermatrixsolutions.invoicesolutions.model.EMPDETAIL;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDriver extends AppCompatActivity {
Spinner spinner;
ArrayList<String>ROCode_Name;
    ArrayList<String>Pump_Legal_Name;
    Button submit;
    ArrayAdapter<String> fuelTypeAdapter;
    ArrayList<String>customer_code_arraylist;
    ArrayList<String>customer__name;
    ArrayList<String>pump_legal_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_ro);
        spinner=(Spinner)findViewById(R.id.spinner);
        submit=(Button)findViewById(R.id.submit);
        final PrefsManager pref=new PrefsManager(getApplicationContext());



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CustomerDriver.this,Driver_DashBoard.class);
                startActivity(i);
                pref.setLogin(true);
                finish();
            }
        });

        customer_code_arraylist=new ArrayList<>();
        customer__name=new ArrayList<>();
        pump_legal_name=new ArrayList<>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.white_color));
                  pref.setCustomer_Code(customer_code_arraylist.get(position));
                  pref.setCustomer_Name(customer__name.get(position));
                  pref.setpump_legal_name(pump_legal_name.get(position));
                  pref.setLogin(true);

//                  pref.createLogin_driver(employeeDetail.get(0).getRegistration_Number(),employeeDetail.get(0).getCustomer_Name(),employeeDetail.get(0).getPump_legal_name());


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getRoList(pref.getKey("key"));
    }

    private void getRoList(String imei) {
        PrefsManager pref=new PrefsManager(getApplicationContext());
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface apiService = com.cybermatrixsolutions.invoicesolutions.rest.ApiClient.getClient().create(com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface.class);
        Call<Driver_Responce> call=apiService.Customer_Driver(imei);
        call.enqueue(new Callback<Driver_Responce>() {
            @Override
            public void onResponse(Call<Driver_Responce> call, Response<Driver_Responce> response) {
                Driver_Responce loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")){
                    ArrayList<DriverCustomer>arrayList=loginResponse.getFirsresponse().getDriver_Customer();
                    if(arrayList!=null){
                        for(int i=0;i<arrayList.size();i++){
                            customer_code_arraylist.add(arrayList.get(i).getCustomer_Code());
                            customer__name.add(arrayList.get(i).getCompany_name());
                            pump_legal_name.add(arrayList.get(i).getPump_legal_name());

                        }
                        fuelTypeAdapter = new ArrayAdapter<String>(CustomerDriver.this,R.layout.simple_spinner_dropdown_item, CustomerDriver.this.customer_code_arraylist);
                        CustomerDriver.this.spinner.setAdapter(fuelTypeAdapter);
                    }








                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(CustomerDriver.this,"Login Failed..!!",R.drawable.dont_sign);
                }

            }
            @Override
            public void onFailure(Call<Driver_Responce> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(CustomerDriver.this,"Connection Failed..!!",R.drawable.dont_sign);

            }
        });

    }
}
