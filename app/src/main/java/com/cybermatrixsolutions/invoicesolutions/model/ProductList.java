package com.cybermatrixsolutions.invoicesolutions.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dhiraj on 15-07-2017.
 */

public class ProductList implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private String id;

    @SerializedName("image")
    private String image;

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
