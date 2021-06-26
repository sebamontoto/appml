package com.example.appml.home.model;

import java.io.Serializable;

public class Product  implements Serializable {

    private String id;
    private String title;
    private String thumbnail;
    float price;
    Shipping shipping;

    public String getThumbnail() {
        return thumbnail;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public Shipping getShipping() {
        return shipping;
    }
}
