package com.example.appml.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    private MutableLiveData<ViewState> viewState; // escritura y lectura
    public LiveData<ViewState> viewStateLiveData; // de solo lectura

    public BaseViewModel(){
        viewState = new MutableLiveData<>();
        viewStateLiveData = viewState;
    }

    protected void setViewAsError(){
        viewState.postValue(ViewState. of(ViewState.State.ERROR));
    }

    protected void setViewAsLoading(){
        viewState.postValue(ViewState.of(ViewState.State.LOADING));
    }

    protected void setViewAsLayout(){
        viewState.postValue(ViewState.of(ViewState.State.LAYOUT));
    }

}
