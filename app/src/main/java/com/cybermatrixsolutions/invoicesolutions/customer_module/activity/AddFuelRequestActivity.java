package com.cybermatrixsolutions.invoicesolutions.customer_module.activity;

import android.R.layout;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cybermatrixsolutions.invoicesolutions.*;
import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.DriversList;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface;

import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFuelRequestActivity extends AppCompatActivity implements OnClickListener {

    ArrayList<String>arrayList;
    Spinner request_type;
    Spinner vehicle_reg_no;

    ArrayList<String>register_no_arrayList;
    ArrayList<String>arrayListFuelType;
    ArrayList<String>arrayListFuelTypeid;
  //  Spinner fuel_type;
    EditText request_value,driver_mobile;
    Button submitbutton;
    LinearLayout request_value_layout;
    String vehicle_type_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_fuel_request);
        this.findViewById(R.id.back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFuelRequestActivity.this.finish();
            }
        });
        this.request_type = (Spinner) this.findViewById(R.id.request_type);
        this.request_value = (EditText) this.findViewById(R.id.request_value);
        this.driver_mobile = (EditText) this.findViewById(R.id.driver_mobile);
        request_value.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        driver_mobile.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        this.submitbutton = (Button) this.findViewById(R.id.submitbutton);
        this.request_value_layout = (LinearLayout) this.findViewById(R.id.request_value_layout);
        this.getvehicle_reg_no();
        this.submitbutton.setOnClickListener(this);
        this.arrayList =new ArrayList<>();
        this.arrayList.add("Full Tank");
        this.arrayList.add("Ltr");
        this.arrayList.add("Rs");
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this, layout.simple_spinner_dropdown_item, this.arrayList);
        this.request_type.setAdapter(adapter);
        this.vehicle_reg_no = (Spinner) this.findViewById(R.id.vehicle_reg_no);
        this.register_no_arrayList =new ArrayList<>();
        this.arrayListFuelType =new ArrayList<>();
        this.arrayListFuelTypeid =new ArrayList<>();
        this.request_type.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(AddFuelRequestActivity.this.request_type.getSelectedItem().toString().equals("Full Tank"))
                {

                    AddFuelRequestActivity.this.request_value_layout.setVisibility(View.GONE);

                }else {
                    AddFuelRequestActivity.this.request_value_layout.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void getvehicle_reg_no() {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.getVehicleList(manager.getKey("key"),manager.getCustomer_Code());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                register_no_arrayList.add("Select Vehicle");
                if(status.equals("success")) {
                    List<DriversList> arrayList = loginResponse.getFirsresponse().getVehicleList();
                    if(arrayList!=null){
                        for(int i=0;i<arrayList.size();i++){
                        String registration_number=arrayList.get(i).getRegistration_Number();
                        AddFuelRequestActivity.this.register_no_arrayList.add(registration_number);
                    }
                    ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(AddFuelRequestActivity.this, layout.simple_spinner_dropdown_item, AddFuelRequestActivity.this.register_no_arrayList);
                    AddFuelRequestActivity.this.vehicle_reg_no.setAdapter(arrayAdapter);
                    vehicle_reg_no.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String vehicle_number=register_no_arrayList.get(position);
                            Log.e("vehicle_number---",vehicle_number);
                            callVehicleTypeApi(vehicle_number);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    }else {
                       CustomDialog.customDialogwithsingleButton(AddFuelRequestActivity.this,"Vehicle Not Found..!!!",R.drawable.dont_sign);
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(AddFuelRequestActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view== this.submitbutton){
            this.checkvalidation();
        }
    }
    private boolean checkvalidation() {
        String reg_no= this.vehicle_reg_no.getSelectedItem().toString();
        String sp_request_type= this.request_type.getSelectedItem().toString();
        String ed_request_value= this.request_value.getText().toString();
        String dmobile=this.driver_mobile.getText().toString();
        if(sp_request_type.equals("Full Tank")){
            if(dmobile.length()!=10){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddFuelRequestActivity.this,"Enter Valid 10 Digit Mobile Number...!!!",R.drawable.dont_sign);
                return false;
            }if(reg_no.contains("Select")){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddFuelRequestActivity.this,"Please Select Vehicle...!!!",R.drawable.dont_sign);
                return false;
            }
            ed_request_value="0";
            this.callFuelRequestApi(reg_no,vehicle_type_id,sp_request_type,ed_request_value,dmobile);
            return true;
        }
        else {
            if(ed_request_value.length()==0){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddFuelRequestActivity.this,"Enter Request Value...!!!",R.drawable.dont_sign);
         return false;
            }if(dmobile.length()!=10){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddFuelRequestActivity.this,"Enter Valid 10 Digit Mobile No...!!!",R.drawable.dont_sign);
                return false;
            }if(reg_no.contains("Select Vehicle")){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddFuelRequestActivity.this,"Please Select Vehicle...!!!",R.drawable.dont_sign);
                return false;
            }
        }
        this.callFuelRequestApi(reg_no, this.vehicle_type_id,sp_request_type,ed_request_value,dmobile);
        return true;
    }
    private void callFuelRequestApi(String reg_no, String sp_fuel_type, String sp_request_type, String ed_request_value,String dmobile) {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.addFuelRequest(manager.getKey("key"),reg_no,sp_fuel_type,sp_request_type,ed_request_value,"11314",dmobile,manager.getCustomer_Code());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                String msg=loginResponse.getFirsresponse().getMsg();
                pb.dismiss();
                if(status.equals("success")) {
                    success_dialog(msg);
                }
                else  if(msg.contains("request already")){
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddFuelRequestActivity.this,"One request already pending for this vehicle..!!!",R.drawable.dont_sign);
                }
                else {
                        Toast.makeText(AddFuelRequestActivity.this,"Please Login Again",Toast.LENGTH_LONG).show();
                        new PrefsManager(AddFuelRequestActivity.this).clearAllData();
                        AddFuelRequestActivity.this.startActivity(new Intent(AddFuelRequestActivity.this, LoginActivity.class));
                        AddFuelRequestActivity.this.finish();

                    }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddFuelRequestActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }
        });
    }

    private void callVehicleTypeApi(String reg_no) {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.getVehicleType(manager.getKey("key"),reg_no);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")) {
                    vehicle_type_id=loginResponse.getFirsresponse().getVehiclefueltype();
                    if(loginResponse.getFirsresponse().getDriver()!=null){
                        driver_mobile.setText(loginResponse.getFirsresponse().getDriver());
                    }
                }
                else {
                    Toast.makeText(AddFuelRequestActivity.this,"Please Login Again",Toast.LENGTH_LONG).show();
                    new PrefsManager(AddFuelRequestActivity.this).clearAllData();
                    AddFuelRequestActivity.this.startActivity(new Intent(AddFuelRequestActivity.this, LoginActivity.class));
                    AddFuelRequestActivity.this.finish();
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddFuelRequestActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }
        });

    }
    public void success_dialog(String status){
        final Dialog ab=new Dialog(AddFuelRequestActivity.this);
        ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view= LayoutInflater.from(AddFuelRequestActivity.this).inflate(R.layout.popdialogwithsinglebutton,null,false);
        ab.setContentView(R.layout.popdialogwithsinglebutton);
        TextView alertmessage=(TextView)ab.findViewById(R.id.alertmessage);
        ImageView image=(ImageView)ab.findViewById(R.id.image);
        image.setImageResource(R.drawable.successimage);
        TextView clickok=(TextView)ab.findViewById(R.id.clickok);
        alertmessage.setText(status);
        ab.setCancelable(false);
        clickok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ab.dismiss();
                AddFuelRequestActivity.this.startActivity(new Intent(AddFuelRequestActivity.this,FuelRequestListActivity.class));
                AddFuelRequestActivity.this.finish();
            }
        });
        ab.show();
    }

}
