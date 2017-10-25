package com.cybermatrixsolutions.invoicesolutions.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.adapter.BarCodeScanAdapter;
import com.cybermatrixsolutions.invoicesolutions.adapter.QrScanAdapter;
import com.cybermatrixsolutions.invoicesolutions.model.QrScanResult;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BarcodeScanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button scanBtn,generate_invoice;
    private List<QrScanResult> qrScanResultList=new ArrayList<>();
    private QrScanResult qrScanResult;
    private RecyclerView recyclerView;
    private TextView subTotal,total,gst;
    LinearLayout totalLayout;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scanBtn = (Button) findViewById(R.id.scan_button);

        generate_invoice=(Button)findViewById(R.id.generate_invoice);

        relativeLayout=(RelativeLayout)findViewById(R.id.main);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        totalLayout=(LinearLayout) findViewById(R.id.Total);

        subTotal=(TextView)findViewById(R.id.sub_total);
        total=(TextView)findViewById(R.id.total);


        gst=(TextView)findViewById(R.id.gst);
        scanBtn.setOnClickListener(this);

        generate_invoice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PdfActivity.class));
             }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan Bar-code");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
      }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                String Content=result.getContents();
                String Formate=result.getFormatName();
              /*  tvScanContent.setText(Content);
                tvScanFormat.setText(Formate);*/
                try {
                    JSONObject jsonObj = new JSONObject(Content);
                    String Product_id=jsonObj.getString("product_id");
                    String Price=jsonObj.getString("price");
                    qrScanResult=new QrScanResult();
                    qrScanResult.setContent(Product_id);
                    qrScanResult.setFormate(Price);
                    qrScanResultList.add(qrScanResult);
                    if(qrScanResultList.size()>0){
                        totalLayout.setVisibility(View.VISIBLE);
                        BarCodeScanAdapter adapter=new BarCodeScanAdapter(getApplicationContext(),qrScanResultList);
                        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);
                        generate_invoice.setVisibility(View.VISIBLE);
                        int sum=0;
                        for(int j=0;j<qrScanResultList.size();j++){
                            String price=qrScanResultList.get(j).getFormate();
                            int total=Integer.parseInt(price);
                            sum=sum+total;
                        }
                        subTotal.setText("Rs."+sum);
                        total.setText("Rs."+sum);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


/*
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        canvas.drawPaint(paint);


        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);


        // write the document content
        String targetPdf = "/sdcard/test.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            generate_invoice.setText("Check PDF");
            boolean_save=true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
*/



/*
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }
*/

}
