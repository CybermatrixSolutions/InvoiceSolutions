package com.cybermatrixsolutions.invoicesolutions.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cybermatrixsolutions.invoicesolutions.Calculation_Activity;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.With_QR.RequestWithQrCode;
import com.cybermatrixsolutions.invoicesolutions.activity.shift_sattlement.ShiftSettleOption;
import com.cybermatrixsolutions.invoicesolutions.adapter.DashboardAdapter;
import com.cybermatrixsolutions.invoicesolutions.fragment.FragmentDrawer;
import com.cybermatrixsolutions.invoicesolutions.activity.walkin.WalkinCustomerInformationActivity;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequest;
import com.cybermatrixsolutions.invoicesolutions.activity.nozzleCmr.EndNozzleReadingActivity;
import com.cybermatrixsolutions.invoicesolutions.activity.shiftallocation.ShiftAllocation;
import com.cybermatrixsolutions.invoicesolutions.activity.WithoutQR.WithoutQrCodeActivityForOil;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiClient;
import com.cybermatrixsolutions.invoicesolutions.rest.ApiInterface;
import com.cybermatrixsolutions.invoicesolutions.utils.CustomDialogWithCurrentActivity;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    String[] data;
    Integer[] itemname;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    LinearLayout ll_overlay;Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    GridView grid;
    private String KEY;


    String[] web = {
            "Scan QR Code",
            "No QR Code",
            "Walk in Customer",
            "Shift Allocation",
            "Nozzle CMR",

            "Shift Settlement"
            } ;
    int[] imageId = {
            R.drawable.fuelrequest,
            R.drawable.lube,
            R.drawable.users,
            R.drawable.shift,
            R.drawable.noz,
            R.drawable.money
            };


    String[] web1 = {
            "Scan QR Code",
            "No QR Code",
            "Walk in Customer",

    } ;
    int[] imageId1 = {
            R.drawable.fuelrequest,
            R.drawable.lube,
            R.drawable.nozzle,
    };
    PrefsManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mToolbar = (Toolbar) findViewById( R.id.toolbar);
        setSupportActionBar(mToolbar);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById( R.id.fragment_navigation_drawer);
        drawerFragment.setUp( R.id.fragment_navigation_drawer, (DrawerLayout) findViewById( R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        grid=(GridView)findViewById(R.id.gridView);

        manager=new PrefsManager(this);
        KEY=manager.getKey("key");


        if(manager.getdisignation().equals("Salesman")){
            DashboardAdapter adapter=new DashboardAdapter(DashboardActivity.this,web1,imageId1);
            grid.setAdapter(adapter);
        }else {
            DashboardAdapter adapter=new DashboardAdapter(DashboardActivity.this,web,imageId);
            grid.setAdapter(adapter);
        }

        if(KEY!=null);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(DashboardActivity.this,RequestWithQrCode.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent barintent=new Intent(DashboardActivity.this,WithoutQrCodeActivityForOil.class);
                        barintent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(barintent);
                        break;
                    case 2:
                        checkshiftOpen();
                        break;
                    case 3:
                        Intent mislinious=new Intent(DashboardActivity.this,ShiftAllocation.class);
                        mislinious.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mislinious);
                        break;
                    case 4:
                        Intent nozzleReading1=new Intent(DashboardActivity.this,EndNozzleReadingActivity.class);
                        nozzleReading1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(nozzleReading1);
                        break;
                    case 5:
                         mislinious=new Intent(DashboardActivity.this,ShiftSettleOption.class);
                        mislinious.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mislinious);

                        break;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            new PrefsManager(this).clearAllData();
            finish();
         }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
            displayView(position);

    }

    public void displayView(int position) {
        Fragment fragment = null;
        String title = getString( R.string.app_name);

        switch (position) {
            case 0:
                Intent  intent1 = new Intent(getApplicationContext(), RequestWithQrCode.class);
                startActivity(intent1);
                break;
            case 1:
              Intent  intent = new Intent(DashboardActivity.this, WithoutQrCodeActivityForOil.class);
                startActivity(intent);
                break;
            case 2:

                checkshiftOpen();
                break;
            case 3:
                if(manager.getdisignation().equals("Salesman")) {
                    Intent intent11 = new Intent(DashboardActivity.this, LoginActivity.class);
                    new PrefsManager(this).clearAllData();
                    finish();
                    startActivity(intent11);
                }else {
                    Intent mislenious = new Intent(DashboardActivity.this, ShiftAllocation.class);
                    startActivity(mislenious);

                }
                break;
            case 4:
                Intent shiftsettlement = new Intent(DashboardActivity.this, EndNozzleReadingActivity.class);
                startActivity(shiftsettlement);
                break;
            case 5:
                Intent intent11 = new Intent(DashboardActivity.this, ShiftSettleOption.class);
                startActivity(intent11);
                finish();
            case 6:
                 intent11 = new Intent(DashboardActivity.this, LoginActivity.class);
                new PrefsManager(this).clearAllData();
                finish();
                startActivity(intent11);
                finish();
                break;

        }

     }
    private void checkshiftOpen() {

        final ProgressDialog pb = new ProgressDialog(this);
        pb.setMessage("Please wait Checking Shift.....");
        pb.setCancelable(false);
        pb.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerRequest> call=apiService.checkshiftopen(KEY);
        call.enqueue(new Callback<CustomerRequest>() {
            @Override
            public void onResponse(Call<CustomerRequest> call, Response<CustomerRequest> response) {
                CustomerRequest requestResponse=response.body();
                String status=requestResponse.getStatus();
                pb.dismiss();
                if(status.equalsIgnoreCase("success")) {
                    Intent nozzleReading=new Intent(DashboardActivity.this,WalkinCustomerInformationActivity.class);
                    nozzleReading.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(nozzleReading);
                }
                else {
                    CustomDialogWithCurrentActivity.customDialogwithsingleButton(DashboardActivity.this,"No Shift Open...!!!",R.drawable.dont_sign);
                }
            }
            @Override
            public void onFailure(Call<CustomerRequest> call, Throwable t) {
                pb.dismiss();
                CustomDialogWithCurrentActivity.customDialogwithsingleButton(DashboardActivity.this,"Connection Failed...!!!",R.drawable.dont_sign);

            }
        });
    }
}
