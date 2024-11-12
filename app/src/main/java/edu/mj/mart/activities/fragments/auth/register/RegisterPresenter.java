package edu.mj.mart.activities.fragments.auth.register;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import edu.mj.mart.base.BasePresenter;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    protected RegisterPresenter(FragmentActivity fragmentActivity, RegisterView view) {
        super(fragmentActivity, view);
    }

    public void register(String email, String password) {
        mView.showLoading();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(fragmentActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mView.hideLoading();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            // FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                            mView.onRegisterSuccessfully();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            mView.onRegisterFailed();
                        }
                    }
                });
    }
}
