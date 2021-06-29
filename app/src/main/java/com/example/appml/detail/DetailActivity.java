package com.example.appml.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.appml.R;
import com.example.appml.common.BaseActivity;
import com.example.appml.databinding.ActivityDetailBinding;
import com.example.appml.detail.model.ProductDetail;
import com.example.appml.home.model.Product;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity<DetailViewModel> {

    private ActivityDetailBinding binding;
    public static final String KEY_PRODUCT = "product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parseBack();
    }

    @Override
    protected void retry() {
    }

    private void parseBack() {
        Product product = (Product) getIntent().getSerializableExtra(KEY_PRODUCT);

        getViewModel().fetchItemDetails(product.getId());

        getViewModel().getProductDetails().observe(this, new Observer<ProductDetail>() {
            @Override
            public void onChanged(ProductDetail productDetail) {

                binding.textCondition.setText(productDetail.getCondition().equals("new")
                        ? getString(R.string.nuevo) : getString(R.string.usado));
                binding.textSoldQuantity.setText(productDetail.getSoldQuantity() + " " + getString(R.string.sold));
                binding.textTitle.setText(productDetail.getTitle());
                binding.textPrice.setText(getString(R.string.signo_pesos) + productDetail.getPrice());
                binding.textWarranty.setText(productDetail.getWarranty());
                binding.textQuantity.setText(getString(R.string.avaliable_quantity) + productDetail.getAvailableQuantity());
                /*Glide.with(DetailActivity.this)
                        .load(productDetail.getPictures().get(1).getUrl())
                        .into(binding.imageView);*/

                binding.carousel.registerLifecycle(getLifecycle());

                List<CarouselItem> list = new ArrayList<>();

                productDetail.getPictures().forEach(picture -> list.add(new CarouselItem(picture.getUrl())));

                binding.carousel.setData(list);
            }
        });



        getViewModel().fetchItemDescription(product.getId());

        getViewModel().getProductDescription().observe(this, new Observer<ProductDetail>() {
            @Override
            public void onChanged(ProductDetail productDetail) {
                binding.textDescription.setText("Description: " + productDetail.getDescription());
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