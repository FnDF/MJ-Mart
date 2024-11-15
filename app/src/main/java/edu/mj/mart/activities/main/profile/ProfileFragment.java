package edu.mj.mart.activities.main.profile;

import static edu.mj.mart.utils.SyntheticEnum.Role.MANAGER;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.R;
import edu.mj.mart.activities.employee.EmployeeManagerActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentProfileBinding;
import edu.mj.mart.model.Account;
import edu.mj.mart.utils.Constants;
import edu.mj.mart.utils.ImageUtil;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfilePresenter> implements ProfileView {

    private Account currentAccount;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentAccount = Constants.currentAccount;
        if (currentAccount != null) {
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

            binding.tvFullName.setText(currentAccount.getFullName());
            if (currentAccount.getRole() == MANAGER.value) {
                binding.tvRole.setText(getString(R.string.title_manager));
            } else {
                binding.tvRole.setText(getString(R.string.title_staff));
                binding.tvOptionManager.setVisibility(View.GONE);
                binding.layoutReport.setVisibility(View.GONE);
                binding.layoutEmployees.setVisibility(View.GONE);
            }
        }

        binding.layoutEmployees.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EmployeeManagerActivity.class)));
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
