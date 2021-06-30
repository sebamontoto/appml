package com.example.appml.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appml.common.BaseViewModel;
import com.example.appml.common.Command;
import com.example.appml.common.networking.ServiceBuilder;
import com.example.appml.home.model.Product;
import com.example.appml.home.model.SearchResponse;
import com.example.appml.home.services.HomeService;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModel {

    private final String TAG = "HomeViewModel";

    private Command command;
    private List<Product> productsList = null;
    private final HomeService homeService;
    private final MutableLiveData<List<Product>> searchState;

    public HomeViewModel() {
        setViewAsLayout();
        homeService = ServiceBuilder.createService(HomeService.class);
        searchState = new MutableLiveData<>();
    }

    public LiveData<List<Product>> getSearchState (){
        return searchState;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        command = null;
    }

    public void fetchProducts(@NonNull String product){
        if(command == null){
            command = new Command() {
                @Override
                public void execute() {
                    fetchProductsInternals(product);
                }
            };
        }
        command.execute();
    }

    private void fetchProductsInternals(String product){
        setViewAsLoading();

        homeService.getProductsFromSearch(product)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableObserver<Response<SearchResponse>>() {
                    @Override
                    public void onNext(Response value) {
                        Log.i(TAG, "onNext: " + value.body());

                        if(value.isSuccessful()){
                            productsList = ((SearchResponse) value.body()).getResults();
                            searchState.postValue(productsList);
                            setViewAsLayout();
                            command = null;
                        } else {
                            setViewAsError("Un error ha ocurrido");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e);
                        setViewAsError("Revisa tu conexión a Internet");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }

    public void retry() {
        if(command != null){
            command.execute();
        }
    }

    @Nullable
    public List<Product> getProductsList() {
        return productsList;
    }

}
