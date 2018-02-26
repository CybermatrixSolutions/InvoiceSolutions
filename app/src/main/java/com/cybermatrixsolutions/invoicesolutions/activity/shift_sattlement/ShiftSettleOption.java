package com.cybermatrixsolutions.invoicesolutions.activity.shift_sattlement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.ShiftDetailModel;
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

public class ShiftSettleOption extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
PrefsManager manager;
ArrayList<String>arrayListshift;
ArrayList<String>arrayListid;
    TextView sales_text;
LinearLayout optionlayout,spinner_layout;
TextView fuel,other,submit;
  String  shift_id;
  ArrayList<String>selectlubenameList;
    ArrayList<String>selectlubeIdList;
    String type=null;
    LinearLayout sales_linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_settle_option);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sales_text=(TextView)findViewById(R.id.sales_text);
        optionlayout=(LinearLayout)findViewById(R.id.optionlayout);
        spinner_layout=(LinearLayout)findViewById(R.id.spinner_layout);
        sales_linear=(LinearLayout)findViewById(R.id.sales_linear);
        fuel=(TextView) findViewById(R.id.fuel);
        other=(TextView) findViewById(R.id.other);
        submit=(TextView) findViewById(R.id.submit);
        fuel.setOnClickListener(this);
        other.setOnClickListener(this);
        sales_text.setOnClickListener(this);
        submit.setOnClickListener(this);
        sales_linear.setOnClickListener(this);


    }

    private void getLuberequest(String type) {
        manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(ShiftSettleOption.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.shiftdetail(manager.getKey("key"),type);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse = response.body();
                String status = requestResponse.getStatus();
                arrayListshift=new ArrayList<>();
                arrayListid=new ArrayList<>();
                pb.dismiss();
                if (status.equalsIgnoreCase("success")) {
                    List<ShiftDetailModel>list=requestResponse.getCustomerRequestResponse().getShift_Detail();
                    if(list!=null){
                        for(int i=0;i<list.size();i++){
                            String date=list.get(i).getDate();
                            String id=list.get(i).getId();
                            String start=list.get(i).getStarttime();
                            String end=list.get(i).getEndtime();
                            arrayListshift.add(date+" ("+start+" )"+" To "+end);
                            arrayListid.add(id);
                        }
                        dialog();
                    }else {
                        CustomDialog.customDialogwithsingleButton(ShiftSettleOption.this,"No Settlement Request Found..!!",R.drawable.dont_sign);
                        optionlayout.setVisibility(View.VISIBLE);
                        spinner_layout.setVisibility(View.GONE);
                    }
                }else {
                    Toast.makeText(ShiftSettleOption.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ShiftSettleOption.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(ShiftSettleOption.this,"Connection Failed..!!",R.drawable.dont_sign);


            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v==fuel){
            spinner_layout.setVisibility(View.VISIBLE);
            optionlayout.setVisibility(View.GONE);
            type="Fuel";

        } if(v==other){
            spinner_layout.setVisibility(View.VISIBLE);
            optionlayout.setVisibility(View.GONE);
            type="other";
        }if(v==sales_text){

            if(type.equals("Fuel")){
                getLuberequest("fuel");
            }else {
                getLuberequest("lube");
            }

        }if(v==sales_linear){

            if(type.equals("Fuel")){
                getLuberequest("fuel");
            }else {
                getLuberequest("lube");
            }

        }


        if(v==submit){
            if(sales_text.getText().toString().equals("Select Shift Time"))
            {
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(ShiftSettleOption.this,"Please Select Shift..!!",R.drawable.dont_sign);

            }else {

                if(type.equals("Fuel")){
                    String textViewtext=""+ ShiftSettleOption.this.selectlubeIdList;
                    textViewtext=textViewtext.replaceAll("\\[|\\]","");
                    Intent i=new Intent(this,ShiftSettlementActivity.class);
                    i.putExtra("shift_id",textViewtext);
                    startActivity(i);
                    finish();
                }else {
                    String textViewtext=""+ ShiftSettleOption.this.selectlubeIdList;
                    textViewtext=textViewtext.replaceAll("\\[|\\]","");
                    Intent i=new Intent(this,ShiftSattlementOther.class);
                    i.putExtra("shift_id",textViewtext);
                    startActivity(i);
                    finish();
                }



            }





        }

    }

    private void dialog() {
        selectlubenameList=new ArrayList<>();
        selectlubeIdList=new ArrayList<>();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,R.layout.simple_list_item_multiple_choice, this.arrayListshift);
        ListView lv = new ListView(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adp);

        AlertDialog.Builder bldr = new AlertDialog.Builder(this);
        bldr.setTitle("Select Item");
        bldr.setView(lv);
        bldr.setPositiveButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String textViewtext=""+ ShiftSettleOption.this.selectlubenameList;
                        textViewtext=textViewtext.replaceAll("\\[|\\]","");
                        ShiftSettleOption.this.sales_text.setText(textViewtext);
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
        CheckedTextView text1= view.findViewById(android.R.id.text1);
        if(text1.isChecked()){
            selectlubenameList=new ArrayList<>();
            selectlubeIdList=new ArrayList<>();
            text1.setChecked(true);
            this.selectlubenameList.add(this.arrayListshift.get(i));
            this.selectlubeIdList.add(this.arrayListid.get(i));
        }else {
            text1.setChecked(false);
            for (int ip = 0; ip< this.arrayListid.size(); ip++){
                for (int j = 0; j< this.selectlubeIdList.size(); j++) {
                    if (this.selectlubeIdList.get(j).equals(this.arrayListid.get(i))) {
                        this.selectlubenameList.remove(j);
                        this.selectlubeIdList.remove(j);
                    }
                }
            }
        }
    }

    }

