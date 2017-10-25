package com.cybermatrixsolutions.invoicesolutions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cybermatrixsolutions.invoicesolutions.activity.DashboardActivity;
import com.cybermatrixsolutions.invoicesolutions.activity.ForgotPasswordActivity;
import com.cybermatrixsolutions.invoicesolutions.activity.MachineNumberActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            }
        });

        findViewById(R.id.forgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });
    }
}
