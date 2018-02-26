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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.DriversList;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.VehicleViewHolder;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface;

import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDriverActivity extends AppCompatActivity implements OnClickListener{

    TextView valid_upto;
    Spinner vehicle_reg_no;
    EditText driver_name,driver_email,driver_licence_no,driver_mobile;

    ArrayList<String> register_no_arrayList;

    Button submitbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_driver);
        this.findViewById(R.id.back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDriverActivity.this.finish();
            }
        });
        this.valid_upto = (TextView) this.findViewById(R.id.valid_upto);
        this.valid_upto.setOnClickListener(this);
        this.vehicle_reg_no = (Spinner) this.findViewById(R.id.vehicle_reg_no);
        this.driver_mobile = (EditText) this.findViewById(R.id.driver_mobile);
        this.driver_name = (EditText) this.findViewById(R.id.driver_name);
        this.driver_email = (EditText) this.findViewById(R.id.driver_email);
        this.driver_licence_no = (EditText) this.findViewById(R.id.driver_licence_no);
        this.submitbutton = (Button) this.findViewById(R.id.submitbutton);
        valid_upto.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        driver_mobile.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        driver_name.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        driver_email.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        driver_licence_no.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        submitbutton.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});


        this.submitbutton.setOnClickListener(this);
        this.register_no_arrayList =new ArrayList<>();
        this.server();
    }
    @Override
    public void onClick(View view) {

        if(view== this.valid_upto){
            this.calenderdialog();
        }if(view== this.submitbutton){
            this.checkvalidation();
        }
    }
    private void calenderdialog() {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog=new DatePickerDialog(this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                month1=month1+1;
                AddDriverActivity.this.valid_upto.setText(""+year1+"/"+month1+"/"+day1);

            }
        },year,month,day);
        dialog.show();
    }
    private void server() {
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
                if(status.equals("success")) {
                    List<DriversList> arrayList = loginResponse.getFirsresponse().getVehicleList();
                    if(arrayList!=null){
                        for(int i=0;i<arrayList.size();i++){
                        String registration_number=arrayList.get(i).getRegistration_Number();
                        AddDriverActivity.this.register_no_arrayList.add(registration_number);

                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(AddDriverActivity.this, layout.simple_spinner_dropdown_item, AddDriverActivity.this.register_no_arrayList);
                    AddDriverActivity.this.vehicle_reg_no.setAdapter(arrayAdapter);
                    }else {

                        CustomDialog.customDialogwithsingleButton(AddDriverActivity.this,"Vehicle Not Found...!!!",R.drawable.dont_sign);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(AddDriverActivity.this,"Connection Failed...!!!",R.drawable.dont_sign);

            }
        });

    }
    private boolean checkvalidation() {
        String reg_no= this.vehicle_reg_no.getSelectedItem().toString();
        String d_name= this.driver_name.getText().toString();
        String d_email= this.driver_email.getText().toString();
        String d_licence_no= this.driver_licence_no.getText().toString();
        String d_valid= this.valid_upto.getText().toString();
        String d_mobile= this.driver_mobile.getText().toString();
        String pattern="^(?<intro>[A-Z]{2})(?<numeric>\\d{2})(?<year>\\d{4})(?<tail>\\d{7})$";
        if(reg_no.length()==0){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddDriverActivity.this,"Select Registration Number..!!!",R.drawable.dont_sign);
            return false;
        }if(d_name.length()==0){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddDriverActivity.this,"Please Enter Driver Name..!!!",R.drawable.dont_sign);
            return false;
        }if(!isValidEmail(d_email)){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddDriverActivity.this,"Please Enter Valid Email..!!!",R.drawable.dont_sign);
            return false;
        }if(!VehicleViewHolder.isValidEmail(d_email)){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddDriverActivity.this,"Please Enter Valid Email..!!!",R.drawable.dont_sign);
            return false;
        }if(d_licence_no.length()==0 ||d_licence_no.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddDriverActivity.this,"Please Enter Valid Driver Licence No...!!!",R.drawable.dont_sign);
            return false;
        }if(d_valid.length()==0){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddDriverActivity.this,"Please Select Valid Date..!!!",R.drawable.dont_sign);
            return false;
        }if(d_mobile.length()<10){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddDriverActivity.this,"Please Enter Valid 10 Digit Mobile Number..!!!",R.drawable.dont_sign);
            return false;}
        this.call_Add_Driver_Api(reg_no,d_name,d_email,d_licence_no,d_valid,d_mobile);
        return  true;
    }
    private void call_Add_Driver_Api(String reg_no, String d_name, String d_email, String d_licence_no, String d_valid, String d_mobile) {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.adddriver(manager.getKey("key"),reg_no,d_name,d_email,d_licence_no,d_valid,d_mobile,manager.getCustomer_Code());
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
                else  if(msg.contains("Driver Already")){
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddDriverActivity.this,"Driver Already Exist..!!!",R.drawable.dont_sign);
                }
                else {
                        Toast.makeText(AddDriverActivity.this.getApplicationContext(),"Please Login Again",Toast.LENGTH_LONG).show();
                        new PrefsManager(AddDriverActivity.this).clearAllData();
                        AddDriverActivity.this.startActivity(new Intent(AddDriverActivity.this.getApplicationContext(),LoginActivity.class));
                        AddDriverActivity.this.finish();
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddDriverActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);
            }
        });
    }

public void success_dialog(String status){
    final Dialog ab=new Dialog(AddDriverActivity.this);
    ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    View view= LayoutInflater.from(AddDriverActivity.this).inflate(R.layout.popdialogwithsinglebutton,null,false);
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
            AddDriverActivity.this.startActivity(new Intent(AddDriverActivity.this, DriverListActivity.class));
            AddDriverActivity.this.finish();
        }
    });
    ab.show();}

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}



