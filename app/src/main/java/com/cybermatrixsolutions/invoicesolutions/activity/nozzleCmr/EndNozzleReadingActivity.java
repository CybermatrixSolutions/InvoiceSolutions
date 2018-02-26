package com.cybermatrixsolutions.invoicesolutions.activity.nozzleCmr;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.activity.DashboardActivity;
import com.cybermatrixsolutions.invoicesolutions.adapter.EndNozzleReadingAdapter;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.NozzlListModel;
import com.cybermatrixsolutions.invoicesolutions.model.NozzleReadingModel;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndNozzleReadingActivity extends AppCompatActivity{
    ApiInterface apiService;
    NozzleReadingModel nozzleReadingModel,nozzleReadingModel1,nozzleReadingModel2,nozzleReadingModel3;
    private EndNozzleReadingAdapter adapter;
    private RecyclerView recyclerView;
    private Button sendEndReading;
    PrefsManager pref; String key,id;
    private List<NozzlListModel> nozzleList ;
    Boolean validate=false;
    List<String> Nozzlename,startReading,idList;

    List<String>testarray,readingarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_nozzle_reading);
        PrefsManager pref=new PrefsManager(getApplicationContext());
        key =  pref.getKey(key);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.noOfNozzleRecyclerView);
        sendEndReading=(Button)findViewById(R.id.sendEndReading);
        pref=new PrefsManager(getApplicationContext());
        startReading=new ArrayList<>();
        idList=new ArrayList<>();
        testarray=new ArrayList<>();
        readingarray=new ArrayList<>();
        key =  pref.getKey(key);
        callNozzleListApi(key);
        sendEndReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validate()) {
                    String nozzleNos = idList.toString().replace("[", "").toString().replace("]", "");
                    String nozzleReading = startReading.toString().replace("[", "").toString().replace("]", "");
                    String string_test = testarray.toString().replace("[", "").toString().replace("]", "");
                    String string_readingarray = readingarray.toString().replace("[", "").toString().replace("]", "");
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int Month = month + 1;
                    String currDate = c.get(Calendar.YEAR) + "-"
                            + Month
                            + "-" + c.get(Calendar.DAY_OF_MONTH);
                    if (validate == true) {
                        CallSendNozzleEndReading(key, nozzleNos, nozzleReading, currDate, "end",string_test,string_readingarray);
                    } else {
                    }
                }
            }
        });
    }
    public boolean Validate(){
        for (int i=0;i<nozzleList.size();i++) {
            View view1 = recyclerView.getChildAt(i);
            String startreading = nozzleList.get(i).getNozzle_Start();
            Double Start = Double.parseDouble(startreading);
            EditText nameEditText = view1.findViewById(R.id.endReading);
            EditText test = view1.findViewById(R.id.test);
            TextView reading=view1.findViewById(R.id.reading);
            String read = nameEditText.getText().toString().trim();
            if (read.isEmpty()) {
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(EndNozzleReadingActivity.this,"Enter CMR..!!",R.drawable.dont_sign);
                return false;
            }
            if (!read.isEmpty()) {
                Double End = Double.parseDouble(read);
                if (Start > End) {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(EndNozzleReadingActivity.this,"CMR can not be less than OMR..!!",R.drawable.dont_sign);
                    return false;
                } else {
                    validate = true;
                    startReading.add(nameEditText.getText().toString().trim());

                    if(test.getText().toString().length()!=0){
                        if(Double.parseDouble(test.getText().toString())>Start){
                            CustomDialogWithCurrentActivity.customDialogwithsingleButton(EndNozzleReadingActivity.this,"Test Can't be greator than OMR..!!",R.drawable.dont_sign);
                            return false;}
                    }
                    if(test.getText().toString().trim().length()>0){
                        testarray.add(test.getText().toString().trim());
                    }else {
                        testarray.add("0");
                    }
                 if(reading.getText().toString().length()>0){
                     readingarray.add(reading.getText().toString().trim());
                 }else {
                     readingarray.add("0");
                 }}
                idList.add(nozzleList.get(i).getNozzle_No());}}
        return true;}
    public void callNozzleListApi(String key) {
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.nozzleReading(key);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest = response.body();
                String status = customerRequest.getStatus();
                pb.dismiss();
                    final StringBuilder commaSepValueBuilder = new StringBuilder();
                     if (status.equalsIgnoreCase("success")) {
                    nozzleList = customerRequest.getCustomerRequestResponse().getNozzle();
                    if(nozzleList!=null) {
                        try {
                            adapter = new EndNozzleReadingAdapter(nozzleList, getApplicationContext());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(layoutManager);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        CustomDialog.customDialogwithsingleButton(EndNozzleReadingActivity.this,"Shift not Found..!!",R.drawable.dont_sign);
                    }
                }else {

                    Toast.makeText(EndNozzleReadingActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(EndNozzleReadingActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                   }

            }@Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(EndNozzleReadingActivity.this,"Connection Failed..!!",R.drawable.dont_sign);

            }
        });}
     public void CallSendNozzleEndReading(String key,String NozzleNos,String NozzleReadings,String ReadingDate, String ReadingType,String test,String read){
    final ProgressDialog pb = new ProgressDialog(this);
    pb.setMessage("Please wait.....");
    pb.show();
    apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<CustomerRequest> call=apiService.sendNozzleEndReading(key, NozzleNos, NozzleReadings, ReadingDate, "end",test,read);
    call.enqueue(new Callback<CustomerRequest>() {
        @Override
        public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
            CustomerRequest customerRequest=response.body();
            String status=customerRequest.getStatus();
            pb.dismiss();
            String msg=customerRequest.getCustomerRequestResponse().getMsg();
            if(status.equalsIgnoreCase("success")){
               success_dialog(msg);
            }else {
                Toast.makeText(EndNozzleReadingActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(EndNozzleReadingActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

        }
        @Override
        public void onFailure(Call<CustomerRequest> call, Throwable t) {
            pb.dismiss();
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(EndNozzleReadingActivity.this,"Connection Failed..!!",R.drawable.dont_sign);

        }
    });}

    public void success_dialog(String status){
        final Dialog ab=new Dialog(EndNozzleReadingActivity.this);
        ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view= LayoutInflater.from(EndNozzleReadingActivity.this).inflate(R.layout.popdialogwithsinglebutton,null,false);
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
                EndNozzleReadingActivity.this.startActivity(new Intent(EndNozzleReadingActivity.this, DashboardActivity.class));
                EndNozzleReadingActivity.this.finish();
            }
        });
        ab.show();
    }
}
