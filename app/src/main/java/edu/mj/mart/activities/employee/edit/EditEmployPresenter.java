package edu.mj.mart.activities.employee.edit;

import androidx.fragment.app.FragmentActivity;

import edu.mj.mart.base.BasePresenter;

public class EditEmployPresenter extends BasePresenter<EditEmployeeView> {
    protected EditEmployPresenter(FragmentActivity fragmentActivity, EditEmployeeView view) {
        super(fragmentActivity, view);
    }
}
