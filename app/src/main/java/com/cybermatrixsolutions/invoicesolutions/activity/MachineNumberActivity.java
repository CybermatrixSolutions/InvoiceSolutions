package com.cybermatrixsolutions.invoicesolutions.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cybermatrixsolutions.invoicesolutions.R;

public class MachineNumberActivity extends AppCompatActivity {

    private RadioGroup select;
    private RadioButton machine,lube;
    private LinearLayout mechine_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_number);
        select=(RadioGroup)findViewById(R.id.radio);
        machine=(RadioButton)findViewById(R.id.machineCheck);
        lube=(RadioButton)findViewById(R.id.lubeCheck);
        mechine_layout=(LinearLayout)findViewById(R.id.mechine_layout);

        select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch ((i)){
                    case R.id.machineCheck:
                        mechine_layout.setVisibility(View.VISIBLE);
                        findViewById(R.id.go1).setVisibility(View.GONE);
                        break;

                    case R.id.lubeCheck:
                        mechine_layout.setVisibility(View.GONE);
                        findViewById(R.id.go1).setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.go1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(intent);
            }
        });

    }
}
