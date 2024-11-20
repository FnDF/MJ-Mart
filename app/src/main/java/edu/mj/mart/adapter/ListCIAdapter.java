package edu.mj.mart.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.mj.mart.R;
import edu.mj.mart.databinding.ItemListCiBinding;
import edu.mj.mart.model.CI;

public class ListCIAdapter extends RecyclerView.Adapter<ListCIAdapter.CIViewHolder> {

    private List<CI> cis;
    private final OnListenerItem<CI> onListenerItem;

    public ListCIAdapter(List<CI> cis, OnListenerItem<CI> onListenerItem) {
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
        ItemListCiBinding binding = ItemListCiBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

        private final ItemListCiBinding binding;
        private final OnListenerItem<CI> onListenerItem;

        public CIViewHolder(ItemListCiBinding binding, OnListenerItem<CI> onListenerItem) {
            super(binding.getRoot());
            this.binding = binding;
            this.onListenerItem = onListenerItem;
        }

        public void bind(CI ci) {
            binding.tvNameCI.setText(ci.getName());

            if (ci.isSelect()) {
                binding.tvNameCI.setBackgroundResource(R.drawable.bg_list_ci_selected);
            } else {
                binding.tvNameCI.setBackgroundResource(R.drawable.bg_list_ci_default);
            }

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
