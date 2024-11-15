package edu.mj.mart.adapter;

import static edu.mj.mart.utils.SyntheticEnum.Role.STAFF;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.mj.mart.R;
import edu.mj.mart.databinding.ItemEmployeeBinding;
import edu.mj.mart.model.Account;
import edu.mj.mart.model.Employee;
import edu.mj.mart.utils.ImageUtil;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> accounts;
    private final OnListenerItem<Employee> onListenerItem;

    public EmployeeAdapter(List<Employee> accounts, OnListenerItem<Employee> onListenerItem) {
        this.accounts = accounts;
        this.onListenerItem = onListenerItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<Employee> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEmployeeBinding binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EmployeeViewHolder(binding, onListenerItem);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        if (position >= accounts.size()) return;
        holder.bind(accounts.get(position));
    }

    @Override
    public int getItemCount() {
        if (accounts != null) return accounts.size();
        return 0;
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private final ItemEmployeeBinding binding;
        private final Context context;
        private final OnListenerItem<Employee> onListenerItem;

        public EmployeeViewHolder(ItemEmployeeBinding binding, OnListenerItem<Employee> onListenerItem) {
            super(binding.getRoot());
            this.binding = binding;
            this.onListenerItem = onListenerItem;
            context = binding.getRoot().getContext();
        }

        public void bind(Employee account) {
            if (account.getRole() == STAFF.value) {
                binding.tvRole.setText(context.getString(R.string.title_staff));
            } else {
                binding.tvRole.setText(context.getString(R.string.title_manager));
            }
            binding.tvFullName.setText(account.getFullName());
            binding.tvPhone.setText(context.getString(R.string.phone_number, account.getPhone()));

            if (account.getAvatar() == null || account.getAvatar().isEmpty()) {
                binding.ivAvatar.setImageResource(R.drawable.icon_profile_default);
            } else {
                StringBuilder ima = new StringBuilder();
                for (String s : account.getAvatar()) {
                    ima.append(s);
                }
                if (ima.toString().isEmpty()) {
                    binding.ivAvatar.setImageResource(R.drawable.icon_profile_default);
                } else {
                    binding.ivAvatar.setImageBitmap(ImageUtil.decode(ima.toString()));
                }
            }

            binding.getRoot().setOnClickListener(v -> {
                if (onListenerItem != null) {
                    onListenerItem.onClickItem(account);
                }
            });
        }
    }
}
