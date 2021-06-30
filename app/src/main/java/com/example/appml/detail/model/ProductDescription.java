package com.example.appml.detail.model;

import com.google.gson.annotations.SerializedName;

public class ProductDescription {

    @SerializedName("plain_text")
    private String description;

    public String getDescription() {
        return description;
    }
}
