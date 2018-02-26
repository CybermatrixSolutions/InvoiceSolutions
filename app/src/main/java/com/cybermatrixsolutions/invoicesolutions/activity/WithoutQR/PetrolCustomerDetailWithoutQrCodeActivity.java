package com.cybermatrixsolutions.invoicesolutions.activity.WithoutQR;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.adapter.Lube_Oil_RequestWithout_Qr_Adapter;
import com.cybermatrixsolutions.invoicesolutions.activity.verify_mobile.VerifyMobileNumberLubeActivity;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequestList;
import com.cybermatrixsolutions.invoicesolutions.model.ItemtaxModel;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;
import android.support.v7.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetrolCustomerDetailWithoutQrCodeActivity extends AppCompatActivity implements View.OnClickListener{
    public  static  String Customer_Name, Customer_Code, Credit_limit, Registration_Number, Driver_Name,itemprice;
    public  static  String  RO_code, address_one, Customer_Type, Mobile, IMEI_No, Email, company_name, address_two, address_three,
            pin_no, pan_no, statefinal, cityfinal,Request_Value,state_code;

    public  static    String Petrol_Diesel_Type, Request_Type, request_date,gst_no,Phone_Number,lubecurrent_driver_mobile,current_driver_mobile;

    String lubedata;

    TextView company_code, customer_name, driver_name, driverMobile, van_number;
    EditText et_driverMobile1;
    RecyclerView recyclerView, recyclerView1;
    ArrayList<String> arrayList;
   public static ArrayList<CustomerRequestList> customerRequestListArrayList;
    public  static String Driver_Mobile,id,request_id;
    public  static ArrayList<CustomerRequestList> lubelistarray;
    TextView total_lube;
    TextView lube_proceed;
    CardView fuel_cart, lube_request_cart;
    List<CustomerRequestList> fuelTypeLists;
    ArrayList<String> fuel_type;
    ArrayList<String> fuel_type_price;
    Spinner spinner_fueltype;
    TextView customerRequest;
    TextView add_new_lube;
    PrefsManager manager;
    ArrayList<String> selectlubenameList;
    ArrayList<String> selectlubeIdList;
    ArrayList<String> selectlubepriceList;
    ArrayList<String> lubeNameArrayList;
    ArrayList<String> lubeIdarrayList;
    ArrayList<String> lubePricearrayList;
    ArrayList<String> assignlubeNameList;
    ArrayList<String> assignlubepriceList;
    ArrayList<String> assignlubeIdList;
    TextView select_item;
    TextView btn_fuel_proceed;
    EditText et_fuel_;
    String   spinnerItemcode,spinnerItemprice,petroldiesel_type;
    TextView btn_new_lube_proceed;
    ArrayList<String>checked_array_lube_name;
    ArrayList<String>checked_array_lube_id;
    String luberequest_id;
    ArrayList<String>checked_array_lube_amount;
    ArrayList<String>checked_arrayqty;
    EditText qty;
    String order_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail_without_qr_code);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Customer_Name = getIntent().getStringExtra("Customer_Name");
        Customer_Code = getIntent().getStringExtra("Customer_Code");
        Credit_limit = getIntent().getStringExtra("Credit_limit");
        Registration_Number = getIntent().getStringExtra("Registration_Number");
        Driver_Name = getIntent().getStringExtra("Driver_Name");
        Driver_Mobile = getIntent().getStringExtra("Driver_Mobile");
        id = getIntent().getStringExtra("id");
        RO_code = getIntent().getStringExtra("RO_code");
        Customer_Type = getIntent().getStringExtra("Customer_Type");
        Phone_Number = getIntent().getStringExtra("Phone_Number");
        Mobile = getIntent().getStringExtra("Mobile");
        IMEI_No = getIntent().getStringExtra("IMEI_No");
        Email = getIntent().getStringExtra("Email");
        gst_no = getIntent().getStringExtra("gst_no");
        company_name = getIntent().getStringExtra("company_name");
        address_one = getIntent().getStringExtra("address_one");
        address_two = getIntent().getStringExtra("address_two");
        address_three = getIntent().getStringExtra("address_three");
        pin_no = getIntent().getStringExtra("pin_no");
        pan_no = getIntent().getStringExtra("pan_no");
        statefinal = getIntent().getStringExtra("statefinal");
        cityfinal = getIntent().getStringExtra("cityfinal");
        Petrol_Diesel_Type = getIntent().getStringExtra("Petrol_Diesel_Type");
        Request_Type = getIntent().getStringExtra("Request_Type");
        request_date = getIntent().getStringExtra("request_date");
        request_id= getIntent().getStringExtra("request_id");
        Request_Value= getIntent().getStringExtra("Request_Value");
        current_driver_mobile= getIntent().getStringExtra("current_driver_mobile");
        state_code= getIntent().getStringExtra("state_code");
        customerRequestListArrayList = new ArrayList<>();
        company_code = (TextView) findViewById(R.id.company_code);
        customer_name = (TextView) findViewById(R.id.customer_name);
        driver_name = (TextView) findViewById(R.id.driver_name);
        driverMobile = (TextView) findViewById(R.id.driverMobile);
        van_number = (TextView) findViewById(R.id.van_number);
        total_lube = (TextView) findViewById(R.id.total_lube);
        lube_proceed = (TextView) findViewById(R.id.lube_proceed);
        et_driverMobile1 = (EditText) findViewById(R.id.et_driverMobile1);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);
        fuel_cart = (CardView) findViewById(R.id.fuel_cart);
        lube_proceed.setOnClickListener(this);
        lube_request_cart = (CardView) findViewById(R.id.lube_request_cart);
        customerRequest = (TextView) findViewById(R.id.customerRequest);
        checked_array_lube_name=new ArrayList<>();
        checked_array_lube_id=new ArrayList<>();
        checked_array_lube_amount=new ArrayList<>();
        customer_name.setText(Customer_Name);
        company_code.setText(Customer_Code);
        driver_name.setText(Driver_Name);
        driverMobile.setText(Driver_Mobile);
        van_number.setText(Registration_Number);
        et_driverMobile1.setText(Driver_Mobile);
        manager = new PrefsManager(this);
        selectlubeIdList=new ArrayList<>();
        selectlubenameList=new ArrayList<>();
        if (id.equals("")) {
            fuel_cart.setVisibility(View.GONE);
        } else {

            fuel_cart.setVisibility(View.VISIBLE);
        }
        CustomerRequestList customerRequestList = new CustomerRequestList();
        customerRequestList.setRequest_date(request_date);
        customerRequestList.setId(id);
        customerRequestList.setPetrol_Diesel_Type(Petrol_Diesel_Type);
        customerRequestList.setRequest_Type(Request_Type);
        customerRequestList.setRequest_Value(Request_Value);
        customerRequestList.setPrice(getIntent().getStringExtra("price"));
        itemprice=getIntent().getStringExtra("price");
        customerRequestListArrayList.add(customerRequestList);
        lubelistarray = new ArrayList<>();
        if (Petrol_Diesel_Type.equals("Petrol")) {
            arrayList = new ArrayList<>();
            arrayList.add("Petrol");
            arrayList.add("Diesel");
        } else {
            arrayList = new ArrayList<>();
            arrayList.add("Diesel");
            arrayList.add("Petrol");
        }
        Lube_Oil_RequestWithout_Qr_Adapter adapter = new Lube_Oil_RequestWithout_Qr_Adapter(PetrolCustomerDetailWithoutQrCodeActivity.this, customerRequestListArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PetrolCustomerDetailWithoutQrCodeActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        lubedata = getIntent().getStringExtra("lubedata");
        double total = 0;
        try {
            if (lubedata != null) {
                JSONArray array = new JSONArray(lubedata);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    customerRequestList = new CustomerRequestList();
                    order_date=object.getString("luberequest_date");
                    customerRequestList.setRequest_date(object.getString("luberequest_date"));
                    customerRequestList.setPetrol_Diesel_Type("Lube");
                    customerRequestList.setRequest_Type(object.getString("lubeitem_name"));
                    customerRequestList.setPrice(object.getString("lubeprice"));
                    customerRequestList.setItem_code(object.getString("lubeid"));
                    customerRequestList.setQuantity(object.getString("quantity"));
                    luberequest_id=object.getString("luberequest_id");
                    lubecurrent_driver_mobile=object.getString("lubecurrent_driver_mobile");
                    lubelistarray.add(customerRequestList);
                    double price = Double.parseDouble(object.getString("lubeprice"));
                    total = total + price;
                }
                total_lube.setText("Total : " + total);
                LubeListAdapter lubeListAdapter = new LubeListAdapter(PetrolCustomerDetailWithoutQrCodeActivity.this, lubelistarray);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(PetrolCustomerDetailWithoutQrCodeActivity.this);
                recyclerView1.setAdapter(lubeListAdapter);
                recyclerView1.setLayoutManager(layoutManager1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (lubedata == null) {
            lube_request_cart.setVisibility(View.GONE);
        } else {
            lube_request_cart.setVisibility(View.VISIBLE);

        }
        if (id.equals("") && lubedata == null) {
            customerRequest.setText("No Customer Request Found");
            customerRequest.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            customerRequest.setText("Customer Requests");
            customerRequest.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }
    @Override
    public void onClick(View v) {
        if (v == lube_proceed) {
            if(checked_array_lube_id.size()>0){
                if(Validate()){
                    String textViewtext = "" + PetrolCustomerDetailWithoutQrCodeActivity.this.checked_array_lube_id;
                    String checkqty = "" + PetrolCustomerDetailWithoutQrCodeActivity.this.checked_arrayqty;
                    textViewtext = textViewtext.replaceAll("\\[|\\]", "");
                    checkqty = checkqty.replaceAll("\\[|\\]", "");
                    CallApi(textViewtext,checkqty);
                }
            }else {
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(PetrolCustomerDetailWithoutQrCodeActivity.this,"Please select Item to Preceed...!!!",R.drawable.dont_sign);

                }
        }

    }

    private boolean Validate() {
        checked_arrayqty=new ArrayList<>();
        for (int i=0;i<lubelistarray.size();i++){
            View view=recyclerView1.getChildAt(i);
            CheckBox  checkbox = view.findViewById(R.id.checkbox);
            qty= view.findViewById(R.id.qty);
            if(checkbox.isChecked()){
                if(qty.getText().toString().length()==0||qty.getText().toString().equals("")){
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(PetrolCustomerDetailWithoutQrCodeActivity.this,"Checked Item Quantity Should be Greator than 0...!!!",R.drawable.dont_sign);
                    return  false;
                }
                if(qty.getText().toString().equals("0")){
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(PetrolCustomerDetailWithoutQrCodeActivity.this,"Checked Item Quantity Should be Greator than 0...!!!",R.drawable.dont_sign);
                    return  false;
                }
                if(qty.getText().toString().length()!=0||!qty.getText().toString().equals("")||!qty.getText().toString().equals("0")){
                    checked_arrayqty.add(qty.getText().toString());

                }
            }

        }


        return  true;
    }

    public class LubeListAdapter  extends RecyclerView.Adapter<LubeListAdapter.ViewHolder> {
        Context context;
        public LubeListAdapter(Context context, List<CustomerRequestList> qrScanResultList) {
            this.context = context;
            this.qrScanResultList = qrScanResultList;
        }
        private List<CustomerRequestList> qrScanResultList;
        @Override
        public LubeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lube_request_layout, parent, false);
            return new LubeListAdapter.ViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final LubeListAdapter.ViewHolder holder, final int position) {
            holder.fuel.setText(qrScanResultList.get(position).getPetrol_Diesel_Type());
            String requestDate = qrScanResultList.get(position).getRequest_date();
            final String requestType = qrScanResultList.get(position).getRequestType();
            holder.qty.setText(qrScanResultList.get(position).getQuantity());
            holder.qty.setFilters(new InputFilter[]{new InputFilterMinMax(1, Integer.parseInt(qrScanResultList.get(position).getQuantity()))});
            holder.date.setText(qrScanResultList.get(position).getRequest_date());
            holder.lube.setText(requestType);
            holder.price.setText(qrScanResultList.get(position).getPrice());
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(holder.checkbox.isChecked()){
                        checked_array_lube_name.add(qrScanResultList.get(position).getRequestType());
                        checked_array_lube_id.add(qrScanResultList.get(position).getItem_code());
                        checked_array_lube_amount.add(qrScanResultList.get(position).getPrice());

                    }else {
                        checked_array_lube_name.remove(qrScanResultList.get(position).getRequestType());
                        checked_array_lube_id.remove(qrScanResultList.get(position).getItem_code());
                        checked_array_lube_amount.remove(qrScanResultList.get(position).getPrice());
                    }}
            });
        }
        @Override
        public int getItemCount() {
            return qrScanResultList.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView fuel;
            TextView lube, date, price;
            Spinner spinner;
            EditText qty;
            LinearLayout spinnerlayout;
            public CheckBox checkbox;
            public ViewHolder(View itemView) {
                super(itemView);
                fuel = itemView.findViewById(R.id.fuel);
                lube = itemView.findViewById(R.id.lube);
                date = itemView.findViewById(R.id.request_date);
                spinner = itemView.findViewById(R.id.spinner);
                price = itemView.findViewById(R.id.price);
                checkbox = itemView.findViewById(R.id.checkbox);
                qty= itemView.findViewById(R.id.qty);
            }}}
    private void CallApi(String s, final String textViewqty) {
        final ProgressDialog pb = new ProgressDialog(PetrolCustomerDetailWithoutQrCodeActivity.this);
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
                                for (int i=0;i<checked_array_lube_id.size();i++){
                                    String id=checked_array_lube_id.get(i);
                                    String qty=checked_arrayqty.get(i);
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
                            }
                        }
                        String textViewtext = "" + PetrolCustomerDetailWithoutQrCodeActivity.this.checked_array_lube_id;
                        textViewtext = textViewtext.replaceAll("\\[|\\]", "");
                        double total=0;
                        for (int ll=0;ll<checked_array_lube_amount.size();ll++){
                            total=total+Integer.parseInt(checked_array_lube_amount.get(ll));
                        }
                        String checked_array_lube_amountarraylist = "" + PetrolCustomerDetailWithoutQrCodeActivity.this.checked_array_lube_amount;
                        checked_array_lube_amountarraylist = checked_array_lube_amountarraylist.replaceAll("\\[|\\]", "");
                        double  n =new Date().getDate()+new Date().getTime();
                        final String  SlipDetail="GRD"+String.valueOf(n);
                        Intent i=new Intent(PetrolCustomerDetailWithoutQrCodeActivity.this,Transaction.class);
                        i.putExtra("Driver_Mobile", Driver_Mobile);
                        i.putExtra("item_code", textViewtext);
                        i.putExtra("Customer_Code",Customer_Code);
                        i.putExtra("itemprice", checked_array_lube_amountarraylist);
                        i.putExtra("petrol_or_lube", "2");
                        i.putExtra("SlipDetail", SlipDetail);
                        i.putExtra("petroldiesel_type","");
                        i.putExtra("petroldiesel_qty","1");
                        i.putExtra("request_id",luberequest_id);
                        i.putExtra("vehicle_no",Registration_Number);
                        i.putExtra("Trans_By",manager.getUserDetails().get("id"));
                        i.putExtra("cust_type","credit");
                        i.putExtra("total",""+total);
                        i.putExtra("cust_name",Customer_Name);
                        i.putExtra("Cust_GST",PetrolCustomerDetailWithoutQrCodeActivity.gst_no);
                        i.putExtra("Cust_mobile",Phone_Number);
                        if(address_three==null){
                            address_three="";
                        } if(address_one==null){
                            address_one="";
                        }if(address_two==null){
                            address_two="";
                        }
                        i.putExtra("address",address_one+" "+address_two+" "+address_three);
                        i.putExtra("address_two",address_two);
                        i.putExtra("address_three",address_three);
                        i.putExtra("drivername",Driver_Name);
                        i.putExtra("company_name",company_name);
                        i.putExtra("taxdata",jsonArray.toString());
                        i.putExtra("qty",textViewqty);
                        i.putExtra("lubecurrent_driver_mobile",lubecurrent_driver_mobile);
                        i.putExtra("fuel","2");
                        i.putExtra("type","");
                        i.putExtra("ordererd_qty","");
                        i.putExtra("order_date",order_date);
                        i.putExtra("pin_no", pin_no);
                        i.putExtra("pan_no",pan_no);
                        i.putExtra("state_code",state_code);
                        i.putExtra("statefinal",statefinal);
                        i.putExtra("petroldiesel_type","");
                        i.putExtra("payment_mode","Credit");
                        i.putExtra("customer_city",cityfinal);
                        if(customerRequestListArrayList.size()>0){
                            i.putExtra("pending_request","1");

                        }else {
                            i.putExtra("pending_request","0");
                        }
                        i.putExtra("activity","withoutQrcode");
                        startActivity(i);
                    }else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(PetrolCustomerDetailWithoutQrCodeActivity.this, "No Record Found...!!!", R.drawable.dont_sign);
                    }
                }else{
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(PetrolCustomerDetailWithoutQrCodeActivity.this,status,R.drawable.dont_sign);
                }}
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(PetrolCustomerDetailWithoutQrCodeActivity.this,"Connection Failed...!!!",R.drawable.dont_sign);
            }});
    }
    public class InputFilterMinMax implements InputFilter {
        private int min;
        private int max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //noinspection EmptyCatchBlock
            try {
                int input = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}