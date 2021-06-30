package com.example.appml.home.services;

import com.example.appml.home.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeService {

    /**
     * Method used to obtain a list of products that matches an item to search
     * @param query product
     * @return List of products
     */
    @GET("/sites/MLA/search")
    Observable<Response<SearchResponse>> getProductsFromSearch(@Query("q") String query);
}
