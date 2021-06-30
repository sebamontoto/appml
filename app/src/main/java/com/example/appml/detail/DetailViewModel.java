package com.example.appml.detail;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appml.common.BaseViewModel;
import com.example.appml.common.networking.ServiceBuilder;
import com.example.appml.detail.model.ProductDetail;
import com.example.appml.detail.services.DetailService;
import com.example.appml.home.model.SearchResponse;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DetailViewModel extends BaseViewModel {

    private String TAG = "DetailViewModel";

    DetailService detailService;
    private MutableLiveData<ProductDetail> productDetails;
    private MutableLiveData<ProductDetail> productDescription;

    public DetailViewModel(){
        setViewAsLayout();
        detailService = ServiceBuilder.createService(DetailService.class);
        productDetails = new MutableLiveData<>();
        productDescription = new MutableLiveData<>();
    }

    public LiveData<ProductDetail> getProductDetails(){
        return productDetails;
    }

    public LiveData<ProductDetail> getProductDescription(){
        return productDescription;
    }

    public void fetchItemDetails(String productId){
        setViewAsLoading();

        detailService.getItemDetails(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableObserver<Response<ProductDetail>>() {
                    @Override
                    public void onNext(Response<ProductDetail> value) {
                        Log.i(TAG, "onNext: " + value.body());
                        productDetails.postValue(value.body());

                        fetchItemDescription(productId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: " );
                    }
                });
    }

    public void fetchItemDescription(String productId){
        detailService.getItemDescription(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableObserver<Response<ProductDetail>>() {
                    @Override
                    public void onNext(Response<ProductDetail> value) {
                        Log.i(TAG, "onNextfetchItemDescription: " + value.body());
                        productDescription.postValue(value.body());
                        setViewAsLayout();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }
}
