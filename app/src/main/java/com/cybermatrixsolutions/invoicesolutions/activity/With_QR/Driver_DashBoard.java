package com.cybermatrixsolutions.invoicesolutions.activity.With_QR;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.model.OTPMODEL;
import com.cybermatrixsolutions.invoicesolutions.model.QRModel;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Driver_DashBoard extends AppCompatActivity {


    PrefsManager manager;
String Qr,OTP;
TextView code;
ImageView bar_code;

Toolbar toolbar;
ImageView logout;
ImageView qrcode_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__dash_board);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        getActionBar();
        logout=(ImageView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Driver_DashBoard.this, LoginActivity.class);
                startActivity(i);
                new PrefsManager(Driver_DashBoard.this).clearAllData();
                finish();

            }
        });
        manager=new PrefsManager(this);
        code=(TextView)findViewById(R.id.code);
        bar_code=(ImageView)findViewById(R.id.bar_code);

        bar_code.setClickable(false);
        bar_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BarCode_DetalDialog();
            }
        });
        CallApi(manager.getKey("key"),manager.getCustomer_Code());
    }

    private void BarCode_DetalDialog() {
        AlertDialog.Builder ab=new AlertDialog.Builder(Driver_DashBoard.this);
        View view1= LayoutInflater.from(Driver_DashBoard.this).inflate(R.layout.detail,null,false);
        ab.setView(view1);
        final TextView customer_name=(TextView) view1.findViewById(R.id.customer_name);
        final TextView vehicel_no=(TextView)view1.findViewById(R.id.vehicel_no);
        final TextView pump_name=(TextView)view1.findViewById(R.id.pump_name);
        qrcode_image=(ImageView)view1.findViewById(R.id.qrcode_image);
        if(Qr!=null){
            try {
                Bitmap bitmap = TextToImageEncode(Qr);
                qrcode_image.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }else {
            qrcode_image.setImageResource(R.drawable.garuda_logo);
        }

         customer_name.setText(manager.getCustomer_Name());
         vehicel_no.setText(manager.getRegistration_Number());
         pump_name.setText(manager.getpump_legal_name());
         final Dialog dialog=ab.create();
         dialog.show();
    }

    private void CallApi(final String key,String customer_code) {
        PrefsManager pref=new PrefsManager(getApplicationContext());
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.showqr(key,customer_code);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest loginResponse=response.body();
                String status=loginResponse.getStatus();
                if(status.equalsIgnoreCase("success")){
                    List<QRModel>arraylis=loginResponse.getCustomerRequestResponse().getQR();
                    if(arraylis!=null){
                        Qr=arraylis.get(0).getQrcode();
                        try {
                        Bitmap bitmap = TextToImageEncode(Qr);
                            bar_code.setImageBitmap(bitmap);
                            bar_code.setClickable(true);

                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                    }else {
                    }
                    CallOtpApi(key);



                }else {

                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();

                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(Driver_DashBoard.this,"Connection Failed",R.drawable.dont_sign);

            }
        });
    }

    private void CallOtpApi(String key) {
        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.showotp(key);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest loginResponse=response.body();
                String status=loginResponse.getStatus();
                if(status.equalsIgnoreCase("success")){
                    List<OTPMODEL>arraylis=loginResponse.getCustomerRequestResponse().getOTP();
                    if(arraylis!=null){
                        OTP=arraylis.get(0).getOTP();
                        code.setText(OTP);
                    }
                }else {

                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(Driver_DashBoard.this,"Connection Failed",R.drawable.dont_sign);

            }
        });
    }
    Bitmap TextToImageEncode(String Value) throws WriterException {
        int QRcodeWidth = 500 ;
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white_color);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}