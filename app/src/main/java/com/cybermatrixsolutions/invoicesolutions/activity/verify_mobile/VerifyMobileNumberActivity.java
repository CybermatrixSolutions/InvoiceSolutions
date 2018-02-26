package com.cybermatrixsolutions.invoicesolutions.activity.verify_mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.printing_activity.PrintActivity;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
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

import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyMobileNumberActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private EditText et_mobile,et_otp;
    private Button sendOtp,verifyOtp,generatInvoiceButton,printBillButton,resendotp;
    private LinearLayout sendOtpLayout,verifyOtpLayout,invoiceLoayout;
    private String Driver_Mobile,mobile,mVerificationId,KEY,OilType,petroldiesel_qty,petroldiesel_type,RoCode,cust_type,Trans_By,
            Customer_Code,Vehicle_No,RequestId,Petrol_or_lube,itemcode,itemprice,cust_name,Cust_GST,Cust_mobile,SlipDetail;
    double total;
    PrefsManager prefsManager;
    String company_name,address_one,address_two,address_three,gst_no,Customer_Name,Driver_Name,spinnerfueltype,fuel_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile_number);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Driver_Mobile=getIntent().getStringExtra("mobile");
        Cust_mobile=getIntent().getStringExtra("Cust_mobile");
        OilType=getIntent().getStringExtra("OilType");
        petroldiesel_qty=getIntent().getStringExtra("petroldiesel_qty");
        Customer_Code=getIntent().getStringExtra("Customer_Code");
        RoCode=getIntent().getStringExtra("Ro_code");
        total=getIntent().getDoubleExtra("total",0.0);
        Vehicle_No=getIntent().getStringExtra("Vehicle_No");
        petroldiesel_type=getIntent().getStringExtra("petroldiesel_type");
        RequestId=getIntent().getStringExtra("RequestId");
        Petrol_or_lube=getIntent().getStringExtra("petrol_or_lube");
        itemcode=getIntent().getStringExtra("itemcode");
        itemprice=getIntent().getStringExtra("itemprice");
        Trans_By=getIntent().getStringExtra("Trans_By");
        cust_type=getIntent().getStringExtra("cust_type");
        cust_name=getIntent().getStringExtra("cust_name");
        Cust_GST=getIntent().getStringExtra("Cust_GST");
        prefsManager=new PrefsManager(this);
        KEY=prefsManager.getKey("key");

        double  n =new Date().getDate()+new Date().getTime();
        SlipDetail="GRD"+String.valueOf(n);
        FirebaseApp.initializeApp(this);
        et_mobile=(EditText)findViewById(R.id.et_mobile);
        et_otp=(EditText)findViewById(R.id.et_otp);

        if(Driver_Mobile!=null&&!Driver_Mobile.isEmpty()) {
            et_mobile.setText(Driver_Mobile);
        }
        else if(Cust_mobile!=null){
            et_mobile.setText(Cust_mobile);
        }
        else {
            et_mobile.setEnabled(true);
            et_mobile.setText("");
        }

        try {
            company_name=getIntent().getStringExtra("company_name");
            address_one=getIntent().getStringExtra("address_one");
            address_two=getIntent().getStringExtra("address_two");
            address_three=getIntent().getStringExtra("address_three");
            gst_no=getIntent().getStringExtra("gst_no");
            Customer_Name=getIntent().getStringExtra("Customer_Name");
            Driver_Name=getIntent().getStringExtra("Driver_Name");
            Vehicle_No=getIntent().getStringExtra("Vehicle_No");
            Driver_Mobile=getIntent().getStringExtra("mobile");
            RequestId=getIntent().getStringExtra("RequestId");
            spinnerfueltype=getIntent().getStringExtra("spinnerfueltype");
            fuel_amount=getIntent().getStringExtra("fuel_amount");
            petroldiesel_type=getIntent().getStringExtra("petroldiesel_type");
        }catch (Exception e){

        }


        sendOtp=(Button)findViewById(R.id.sendotp);
        verifyOtp=(Button)findViewById(R.id.verifyOtp);
        resendotp=(Button)findViewById(R.id.resendotp);
        generatInvoiceButton=(Button)findViewById(R.id.generatInvoiceButton);
        printBillButton=(Button)findViewById(R.id.printBillButton);
        sendOtpLayout=(LinearLayout)findViewById(R.id.sendOtp_layout);
        verifyOtpLayout=(LinearLayout)findViewById(R.id.verifyOtpLayout);
        invoiceLoayout=(LinearLayout)findViewById(R.id.invoiceLoayout);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validatePhoneNumber()){
                    resendVerificationCode(mobile,mResendToken);
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
                //  Log.w(TAG, "onVerificationFailed", e);
                resendotp.setVisibility(View.VISIBLE);
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
                sendOtpLayout.setVisibility(View.GONE);
                verifyOtpLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"OTP has been sent to your Mobile Number "+mobile,Toast.LENGTH_LONG).show();

            }
        };

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validatePhoneNumber()) {
                    startPhoneNumberVerification("+91"+mobile);

                }



            }
        });
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = et_otp.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    et_otp.setError("Cannot be empty.");
                    return;
                }else {
                    verifyPhoneNumberWithCode(mVerificationId, code);

                }

            }
        });

        generatInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        printBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VerifyMobileNumberActivity.this,PrintActivity.class);
                intent.putExtra("company_name", company_name);
                intent.putExtra("address_one", address_one);
                intent.putExtra("address_two", address_two);
                intent.putExtra("address_three", address_three);
                intent.putExtra("gst_no", gst_no);
                intent.putExtra("Customer_Name", Customer_Name);
                intent.putExtra("Driver_Name",Driver_Name);
                intent.putExtra("spinnerfueltype",spinnerfueltype);
                intent.putExtra("Vehicle_No",Vehicle_No);
                intent.putExtra("mobile",Driver_Mobile);
                intent.putExtra("RequestId",RequestId);
                intent.putExtra("amount",petroldiesel_qty);
                intent.putExtra("petroldiesel_type",petroldiesel_type);
                startActivity(intent);

            }
        });

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private boolean validatePhoneNumber() {
        mobile = et_mobile.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            et_mobile.setError("Enter phone number.");
            return false;
        }
        return true;
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

                            sendOtpLayout.setVisibility(View.GONE);
                            verifyOtpLayout.setVisibility(View.GONE);
                            FirebaseUser user = task.getResult().getUser();
                           // String mobile=user.getPhoneNumber();
                            ////////api call here======//////
                            try {
                                CallTransactionApi(KEY,Customer_Code,itemcode,itemprice,Petrol_or_lube,SlipDetail,petroldiesel_type,petroldiesel_qty,
                                        RequestId,Vehicle_No,Driver_Mobile,Trans_By,cust_type,String.valueOf(total),cust_name,Cust_GST,Cust_mobile,mobile);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            pb.dismiss();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                pb.dismiss();
                                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });
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
    }
    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    public void CallTransactionApi(String key,String customer_code,String item_code,String item_price,String petrol_or_lube, String slip_detail,String petroldiesel_typ,String petroldiesel_qty
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
                    invoiceLoayout.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Transaction Sucessfull",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Transaction Failed",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

            }
        });
    }
}
