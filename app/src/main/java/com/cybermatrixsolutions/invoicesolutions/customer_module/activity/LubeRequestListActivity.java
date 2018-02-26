package com.cybermatrixsolutions.invoicesolutions.customer_module.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.customer_module.adapter.LubeListAdapter;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.FuelRequest;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.FuelRequestModel;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface;

import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LubeRequestListActivity extends AppCompatActivity {

    RecyclerView viewfueltyperecyle;
    FloatingActionButton button_addc;
    LubeListAdapter adapter;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_lube_request);
        this.findViewById(R.id.back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LubeRequestListActivity.this.finish();
            }
        });
        search= (EditText) findViewById(R.id.search);
        this.button_addc = (FloatingActionButton) this.findViewById(R.id.button_add);
        this.viewfueltyperecyle = (RecyclerView) this.findViewById(R.id.viewfueltyperecyle);
        this.button_addc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LubeRequestListActivity.this,AddLubeRequestActivity.class);
                LubeRequestListActivity.this.startActivity(i);
                LubeRequestListActivity.this.finish();
            }
        });
        this.server();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filter(charSequence.toString().trim().toLowerCase());
                viewfueltyperecyle.invalidate();
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void server() {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<FuelRequest> call=apiService.viewLubeRequest(manager.getKey("key"),manager.getCustomer_Code());
        call.enqueue(new Callback<FuelRequest>() {
            @Override
            public void onResponse(Call<FuelRequest> call, Response<FuelRequest> response) {
                FuelRequest loginResponse=response.body();
                String status=loginResponse.getStatus();
                if(status.equals("success")) {
                    ArrayList<FuelRequestModel> arrayList = loginResponse.getFirsresponse().getLubeRequestModels();
                    if(arrayList!=null){
                        LinearLayoutManager manager = new LinearLayoutManager(LubeRequestListActivity.this);
                        LubeRequestListActivity.this.viewfueltyperecyle.setLayoutManager(manager);
                        adapter = new LubeListAdapter(LubeRequestListActivity.this, arrayList);
                        LubeRequestListActivity.this.viewfueltyperecyle.setAdapter(adapter);
                    }else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(LubeRequestListActivity.this,"Lube Request not Found..!!!",R.drawable.dont_sign);
                    }


                }
                else {
                    Toast.makeText(LubeRequestListActivity.this.getApplicationContext(),"Please Login Again",Toast.LENGTH_LONG).show();
                    new PrefsManager(LubeRequestListActivity.this).clearAllData();
                    LubeRequestListActivity.this.startActivity(new Intent(LubeRequestListActivity.this.getApplicationContext(),LoginActivity.class));
                    LubeRequestListActivity.this.finish();
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<FuelRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(LubeRequestListActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }
        });

    }


}
