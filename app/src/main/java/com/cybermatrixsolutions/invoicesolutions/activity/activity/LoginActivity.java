package com.cybermatrixsolutions.invoicesolutions.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.With_QR.Driver_DashBoard;
import com.cybermatrixsolutions.invoicesolutions.customer_module.activity.CustomerDriver;
import com.cybermatrixsolutions.invoicesolutions.customer_module.activity.CustomerRO;
import com.cybermatrixsolutions.invoicesolutions.model.Driver_Responce;
import com.cybermatrixsolutions.invoicesolutions.model.EMPDETAIL;
import com.cybermatrixsolutions.invoicesolutions.model.EmployeeDetail;
import com.cybermatrixsolutions.invoicesolutions.model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText empId,password;
    PrefsManager prefsManager;
    private String imei;

    Button change_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = (Toolbar) findViewById( R.id.toolbar);
        setSupportActionBar(mToolbar);
        empId=(EditText)findViewById(R.id.empId);
        password=(EditText)findViewById(R.id.et_pin);
        change_login=(Button)findViewById(R.id.change_login);
        change_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=(new Intent(getApplicationContext(), DeviceIdMobileNumberActivity.class));
                startActivity(intent);
            }
        });
        password.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        empId.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});

      //  imei=getIntent().getStringExtra("imei");
        prefsManager=new PrefsManager(this);
        imei=prefsManager.getIMEI("imei");
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValiadePin();
            }
        });

    }
    public boolean ValiadePin(){
        String EmpId="GARP002";empId.getText().toString().trim();
        String Pin=password.getText().toString().trim();
        if(EmpId.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(LoginActivity.this,"Please Enter your Employee Id..!!",R.drawable.dont_sign);

            return false;
        }
        if(Pin.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(LoginActivity.this,"Please Enter your Pin..!!",R.drawable.dont_sign);
            return false;
        }
        try {
        }catch (Exception e){
        }
        PrefsManager pref=new PrefsManager(this);
        if(pref.getUsertype().equals("Sales")) {
            CallLoginApi(imei,Pin);
        }else if(pref.getUsertype().equals("Customer")){

            CallLoginApi1(imei,Pin);
        }else {
            CallLoginApi2( imei, Pin);
        }
        return true;
    }
    private void CallLoginApi2(String imei, String pin) {
        PrefsManager pref=new PrefsManager(getApplicationContext());
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Driver_Responce> call=apiService.employeeLogindriver(imei, pin,pref.getUsertype());
        call.enqueue(new Callback<Driver_Responce>() {
            @Override
            public void onResponse(Call<Driver_Responce> call, Response<Driver_Responce> response) {
                Driver_Responce loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")){
                    String key=loginResponse.getFirsresponse().getKey();
                    PrefsManager pref=new PrefsManager(getApplicationContext());
                    pref.setKey(key);
                    List<EMPDETAIL> employeeDetail=loginResponse.getFirsresponse().getList();
                    if(employeeDetail!=null){
                        pref.setLogin(true);
                        pref.createLogin_driver(employeeDetail.get(0).getRegistration_Number(),employeeDetail.get(0).getCustomer_Name(),employeeDetail.get(0).getPump_legal_name());

                    }
                    Intent intent=(new Intent(getApplicationContext(), CustomerDriver.class));
                    intent.putExtra("key",key);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(LoginActivity.this,"Login Failed..!!",R.drawable.dont_sign);
                }

            }
            @Override
            public void onFailure(Call<Driver_Responce> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(LoginActivity.this,"Connection Failed..!!",R.drawable.dont_sign);

            }
        });

    }
    public void CallLoginApi( final String imei, String pin){
        PrefsManager pref=new PrefsManager(getApplicationContext());
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.employeeLogin(imei, pin,pref.getUsertype(),pref.getMobile());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")){
                   String key=loginResponse.getFirsresponse().getKey();
                    PrefsManager pref=new PrefsManager(getApplicationContext());
                    pref.setKey(key);
                    List<EmployeeDetail> employeeDetail=loginResponse.getFirsresponse().getEmployeeDetail();
                    if(employeeDetail!=null){
                        String id=employeeDetail.get(0).getId();
                        String Personnel_Name=employeeDetail.get(0).getPersonnel_Name();
                        String Designation_Name=employeeDetail.get(0).getDesignation_Name();
                        String Date_of_Birth=employeeDetail.get(0).getDate_of_Birth();
                        String Date_of_Appointment=employeeDetail.get(0).getDate_of_Appointment();
                        String Mobile=employeeDetail.get(0).getMobile();
                        String Email=employeeDetail.get(0).getEmail();
                        String Employeecode=employeeDetail.get(0).getEmployeecode();
                        pref.setLogin(true);
                        pref.createLogin(id,
                                Personnel_Name,
                                Designation_Name,
                                Email,Mobile,
                                Date_of_Appointment,
                                Employeecode,
                                Date_of_Birth,
                                employeeDetail.get(0).getState()
                                ,employeeDetail.get(0).getPin_code()
                                ,employeeDetail.get(0).getPump_legal_name(),
                                employeeDetail.get(0).getPump_address(),
                                employeeDetail.get(0).getAddress_2(),
                                employeeDetail.get(0).getAddress_3(),
                                employeeDetail.get(0).getCity()
                                ,employeeDetail.get(0).getState(),
                                employeeDetail.get(0).getGstcode(),
                                employeeDetail.get(0).getPin_code(),
                                employeeDetail.get(0).getCustomer_care()
                                ,employeeDetail.get(0).getPhone_no()
                                ,employeeDetail.get(0).getMobile(),
                                employeeDetail.get(0).getVAT_TIN(),
                                employeeDetail.get(0).getGST_TIN(),
                                employeeDetail.get(0).getInvoice_prefix(),
                                employeeDetail.get(0).getPAN_No(),
                                employeeDetail.get(0).getGST_prefix(),
                                employeeDetail.get(0).getTC_Delivery_Slip(),
                                employeeDetail.get(0).getTC_Delivery_Slip2(),
                                employeeDetail.get(0).getTC_for_GST_Invoice_Slip(),
                                employeeDetail.get(0).getTC_for_GST_Invoice_Slip2());


                    }
                        Intent intent=(new Intent(getApplicationContext(), DashboardActivity.class));
                        intent.putExtra("key",key);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);




                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(LoginActivity.this,"Login Failed..!!",R.drawable.dont_sign);

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(LoginActivity.this,"Connection  Failed..!!",R.drawable.dont_sign);
            }
        });


    } private void CallLoginApi1(final String imei, String pin) {
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface apiService = com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiClient.getClient().create(com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface.class);
        Call<com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse> call=apiService.firstTimelogin(imei,pin,prefsManager.getUsertype(),prefsManager.getid());
        call.enqueue(new Callback<com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse>() {
            @Override
            public void onResponse(Call<com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse> call, retrofit2.Response<com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse> response) {
                com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                String msg=loginResponse.getStatus();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")){
                    String key=loginResponse.getFirsresponse().getKey();
                    PrefsManager pref=new PrefsManager(LoginActivity.this);
                    pref.setKey(key);
                    Intent intent=(new Intent(LoginActivity.this, CustomerRO.class));
                    intent.putExtra("key",key);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    LoginActivity.this.startActivity(intent);
                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(LoginActivity.this,"Login  Failed..!!",R.drawable.dont_sign);
                }

            }

            @Override
            public void onFailure(Call<com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(LoginActivity.this,"Connection  Failed..!!",R.drawable.dont_sign);

            }
        });
    }

}
