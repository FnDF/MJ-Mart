package edu.mj.mart.activities.employee.list;

import java.util.List;

import edu.mj.mart.base.BaseView;
import edu.mj.mart.model.Account;

public interface EmployeesView extends BaseView {

    void onGetEmployeesSuccess(List<Account> employees);

    void onGetEmployeesFailed(String message);
}
