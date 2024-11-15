package edu.mj.mart.activities.auth.login;

import static edu.mj.mart.utils.Constants.ACCOUNT_ACTIVE;
import static edu.mj.mart.utils.Constants.ACCOUNT_AVATAR;
import static edu.mj.mart.utils.Constants.ACCOUNT_FULL_NAME;
import static edu.mj.mart.utils.Constants.ACCOUNT_PASSWORD;
import static edu.mj.mart.utils.Constants.ACCOUNT_PHONE;
import static edu.mj.mart.utils.Constants.ACCOUNT_ROLE;
import static edu.mj.mart.utils.Constants.EMAIL_ADMIN_EXAMPLE;
import static edu.mj.mart.utils.Constants.FULL_NAME_ADMIN_EXAMPLE;
import static edu.mj.mart.utils.Constants.PASSWORD_ADMIN_EXAMPLE;

import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Objects;

import edu.mj.mart.base.BasePresenter;
import edu.mj.mart.model.Account;
import edu.mj.mart.utils.Constants;
import edu.mj.mart.utils.SyntheticEnum.Role;
import edu.mj.mart.utils.SyntheticEnum.StatusEmployee;

public class LoginPresenter extends BasePresenter<LoginView> {

    protected LoginPresenter(FragmentActivity fragmentActivity, LoginView view) {
        super(fragmentActivity, view);
        registerAdmin();
    }

    public void registerAdmin() {
        db.collection(Constants.DB_COLLECTION_USERS)
                .whereEqualTo(Constants.DB_COLLECTION_KEY_EMAIL, EMAIL_ADMIN_EXAMPLE)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    if (task.getResult() == null || task.getResult().isEmpty() || !task.isSuccessful()) {
                        Account account = new Account();
                        account.setRole(Role.MANAGER.value);
                        account.setEmail(EMAIL_ADMIN_EXAMPLE);
                        account.setFullName(FULL_NAME_ADMIN_EXAMPLE);
                        account.setPassword(PASSWORD_ADMIN_EXAMPLE);
                        account.setActive(StatusEmployee.ACTIVE.value);

                        db.collection(Constants.DB_COLLECTION_USERS)
                                .add(account.convertToHashMap())
                                .addOnSuccessListener(documentReference -> {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                })
                                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                    }
                });
    }

    public void login(String email, String password) {
        if (mView != null) {
            mView.showLoading();
        }
        db.collection(Constants.DB_COLLECTION_USERS)
                .whereEqualTo(Constants.DB_COLLECTION_KEY_EMAIL, email)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    mView.hideLoading();

                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String storedPassword = document.getString(ACCOUNT_PASSWORD);

                        if (storedPassword != null && storedPassword.equals(password)) {
                            int role = 0;
                            if (document.contains(ACCOUNT_ROLE)) {
                                role = Objects.requireNonNull(document.getLong(ACCOUNT_ROLE)).intValue();
                            }
                            String fullName = document.getString(ACCOUNT_FULL_NAME);
                            List<String> avatar = (List<String>) document.get(ACCOUNT_AVATAR);
                            String phone = document.getString(ACCOUNT_PHONE);

                            int active = 1;
                            if (document.contains(ACCOUNT_ACTIVE)) {
                                active = Objects.requireNonNull(document.getLong(ACCOUNT_ACTIVE)).intValue();
                            }
                            String id = document.getId();

                            Account account = new Account(id, role, email, password, phone, fullName, active, avatar);
                            mView.loginSuccessfully(account);
                        } else {
                            mView.loginFailed("Mật khẩu không chính xác");
                        }
                    } else {
                        mView.loginFailed("Không tìm thấy người dùng");
                    }
                });
    }
}
