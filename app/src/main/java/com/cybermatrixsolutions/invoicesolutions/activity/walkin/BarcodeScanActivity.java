package com.cybermatrixsolutions.invoicesolutions.activity.walkin;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.adapter.BarCodeScanAdapter;
import com.cybermatrixsolutions.invoicesolutions.model.QrScanResult;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BarcodeScanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button scanBtn,next;
    private List<QrScanResult> qrScanResultList=new ArrayList<>();
    private QrScanResult qrScanResult;
    private RecyclerView recyclerView;
    private TextView total;
    LinearLayout totalLayout,creditCustomerLayout;
    RelativeLayout relativeLayout;
    String Mobile,cust_name,Cust_GST,cust_type,Trans_By,Vehicle_No,Customer_Code,Cust_mobile,RequestId;
    List<String> fuel_type,Item_Code,Item_Price;
    private PrefsManager prefsManager;

    ArrayList<String>arrayList_Product_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);
        scanBtn = (Button) findViewById(R.id.scan_button);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next=(Button)findViewById(R.id.addLube);
        Mobile=getIntent().getStringExtra("mobile");
        Cust_mobile=getIntent().getStringExtra("Cust_mobile");
        RequestId=getIntent().getStringExtra("RequestId");
        Trans_By=getIntent().getStringExtra("Trans_By");
        cust_type=getIntent().getStringExtra("cust_type");
        cust_name=getIntent().getStringExtra("cust_name");
        Cust_GST=getIntent().getStringExtra("Cust_GST");
        Vehicle_No=getIntent().getStringExtra("Vehicle_No");
        Customer_Code=getIntent().getStringExtra("Customer_Code");
        if(Mobile!=null)
        relativeLayout=(RelativeLayout)findViewById(R.id.main);
        prefsManager=new PrefsManager(this);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        totalLayout=(LinearLayout) findViewById(R.id.Total);
        creditCustomerLayout=(LinearLayout) findViewById(R.id.creditCustomerLayout);
        arrayList_Product_id=new ArrayList<>();
        total=(TextView)findViewById(R.id.total);
        scanBtn.setOnClickListener(this);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item_Code=new ArrayList<>();
                Item_Price=new ArrayList<>();
                double total=0;
                for (int i = 0; i < qrScanResultList.size(); i++) {
                    Item_Code.add(qrScanResultList.get(i).getContent());
                    Item_Price.add(qrScanResultList.get(i).getFormate());
                    double price=Double.parseDouble(qrScanResultList.get(i).getFormate());
                    total=total+price;
                }


                  Toast.makeText(BarcodeScanActivity.this, arrayList_Product_id.toString(), Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(getApplicationContext(),VerifyMobileNumberLubeActivity.class);
//                String itemcode = Item_Code.toString().replace("[", "").toString().replace("]", "");
//                String itemprice = Item_Price.toString().replace("[", "").toString().replace("]", "");
//                intent.putExtra("OilType", "Mobil");
//                intent.putExtra("Customer_Code", Customer_Code);
//                intent.putExtra("Ro_code","GARD1");
//                intent.putExtra("total",total);
//                intent.putExtra("itemcode", itemcode);
//                intent.putExtra("petroldiesel_type","");
//                intent.putExtra("petroldiesel_qty", "");
//                intent.putExtra("itemprice", itemprice);
//                intent.putExtra("petrol_or_lube", "2");
//                intent.putExtra("RequestId", RequestId);
//                intent.putExtra("Vehicle_No",Vehicle_No);
//                intent.putExtra("cust_type",cust_type);
//                intent.putExtra("cust_name",cust_name);
//                intent.putExtra("Cust_GST",Cust_GST);
//                intent.putExtra("Trans_By",prefsManager.getUserDetails().get("id"));
//                intent.putExtra("mobile",Mobile);
//                intent.putExtra("Cust_mobile",Cust_mobile);
//
//
//
//            startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan Bar-code");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
      }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                try {
                String Content=result.getContents();
                String Formate=result.getFormatName();

                    JSONObject jsonObj = new JSONObject(Content);
                    String Product_id=jsonObj.getString("product_id");
                    arrayList_Product_id.add(Product_id);


                    String Price=jsonObj.getString("price");
                    qrScanResult=new QrScanResult();
                    qrScanResult.setContent(Product_id);
                    qrScanResult.setFormate(Price);
                    qrScanResultList.add(qrScanResult);
                    if(qrScanResultList.size()>0){
                        next.setVisibility(View.VISIBLE);
                        creditCustomerLayout.setVisibility(View.VISIBLE);
                        totalLayout.setVisibility(View.VISIBLE);
                        BarCodeScanAdapter adapter=new BarCodeScanAdapter(getApplicationContext(),qrScanResultList);
                        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);
                        int sum=0;
                        for(int j=0;j<qrScanResultList.size();j++){
                            String price=qrScanResultList.get(j).getFormate();
                            int total=Integer.parseInt(price);
                            sum=sum+total;
                        }
                        total.setText("Rs."+sum);

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
