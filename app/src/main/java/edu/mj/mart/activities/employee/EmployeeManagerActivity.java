package edu.mj.mart.activities.employee;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import edu.mj.mart.R;
import edu.mj.mart.activities.auth.register.RegisterFragment;
import edu.mj.mart.activities.employee.create.CreateEmployeeFragment;
import edu.mj.mart.activities.employee.list.EmployeesFragment;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityEmployeeManagerBinding;

public class EmployeeManagerActivity extends BaseActivity<ActivityEmployeeManagerBinding> {

    private EmployeesFragment employeesFragment;

    @NonNull
    @Override
    protected ActivityEmployeeManagerBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityEmployeeManagerBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        super.setupView();

        if (employeesFragment == null) {
            employeesFragment = new EmployeesFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(binding.subContainer.getId(), employeesFragment)
                .commit();
    }

    public void showLoading() {
        binding.layoutLoading.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        binding.layoutLoading.setVisibility(View.GONE);
    }

    public void onNavigationCreate() {
        getSupportFragmentManager().beginTransaction()
                .add(binding.subContainer.getId(), new CreateEmployeeFragment())
                .addToBackStack("create")
                .commit();
    }
}