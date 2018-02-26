package com.cybermatrixsolutions.invoicesolutions.activity.walkin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.States;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalkinCustomerInformationActivity extends AppCompatActivity {

    private Button next;
    private EditText customerName,customerNumber,gstNumber,address;
    String CustomerName,CustomerNumber,GstNumber,Customeraddress,Customerstate,Customercity,Customerpan,Customerpin;
    Spinner state;
    ArrayList<String>state_name;
    ArrayList<String>state_code;
    String states_code=null;
    EditText city,pin,pan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkin_customer_information);
        customerName=(EditText)findViewById(R.id.customer_name);
        customerNumber=(EditText)findViewById(R.id.customer_number);
        gstNumber=(EditText)findViewById(R.id.gstNumber);
        address=(EditText)findViewById(R.id.address);
        state=(Spinner)findViewById(R.id.state);
        city=(EditText)findViewById(R.id.city);
        pan=(EditText)findViewById(R.id.pan);
        pin=(EditText)findViewById(R.id.pin);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next=(Button)findViewById(R.id.addLube);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Validation();
            }
        });
        next.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        customerName.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        customerNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        address.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
        city.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(15)});
        pan.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        pin.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(6)});


        state_name=new ArrayList<>();
        state_code=new ArrayList<>();
        getStateList();
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40)});
                   if(state_code.size()>0){
                  states_code=state_code.get(position);
              }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getStateList() {
        final ProgressDialog pb = new ProgressDialog(WalkinCustomerInformationActivity.this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.getStatelist("");
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest = response.body();
                String status = customerRequest.getStatus();
                pb.dismiss();
                if(status.equals("success")){
                    List<States>statesList=customerRequest.getCustomerRequestResponse().getGetState();

                    if(statesList!=null){
                        for (int i=0;i<statesList.size();i++){
                            state_name.add(statesList.get(i).getName());
                            state_code.add(statesList.get(i).getGstcode());
                        }
                        state_name.add(0,"Select State");
                        state_code.add(0,"000");
                        ArrayAdapter adapter=new ArrayAdapter(WalkinCustomerInformationActivity.this,R.layout.spinneritem,state_name);
                        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        state.setAdapter(adapter);
                    }else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Failed..!!!!",R.drawable.dont_sign);
                    }
                }else {
                    CustomDialog.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Failed..!!!!",R.drawable.dont_sign);
                }

            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Failed..!!!!",R.drawable.dont_sign);

            }

        });



    }

    public boolean Validation(){
        CustomerName=customerName.getText().toString().trim();
        CustomerNumber=customerNumber.getText().toString().trim();
        GstNumber=gstNumber.getText().toString().trim();
        Customeraddress=address.getText().toString().trim();
        Customerstate=state.getSelectedItem().toString().trim();
        Customercity=city.getText().toString().trim();
        Customerpan=pan.getText().toString().trim();
        Customerpin=pin.getText().toString().trim();

        city=(EditText)findViewById(R.id.city);
        pan=(EditText)findViewById(R.id.pan);
        pin=(EditText)findViewById(R.id.pin);

        String GST_PATTERN="^([0][1-9]|[1-2][0-9]|[3][0-5])([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$";
        if(CustomerName.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Please Enter Customer Name..!!!!",R.drawable.dont_sign);
            return false;
        }  if(Customeraddress.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Please Enter Customer Address..!!!!",R.drawable.dont_sign);
            return false;
     }
    if(CustomerNumber.isEmpty()){
        CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Please Enter Customer Mobile Number..!!!!",R.drawable.dont_sign);
        return false;
    }
    if(CustomerNumber.length()!=10){
        CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Please Enter Customer 10 digit Mobile Number..!!!!",R.drawable.dont_sign);
        return false;
    }
        if(Customerstate.equals("Select State")){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Please Enter Valid GST Number..!!!!",R.drawable.dont_sign);
            return false;
        }

     if(!GstNumber.isEmpty()) {
         if (!GstNumber.matches(GST_PATTERN)) {
             CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Please Enter Valid GST Number..!!!!",R.drawable.dont_sign);
             return false;
         }
     }if(Customercity.isEmpty()) {
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Please Enter City..!!!!",R.drawable.dont_sign);
            return false;
        }if(Customerpin.length()!=6) {
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Please Enter Valid pincode..!!!!",R.drawable.dont_sign);
            return false;
        }if(Customerpan.isEmpty()) {
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(WalkinCustomerInformationActivity.this,"Please Enter pan no..!!!!",R.drawable.dont_sign);
            return false;
        }
         Intent intent = new Intent(getApplicationContext(), BarCodeOptionActivity.class);
         intent.putExtra("customer_number", CustomerNumber);
         intent.putExtra("customer_name", CustomerName);
         intent.putExtra("gstNumber", GstNumber);
         intent.putExtra("cust_type", "walkin");
         intent.putExtra("Customer_Code", "walkin");
         intent.putExtra("Vehicle_No", "walkin");
         intent.putExtra("Customeraddress", Customeraddress);
         intent.putExtra("Customerstate", Customerstate);
         intent.putExtra("states_code", states_code);
         intent.putExtra("customer_city", Customercity);
         intent.putExtra("pin_no", Customerpin);
         intent.putExtra("pan_no", Customerpan);
         intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         startActivity(intent);
         finish();
         return true;
     }

}
