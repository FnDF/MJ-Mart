package edu.mj.mart.activities.employee.list;

import static edu.mj.mart.utils.Constants.ACCOUNT_ACTIVE;
import static edu.mj.mart.utils.Constants.ACCOUNT_AVATAR;
import static edu.mj.mart.utils.Constants.ACCOUNT_EMAIL;
import static edu.mj.mart.utils.Constants.ACCOUNT_FULL_NAME;
import static edu.mj.mart.utils.Constants.ACCOUNT_PHONE;
import static edu.mj.mart.utils.Constants.ACCOUNT_ROLE;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.mj.mart.base.BasePresenter;
import edu.mj.mart.model.Account;
import edu.mj.mart.utils.Constants;

public class EmployeesPresenter extends BasePresenter<EmployeesView> {

    protected EmployeesPresenter(FragmentActivity fragmentActivity, EmployeesView view) {
        super(fragmentActivity, view);
    }

    public void getEmployees() {
        if (mView != null) {
            mView.showLoading();
        }
        db.collection(Constants.DB_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    ArrayList<Account> accounts = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            int role = 0;
                            if (document.contains(ACCOUNT_ROLE)) {
                                role = Objects.requireNonNull(document.getLong(ACCOUNT_ROLE)).intValue();
                            }
                            String fullName = document.getString(ACCOUNT_FULL_NAME);
                            List<String> avatar = (List<String>) document.get(ACCOUNT_AVATAR);
                            String phone = document.getString(ACCOUNT_PHONE);
                            String email = document.getString(ACCOUNT_EMAIL);

                            int active = 1;
                            if (document.contains(ACCOUNT_ACTIVE)) {
                                active = Objects.requireNonNull(document.getLong(ACCOUNT_ACTIVE)).intValue();
                            }

                            Account account = new Account(document.getId(), role, email, phone, fullName, active, avatar);
                            accounts.add(account);
                        }
                    } else {
                        Log.w("EmployeesPresenter", "Error getting documents.", task.getException());
                    }
                    mView.onGetEmployeesSuccess(accounts);
                }).addOnFailureListener(e -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    mView.onGetEmployeesFailed(e.getMessage());
                });
    }

}
