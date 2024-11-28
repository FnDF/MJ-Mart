package edu.mj.mart.activities.customer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.mj.mart.adapter.CustomerAdapter;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityCustomerBinding;
import edu.mj.mart.model.Customer;

public class CustomerActivity extends BaseActivity<ActivityCustomerBinding> implements CustomerView {

    private CustomerPresenter presenter;
    private CustomerAdapter adapter;

    private ArrayList<Customer> customers = new ArrayList<>();

    @NonNull
    @Override
    protected ActivityCustomerBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityCustomerBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        super.setupView();
        presenter = new CustomerPresenter(CustomerActivity.this, this);
        binding.ivBack.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        fetchData();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerAdapter(new ArrayList<>(), customer -> {
            CustomerBottomSheetDialog dialog = new CustomerBottomSheetDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable("customer", customer);
            bundle.putSerializable("listener", (CustomerBottomSheetDialog.OnCustomerListener) (name, phone) -> {
                customer.setName(name);
                customer.setPhone(phone);
                presenter.updateCustomer(customer);
            });
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "customer");
        });

        binding.recyclerView.setAdapter(adapter);
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key = editable.toString().trim();
                if (TextUtils.isEmpty(key)) {
                    adapter.setDataSource(customers);
                } else {
                    ArrayList<Customer> filter = new ArrayList<>();
                    customers.forEach(customer -> {
                        if (customer.getName().isEmpty() || customer.getPhone().isEmpty()) return;
                        if (customer.getName().toLowerCase().contains(key.toLowerCase()) ||
                                customer.getPhone().toLowerCase().contains(key.toLowerCase())) {
                            filter.add(customer);
                        }
                    });
                    adapter.setDataSource(filter);
                }
            }
        });
        binding.cardViewCreateCustomer.setOnClickListener(v -> {
            CustomerBottomSheetDialog dialog = new CustomerBottomSheetDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable("listener", (CustomerBottomSheetDialog.OnCustomerListener) (name, phone) -> {
                presenter.createCustomer(name, phone);
            });
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "customer");
        });
    }

    private void fetchData() {
        presenter.getAllCustomer();
    }

    @Override
    public void showLoading() {
        binding.layoutLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.layoutLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean isNetworkConnected() {
        return true;
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void onCreateCustomerSuccess(Customer customer) {
        customers.add(0, customer);
        adapter.addDataSource(customer);
    }

    @Override
    public void onUpdateCustomerSuccess(Customer customer) {
        fetchData();
    }

    @Override
    public void onGetAllCustomerSuccess(List<Customer> customers) {
        this.customers.clear();
        this.customers.addAll(customers);

        if (customers.isEmpty()) {
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            adapter.setDataSource(customers);
        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}