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
import edu.mj.mart.databinding.ItemSupplierBinding;
import edu.mj.mart.model.Supplier;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder> {

    private List<Supplier> suppliers;
    private final OnListenerItem<Supplier> onListenerItem;

    public SupplierAdapter(List<Supplier> suppliers, OnListenerItem<Supplier> onListenerItem) {
        this.suppliers = suppliers;
        this.onListenerItem = onListenerItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<Supplier> suppliers) {
        this.suppliers = suppliers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSupplierBinding binding = ItemSupplierBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SupplierViewHolder(binding, onListenerItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierViewHolder holder, int position) {
        if (position >= suppliers.size()) return;
        holder.bind(suppliers.get(position));
    }

    @Override
    public int getItemCount() {
        if (suppliers != null) return suppliers.size();
        return 0;
    }

    static class SupplierViewHolder extends RecyclerView.ViewHolder {

        private final ItemSupplierBinding binding;
        private final Context context;
        private final OnListenerItem<Supplier> onListenerItem;

        public SupplierViewHolder(ItemSupplierBinding binding, OnListenerItem<Supplier> onListenerItem) {
            super(binding.getRoot());
            this.binding = binding;
            this.onListenerItem = onListenerItem;
            context = binding.getRoot().getContext();
        }

        public void bind(Supplier supplier) {
            binding.tvName.setText(supplier.getName());
            binding.tvId.setText(context.getString(R.string.description_ci, supplier.getPhone()));

            binding.ivCall.setOnClickListener(view -> {
                if (onListenerItem != null) {
                    onListenerItem.onCallSupplier(supplier);
                }
            });
            binding.getRoot().setOnClickListener(view -> {
                if (onListenerItem != null) {
                    onListenerItem.onClickItem(supplier);
                }
            });
        }
    }
}
