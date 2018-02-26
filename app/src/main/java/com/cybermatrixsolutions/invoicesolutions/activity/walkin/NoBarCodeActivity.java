package com.cybermatrixsolutions.invoicesolutions.activity.walkin;

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
import com.cybermatrixsolutions.invoicesolutions.adapter.NobarCodeProductAdapter;
import com.cybermatrixsolutions.invoicesolutions.activity.verify_mobile.VerifyMobileNumberLubeActivity;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoBarCodeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView total;
    LinearLayout totallayout;
    private Button add,verify;
    private AutoCompleteTextView mProductList;
    List<ProductModel> models=new ArrayList<>();
    List<LubeList> fuelTypeLists;
    PrefsManager prefsManager;
    PrefsManager manager;
    ArrayList<String>arrayListid;
    boolean checkitem=false;
    List<String> Product_name;
    List<String> Product_price;
    List<String> Product_qty;
    List<String> sumtotal;
    List<String> items_price;
    EditText  qty;
    List<String> qtysss;
EditText productName;
String mobile,customer_number,product,customer_name,gstNumber,
        OilType,Customer_Code,Ro_code,petrol_or_lube,RequestId,Vehicle_No,cust_type,Trans_By,
        Customeraddress,Customerstate,states_code,customer_city,pin_no,pan_no;
String KEY=null;
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

        Customeraddress=getIntent().getStringExtra("Customeraddress");
        Customerstate=getIntent().getStringExtra("Customerstate");
        mobile=getIntent().getStringExtra("mobile");
        customer_number=getIntent().getStringExtra("customer_number");
        product=getIntent().getStringExtra("product");
        customer_name=getIntent().getStringExtra("customer_name");
        gstNumber=getIntent().getStringExtra("gstNumber");
        OilType=getIntent().getStringExtra("OilType");
        Customer_Code=getIntent().getStringExtra("Customer_Code");
        Ro_code=getIntent().getStringExtra("Ro_code");
        petrol_or_lube=getIntent().getStringExtra("petrol_or_lube");
        RequestId=getIntent().getStringExtra("RequestId");
        Vehicle_No=getIntent().getStringExtra("Vehicle_No");
        cust_type=getIntent().getStringExtra("cust_type");
        Trans_By=getIntent().getStringExtra("Trans_By");
        Customeraddress=getIntent().getStringExtra("Customeraddress");
        states_code=getIntent().getStringExtra("states_code");
        customer_city=getIntent().getStringExtra("customer_city");
        pin_no=getIntent().getStringExtra("pin_no");
        pan_no=getIntent().getStringExtra("pan_no");
        KEY=manager.getKey("key");
        verify=(Button)findViewById(R.id.verifyOtp);
        if(KEY!=null) {
            try {
                CallLubeListApi(KEY);

            } catch (Exception e) {
                e.printStackTrace();
            }}
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
                            NobarCodeProductAdapter adapter = new NobarCodeProductAdapter(models, NoBarCodeActivity.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(NoBarCodeActivity.this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(layoutManager);
                            verify.setVisibility(View.VISIBLE);
                        }else {
                            CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,"Search Items..!!!!",R.drawable.dont_sign);
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
                        NobarCodeProductAdapter adapter = new NobarCodeProductAdapter(models, NoBarCodeActivity.this);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(NoBarCodeActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);
                        verify.setVisibility(View.VISIBLE);
                    }else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,"Search Items..!!!!",R.drawable.dont_sign);
                    }
                }
            }
        });
        mProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkitem=true;
            }
        });
    }

    private boolean duplicate_entry() {

        String getItem_Name=mProductList.getText().toString();


        for (int l=0;l<models.size();l++){
            if(models.get(l).getProductName().equals(getItem_Name)){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,"Product already selected..!!!!",R.drawable.dont_sign);
            return  false;
            }
        }
        return  true;
    }

    private boolean Validate() {
        qtysss=new ArrayList<>();
        for(int i=0;i<models.size();i++) {
            View v = recyclerView.getLayoutManager().findViewByPosition(i);
            qty = v.findViewById(R.id.times);
            if(qty.getText().toString().length()>0){
                qtysss.add(qty.getText().toString());
            }else {
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,"Enter Quantity..!!!!",R.drawable.dont_sign);
                return  false;
            }


        }
        return  true;
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
                String status=customerRequest.getStatus();
                pb.dismiss();
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
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(NoBarCodeActivity.this, android.R.layout.simple_dropdown_item_1line, Product_name);
                            mProductList.setAdapter(adapter);
                        }
                    }
                    else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,"Items Not Found..!!!!",R.drawable.dont_sign);

                    }
                }else{
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,"Login Session Expired..!!!!",R.drawable.dont_sign);
                }

            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,"Connection Failed..!!!!",R.drawable.dont_sign);

            }
        });
    }
    private void CallVerifyApi(final String total, final String s,final String qtys) {
        final ProgressDialog pb = new ProgressDialog(NoBarCodeActivity.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.product_tax(manager.getKey("key"),s,manager.getUserDetails().get("state"),Customerstate);
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
                            }
                        }
                        double  n =new Date().getDate()+new Date().getTime();
                        final String  SlipDetail="GRD"+String.valueOf(n);
                        String item_Prices = items_price.toString().replace("[", "").toString().replace("]", "");
                        Intent i=new Intent(NoBarCodeActivity.this,WalkinVerifyMobileNumberLubeActivity.class);
                        Calendar calendar=Calendar.getInstance();
                        int   year=calendar.get(Calendar.YEAR);
                        int  month=calendar.get(Calendar.MONTH);
                        int   day=calendar.get(Calendar.DAY_OF_MONTH);
                        month=month+1;
                        i.putExtra("Driver_Mobile", customer_number);
                        i.putExtra("item_code", s);
                        i.putExtra("Customer_Code",Customer_Code);
                        i.putExtra("itemprice", item_Prices);
                        i.putExtra("petrol_or_lube", "2");
                        i.putExtra("SlipDetail", SlipDetail);
                        i.putExtra("petroldiesel_type","0");
                        i.putExtra("petroldiesel_qty","0");
                        i.putExtra("request_id",RequestId);
                        i.putExtra("vehicle_no",Vehicle_No);
                        i.putExtra("Trans_By",manager.getUserDetails().get("id"));
                        i.putExtra("cust_type","credit");
                        i.putExtra("total",""+total);
                        i.putExtra("cust_name",customer_name);
                        i.putExtra("Cust_GST",gstNumber);
                        i.putExtra("Cust_mobile",customer_number);
                        i.putExtra("address",Customeraddress);
                        i.putExtra("drivername",customer_name);
                        i.putExtra("qty",qtys);
                        i.putExtra("company_name",customer_name);
                        i.putExtra("taxdata",jsonArray.toString());
                        i.putExtra("lubecurrent_driver_mobile","");
                        i.putExtra("fuel","2");
                        i.putExtra("state_code",states_code);
                        i.putExtra("statefinal",Customerstate);
                        i.putExtra("order_date",""+day+"/"+month+"/"+year);
                        i. putExtra("payment_mode","Cash");
                        i.putExtra("customer_city", customer_city);
                        i.putExtra("pin_no", pin_no);
                        i.putExtra("pan_no", pan_no);
                        startActivity(i);
                        finish();
                    }else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,"No Record Found...!!!",R.drawable.dont_sign);
                    }
                }else{
                   CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,status,R.drawable.dont_sign);


                }
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(NoBarCodeActivity.this,"Connection Failed..!!!!",R.drawable.dont_sign);

            }
        });








    }






}
