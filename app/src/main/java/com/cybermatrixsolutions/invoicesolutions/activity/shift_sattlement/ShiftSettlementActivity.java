package com.cybermatrixsolutions.invoicesolutions.activity.shift_sattlement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.activity.DashboardActivity;
import com.cybermatrixsolutions.invoicesolutions.adapter.LubeSalesSettlementAdapter;
import com.cybermatrixsolutions.invoicesolutions.adapter.PaymentAdapter;
import com.cybermatrixsolutions.invoicesolutions.adapter.PetrolDieselSettlementAdapter;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.NozzlList;
import com.cybermatrixsolutions.invoicesolutions.model.Payment_modeModel;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShiftSettlementActivity extends AppCompatActivity implements View.OnClickListener {
   public static TextView oilAmount,lubeAmount,differenceAmount;
    private RecyclerView oilSettlementRecyclerView;
    PetrolDieselSettlementAdapter adapter;
    private Button verify;

    double sum=0.0;
  public  static   double total=0;
    double totalsale=0.0;
    List<NozzlList> nozzlLists;
    double TotalPrice,TotalPrice1, TotalAmount;
    LubeSalesSettlementAdapter lubeAdapter;
    LinearLayout mainLayout;

    String KEY,Credit_lube,Credit_Petrol;
    private PrefsManager prefsManager;
    private String PetroCardAmount="",CreditCardAmount="",WalletAmount="",CashAmount="",DifferenceAmount="";
    RecyclerView payment_recycle;
    String shift_id;
    TextView total_amount;
    List<Payment_modeModel>arraylist;

    ArrayList<String>modeid;
    ArrayList<String>mode_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_settlement);
        oilAmount = (TextView)findViewById(R.id.oilSalesAmount);
        lubeAmount = (TextView)findViewById(R.id.lubesalesAmount);
        payment_recycle = (RecyclerView) findViewById(R.id.payment_recycle);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mainLayout=(LinearLayout)findViewById(R.id.main);
        prefsManager=new PrefsManager(this);
        KEY=prefsManager.getKey("key");
        differenceAmount = (TextView)findViewById(R.id.differenceAmount);
        Calendar c = Calendar.getInstance();
        int month=c.get(Calendar.MONTH);
        int Month=month+1;
        String sDate = c.get(Calendar.YEAR) + "-"
                + Month
                + "-" + c.get(Calendar.DAY_OF_MONTH);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        oilSettlementRecyclerView =(RecyclerView)findViewById(R.id.oilSettlement) ;
        verify=(Button)findViewById(R.id.verify);
        ///api calling////
        shift_id=getIntent().getStringExtra("shift_id");
        verify.setOnClickListener(this);
        total_amount=(TextView)findViewById(R.id.total_amount);
        CallSettlementApi(KEY, sDate,shift_id);

    }
    public void CallSettlementApi(String key,String currDate,String shift_id){
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.settlement(key, currDate,shift_id);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest = response.body();
                String status = customerRequest.getStatus();
                pb.dismiss();
                try {
                    if (status.equalsIgnoreCase("success")) {
                        Credit_lube = customerRequest.getCustomerRequestResponse().getCredit_lube();
                        Credit_Petrol = customerRequest.getCustomerRequestResponse().getCredit_Petrol();
                        if (Credit_lube != null) {
                            TotalPrice = Double.parseDouble(Credit_lube);
                        }
                        if (Credit_Petrol != null) {
                            TotalPrice1 = Double.parseDouble(Credit_Petrol);
                        }
                        if (Credit_Petrol != null) {
                            oilAmount.setText("Rs. " + Credit_Petrol);
                        } else {
                            oilAmount.setText("");
                        }
                        if (Credit_lube != null) {
                            lubeAmount.setText("Rs. " + Credit_lube);
                        } else {
                            lubeAmount.setText("");
                        }
                        nozzlLists = customerRequest.getCustomerRequestResponse().getNozzlLists();
                        if (nozzlLists != null) {
                            mainLayout.setVisibility(View.VISIBLE);
                            double sum=0;
                            adapter = new PetrolDieselSettlementAdapter(nozzlLists, getApplicationContext());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            oilSettlementRecyclerView.setLayoutManager(layoutManager);
                            oilSettlementRecyclerView.setAdapter(adapter);
                            oilSettlementRecyclerView.setNestedScrollingEnabled(true);
                            for (int i = 0; i < nozzlLists.size(); i++) {
                                String NozzleStart = nozzlLists.get(i).getNozzle_Start();
                                String NozzleEnd = nozzlLists.get(i).getNozzle_End();
                                String sales = nozzlLists.get(i).getPrice();
                                if (NozzleStart != null || NozzleEnd != null || sales != null) {
                                    double startreading = Double.parseDouble(NozzleStart);
                                    double endReading = Double.parseDouble(NozzleEnd);
                                    double difference = endReading - startreading;
                                    double Amount = Double.parseDouble(sales);
                                    double Total = difference * Amount;
                                    sum=sum+Total;
                                    totalsale = totalsale + Total;
                                    total = TotalPrice + (totalsale - TotalPrice1);
                                }
                                DecimalFormat df= new DecimalFormat("###############00");
                                total_amount.setText(df.format(sum));
                            }
                            CallPayment_mode();
                        }
                        else {
                            CustomDialog.customDialogwithsingleButton(ShiftSettlementActivity.this,"Nozzle Settlement Shift Not Found..!!",R.drawable.dont_sign);
                        }



                    } else {
                        CustomDialog.customDialogwithsingleButton(ShiftSettlementActivity.this,status,R.drawable.dont_sign);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(ShiftSettlementActivity.this,"Connection Failed...!!!",R.drawable.dont_sign);

            }
        });
    }

    private void CallPayment_mode() {
        final ProgressDialog pb = new ProgressDialog(ShiftSettlementActivity.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.paymentmode(KEY);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse = response.body();
                String status = requestResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")){
                    arraylist=requestResponse.getCustomerRequestResponse().getPayment_mode();
                    if(arraylist!=null){
                        PaymentAdapter adapter = new PaymentAdapter(getApplicationContext(),arraylist);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        payment_recycle.setLayoutManager(layoutManager);
                        payment_recycle.setAdapter(adapter);

                    }
                    else {
                        CustomDialog.customDialogwithsingleButton(ShiftSettlementActivity.this,"Payment Mode Not Found..!!",R.drawable.dont_sign);
                    }
                }else {
                    Toast.makeText(ShiftSettlementActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ShiftSettlementActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(ShiftSettlementActivity.this,"Connection Failed...!!!",R.drawable.dont_sign);

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
                    CallShiftsettlementSendApi1(prefsManager.getKey("key"),shift_id,total_amount.getText().toString(), "0",mode_pricees,iddd,differenceAmount.getText().toString());

                }else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(ShiftSettlementActivity.this,"Please Fill Data...!!!",R.drawable.dont_sign);
                }


            }else {
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(ShiftSettlementActivity.this,"No Data Found...!!!",R.drawable.dont_sign);

            }

        }
    }

    private void CallShiftsettlementSendApi1(String key, String shift_id, String total, String s1, String mode_pricees, String iddd, String diff) {
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.settlementdone(key, shift_id, total, "0", iddd, mode_pricees,
                diff);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest=response.body();
                String status=customerRequest.getStatus();
                if(status.equalsIgnoreCase("success")){
                    success_dialog("Settlement Sucessfull done");
                }else {
                    Toast.makeText(ShiftSettlementActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ShiftSettlementActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(ShiftSettlementActivity.this,"Connection Failed...!!!",R.drawable.dont_sign);


            }
        });

    }  public void success_dialog(String status){
        final Dialog ab=new Dialog(ShiftSettlementActivity.this);
        ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view= LayoutInflater.from(ShiftSettlementActivity.this).inflate(R.layout.popdialogwithsinglebutton,null,false);
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
                ShiftSettlementActivity.this.startActivity(new Intent(ShiftSettlementActivity.this, DashboardActivity.class));
                ShiftSettlementActivity.this.finish();
            }
        });
        ab.show();
    }
}
