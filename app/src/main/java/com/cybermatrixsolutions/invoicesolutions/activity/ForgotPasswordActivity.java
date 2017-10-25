package com.cybermatrixsolutions.invoicesolutions.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button submit,resetPassword;
    private LinearLayout mainLayout,submitLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        submit =(Button)findViewById(R.id.submit);
        resetPassword =(Button)findViewById(R.id.resetPassword);
        mainLayout=(LinearLayout)findViewById(R.id.mainlayout);
        submitLayout=(LinearLayout)findViewById(R.id.resetLayout);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.setVisibility(View.GONE);
                submitLayout.setVisibility(View.VISIBLE);
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Password has been Changed",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
