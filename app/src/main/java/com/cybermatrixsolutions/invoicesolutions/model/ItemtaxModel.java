package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/11/2018.
 */

public class ItemtaxModel {

    @SerializedName("id")
    private String id;
    @SerializedName("Item_Name")
    private String Item_Name;
    @SerializedName("price")
    private String price;
    @SerializedName("GST_type")
    private String tax_type;
    @SerializedName("Tax_percentage")
    private String Tax_percentage;
    @SerializedName("Description")
    private String Description;
    @SerializedName("tax_name")
    private String tax_name;
    @SerializedName("strate_type")
    private String strate_type;
    @SerializedName("hsncode")
    private String hsncode;
    @SerializedName("position")
    private String position;
    public String getId() {
        return id;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public String getPrice() {
        return price;
    }

    public String getTax_type() {
        return tax_type;
    }

    public String getTax_percentage() {
        return Tax_percentage;
    }

    public String getDescription() {
        return Description;
    }

    public String getTax_name() {
        return tax_name;
    }

    public String getStrate_type() {
        return strate_type;
    }

    public String getHsncode() {
        return hsncode;
    }

    public String getPosition() {
        return position;
    }



}
