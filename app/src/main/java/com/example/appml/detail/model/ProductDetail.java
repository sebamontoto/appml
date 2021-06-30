package com.example.appml.detail.model;

import com.example.appml.home.model.Shipping;

import java.util.List;

public class ProductDetail {

    private String id;
    private String title;
    private String sellerId;
    private float price;
    private float basePrice;
    private float originalPrice;
    private int availableQuantity;
    private int soldQuantity;
    private String condition;
    private String thumbnail;
    private List<Pictures> pictures;
    private boolean acceptsMercadoPago;
    private Shipping shipping;
    private String warranty;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSellerId() {
        return sellerId;
    }

    public float getPrice() {
        return price;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public String getCondition() {
        return condition;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean isAcceptsMercadoPago() {
        return acceptsMercadoPago;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public String getWarranty() {
        return warranty;
    }

    public List<Pictures> getPictures() {
        return pictures;
    }

}
