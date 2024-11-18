package edu.mj.mart.activities.main.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.activities.industry.CommodityIndustryActivity;
import edu.mj.mart.activities.supplier.SupplierActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentProductBinding;

public class ProductFragment extends BaseFragment<FragmentProductBinding, ProductPresenter> implements ProductView {

    @NonNull
    @Override
    protected FragmentProductBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProductBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected ProductPresenter createPresenter() {
        return new ProductPresenter(requireActivity(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.layoutCommodityIndustry.setOnClickListener(view1 -> startActivity(new Intent(requireActivity(), CommodityIndustryActivity.class)));
        binding.layoutSupplier.setOnClickListener(view1 -> startActivity(new Intent(requireActivity(), SupplierActivity.class)));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }
}
