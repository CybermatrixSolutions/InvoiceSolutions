package com.cybermatrixsolutions.invoicesolutions.activity.printing_activity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class WalkinPrintLubeActivity extends AppCompatActivity implements View.OnClickListener {
    PrefsManager prefsManager;
    String petrol_pump_name, pump_address, address_2, address_3, city, state, gstcode, pin_code, GST_TIN, VAT_TIN;
    String address3, state_with_pin, credit_delivery_advise, advise_no, GST_prefix;
    int month, year, day;
    String yearsplit;
    String company_name, address_one, address_two, address_three, gst_no, Customer_Name, Driver_Name,Vehicle_No, Driver_Mobile, RequestId;
    String customer_address, petroldiesel_type,taxdata;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothSocket mmSocket;
    BluetoothAdapter bluetoothAdapter;
    PulsatorLayout mPulsator;
    ImageView locationmarker;
    TextView text1, textView2, textView, textView3;
    LinearLayout linea1, linea2, linea3, linea4, linea5, linea6;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList1;
    String order_date,pin_no,pan_no,state_code,statefinal;
    String gst=null;
    String pan=null;
    String payment_mode;
    String customer_city=null;

                String pending_request,activity,invoice_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        prefsManager = new PrefsManager(this);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        month = month + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        yearsplit = "" + year;
        yearsplit = yearsplit.substring(2, 4);
        petrol_pump_name = prefsManager.getUserDetails().get("pump_legal_name");
        pump_address = prefsManager.getUserDetails().get("pump_address");
        address_2 = prefsManager.getUserDetails().get("address_2");
        address_3 = prefsManager.getUserDetails().get("address_3");
        city = prefsManager.getUserDetails().get("city");
        state = prefsManager.getUserDetails().get("state");
        gstcode = prefsManager.getUserDetails().get("gstcode");
        pin_code = prefsManager.getUserDetails().get("pin_code");
        GST_TIN = prefsManager.getUserDetails().get("GST_TIN");
        VAT_TIN = prefsManager.getUserDetails().get("VAT_TIN");
        GST_prefix = prefsManager.getUserDetails().get("gst_prefix");
        petrol_pump_name = petrol_pump_name + "\n";
        pump_address = pump_address + "\n";
        address3 = address_2 + " " + address_3 + "\n";
        state_with_pin = city+","+state + "-" + pin_code + "\n";
        GST_TIN = "GST/UIN : " + GST_TIN + "\n";
        VAT_TIN = "VATT TIN No : " + VAT_TIN + "\n";
        state = "State Name : " + state + ", " + "Code : " + gstcode + "\n";
        credit_delivery_advise = "GST Invoice" + "\n";
        invoice_no = getIntent().getStringExtra("invoice_no");
        if (invoice_no==null){
            invoice_no="";
        }
        advise_no = "Invoice No.: " + invoice_no+"        "+"Dt:"+day+"/"+month+"/"+year+"\n";
        company_name = getIntent().getStringExtra("company_name");
        if (company_name==null){
            company_name="";
        }
        address_one = getIntent().getStringExtra("address");
        if (address_one==null){
            address_one="";
        }
        address_two = getIntent().getStringExtra("address_two");
        if (address_two==null){
            address_two="";
        }
        address_three = getIntent().getStringExtra("address_three");
        if (address_three==null){
            address_three="";
        }
        gst_no = getIntent().getStringExtra("gst_no");
        if (gst_no==null){
            gst_no="";
        }
        Customer_Name = getIntent().getStringExtra("Customer_Name");
        if (Customer_Name==null){
            Customer_Name="";
        }
        Driver_Name = getIntent().getStringExtra("Driver_Name");
        if (Driver_Name==null){
            Driver_Name="";
        }
        petroldiesel_type = getIntent().getStringExtra("petroldiesel_type");
        if (petroldiesel_type==null){
            petroldiesel_type="";
        }
        Vehicle_No = getIntent().getStringExtra("Vehicle_No");
        if (Vehicle_No==null){
            Vehicle_No="";
        }
        Driver_Mobile = getIntent().getStringExtra("mobile");
        if (Driver_Mobile==null){
            Driver_Mobile="";
        }
        RequestId = getIntent().getStringExtra("RequestId");
        if (RequestId==null){
            RequestId="";
        }
        taxdata = getIntent().getStringExtra("taxdata");
        if (taxdata==null){
            taxdata="";
        }
        pin_no = getIntent().getStringExtra("pin_no");
        if (pin_no==null){
            pin_no="";
        }
        pan_no = getIntent().getStringExtra("pan_no");
        if (pan_no==null){
            pan_no="";
        }
        state_code = getIntent().getStringExtra("state_code");
        if (state_code==null){
            state_code="";
        }
        statefinal = getIntent().getStringExtra("statefinal");
        if (statefinal==null){
            statefinal="";
        }

        order_date = getIntent().getStringExtra("order_date");
        if (order_date==null){
            order_date="";
        }
        payment_mode= getIntent().getStringExtra("payment_mode");

        customer_city= getIntent().getStringExtra("customer_city");
        gst = "GSTIN      : " + gst_no + "\n";
        pan = "PAN        : " + pan_no + "\n";
        mPulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        locationmarker = (ImageView) findViewById(R.id.locationmarker);
        text1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView = (TextView) findViewById(R.id.textView);
        customer_address = address_one + "\n" +customer_city+","+ statefinal + "-" + pin_no + ".  " + "State Code:" + state_code + "\n";
        company_name = company_name + "\n";
        CheckBlueToothState();
        locationmarker.setOnClickListener(btnScanDeviceOnClickListener);
        registerReceiver(ActionFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        intialize();
    }
    private void intialize() {
        linea1 = (LinearLayout) findViewById(R.id.linea1);
        linea2 = (LinearLayout) findViewById(R.id.linea2);
        linea3 = (LinearLayout) findViewById(R.id.linea3);
        linea4 = (LinearLayout) findViewById(R.id.linea4);
        linea5 = (LinearLayout) findViewById(R.id.linea5);
        linea6 = (LinearLayout) findViewById(R.id.linea6);
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
    }
    private void CheckBlueToothState() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Your devices does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (bluetoothAdapter.isEnabled()) {
                if (bluetoothAdapter.isDiscovering()) {
                    Toast.makeText(this, "Searching Devices...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Bluetooth Enabled...", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Bluetooth is not Enabled...", Toast.LENGTH_SHORT).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }
    private Button.OnClickListener btnScanDeviceOnClickListener
            = new Button.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            boolean result = Utility.checkPermission(WalkinPrintLubeActivity.this);
            if (result) {
                bluetoothAdapter.startDiscovery();
                mPulsator.start();
                arrayList = new ArrayList();
                arrayList1 = new ArrayList();
            }}
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            CheckBlueToothState();
        }
    }
    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrayList.add(device.getAddress());
                arrayList1.add(device.getName());
                checkcondition(arrayList);
            }
        }
    };
    private void checkcondition(ArrayList<String> arrayList) {
        if (arrayList.size() == 1) {
            linea1.setVisibility(View.VISIBLE);
            text1.setText(arrayList1.get(0));
        } else if (arrayList.size() == 2) {
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            text1.setText(arrayList1.get(0));
            textView2.setText(arrayList1.get(1));
        } else if (arrayList.size() == 3) {
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.VISIBLE);
            text1.setText(arrayList1.get(0));
            textView2.setText(arrayList1.get(1));
            textView3.setText(arrayList1.get(2));
        } else if (arrayList.size() == 4) {
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.VISIBLE);
            linea4.setVisibility(View.VISIBLE);
            text1.setText(arrayList1.get(0));
            textView2.setText(arrayList1.get(1));
            textView3.setText(arrayList1.get(2));
            textView.setText(arrayList1.get(3));
        } else if (arrayList.size() == 5) {
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.VISIBLE);
            linea4.setVisibility(View.VISIBLE);
            linea5.setVisibility(View.VISIBLE);
        } else if (arrayList.size() == 6) {
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.VISIBLE);
            linea4.setVisibility(View.VISIBLE);
            linea5.setVisibility(View.VISIBLE);
            linea6.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        if (v == linea1) {
            printBillToDevice(arrayList.get(0));
        }
        if (v == linea2) {
            printBillToDevice(arrayList.get(1));
        }
        if (v == linea3) {
            printBillToDevice(arrayList.get(2));
        }
        if (v == linea4) {
            printBillToDevice(arrayList.get(3));
        }
        if (v == linea5) {
            printBillToDevice(arrayList.get(4));
        }
        if (v == linea6) {
            printBillToDevice(arrayList.get(5));
        }
    }
    private void printBillToDevice(final String address) {
        final String line="-----------------------------------------------"+"\n";
        final String request_id_with_date="Req.No.    : "+RequestId+" Dt:"+order_date+"\n";
        final StringBuilder driver_datails = new StringBuilder();
        driver_datails.append(
                        "Agent      : "+Customer_Name+"\n"+
                        "Vehicle No.: "+Vehicle_No+"\n"+
                        "Driver     : "+Driver_Name+"\n"+
                        "Mobile No. : "+Driver_Mobile+"\n");
        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                    }
                });
                bluetoothAdapter.cancelDiscovery();
                try {
                            BluetoothDevice mdevice = bluetoothAdapter
                            .getRemoteDevice(address);
                             Method m = mdevice.getClass().getMethod(
                            "createRfcommSocket", new Class[]{int.class});
                    mmSocket = (BluetoothSocket) m.invoke(mdevice, 1);
                    mmSocket.connect();
                    writeWithFormat(petrol_pump_name.getBytes(), new Formatter().bold_with_height().get(), Formatter.centerAlign());
                    writeWithFormat(pump_address.getBytes(), new Formatter().height().get(), Formatter.centerAlign());
                    writeWithFormat(state_with_pin.getBytes(), new Formatter().height().get(), Formatter.centerAlign());
                    writeWithFormat(GST_TIN.getBytes(), new Formatter().height().get(), Formatter.centerAlign());
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
                    String[]  cards = {"Product Deld.(HSN/SAC)", "Qty.", "Rate", "Amount",
                    };
                    for (int i = 0; i < cards.length; i += 4) {
                        String sss = cards[i] + getSeparation(cards[i].length(), 25) +
                                cards[i + 1] + getSeparation(cards[i + 1].length(), 7) +
                                cards[i + 2] + getSeparation(cards[i + 2].length(), 7) +
                                cards[i + 3] + getSeparation(cards[i + 3].length(), 10) +"\n";
                        writeWithFormat(sss.getBytes(), new Formatter().bold().get(), Formatter.rightAlign());
                    }

                    Print_Product_detail();
                    writeWithFormat(line.getBytes(), new Formatter().bold().small().get(), Formatter.leftAlign());
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
                            Toast.makeText(WalkinPrintLubeActivity.this, "Failed To Print", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }









    private void Print_Product_detail() {
        ArrayList<String> arry;
        ArrayList<String> arryprice;
        ArrayList<String> hsn;
        ArrayList<String> Item_Namearray;
        ArrayList<String> extname;
        ArrayList<String>textamount;
        ArrayList<String>quantity;
        try {
            JSONArray array1=new JSONArray(taxdata);
            arry=new ArrayList<>();
            arryprice=new ArrayList<>();
            hsn=new ArrayList<>();
            Item_Namearray=new ArrayList<>();
            extname=new ArrayList<>();
            textamount=new ArrayList<>();
            quantity=new ArrayList<>();

            for(int i=0;i<array1.length();i++) {
                JSONObject object = array1.getJSONObject(i);
                String id = object.getString("id");
                arry.add(id);
            }
            List<String> uniqueList = new ArrayList<String>(new HashSet<String>(arry));
            for (int i=0;i<uniqueList.size();i++) {
                String id = uniqueList.get(i);
                double sum=0;
                double price=0;
                String hsncode=null;
                String Item_Name=null;
                String texname=null;
                double textsvalue=0;
                String quant=null;
                for (int p=0;p<array1.length();p++){
                    JSONObject object = array1.getJSONObject(p);
                    String   idd = object.getString("id");
                    if(idd.equals(id)){
                        hsncode= object.getString("hsncode");
                        Item_Name= object.getString("Item_Name");
                        textsvalue= object.getDouble("Tax_percentage");
                        price = object.getDouble("price");
                        texname= object.getString("tax_type");
                        quant= object.getString("quantity");
                        sum=sum+textsvalue;
                        extname.add(texname);}
                }
                textamount.add(""+sum);
                arryprice.add(""+price);
                hsn.add(hsncode);
                quantity.add(quant);
                Item_Namearray.add(Item_Name);
            }
            double sum=0;
            ArrayList<String>cgstarray=new ArrayList<>();
            ArrayList<String>igtsarray=new ArrayList<>();
            ArrayList<String>sgstarray=new ArrayList<>();
            ArrayList<String>cessarray=new ArrayList<>();
            double due_amt=0;
            for(int p=0;p<Item_Namearray.size();p++){
                String hssncode=hsn.get(p);
                String id = uniqueList.get(p);
                String quant = quantity.get(p);

                double rete=Double.parseDouble(arryprice.get(p))*100;
                due_amt=due_amt+Double.parseDouble(arryprice.get(p));
                double devide=Double.parseDouble(textamount.get(p))+100;
                DecimalFormat df2 = new DecimalFormat(".##");
                double textbaleamount=rete/devide;
                double textbaleamount2=rete/devide;
                String rate=""+df2.format(textbaleamount);
                sum=sum+textbaleamount;
                for (int s=0;s<array1.length();s++){
                    double cgstamt=0;
                    JSONObject object = array1.getJSONObject(s);
                    double   textsvalue= object.getDouble("Tax_percentage");
                    String   texname= object.getString("tax_type");
                    if(object.getString("id").equals(id)){
                        if(texname.contains("CGST")){
                            double devidevalue=0;
                            devidevalue=textbaleamount2;
                            double  textbaleamount3=devidevalue*textsvalue;
                            cgstamt= textbaleamount3/100;
                            cgstarray.add(""+cgstamt);
                        }
                        if(texname.contains("SGST")){
                            double devidevalue=0;
                            devidevalue=textbaleamount2;
                            double  textbaleamount3=devidevalue*textsvalue;
                            cgstamt= textbaleamount3/100;
                            sgstarray.add(""+cgstamt);
                        }
                        if(texname.contains("IGST")){
                            double devidevalue=0;
                            devidevalue=textbaleamount2;
                            double  textbaleamount3=devidevalue*textsvalue;
                            cgstamt= textbaleamount3/100;
                            igtsarray.add(""+cgstamt);

                        }
                        if(texname.contains("CESS")){
                            double devidevalue=0;
                            devidevalue=textbaleamount2;
                            double  textbaleamount3=devidevalue*textsvalue;
                            cgstamt= textbaleamount3/100;
                            cessarray.add(""+cgstamt);
                        }}}
                String[]  cards1 = new String[]{Item_Namearray.get(p)+"("+hssncode+")",quant,rate,rate,
                };for (int j = 0; j < cards1.length; j += 4) {
                    String ss = cards1[j] + getSeparation(cards1[j].length(), 25) +
                            cards1[j + 1] + getSeparation(cards1[j + 1].length(), 7) +
                            cards1[j + 2] + getSeparation(cards1[j + 2].length(), 7) +
                            cards1[j + 3] + getSeparation(cards1[j + 3].length(), 10) + "\n";
                    writeWithFormat(ss.getBytes(), new Formatter().height().get(), Formatter.rightAlign());
                }}
            String sum_all = "Total:"+String.format("%.2f", sum);
            sum_all=sum_all+"   "+"\n";
            writeWithFormat(sum_all.getBytes(), new Formatter().height().bold().get(), Formatter.rightAlign());
            double round_calculate=sum;
            double due_amount=0;
            double cgsttotal=0;
            if(cgstarray.size()>0){
                for(int l=0;l<cgstarray.size();l++){
                    due_amount=due_amount+(Double.parseDouble(cgstarray.get(l)));
                    cgsttotal=cgsttotal+(Double.parseDouble(cgstarray.get(l)));
                }double sumpercent=cgsttotal*100;
                double  total_cgst_percent=sumpercent/sum;
                String cess_total_amt ="CGST:"+String.format("%.2f", cgsttotal);
                cess_total_amt=cess_total_amt+"   "+"\n";

                writeWithFormat(cess_total_amt.getBytes(), new Formatter().height().bold().get(), Formatter.rightAlign());
                round_calculate=round_calculate+cgsttotal;
            }
            double  sgst_total=0;
            if(sgstarray.size()>0){
                for(int l=0;l<sgstarray.size();l++){
                    due_amount=due_amount+(Double.parseDouble(sgstarray.get(l)));
                    sgst_total=sgst_total+(Double.parseDouble(sgstarray.get(l)));
                }
                double sumpercent=sgst_total*100;
                double  total_sgst_percent=sumpercent/sum;
                String cess_total_amt ="SGST:"+String.format("%.2f", sgst_total);
                cess_total_amt=cess_total_amt+"   "+"\n";
                writeWithFormat(cess_total_amt.getBytes(), new Formatter().height().bold().get(), Formatter.rightAlign());
                round_calculate=round_calculate+sgst_total;
            }
            double  igst_total=0;
            if(igtsarray.size()>0){
                for(int l=0;l<igtsarray.size();l++){
                    due_amount=due_amount+(Double.parseDouble(igtsarray.get(l)));
                    igst_total=igst_total+(Double.parseDouble(igtsarray.get(l)));
                }
                double sumpercent=igst_total*100;
                double  total_igst_percent=sumpercent/sum;
                String cess_total_amt ="IGST:"+String.format("%.2f", igst_total);
                cess_total_amt=cess_total_amt+"   "+"\n";
                writeWithFormat(cess_total_amt.getBytes(), new Formatter().height().bold().get(), Formatter.rightAlign());
                round_calculate=round_calculate+igst_total;
            }
            double  cess_total=0;
            if(cessarray.size()>0) {
                for (int l = 0; l < cessarray.size(); l++) {
                    due_amount = due_amount + (Double.parseDouble(cessarray.get(l)));
                    cess_total = cess_total + (Double.parseDouble(cessarray.get(l)));
                }
                double sumpercent=cess_total*100;
                double  total_cess_percent=sumpercent/sum;
                String cess_total_amt ="CESS:"+String.format("%.2f", cess_total);
                cess_total_amt=cess_total_amt+"   "+"\n";
                writeWithFormat(cess_total_amt.getBytes(), new Formatter().height().bold().get(), Formatter.rightAlign());
                round_calculate=round_calculate+cess_total;
            }
            double round_value=0;
            if(due_amt>round_calculate){
                round_value=due_amt-round_calculate;
                String rd="R/Off(+):  "+String.format("%.2f", round_value);
                String ss=""+String.format("%.2f", round_value);
                rd=rd+"   "+"\n";
                if(!ss.equals("0.00")){
                    writeWithFormat(rd.getBytes(), new Formatter().height().get(), Formatter.rightAlign());
                }
            }else if(due_amt<round_calculate){
                round_value=round_calculate-due_amt;

                String rd="R/Off(-):  "+String.format("%.2f", round_value);
                rd=rd+"   "+"\n";
                String ss=""+String.format("%.2f", round_value);
                if(!ss.equals("0.00")){
                    writeWithFormat(rd.getBytes(), new Formatter().height().get(), Formatter.rightAlign());
                }


            }
            String dues_amt="   Due Amt.:"+String.format("%.2f", due_amt);
            dues_amt=dues_amt+"   "+"\n";
            writeWithFormat(dues_amt.getBytes(), new Formatter().height().bold().get(), Formatter.rightAlign());
            printbrief_info(hsn,arryprice,textamount,uniqueList);
        }catch (Exception e){
        }
    }
    private void printbrief_info(ArrayList<String> hsn, ArrayList<String> arryprice, ArrayList<String> textamount, List<String> uniqueList) {
        final String line="-----------------------------------------------"+"\n";
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
                    String[]  cards4 = {"HSN/SAC","(%)", "Amt", "CGST", "SGST","IGST", "Cess", "Total",
                    };
                          for (int l = 0; l < cards4.length; l += 8) {
                            String ssssss = cards4[l] + getSeparation(cards4[l].length(), 9) +
                                    cards4[l + 1] + getSeparation(cards4[l + 1].length(), 7) +

                                    cards4[l + 2] + getSeparation(cards4[l + 2].length(), 9) +
                                cards4[l + 3] + getSeparation(cards4[l + 3].length(), 7) +
                                cards4[l + 4] + getSeparation(cards4[l + 4].length(), 7) +
                                cards4[l + 5] + getSeparation(cards4[l + 5].length(), 7) +
                                    cards4[l + 6] + getSeparation(cards4[l + 6].length(), 7) +


                                cards4[l + 7] + getSeparation(cards4[l + 7].length(), 9)+"\n";
                        writeWithFormat(ssssss.getBytes(), new Formatter().small().bold().get(), Formatter.leftAlign());
                    }
                     writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
                          try {
            JSONArray array1 = new JSONArray(taxdata);
            double total_texable_amount=0;
            double total_cgst_amount=0;
            double total_sgst_amount=0;
            double total_igst=0;
            double total_cess=0;
            double total_total=0;
                          ArrayList<String>hsn2 =hsn;
           hsn = new ArrayList<String>(new HashSet<String>(hsn));
           double calculate_without_all_tax_amount=0;
                              double calculate_cgsts=0;
                              double calculate_igsts=0;
                              double calculate_sgsts=0;
                              double calculate_cess=0;
                              double toal_with_texas=0;

         for (int i=0;i<hsn.size();i++) {
                String hsncode=hsn.get(i);
                ArrayList<String>hsncodearr=new ArrayList<>();
                hsncodearr.add(hsncode);
                ArrayList<String>cgstarray=new ArrayList<>();
                ArrayList<String>igstarray=new ArrayList<>();
                ArrayList<String>cessarray=new ArrayList<>();
                ArrayList<String>sgstarray=new ArrayList<>();
                ArrayList<String>total_texas=new ArrayList<>();
                  double ttttttt=0;
                   double prices=0;
                   double withouttaxvalue=0;
                   for (int l=0;l<uniqueList.size();l++){
                    JSONObject object=array1.getJSONObject(l);
                    String hsncode2=hsn2.get(l);
                    if(hsncode2.equals(hsncode)){
                        String price=arryprice.get(l);

                        double   ttttt=0;
                    prices=prices+Double.parseDouble(arryprice.get(l));
                    toal_with_texas=toal_with_texas+Double.parseDouble(arryprice.get(l));
                       String id=uniqueList.get(l);
                    double   total_percent=0;
                    double   cgst=0;
                    double   sgts=0;
                    double   igst=0;
                    double   cess=0;
                        for (int k=0;k<array1.length();k++){
                            JSONObject object2=array1.getJSONObject(k);
                            String hsncode3=object2.getString("hsncode");
                            String taxtype=object2.getString("tax_type");
                            String idd=object2.getString("id");
                             String Tax_percentage=object2.getString("Tax_percentage");
                               if(idd.equals(id)){
                                if(taxtype.contains("CGST")){
                                    cgst=Double.parseDouble(Tax_percentage);
                                    total_percent=total_percent+Double.parseDouble(Tax_percentage);
                                    ttttt=ttttt+Double.parseDouble(Tax_percentage);
                                }
                                if(taxtype.contains("IGST")){
                                    igst=Double.parseDouble(Tax_percentage);
                                    total_percent=total_percent+Double.parseDouble(Tax_percentage);
                                    ttttt=ttttt+Double.parseDouble(Tax_percentage);
                                } if(taxtype.contains("SGST")){
                                    sgts=Double.parseDouble(Tax_percentage);
                                    total_percent=total_percent+Double.parseDouble(Tax_percentage);
                                       ttttt=ttttt+Double.parseDouble(Tax_percentage);
                                }if(taxtype.contains("CESS")){
                                    cess=Double.parseDouble(Tax_percentage);
                                    total_percent=total_percent+Double.parseDouble(Tax_percentage);
                                }}

                        }
                        ttttttt=ttttt;
                        double total_amt=Double.parseDouble(price)*100;
                        double devide_value=total_percent+100;
                        double withouttax=total_amt/devide_value;
                        calculate_without_all_tax_amount=calculate_without_all_tax_amount+withouttax;
                        withouttaxvalue=+withouttaxvalue+withouttax;
                        double totalcgst=withouttax*cgst;
                        double totalsgst=withouttax*sgts;
                        double totaligst=withouttax*igst;
                        double totalcess=withouttax*cess;
                        cgstarray.add(""+totalcgst/100);
                         igstarray.add(""+totaligst/100);
                         cessarray.add(""+totalcess/100);
                         sgstarray.add(""+totalsgst/100);
                         calculate_cgsts=calculate_cgsts+totalcgst/100;
                         calculate_igsts=calculate_igsts+totaligst/100;
                         calculate_sgsts=calculate_sgsts+totalsgst/100;
                         calculate_cess=calculate_cess+totalcess/100;


                }}
                double cgstssum=0;
                if(cgstarray.size()>0){
                    for(int p=0;p<cgstarray.size();p++){
                        cgstssum=cgstssum+Double.parseDouble(cgstarray.get(p));
                    }
                }
                double sgstsum=0;
                if(sgstarray.size()>0){
                    for(int p=0;p<sgstarray.size();p++){
                        sgstsum=sgstsum+Double.parseDouble(sgstarray.get(p));
                    }
                }  double igstsum=0;
                if(igstarray.size()>0){
                    for(int p=0;p<igstarray.size();p++){
                        igstsum=igstsum+Double.parseDouble(igstarray.get(p));
                    }
                }
                double cesssum=0;
                if(cessarray.size()>0){
                    for(int p=0;p<cessarray.size();p++){
                        cesssum=cesssum+Double.parseDouble(cessarray.get(p));
                    }
                }
                String cgstcgsttotal=null ,sgstsgstttotal=null ,igsttotaligsttotaltttotal=null ,cesstotralcesstotralttotal=null;
                if(cgstssum>0){
                    cgstcgsttotal=String.format("%.2f", cgstssum);
                }else {
                    cgstcgsttotal="0.00";
                }
                if(sgstsum>0){
                    sgstsgstttotal=String.format("%.2f", sgstsum);
                }else {
                    sgstsgstttotal="0.00";
                }
                if(igstsum>0){
                    igsttotaligsttotaltttotal=String.format("%.2f", igstsum);
                }else {
                    igsttotaligsttotaltttotal="0.00";
                }  if(cesssum>0){
                    cesstotralcesstotralttotal=String.format("%.2f", cesssum);
                }else {
                    cesstotralcesstotralttotal="0.00";
                }

                String ssss=String.format("%.2f", ttttttt)+"%";

//                Double with_tax_amount=Double.parseDouble(prices);
                String[]  cards5 = {hsncode,ssss,String.format("%.2f", withouttaxvalue),cgstcgsttotal,sgstsgstttotal,igsttotaligsttotaltttotal,cesstotralcesstotralttotal,String.format("%.2f", prices),
                };
                for (int l = 0; l < cards5.length; l += 8) {
                    String ssssss = cards5[l] + getSeparation(cards5[l].length(), 9) +
                            cards5[l + 1] + getSeparation(cards5[l + 1].length(), 7) +
                            cards5[l + 2] + getSeparation(cards5[l + 2].length(), 9) +
                            cards5[l + 3] + getSeparation(cards5[l + 3].length(), 7) +
                            cards5[l + 4] + getSeparation(cards5[l + 4].length(), 7) +
                            cards5[l + 5] + getSeparation(cards5[l + 5].length(), 7) +
                            cards5[l + 6] + getSeparation(cards5[l + 6].length(), 7) +
                            cards5[l + 7] + getSeparation(cards5[l + 7].length(), 9)+"\n";
                    writeWithFormat(ssssss.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
                }
            }
            print("Total",calculate_without_all_tax_amount,calculate_cgsts,calculate_sgsts,calculate_igsts,calculate_cess,toal_with_texas);
        }catch (Exception e){
        }
    }
    private void print(String total, double total_texable_amount, double total_cgst_amount, double total_sgst_amount, double total_igst, double total_cess, double total_total) {
        String total_cgst=null;
        String total_sgst_=null;
        String total_igstt=null;
        String total_cesss=null;
       if(total_cgst_amount>0){
           total_cgst=String.format("%.2f", total_cgst_amount);

      }else {
           total_cgst="0.00";
      }
      if(total_sgst_amount>0){
          total_sgst_=String.format("%.2f", total_sgst_amount);
        }else {
          total_sgst_="0.00";
        }
        if(total_igst>0){
            total_igstt=String.format("%.2f", total_igst);

        }else {
            total_igstt="0.00";
        }  if(total_cess>0){
            total_cesss=String.format("%.2f", total_cess);

        }else {
            total_cesss="0.00";
        }
        String   total_texabl=String.format("%.2f", total_texable_amount);
        String   total_=String.format("%.2f",total_total);
        final String line="-----------------------------------------------"+"\n";
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        String[]  cards5 = {total,"",total_texabl,total_cgst,total_sgst_,total_igstt,total_cesss,total_,
        };
        for (int l = 0; l < cards5.length; l += 8) {
            String ssssss = cards5[l] + getSeparation(cards5[l].length(), 9) +
                    cards5[l + 1] + getSeparation(cards5[l + 1].length(), 7) +
                    cards5[l + 2] + getSeparation(cards5[l + 2].length(), 9) +
                    cards5[l + 3] + getSeparation(cards5[l + 3].length(), 7) +
                    cards5[l + 4] + getSeparation(cards5[l + 4].length(), 7) +
                    cards5[l + 5] + getSeparation(cards5[l + 5].length(), 7) +
                    cards5[l + 6] + getSeparation(cards5[l + 6].length(), 7) +
                    cards5[l + 7] + getSeparation(cards5[l + 7].length(), 9)+"\n";
            writeWithFormat(ssssss.getBytes(), new Formatter().bold().small().get(), Formatter.leftAlign());
        }
        String mode="Payment Mode: "+payment_mode+"\n";
        String terms="Terms & Condition:"+"\n";
        String dumy="Computer generated invoice"+"\n";
        String garudda_="Powered by:www.Garruda.co.in"+"\n";
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(mode.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
        if(prefsManager.getgsttc1()==null&&prefsManager.getgsttc2()==null) {
        }else {
            writeWithFormat(terms.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
        }
        if(prefsManager.getgsttc1()!=null){
            if(prefsManager.getgsttc2()==null){
                String ss=prefsManager.getgsttc1()+"\n\n";
                writeWithFormat(ss.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
            }else {
                String ss=prefsManager.getgsttc1()+"\n";
                writeWithFormat(ss.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
            }

        }
        if(prefsManager.getgsttc2()!=null){
            String sss=prefsManager.getgsttc2()+"\n\n";
            writeWithFormat(sss.getBytes(), new Formatter().height().bold().get(), Formatter.leftAlign());
        }
        String pump="for "+petrol_pump_name+"\n";
        writeWithFormat(pump.getBytes(), new Formatter().height().bold().get(), Formatter.rightAlign());
        writeWithFormat(dumy.getBytes(), new Formatter().height().bold().get(), Formatter.centerAlign());
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
        writeWithFormat(garudda_.getBytes(), new Formatter().height().bold().get(), Formatter.centerAlign());
        writeWithFormat(line.getBytes(), new Formatter().bold().get(), Formatter.leftAlign());
    }
    static String getSeparation(int len, int len2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len2 - len; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationmarker.setEnabled(true);
                } else {
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
        final Dialog ab=new Dialog(WalkinPrintLubeActivity.this);
        ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view= LayoutInflater.from(WalkinPrintLubeActivity.this).inflate(R.layout.popdialogforpending_request,null,false);
        ab.setContentView(R.layout.popdialogforpending_request);
        TextView alertmessage=(TextView)ab.findViewById(R.id.alertmessage);
        TextView clickokpending=(TextView)ab.findViewById(R.id.clickokpending);
        TextView clickokhome=(TextView)ab.findViewById(R.id.clickokhome);
            clickokpending.setVisibility(View.GONE);
            alertmessage.setText("Do you want to leave this page?");
                clickokhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(WalkinPrintLubeActivity.this,DashboardActivity.class);
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
}