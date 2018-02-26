package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abcd on 12/5/2017.
 */

public class LubeType implements Serializable{
    @SerializedName("Item_Name")
    private String name;

    @SerializedName("id")
    private String code;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    }
