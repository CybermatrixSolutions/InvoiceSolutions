package com.cybermatrixsolutions.invoicesolutions.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cybermatrixsolutions.invoicesolutions.R;

/**
 * Created by Diwakar on 10/6/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.changePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),VerifyMobileNumber.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });
    }
}
