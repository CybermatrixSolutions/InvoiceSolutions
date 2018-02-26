package com.cybermatrixsolutions.invoicesolutions.activity.verify_mobile;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.With_QR.Fuel_Lube_List;
import com.cybermatrixsolutions.invoicesolutions.activity.printing_activity.PrintActivity;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyMobile extends AppCompatActivity implements View.OnClickListener {
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
    cust_type,total,cust_name,Cust_GST,Cust_mobile,address_one,address_two,address_three,drivername,item_name,company_name,current_driver_mobile;
    PrefsManager manager;
    LinearLayout inoice_layout,main_layout;
    TextView print_invoice;
    String type,ordererd_qty,order_date,pin_no,pan_no,state_code,statefinal;
    String customer_city;
    String payment_mode,pending_request,activity;
    String invoice_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sendOtp_layout=(LinearLayout)findViewById(R.id.sendOtp_layout);
        verifyOtp_layout=(LinearLayout)findViewById(R.id.verifyOtp_layout);
        mobile_no_edit=(EditText)findViewById(R.id.mobile_no_edit);
        inoice_layout=(LinearLayout) findViewById(R.id.inoice_layout);
        main_layout=(LinearLayout) findViewById(R.id.main_layout);
        Driver_Mobile= getIntent().getStringExtra("Driver_Mobile");
        mobile_no_edit.setText(Driver_Mobile);
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
        address_one = getIntent().getStringExtra("address");
        payment_mode= getIntent().getStringExtra("payment_mode");
        drivername = getIntent().getStringExtra("drivername");
        item_name= getIntent().getStringExtra("item_name");
        company_name= getIntent().getStringExtra("company_name");
        current_driver_mobile= getIntent().getStringExtra("current_driver_mobile");
        ordererd_qty= getIntent().getStringExtra("ordererd_qty");
        type= getIntent().getStringExtra("type");
        order_date= getIntent().getStringExtra("order_date");
        customer_city= getIntent().getStringExtra("customer_city");
        pin_no= getIntent().getStringExtra("pin_no");
        pan_no= getIntent().getStringExtra("pan_no");
        state_code= getIntent().getStringExtra("state_code");
        statefinal= getIntent().getStringExtra("statefinal");
        pending_request= getIntent().getStringExtra("pending_request");
        activity= getIntent().getStringExtra("activity");
        print_invoice=(TextView)findViewById(R.id.print_invoice);
        print_invoice.setOnClickListener(this);
        manager=new PrefsManager(this);
        if(request_id.equals("NewRequest")){
            sendOtp_layout.setVisibility(View.VISIBLE);
            verifyOtp_layout.setVisibility(View.GONE);
        }else {
            sendOtp_layout.setVisibility(View.GONE);
            verifyOtp_layout.setVisibility(View.VISIBLE);
        }
        et_otp=(EditText)findViewById(R.id.et_otp);
        send_otp=(TextView)findViewById(R.id.send_otp);
        verify=(TextView)findViewById(R.id.verify);
        resendotp=(TextView)findViewById(R.id.resendotp);
        FirebaseApp.initializeApp(this);
        send_otp.setOnClickListener(this);
        verify.setOnClickListener(this);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validatePhoneNumber()){
                    resendVerificationCode("+91"+mobile,mResendToken);
                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("Sucess", "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                resendotp.setVisibility(View.VISIBLE);
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(VerifyMobile.this,"Failed Resend OTP...!!!",R.drawable.dont_sign);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                } else if (e instanceof FirebaseTooManyRequestsException) {

                }
            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d("Code sent", "onCodeSent:" + verificationId);
                mResendToken = token;
                mVerificationId = verificationId;
                sendOtp_layout.setVisibility(View.GONE);
                verifyOtp_layout.setVisibility(View.VISIBLE);
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(VerifyMobile.this,"OTP has been sent to your Mobile Number...!!!",R.drawable.successimage);
            }
        };
    }
    @Override
    public void onClick(View v) {
        if(v==send_otp){
            mobile=mobile_no_edit.getText().toString();
            if(mobile.length()==10){
                startPhoneNumberVerification("+91"+mobile);
            }else {
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(VerifyMobile.this,"Enter Valid 10 digit mobile number...!!!",R.drawable.dont_sign);
            }
        }
        if(v==verify){
            if(request_id.equals("NewRequest")){
                String code = et_otp.getText().toString();
                if(code.length()==0){
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(VerifyMobile.this,"Please Enter Otp...!!!",R.drawable.dont_sign);
                }else {
                    verifyPhoneNumberWithCode(mVerificationId, code);
                }
            }else {
                String code = et_otp.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    et_otp.setError("Cannot be empty.");
                    return;
                }else {
                    mobile=current_driver_mobile;
                    calluserValidate_Api(current_driver_mobile,code,manager.getKey("key"));


                }
            }

    }
        if(v==print_invoice){
            Intent i=new Intent(this, PrintActivity.class);
            i.putExtra("address",address_one);
            i.putExtra("gst_no",Cust_GST);
            i.putExtra("Customer_Name",cust_name);
            i.putExtra("Driver_Name",drivername);
            i.putExtra("Vehicle_No",vehicle_no);
            i.putExtra("mobile",Driver_Mobile);
            i.putExtra("RequestId",request_id);
            i.putExtra("petroldiesel_type",petroldiesel_type);
            i.putExtra("amount",petroldiesel_qty);
            i.putExtra("item_name",item_name);
            i.putExtra("company_name",company_name);
            i.putExtra("type",type);
            i.putExtra("ordererd_qty",ordererd_qty);
            i.putExtra("order_date",order_date);
            i.putExtra("pin_no",pin_no);
            i.putExtra("pan_no",pan_no);
            i.putExtra("state_code",state_code);
            i.putExtra("statefinal",statefinal);
            i.putExtra("customer_city",customer_city);
            i.putExtra("payment_mode",payment_mode);
            i.putExtra("pending_request",pending_request);
            i.putExtra("activity",activity);
            i.putExtra("invoice_no",invoice_no);

            startActivity(i);
            finish();


        }

    }

    public void calluserValidate_Api(String current_driver_mobile, String code, String key) {
        manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(VerifyMobile.this);
        pb.setMessage("Please Wait....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.verify_otp(manager.getKey("key"),current_driver_mobile,code);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse = response.body();
                String status = requestResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")){
                    Toast.makeText(VerifyMobile.this, "Verified", Toast.LENGTH_SHORT).show();
                    try {
                        CallTransactionApi(manager.getKey("key"),Customer_Code,item_code,itemprice,petrol_or_lube,SlipDetail,petroldiesel_type,petroldiesel_qty,
                                request_id,vehicle_no,Driver_Mobile,Trans_By,cust_type,String.valueOf(total),cust_name,Cust_GST,Cust_mobile,mobile);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(VerifyMobile.this,"Invalid OTP...!!!",R.drawable.dont_sign);

                }

            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(VerifyMobile.this,"Connection Failed...!!!",R.drawable.dont_sign);

            }
        });

    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }  private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            sendOtp_layout.setVisibility(View.GONE);
                            verifyOtp_layout.setVisibility(View.GONE);
                            FirebaseUser user = task.getResult().getUser();
                            // String mobile=user.getPhoneNumber();
                            ////////api call here======//////
                            try {
                                CallTransactionApi(manager.getKey("key"),Customer_Code,item_code,itemprice,petrol_or_lube,SlipDetail,petroldiesel_type,petroldiesel_qty,
                                        request_id,vehicle_no,Driver_Mobile,Trans_By,cust_type,String.valueOf(total),cust_name,Cust_GST,Cust_mobile,mobile);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            pb.dismiss();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                pb.dismiss();
                                CustomDialogWithCurrentActivity.customDialogwithsingleButton(VerifyMobile.this,"Failed...!!!",R.drawable.dont_sign);

                            }
                        }
                    }
                });
    }
    private boolean validatePhoneNumber() {
        mobile = mobile_no_edit.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            mobile_no_edit.setError("Enter phone number.");
            return false;
        }
        return true;
    }   public void CallTransactionApi(String key,String customer_code,String item_code,String item_price,String petrol_or_lube, String slip_detail,String petroldiesel_typ,String petroldiesel_qty
            ,String Request_id,String Vehicle_Reg_No,String Driver_Mobile,String
                                               Trans_By,String cust_type,String total,String cust_name,String cust_gst,String cust_mob,String Trans_Mobile){
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.transaction(key, customer_code, item_code,item_price, petrol_or_lube, slip_detail
                ,petroldiesel_typ,petroldiesel_qty, Request_id, Vehicle_Reg_No, Driver_Mobile, Trans_By,cust_type,total,cust_name,cust_gst,cust_mob,Trans_Mobile);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest=response.body();
                String status=customerRequest.getStatus();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")){
                     invoice_no=customerRequest.getCustomerRequestResponse().getInvoice();

                    main_layout.setVisibility(View.GONE);
                    inoice_layout.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Transaction Sucessfull",Toast.LENGTH_LONG).show();
                    try {
                        Fuel_Lube_List.customerRequestListArrayList.clear();
                        Fuel_Lube_List.fuel_cart.setVisibility(View.GONE);
                        Fuel_Lube_List.new_fuel_request_layout.setVisibility(View.GONE);
                    }catch (Exception e){
                    }
                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(VerifyMobile.this,"Transaction Failed...!!!",R.drawable.dont_sign);

                }

            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(VerifyMobile.this,"Connection Failed...!!!",R.drawable.dont_sign);

            }
        });
    }

}
