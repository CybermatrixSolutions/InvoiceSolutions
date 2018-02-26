package com.cybermatrixsolutions.invoicesolutions.customer_module.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.CustomerRO_List;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.FuelType;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface;

import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRO extends AppCompatActivity {
Spinner spinner;
ArrayList<String>ROCode_Name;
    ArrayList<String>Pump_Legal_Name;
    Button submit;
    ArrayAdapter<String> fuelTypeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_ro);
        spinner=(Spinner)findViewById(R.id.spinner);
        submit=(Button)findViewById(R.id.submit);
        final PrefsManager manager=new PrefsManager(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CustomerRO.this,DashboardActivity.class);
                startActivity(i);
                manager.setLogin(true);
                finish();
            }
        });

        ROCode_Name=new ArrayList<>();
        Pump_Legal_Name=new ArrayList<>();
        getRoList(manager.getKey("key"));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.white_color));
                 manager.setCustomer_Code(ROCode_Name.get(position));

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getRoList(String key) {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.Get_Customer_RO(manager.getKey("key"));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")) {
                    List<CustomerRO_List> arrayList = loginResponse.getFirsresponse().getCustomer_RO();
                    if(arrayList!=null) {
                        for (int i = 0; i < arrayList.size(); i++) {
                            CustomerRO.this.Pump_Legal_Name.add(arrayList.get(i).getPump_legal_name());
                            CustomerRO.this.ROCode_Name.add(arrayList.get(i).getCustomer_Code());
                        }
                        fuelTypeAdapter = new ArrayAdapter<String>(CustomerRO.this,R.layout.simple_spinner_dropdown_item, CustomerRO.this.ROCode_Name);
                        CustomerRO.this.spinner.setAdapter(fuelTypeAdapter);
                    }else {
                        CustomDialog.customDialogwithsingleButton(CustomerRO.this,"Failed..!!!",R.drawable.dont_sign);
                    }

                }}
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(CustomerRO.this,"Connection Failed..!!!",R.drawable.dont_sign);
            }
        });


    }
}
