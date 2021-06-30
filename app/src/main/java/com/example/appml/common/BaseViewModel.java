package com.example.appml.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    private final MutableLiveData<ViewState> viewState;
    public LiveData<ViewState> viewStateLiveData;

    public BaseViewModel(){
        viewState = new MutableLiveData<>();
        viewStateLiveData = viewState;
    }

    protected void setViewAsError(){
        viewState.postValue(ViewState.of(ViewState.State.ERROR));
    }

    protected void setViewAsError(String message){
        viewState.postValue(ViewState.of(ViewState.State.ERROR, message));
    }

    protected void setViewAsLoading(){
        viewState.postValue(ViewState.of(ViewState.State.LOADING));
    }

    protected void setViewAsLayout(){
        viewState.postValue(ViewState.of(ViewState.State.LAYOUT));
    }

}
