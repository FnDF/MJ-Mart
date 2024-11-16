package edu.mj.mart.activities.employee.create;

import androidx.fragment.app.FragmentActivity;

import edu.mj.mart.base.BasePresenter;
import edu.mj.mart.model.Employee;
import edu.mj.mart.utils.Constants;

public class CreateEmployeePresenter extends BasePresenter<CreateEmployeeView> {

    protected CreateEmployeePresenter(FragmentActivity fragmentActivity, CreateEmployeeView view) {
        super(fragmentActivity, view);
    }

    public void checkEmailIsExists(String email) {
        db.collection(Constants.DB_COLLECTION_USERS)
                .whereEqualTo(Constants.DB_COLLECTION_KEY_EMAIL, email)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    boolean isExists = task.getResult() != null && !task.getResult().isEmpty();
                    if (isExists) {
                        mView.hideLoading();
                    }
                    mView.onEmailsIsExists(isExists);
                })
                .addOnFailureListener(ex -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    mView.onError(ex.getMessage());
                });
    }

    public void createEmployee(Employee employee) {
        db.collection(Constants.DB_COLLECTION_USERS)
                .add(employee.convertToHashMap())
                .addOnSuccessListener(documentReference -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    mView.onCreateEmployeeSuccessfully();
                })
                .addOnFailureListener(e -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    mView.onCreateEmployeeFailed();
                });
    }
}
