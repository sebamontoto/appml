package com.example.appml.detail.services;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailService {

    @GET("items/{itemId}")
    Observable <Response<Object>> getItemDetails(@Path("itemId") @NonNull final String itemId);
}
