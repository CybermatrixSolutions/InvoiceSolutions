package com.cybermatrixsolutions.invoicesolutions.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.adapter.QrScanAdapter;
import com.cybermatrixsolutions.invoicesolutions.model.QrScanResult;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreditCustomerActivity extends AppCompatActivity implements View.OnClickListener{

    private Button scanBtn,scan_barcode;
    private TextView credit_limit, customer_name,van_number;
    private List<QrScanResult> qrScanResultList=new ArrayList<>();
    private QrScanResult qrScanResult;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    ArrayList itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_customer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        itemlist = new ArrayList();
        scanBtn = (Button) findViewById(R.id.scan_button);
        scan_barcode = (Button) findViewById(R.id.scan_barcode);
        credit_limit = (TextView) findViewById(R.id.credit_limit);
        customer_name = (TextView) findViewById(R.id.customer_name);
        van_number = (TextView) findViewById(R.id.van_number);
        linearLayout=(LinearLayout)findViewById(R.id.main);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        scanBtn.setOnClickListener(this);
        scan_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),BarcodeScanActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan QRcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();

//        Use this for more customization
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//        integrator.setPrompt("Scan a barcode");
//        integrator.setCameraId(0);  // Use a specific camera of the device
//        integrator.setBeepEnabled(false);
//        integrator.setBarcodeImageEnabled(true);
//        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                 String Content=result.getContents();
                String Formate=result.getFormatName();
              /*  tvScanContent.setText(Content);
                tvScanFormat.setText(Formate);*/
                try {
                    scanBtn.setVisibility(View.GONE);
                    scan_barcode.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    JSONObject jsonObj = new JSONObject(Content);
                    String customer_id=jsonObj.getString("id");
                    String Customer_name=jsonObj.getString("name");
                    String Credit_limit=jsonObj.getString("credit limit");
                    String Van_name=jsonObj.getString("van_name");
                    customer_name.setText(Customer_name);
                    credit_limit.setText(Credit_limit);
                    van_number.setText(Van_name);
                    JSONArray jsonArray=jsonObj.getJSONArray("requirement");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String item=jsonObject.getString("item");
                        itemlist.add(item);
                        String qty=jsonObject.getString("qty");
                        qrScanResult=new QrScanResult();
                        qrScanResult.setContent(item);
                        qrScanResult.setFormate(qty);
                        qrScanResultList.add(qrScanResult);
                    }
                    if(qrScanResultList.size()>0){
                        QrScanAdapter adapter=new QrScanAdapter(getApplicationContext(),qrScanResultList);
                        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
