package com.cybermatrixsolutions.invoicesolutions.customer_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.cybermatrixsolutions.invoicesolutions.activity.activity.LoginActivity;
import com.cybermatrixsolutions.invoicesolutions.R;

import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;


public class SplashActivity extends AppCompatActivity {

    PrefsManager prefsManager;
    boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash1);
        this.getSupportActionBar().hide();
        this.prefsManager =new PrefsManager(this);
        this.isLogin = this.prefsManager.isLoggedIn();
       // prefsManager.setLogin(false);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SplashActivity.this.isLogin){
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this,DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    SplashActivity.this.finish();
                }else
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                SplashActivity.this.finish();
            }
        },2000);
    }
}
