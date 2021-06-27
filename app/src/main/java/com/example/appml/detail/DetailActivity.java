package com.example.appml.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.appml.common.BaseActivity;
import com.example.appml.databinding.ActivityDetailBinding;
import com.example.appml.detail.model.ProductDetail;
import com.example.appml.home.model.Product;

public class DetailActivity extends BaseActivity<DetailViewModel> {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parseBack();
    }

    private void parseBack() {
        //Revisar esto
        Object object = getIntent().getSerializableExtra("product");
        Product product = (Product) object;

        getViewModel().fetchItemDetails(product.getId());

        getViewModel().getProductDetails().observe(this, new Observer<ProductDetail>() {
            @Override
            public void onChanged(ProductDetail productDetail) {

                //Revisar
                binding.textCondition.setText(productDetail.getCondition().equals("new") ? "Nuevo" : "Usado");
                binding.textTitle.setText(productDetail.getTitle());
                binding.textPrice.setText("$" + productDetail.getPrice());
                binding.textWarranty.setText(productDetail.getWarranty());
                binding.textQuantity.setText("Cantidad disponible: " + productDetail.getAvailableQuantity());
                Glide.with(DetailActivity.this)
                        .load(productDetail.getThumbnail())
                        .into(binding.imageView);
            }
        });
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