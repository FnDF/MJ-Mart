package edu.mj.mart.activities.employee;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

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
                .replace(binding.main.getId(), employeesFragment)
                .addToBackStack("otp")
                .commit();
    }
}