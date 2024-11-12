package edu.mj.mart.activities.main.fragments.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomePresenter> implements HomeView {
    @NonNull
    @Override
    protected FragmentHomeBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(requireActivity(), this);
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
