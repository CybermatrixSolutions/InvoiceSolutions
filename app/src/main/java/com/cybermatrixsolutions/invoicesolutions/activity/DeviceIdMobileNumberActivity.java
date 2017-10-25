package com.cybermatrixsolutions.invoicesolutions.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;

public class DeviceIdMobileNumberActivity extends AppCompatActivity {

    private EditText et_mobile,et_otp,et_pin,et_cnfmpin;
    private Button sendOtp,verifyOtp,setPin;
    private LinearLayout sendOtpLayout,verifyOtpLayout,setPinLayout;
    String TAG=DeviceIdMobileNumberActivity.class.getName();
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_id_mobile_number);
        getSupportActionBar().hide();
        et_mobile=(EditText)findViewById(R.id.et_mobile);
        et_otp=(EditText)findViewById(R.id.et_otp);
        sendOtp=(Button)findViewById(R.id.sendotp);
        verifyOtp=(Button)findViewById(R.id.verifyotp);
        setPin=(Button)findViewById(R.id.setpin);
        et_pin=(EditText)findViewById(R.id.et_pin);
        et_cnfmpin=(EditText)findViewById(R.id.et_cnfmpin);

        sendOtpLayout=(LinearLayout)findViewById(R.id.senOtpLayout);
        verifyOtpLayout=(LinearLayout)findViewById(R.id.verifyOtpLayout);
        setPinLayout=(LinearLayout)findViewById(R.id.setPinLayout);

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {

            } else {
                requestPermission(); // Code for permission
            }
        }
        else
        {
        }
        TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String getSimSerialNumber = telemamanger.getSimSerialNumber();
        String deviceId=telemamanger.getDeviceId();
        String getSimNumber = telemamanger.getLine1Number();
        Log.e("SimSerialNumber",getSimSerialNumber);
        Log.e("deviceId",deviceId);
        Log.e("SimNumber",getSimNumber);
        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtpLayout.setVisibility(View.GONE);
                verifyOtpLayout.setVisibility(View.VISIBLE);
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
                startActivity(new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(DeviceIdMobileNumberActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(DeviceIdMobileNumberActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(DeviceIdMobileNumberActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(DeviceIdMobileNumberActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }    }

}
