package com.example.appml.common;

import androidx.annotation.NonNull;

/**
 * Class in charge of indicating the current state of the application
 */
public class ViewState {

    private final State state;
    private int statusCode;
    private String message;

    public ViewState(@NonNull final State state, final int statusCode) {
        this.state = state;
        this.statusCode = statusCode;
    }

    public ViewState(@NonNull final State state, final int statusCode, String message) {
        this.state = state;
        this.statusCode = statusCode;
        this.message = message;
    }

    public State getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    static ViewState of(@NonNull final State state){
        return new ViewState(state, 0);
    }

    static ViewState of(@NonNull final State state, String message){
        return new ViewState(state, 0, message);
    }

    enum State {
        LAYOUT, ERROR, LOADING;
    }
}
