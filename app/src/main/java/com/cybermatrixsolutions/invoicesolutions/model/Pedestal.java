package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pedestal implements Serializable{

@SerializedName("id")
@Expose
private String id;
@SerializedName("Pedestal_Number")
@Expose
private String pedestalNumber;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getPedestalNumber() {
return pedestalNumber;
}

public void setPedestalNumber(String pedestalNumber) {
this.pedestalNumber = pedestalNumber;
}

}