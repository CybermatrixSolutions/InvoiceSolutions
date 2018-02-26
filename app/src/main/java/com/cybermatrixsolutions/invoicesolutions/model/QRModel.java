package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/12/2018.
 */

public class QRModel {

    @SerializedName("qrcode")
    private String qrcode;
    public String getQrcode() {
        return qrcode;
    }


}
