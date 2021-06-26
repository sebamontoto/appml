package com.example.appml.home.services;

import com.example.appml.home.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeService {

    /**
     * Método que sirve para obtener una lista de productos que coincida con un elemento a buscar
     * @param query
     * @return
     */
    @GET("/sites/MLA/search")
    Observable<Response<SearchResponse>> getProductsFromSearch(@Query("q") String query);
}
