package com.cybermatrixsolutions.invoicesolutions.activity.shift_sattlement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.activity.DashboardActivity;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.Payment_modeModel;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShiftSattlementOther extends AppCompatActivity implements View.OnClickListener {
PrefsManager manager;
RecyclerView payment_recycle;


List<Payment_modeModel>arraylist;
TextView lubesalesAmount;
String shift_id;
TextView differenceAmount;
Button verify;
    ArrayList<String>modeid;
    ArrayList<String>mode_price;
    LinearLayout linear_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_sattlement_other);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        arraylist=new ArrayList<>();
        manager=new PrefsManager(this);
        payment_recycle=(RecyclerView)findViewById(R.id.payment_recycle);
        lubesalesAmount=(TextView)findViewById(R.id.lubesalesAmount);
        shift_id=getIntent().getStringExtra("shift_id");
        differenceAmount=(TextView)findViewById(R.id.differenceAmount);
        linear_layout=(LinearLayout) findViewById(R.id.linear_layout);
        verify=(Button) findViewById(R.id.verify);
        verify.setOnClickListener(this);
        linear_layout.setVisibility(View.GONE);
        getOtherWalkinApi();

    }
    private void getOtherWalkinApi() {
        final ProgressDialog pb = new ProgressDialog(ShiftSattlementOther.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.settlement_others(manager.getKey("key"),shift_id);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse = response.body();
                String status = requestResponse.getStatus();
                if(status.equals("success")){
                    String credit_other_walkin_amount=requestResponse.getCustomerRequestResponse().getCredit_lube();
                    if(credit_other_walkin_amount!=null){
                        lubesalesAmount.setText(credit_other_walkin_amount);
                        linear_layout.setVisibility(View.VISIBLE);
                        CallPayment_mode();
                    }else {
                        CustomDialog.customDialogwithsingleButton(ShiftSattlementOther.this,"No Settlement Found..!!",R.drawable.dont_sign);
                    }
                }else {
                    Toast.makeText(ShiftSattlementOther.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ShiftSattlementOther.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                pb.dismiss();
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                CustomDialog.customDialogwithsingleButton(ShiftSattlementOther.this,"Connection Failed..!!",R.drawable.dont_sign);

                pb.dismiss();
            }
        });


    }

    private void CallPayment_mode() {
        final ProgressDialog pb = new ProgressDialog(ShiftSattlementOther.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.paymentmode(manager.getKey("key"));
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse = response.body();
                String status = requestResponse.getStatus();

                if(status.equals("success")){
                    arraylist=requestResponse.getCustomerRequestResponse().getPayment_mode();
                    if(arraylist!=null){
                        PaymentAdapter adapter = new PaymentAdapter(getApplicationContext(),arraylist);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        payment_recycle.setLayoutManager(layoutManager);
                        payment_recycle.setAdapter(adapter);

                    }
                    else {
                        CustomDialog.customDialogwithsingleButton(ShiftSattlementOther.this,"Payment Mode Not Found..!!",R.drawable.dont_sign);
                    }
                }else {
                    Toast.makeText(ShiftSattlementOther.this, "Login Failed", Toast.LENGTH_SHORT).show();
                  Intent i=new Intent(ShiftSattlementOther.this, LoginActivity.class);
              startActivity(i);
              finish();

                }
                pb.dismiss();






            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                CustomDialog.customDialogwithsingleButton(ShiftSattlementOther.this,"Connection Failed..!!",R.drawable.dont_sign);
                pb.dismiss();
            }
        });


    }

    @Override
    public void onClick(View v) {

        if(v==verify){

            if (arraylist!=null){
                modeid=new ArrayList<>();
                mode_price=new ArrayList<>();
                for (int i=0;i<arraylist.size();i++){
                    View view=payment_recycle.getChildAt(i);
                    TextView price=(TextView)view.findViewById(R.id.price);

                    if(price.getText().toString().length()>0){
                        mode_price.add(price.getText().toString());
                        modeid.add(arraylist.get(i).getId());
                    }
                }

                String iddd=""+modeid;
                iddd=iddd.replaceAll("\\[|\\]","");

                String mode_pricees=""+mode_price;
                mode_pricees=mode_pricees.replaceAll("\\[|\\]","");


                if(modeid.size()>0){
                    CallShiftsettlementSendApi1(manager.getKey("key"),shift_id,"", "0",mode_pricees,iddd,differenceAmount.getText().toString());
                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(ShiftSattlementOther.this,"Please Fill Data..!!",R.drawable.dont_sign);

                }
            }
            else {
                CustomDialog.customDialogwithsingleButton(ShiftSattlementOther.this,"no Data Found..!!",R.drawable.dont_sign);
            }


        }

    }

    private void CallShiftsettlementSendApi1(String key, String shift_id, String s, String s1, String mode_pricees, String iddd, String s2) {
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.settlement_trans_others(manager.getKey("key"),shift_id,lubesalesAmount.getText().toString(),iddd,mode_pricees,s2);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest=response.body();
                String status=customerRequest.getStatus();
                if(status.equalsIgnoreCase("success")){
                    success_dialog("Settlement Sucessfull done");
                }else {
                    Toast.makeText(ShiftSattlementOther.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ShiftSattlementOther.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(ShiftSattlementOther.this,"Connection Failed..!!",R.drawable.dont_sign);


            }
        });

    }





    public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
        Context context;
        private List<Payment_modeModel> arraylist;

        public PaymentAdapter(Context context, List<Payment_modeModel> arraylist) {
            this.context = context;
            this.arraylist = arraylist;
        }

        @Override
        public PaymentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_layout, parent, false);
            return  new PaymentAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final PaymentAdapter.ViewHolder holder, final int position) {
            final Payment_modeModel modeModel=arraylist.get(position);
            modeModel.setPrice("");
            holder.price.setText(modeModel.getPrice());
            holder.text.setText(modeModel.getRo_code());


            holder.price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String ss=holder.price.getText().toString();
                    if(ss.equals("")){
                        ss="0";
                    }
                    modeModel.setPrice(""+Double.parseDouble(ss));
                    double sum=0;
                    for (int i=0;i<arraylist.size();i++){
                        if(!arraylist.get(i).getPrice().equals("")){
                            sum=sum+(Double.parseDouble(arraylist.get(i).getPrice()));}
                    }
                   if(lubesalesAmount.getText().toString().length()!=0){
                        double total=sum-Double.parseDouble(lubesalesAmount.getText().toString());
                       differenceAmount.setText(""+total);
                   }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView text;
            EditText price;

            public ViewHolder(View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
                price=(EditText)itemView.findViewById(R.id.price);
            }
        }

    }
    public void success_dialog(String status){
        final Dialog ab=new Dialog(ShiftSattlementOther.this);
        ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view= LayoutInflater.from(ShiftSattlementOther.this).inflate(R.layout.popdialogwithsinglebutton,null,false);
        ab.setContentView(R.layout.popdialogwithsinglebutton);
        TextView alertmessage=(TextView)ab.findViewById(R.id.alertmessage);
        ImageView image=(ImageView)ab.findViewById(R.id.image);
        image.setImageResource(R.drawable.successimage);
        TextView clickok=(TextView)ab.findViewById(R.id.clickok);
        alertmessage.setText(status);
        ab.setCancelable(false);
        clickok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ab.dismiss();
                ShiftSattlementOther.this.startActivity(new Intent(ShiftSattlementOther.this, DashboardActivity.class));
                ShiftSattlementOther.this.finish();
            }
        });
        ab.show();
    }

}
