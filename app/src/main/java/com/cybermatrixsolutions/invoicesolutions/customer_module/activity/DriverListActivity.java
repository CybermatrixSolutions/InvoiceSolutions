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
import com.cybermatrixsolutions.invoicesolutions.customer_module.adapter.DriverListAdapter;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.DriversList;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface;

import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverListActivity extends AppCompatActivity {

    private FloatingActionButton button_addc;
    private RecyclerView recyclerView;
    private EditText search;
    DriverListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_activity);
        this.findViewById(R.id.back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverListActivity.this.finish();
            }
        });
        search= (EditText) findViewById(R.id.search);
        this.button_addc = (FloatingActionButton) this.findViewById(R.id.button_add);
        this.recyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);

        this.button_addc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DriverListActivity.this,AddDriverActivity.class);
                DriverListActivity.this.startActivity(i);
                DriverListActivity.this.finish();
            }
        });
        try {
            this.CallDriverListApi();
        }catch (Exception e){
            e.printStackTrace();
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filter(charSequence.toString().trim().toLowerCase());
                recyclerView.invalidate();
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void CallDriverListApi() {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.getDriverList(manager.getKey("key"),manager.getCustomer_Code());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                if(status.equals("success")) {
                    List<DriversList> arrayList = loginResponse.getFirsresponse().getDriversLists();
                    if(arrayList!=null) {
                        LinearLayoutManager manager = new LinearLayoutManager(DriverListActivity.this);
                        DriverListActivity.this.recyclerView.setLayoutManager(manager);
                         adapter = new DriverListAdapter(DriverListActivity.this, arrayList);
                        DriverListActivity.this.recyclerView.setAdapter(adapter);
                    }else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(DriverListActivity.this,"No Driver Found..!!!",R.drawable.dont_sign);
                    }
                }else {
                    Toast.makeText(DriverListActivity.this,"Please Login Again",Toast.LENGTH_LONG).show();
                    new PrefsManager(DriverListActivity.this).clearAllData();
                    DriverListActivity.this.startActivity(new Intent(DriverListActivity.this,LoginActivity.class));
                    DriverListActivity.this.finish();
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(DriverListActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.CallDriverListApi();
    }


}
