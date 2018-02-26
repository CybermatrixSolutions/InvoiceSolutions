package com.cybermatrixsolutions.invoicesolutions.activity.printing_activity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.activity.DashboardActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.Formatter;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;
import com.cybermatrixsolutions.invoicesolutions.utils.PulsatorLayout;
import com.cybermatrixsolutions.invoicesolutions.utils.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Set;

public class PrintActivity extends AppCompatActivity implements View.OnClickListener {

PrefsManager prefsManager;
String petrol_pump_name,pump_address,address_2,address_3,city,state,gstcode,pin_code,GST_TIN,VAT_TIN;



String address3,state_with_pin,credit_delivery_advise,advise_no,invoice_prefix;
int month,year,day;
String yearsplit;

    String company_name,address_one,gst_no,Customer_Name,Driver_Name,spinnerfueltype,fuel_amount,Vehicle_No,Driver_Mobile,RequestId;

    String customer_address,customer_gst_no,petroldiesel_type;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothSocket mmSocket;
    BluetoothAdapter bluetoothAdapter;
    PulsatorLayout mPulsator;
    ImageView locationmarker;
    TextView text1,textView2,textView,textView3;
    LinearLayout linea1,linea2,linea3,linea4,linea5,linea6;
    ArrayList<String> arrayList;
    ArrayList<String>arrayList1;
    String item_name;
    String ordererd_qty,type,order_date,pin_no,pan_no,state_code,statefinal;
    String ordered_qty=null;
    String deliverd=null;
    String line=null;
    String gst=null;
    String pan=null;
    StringBuilder driver_datails=null;
    String request_id_with_date=null;
    String product_gheading=null;
    StringBuilder stringBuilder4=null;
    String ptrol_pump_bootom_detail=null;
    String pump=null;
    StringBuilder stringBuilder5=null;
    String  line1;
    BluetoothDevice device;
    String payment_mode=null;
    String customer_city=null;
    String mode,terms,pending_request,activity;
    String invoice_no;
    BluetoothDevice bdDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        prefsManager = new PrefsManager(this);
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        yearsplit=""+year;
        month=month+1;
        yearsplit=yearsplit.substring(2,4);
        petrol_pump_name=prefsManager.getUserDetails().get("pump_legal_name");
        pump_address=prefsManager.getUserDetails().get("pump_address");
        address_2=prefsManager.getUserDetails().get("address_2");
        address_3=prefsManager.getUserDetails().get("address_3");
        city=prefsManager.getUserDetails().get("city");
        state=prefsManager.getUserDetails().get("state");
        gstcode=prefsManager.getUserDetails().get("gstcode");
        pin_code=prefsManager.getUserDetails().get("pin_code");
        GST_TIN=prefsManager.getUserDetails().get("GST_TIN");
        VAT_TIN=prefsManager.getUserDetails().get("VAT_TIN");
        invoice_prefix=prefsManager.getUserDetails().get("invoice_prefix");

        petrol_pump_name=petrol_pump_name+"\n";
        pump_address=pump_address+"\n";
        state_with_pin=city+","+state+"-"+pin_code+"\n";
        GST_TIN="GST/UIN : "+GST_TIN+"\n";
        VAT_TIN="VATT TIN No : "+VAT_TIN+"\n";
        state="State Name : "+state+", "+"Code : "+gstcode+"\n";
        credit_delivery_advise="Credit Delivery Advice"+"\n";
        invoice_no=getIntent().getStringExtra("invoice_no");
        if (invoice_no==null){
            invoice_no="";
        }
        advise_no = "Invoice No.: "+invoice_no+"      "+"Dt:"+day+"/"+month+"/"+year+"\n";
        company_name=getIntent().getStringExtra("company_name");
        if (company_name==null){
            company_name="";
        }
        address_one=getIntent().getStringExtra("address");
        if (address_one==null){
            address_one="";
        }


        gst_no=getIntent().getStringExtra("gst_no");
        if (gst_no==null){
            gst_no="";
        }
        Customer_Name=getIntent().getStringExtra("Customer_Name");
        if (Customer_Name==null){
            Customer_Name="";
        }
        Driver_Name=getIntent().getStringExtra("Driver_Name");
        if (Driver_Name==null){
            Driver_Name="";
        }
        petroldiesel_type=getIntent().getStringExtra("petroldiesel_type");
        if (petroldiesel_type==null){
            petroldiesel_type="";
        }
        Vehicle_No=getIntent().getStringExtra("Vehicle_No");
        if (Vehicle_No==null){
            Vehicle_No="";
        }
        Driver_Mobile=getIntent().getStringExtra("mobile");
        if (Driver_Mobile==null){
            Driver_Mobile="";
        }
        RequestId=getIntent().getStringExtra("RequestId");
        if (RequestId==null){
            RequestId="";
        }
        fuel_amount=getIntent().getStringExtra("amount");
        if (fuel_amount==null){
            fuel_amount="";
        }
        item_name=getIntent().getStringExtra("item_name");
        if (item_name==null){
            item_name="";
        }
        ordererd_qty= getIntent().getStringExtra("ordererd_qty");
        if (ordererd_qty==null){
            ordererd_qty="";
        }
        type= getIntent().getStringExtra("type");
        if (type==null){
            type="";
        }
        order_date= getIntent().getStringExtra("order_date");
        if (order_date==null){
            order_date="";
        }
        pin_no= getIntent().getStringExtra("pin_no");
        if (pin_no==null){
            pin_no="";
        }
        pan_no= getIntent().getStringExtra("pan_no");
        if (pan_no==null){
            pan_no="";
        }
        state_code= getIntent().getStringExtra("state_code");
        if (state_code==null){
            state_code="";
        }
        statefinal= getIntent().getStringExtra("statefinal");
        if (statefinal==null){
            statefinal="";
        }
        payment_mode= getIntent().getStringExtra("payment_mode");
        if (payment_mode==null){
            payment_mode="";
        }
        customer_city= getIntent().getStringExtra("customer_city");
        if (customer_city==null){
            customer_city="";
        }
        pending_request= getIntent().getStringExtra("pending_request");
        activity= getIntent().getStringExtra("activity");
        mPulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        locationmarker=(ImageView)findViewById(R.id.locationmarker);
        text1= (TextView)findViewById(R.id.text1);
        textView2= (TextView)findViewById(R.id.textView2);
        textView3= (TextView)findViewById(R.id.textView3);
        textView= (TextView)findViewById(R.id.textView);
        customer_address=address_one+"\n"+customer_city+","+statefinal+"-"+pin_no+".  "+"State Code :"+state_code+"\n";
        company_name=company_name+"\n";
        CheckBlueToothState();
        locationmarker.setOnClickListener(btnScanDeviceOnClickListener);
        registerReceiver(ActionFoundReceiver,new IntentFilter(BluetoothDevice.ACTION_FOUND));
        intialize();
    }
    private void intialize() {
        linea1=(LinearLayout)findViewById(R.id.linea1);
        linea2=(LinearLayout)findViewById(R.id.linea2);
        linea3=(LinearLayout)findViewById(R.id.linea3);
        linea4=(LinearLayout)findViewById(R.id.linea4);
        linea5=(LinearLayout)findViewById(R.id.linea5);
        linea6=(LinearLayout)findViewById(R.id.linea6);
        linea1.setVisibility(View.GONE);
        linea2.setVisibility(View.GONE);
        linea3.setVisibility(View.GONE);
        linea4.setVisibility(View.GONE);
        linea5.setVisibility(View.GONE);
        linea6.setVisibility(View.GONE);
        linea1.setOnClickListener(this);
        linea2.setOnClickListener(this);
        linea3.setOnClickListener(this);
        linea4.setOnClickListener(this);
        linea5.setOnClickListener(this);
        linea6.setOnClickListener(this);
    }
    public boolean writeWithFormat(byte[] buffer, final byte[] pFormat, final byte[] pAlignment) {
        try {
            OutputStream os = mmSocket.getOutputStream();
            os.write(pAlignment);
            os.write(pFormat);
            os.write(buffer, 0, buffer.length);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(ActionFoundReceiver);
    }private void CheckBlueToothState(){
    if (bluetoothAdapter == null){
        Toast.makeText(this, "Your devices does not support Bluetooth", Toast.LENGTH_SHORT).show();
    }else{
        if (bluetoothAdapter.isEnabled()){
            if(bluetoothAdapter.isDiscovering()){
                Toast.makeText(this, "Searching Devices...", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Bluetooth Enabled...", Toast.LENGTH_SHORT).show();
            }}else{
            Toast.makeText(this, "Bluetooth is not Enabled...", Toast.LENGTH_SHORT).show();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }}}private Button.OnClickListener btnScanDeviceOnClickListener
        = new Button.OnClickListener(){
    @Override
    public void onClick(View arg0) {

        boolean result= Utility.checkPermission(PrintActivity.this);
        if(result){

            bluetoothAdapter.startDiscovery();
            mPulsator.start();
            arrayList=new ArrayList();
            arrayList1=new ArrayList();
        }

    }};
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ENABLE_BT){
            CheckBlueToothState();
        }
    }
        private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){
            @Override
         public void onReceive(Context context, Intent intent) {
             String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
             device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
             arrayList.add(device.getAddress());
             arrayList1.add(device.getName());
            checkcondition(arrayList);
        }}
    };
    private void checkcondition(ArrayList<String> arrayList) {
        if(arrayList.size()==1){
            linea1.setVisibility(View.VISIBLE);
            text1.setText(arrayList1.get(0));
        }else if(arrayList.size()==2){
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            text1.setText(arrayList1.get(0));
            textView2.setText(arrayList1.get(1));
        }else if(arrayList.size()==3){
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.VISIBLE);
            text1.setText(arrayList1.get(0));
            textView2.setText(arrayList1.get(1));
            textView3.setText(arrayList1.get(2));
        }else if(arrayList.size()==4){
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.VISIBLE);
            linea4.setVisibility(View.VISIBLE);
            text1.setText(arrayList1.get(0));
            textView2.setText(arrayList1.get(1));
            textView3.setText(arrayList1.get(2));
            textView.setText(arrayList1.get(3));
        } else if(arrayList.size()==5){
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.VISIBLE);
            linea4.setVisibility(View.VISIBLE);
            linea5.setVisibility(View.VISIBLE);
        } else if(arrayList.size()==6){
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.VISIBLE);
            linea4.setVisibility(View.VISIBLE);
            linea5.setVisibility(View.VISIBLE);
            linea6.setVisibility(View.VISIBLE);
        }}
    @Override
    public void onClick(View v) {
        if(v==linea1){
            printBillToDevice(arrayList.get(0));
        } if(v==linea2){
            printBillToDevice(arrayList.get(1));
        }if(v==linea3){
            printBillToDevice(arrayList.get(2));
        }if(v==linea4){
            printBillToDevice(arrayList.get(3));
        }if(v==linea5){
            printBillToDevice(arrayList.get(4));
        }if(v==linea6){
            printBillToDevice(arrayList.get(5));
        }
    }
    private void printBillToDevice(final String address) {
        printTop_info();
        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                    }
                });
                bluetoothAdapter.cancelDiscovery();
                try {
                    System.out
                            .println("**************************#****connecting");
                    BluetoothDevice mdevice = bluetoothAdapter
                            .getRemoteDevice(address);
                    Method m = mdevice.getClass().getMethod(
                            "createRfcommSocket", new Class[] { int.class });
                    mmSocket = (BluetoothSocket) m.invoke(mdevice, 1);
                    mmSocket.connect();
                    printdetails();
                    if (bluetoothAdapter != null)
                        bluetoothAdapter.cancelDiscovery();
                    mmSocket.close();
                    setResult(RESULT_OK);
                } catch (Exception e) {
                    Log.e("Class ", "My Exe ", e);
                    e.printStackTrace();
                    setResult(RESULT_CANCELED);
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {


                        } catch (Exception e) {
                            Log.e("Class ", "My Exe ", e);

                            Toast.makeText(PrintActivity.this, "Failed To Print", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();



    }

    private void printdetails() {
        petrol_pump_name="\n"+petrol_pump_name;
        writeWithFormat(petrol_pump_name.getBytes(), new Formatter().bold_with_height().get(), Formatter.centerAlign());
        writeWithFormat(pump_address.getBytes(), new Formatter().height().get(), Formatter.centerAlign());
        writeWithFormat(state_with_pin.getBytes(), new Formatter().height().get(), Formatter.centerAlign());
        writeWithFormat(GST_TIN.getBytes(), new Formatter().height().get(), Formatter.centerAlign());
        writeWithFormat(VAT_TIN.getBytes(), new Formatter().height().get(), Formatter.centerAlign());
        writeWithFormat(state.getBytes(), new Formatter().height().get(), Formatter.centerAlign());
        writeWithFormat(credit_delivery_advise.getBytes(), new Formatter().bold().get(), Formatter.centerAlign());
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(advise_no.getBytes(), new Formatter().height().get(), Formatter.leftAlign());
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(company_name.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(customer_address.getBytes(), new Formatter().height().get(), Formatter.leftAlign());
        writeWithFormat(gst.getBytes(), new Formatter().height().get(), Formatter.leftAlign());
        writeWithFormat(pan.getBytes(), new Formatter().height().get(), Formatter.leftAlign());
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(driver_datails.toString().getBytes(), new Formatter().height().get(), Formatter.leftAlign());
        writeWithFormat(request_id_with_date.getBytes(), new Formatter().height().get(), Formatter.leftAlign());
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(product_gheading.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(item_name.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(ordered_qty.getBytes(), new Formatter().height().get(), Formatter.leftAlign());
        writeWithFormat(deliverd.getBytes(), new Formatter().height().get(), Formatter.leftAlign());
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
//        writeWithFormat(mode.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
        if(prefsManager.getfueltc1()==null&&prefsManager.getfueltc2()==null) {
        }else {
            writeWithFormat(terms.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
        }
        if(prefsManager.getfueltc1()!=null){
            if(prefsManager.getfueltc2()==null){
                String ss=prefsManager.getfueltc1()+"\n\n";
                writeWithFormat(ss.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
            }else {
                String ss=prefsManager.getfueltc1()+"\n";
                writeWithFormat(ss.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
            }
        }
        if(prefsManager.getfueltc2()!=null){
            String sss=prefsManager.getfueltc2()+"\n\n";
            writeWithFormat(sss.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
        }
        writeWithFormat(stringBuilder4.toString().getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
        writeWithFormat(pump.getBytes(),new Formatter().height().bold().get(),Formatter.rightAlign());
        writeWithFormat(ptrol_pump_bootom_detail.getBytes(), new Formatter().height().bold().get(), Formatter.centerAlign());
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(stringBuilder5.toString().getBytes(), new Formatter().height().height().get(), Formatter.centerAlign());
        writeWithFormat(line1.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());


    }

    private void printTop_info() {
         product_gheading="Product Delivered :"+"\n";
         item_name=item_name+"\n";
         line="-----------------------------------------------"+"\n";
         line1="------------------------------------------------"+"\n\n";
         if(!ordererd_qty.equals("Full Tank")){
            ordered_qty="Qty. Ord  : "+ordererd_qty+" "+type+"\n";
         }else {
            ordered_qty="Qty. Ord  : "+ordererd_qty+" "+"\n";
         }
         if(!petroldiesel_type.contains("Full")){
            deliverd="Qty. Deld : "+fuel_amount+" "+petroldiesel_type+"\n";
         }else {
            deliverd="Qty. Deld : "+fuel_amount+" "+"Ltr"+"\n";
         }
         gst="GSTIN      : "+gst_no+"\n";
         pan="PAN        : "+pan_no+"\n";
         driver_datails=new StringBuilder();
         driver_datails.append(
                        "Agent      : "+Customer_Name+"\n"+
                        "Vehicle No.: "+Vehicle_No+"\n"+
                        "Driver     : "+Driver_Name+"\n"+
                        "Mobile No. : "+Driver_Mobile+"\n");
         request_id_with_date="Req.No.    : "+RequestId+"    Dt:"+order_date+"\n";
         stringBuilder4=new StringBuilder();
         stringBuilder4.append("*Rates applicable on the date & time of delivery will be charged in the periodic bill"+"\n\n");
         ptrol_pump_bootom_detail="Computer Generate Invoice"+"\n";
         pump="for "+petrol_pump_name+"\n";
         stringBuilder5=new StringBuilder();
         stringBuilder5.append("Powered by : www.garruda.co.in"+"\n");
         mode="Payment Mode: "+payment_mode+"\n";
          terms="Terms & Condition:"+"\n";
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationmarker.setEnabled(true);
                }else {
                    locationmarker.setEnabled(false);
                    Toast.makeText(this, "Please enable Location Setting", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    @Override
    public void onBackPressed() {
        backdialog();
    }
    private void backdialog() {
        final Dialog ab=new Dialog(PrintActivity.this);
        ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view= LayoutInflater.from(PrintActivity.this).inflate(R.layout.popdialogforpending_request,null,false);
        ab.setContentView(R.layout.popdialogforpending_request);
        TextView alertmessage=(TextView)ab.findViewById(R.id.alertmessage);
        TextView clickokpending=(TextView)ab.findViewById(R.id.clickokpending);
        TextView clickokhome=(TextView)ab.findViewById(R.id.clickokhome);
        if(pending_request.equals("0")){
            clickokpending.setVisibility(View.GONE);
            alertmessage.setText("Do you want to leave this page?");
        }else {
            alertmessage.setText("You have pending request for Other.Do you want to proceed?");
        }
        clickokhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PrintActivity.this,DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
        clickokpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ab.show();
    }

    public boolean createBond(String btDevice)
            throws Exception
    {
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = class1.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }


    private void getPairedDevices() {
        Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
        if(pairedDevice.size()>0)
        {
            for(BluetoothDevice device : pairedDevice)
            {

            }
        }

    }
}