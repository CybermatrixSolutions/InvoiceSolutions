package com.cybermatrixsolutions.invoicesolutions.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Dell on 07/01/2017.
 */

public  class PrefsManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "garuda";
    private static final String Personnel_Name = "Personnel_Name";
    private static final String Designation_Name = "Designation_Name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "Mobile";
    private static final String USER_ID = "id";
    private static final String Date_of_Birth = "Date_of_Birth";
    private static final String Date_of_Appointment = "Date_of_Appointment";
    private static final String Employeecode = "Employeecode";
    private static final String KEY_STATE = "state";
    private static final String KEY_PINCODE = "pincode";
    private static final String KEY_pump_legal_name = "pump_legal_name";
    private static final String KEY_pump_address = "pump_address";
    private static final String KEY_address_2 = "address_2";
    private static final String KEY_address_3 = "address_3";
    private static final String KEY_city = "city";
    private static final String KEY_state = "state";
    private static final String KEY_gstcode = "gstcode";
    private static final String KEY_pin_code = "pin_code";
    private static final String KEY_customer_care = "customer_care";
    private static final String KEY_phone_no = "phone_no";
    private static final String KEY_mobile = "mobile";
    private static final String KEY_VAT_TIN = "VAT_TIN";
    private static final String KEY_GST_TIN = "GST_TIN";
    private static final String KEY_invoice_prefix = "invoice_prefix";
    private static final String KEY_PAN_No = "PAN_No";
    private static final String GST_PERFIX = "gst_prefix";
    private static final String Usertype = "usertype";

    private static final String TC_Delivery_Slip = "TC_Delivery_Slip";
    private static final String TC_Delivery_Slip2 = "TC_Delivery_Slip2";
    private static final String TC_for_GST_Invoice_Slip = "TC_for_GST_Invoice_Slip";
    private static final String TC_for_GST_Invoice_Slip2 = "TC_for_GST_Invoice_Slip2";









    private static final String LOGIN = "login";
    public PrefsManager(Context context) {
        _context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void clearAllData() {
       editor.remove("key");
       editor.commit();
    }

    public void createLogin(String id, String personnel_Name, String designation_Name, String email, String mobile,
                            String appointment, String empcode, String dob,String state,String PINCODE,String legal_name
            ,String address,String address_2,String address_3,String city,String state1,String gstcode,String pin_code,String customer_care
            ,String phone_no,String mobile1,String VAT_TIN,String GST_TIN,String invoice_prefix,String PAN_No,String Gst_prefix,String TC_Delivery_Slip3,String TC_Delivery_Slip4
            ,String TC_for_GST_Invoice_Slip3 ,String TC_for_GST_Invoice_Slip4) {


        editor.putString(Personnel_Name, personnel_Name);
        editor.putString(Designation_Name, designation_Name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(USER_ID, id);
        editor.putString(Date_of_Appointment, appointment);
        editor.putString(Employeecode, empcode);
        editor.putString(Date_of_Birth,dob);
        editor.putString(KEY_STATE,state);
        editor.putString(KEY_PINCODE,PINCODE);
        editor.putString(KEY_pump_legal_name,legal_name);
        editor.putString(KEY_pump_address,address);
        editor.putString(KEY_address_2,address_2);
        editor.putString(KEY_address_3,address_3);
        editor.putString(KEY_city,city);
        editor.putString(KEY_state,state1);
        editor.putString(KEY_gstcode,gstcode);
        editor.putString(KEY_pin_code,pin_code);
        editor.putString(KEY_customer_care,customer_care);
        editor.putString(KEY_phone_no,phone_no);
        editor.putString(KEY_mobile,mobile1);
        editor.putString(KEY_VAT_TIN,VAT_TIN);
        editor.putString(KEY_GST_TIN,GST_TIN);
        editor.putString(KEY_invoice_prefix,invoice_prefix);
        editor.putString(GST_PERFIX,Gst_prefix);
        editor.putString(KEY_PAN_No,PAN_No);
        editor.putBoolean(LOGIN, true);
        editor.putString(TC_Delivery_Slip,TC_Delivery_Slip3);
        editor.putString(TC_Delivery_Slip2,TC_Delivery_Slip4);
        editor.putString(TC_for_GST_Invoice_Slip,TC_for_GST_Invoice_Slip3);
        editor.putString(TC_for_GST_Invoice_Slip2, TC_for_GST_Invoice_Slip4);



        editor.commit();
    }



    public void createLogin_driver(String Registration_Number,String Customer_Name,String pump_legal_name) {
        editor.putString("Registration_Number",Registration_Number);
        editor.putString("Customer_Name",Customer_Name);
        editor.putString("pump_legal_name",pump_legal_name);
        editor.putBoolean(LOGIN, true);
        editor.commit();
    }


    public String getRegistration_Number(){
        return pref.getString("Registration_Number",null);
    }
    public String getCustomer_Name(){
        return pref.getString("Customer_Name",null);
    }  public String getpump_legal_name(){
        return pref.getString("pump_legal_name",null);
    }


    public void setKey(String key){
        editor.putString("key",key);
        editor.commit();
    }
    public void setid(String id){
        editor.putString("id",id);
        editor.commit();
    }
    public void setCustomer_Code(String Code){
        editor.putString("setCustomer_Code",Code);
        editor.commit();
    }
    public void setCustomer_Name(String Customer_Name){
        editor.putString("Customer_Name",Customer_Name);
        editor.commit();
    }
    public void setpump_legal_name(String pump_legal_name){
        editor.putString("pump_legal_name",pump_legal_name);
        editor.commit();
    }


    public String getCustomer_Code(){
        return pref.getString("setCustomer_Code",null);
    }

    public void setUser_type(String user_type){
        editor.putString(Usertype,user_type);
        editor.commit();
    }
    public void setIMEI(String imei){
        editor.putString("imei",imei);
        editor.commit();
    }
    public  void setLogin(boolean login) {
        editor.putBoolean(LOGIN, login);
        editor.commit();
    }

    public String getIMEI(String imei){
        return pref.getString("imei",null);
    }
    public String getfueltc1(){
        return pref.getString(TC_Delivery_Slip,null);
    } public String getfueltc2(){
        return pref.getString(TC_Delivery_Slip2,null);
    } public String getgsttc1(){
        return pref.getString(TC_for_GST_Invoice_Slip,null);
    } public String getgsttc2(){
        return pref.getString(TC_for_GST_Invoice_Slip2,null);
    }
    public String getMobile(){
        return pref.getString(KEY_MOBILE,null);
    }
    public void setMobile(String mobile){
        editor.putString(KEY_MOBILE, mobile);
        editor.commit();
    }
    public String getUsertype(){
        return pref.getString(Usertype,null);
    } public String getid(){
        return pref.getString("id",null);
    }

    public String getKey(String key){
         return pref.getString("key",null);
    }
    public String getdisignation(){
        return pref.getString(Designation_Name,null);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(LOGIN, false);
    }
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> profile = new HashMap<>();
        profile.put("Personnel_Name", pref.getString(Personnel_Name, null));
        profile.put("Designation_Name", pref.getString(Designation_Name, null));
        profile.put("email", pref.getString(KEY_EMAIL, null));
        profile.put("mobile", pref.getString(KEY_MOBILE, null));
        profile.put("id", pref.getString(USER_ID, null));
        profile.put("Date_of_Appointment", pref.getString(Date_of_Appointment, null));
        profile.put("Employeecode", pref.getString(Employeecode, null));
        profile.put("Date_of_Birth", pref.getString(Date_of_Birth, null));
        profile.put(KEY_STATE,pref.getString(KEY_STATE, null));
        profile.put(KEY_PINCODE,pref.getString(KEY_PINCODE, null));
        profile.put(KEY_pump_legal_name,pref.getString(KEY_pump_legal_name, null));
        profile.put(KEY_pump_address,pref.getString(KEY_pump_address, null));
        profile.put(KEY_address_2,pref.getString(KEY_address_2, null));
        profile.put(KEY_address_3,pref.getString(KEY_address_3, null));
        profile.put(KEY_city,pref.getString(KEY_city, null));
        profile.put(KEY_state,pref.getString(KEY_state, null));
        profile.put(KEY_gstcode,pref.getString(KEY_gstcode, null));
        profile.put(KEY_pin_code,pref.getString(KEY_pin_code, null));
        profile.put(KEY_customer_care,pref.getString(KEY_customer_care, null));
        profile.put(KEY_phone_no,pref.getString(KEY_phone_no, null));
        profile.put(KEY_mobile,pref.getString(KEY_mobile, null));
        profile.put(KEY_VAT_TIN,pref.getString(KEY_VAT_TIN, null));
        profile.put(KEY_GST_TIN,pref.getString(KEY_GST_TIN, null));
        profile.put(KEY_invoice_prefix,pref.getString(KEY_invoice_prefix, null));
        profile.put(KEY_PAN_No,pref.getString(KEY_PAN_No, null));
        profile.put(GST_PERFIX,pref.getString(GST_PERFIX, null));



         return profile;
    }

}
