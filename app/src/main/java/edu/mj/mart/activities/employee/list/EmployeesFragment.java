package edu.mj.mart.activities.employee.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentEmployeesBinding;

public class EmployeesFragment extends BaseFragment<FragmentEmployeesBinding, EmployeesPresenter>
        implements EmployeesView {

    @NonNull
    @Override
    protected FragmentEmployeesBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEmployeesBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected EmployeesPresenter createPresenter() {
        return new EmployeesPresenter(requireActivity(), this);
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
