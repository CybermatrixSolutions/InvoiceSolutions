package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 11/1/2017.
 */

public class LubeList implements Serializable {

    @SerializedName("Item_Code")
    private String Item_Code;

    @SerializedName("Item_Name")
    private String Item_Name;

    @SerializedName("Price")
    private String Price;

    @SerializedName("MRP")
    private String MRP;

    @SerializedName("id")
    private String id;

    @SerializedName("Volume_ltr")
    private String Volume_ltr;

    public String getId() {
        return id;
    }

    public void setItem_Code(String item_Code) {
        Item_Code = item_Code;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public void setVolume_ltr(String volume_ltr) {
        Volume_ltr = volume_ltr;
    }

    public String getMRP() {
        return MRP;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public String getItem_Code() {
        return Item_Code;
    }

    public String getVolume_ltr() {
        return Volume_ltr;
    }

    public String getPrice() {
        return Price;
    }
}
