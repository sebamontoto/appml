package com.example.appml.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.example.appml.common.BaseActivity;
import com.example.appml.databinding.ActivityHomeBinding;
import com.example.appml.detail.DetailActivity;
import com.example.appml.home.adapter.AdapterListProducts;
import com.example.appml.home.model.Product;

import java.util.List;

public class HomeActivity extends BaseActivity<HomeViewModel> {

    private ActivityHomeBinding binding;
    private AdapterListProducts adapterListProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Para poblar la pantalla de entrada
        if(getViewModel().getProductsList() == null){
            getViewModel().fetchProducts("4k");
        }

        adapterListProducts = new AdapterListProducts(item -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.KEY_PRODUCT, item);
            startActivity(intent);
        });

        initRecyclerView();

        getViewModel().getSearchState().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapterListProducts.setListData(products);
            }
        });

        binding.editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getViewModel().fetchProducts(binding.editTextSearch.getText().toString());

                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return true;
            }
            return  false;
        });
    }

    @Override
    protected void retry() {
        getViewModel().retry();
    }

    private void initRecyclerView() {
        binding.recyclerProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerProducts.setHasFixedSize(true);
        binding.recyclerProducts.setAdapter(adapterListProducts);
    }

    @Override
    protected View getLayoutView() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected HomeViewModel createViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }


}