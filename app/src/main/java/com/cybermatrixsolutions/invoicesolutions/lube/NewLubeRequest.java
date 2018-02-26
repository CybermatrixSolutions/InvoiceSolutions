package com.cybermatrixsolutions.invoicesolutions.lube;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.With_QR.RequestWithQrCode;
import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.activity.shiftallocation.ShiftAllocation;
import com.cybermatrixsolutions.invoicesolutions.activity.verify_mobile.VerifyMobileNumberLubeActivity;
import com.cybermatrixsolutions.invoicesolutions.activity.walkin.NoBarCodeActivity;
import com.cybermatrixsolutions.invoicesolutions.activity.walkin.WalkinVerifyMobileNumberLubeActivity;
import com.cybermatrixsolutions.invoicesolutions.adapter.NobarCodeProductAdapter;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.ItemtaxModel;
import com.cybermatrixsolutions.invoicesolutions.model.LubeList;
import com.cybermatrixsolutions.invoicesolutions.model.ProductModel;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLubeRequest extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText productName;
    private TextView total;
    LinearLayout totallayout;
    private Button add,verify;
    private String ProductName,Price,Qty,ItemCode; int Inttotal,Total=0;
    private AutoCompleteTextView mProductList;

    List<ProductModel> models1=new ArrayList<>();
    List<ProductModel> models=new ArrayList<>();
    ArrayList<String> category=new ArrayList<>();
    Spinner spinner1;
    List<LubeList> fuelTypeLists;
    PrefsManager prefsManager;
    List<String> fuel_type,Item_Code,Item_Price;
    NobarCodeProductAdapter adapter;
    String itemCode;double TotalAmount=0;
    String itemid;
    String   Customeraddress,Customerstate;
    PrefsManager manager;
    ArrayList<String>arrayListid;
    boolean checkitem=false;
    List<String> Product_name;
    List<String> Product_price;
    List<String> Product_qty;
    List<String> sumtotal;
    List<String> items_price;
    String Driver_Mobile,Customer_Code,Vehicle_No,cust_name,Cust_GST,Cust_mobile,statefinal,company_name,drivername;
    String KEY=null;
    List<String> qtysss;
    EditText qty;
    String order_date,pin_no,pan_no,state_code,payment_mode,customer_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_bar_code);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        arrayListid=new ArrayList<>();manager=new PrefsManager(this);
        recyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        add=(Button)findViewById(R.id.add);
        productName = (EditText) findViewById(R.id.medicine);
        mProductList = (AutoCompleteTextView) findViewById(R.id.product);
        total=(TextView) findViewById(R.id.total);
        totallayout=(LinearLayout)findViewById(R.id.toolbar);
        add=(Button)findViewById(R.id.add);
        prefsManager=new PrefsManager(this);
        Driver_Mobile=getIntent().getStringExtra("Driver_Mobile");
        Customer_Code=getIntent().getStringExtra("Customer_Code");
        Vehicle_No=getIntent().getStringExtra("vehicle_no");
        cust_name=getIntent().getStringExtra("cust_name");
        Cust_GST=getIntent().getStringExtra("Cust_GST");
        Cust_mobile=getIntent().getStringExtra("Cust_mobile");
        statefinal=getIntent().getStringExtra("statefinal");
        Customeraddress=getIntent().getStringExtra("address");
        drivername=getIntent().getStringExtra("drivername");
        company_name=getIntent().getStringExtra("company_name");
        order_date=getIntent().getStringExtra("order_date");
        pin_no=getIntent().getStringExtra("pin_no");
        pan_no=getIntent().getStringExtra("pan_no");
        state_code=getIntent().getStringExtra("state_code");
        payment_mode=getIntent().getStringExtra("payment_mode");
        customer_city=getIntent().getStringExtra("customer_city");
        verify=(Button)findViewById(R.id.verifyOtp);
        KEY=manager.getKey("key");
        if(KEY!=null) {
            try {
                CallLubeListApi(KEY);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        arrayListid=new ArrayList<>();
        items_price=new ArrayList<>();
        sumtotal=new ArrayList<>();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validate()){
                    String itemcode = arrayListid.toString().replace("[", "").toString().replace("]", "");
                    String itemqty = qtysss.toString().toString().replace("[", "").toString().replace("]", "");
                    CallVerifyApi(total.getText().toString(),itemcode,itemqty);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayListid.size()>0){
                    if(duplicate_entry()){
                        String getItem_Name=mProductList.getText().toString();
                        if(getItem_Name.length()>0&&checkitem==true){
                            ProductModel model=new ProductModel();
                            model.setProductName(getItem_Name);
                            double sum=0;
                            for (int i=0;i<fuelTypeLists.size();i++){
                                if(fuelTypeLists.get(i).getItem_Name().equals(getItem_Name)){
                                    model.setPrice(fuelTypeLists.get(i).getPrice());
                                    model.setQty(fuelTypeLists.get(i).getVolume_ltr());
                                    arrayListid.add(fuelTypeLists.get(i).getId());
                                    items_price.add(fuelTypeLists.get(i).getPrice());
                                }
                            }
                            for (int i=0;i<arrayListid.size();i++){
                                String id=arrayListid.get(i);
                                for (int j=0;j<fuelTypeLists.size();j++){
                                    String idd=fuelTypeLists.get(j).getId();
                                    if(id.equals(idd)){
                                        sum=sum+Double.parseDouble(fuelTypeLists.get(j).getPrice());}
                                }}
                            models.add(model);
                            checkitem=false;
                            mProductList.setText("");
                            total.setText(""+sum);
                            totallayout.setVisibility(View.VISIBLE);
                            NobarCodeProductAdapter adapter = new NobarCodeProductAdapter(models, NewLubeRequest.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(NewLubeRequest.this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(layoutManager);
                            verify.setVisibility(View.VISIBLE);
                        }else {
                            CustomDialogWithCurrentActivity.customDialogwithsingleButton(NewLubeRequest.this,"Search Items..!!!!",R.drawable.dont_sign);
                        }
                    }
                }else{
                    String getItem_Name=mProductList.getText().toString();
                    if(getItem_Name.length()>0&&checkitem==true){
                        ProductModel model=new ProductModel();
                        model.setProductName(getItem_Name);
                        double sum=0;
                        for (int i=0;i<fuelTypeLists.size();i++){
                            if(fuelTypeLists.get(i).getItem_Name().equals(getItem_Name)){
                                model.setPrice(fuelTypeLists.get(i).getPrice());
                                model.setQty(fuelTypeLists.get(i).getVolume_ltr());
                                arrayListid.add(fuelTypeLists.get(i).getId());
                                items_price.add(fuelTypeLists.get(i).getPrice());

                            }
                        }
                        for (int i=0;i<arrayListid.size();i++){
                            String id=arrayListid.get(i);
                            for (int j=0;j<fuelTypeLists.size();j++){
                                String idd=fuelTypeLists.get(j).getId();
                                if(id.equals(idd)){
                                    sum=sum+Double.parseDouble(fuelTypeLists.get(j).getPrice());}
                            }
                        }
                        models.add(model);
                        checkitem=false;
                        mProductList.setText("");
                        total.setText(""+sum);
                        totallayout.setVisibility(View.VISIBLE);
                        NobarCodeProductAdapter adapter = new NobarCodeProductAdapter(models, NewLubeRequest.this);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(NewLubeRequest.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);
                        verify.setVisibility(View.VISIBLE);
                    }else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(NewLubeRequest.this,"Search Items..!!!!",R.drawable.dont_sign);
                    }
                }



            }
        });
        mProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkitem=true;}
        });
    }
    public void CallLubeListApi(String key){
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.lubetypeList(key);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest=response.body();
                pb.dismiss();
                String status=customerRequest.getStatus();
                if(status.equalsIgnoreCase("success")){
                    Product_name=new ArrayList<>();
                    Product_price=new ArrayList<>();
                    Product_qty=new ArrayList<>();
                    fuelTypeLists=customerRequest.getCustomerRequestResponse().getLubeLists();
                    if(fuelTypeLists!=null){
                        for (int i=0;i<fuelTypeLists.size();i++){
                            String ProductName=fuelTypeLists.get(i).getItem_Name();
                            if(!Product_name.contains(ProductName)){
                                Product_name.add(ProductName);
                                Product_price.add(fuelTypeLists.get(i).getPrice());
                                Product_qty.add(fuelTypeLists.get(i).getVolume_ltr());
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewLubeRequest.this, android.R.layout.simple_dropdown_item_1line, Product_name);
                            mProductList.setAdapter(adapter);
                        }
                    }
                    else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(NewLubeRequest.this,"No Record Found..!!!",R.drawable.dont_sign);
                        finish();
                    }
                }else{
                    Toast.makeText(NewLubeRequest.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(NewLubeRequest.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }}
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(NewLubeRequest.this,"Connection Failed..!!!",R.drawable.dont_sign);
            }
        });
    }
    private boolean Validate() {
        qtysss=new ArrayList<>();
        for(int i=0;i<models.size();i++) {
            View v = recyclerView.getLayoutManager().findViewByPosition(i);
            qty = v.findViewById(R.id.times);
            if(qty.getText().toString().length()>0){
                qtysss.add(qty.getText().toString());
            }else {
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(NewLubeRequest.this,"Quantity an not be less than 0..!!!",R.drawable.dont_sign);
                return  false;
            }
        }
        return  true;
    }
    private void CallVerifyApi(final String total, final String s,final String qty) {
        final ProgressDialog pb = new ProgressDialog(NewLubeRequest.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.product_tax(manager.getKey("key"),s,manager.getUserDetails().get("state"),statefinal);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse=response.body();
                String status=requestResponse.getStatus();
                JSONArray jsonArray=new JSONArray();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")) {
                    List<ItemtaxModel>itemtaxModelList=requestResponse.getCustomerRequestResponse().getItemtax();
                    if(itemtaxModelList!=null){
                        for (int k=0;k<itemtaxModelList.size();k++){
                            try {
                                JSONObject object=new JSONObject();
                                String name=null;
                                object.put("id",itemtaxModelList.get(k).getId());
                                for (int i=0;i<arrayListid.size();i++){
                                    String id=arrayListid.get(i);
                                    String qty=qtysss.get(i);
                                    if(id.equals(itemtaxModelList.get(k).getId())){
                                        object.put("quantity",qty);
                                        object.put("price",Double.parseDouble(itemtaxModelList.get(k).getPrice())*Double.parseDouble(qty));
                                    }
                                }
                                if(itemtaxModelList.get(k).getItem_Name().length()>15){
                                    name=itemtaxModelList.get(k).getItem_Name().substring(0,14);
                                }
                                else {
                                    name  =itemtaxModelList.get(k).getItem_Name();
                                }
                                object.put("Item_Name",name);
                                object.put("price",itemtaxModelList.get(k).getPrice());
                                object.put("tax_type",itemtaxModelList.get(k).getTax_type());
                                object.put("Tax_percentage",itemtaxModelList.get(k).getTax_percentage());
                                object.put("Description",itemtaxModelList.get(k).getDescription());
                                object.put("tax_name",itemtaxModelList.get(k).getTax_name());
                                object.put("strate_type",itemtaxModelList.get(k).getStrate_type());
                                object.put("hsncode",itemtaxModelList.get(k).getHsncode());
                                object.put("position",itemtaxModelList.get(k).getPosition());
                                jsonArray.put(object);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}
                        double  n =new Date().getDate()+new Date().getTime();
                        final String  SlipDetail="GRD"+String.valueOf(n);
                        String item_Prices = items_price.toString().replace("[", "").toString().replace("]", "");
                        Intent i=new Intent(NewLubeRequest.this,WalkinVerifyMobileNumberLubeActivity.class);
                        i.putExtra("Driver_Mobile", Driver_Mobile);
                        i.putExtra("item_code", s);
                        i.putExtra("Customer_Code",Customer_Code);
                        i.putExtra("itemprice", item_Prices);
                        i.putExtra("petrol_or_lube", "2");
                        i.putExtra("SlipDetail", SlipDetail);
                        i.putExtra("petroldiesel_type","");
                        i.putExtra("petroldiesel_qty","");
                        i.putExtra("request_id","NoRequest");
                        i.putExtra("vehicle_no",Vehicle_No);
                        i.putExtra("Trans_By",manager.getUserDetails().get("id"));
                        i.putExtra("cust_type","credit");
                        i.putExtra("total",""+total);
                        i.putExtra("cust_name",cust_name);
                        i.putExtra("Cust_GST",Cust_GST);
                        i.putExtra("qty",qty);
                        i.putExtra("Cust_mobile",Cust_mobile);
                        i.putExtra("address",Customeraddress);
                        i.putExtra("drivername",drivername);
                        i.putExtra("company_name",company_name);
                        i.putExtra("taxdata",jsonArray.toString());
                        i.putExtra("lubecurrent_driver_mobile","");
                        i.putExtra("order_date",order_date);
                        i.putExtra("pin_no",pin_no);
                        i.putExtra("pan_no",pan_no);
                        i.putExtra("state_code",state_code);
                        i.putExtra("payment_mode",payment_mode);
                        i.putExtra("customer_city",customer_city);
                        i.putExtra("pending_request","0");
                        i.putExtra("activity","newlube_request");
                        startActivity(i);
                        finish();
                    }else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(NewLubeRequest.this,"No Record Found..!!!",R.drawable.dont_sign);
                    }
                }else{
                    Toast.makeText(NewLubeRequest.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(NewLubeRequest.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }}
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(NewLubeRequest.this,"Connection Failed..!!!",R.drawable.dont_sign);
            }
        });
    }
    private boolean duplicate_entry() {
        String getItem_Name=mProductList.getText().toString();
        for (int l=0;l<models.size();l++){
            if(models.get(l).getProductName().equals(getItem_Name)){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(NewLubeRequest.this,"Product already selected..!!!!",R.drawable.dont_sign);
                return  false;
            }
        }
        return  true;
    }
}
