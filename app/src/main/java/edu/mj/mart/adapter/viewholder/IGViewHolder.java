package edu.mj.mart.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import edu.mj.mart.databinding.ItemImportGoodsBinding;
import edu.mj.mart.model.ImportGoods;

public class IGViewHolder extends RecyclerView.ViewHolder {

    private final ItemImportGoodsBinding binding;

    public IGViewHolder(ItemImportGoodsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ImportGoods ig) {
        binding.tvNameProduct.setText(ig.getProduct().getName());
        binding.tvQuantity.setText(String.valueOf(ig.getQuantity()));
        binding.tvPurchasePrice.setText(String.valueOf(ig.getProduct().getPurchasePrice()));
        long totalAmount = ig.getProduct().getPurchasePrice() * ig.getQuantity();
        binding.tvTotalAmount.setText(String.valueOf(totalAmount));
    }
}
