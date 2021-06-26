package com.example.appml.common;

import androidx.annotation.NonNull;

/**
 * Clase encargada de indicar el estado actual de la aplicación
 */
public class ViewState {

    private State state;
    private int statusCode;

    public ViewState(@NonNull final State state, final int statusCode) {
        this.state = state;
        this.statusCode = statusCode;
    }

    public State getState() {
        return state;
    }

    static ViewState of(@NonNull final State state){
        return new ViewState(state, 0);
    }

    // Lista de constantes
    enum State {
        LAYOUT, ERROR, LOADING;
    }
}
