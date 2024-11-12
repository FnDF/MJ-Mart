package edu.mj.mart.activities.fragments.auth.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.mj.mart.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginView> {

    protected LoginPresenter(FragmentActivity fragmentActivity, LoginView view) {
        super(fragmentActivity, view);
    }

    public void login(String email, String password) {
        mView.showLoading();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(fragmentActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                            mView.loginSuccessfully();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            mView.loginFailed();
                        }
                    }
                });
    }


}
