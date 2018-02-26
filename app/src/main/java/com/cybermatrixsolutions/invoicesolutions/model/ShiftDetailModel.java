package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diwakar on 1/16/2018.
 */

public class ShiftDetailModel {
    @SerializedName("id")
    private String id;
    @SerializedName("starttime")
    private String starttime;
    @SerializedName("endtime")
    private String endtime;
    @SerializedName("date")
    private String date;
    public String getId() {
        return id;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getDate() {
        return date;
    }


}
