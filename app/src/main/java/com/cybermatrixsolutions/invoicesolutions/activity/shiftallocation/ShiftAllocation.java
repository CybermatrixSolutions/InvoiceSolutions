package com.cybermatrixsolutions.invoicesolutions.activity.shiftallocation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.activity.DashboardActivity;


import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.PedestalModel;
import com.cybermatrixsolutions.invoicesolutions.model.SalesModel;
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

public class ShiftAllocation extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {


    PrefsManager manager;
    TextView pedestal_texview,sales_text;
    LinearLayout pedestal_linear,sales_linear;
    ArrayList<String>pedestral_number;
    ArrayList<String>pedestral_id;
    ArrayList<String>selectpedestral_number;
    ArrayList<String>selectpedestral_id;
    ArrayList<String>assignpedestral_number;
    ArrayList<String>assignpedestral_id;
    ArrayList<String>selectPersonnel_Name;
    ArrayList<String>selectPersonnel__id;
    ArrayList<String>assignPersonnel_Name;
    ArrayList<String>assignPersonnel__id;
    ArrayList<String>Personnel_Name;
    ArrayList<String>Personnel__id;
    int integer=0;
    TextView submit;
    String pednumber="",sales_name="";
    String pedid="",salid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_allocation);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pedestral_number=new ArrayList<>();
        pedestral_id=new ArrayList<>();
        Personnel_Name=new ArrayList<>();
        Personnel__id=new ArrayList<>();
        selectpedestral_number=new ArrayList<>();
        selectpedestral_id=new ArrayList<>();
        assignpedestral_number=new ArrayList<>();
        assignpedestral_id=new ArrayList<>();
        selectPersonnel_Name=new ArrayList<>();
        selectPersonnel__id=new ArrayList<>();
        assignPersonnel_Name=new ArrayList<>();
        assignPersonnel__id=new ArrayList<>();

        pedestal_texview=(TextView)findViewById(R.id.pedestal_texview);
        sales_text=(TextView)findViewById(R.id.sales_text);
        pedestal_linear=(LinearLayout) findViewById(R.id.pedestal_linear);
        sales_linear=(LinearLayout) findViewById(R.id.sales_linear);
        pedestal_linear.setOnClickListener(this);
        sales_linear.setOnClickListener(this);
        getLuberequest();

        submit=(TextView)findViewById(R.id.submit);
        submit.setOnClickListener(this);

    }


    private void getLuberequest() {
        manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(ShiftAllocation.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.getshiftallocation(manager.getKey("key"));
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse = response.body();
                String status = requestResponse.getStatus();
                pb.dismiss();
                if (status.equalsIgnoreCase("success")) {
                    List<SalesModel>salesModels=requestResponse.getCustomerRequestResponse().getSales();
                    if(salesModels!=null){
                        for(int i=0;i<salesModels.size();i++){
                            Personnel_Name.add(salesModels.get(i).getPersonnel_Name());
                            Personnel__id.add(salesModels.get(i).getId());
                        }
                    }

                    List<PedestalModel>pedestalModels=requestResponse.getCustomerRequestResponse().getPedestal();
                    if(pedestalModels!=null){
                        for(int i=0;i<pedestalModels.size();i++){
                            pedestral_number.add(pedestalModels.get(i).getPedestal_Number());
                            pedestral_id.add(pedestalModels.get(i).getId());
                        }
                    }

                    if(salesModels==null&&pedestalModels==null){
                        CustomDialog.customDialogwithsingleButton(ShiftAllocation.this,"Previous Shift not closed..!!",R.drawable.dont_sign);

                    }
                }else {
                    CustomDialog.customDialogwithsingleButton(ShiftAllocation.this, requestResponse.getCustomerRequestResponse().getMsg(),R.drawable.dont_sign);
                }
            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(ShiftAllocation.this, "Connection Failed...!!!!",R.drawable.dont_sign);

            }
        });
    }
    @Override
    public void onClick(View v) {

        if(v==pedestal_linear){
            integer=0;
            pedestrialDialog();
        }if(v==sales_linear){
            integer=1;
            SalesDialog();

        }
        if(v==submit){
            pednumber=pedestal_texview.getText().toString();
            sales_name=sales_text.getText().toString();
            if(pednumber.length()!=0){
            callApi();
            }else {
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(ShiftAllocation.this, "Select Both Items...!!!!",R.drawable.dont_sign);
            }
        }


    }


    private void callApi() {
        pedid =""+selectpedestral_id;
        pedid = pedid.replaceAll("\\[|\\]", "");
        salid=""+selectPersonnel__id;
        salid = salid.replaceAll("\\[|\\]", "");
        manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(ShiftAllocation.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.shift_trans(manager.getKey("key"),pedid,salid);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse = response.body();
                String status = requestResponse.getStatus();
                String msg = requestResponse.getCustomerRequestResponse().getMsg();
                pb.dismiss();
                if (status.equalsIgnoreCase("success")) {
                  success_dialog(msg);
                }else {
                    Toast.makeText(ShiftAllocation.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ShiftAllocation.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(ShiftAllocation.this, "Network Error...!!!!",R.drawable.dont_sign);

            }
        });
    }

    private void SalesDialog() {
        this.assignPersonnel_Name =new ArrayList<>();
        this.assignPersonnel__id =new ArrayList<>();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,R.layout.simple_list_item_multiple_choice, this.Personnel_Name);
        ListView lv = new ListView(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adp);
        if(this.selectPersonnel__id.size()!=0){
            for (int i = 0; i< this.Personnel_Name.size(); i++){
                for (int j = 0; j< this.selectPersonnel__id.size(); j++){
                    if(this.selectPersonnel__id.get(j).equals(this.Personnel__id.get(i)))
                    {lv.setItemChecked(i,true);
                        this.assignPersonnel_Name.add(this.Personnel_Name.get(i));
                        this.assignPersonnel__id.add(this.Personnel__id.get(i));
                    }}}
            this.selectPersonnel_Name =new ArrayList<>();
            this.selectPersonnel__id =new ArrayList<>();
            for (int k = 0; k< this.assignPersonnel_Name.size(); k++){
                this.selectPersonnel_Name.add(this.assignPersonnel_Name.get(k));
                this.selectPersonnel__id.add(this.assignPersonnel__id.get(k));
            }
        }
        AlertDialog.Builder bldr = new AlertDialog.Builder(this);
        bldr.setTitle("Select Item");
        bldr.setView(lv);
        bldr.setPositiveButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Toast.makeText(AddLubeRequestActivity.this, ""+selectlubenameList, Toast.LENGTH_SHORT).show();
                        String textViewtext=""+ ShiftAllocation.this.selectPersonnel_Name;
                        textViewtext=textViewtext.replaceAll("\\[|\\]","");
                        ShiftAllocation.this.sales_text.setText(textViewtext);
                    }
                });
        bldr.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }});
        Dialog dlg = bldr.create();
        dlg.show();


    }



    private void pedestrialDialog() {
        this.assignpedestral_number =new ArrayList<>();
        this.assignpedestral_id =new ArrayList<>();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,R.layout.simple_list_item_multiple_choice, this.pedestral_number);
        ListView lv = new ListView(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adp);
        if(this.selectpedestral_id.size()!=0){
            for (int i = 0; i< this.pedestral_number.size(); i++){
                for (int j = 0; j< this.selectpedestral_id.size(); j++){
                    if(this.selectpedestral_id.get(j).equals(this.pedestral_id.get(i)))
                    {lv.setItemChecked(i,true);
                        this.assignpedestral_number.add(this.pedestral_number.get(i));
                        this.assignpedestral_id.add(this.pedestral_id.get(i));
                    }}}
            this.selectpedestral_number =new ArrayList<>();
            this.selectpedestral_id =new ArrayList<>();
            for (int k = 0; k< this.assignpedestral_number.size(); k++){
                this.selectpedestral_number.add(this.assignpedestral_number.get(k));
                this.selectpedestral_id.add(this.assignpedestral_id.get(k));
            }
        }
        AlertDialog.Builder bldr = new AlertDialog.Builder(this);
        bldr.setTitle("Select Item");
        bldr.setView(lv);
        bldr.setPositiveButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Toast.makeText(AddLubeRequestActivity.this, ""+selectlubenameList, Toast.LENGTH_SHORT).show();
                        String textViewtext=""+ ShiftAllocation.this.selectpedestral_number;
                        textViewtext=textViewtext.replaceAll("\\[|\\]","");
                        ShiftAllocation.this.pedestal_texview.setText(textViewtext);
                    }
                });
        bldr.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }});
        Dialog dlg = bldr.create();
        dlg.show();
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(integer!=1){
            CheckedTextView text1= view.findViewById(android.R.id.text1);
            if(text1.isChecked()){
                this.selectpedestral_number.add(this.pedestral_number.get(i));
                this.selectpedestral_id.add(this.pedestral_id.get(i));
            }else {
                for (int ip = 0; ip< this.pedestral_id.size(); ip++){
                    for (int j = 0; j< this.selectpedestral_id.size(); j++) {
                        if (this.selectpedestral_id.get(j).equals(this.pedestral_id.get(i))) {
                            this.selectpedestral_number.remove(j);
                            this.selectpedestral_id.remove(j);
                        }
                    }
                }
            }
        }
        else {
            CheckedTextView text1= view.findViewById(android.R.id.text1);
            if(text1.isChecked()){
                this.selectPersonnel_Name.add(this.Personnel_Name.get(i));
                this.selectPersonnel__id.add(this.Personnel__id.get(i));
            }else {
                for (int ip = 0; ip< this.Personnel__id.size(); ip++){
                    for (int j = 0; j< this.selectPersonnel__id.size(); j++) {
                        if (this.selectPersonnel__id.get(j).equals(this.Personnel__id.get(i))) {
                            this.selectPersonnel_Name.remove(j);
                            this.selectPersonnel__id.remove(j);
                        }
                    }
                }
            }


        }
        }

    public void success_dialog(String status){
        final Dialog ab=new Dialog(ShiftAllocation.this);
        ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view= LayoutInflater.from(ShiftAllocation.this).inflate(R.layout.popdialogwithsinglebutton,null,false);
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
                ShiftAllocation.this.startActivity(new Intent(ShiftAllocation.this, DashboardActivity.class));
                ShiftAllocation.this.finish();
            }
        });
        ab.show();
    }


    }
