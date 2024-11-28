package edu.mj.mart.activities.main.bills;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentBillsBinding;

public class BillsFragment extends BaseFragment<FragmentBillsBinding, BillsPresenter> implements BillsView {
    @NonNull
    @Override
    protected FragmentBillsBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentBillsBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected BillsPresenter createPresenter() {
        return new BillsPresenter(requireActivity(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.viewCreate.setOnClickListener(view1 -> {

        });
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
