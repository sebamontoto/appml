package com.example.appml.detail;

import android.util.Log;

import com.example.appml.common.BaseViewModel;
import com.example.appml.common.networking.ServiceBuilder;
import com.example.appml.detail.services.DetailService;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DetailViewModel extends BaseViewModel {

    private String TAG = "DetailViewModel";

    DetailService detailService;

    public DetailViewModel(){
        setViewAsLayout();
        detailService = ServiceBuilder.createService(DetailService.class);
    }

    public void fetchItemDetails(String productId){
        detailService.getItemDetails(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableObserver<Response<Object>>() {
                    @Override
                    public void onNext(Response<Object> value) {
                        Log.i(TAG, "onNext: " + value.body());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
