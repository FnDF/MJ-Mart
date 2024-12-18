package edu.mj.mart.activities.main.home;

import static edu.mj.mart.utils.SyntheticEnum.Role.STAFF;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.R;
import edu.mj.mart.activities.employee.EmployeeManagerActivity;
import edu.mj.mart.activities.main.MainActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentHomeBinding;
import edu.mj.mart.model.Account;
import edu.mj.mart.utils.Constants;
import edu.mj.mart.utils.ImageUtil;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomePresenter> implements HomeView {

    private Account currentAccount;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Constants.getAccountLiveData().observe(getViewLifecycleOwner(), account -> {
            currentAccount = account;
            setupAccount();
        });
        currentAccount = Constants.currentAccount;
        if (currentAccount != null) {
            setupAccount();
            if (currentAccount.getRole() == STAFF.value) {

                binding.tvOverview1.setText(getString(R.string.list_job));
                binding.tvValueOverview1.setText("22");
                binding.tvOverview2.setText(getString(R.string.orders_of_the_day));
                binding.tvValueOverview2.setText("15.000");
            }
        }

        binding.layoutOption3.setOnClickListener(v -> {
            if (currentAccount == null) return;
            if (currentAccount.getRole() == STAFF.value) {
                Toast.makeText(requireContext(), "Bạn không có đủ quyền hạn cần thiết", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Chức năng Thống kê đang được xây dựng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAccount() {
        binding.tvTitle.setText(getString(R.string.title_welcome, currentAccount.getFullName()));

        if (currentAccount.getAvatar() == null || currentAccount.getAvatar().isEmpty()) {
            binding.ivAvatar.setImageResource(R.drawable.icon_profile_default);
        } else {
            StringBuilder ima = new StringBuilder();
            for (String s : currentAccount.getAvatar()) {
                ima.append(s);
            }
            if (ima.toString().isEmpty()) {
                binding.ivAvatar.setImageResource(R.drawable.icon_profile_default);
            } else {
                binding.ivAvatar.setImageBitmap(ImageUtil.decode(ima.toString()));
            }
        }
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
