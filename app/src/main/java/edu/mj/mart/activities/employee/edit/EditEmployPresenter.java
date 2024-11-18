package edu.mj.mart.activities.employee.edit;

import androidx.fragment.app.FragmentActivity;

import edu.mj.mart.base.BasePresenter;
import edu.mj.mart.model.Employee;
import edu.mj.mart.utils.Constants;

public class EditEmployPresenter extends BasePresenter<EditEmployeeView> {
    protected EditEmployPresenter(FragmentActivity fragmentActivity, EditEmployeeView view) {
        super(fragmentActivity, view);
    }

    public void updateEmployee(Employee employee) {
        if (mView != null) {
            mView.showLoading();
            mView.hideKeyboard();
        }
        // Add a new document with a generated ID
        db.collection(Constants.DB_COLLECTION_USERS)
                .document(employee.getId())
                .set(employee.convertToHashMap())
                .addOnSuccessListener(aVoid -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    mView.onUpdateSuccess(employee);
                })
                .addOnFailureListener(e -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    mView.onUpdateFailed();
                });
    }
}
