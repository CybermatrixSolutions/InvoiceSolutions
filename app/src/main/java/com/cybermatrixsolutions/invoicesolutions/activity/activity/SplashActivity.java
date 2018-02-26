package com.cybermatrixsolutions.invoicesolutions.activity.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.With_QR.Driver_DashBoard;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

public class SplashActivity extends AppCompatActivity {

    boolean isLogin=false;
    private PrefsManager pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        pref=new PrefsManager(this);
        isLogin=pref.isLoggedIn();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLogin){

                    if(pref.getKey("key")!=null){
                        if(pref.getUsertype().equals("Sales")){
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

                        }else if(pref.getUsertype().equals("Customer")) {
                            startActivity(new Intent(getApplicationContext(), com.cybermatrixsolutions.invoicesolutions.customer_module.activity.DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

                        }else {
                            startActivity(new Intent(getApplicationContext(), Driver_DashBoard.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

                        }
                    }else {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    }
                }
                else
                startActivity(new Intent(getApplicationContext(),DeviceIdMobileNumberActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        },2000);
    }

}
