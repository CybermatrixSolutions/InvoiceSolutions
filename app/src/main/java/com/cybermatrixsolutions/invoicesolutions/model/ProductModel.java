package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Diwakar on 9/15/2017.
 */

public class ProductModel implements Serializable {

    @SerializedName("Medicine")
    String productName;

    @SerializedName("price")
    String price;

    @SerializedName("volume")
    String qty;

    @SerializedName("Item_Code")
    String Item_Code;

    public String getItem_Code() {
        return Item_Code;
    }

    public void setItem_Code(String item_Code) {
        Item_Code = item_Code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
