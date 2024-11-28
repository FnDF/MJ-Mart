package edu.mj.mart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.mj.mart.R;
import edu.mj.mart.databinding.ItemProductBinding;
import edu.mj.mart.model.Product;
import edu.mj.mart.utils.ImageUtil;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private final OnListenerItem<Product> onListenerItem;

    public ProductAdapter(List<Product> products, OnListenerItem<Product> onListenerItem) {
        this.products = products;
        this.onListenerItem = onListenerItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding, onListenerItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (position >= products.size()) return;
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        if (products != null) return products.size();
        return 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ItemProductBinding binding;
        private final Context context;
        private final OnListenerItem<Product> onListenerItem;

        public ProductViewHolder(ItemProductBinding binding, OnListenerItem<Product> onListenerItem) {
            super(binding.getRoot());
            this.binding = binding;
            this.onListenerItem = onListenerItem;
            context = binding.getRoot().getContext();
        }

        public void bind(Product product) {
            binding.tvName.setText(product.getName());
            binding.tvId.setText(context.getString(R.string.description_product, product.getId()));

            if (product.getImages() != null && !product.getImages().isEmpty()) {
                StringBuilder ima = new StringBuilder();
                for (String s : product.getImages()) {
                    ima.append(s);
                }
                if (ima.toString().isEmpty()) {
                    binding.ivProduct.setImageResource(R.drawable.logo_no_background);
                } else {
                    binding.ivProduct.setImageBitmap(ImageUtil.decode(ima.toString()));
                }
            } else {
                binding.ivProduct.setImageResource(R.drawable.avatar_default);
            }

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onListenerItem != null) {
                        onListenerItem.onClickItem(product);
                    }
                }
            });
        }
    }
}
