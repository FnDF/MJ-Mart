package edu.mj.mart.activities.employee.create;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

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
