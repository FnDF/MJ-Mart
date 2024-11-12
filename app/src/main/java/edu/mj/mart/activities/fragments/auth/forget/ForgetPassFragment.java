package edu.mj.mart.activities.fragments.auth.forget;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import edu.mj.mart.activities.AuthActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentForgetPassBinding;

public class ForgetPassFragment extends BaseFragment<FragmentForgetPassBinding, ForgetPassPresenter>
        implements ForgetPassView {
    @NonNull
    @Override
    protected FragmentForgetPassBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentForgetPassBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected ForgetPassPresenter createPresenter() {
        return new ForgetPassPresenter(requireActivity(), this);
    }

    @Override
    public void sendOTPSuccessfully() {

    }

    @Override
    public void sendOTPFailed() {

    }

    @Override
    public void showLoading() {
        ((AuthActivity) requireActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((AuthActivity) requireActivity()).hideLoading();
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }
}
