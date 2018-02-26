package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nozzle {

@SerializedName("Nozzle_Number")
@Expose
private String nozzleNumber;
@SerializedName("id")
@Expose
private String id;

public String getNozzleNumber() {
return nozzleNumber;
}

public void setNozzleNumber(String nozzleNumber) {
this.nozzleNumber = nozzleNumber;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}