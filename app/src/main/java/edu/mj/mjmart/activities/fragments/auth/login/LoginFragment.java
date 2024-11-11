package edu.mj.mjmart.activities.fragments.auth.login;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import edu.mj.mjmart.core.BaseFragment;
import edu.mj.mjmart.databinding.FragmentLoginBinding;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginPresenter> implements LoginView {
    @NonNull
    @Override
    protected FragmentLoginBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(requireActivity(), this);
    }

    @Override
    public void loginSuccessfully() {

    }

    @Override
    public void loginFailed() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }
}
