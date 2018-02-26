package com.cybermatrixsolutions.invoicesolutions.customer_module.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.customer_module.adapter.FuelListAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FuelRequestListActivity extends Activity {

    public FuelRequestListActivity() {
        // Required empty public constructor
    }
    RecyclerView viewfueltyperecyle;
    FloatingActionButton button_addc;
    FuelListAdapter adapter;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_view_fuel_request);
        this.findViewById(R.id.back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FuelRequestListActivity.this.finish();
            }
        });
        search=findViewById(R.id.search);
        this.viewfueltyperecyle = this.findViewById(R.id.viewfueltyperecyle);
        this.button_addc = this.findViewById(R.id.button_add);

        this.button_addc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FuelRequestListActivity.this,AddFuelRequestActivity.class);
                FuelRequestListActivity.this.startActivity(i);
                FuelRequestListActivity.this.finish();
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
        Call<FuelRequest> call=apiService.viewFuelRequest(manager.getKey("key"),manager.getCustomer_Code());
        Log.e("Key--",manager.getKey("key"));
        call.enqueue(new Callback<FuelRequest>() {
            @Override
            public void onResponse(Call<FuelRequest> call, Response<FuelRequest> response) {
                FuelRequest loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")) {
                    ArrayList<FuelRequestModel> arrayList = loginResponse.getFirsresponse().getFuelRequestModels();
                    if(arrayList!=null){
                        LinearLayoutManager manager = new LinearLayoutManager(FuelRequestListActivity.this);
                        FuelRequestListActivity.this.viewfueltyperecyle.setLayoutManager(manager);
                        adapter = new FuelListAdapter(FuelRequestListActivity.this, arrayList);
                        FuelRequestListActivity.this.viewfueltyperecyle.setAdapter(adapter);
                    }else {
                        CustomDialogWithCurrentActivity.customDialogwithsingleButton(FuelRequestListActivity.this,"No Request Found..!!!",R.drawable.dont_sign);
                    }

                }else {
                    Toast.makeText(FuelRequestListActivity.this,"Please Login Again",Toast.LENGTH_LONG).show();
                    new PrefsManager(FuelRequestListActivity.this).clearAllData();
                    FuelRequestListActivity.this.startActivity(new Intent(FuelRequestListActivity.this,LoginActivity.class));
                    FuelRequestListActivity.this.finish();
                }

            }

            @Override
            public void onFailure(Call<FuelRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(FuelRequestListActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }
        });

    }


    }



