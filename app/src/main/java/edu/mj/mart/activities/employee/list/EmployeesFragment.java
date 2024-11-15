package edu.mj.mart.activities.employee.list;

import static edu.mj.mart.utils.SyntheticEnum.Role.MANAGER;
import static edu.mj.mart.utils.SyntheticEnum.Role.STAFF;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import edu.mj.mart.activities.employee.EmployeeManagerActivity;
import edu.mj.mart.adapter.EmployeeAdapter;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentEmployeesBinding;
import edu.mj.mart.model.Employee;
import edu.mj.mart.model.Account;
import edu.mj.mart.utils.Constants;

public class EmployeesFragment extends BaseFragment<FragmentEmployeesBinding, EmployeesPresenter>
        implements EmployeesView {

    private Account currentAccount;
    private EmployeeAdapter adapter;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivBack.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        currentAccount = Constants.currentAccount;
        if (currentAccount != null && currentAccount.getRole() == STAFF.value) {
            binding.cardViewCreateEmployee.setVisibility(View.GONE);
        }

        adapter = new EmployeeAdapter(new ArrayList<>(), item -> {
            if (currentAccount != null && currentAccount.getRole() == MANAGER.value) {
                // todo
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setAdapter(adapter);

        binding.cardViewCreateEmployee.setOnClickListener(v -> {
            ((EmployeeManagerActivity) requireActivity()).onNavigationCreate();
        });

        presenter.getEmployees();
    }

    @Override
    public void showLoading() {
        ((EmployeeManagerActivity) requireActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((EmployeeManagerActivity) requireActivity()).hideLoading();
    }

    @Override
    public void onGetEmployeesSuccess(List<Employee> employees) {
        adapter.setDataSource(employees);
    }

    @Override
    public void onGetEmployeesFailed(String message) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }
}
