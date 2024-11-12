package edu.mj.mart.base;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;

public abstract class BasePresenter<V extends BaseView> {

    protected final FragmentActivity fragmentActivity;

    protected FirebaseAuth mAuth;

    protected String TAG = this.getClass().getName();

    protected V mView;

    protected BasePresenter(FragmentActivity fragmentActivity, V view) {
        this.fragmentActivity = fragmentActivity;
        this.mView = view;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void onDetach() {
        mView = null;
    }
}
