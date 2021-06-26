package com.example.appml.detail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appml.R;
import com.example.appml.common.BaseActivity;
import com.example.appml.databinding.ActivityDetailBinding;
import com.example.appml.home.model.Product;

public class DetailActivity extends BaseActivity<DetailViewModel> {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getViewModel().fetchItemDetails("121212");

        parseBack();
    }

    private void parseBack() {
        Object object = getIntent().getSerializableExtra("product");
        Product product = (Product) object;
        Toast.makeText(this, product.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected View getLayoutView() {
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected DetailViewModel createViewModel() {
        return new ViewModelProvider(this).get(DetailViewModel.class);
    }

}