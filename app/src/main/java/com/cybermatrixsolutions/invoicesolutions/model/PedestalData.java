package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PedestalData implements Serializable{

@SerializedName("msg")
@Expose
private String msg;

@SerializedName("key")
@Expose
private String key;
@SerializedName("Pedestal")
@Expose
private List<Pedestal> pedestal = null;

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public String getKey() {
return key;
}

public void setKey(String key) {
this.key = key;
}

public List<Pedestal> getPedestal() {
return pedestal;
}

public void setPedestal(List<Pedestal> pedestal) {
this.pedestal = pedestal;
}

}