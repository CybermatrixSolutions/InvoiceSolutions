package com.cybermatrixsolutions.invoicesolutions.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.R;

public class VerifyMobileNumber extends AppCompatActivity {

    private EditText et_mobile,et_otp;
    private Button sendOtp,verifyOtp;
    private LinearLayout sendOtpLayout,verifyOtpLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile_number);
        et_mobile=(EditText)findViewById(R.id.et_mobile);
        et_otp=(EditText)findViewById(R.id.et_otp);

        sendOtp=(Button)findViewById(R.id.sendotp);
        verifyOtp=(Button)findViewById(R.id.verifyOtp);

        sendOtpLayout=(LinearLayout)findViewById(R.id.sendOtp_layout);
        verifyOtpLayout=(LinearLayout)findViewById(R.id.verifyOtpLayout);

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
                Toast.makeText(getApplicationContext(),"Verified",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });
    }
}
