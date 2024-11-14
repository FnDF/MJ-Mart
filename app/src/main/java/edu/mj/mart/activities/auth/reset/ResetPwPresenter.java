package edu.mj.mart.activities.auth.reset;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

import edu.mj.mart.base.BasePresenter;
import edu.mj.mart.utils.Constants;

public class ResetPwPresenter extends BasePresenter<ResetPwView> {

    protected ResetPwPresenter(FragmentActivity fragmentActivity, ResetPwView view) {
        super(fragmentActivity, view);
    }

    public void resetPassword(String email, String password) {
        if (mView != null) {
            mView.showLoading();
        }
        db.collection(Constants.DB_COLLECTION_USERS)
                .whereEqualTo(Constants.DB_COLLECTION_KEY_EMAIL, email)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    mView.hideLoading();
                    if (task.getResult() == null || task.getResult().isEmpty() || !task.isSuccessful()) {
                        mView.hideLoading();
                        mView.getEmailFailed();
                    } else {
                        Map<String, Object> rs = new HashMap<>();
                        rs.put("password", password);
                        rs.put("email", email);
                        rs.put("id", task.getResult().getDocuments().get(0).get("id"));
                        rs.put("role", task.getResult().getDocuments().get(0).get("role"));
                        db.collection(Constants.DB_COLLECTION_USERS)
                                .document(task.getResult().getDocuments().get(0).getId())
                                .set(rs)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (mView == null) return;
                                        mView.hideLoading();
                                        if (task.isSuccessful()) {
                                            mView.resetPWSuccessfully();
                                        } else {
                                            mView.getEmailFailed();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        if (mView != null) {
                                            mView.hideLoading();
                                            mView.getEmailFailed();
                                        }
                                    }
                                });

                    }
                })
                .addOnFailureListener(e -> {
                    mView.hideLoading();
                    mView.getEmailFailed();
                });
    }
}
