package com.cybermatrixsolutions.invoicesolutions.activity.WithoutQR;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.With_QR.Fuel_Lube_List;
import com.cybermatrixsolutions.invoicesolutions.activity.printing_activity.PrintWithoutQR_For_Fuel;
import com.cybermatrixsolutions.invoicesolutions.activity.printing_activity.PrintLubeActivity;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaction extends AppCompatActivity implements View.OnClickListener{
    LinearLayout sendOtp_layout,verifyOtp_layout;
    EditText mobile_no_edit,et_otp;
    TextView send_otp;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    TextView resendotp,verify;
    String mobile;
    String mVerificationId;
    String Driver_Mobile,item_code,Customer_Code,itemprice,petrol_or_lube,SlipDetail,petroldiesel_type,petroldiesel_qty,request_id,vehicle_no,Trans_By,
            cust_type,total,cust_name,Cust_GST,Cust_mobile,drivername,address_one,address_two,address_three,company_name,taxdata,lubecurrent_driver_mobile;
    PrefsManager manager;
    LinearLayout inoice_layout,main_layout;
    String item_name;
    TextView print_invoice;
    String qty,invoice_no;
    String type,ordererd_qty,order_date,pin_no,pan_no,state_code,statefinal,fuel,payment_mode,custmer_city,pending_request,activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        inoice_layout=(LinearLayout) findViewById(R.id.inoice_layout);
        Driver_Mobile= getIntent().getStringExtra("Driver_Mobile");
        item_code=getIntent().getStringExtra("item_code");
        Customer_Code=getIntent().getStringExtra("Customer_Code");
        itemprice=getIntent().getStringExtra("itemprice");
        petrol_or_lube=getIntent().getStringExtra("petrol_or_lube");
        SlipDetail=getIntent().getStringExtra("SlipDetail");
        petroldiesel_type=getIntent().getStringExtra("petroldiesel_type");
        petroldiesel_qty=getIntent().getStringExtra("petroldiesel_qty");
        request_id=getIntent().getStringExtra("request_id");
        vehicle_no=getIntent().getStringExtra("vehicle_no");
        Trans_By=getIntent().getStringExtra("Trans_By");
        cust_type=getIntent().getStringExtra("cust_type");
        total=getIntent().getStringExtra("total");
        cust_name=getIntent().getStringExtra("cust_name");
        Cust_GST=getIntent().getStringExtra("Cust_GST");
        Cust_mobile=getIntent().getStringExtra("Cust_mobile");
        drivername=getIntent().getStringExtra("drivername");
        address_one = getIntent().getStringExtra("address");
        lubecurrent_driver_mobile= getIntent().getStringExtra("lubecurrent_driver_mobile");
        drivername = getIntent().getStringExtra("drivername");
        company_name = getIntent().getStringExtra("company_name");
        taxdata = getIntent().getStringExtra("taxdata");
        qty = getIntent().getStringExtra("qty");
        ordererd_qty= getIntent().getStringExtra("ordererd_qty");
        type= getIntent().getStringExtra("type");
        order_date= getIntent().getStringExtra("order_date");
        pin_no= getIntent().getStringExtra("pin_no");
        pan_no= getIntent().getStringExtra("pan_no");
        state_code= getIntent().getStringExtra("state_code");
        statefinal= getIntent().getStringExtra("statefinal");
        item_name= getIntent().getStringExtra("item_name");
        fuel= getIntent().getStringExtra("fuel");
        payment_mode= getIntent().getStringExtra("payment_mode");
        custmer_city= getIntent().getStringExtra("customer_city");
        pending_request= getIntent().getStringExtra("pending_request");
        activity= getIntent().getStringExtra("activity");
        manager=new PrefsManager(this);
        print_invoice=(TextView)findViewById(R.id.print_invoice);
        print_invoice.setOnClickListener(this);
        mobile=lubecurrent_driver_mobile;
        try {
             CallTransactionApi(manager.getKey("key"),Customer_Code,item_code,itemprice,petrol_or_lube,SlipDetail,petroldiesel_type,petroldiesel_qty,
                    request_id,vehicle_no,Driver_Mobile,Trans_By,cust_type,String.valueOf(total),cust_name,Cust_GST,Cust_mobile,mobile,qty);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void CallTransactionApi(String key,String customer_code,String item_code,String item_price,String petrol_or_lube, String slip_detail,String petroldiesel_typ,String petroldiesel_qty
            ,String Request_id,String Vehicle_Reg_No,String Driver_Mobile,String
                                               Trans_By,String cust_type,String total,String cust_name,String cust_gst,String cust_mob,String Trans_Mobile,String qty){
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.transaction1(key, customer_code, item_code,item_price, petrol_or_lube, slip_detail
                ,petroldiesel_typ,petroldiesel_qty, Request_id, Vehicle_Reg_No, Driver_Mobile, Trans_By,cust_type,total,cust_name,cust_gst,cust_mob,Trans_Mobile,qty);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest=response.body();
                String status=customerRequest.getStatus();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")){
                    invoice_no=customerRequest.getCustomerRequestResponse().getInvoice();
                    Toast.makeText(getApplicationContext(),"Transaction Sucessfull",Toast.LENGTH_LONG).show();
                    inoice_layout.setVisibility(View.VISIBLE);
                    try {
                        Fuel_Lube_List.lubelistarray.clear();
                        Fuel_Lube_List.new_lube_request.setVisibility(View.GONE);
                        Fuel_Lube_List.new_fuel_request_layout.setVisibility(View.GONE);
                        Fuel_Lube_List.new_lube_select_item.setVisibility(View.GONE);
                    }catch (Exception e){
                    }
                    try {
                        if(fuel.equals("1")){
                            PetrolCustomerDetailWithoutQrCodeActivity.customerRequestListArrayList.clear();
                        }else {
                            PetrolCustomerDetailWithoutQrCodeActivity.lubelistarray.clear();
                        }
                    }catch (Exception e){
                    }
                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(Transaction.this,"Transaction Failed..!!!!",R.drawable.dont_sign);
                }
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(Transaction.this,"Server Error ..!!!!",R.drawable.dont_sign);
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onClick(View v) {
        if (v == print_invoice) {
            if(fuel.equals("1")){
                Intent i = new Intent(this, PrintWithoutQR_For_Fuel.class);
                i.putExtra("address", address_one);
                i.putExtra("gst_no", Cust_GST);
                i.putExtra("Customer_Name", cust_name);
                i.putExtra("Driver_Name", drivername);
                i.putExtra("Vehicle_No", vehicle_no);
                i.putExtra("mobile", Driver_Mobile);
                i.putExtra("RequestId", request_id);
                i.putExtra("petroldiesel_type", petroldiesel_type);
                i.putExtra("amount", petroldiesel_qty);
                i.putExtra("RequestId", request_id);
                i.putExtra("company_name", company_name);
                i.putExtra("type",type);
                i.putExtra("ordererd_qty",ordererd_qty);
                i.putExtra("order_date",order_date);
                i.putExtra("pin_no",pin_no);
                i.putExtra("pan_no",pan_no);
                i.putExtra("state_code",state_code);
                i.putExtra("statefinal",statefinal);
                i.putExtra("item_name",item_name);
                i.putExtra("payment_mode",payment_mode);
                i.putExtra("customer_city",custmer_city);
                i.putExtra("pending_request",pending_request);
                i.putExtra("activity",activity);
                i.putExtra("invoice_no",invoice_no);
                startActivity(i);
                finish();
            }else {
                Intent i = new Intent(this, PrintLubeActivity.class);
                i.putExtra("address", address_one);
                i.putExtra("gst_no", Cust_GST);
                i.putExtra("Customer_Name", cust_name);
                i.putExtra("Driver_Name", drivername);
                i.putExtra("Vehicle_No", vehicle_no);
                i.putExtra("mobile", Driver_Mobile);
                i.putExtra("RequestId", request_id);
                i.putExtra("petroldiesel_type", petroldiesel_type);
                i.putExtra("amount", petroldiesel_qty);
                i.putExtra("RequestId", request_id);
                i.putExtra("company_name", company_name);
                i.putExtra("taxdata", taxdata);
                i.putExtra("type",type);
                i.putExtra("ordererd_qty",ordererd_qty);
                i.putExtra("order_date",order_date);
                i.putExtra("pin_no",pin_no);
                i.putExtra("pan_no",pan_no);
                i.putExtra("state_code",state_code);
                i.putExtra("statefinal",statefinal);
                i.putExtra("item_name",item_name);
                i.putExtra("payment_mode",payment_mode);
                i.putExtra("customer_city",custmer_city);
                i.putExtra("pending_request",pending_request);
                i.putExtra("activity",activity);
                i.putExtra("invoice_no",invoice_no);
                startActivity(i);
                finish();
            }
        }
    }
}
