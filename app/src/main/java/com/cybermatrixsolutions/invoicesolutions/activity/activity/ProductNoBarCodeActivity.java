package com.cybermatrixsolutions.invoicesolutions.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.adapter.ProductNobarCodeAdapter;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.LubeList;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductNoBarCodeActivity extends AppCompatActivity {

    List<LubeList> fuelTypeLists;
    ArrayList<String> category=new ArrayList<>();
    Spinner spinner1;
    private ProductNobarCodeAdapter adapter;
    private RecyclerView recyclerView;
    PrefsManager prefsManager;
    private Button add,verify;
    private TextView total,price;
    LinearLayout totallayout;
    private AutoCompleteTextView mProductList;
    List<String> COUNTRIES;
    private String Driver_Mobile,OilType,petroldiesel_qty,RoCode,cust_type,Trans_By,
            Customer_Code,Vehicle_No,RequestId,Petrol_or_lube,itemcode,itemprice,cust_name,Cust_GST,Cust_mobile,SlipDetail;
    List<LubeList> models=new ArrayList<>();
    String KEY,Mobile,itemCode; private String ProductName,Price,Qty; int Inttotal,Total=0;
    double TotalAmount;
    List<String> fuel_type,Item_Code,Item_Price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_no_bar_code);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        add=(Button)findViewById(R.id.add);
        verify=(Button)findViewById(R.id.verifyOtp);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        price = (TextView) findViewById(R.id.item_price);
        total=(TextView) findViewById(R.id.total);
        totallayout=(LinearLayout)findViewById(R.id.toolbar);
        mProductList = (AutoCompleteTextView) findViewById(R.id.product);
        prefsManager=new PrefsManager(this);
        KEY=prefsManager.getKey("key");
        Mobile=getIntent().getStringExtra("mobile");
        Driver_Mobile=getIntent().getStringExtra("mobile");
        Cust_mobile=getIntent().getStringExtra("Cust_mobile");
        OilType=getIntent().getStringExtra("OilType");
        cust_name=getIntent().getStringExtra("cust_name");
        Cust_GST=getIntent().getStringExtra("Cust_GST");
        petroldiesel_qty=getIntent().getStringExtra("petroldiesel_qty");
        Customer_Code=getIntent().getStringExtra("Customer_Code");
        RoCode=getIntent().getStringExtra("Ro_code");
        TotalAmount=getIntent().getDoubleExtra("total",0.0);
        Vehicle_No=getIntent().getStringExtra("Vehicle_No");
        RequestId=getIntent().getStringExtra("RequestId");
        Petrol_or_lube=getIntent().getStringExtra("petrol_or_lube");
        itemcode=getIntent().getStringExtra("itemcode");
        itemprice=getIntent().getStringExtra("itemprice");
        Trans_By=getIntent().getStringExtra("Trans_By");
        cust_type=getIntent().getStringExtra("cust_type");
        prefsManager=new PrefsManager(this);
        KEY=prefsManager.getKey("key");
        if(KEY!=null) {
            try {
                CallPrductListApi(KEY);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        double  n =new Date().getDate()+new Date().getTime();
        SlipDetail="GRD"+String.valueOf(n);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item_Code=new ArrayList<>();
                Item_Price=new ArrayList<>();
                double total=0;
                for (int i = 0; i < models.size(); i++) {
                    Item_Code.add(models.get(i).getId());
                    Item_Price.add(models.get(i).getPrice());
                    double price=Double.parseDouble(models.get(i).getPrice());
                    total=total+price;
                }
                try {
                    String itemcode = Item_Code.toString().replace("[", "").toString().replace("]", "");
                    String itemprice = Item_Price.toString().replace("[", "").toString().replace("]", "");
                    CallTransactionApi(KEY,Customer_Code,itemcode,itemprice,Petrol_or_lube,SlipDetail,"",petroldiesel_qty,
                            RequestId,Vehicle_No,Driver_Mobile,Trans_By,cust_type,String.valueOf(total),cust_name,Cust_GST,Cust_mobile,Cust_mobile);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getText()) {
                    if (fuelTypeLists.size() > 0) {
                        double Total=0;
                        try {
                            verify.setVisibility(View.VISIBLE);
                            double total1=0;
                            for (int k=0;k<models.size();k++){
                                int i=Integer.parseInt(models.get(k).getPrice());
                                total1=total1+i;
                            }
                            total.setText(String.valueOf("Rs."+total1));
                            totallayout.setVisibility(View.VISIBLE);
                            adapter = new ProductNobarCodeAdapter(models,getApplicationContext());
                            LinearLayoutManager manager = new LinearLayoutManager(ProductNoBarCodeActivity.this);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(manager);
                            mProductList.setText("");
                            price.setText("");

                            if(fuelTypeLists!=null){
                                category.clear();
                                for(int i=0;i<fuelTypeLists.size();i++){
                                    String fuel=fuelTypeLists.get(i).getItem_Name();
                                    category.add(fuel);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ProductNoBarCodeActivity.this,
                                        R.layout.spinner_layout, category);
                                spinner1=(Spinner)findViewById(R.id.spinner1);
                                spinner1.setAdapter(dataAdapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        String Price=fuelTypeLists.get(i).getPrice();
                                        price.setText("Rs. "+Price);
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                CustomDialog.customDialogwithsingleButton(ProductNoBarCodeActivity.this,"Record Not Found...!!!",R.drawable.dont_sign);

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }


    public void CallTransactionApi(String key,String customer_code,String item_code,String item_price,String petrol_or_lube, String slip_detail,String petroldiesel_type,String petroldiesel_qty
            ,String Request_id,String Vehicle_Reg_No,String Driver_Mobile,String
                                           Trans_By,String cust_type,String total,String cust_name,String cust_gst,String cust_mob,String Trans_Mobile){

        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.transaction(key, customer_code, item_code,item_price, petrol_or_lube, slip_detail,
                petroldiesel_type,petroldiesel_qty, Request_id, Vehicle_Reg_No, Driver_Mobile, Trans_By,cust_type,total,cust_name,cust_gst,cust_mob,Trans_Mobile);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest=response.body();
                String status=customerRequest.getStatus();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")){
                    Intent intent=(new Intent(getApplicationContext(),DashboardActivity.class));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Transaction Sucessfull",Toast.LENGTH_LONG).show();
                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(ProductNoBarCodeActivity.this,"Transaction Failed...!!!",R.drawable.dont_sign);

                }
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(ProductNoBarCodeActivity.this,"Connection Failed...!!!",R.drawable.dont_sign);


            }
        });
    }


    public void CallPrductListApi(String key){
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.productmisc(key);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest=response.body();
                String status=customerRequest.getStatus();
                pb.dismiss();


                if(status.equalsIgnoreCase("success")){
                    fuelTypeLists=customerRequest.getCustomerRequestResponse().getLubeLists();
                    if(fuelTypeLists!=null){
                        for(int i=0;i<fuelTypeLists.size();i++){
                            String fuel=fuelTypeLists.get(i).getItem_Name();
                            category.add(fuel);
                        }
                        COUNTRIES=new ArrayList<>();
                        for (int i=0;i<fuelTypeLists.size();i++){
                            String ProductName=fuelTypeLists.get(i).getItem_Name();
                            COUNTRIES.add(ProductName);
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductNoBarCodeActivity.this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
                            mProductList.setAdapter(adapter);
                            mProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String Price=fuelTypeLists.get(i).getPrice();
                                    itemCode=fuelTypeLists.get(i).getItem_Code();
                                    price.setText("Rs. "+Price);
                                }
                            });
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ProductNoBarCodeActivity.this,
                                R.layout.spinner_layout, category);
                        spinner1=(Spinner)findViewById(R.id.spinner1);
                        spinner1.setAdapter(dataAdapter);
                    }else {
                        CustomDialog.customDialogwithsingleButton(ProductNoBarCodeActivity.this,"No Record Found...!!!",R.drawable.dont_sign);

                    }
                }else {
                    CustomDialog.customDialogwithsingleButton(ProductNoBarCodeActivity.this,"No Record Found...!!!",R.drawable.dont_sign);

                }

            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(ProductNoBarCodeActivity.this,"Connection Failed...!!!",R.drawable.dont_sign);

            }
        });
    }

    public boolean getText(){
        ProductName  =spinner1.getSelectedItem().toString().trim();
        Price=price.getText().toString().trim().replace("Rs. ","");
        String ItemCode=itemCode;
        if(ProductName.equalsIgnoreCase("select fuel")){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(ProductNoBarCodeActivity.this,"Fill all Details...!!!",R.drawable.dont_sign);

            return false;
        }
        if(Price.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(ProductNoBarCodeActivity.this,"Fill all Details...!!!",R.drawable.dont_sign);
            return false;
        }
        Inttotal=Integer.parseInt(Price);
        Total=Inttotal+Total;
        LubeList model=new LubeList();
        model.setItem_Name(ProductName);
        model.setItem_Code(ItemCode);
        model.setPrice(Price);
        models.add(model);
        return true;
    }

}
