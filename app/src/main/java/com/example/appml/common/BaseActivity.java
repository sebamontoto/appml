package com.example.appml.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appml.R;

/**
 * Clase abstracta parametrizada encargada de aportar comportamiento a las clases heredadas
 *
 * @param <T> tipo de dato que hereda de BaseViewModel
 */
public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {

    private T viewModel; // en T recibe el viewmodel especifico de cada activity?
    private ViewGroup baseLayoutView;
    private ViewGroup baseLayoutError;
    private ViewGroup baseLayoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        baseLayoutView = findViewById(R.id.baseLayoutView);
        baseLayoutError = findViewById(R.id.baseLayoutError);
        baseLayoutLoading = findViewById(R.id.baseLayoutLoading);

        baseLayoutLoading.addView(LayoutInflater.from(this).inflate(R.layout.base_loading_view, null));

        viewModel = createViewModel();
        viewModel.viewStateLiveData.observe(this, new Observer<ViewState>() {
            @Override
            public void onChanged(ViewState viewState) {

                switch (viewState.getState()){
                    case ERROR:
                        baseLayoutView.setVisibility(View.GONE);
                        baseLayoutLoading.setVisibility(View.GONE);
                        baseLayoutError.setVisibility(View.VISIBLE);
                        break;

                    case LAYOUT:
                        baseLayoutView.setVisibility(View.VISIBLE);
                        baseLayoutLoading.setVisibility(View.GONE);
                        baseLayoutError.setVisibility(View.GONE);
                        break;

                    case LOADING:
                        baseLayoutView.setVisibility(View.GONE);
                        baseLayoutLoading.setVisibility(View.VISIBLE);
                        baseLayoutError.setVisibility(View.GONE);
                        break;
                }
            }
        });

        baseLayoutView.addView(getLayoutView());
    }


    protected abstract T createViewModel();

    protected abstract View getLayoutView();

    protected T getViewModel(){
        return viewModel;
    }


}