package com.example.appml.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appml.R;

/**
 * Parameterized abstract class responsible for providing behavior to inherited classes
 * @param <T> data type inheriting from BaseViewModel
 */
public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {

    private T viewModel;
    private ViewGroup baseLayoutView;
    private ViewGroup baseLayoutError;
    private ViewGroup baseLayoutLoading;

    private Button buttonRetry;
    private TextView textError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        baseLayoutView = findViewById(R.id.baseLayoutView);
        baseLayoutError = findViewById(R.id.baseLayoutError);
        baseLayoutLoading = findViewById(R.id.baseLayoutLoading);

        View errorView = LayoutInflater.from(this).inflate(R.layout.base_error_view, null);
        buttonRetry = errorView.findViewById(R.id.buttonRetry);
        textError = errorView.findViewById(R.id.textTitle);

        baseLayoutLoading.addView(LayoutInflater.from(this).inflate(R.layout.base_loading_view, null));
        baseLayoutError.addView(errorView);

        viewModel = createViewModel();
        viewModel.viewStateLiveData.observe(this, new Observer<ViewState>() {
            @Override
            public void onChanged(ViewState viewState) {

                switch (viewState.getState()){
                    case ERROR:
                        String message = viewState.getMessage();
                        textError.setText(message);
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

        buttonRetry.setOnClickListener(l -> retry());
    }

    protected abstract void retry();

    protected abstract T createViewModel();

    protected abstract View getLayoutView();

    protected T getViewModel(){
        return viewModel;
    }


}