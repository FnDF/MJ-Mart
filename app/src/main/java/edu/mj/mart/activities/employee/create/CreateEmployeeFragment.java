package edu.mj.mart.activities.employee.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentCreateEmployeeBinding;

public class CreateEmployeeFragment extends BaseFragment<FragmentCreateEmployeeBinding, CreateEmployeePresenter>
        implements CreateEmployeeView {

    @NonNull
    @Override
    protected FragmentCreateEmployeeBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCreateEmployeeBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected CreateEmployeePresenter createPresenter() {
        return new CreateEmployeePresenter(requireActivity(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivBack.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
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
