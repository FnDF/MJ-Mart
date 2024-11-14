package edu.mj.mart.activities.auth.login;

import static edu.mj.mart.utils.SharedPrefUtils.KEY_IS_REGISTER_ADMIN;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

import edu.mj.mart.base.BasePresenter;
import edu.mj.mart.utils.AESEncryption;
import edu.mj.mart.utils.Constants;
import edu.mj.mart.utils.SharedPrefUtils;

public class LoginPresenter extends BasePresenter<LoginView> {

    protected LoginPresenter(FragmentActivity fragmentActivity, LoginView view) {
        super(fragmentActivity, view);

        registerAdmin();
    }

    public void registerAdmin() {
        db.collection(Constants.DB_COLLECTION_USERS)
                .whereEqualTo(Constants.DB_COLLECTION_KEY_EMAIL, "hoanggiadai19@gmail.com")
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    if (task.getResult() == null || task.getResult().isEmpty() || !task.isSuccessful()) {
                        Map<String, Object> user = new HashMap<>();
                        user.put("id", "1");
                        user.put("role", "1");
                        user.put("email", "hoanggiadai19@gmail.com");
                        user.put("password", AESEncryption.encrypt("Abc12345"));
                        db.collection(Constants.DB_COLLECTION_USERS)
                                .add(user)
                                .addOnSuccessListener(documentReference -> {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    SharedPrefUtils.saveData(fragmentActivity, KEY_IS_REGISTER_ADMIN, true);

                                })
                                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                    }
                });
    }

    public void login(String email, String password) {
        if (mView != null) {
            mView.showLoading();
        }
//        db.collection(Constants.DB_COLLECTION_USERS)
//                .whereEqualTo(Constants.DB_COLLECTION_KEY_EMAIL, email)
//                .whereEqualTo(Constants.DB_COLLECTION_KEY_PASSWORD, password)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (mView == null) return;
//                    mView.hideLoading();
//                    if (task.getResult() == null || !task.isSuccessful()) {
//                        mView.loginFailed();
//                    } else {
//                        mView.loginSuccessfully();
//                    }
//                });

        db.collection(Constants.DB_COLLECTION_USERS)
                .whereEqualTo(Constants.DB_COLLECTION_KEY_EMAIL, email)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    mView.hideLoading();

                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String storedPassword = document.getString("password");
                        String role = document.getString("role");

                        // Kiểm tra mật khẩu
                        if (storedPassword != null && storedPassword.equals(password)) {
                            mView.loginSuccessfully();
                        } else {
                            mView.loginFailed("Mật khẩu không chính xác");
                        }
                    } else {
                        mView.loginFailed("Không tìm thấy người dùng");
                    }
                });
    }
}
