package com.example.appml.detail.services;

import androidx.annotation.NonNull;

import com.example.appml.detail.model.ProductDescription;
import com.example.appml.detail.model.ProductDetail;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailService {

    /**
     * Method used for the details of a specific product
     * @param itemId product identifier
     * @return ProductDetail object
     */
    @GET("items/{itemId}")
    Observable <Response<ProductDetail>> getItemDetails(@Path("itemId") @NonNull final String itemId);

    /**
     * Method used for the description of a specific product
     * @param itemId product identifier
     * @return ProductDescription object
     */
    @GET("items/{itemId}/description")
    Observable <Response<ProductDescription>> getItemDescription(@Path("itemId") @NonNull final String itemId);
}
