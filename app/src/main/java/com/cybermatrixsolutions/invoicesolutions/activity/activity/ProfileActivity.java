package com.cybermatrixsolutions.invoicesolutions.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.utils.PrefsManager;

/**
 * Created by Diwakar on 10/6/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    private TextView name,desigation,dob,appointment,mobile,empcode,email;
    private String Name,Desigation,Dob,Appointment,Mobile,Empcode,Email;
    private PrefsManager prefsManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        name=(TextView)findViewById(R.id.name);
        desigation=(TextView)findViewById(R.id.designation);
        dob=(TextView)findViewById(R.id.dob);
        appointment=(TextView)findViewById(R.id.appointment);
        mobile=(TextView)findViewById(R.id.mobile);
        empcode=(TextView)findViewById(R.id.empcode);
        email=(TextView)findViewById(R.id.email);
        prefsManager=new PrefsManager(this);
        Name=prefsManager.getUserDetails().get("Personnel_Name");
        Desigation=prefsManager.getUserDetails().get("Designation_Name");
        Dob=prefsManager.getUserDetails().get("Date_of_Birth");
        Appointment=prefsManager.getUserDetails().get("Date_of_Appointment");
        Mobile=prefsManager.getUserDetails().get("mobile");
        Empcode=prefsManager.getUserDetails().get("Employeecode");
        Email=prefsManager.getUserDetails().get("email");
        if(Name!=null){
            name.setText(Name);
        }
        if(Desigation!=null){
            desigation.setText(Desigation);
        }
        if(Dob!=null){
            String [] Datediv=Dob.split("-");
            final   String yy=Datediv[0];
            final   String mm=Datediv[1];
            final   String dd=Datediv[2];
            final   int yy1= Integer.parseInt(Datediv[0]);
            final   int mm1= Integer.parseInt(Datediv[1]);
            final   int dd1= Integer.parseInt(Datediv[2]);
            String Month="";
            if(mm.equalsIgnoreCase("01")){
                Month="Jan";
            }
            if(mm.equalsIgnoreCase("02")){
                Month="Feb";

            }
            if(mm.equalsIgnoreCase("03")){
                Month="March";

            }
            if(mm.equalsIgnoreCase("04")){
                Month="April";

            }
            if(mm.equalsIgnoreCase("05")){
                Month="May";

            }
            if(mm.equalsIgnoreCase("06")){
                Month="June";
            }
            if(mm.equalsIgnoreCase("07")){
                Month="July";

            }
            if(mm.equalsIgnoreCase("08")){
                Month="Aug";
            }
            if(mm.equalsIgnoreCase("09")){
                Month="Sep";
            }
            if(mm.equalsIgnoreCase("10")){
                Month="Oct";
            }
            if(mm.equalsIgnoreCase("11")){
                Month="Nov";
            }
            if(mm.equalsIgnoreCase("12")){
                Month="Dec";
            }
                dob.setText(Month+", "+dd+", "+yy);
        }

        if(Appointment!=null){
            String []DateandTime=Appointment.split(" ");
            String Date=DateandTime[0];
            String [] Datediv=Date.split("-");
            final   String yy=Datediv[0];
            final   String mm=Datediv[1];
            final   String dd=Datediv[2];
            final   int yy1= Integer.parseInt(Datediv[0]);
            final   int mm1= Integer.parseInt(Datediv[1]);
            final   int dd1= Integer.parseInt(Datediv[2]);
            String Month="";
            if(mm.equalsIgnoreCase("01")){
                Month="Jan";
            }
            if(mm.equalsIgnoreCase("02")){
                Month="Feb";

            }
            if(mm.equalsIgnoreCase("03")){
                Month="March";

            }
            if(mm.equalsIgnoreCase("04")){
                Month="April";

            }
            if(mm.equalsIgnoreCase("05")){
                Month="May";

            }
            if(mm.equalsIgnoreCase("06")){
                Month="June";
            }
            if(mm.equalsIgnoreCase("07")){
                Month="July";

            }
            if(mm.equalsIgnoreCase("08")){
                Month="Aug";
            }
            if(mm.equalsIgnoreCase("09")){
                Month="Sep";
            }
            if(mm.equalsIgnoreCase("10")){
                Month="Oct";
            }
            if(mm.equalsIgnoreCase("11")){
                Month="Nov";
            }
            if(mm.equalsIgnoreCase("12")){
                Month="Dec";
            }
            appointment.setText(Month+", "+dd+", "+yy);
        }
        if(Mobile!=null){
           mobile.setText(Mobile);
        }
        if(Empcode!=null){
            empcode.setText(Empcode);
        }
        if(Email!=null){
            email.setText(Email);
        }
/*
        findViewById(R.id.changePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),VerifyMobileNumberActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });
*/
    }
}
