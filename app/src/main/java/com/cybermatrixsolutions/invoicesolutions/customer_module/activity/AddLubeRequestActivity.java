package com.cybermatrixsolutions.invoicesolutions.customer_module.activity;

import android.R.id;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.customer_module.adapter.Lube_ItemAdd_Adapter;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.DriversList;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LoginResponse;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.LubeType;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.customer_module.rest.ApiInterface;

import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialog;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLubeRequestActivity extends AppCompatActivity implements OnItemClickListener,View.OnClickListener {

    List<String> Vehicle_reg_no;
    private AutoCompleteTextView select_item;
    ArrayAdapter fueladapter;
    String Item,VehicleNumber,dmobile;
    ArrayList<String>selectlubenameList;
    ArrayList<String>selectlubeIdList;
    ArrayList<String>lubeNameArrayList;
    ArrayList<String>lubeIdarrayList;
    ArrayList<String>assignlubeNameList;
    ArrayList<String>assignlubeIdList;
    Spinner vehicle_reg_no;
    EditText driver_mobile;
    ArrayList<String>arrayList;
    ArrayList<String>arrayListqty;
    ArrayList<String>arrayListid;

    ListView list_item;
    EditText qty;
   public static Button submit;
    boolean click=false;
    String qtys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_lube_request);
        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLubeRequestActivity.this.finish();
            }
        });
        this.select_item = (AutoCompleteTextView) this.findViewById(R.id.select_item);
        this.vehicle_reg_no = (Spinner) this.findViewById(R.id.vehicle_reg_no);
        driver_mobile= (EditText) this.findViewById(R.id.driver_mobile);
        list_item= (ListView) this.findViewById(R.id.list_item);
        qty= (EditText) this.findViewById(R.id.qty);
        this.submit= (Button) this.findViewById(R.id.submit);
        driver_mobile.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        this.submit.setOnClickListener(this);
        this.selectlubenameList =new ArrayList<>();
        this.lubeNameArrayList =new ArrayList<>();
        this.lubeIdarrayList =new ArrayList<>();
        arrayListqty =new ArrayList<>();
        arrayListid=new ArrayList<>();
        submit.setVisibility(View.GONE);
        this.selectlubeIdList =new ArrayList<>();
        this.select_item.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                click=true;
            }
        });
        this.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validation()){
                    if(arrayList.size()>0){
                        if(validate_duplcate_entry()){
                            arrayList.add(select_item.getText().toString());
                            arrayListqty.add(qty.getText().toString());
                            select_item.setText("");
                            qty.setText("");
                            Lube_ItemAdd_Adapter add_adapter=new Lube_ItemAdd_Adapter(arrayList,AddLubeRequestActivity.this,arrayListqty);
                            list_item.setAdapter(add_adapter);
                            add_adapter.notifyDataSetChanged();
                            click=false;
                            submit.setVisibility(View.VISIBLE);
                        }
                    }else {
                        arrayList.add(select_item.getText().toString());
                        arrayListqty.add(qty.getText().toString());
                        select_item.setText("");
                        qty.setText("");
                        Lube_ItemAdd_Adapter add_adapter=new Lube_ItemAdd_Adapter(arrayList,AddLubeRequestActivity.this,arrayListqty);
                        list_item.setAdapter(add_adapter);
                        add_adapter.notifyDataSetChanged();
                        click=false;
                        submit.setVisibility(View.VISIBLE);
                    }



                }
            }


        });
        this.server();
        this.getLuberequest();
        arrayList=new ArrayList<>();
    }
    private boolean validate_duplcate_entry() {
        for(int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).equals(select_item.getText().toString())){
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddLubeRequestActivity.this,"Item already selected..!!",R.drawable.dont_sign);
              return  false;
            }
        }
        return true;
    }
    private boolean Validation() {
        if(select_item.getText().toString().length()==0||select_item.getText().toString().equals("")){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddLubeRequestActivity.this,"Please Search Items",R.drawable.dont_sign);
            return false;

        }if(qty.getText().toString().length()==0||qty.getText().toString().equals("")){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddLubeRequestActivity.this,"Please Enter  Quantity",R.drawable.dont_sign);
            return false;

        }if(click!=true){

            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddLubeRequestActivity.this,"Please Select Only Search Items",R.drawable.dont_sign);
            return false;

        }
        return  true;
    }

    private void server() {
        PrefsManager manager = new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.getVehicleList(manager.getKey("key"),manager.getCustomer_Code());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                String status = loginResponse.getStatus();
                pb.dismiss();
                if (status.equals("success")) {
                    final List<DriversList> arrayList = loginResponse.getFirsresponse().getVehicleList();
                    if(arrayList!=null){
                        AddLubeRequestActivity.this.Vehicle_reg_no = new ArrayList<>();
                        for (int i = 0; i < arrayList.size(); i++) {
                        String Number = arrayList.get(i).getRegistration_Number();
                        Vehicle_reg_no.add(Number);
                        AddLubeRequestActivity.this.fueladapter = new ArrayAdapter(AddLubeRequestActivity.this, R.layout.spinner_layout, AddLubeRequestActivity.this.Vehicle_reg_no);
                        AddLubeRequestActivity.this.vehicle_reg_no.setAdapter(AddLubeRequestActivity.this.fueladapter);
                        AddLubeRequestActivity.this.vehicle_reg_no.setOnItemSelectedListener(new OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                AddLubeRequestActivity.this.VehicleNumber = arrayList.get(i).getRegistration_Number();
                                getCustomer_mobile_api(AddLubeRequestActivity.this.VehicleNumber );
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });}
                    }else {
                        CustomDialog.customDialogwithsingleButton(AddLubeRequestActivity.this,"Vehicle Not Found",R.drawable.dont_sign);
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(AddLubeRequestActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }
        });

    }

    private void getCustomer_mobile_api(String vehicleNumber) {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please Wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.getDriver(manager.getKey("key"),vehicleNumber);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")) {
                    if(loginResponse.getFirsresponse().getDriver()!=null){
                        driver_mobile.setText(loginResponse.getFirsresponse().getDriver());
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddLubeRequestActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }});

    }
    private void getLuberequest() {
        PrefsManager manager=new PrefsManager(this);
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.getlubelist(manager.getKey("key"));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                pb.dismiss();
                if(status.equals("success")) {
                    ArrayList<LubeType> arrayList2 = loginResponse.getFirsresponse().getLubeTypes();
                    if(arrayList2!=null){
                        for (int i=0;i<arrayList2.size();i++){
                            AddLubeRequestActivity.this.lubeNameArrayList.add(arrayList2.get(i).getName());
                            AddLubeRequestActivity.this.lubeIdarrayList.add(arrayList2.get(i).getCode());
                        }
                        ArrayAdapter<String> adp = new ArrayAdapter<String>(AddLubeRequestActivity.this,android.R.layout.simple_list_item_1, AddLubeRequestActivity.this.lubeNameArrayList);
                        select_item.setAdapter(adp);
                    }else {
                        CustomDialog.customDialogwithsingleButton(AddLubeRequestActivity.this,"Product Not Found..!!!",R.drawable.dont_sign);
                    }

                }}
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialog.customDialogwithsingleButton(AddLubeRequestActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }});
    }
    @Override
    public void onClick(View view) {
        if(view== this.submit){
            arrayListid=new ArrayList<>();
            if(Validate()){
                PrefsManager manager=new PrefsManager(this);
                this.CallLubeRequestApi(new PrefsManager(this).getKey("key"), this.Item, this.VehicleNumber,"REQUEST",manager.getCustomer_Code(),qtys);

            }
        }
    }
    private boolean Validate() {
        for(int i=0;i<lubeNameArrayList.size();i++){
            String name=lubeNameArrayList.get(i);
            for(int j=0;j<arrayList.size();j++){
                if(arrayList.get(j).equals(name)){
                    arrayListid.add(lubeIdarrayList.get(i));
                }}}
        this.Item = this.arrayListid.toString().replaceAll("\\[|\\]","");
        this.VehicleNumber = this.vehicle_reg_no.getSelectedItem().toString().trim();
        this.qtys = this.arrayListqty.toString().replaceAll("\\[|\\]","");
        this.dmobile = this.driver_mobile.getText().toString().trim();
        if(this.Item.isEmpty()){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddLubeRequestActivity.this,"Please Select Item..!!!",R.drawable.dont_sign);
            return false;
        }  if(this.dmobile.length()!=10){
            CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddLubeRequestActivity.this,"Enter Valid 10 Digit Mobile No..!!!",R.drawable.dont_sign);
            return false;
        }
        return true;
    }
    private void CallLubeRequestApi(String key,String item_id,String vehicle_no,String rewuest_id,String code,String qtyss) {
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.addLubeRequest(key,item_id,vehicle_no,rewuest_id,dmobile,code,qtyss);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                String msg=loginResponse.getFirsresponse().getMsg();
                pb.dismiss();
                if(status.equals("success")) {
                    success_dialog(msg);
                } else  if(msg.contains("request already")){

                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddLubeRequestActivity.this,"One request already pending for this vehicle..!!!",R.drawable.dont_sign);
                }

                else {
                        Toast.makeText(AddLubeRequestActivity.this,"Please Login Again",Toast.LENGTH_LONG).show();
                        new PrefsManager(AddLubeRequestActivity.this).clearAllData();
                        AddLubeRequestActivity.this.startActivity(new Intent(AddLubeRequestActivity.this,LoginActivity.class));
                        AddLubeRequestActivity.this.finish();

                    }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(AddLubeRequestActivity.this,"Connection Failed..!!!",R.drawable.dont_sign);

            }
        });

    }
    private void dialog() {
        this.assignlubeNameList =new ArrayList<>();
        this.assignlubeIdList =new ArrayList<>();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,R.layout.simple_list_item_multiple_choice, this.lubeNameArrayList);
        ListView lv = new ListView(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adp);
        if(this.selectlubeIdList.size()!=0){
            for (int i = 0; i< this.lubeIdarrayList.size(); i++){
                for (int j = 0; j< this.selectlubeIdList.size(); j++){
                    if(this.selectlubeIdList.get(j).equals(this.lubeIdarrayList.get(i)))
                    {lv.setItemChecked(i,true);
                        this.assignlubeNameList.add(this.lubeNameArrayList.get(i));
                        this.assignlubeIdList.add(this.lubeIdarrayList.get(i));
                    }}}
            this.selectlubenameList =new ArrayList<>();
            this.selectlubeIdList =new ArrayList<>();
            for (int k = 0; k< this.assignlubeNameList.size(); k++){
                this.selectlubenameList.add(this.assignlubeNameList.get(k));
                this.selectlubeIdList.add(this.assignlubeIdList.get(k));
            }
        }
        Builder bldr = new Builder(this);
        bldr.setTitle("Select Item");
        bldr.setView(lv);
        bldr.setPositiveButton("Done",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  Toast.makeText(AddLubeRequestActivity.this, ""+selectlubenameList, Toast.LENGTH_SHORT).show();
                        String textViewtext=""+ AddLubeRequestActivity.this.selectlubenameList;
                        textViewtext=textViewtext.replaceAll("\\[|\\]","");
                        AddLubeRequestActivity.this.select_item.setText(textViewtext);
                    }
                });
        bldr.setNegativeButton("Cancel",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }});
        Dialog dlg = bldr.create();
        dlg.show();
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CheckedTextView text1= view.findViewById(id.text1);
        if(text1.isChecked()){
            this.selectlubenameList.add(this.lubeNameArrayList.get(i));
            this.selectlubeIdList.add(this.lubeIdarrayList.get(i));
        }else {
            for (int ip = 0; ip< this.lubeIdarrayList.size(); ip++){
                for (int j = 0; j< this.selectlubeIdList.size(); j++) {
                    if (this.selectlubeIdList.get(j).equals(this.lubeIdarrayList.get(i))) {
                        this.selectlubenameList.remove(j);
                        this.selectlubeIdList.remove(j);
                    }
                }
            }
        }
    }

    public void success_dialog(String status){
        final Dialog ab=new Dialog(AddLubeRequestActivity.this);
        ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view= LayoutInflater.from(AddLubeRequestActivity.this).inflate(R.layout.popdialogwithsinglebutton,null,false);
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
                AddLubeRequestActivity.this.startActivity(new Intent(AddLubeRequestActivity.this,LubeRequestListActivity.class));
                AddLubeRequestActivity.this.finish();
            }
        });
        ab.show();

    }
}
