package com.cybermatrixsolutions.invoicesolutions.customer_module.activity;

import android.R.layout;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.Capacity;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.FuelType;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.MakeModel;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVehicleActivity extends AppCompatActivity implements OnClickListener {

    TextView insurance_due_date,valid_upto,rc;
    EditText vehicle_number,color,capacity,makeEntry,modelEntry;
    String VehicleNumber,Make,Model,Color,InsuranceDueDate,ValidUpto,FuelType1,getrc,getcapacity;

    private Button submit;
    ArrayList<String> arrayListFuelType;
    ArrayList<String>arrayListFuelTypeid;
    String sp_fuel_type;
    Spinner fuel_type,make,model;
    LinearLayout makelinear;
ArrayList<String>makename;
    ArrayList<String>modelname;
ArrayList<String>makeid;
LinearLayout rclinear,makelinear_Other,model_other_linear,model_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_vehicle);
        this.findViewById(R.id.back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVehicleActivity.this.finish();
            }
        });
        this.valid_upto = (TextView) this.findViewById(R.id.valid_upto);
        this.insurance_due_date = (TextView) this.findViewById(R.id.insurance_due_date);
        rc= (TextView) this.findViewById(R.id.rc);
        this.fuel_type = (Spinner) this.findViewById(R.id.fueltype);
        this.vehicle_number = (EditText) this.findViewById(R.id.vehicle_number);
        this.makelinear=(LinearLayout)this.findViewById(R.id.makelinear);
        this.makelinear_Other=(LinearLayout)this.findViewById(R.id.makelinear_Other);
        this.model_other_linear=(LinearLayout)this.findViewById(R.id.model_other_linear);
        this.model_linear=(LinearLayout)this.findViewById(R.id.model_linear);
        rclinear=(LinearLayout)this.findViewById(R.id.rclinear);
        this.make = (Spinner) this.findViewById(R.id.make);
        this.model = (Spinner) this.findViewById(R.id.model);
        this.color = (EditText) this.findViewById(R.id.color);
        capacity = (EditText) this.findViewById(R.id.capacity);
        makeEntry= (EditText) this.findViewById(R.id.makeEntry);
        modelEntry= (EditText) this.findViewById(R.id.modelEntry);
        this.submit = (Button) this.findViewById(R.id.submit);
        valid_upto.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        insurance_due_date.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        rc.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        vehicle_number.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(15)});
        color.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        capacity.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        makeEntry.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        modelEntry.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        submit.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        this.arrayListFuelType =new ArrayList<>();
        this.arrayListFuelTypeid =new ArrayList<>();
        this.submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVehicleActivity.this.Validate();
            }
        });
        this.getfueltype();
        makename=new ArrayList<>();
        makeid=new ArrayList<>();
        this.valid_upto.setOnClickListener(this);
        this.insurance_due_date.setOnClickListener(this);
        model_other_linear.setVisibility(View.GONE);
        model.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if(position==modelname.size()-1){
                        model_other_linear.setVisibility(View.VISIBLE);
                    }else {
                        model_other_linear.setVisibility(View.GONE);
                        Call_Capacity_api(modelname.get(position));


                    }
                }catch (Exception e){

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.fuel_type.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddVehicleActivity.this.sp_fuel_type = AddVehicleActivity.this.arrayListFuelTypeid.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        makelinear.setOnClickListener(this);
        rclinear.setOnClickListener(this);
        makeListApi();
        make.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0&&position<makeid.size()-1){
                    PrefsManager manager=new PrefsManager(AddVehicleActivity.this);
                    callModelApi(manager.getKey("key"),makeid.get(position));
                    makelinear_Other.setVisibility(View.GONE);
                    model_other_linear.setVisibility(View.GONE);
                    model_linear.setVisibility(View.VISIBLE);
                }else if(position==makeid.size()-1){
                    makelinear_Other.setVisibility(View.VISIBLE);
                    model_other_linear.setVisibility(View.VISIBLE);
                    model_linear.setVisibility(View.GONE);
                    capacity.setEnabled(true);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void Call_Capacity_api(String s) {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.capacity(manager.getKey("key"),s);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")) {
                    ArrayList<Capacity>arrayList=loginResponse.getFirsresponse().getCapacity();

                    if(arrayList!=null){
                        capacity.setText(arrayList.get(0).getCapacity());
                    }

                }else {
                    Toast.makeText(AddVehicleActivity.this.getApplicationContext(),"Please Login Again",Toast.LENGTH_LONG).show();
                    new PrefsManager(AddVehicleActivity.this).clearAllData();
                    AddVehicleActivity.this.startActivity(new Intent(AddVehicleActivity.this.getApplicationContext(),LoginActivity.class));
                    AddVehicleActivity.this.finish();
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);
            }
        });


    }

    private void callModelApi(String key, String id) {
        modelname=new ArrayList<>();
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.model(key,id);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")) {
                    modelname.add("Select Model");
                    ArrayList<MakeModel>makeModels=loginResponse.getFirsresponse().getModel();
                    if(makeModels!=null){
                        for (int i=0;i<makeModels.size();i++){
                            modelname.add(makeModels.get(i).getName());
                        }
                        modelname.add("Other");
                        ArrayAdapter adapter=new ArrayAdapter(AddVehicleActivity.this, layout.simple_spinner_dropdown_item,modelname);
                        model.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(AddVehicleActivity.this.getApplicationContext(),"Please Login Again",Toast.LENGTH_LONG).show();
                    new PrefsManager(AddVehicleActivity.this).clearAllData();
                    AddVehicleActivity.this.startActivity(new Intent(AddVehicleActivity.this.getApplicationContext(),LoginActivity.class));
                    AddVehicleActivity.this.finish();
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }
        });
    }
    private boolean Validate() {
        this.VehicleNumber = this.vehicle_number.getText().toString().trim();
        this.Make = this.make.getSelectedItem().toString().trim();
        String Make1 = this.make.getSelectedItem().toString().trim();
        this.Model = this.model.getSelectedItem().toString().trim();
        this.InsuranceDueDate = this.insurance_due_date.getText().toString().trim();
        this.ValidUpto = this.valid_upto.getText().toString().trim();
        this.Color = this.color.getText().toString().trim();
        this.FuelType1 = this.fuel_type.getSelectedItem().toString().trim();
        this.getrc = this.rc.getText().toString().trim();
        this.getcapacity = this.capacity.getText().toString().trim();

        String pattern="^[A-Z]{2} [0-9]{2} [A-Z]{2} [0-9]{4}$";
        if(!this.VehicleNumber.matches(pattern)){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Enter Valid Vehicle Number..!!!",R.drawable.dont_sign);
            return false;
            }
        if(Make.equals("Other")){
            Make=makeEntry.getText().toString();
            if(Make.isEmpty()){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Enter  Make..!!!",R.drawable.dont_sign);
                return false;
            }

            }else {
                if(this.Make.isEmpty()||this.Make.equals("Select Make")){
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Select Make..!!!",R.drawable.dont_sign);
                    return false;
                }
            }
            if(Make1.equals("Other")){
            Model=modelEntry.getText().toString();
            if(Model.isEmpty()){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Enter Model Type..!!!",R.drawable.dont_sign);
                return  false;
            }
           }else {
            if(this.Model.isEmpty()||this.Model.equals("Select Model")){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Select  Model..!!!",R.drawable.dont_sign);
                return false;
            }
        }

        if(this.InsuranceDueDate.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Select Insurance Due Date..!!!",R.drawable.dont_sign);
            return false;
        }
        if(this.ValidUpto.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Select Date ..!!!",R.drawable.dont_sign);
            return false;
        }
        if(this.Color.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Enter Color..!!!",R.drawable.dont_sign);
            return false;
       }
       if(this.getcapacity.isEmpty()){
           CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Enter Capacity..!!!",R.drawable.dont_sign);
           return false;
        }
        if(this.getrc.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Select RC Validity Date..!!!",R.drawable.dont_sign);
            return false;
        }
         if(Make.equals("Other")){
             Make=makeEntry.getText().toString().trim();
             if(Make.isEmpty()){
                 CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Enter Make Type..!!!",R.drawable.dont_sign);
                 return false;
             }
         }
        if(Model.equals("Other")){
            Model=modelEntry.getText().toString().trim();
            if(Model.isEmpty()){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Please Enter Model Type..!!!",R.drawable.dont_sign);
                return false;
            }
        }
        this.CallAddVehivleApi(new PrefsManager(this).getKey("key"), this.VehicleNumber, this.sp_fuel_type, this.InsuranceDueDate, this.Color, this.ValidUpto, this.Model
                               , this.Make,getrc,getcapacity);
        return true;
    }
    @Override
    public void onClick(View view) {
        if(view== this.valid_upto){
            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(this, new OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                    month1=month1+1;
                    AddVehicleActivity.this.valid_upto.setText(""+year1+"/"+month1+"/"+day1);
                }
            },year,month,day);
            dialog.show();
        }
        if(view== this.insurance_due_date){
            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(this, new OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                    month1=month1+1;
                    AddVehicleActivity.this.insurance_due_date.setText(""+year1+"/"+month1+"/"+day1);
                }
            },year,month,day);
            dialog.show();
        } if(view== this.rclinear){
            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(this, new OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                    month1=month1+1;
                    AddVehicleActivity.this.rc.setText(""+year1+"/"+month1+"/"+day1);
                }
            },year,month,day);
            dialog.show();
        }

        if(view==makelinear){
            PrefsManager manager=new PrefsManager(this);
            final ProgressDialog pb = new ProgressDialog(this);
            pb.setMessage("Please wait.....");
            pb.setCancelable(false);
            pb.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<LoginResponse> call=apiService.make(manager.getKey("key"));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse=response.body();
                    String status=loginResponse.getStatus();
                    if(status.equals("success")) {
                        makename.add("Select Make");
                        makeid.add("0");
                        ArrayList<MakeModel>makeModels=loginResponse.getFirsresponse().getMake();
                        pb.dismiss();
                        if(makeModels!=null){
                            for (int i=0;i<makeModels.size();i++){
                                if(makeModels.get(i).getName()!=null&&makeModels.get(i).getId()!=null){
                                    makename.add(makeModels.get(i).getName());
                                    makeid.add(makeModels.get(i).getId());
                                }}
                            makename.add("Other");
                            makeid.add("00");
                            ArrayAdapter adapter=new ArrayAdapter(AddVehicleActivity.this, layout.simple_spinner_dropdown_item,makename);
                            make.setAdapter(adapter);
                        }else{
                            CustomDialog.customDialogwithsingleButton(AddVehicleActivity.this,"Make Type Not Found..!!!",R.drawable.dont_sign);
                        }
                    }else {
                        Toast.makeText(AddVehicleActivity.this.getApplicationContext(),"Please Login Again",Toast.LENGTH_LONG).show();
                        new PrefsManager(AddVehicleActivity.this).clearAllData();
                        AddVehicleActivity.this.startActivity(new Intent(AddVehicleActivity.this.getApplicationContext(),LoginActivity.class));
                        AddVehicleActivity.this.finish();
                    }

                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    pb.dismiss();
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);
                }
            });
        }
    }
    private void makeListApi() {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.make(manager.getKey("key"));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")) {
                    makename.add("Select Make");
                    makeid.add("0");
                    ArrayList<MakeModel>makeModels=loginResponse.getFirsresponse().getMake();
                    if(makeModels!=null){
                        for (int i=0;i<makeModels.size();i++){
                            if(makeModels.get(i).getName()!=null&&makeModels.get(i).getId()!=null){
                                makename.add(makeModels.get(i).getName());
                                makeid.add(makeModels.get(i).getId());
                            }}
                        makename.add("Other");
                        makeid.add("00");
                        ArrayAdapter adapter=new ArrayAdapter(AddVehicleActivity.this, layout.simple_spinner_dropdown_item,makename);
                        make.setAdapter(adapter);
                    }else {
                        CustomDialog.customDialogwithsingleButton(AddVehicleActivity.this,"Make Type Not Found..!!!",R.drawable.dont_sign);
                    }

                }else {
                    Toast.makeText(AddVehicleActivity.this.getApplicationContext(),"Please Login Again",Toast.LENGTH_LONG).show();
                    new PrefsManager(AddVehicleActivity.this).clearAllData();
                    AddVehicleActivity.this.startActivity(new Intent(AddVehicleActivity.this.getApplicationContext(),LoginActivity.class));
                    AddVehicleActivity.this.finish();
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);
            }
        });}
    private void CallAddVehivleApi(String key,String Registration_Number,String Fule_Type,String Insurance_Due_Date,String Van_Color,String Puc_Date,
                                                   String Model,String Make,String rc,String capacity) {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.AddVehicle(key,Registration_Number,Fule_Type,Insurance_Due_Date,Puc_Date,Van_Color,Model,Make,rc,capacity,manager.getCustomer_Code());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                String msg=loginResponse.getFirsresponse().getMsg();
                pb.dismiss();
                if(status.equals("success")) {
                    success_dialog("Vehicle Added Successfully");
                }
                else  if(msg.contains("Driver Already")){
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Driver Already Exist..!!!",R.drawable.dont_sign);
                }
                else {
                    Toast.makeText(AddVehicleActivity.this.getApplicationContext(),"Please Login Again",Toast.LENGTH_LONG).show();
                    new PrefsManager(AddVehicleActivity.this).clearAllData();
                    AddVehicleActivity.this.startActivity(new Intent(AddVehicleActivity.this.getApplicationContext(),LoginActivity.class));
                    AddVehicleActivity.this.finish();
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddVehicleActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);
            }
        });}
    private void getfueltype() {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.getfueltype(manager.getKey("key"));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")) {
                    List<FuelType> arrayList = loginResponse.getFirsresponse().getFuelTypes();
                    if(arrayList!=null) {
                        for (int i = 0; i < arrayList.size(); i++) {
                            AddVehicleActivity.this.arrayListFuelType.add(arrayList.get(i).getType());
                            AddVehicleActivity.this.arrayListFuelTypeid.add(arrayList.get(i).getItem_code());
                        }
                        ArrayAdapter<String> fuelTypeAdapter = new ArrayAdapter<String>(AddVehicleActivity.this, layout.simple_spinner_dropdown_item, AddVehicleActivity.this.arrayListFuelType);
                        AddVehicleActivity.this.fuel_type.setAdapter(fuelTypeAdapter);
                    }else {
                        CustomDialog.customDialogwithsingleButton(AddVehicleActivity.this,"Fuel Type Not Found..!!!",R.drawable.dont_sign);
                    }

                }}
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(AddVehicleActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);
            }
        });}


    public void success_dialog(String status){
        final Dialog ab=new Dialog(AddVehicleActivity.this);
        ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view= LayoutInflater.from(AddVehicleActivity.this).inflate(R.layout.popdialogwithsinglebutton,null,false);
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
                AddVehicleActivity.this.startActivity(new Intent(AddVehicleActivity.this,VehicleListActivity.class));
                AddVehicleActivity.this.finish();
            }
        });
        ab.show();
    }
}
