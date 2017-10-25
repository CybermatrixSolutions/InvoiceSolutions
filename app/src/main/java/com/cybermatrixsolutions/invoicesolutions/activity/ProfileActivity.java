package com.cybermatrixsolutions.invoicesolutions.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
    }
}
