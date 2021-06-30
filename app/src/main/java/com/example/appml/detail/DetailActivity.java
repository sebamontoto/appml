package com.example.appml.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.appml.R;
import com.example.appml.common.BaseActivity;
import com.example.appml.databinding.ActivityDetailBinding;
import com.example.appml.detail.model.ProductDescription;
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

        Product product = (Product) getIntent().getSerializableExtra(KEY_PRODUCT);

        if(getViewModel().getProductDetail() == null){
            getViewModel().fetchItemDetails(product.getId());
        }

        getViewModel().getDetailsState().observe(this, new Observer<ProductDetail>() {
            @Override
            public void onChanged(ProductDetail productDetail) {

                binding.textCondition.setText(productDetail.getCondition().equals("new")
                        ? getString(R.string.nuevo) : getString(R.string.usado));
                binding.textSoldQuantity.setText(getString(R.string.sold, productDetail.getSoldQuantity()));
                binding.textTitle.setText(productDetail.getTitle());
                binding.textPrice.setText(getString(R.string.signo_pesos, productDetail.getPrice()));

                binding.textShipping.setVisibility((productDetail.getShipping().isFreeShipping() ? View.VISIBLE : View.GONE));
                binding.textShipping.setText((productDetail.getShipping().isFreeShipping() ? getString(R.string.free_shipping) : ""));

                binding.textWarranty.setText(productDetail.getWarranty());
                binding.textQuantity.setText(getString(R.string.avaliable_quantity, productDetail.getAvailableQuantity()));

                binding.carousel.registerLifecycle(getLifecycle());

                List<CarouselItem> list = new ArrayList<>();
                productDetail.getPictures().forEach(picture -> list.add(new CarouselItem(picture.getUrl())));
                binding.carousel.setData(list);
            }
        });

        getViewModel().getDescriptionState().observe(this, new Observer<ProductDescription>() {
            @Override
            public void onChanged(ProductDescription productDescription) {
                binding.textDescription.setText(productDescription.getDescription());
            }
        });
    }

    @Override
    protected void retry() {
        getViewModel().retry();
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