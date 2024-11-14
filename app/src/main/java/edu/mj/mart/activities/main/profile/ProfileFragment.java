package edu.mj.mart.activities.main.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentProfileBinding;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfilePresenter> implements ProfileView {
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

    @NonNull
    @Override
    protected FragmentProfileBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected ProfilePresenter createPresenter() {
        return new ProfilePresenter(requireActivity(), this);
    }
}
