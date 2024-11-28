package edu.mj.mart.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.mj.mart.databinding.ItemCustomerBinding;
import edu.mj.mart.model.Customer;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customers;
    private final OnCustomerListener onCustomerListener;

    public CustomerAdapter(List<Customer> customers, OnCustomerListener onCustomerListener) {
        this.customers = customers;
        this.onCustomerListener = onCustomerListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<Customer> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addDataSource(Customer customer) {
        ArrayList<Customer> newCustomers = new ArrayList<>(this.customers);
        newCustomers.add(customer);
        setDataSource(newCustomers);
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomerBinding binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomerViewHolder(binding, onCustomerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        if (position < customers.size()) {
            holder.bind(customers.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return customers != null ? customers.size() : 0;
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {

        private final ItemCustomerBinding binding;
        private final OnCustomerListener onCustomerListener;

        public CustomerViewHolder(ItemCustomerBinding binding, OnCustomerListener onCustomerListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onCustomerListener = onCustomerListener;
        }

        public void bind(Customer customer) {
            binding.tvFullName.setText(customer.getName());
            binding.tvPhone.setText("SĐT: " + customer.getPhone());
            binding.getRoot().setOnClickListener(v -> {
                if (onCustomerListener != null) {
                    onCustomerListener.onEditCustomer(customer);
                }
            });
        }
    }

    public interface OnCustomerListener {
        void onEditCustomer(Customer customer);
    }
}
