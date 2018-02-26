package com.cybermatrixsolutions.invoicesolutions.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.With_QR.Fuel_Lube_List;
import com.cybermatrixsolutions.invoicesolutions.activity.WithoutQR.Transaction;
import com.cybermatrixsolutions.invoicesolutions.activity.WithoutQR.WithoutQrCodeActivityForOil;
import com.cybermatrixsolutions.invoicesolutions.activity.verify_mobile.VerifyMobile;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequestList;
import com.cybermatrixsolutions.invoicesolutions.activity.WithoutQR.PetrolCustomerDetailWithoutQrCodeActivity;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diwakar on 1/10/2018.
 */

public class Lube_Oil_RequestWithout_Qr_Adapter extends RecyclerView.Adapter<Lube_Oil_RequestWithout_Qr_Adapter.ViewHolder> {
    Context context;

    public Lube_Oil_RequestWithout_Qr_Adapter(Context context, List<CustomerRequestList> qrScanResultList) {
        this.context = context;
        this.qrScanResultList = qrScanResultList;

    }
    String item_price;
    List<CustomerRequestList> fuelTypeLists;
    private List<CustomerRequestList> qrScanResultList;

    ArrayList<String> fuel_type_price;
    ArrayList<String> fuel_type;
String type=null;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.qrlube_oil_request_layout, parent, false);
        return new Lube_Oil_RequestWithout_Qr_Adapter.ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.fuel.setText(qrScanResultList.get(position).getPetrol_Diesel_Type());
        String requestDate = qrScanResultList.get(position).getRequest_date();
        final String requestType = qrScanResultList.get(position).getRequestType();
        PrefsManager manager = new PrefsManager(context);
        holder.date.setText(qrScanResultList.get(position).getRequest_date());
        holder.lube.setText(requestType);

        final ProgressDialog pb = new ProgressDialog(context);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call = apiService.fueltypeList(manager.getKey("key"));
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest customerRequest = response.body();
                pb.dismiss();
                fuel_type_price = new ArrayList<>();
                fuel_type = new ArrayList<>();
                String status = customerRequest.getStatus();
                if (status.equalsIgnoreCase("success")) {
                    fuelTypeLists = customerRequest.getCustomerRequestResponse().getFuelTypeList();
                    if (fuelTypeLists != null) {
                        fuel_type = new ArrayList<String>();
                        for (int i = 0; i < fuelTypeLists.size(); i++) {
                            String fuel = fuelTypeLists.get(i).getPetrol_Diesel_Type();
                            fuel_type.add(fuel);
                            fuel_type_price.add(fuelTypeLists.get(i).getPrice());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, fuel_type);
                        holder.spinner.setAdapter(adapter);
                        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                 item_price=fuel_type_price.get(position);
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    } else {
                        CustomDialog.customDialogwithsingleButton(context,"Fuel List Not Found...!!!",R.drawable.dont_sign);


                    }
                }

            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(context,"Connection Failed...!!!",R.drawable.dont_sign);

            }
        });

        holder.price.setText(qrScanResultList.get(position).getPrice());
        if (position == 1) {
            holder.spinnerlayout.setVisibility(View.GONE);
        } else {
            holder.spinnerlayout.setVisibility(View.VISIBLE);
        }

        if(!qrScanResultList.get(position).getRequestType().equals("Full Tank")){
            holder.qty.setText(qrScanResultList.get(position).getRequest_Value());
            holder.ordqty.setText(qrScanResultList.get(position).getRequest_Value()+" "+qrScanResultList.get(position).getRequestType());
            type="";
        }else {
            holder.ordqty.setText("Full Tank");
            type="Ltr";

        }
        double n = new Date().getDate() + new Date().getTime();
        final String SlipDetail = "GRD" + String.valueOf(n);
        final PrefsManager prefsManager = new PrefsManager(context);
        holder.preceed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Transaction.class);
                i.putExtra("Driver_Mobile", PetrolCustomerDetailWithoutQrCodeActivity.Driver_Mobile);
                i.putExtra("item_code", PetrolCustomerDetailWithoutQrCodeActivity.id);
                i.putExtra("Customer_Code", PetrolCustomerDetailWithoutQrCodeActivity.Customer_Code);
                i.putExtra("itemprice", item_price);
                i.putExtra("petrol_or_lube", "1");
                i.putExtra("SlipDetail", SlipDetail);
                i.putExtra("petroldiesel_qty", holder.qty.getText().toString());
                i.putExtra("request_id", PetrolCustomerDetailWithoutQrCodeActivity.request_id);
                i.putExtra("vehicle_no", PetrolCustomerDetailWithoutQrCodeActivity.Registration_Number);
                i.putExtra("Trans_By", prefsManager.getUserDetails().get("id"));
                i.putExtra("cust_type", "credit");
                i.putExtra("total", "0.0");
                i.putExtra("item_name", holder.spinner.getSelectedItem().toString());
                i.putExtra("cust_name", PetrolCustomerDetailWithoutQrCodeActivity.Customer_Name);
                i.putExtra("Cust_GST", PetrolCustomerDetailWithoutQrCodeActivity.gst_no);
                i.putExtra("Cust_mobile", PetrolCustomerDetailWithoutQrCodeActivity.Phone_Number);
                if( PetrolCustomerDetailWithoutQrCodeActivity.address_three==null){
                    PetrolCustomerDetailWithoutQrCodeActivity.address_three="";
                } if( PetrolCustomerDetailWithoutQrCodeActivity.address_one==null){
                    PetrolCustomerDetailWithoutQrCodeActivity.address_one="";
                }if( PetrolCustomerDetailWithoutQrCodeActivity.address_two==null){
                    PetrolCustomerDetailWithoutQrCodeActivity.address_two="";
                }
                i.putExtra("address", PetrolCustomerDetailWithoutQrCodeActivity.address_one+" "+PetrolCustomerDetailWithoutQrCodeActivity.address_two+" "+PetrolCustomerDetailWithoutQrCodeActivity.address_three);
                i.putExtra("address_two", PetrolCustomerDetailWithoutQrCodeActivity.address_two);
                i.putExtra("address_three", PetrolCustomerDetailWithoutQrCodeActivity.address_three);
                i.putExtra("drivername", PetrolCustomerDetailWithoutQrCodeActivity.Driver_Name);
                i.putExtra("company_name", PetrolCustomerDetailWithoutQrCodeActivity.company_name);
                i.putExtra("petroldiesel_type", qrScanResultList.get(position).getRequestType());
                i.putExtra("current_driver_mobile", PetrolCustomerDetailWithoutQrCodeActivity.current_driver_mobile);
                i.putExtra("type",type);
                i.putExtra("ordererd_qty",holder.ordqty.getText().toString());
                i.putExtra("order_date",holder.date.getText().toString());
                i.putExtra("pin_no", PetrolCustomerDetailWithoutQrCodeActivity.pin_no);
                i.putExtra("pan_no",PetrolCustomerDetailWithoutQrCodeActivity.pan_no);
                i.putExtra("state_code",PetrolCustomerDetailWithoutQrCodeActivity.state_code);
                i.putExtra("statefinal",PetrolCustomerDetailWithoutQrCodeActivity.statefinal);
                i.putExtra("customer_city",PetrolCustomerDetailWithoutQrCodeActivity.cityfinal);
                i.putExtra("petroldiesel_type",qrScanResultList.get(position).getRequestType());
                i.putExtra("fuel","1");
                i.putExtra("payment_mode","Credit");

                if(PetrolCustomerDetailWithoutQrCodeActivity.lubelistarray.size()>0){
                    i.putExtra("pending_request","1");}
                else{
                    i.putExtra("pending_request","0");}
                i.putExtra("activity","Fuel_Lube_List");







                context.startActivity(i);

            }
        });


    }
    @Override
    public int getItemCount() {
        return qrScanResultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fuel;
        TextView lube,date,price,preceed_button,ordqty;
        Spinner spinner;
        LinearLayout spinnerlayout;
        EditText qty;

        public ViewHolder(View itemView) {
            super(itemView);
            fuel = itemView.findViewById(R.id.fuel);
            lube = itemView.findViewById(R.id.lube);
            date = itemView.findViewById(R.id.request_date);
            spinner = itemView.findViewById(R.id.spinner);
            price= itemView.findViewById(R.id.price);
            spinnerlayout=itemView.findViewById(R.id.spinnerlayout);
            preceed_button = itemView.findViewById(R.id.preceed_button);
            qty=(EditText)itemView.findViewById(R.id.qty);
            ordqty = itemView.findViewById(R.id.ordqty);
        }
    }
}
