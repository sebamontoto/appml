package com.example.appml.home.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class SearchResponse {

    @NonNull
    private ArrayList<Product> results;

    @NonNull
    public ArrayList<Product> getResults() {
        return results;
    }
}
