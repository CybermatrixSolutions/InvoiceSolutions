package com.cybermatrixsolutions.invoicesolutions.customer_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.cybermatrixsolutions.invoicesolutions.*;
import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;

public class DashboardActivity extends AppCompatActivity implements OnClickListener {


    Toolbar mToolbar;
    LinearLayout fuel_request,lube_request,manage_driver,manage_vehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_dashboard1);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
       setSupportActionBar(mToolbar);
       fuel_request = (LinearLayout) findViewById(R.id.fuel_request);
       lube_request = (LinearLayout)findViewById(R.id.lube_request);
        manage_driver = (LinearLayout)findViewById(R.id.manage_driver);
        manage_vehicle = (LinearLayout)findViewById(R.id.manage_vehicle);
        findViewById(R.id.logout).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                new com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager(DashboardActivity.this).clearAllData();
                finish();
            }
        });
        fuel_request.setOnClickListener(this);
        lube_request.setOnClickListener(this);
        manage_driver.setOnClickListener(this);
        manage_vehicle.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view== this.fuel_request)
        {

            Intent intent=new Intent(this,FuelRequestListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.startActivity(intent);


        }  if(view== this.lube_request)
        {

            Intent intent=new Intent(this,LubeRequestListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.startActivity(intent);


        }if(view== this.manage_driver)
        {

            Intent intent=new Intent(this,DriverListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.startActivity(intent);


        }if(view== this.manage_vehicle)
        {

            Intent intent=new Intent(this,VehicleListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.startActivity(intent);


        }

    }
}
