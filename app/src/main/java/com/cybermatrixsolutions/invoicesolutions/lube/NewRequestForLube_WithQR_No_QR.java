package com.cybermatrixsolutions.invoicesolutions.lube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.With_QR.Fuel_Lube_List;
import com.cybermatrixsolutions.invoicesolutions.activity.walkin.BarcodeScanActivity;

public class NewRequestForLube_WithQR_No_QR extends AppCompatActivity {
String  Driver_Mobile,Customer_Code,Registration_Number,Customer_Name,statefinal,Cust_GST,Cust_mobile,address,
        drivername,company_name,lubecurrent_driver_mobile;
  Button withoutBarcode,withbarcode;
String order_date,pin_no,pan_no,state_code,payment_mode,customer_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_option);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Driver_Mobile=getIntent().getStringExtra("Driver_Mobile");
        Customer_Code=getIntent().getStringExtra("Customer_Code");
        Registration_Number=getIntent().getStringExtra("vehicle_no");
        Customer_Name=getIntent().getStringExtra("cust_name");
        Cust_GST=getIntent().getStringExtra("Cust_GST");
        Cust_mobile=getIntent().getStringExtra("Cust_mobile");
        address=getIntent().getStringExtra("address");
        drivername=getIntent().getStringExtra("drivername");
        company_name=getIntent().getStringExtra("company_name");
        lubecurrent_driver_mobile=getIntent().getStringExtra("lubecurrent_driver_mobile");
        statefinal=getIntent().getStringExtra("statefinal");
        order_date=getIntent().getStringExtra("order_date");
        pin_no=getIntent().getStringExtra("pin_no");
        pan_no=getIntent().getStringExtra("pan_no");
        state_code=getIntent().getStringExtra("state_code");
        payment_mode=getIntent().getStringExtra("payment_mode");
        customer_city=getIntent().getStringExtra("customer_city");

        withoutBarcode=(Button)findViewById(R.id.withoutBarcode);
        withbarcode=(Button)findViewById(R.id.withBarcode);
        withoutBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NewRequestForLube_WithQR_No_QR.this,NewLubeRequest.class);
                i.putExtra("Driver_Mobile",Driver_Mobile);
                i.putExtra("Customer_Code",Customer_Code);
                i.putExtra("vehicle_no",Registration_Number);
                i.putExtra("cust_name",Customer_Name);
                i.putExtra("Cust_GST",Cust_GST);
                i.putExtra("Cust_mobile",Cust_mobile);
                i.putExtra("address",address);
                i.putExtra("drivername",drivername);
                i.putExtra("company_name",company_name);
                i.putExtra("lubecurrent_driver_mobile",lubecurrent_driver_mobile);
                i.putExtra("statefinal",statefinal);
                i.putExtra("order_date",order_date);
                i.putExtra("pin_no",pin_no);
                i.putExtra("pan_no",pan_no);
                i.putExtra("state_code",state_code);
                i.putExtra("payment_mode",payment_mode);
                i.putExtra("customer_city",customer_city);
                startActivity(i);

            }
        });
        withbarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=(new Intent(getApplicationContext(), BarcodeScanActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("mobile","");
                intent.putExtra("Vehicle_No", "");
                intent.putExtra("product","");
                intent.putExtra("cust_type","");
                intent.putExtra("cust_name","");
                intent.putExtra("RequestId", "Walkin");
                intent.putExtra("Cust_GST",Cust_GST);
                intent.putExtra("Cust_mobile",Cust_mobile);
                intent.putExtra("Customer_Code", Customer_Code);
                startActivity(intent);

            }
        });



    }
}
