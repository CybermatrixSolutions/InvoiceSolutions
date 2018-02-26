package com.cybermatrixsolutions.invoicesolutions.activity.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.FirstTimeLoginResponse;
import com.cybermatrixsolutions.invoicesolutions.model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceIdMobileNumberActivity extends AppCompatActivity {

    private EditText et_mobile, et_otp, et_pin, et_cnfmpin;
    private Button sendOtp, verifyOtp, setPin;
    private String IMEI;
    private String Mobile, ID;
    private LinearLayout sendOtpLayout, verifyOtpLayout, setPinLayout;
    String TAG = DeviceIdMobileNumberActivity.class.getName();
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private TextView welcome;
    private TelephonyManager mTelephonyManager;
    String user_type;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_id_mobile_number);
        getSupportActionBar().hide();
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_otp = (EditText) findViewById(R.id.et_otp);
        sendOtp = (Button) findViewById(R.id.sendotp);
        verifyOtp = (Button) findViewById(R.id.verifyotp);
        setPin = (Button) findViewById(R.id.setpin);
        welcome = (TextView) findViewById(R.id.welcome);
        et_pin = (EditText) findViewById(R.id.et_pin);
        et_cnfmpin = (EditText) findViewById(R.id.et_cnfmpin);
        sendOtpLayout = (LinearLayout) findViewById(R.id.senOtpLayout);
        verifyOtpLayout = (LinearLayout) findViewById(R.id.verifyOtpLayout);
        setPinLayout = (LinearLayout) findViewById(R.id.setPinLayout);
        if(Build.VERSION.SDK_INT>22) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }else {
                getDeviceImei();
            }

        }  else {
            getDeviceImei();
        }
        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Valiadation();

            }
        });
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOtpLayout.setVisibility(View.GONE);
                setPinLayout.setVisibility(View.VISIBLE);
            }
        });
        setPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValiadePin();
            }
        });
        et_mobile.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        et_otp.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        sendOtp.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        verifyOtp.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        et_pin.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        et_cnfmpin.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        setPin.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});



    }


    private void getDeviceImei() {

        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
             IMEI = mTelephonyManager.getDeviceId();
            Log.d("msg", "DeviceImei " + IMEI);
          //  Toast.makeText(getApplicationContext(),IMEI.toString(),Toast.LENGTH_LONG).show();
        }catch (SecurityException e){
            e.printStackTrace();
        }

    }

    public boolean Valiadation() {
        Mobile = et_mobile.getText().toString().trim();
        if (Mobile.isEmpty()) {
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(DeviceIdMobileNumberActivity.this,"Please Enter Mobile Number..!!",R.drawable.dont_sign);

            return false;
        }
        CallImeiCheckApi(Mobile, IMEI);
        return true;
    }

    public boolean ValiadePin() {
        String Pin = et_pin.getText().toString().trim();
        String ConfirmPin = et_cnfmpin.getText().toString().trim();
        if (Pin.isEmpty()) {
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(DeviceIdMobileNumberActivity.this,"Please Enter 4 digit pin..!!",R.drawable.dont_sign);
            return false;
        }
        if (ConfirmPin.isEmpty()) {
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(DeviceIdMobileNumberActivity.this,"Please Confirm your pin..!!",R.drawable.dont_sign);

            return false;
        }
        if (!ConfirmPin.equals(Pin)) {
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(DeviceIdMobileNumberActivity.this,"Please Confirm your pin..!!",R.drawable.dont_sign);
            return false;
        }

        try {
            CallSetPinApi(Mobile, ID, Pin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void CallImeiCheckApi(String mobile, String imei) {
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.firstTimelogin(mobile, imei);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();//mobile no not exits
                String status = loginResponse.getStatus();
                pb.dismiss();
                if (!status.equalsIgnoreCase("failed")) {
                    welcome.setText("Set Your Pin");
                    FirstTimeLoginResponse firstTimeLoginResponse = loginResponse.getFirsresponse();
                    String key = firstTimeLoginResponse.getKey();
                    String msg = firstTimeLoginResponse.getMsg();
                     user_type=firstTimeLoginResponse.getUsertype();
                     ID = firstTimeLoginResponse.getID();
                    verifyOtpLayout.setVisibility(View.GONE);
                    setPinLayout.setVisibility(View.VISIBLE);
                    sendOtpLayout.setVisibility(View.GONE);
                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(DeviceIdMobileNumberActivity.this,"Invalid User..!!",R.drawable.dont_sign);
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(DeviceIdMobileNumberActivity.this,"Network Connection Error..!!",R.drawable.dont_sign);

            }
        });

    }

    public void CallSetPinApi(final String Mobile, final String id, String pin) {
       final PrefsManager manager=new PrefsManager(this);

        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.generatepin(Mobile, id, pin,user_type);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                String status = loginResponse.getStatus();
                pb.dismiss();
                if (status.equalsIgnoreCase("success")) {
                    String mobile = loginResponse.getFirsresponse().getMobile();
                    String imei = loginResponse.getFirsresponse().getKey();
                    if (imei != null)
                        Toast.makeText(getApplicationContext(), "Pin Set Sucessfull", Toast.LENGTH_LONG).show();
                    Intent intent = (new Intent(getApplicationContext(), LoginActivity.class));
                    PrefsManager pref=new PrefsManager(getApplicationContext());
                    pref.setIMEI(IMEI);
                    manager.setMobile(Mobile);
                    pref.setUser_type(user_type);
                    pref.setid(id);
                    intent.putExtra("imei", IMEI);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(DeviceIdMobileNumberActivity.this,"Network Connection Error..!!",R.drawable.dont_sign);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceImei();
        }

    }
}
