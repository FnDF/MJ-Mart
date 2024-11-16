package edu.mj.mart.activities.employee.list;

import java.util.List;

import edu.mj.mart.base.BaseView;
import edu.mj.mart.model.Employee;

public interface EmployeesView extends BaseView {

    void onGetEmployeesSuccess(List<Employee> employees);

    void onGetEmployeesFailed(String message);
}
