package edu.mj.mart.activities.employee.edit;

import edu.mj.mart.base.BaseView;

public interface EditEmployeeView extends BaseView {
    void onUpdateSuccess();

    void onUpdateFailed();
}
