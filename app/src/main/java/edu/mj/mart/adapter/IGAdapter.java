package edu.mj.mart.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.mj.mart.adapter.viewholder.IGViewHolder;
import edu.mj.mart.databinding.ItemImportGoodsBinding;
import edu.mj.mart.model.ImportGoods;

public class IGAdapter extends RecyclerView.Adapter<IGViewHolder> {

    private List<ImportGoods> igs;

    public IGAdapter(List<ImportGoods> igs) {
        this.igs = igs;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<ImportGoods> igs) {
        this.igs = igs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IGViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImportGoodsBinding binding = ItemImportGoodsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new IGViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IGViewHolder holder, int position) {
        if (position < igs.size()) {
            holder.bind(igs.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return igs != null ? igs.size() : 0;
    }
}
