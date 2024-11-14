package edu.mj.mart.activities.employee.list;

import androidx.fragment.app.FragmentActivity;

import edu.mj.mart.base.BasePresenter;

public class EmployeesPresenter extends BasePresenter<EmployeesView> {

    protected EmployeesPresenter(FragmentActivity fragmentActivity, EmployeesView view) {
        super(fragmentActivity, view);
    }
}
