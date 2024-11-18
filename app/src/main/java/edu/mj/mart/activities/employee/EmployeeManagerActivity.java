package edu.mj.mart.activities.employee;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Objects;

import edu.mj.mart.activities.employee.create.CreateEmployeeFragment;
import edu.mj.mart.activities.employee.edit.EditEmployeeFragment;
import edu.mj.mart.activities.employee.list.EmployeesFragment;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityEmployeeManagerBinding;
import edu.mj.mart.model.Employee;
import edu.mj.mart.utils.Constants;

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
        if (getIntent() != null) {
            if (getIntent().hasExtra("is_start_edit_current_account")
                    && getIntent().getBooleanExtra("is_start_edit_current_account", false)) {
                if (Constants.convertFromCurrentAccount() != null) {
                    onNavigationEdit(Objects.requireNonNull(Constants.convertFromCurrentAccount()), false, true);
                    return;
                }
            }
        }
        showEmployees();
    }

    private void showEmployees() {
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

    public void reloadData(Employee employee) {
        if (employeesFragment != null) {
            if (Objects.equals(employee.getId(), Constants.currentAccount.getId())) {
                Constants.updateCurrentAccount(employee);
            }
            employeesFragment.reloadData();
        } else {
            Constants.updateCurrentAccount(employee);
            finish();
        }
    }

    public void onNavigationCreate() {
        getSupportFragmentManager().beginTransaction()
                .add(binding.subContainer.getId(), new CreateEmployeeFragment())
                .addToBackStack("create")
                .commit();
    }

    public void onNavigationEdit(Employee employee, boolean isAddToBackStack, boolean isEditMySelf) {
        EditEmployeeFragment fragment = new EditEmployeeFragment(employee.copy(), isEditMySelf);
        if (isAddToBackStack) {
            getSupportFragmentManager().beginTransaction().add(binding.subContainer.getId(), fragment).addToBackStack("edit").commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(binding.subContainer.getId(), fragment).commit();
        }
    }
}