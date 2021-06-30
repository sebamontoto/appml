package com.example.appml.home.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appml.R;
import com.example.appml.home.model.Product;

import java.util.ArrayList;
import java.util.List;

public class AdapterListProducts extends RecyclerView.Adapter<AdapterListProducts.ViewHolder> {


    private static final String TAG = "AdapterListProducts";

    private List<Product> mProductsArray = new ArrayList<>();
    private final AdapterListProducts.OnItemClickListener<Product> mItemClickListener;


    public AdapterListProducts(AdapterListProducts.OnItemClickListener<Product> mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public AdapterListProducts.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.bind(mProductsArray.get(position), mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mProductsArray.size();
    }

    public void setListData(List<Product> list){
        mProductsArray = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtPrice;
        public TextView txtFreeShipping;
        public ImageView imageThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageThumbnail = itemView.findViewById(R.id.image_product_thumbnail);
            txtTitle = itemView.findViewById(R.id.text_title);
            txtPrice = itemView.findViewById(R.id.textPrice);
            txtFreeShipping = itemView.findViewById(R.id.text_free_shipping);
        }

        public void bind(final Product product, final AdapterListProducts.OnItemClickListener itemClickListener) {

            Glide.with(itemView.getContext())
                    .load(product.getThumbnail())
                    .into(imageThumbnail);

            txtTitle.setText(product.getTitle());
            txtPrice.setText("" + product.getPrice());
            txtFreeShipping.setText((product.getShipping().isFreeShipping() ? "Envío gratis" : ""));

            itemView.setOnClickListener(v -> itemClickListener.onItemClick(product));
        }
    }

    public interface OnItemClickListener <T>{
        void onItemClick(T item);
    }

}