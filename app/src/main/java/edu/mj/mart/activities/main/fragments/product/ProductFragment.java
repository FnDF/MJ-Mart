package edu.mj.mart.activities.main.fragments.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentProductBinding;

public class ProductFragment extends BaseFragment<FragmentProductBinding, ProductPresenter> implements ProductView {
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
}
