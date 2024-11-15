package edu.mj.mart.activities.employee.create;

import edu.mj.mart.base.BaseView;

public interface CreateEmployeeView extends BaseView {

    void onError(String msg);

    void onEmailsIsExists(boolean isExists);

    void onCreateEmployeeSuccessfully();

    void onCreateEmployeeFailed();
}
