package edu.mj.mart.activities.employee.edit;

import edu.mj.mart.base.BaseView;
import edu.mj.mart.model.Employee;

public interface EditEmployeeView extends BaseView {
    void onUpdateSuccess(Employee employee);

    void onUpdateFailed();
}
