package com.example.appml.detail;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appml.common.BaseViewModel;
import com.example.appml.common.Command;
import com.example.appml.common.networking.ServiceBuilder;
import com.example.appml.detail.model.ProductDescription;
import com.example.appml.detail.model.ProductDetail;
import com.example.appml.detail.services.DetailService;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DetailViewModel extends BaseViewModel {

    private String TAG = "DetailViewModel";

    private Command command;

    private ProductDetail productDetail = null;
    private ProductDescription productDescription = null;

    DetailService detailService;

    private MutableLiveData<ProductDetail> detailsState;
    private MutableLiveData<ProductDescription> descriptionState;

    public DetailViewModel(){
        setViewAsLayout();
        detailService = ServiceBuilder.createService(DetailService.class);
        detailsState = new MutableLiveData<>();
        descriptionState = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        command = null;
    }

    public LiveData<ProductDetail> getDetailsState(){
        return detailsState;
    }

    public LiveData<ProductDescription> getDescriptionState(){
        return descriptionState;
    }

    public void fetchItemDetails(String productId){
        if(command == null){
            command = new Command() {
                @Override
                public void execute() {
                    fetchItemDetailsInternals(productId);
                }
            };
        }
        command.execute();
    }


    public void fetchItemDetailsInternals(String productId){
        setViewAsLoading();

        detailService.getItemDetails(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableObserver<Response<ProductDetail>>() {
                    @Override
                    public void onNext(Response<ProductDetail> value) {
                        Log.i(TAG, "onNext: " + value.body());

                        if(value.isSuccessful()){
                            productDetail = value.body();
                            detailsState.postValue(productDetail);
                            fetchItemDescription(productId);
                            command = null;
                        } else {
                            setViewAsError("Un error ha ocurrido");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e);
                        setViewAsError("Revisa tu conexión a internet");
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
                .subscribe(new DisposableObserver<Response<ProductDescription>>() {
                    @Override
                    public void onNext(Response<ProductDescription> value) {
                        Log.i(TAG, "onNextfetchItemDescription: " + value.body());

                        productDescription = value.body();
                        descriptionState.postValue(productDescription);
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

    public void retry(){
        if(command != null){
            command.execute();
        }
    }

    @Nullable
    public ProductDetail getProductDetail() {
        return productDetail;
    }

    @Nullable
    public ProductDescription getProductDescription() {
        return productDescription;
    }
}
