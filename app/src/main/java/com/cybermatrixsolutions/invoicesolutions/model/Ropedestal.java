package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ropedestal implements Serializable{

@SerializedName("status")
@Expose
private String status;
@SerializedName("data")
@Expose
private PedestalData data;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public PedestalData getData() {
return data;
}

public void setData(PedestalData data) {
this.data = data;
}

}