package com.cybermatrixsolutions.invoicesolutions.activity.walkin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

public class BarCodeOptionActivity extends AppCompatActivity {

    private Button noBarcode,withBarcode;
    PrefsManager prefsManager;
    String Mobile,product,cust_name,Cust_GST,cust_type,Customer_Code,Vehicle_No,Cust_mobile,Customeraddress,Customerstate;
    String states_code,customer_city,pin_no,pan_no;
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
        Mobile=getIntent().getStringExtra("mobile");
        Cust_mobile=getIntent().getStringExtra("customer_number");
        product=getIntent().getStringExtra("product");
        cust_name=getIntent().getStringExtra("customer_name");
        Cust_GST=getIntent().getStringExtra("gstNumber");
        cust_type=getIntent().getStringExtra("cust_type");
        Customer_Code=getIntent().getStringExtra("Customer_Code");
        Vehicle_No=getIntent().getStringExtra("Vehicle_No");
        states_code=getIntent().getStringExtra("states_code");
        customer_city=getIntent().getStringExtra("customer_city");
                pin_no=getIntent().getStringExtra("pin_no");
        pan_no=getIntent().getStringExtra("pan_no");
        noBarcode=(Button)findViewById(R.id.withoutBarcode);
        withBarcode=(Button)findViewById(R.id.withBarcode);
        prefsManager=new PrefsManager(this);
        noBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=null;
                    Customeraddress=getIntent().getStringExtra("Customeraddress");
                    Customerstate=getIntent().getStringExtra("Customerstate");
                    intent = (new Intent(getApplicationContext(), NoBarCodeActivity.class));
                    intent.putExtra("mobile",Mobile);
                    intent.putExtra("customer_number",Cust_mobile);
                    intent.putExtra("product",product);
                    intent.putExtra("customer_name",cust_name);
                    intent.putExtra("gstNumber",Cust_GST);
                    intent.putExtra("OilType", "Mobil");
                    intent.putExtra("Customer_Code", "Walkin");
                    intent.putExtra("Ro_code","Walkin");
                    intent.putExtra("petrol_or_lube", "2");
                    intent.putExtra("RequestId", "Walkin");
                    intent.putExtra("Vehicle_No", "Walkin");
                    intent.putExtra("cust_type",cust_type);
                    intent.putExtra("Trans_By",prefsManager.getUserDetails().get("user_id"));
                    intent.putExtra("Customeraddress",Customeraddress);
                    intent.putExtra("Customerstate",Customerstate);
                    intent.putExtra("states_code",states_code);
                    intent.putExtra("customer_city", customer_city);
                    intent.putExtra("pin_no", pin_no);
                    intent.putExtra("pan_no", pan_no);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
//                }
            }
        });
        withBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=(new Intent(getApplicationContext(), BarcodeScanActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("mobile",Mobile);
                intent.putExtra("Vehicle_No", Vehicle_No);
                    intent.putExtra("product",product);
                    intent.putExtra("cust_type",cust_type);
                    intent.putExtra("cust_name",cust_name);
                   intent.putExtra("RequestId", "Walkin");
                    intent.putExtra("Cust_GST",Cust_GST);
                    intent.putExtra("Cust_mobile",Cust_mobile);
                    intent.putExtra("Customer_Code", Customer_Code);
                startActivity(intent);
            }
        });

    }
}
