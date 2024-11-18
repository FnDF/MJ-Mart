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
import edu.mj.mart.databinding.ItemCiBinding;
import edu.mj.mart.model.CI;

public class CIAdapter extends RecyclerView.Adapter<CIAdapter.CIViewHolder> {

    private List<CI> cis;
    private final OnListenerItem<CI> onListenerItem;

    public CIAdapter(List<CI> cis, OnListenerItem<CI> onListenerItem) {
        this.cis = cis;
        this.onListenerItem = onListenerItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<CI> cis) {
        this.cis = cis;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCiBinding binding = ItemCiBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CIViewHolder(binding, onListenerItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CIViewHolder holder, int position) {
        if (position >= cis.size()) return;
        holder.bind(cis.get(position));
    }

    @Override
    public int getItemCount() {
        if (cis != null) return cis.size();
        return 0;
    }

    static class CIViewHolder extends RecyclerView.ViewHolder {

        private final ItemCiBinding binding;
        private final Context context;
        private final OnListenerItem<CI> onListenerItem;

        public CIViewHolder(ItemCiBinding binding, OnListenerItem<CI> onListenerItem) {
            super(binding.getRoot());
            this.binding = binding;
            this.onListenerItem = onListenerItem;
            context = binding.getRoot().getContext();
        }

        public void bind(CI ci) {
            binding.tvName.setText(ci.getName());
            binding.tvId.setText(context.getString(R.string.description_ci, ci.getId()));

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onListenerItem != null) {
                        onListenerItem.onClickItem(ci);
                    }
                }
            });
        }
    }
}
